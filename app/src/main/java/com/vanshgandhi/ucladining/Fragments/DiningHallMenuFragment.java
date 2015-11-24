package com.vanshgandhi.ucladining.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.vanshgandhi.ucladining.Activities.FoodDetailActivity;
import com.vanshgandhi.ucladining.Activities.MainActivity;
import com.vanshgandhi.ucladining.Adapters.MenuAdapter;
import com.vanshgandhi.ucladining.Helpers.JsonObjectRequestWithCache;
import com.vanshgandhi.ucladining.Models.FoodItem;
import com.vanshgandhi.ucladining.Models.Menu;
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


public class DiningHallMenuFragment extends ListFragment
{
    MainActivity mainActivity;

    private static final String ARG_HALL_NUMBER = "hall_number";

    private static final int COVEL  = 0;
    private static final int DENEVE = 1;
    private static final int FEAST  = 2;
    private static final int BPLATE = 3;

    private ArrayList<String>   foodNames = new ArrayList<>();
    private ArrayList<FoodItem> foodItems = new ArrayList<>();
    private Menu menu;

    public static DiningHallMenuFragment newInstance(int hallNumber)
    {
        DiningHallMenuFragment fragment = new DiningHallMenuFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_HALL_NUMBER, hallNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public DiningHallMenuFragment()
    {
        //Mandatory empty constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        int hall = getArguments().getInt(ARG_HALL_NUMBER);
        menu = new Menu(hall);
        final String url;
        String baseUrl = "https://api.import.io/store/data/";
        String twoMeal = "f20fc91a-caf1-409c-9322-efa0ef770223/_query?input/webpage/url=";
        String threeMeal = "d90c4352-d57c-4773-9064-4af17341beef/_query?input/webpage/url=";
        String ingredients = "eacba959-1feb-4119-9388-bbb5cd4fdfff/_query?input/webpage/url=";
        String uclaBaseUrl = "http%3A%2F%2Fmenu.ha.ucla.edu%2Ffoodpro%2F";
        String fullMenu = "default.asp%3Flocation%3D" + getHallCode(hall) + "%26date%3D" + (mainActivity.getMonth() + 1) + "%252F" + mainActivity.getDay() + "%252F" + mainActivity.getYear();
        String apiKey = "&_user=22403bda-b7eb-4c87-904a-78de1838426c&_apikey=22403bdab7eb4c87904a78de1838426c6e7d3048637d4bbae71657eb53b31c47d987e5e1cb53206a5fac41e1b938b1abcbb0ed68909ebb9d9e75447cc09546577d6725bd3f2bee95e827ee604fa7d84c";
        int dayOfWeek = mainActivity.getDayOfWeek();
        if (hall == COVEL || hall == FEAST || dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) //2 Meal
        {
            url = baseUrl + twoMeal + uclaBaseUrl + fullMenu + apiKey;

            Cache cache = queue.getCache();
            Cache.Entry entry = cache.get(url);
            if(entry != null)
            {
                String data = new String(entry.data);
                try {
                    JSONObject response = new JSONObject(data);
                    processList(response, false);
                }
                catch (JSONException e) {
                    foodItems.add(new FoodItem("Error"));
                    setListAdapter(new MenuAdapter(getContext(), R.layout.list_item_food, foodItems));
                    cache.remove(url);
                }
            }
            else {
                JsonObjectRequestWithCache request = new JsonObjectRequestWithCache(Request.Method.GET, url, new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try {
                            processList(response, false);
                        }
                        catch (JSONException e) {
                            foodItems.add(new FoodItem("Error"));
                            setListAdapter(new MenuAdapter(getContext(), R.layout.list_item_food, foodItems));
                        }
                    }
                }, new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        foodItems.add(new FoodItem("Error"));
                        setListAdapter(new MenuAdapter(getContext(), R.layout.list_item_food, foodItems));
                    }
                });

