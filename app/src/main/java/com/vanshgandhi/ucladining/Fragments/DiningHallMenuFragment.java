package com.vanshgandhi.ucladining.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vanshgandhi.ucladining.API;
import com.vanshgandhi.ucladining.Activities.MainActivity;
import com.vanshgandhi.ucladining.Activities.MainActivity.DiningHall;
import com.vanshgandhi.ucladining.Activities.MainActivity.Meal;
import com.vanshgandhi.ucladining.Adapters.MenuAdapter;
import com.vanshgandhi.ucladining.Helpers.GridSpacingItemDecoration;
import com.vanshgandhi.ucladining.Helpers.Refreshable;
import com.vanshgandhi.ucladining.Models.FoodItem;
import com.vanshgandhi.ucladining.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;

public class DiningHallMenuFragment extends Fragment implements Refreshable {

    private MainActivity mainActivity;

    private static final String ARG_MEAL = "meal_type";
    private static final String HALL     = "hall";

    private static final int COVEL  = 0;
    private static final int DENEVE = 1;
    private static final int FEAST  = 2;
    private static final int BPLATE = 3;

    private RecyclerView   recyclerView;
    private ArrayList<FoodItem> food;

    public static DiningHallMenuFragment newInstance(
            DiningHall hall) {
        DiningHallMenuFragment fragment = new DiningHallMenuFragment();
        Bundle args = new Bundle();
        args.putInt(HALL, hall.ordinal());
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
        recyclerView = (RecyclerView) v.findViewById(R.id.list);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 24, true));

        if (savedInstanceState != null) {
            Log.v("Recovering", "Positive");
            food = savedInstanceState.getParcelableArrayList(HALL);
            recyclerView.setAdapter(new MenuAdapter(food));
            return v;
        }
        food = new ArrayList<>();
        refresh();
        return v;
    }

    private void processList(JSONObject result, boolean threeMeal) throws JSONException {
        JSONArray jsonArray;
        Log.v("Result String", result.toString());
        if (result.has("results")) {
            jsonArray = result.getJSONArray("results");
        } else {
            showError();
            return;
        }
        if (food != null) {
            food.clear();
        }
        String breakfast;
        String lunch;
        String dinner;
        Document doc;
        Elements ul;
        Elements li;
        Meal current = mainActivity.getCurrentMeal();
        if (threeMeal && current == Meal.Breakfast) {
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

        for (int i = 0; i < jsonArray.length() && current == Meal.Lunch; i++) {
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

        for (int i = 0; i < jsonArray.length() && current == Meal.Dinner; i++) {
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
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (recyclerView.getAdapter() == null) {
                    recyclerView.setAdapter(new MenuAdapter(food));
                } else {
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(HALL, food);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            DiningHallMenusHolderFragment f = (DiningHallMenusHolderFragment) getParentFragment();
            f.setCurrentHall(getArguments().getInt(ARG_MEAL));
        }
    }

    private String getHallCode(int hall) {
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

    private void showError() {
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mainActivity, "Error in loading Menu", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void refresh() {
        Log.v("DiningHallMenuFragment", "In Refresh");
        if (mainActivity == null || recyclerView == null) {
            showError();
            return;
        }
        if (food == null) {
            food = new ArrayList<>();
        }

        food.clear();
        int hall = getArguments().getInt(HALL);
        int dayOfWeek = mainActivity.getDayOfWeek();
        if (hall == COVEL || hall == FEAST || dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) //2 Meal
        {
            Callback callbackTwoMeal = new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    showError();
                    e.printStackTrace();
                }
                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        showError();
                        return;
                    }
                    final String responseData = response.body().string();
                    try {
                        JSONObject json = new JSONObject(responseData);
                        processList(json, false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        showError();
                    }

                }
            };
            try {
                API.getInstance()
                        .loadTwoMealHall(callbackTwoMeal, getHallCode(hall), mainActivity.getMonth() + 1,
                                mainActivity.getDay(), mainActivity.getYear());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {    //if(hall == DENEVE || hall == BPLATE) //3 Meal
            Callback callbackThreeMeal = new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    showError();
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        showError();
                        return;
                    }
                    final String responseData = response.body().string();
                    try {
                        JSONObject json = new JSONObject(responseData);
                        processList(json, true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        showError();
                    }

                }
            };
            try {
                API.getInstance()
                        .loadThreeMealHall(callbackThreeMeal, getHallCode(hall), mainActivity.getMonth() + 1,
                                mainActivity.getDay(), mainActivity.getYear());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }
}
