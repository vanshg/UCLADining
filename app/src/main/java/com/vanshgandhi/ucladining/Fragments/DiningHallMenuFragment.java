package com.vanshgandhi.ucladining.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.vanshgandhi.ucladining.Activities.MainActivity;
import com.vanshgandhi.ucladining.Adapters.MenuAdapter;
import com.vanshgandhi.ucladining.Helpers.GridSpacingItemDecoration;
import com.vanshgandhi.ucladining.Helpers.JsonObjectRequestWithCache;
import com.vanshgandhi.ucladining.Models.FoodItem;
import com.vanshgandhi.ucladining.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DiningHallMenuFragment extends Fragment {
    MainActivity mainActivity;

    private static final String ARG_MEAL = "meal_type";

    public enum Meal {Breakfast, Lunch, Dinner}

    private static final int COVEL  = 0;
    private static final int DENEVE = 1;
    private static final int FEAST  = 2;
    private static final int BPLATE = 3;

    RecyclerView   recyclerView;
    List<FoodItem> food;

    public static DiningHallMenuFragment newInstance(Meal meal) {
        DiningHallMenuFragment fragment = new DiningHallMenuFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MEAL, meal.ordinal());
        fragment.setArguments(args);
        return fragment;
    }

    public DiningHallMenuFragment() {
        //Mandatory empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dining_hall_menu, container, false);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        recyclerView = (RecyclerView) v.findViewById(R.id.list);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 24, true));
        food = new ArrayList<>();
        int hall = getArguments().getInt(ARG_MEAL);

        final String url;
        String baseUrl = "https://api.import.io/store/data/";
        String twoMeal = "f20fc91a-caf1-409c-9322-efa0ef770223/_query?input/webpage/url=";
        String threeMeal = "d90c4352-d57c-4773-9064-4af17341beef/_query?input/webpage/url=";
        String ingredients = "eacba959-1feb-4119-9388-bbb5cd4fdfff/_query?input/webpage/url=";
        String uclaBaseUrl = "http%3A%2F%2Fmenu.ha.ucla.edu%2Ffoodpro%2F";
        String fullMenu = "default.asp%3Flocation%3D" + getHallCode(
                hall) + "%26date%3D" + (mainActivity.getMonth() + 1) + "%252F" + mainActivity.getDay() + "%252F" + mainActivity
                .getYear();
        String apiKey = "&_user=22403bda-b7eb-4c87-904a-78de1838426c&_apikey=22403bdab7eb4c87904a78de1838426c6e7d3048637d4bbae71657eb53b31c47d987e5e1cb53206a5fac41e1b938b1abcbb0ed68909ebb9d9e75447cc09546577d6725bd3f2bee95e827ee604fa7d84c";
        int dayOfWeek = mainActivity.getDayOfWeek();
        if (hall == COVEL || hall == FEAST || dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) //2 Meal
        {
            url = baseUrl + twoMeal + uclaBaseUrl + fullMenu + apiKey;
            Cache cache = queue.getCache();
            Cache.Entry entry = cache.get(url);
            if (entry != null) {
                String data = new String(entry.data);
                try {
                    JSONObject response = new JSONObject(data);
                    processList(response, false);
                } catch (JSONException e) {
                    recyclerView.setAdapter(new MenuAdapter(food));
                    cache.remove(url);
                }
            } else {
                JsonObjectRequestWithCache request = new JsonObjectRequestWithCache(
                        Request.Method.GET, url, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            processList(response, false);
                        } catch (JSONException e) {
                            recyclerView.setAdapter(new MenuAdapter(food));
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        recyclerView.setAdapter(new MenuAdapter(food));
                    }
                });

                queue.add(request);
            }
        } else //if(hall == DENEVE || hall == BPLATE) //3 Meal
        {
            url = baseUrl + threeMeal + uclaBaseUrl + fullMenu + apiKey;
            Cache cache = queue.getCache();
            Cache.Entry entry = cache.get(url);
            if (entry != null) {
                String data = new String(entry.data);
                try {
                    JSONObject response = new JSONObject(data);
                    processList(response, false);
                } catch (JSONException e) {
                    recyclerView.setAdapter(new MenuAdapter(food));
                    cache.remove(url);
                }
            } else {
                JsonObjectRequestWithCache request = new JsonObjectRequestWithCache(
                        Request.Method.GET, url, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            processList(response, true);
                        } catch (JSONException e) {
                            recyclerView.setAdapter(new MenuAdapter(food));
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        recyclerView.setAdapter(new MenuAdapter(food));
                    }
                });

                queue.add(request);
            }
        }
        return v;
    }

    private void processList(JSONObject result, boolean threeMeal) throws JSONException {
        JSONArray jsonArray;

        if (result.has("results")) {
            jsonArray = result.getJSONArray("results");
        } else {
            return;
        }

        String breakfast;
        String lunch;
        String dinner;
        Document doc;
        Elements ul;
        Elements li;
        if (threeMeal) {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (jsonObject.has("breakfast")) {
                    breakfast = jsonObject.getString("breakfast");
                } else {
                    continue;
                }

                doc = Jsoup.parse(breakfast);
                ul = doc.select("ul");
                li = ul.select("li"); // select all li from ul
                for (Element element : li) {
                    String title = element.select("a").text();
                    if (title.isEmpty()) {
                        continue;
                    }
                    final FoodItem item = new FoodItem(title);
                    String href = element.select("a").attr("href");
                    if (href.contains("recipedetail.asp")) {
                        item.setRecipeNumber(href.substring(30, 36));
                        item.setPortionSize(href.substring(49));
                    }

                    food.add(item);
                }
            }
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            if (jsonObject.has("lunch")) {
                lunch = jsonObject.getString("lunch");
            } else {
                continue;
            }

            doc = Jsoup.parse(lunch);
            ul = doc.select("ul");
            li = ul.select("li"); // select all li from ul
            for (Element element : li) {
                String title = element.select("a").text();
                if (title.isEmpty()) {
                    continue;
                }
                final FoodItem item = new FoodItem(title);
                String href = element.select("a").attr("href");
                if (href.contains("recipedetail.asp")) {
                    item.setRecipeNumber(href.substring(30, 36));
                    item.setPortionSize(href.substring(49));
                }
                food.add(item);
            }
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            if (jsonObject.has("dinner")) {
                dinner = jsonObject.getString("dinner");
            } else {
                continue;
            }

            doc = Jsoup.parse(dinner);
            ul = doc.select("ul");
            li = ul.select("li"); // select all li from ul
            for (Element element : li) {
                String title = element.select("a").text();
                if (title.isEmpty()) {
                    continue;
                }
                final FoodItem item = new FoodItem(title);
                String href = element.select("a").attr("href");
                if (href.contains("recipedetail.asp")) {
                    item.setRecipeNumber(href.substring(30, 36));
                    item.setPortionSize(href.substring(49));
                }
                food.add(item);
            }
        }
        recyclerView.setAdapter(new MenuAdapter(food));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            DiningHallMenusHolderFragment f = (DiningHallMenusHolderFragment) getParentFragment();
            f.setCurrentHall(getArguments().getInt(ARG_MEAL));
        }
    }

    public String getHallCode(int hall) {
        switch (hall) {
            case COVEL:
                return "07";
            case DENEVE:
                return "01";
            case BPLATE:
                return "02";
            case FEAST:
                return "04";
            default:
                return "01";
        }
    }
}