                queue.add(request);
            }
        }
        else //if(hall == DENEVE || hall == BPLATE) //3 Meal
        {
            url = baseUrl + threeMeal + uclaBaseUrl + fullMenu + apiKey;
            Cache cache = queue.getCache();
            Cache.Entry entry = cache.get(url);
            if(entry != null)
            {
                String data = new String(entry.data);
                try {
                    JSONObject response = new JSONObject(data);
                    processList(response, false);
                }
                catch (JSONException e) {
                    foodItems.add(new FoodItem("Error"));
                    setListAdapter(new MenuAdapter(getContext(), R.layout.list_item_food, foodItems));
                    cache.remove(url);
                }
            }
            else {
                JsonObjectRequestWithCache request = new JsonObjectRequestWithCache(Request.Method.GET, url, new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try {
                            processList(response, true);
                        }
                        catch (JSONException e) {
                            foodItems.add(new FoodItem("Error"));
                            setListAdapter(new MenuAdapter(getContext(), R.layout.list_item_food, foodItems));
                        }
                    }
                }, new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        foodItems.add(new FoodItem("Error"));
                        setListAdapter(new MenuAdapter(getContext(), R.layout.list_item_food, foodItems));
                    }
                });

                queue.add(request);
            }
        }

    }

    private void processList(JSONObject result, boolean threeMeal) throws JSONException
    {
        JSONArray jsonArray;
        if (result.has("results")) {
            jsonArray = result.getJSONArray("results");
        }
        else {
            return;
        }

        String lunch;
        String dinner;
        Document doc;
        Elements ul;
        Elements li;
        if(threeMeal) {
            foodItems.add(new FoodItem("BREAKFAST"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (jsonObject.has("breakfast")) {
                    lunch = jsonObject.getString("breakfast");
                }
                else {
                    continue;
                }
                doc = Jsoup.parse(lunch);
                ul = doc.select("ul");
                li = ul.select("li"); // select all li from ul
                for (Element element : li) {
                    String title = element.select("a").text();
                    FoodItem item = new FoodItem(title);
                    String href = element.select("a").attr("href");
                    if (href.contains("recipedetail.asp")) {
                        item.setRecipeNumber(href.substring(30, 36));
                        item.setPortionSize(href.substring(49));
                    }
                    foodItems.add(item);
                }

            }
        }
        foodItems.add(new FoodItem("LUNCH"));

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            if (jsonObject.has("lunch")) {
                lunch = jsonObject.getString("lunch");
            }
            else {
                continue;
            }

            doc = Jsoup.parse(lunch);
            ul = doc.select("ul");
            li = ul.select("li"); // select all li from ul
            for (Element element : li) {
                String title = element.select("a").text();
                FoodItem item = new FoodItem(title);
                String href = element.select("a").attr("href");
                if (href.contains("recipedetail.asp")) {
                    item.setRecipeNumber(href.substring(30, 36));
                    item.setPortionSize(href.substring(49));
                }
                foodItems.add(item);
            }

        }
        foodItems.add(new FoodItem("DINNER"));
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            if (jsonObject.has("dinner")) {
                dinner = jsonObject.getString("dinner");
            }
            else {
                continue;
            }
            doc = Jsoup.parse(dinner);
            ul = doc.select("ul");
            li = ul.select("li"); // select all li from ul
            for (Element element : li) {
                String title = element.select("a").text();
                FoodItem item = new FoodItem(title);
                String href = element.select("a").attr("href");
                if (href.contains("recipedetail.asp")) {
                    item.setRecipeNumber(href.substring(30, 36));
                    item.setPortionSize(href.substring(49));
                }
                foodItems.add(item);
            }
        }
        setListAdapter(new MenuAdapter(getContext(), R.layout.list_item_food, foodItems));
    }


    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        mainActivity = (MainActivity) activity;
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);

        Intent intent = new Intent(getContext(), FoodDetailActivity.class);
        intent.putExtra("RECIPE_NUMBER", ((FoodItem) l.getAdapter().getItem(position)).getRecipeNumber());
        intent.putExtra("PORTION_SIZE", ((FoodItem) l.getAdapter().getItem(position)).getPortionSize());
        intent.putExtra("TITLE", ((FoodItem) l.getAdapter().getItem(position)).getTitle());
        startActivity(intent);
        //TODO: Proper Transitions
        // ActivityCompat.startActivity(getActivity(), intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());

        /**
         * TODO: When a menu item is selected, a new activity must be launched, containing details
         * TODO: about that food item (identfied through a URL?).
         * TODO: TBD is how information will be passed from fragment to Activity
         * TODO: Probably through some sort of identifier and the actual activity will get details
         * TODO: such as Nutrition info, A Picture, and Ingredients
         */
        //FoodItem item = getListAdapter().getItem(position);
    }

    public String getHallCode(int hall)
    {
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
