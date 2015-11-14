package com.vanshgandhi.ucladining.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.vanshgandhi.ucladining.Activities.FoodDetailActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;


public class DiningHallMenuFragment extends ListFragment
{
    private static final String ARG_HALL_NUMBER = "hall_number";

    private static final int COVEL  = 0;
    private static final int DENEVE = 1;
    private static final int FEAST  = 2;
    private static final int BPLATE = 3;

    private ArrayList<String> foodItems = new ArrayList<>();

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
        int hall = getArguments().getInt(ARG_HALL_NUMBER);
        String url = "";
        if (hall == COVEL || hall == FEAST) //2 Meal
        {
            url = "https://api.import.io/store/data/f20fc91a-caf1-409c-9322-efa0ef770223/_query?input/webpage/url=http%3A%2F%2Fmenu.ha.ucla.edu%2Ffoodpro%2Fdefault.asp%3Flocation%3D07%26date%3D11%252F12%252F2015&_user=22403bda-b7eb-4c87-904a-78de1838426c&_apikey=22403bdab7eb4c87904a78de1838426c6e7d3048637d4bbae71657eb53b31c47d987e5e1cb53206a5fac41e1b938b1abcbb0ed68909ebb9d9e75447cc09546577d6725bd3f2bee95e827ee604fa7d84c";
            Ion.with(this)
                    .load(url)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>()
                    {
                        @Override
                        public void onCompleted(Exception e, JsonObject result)
                        {
                            if (e != null) {
                                return;
                            }
                            processList(result);
                        }
                    });
        }
        else if(hall == DENEVE || hall == BPLATE) //3 Meal
        {
            url = "https://api.import.io/store/data/d90c4352-d57c-4773-9064-4af17341beef/_query?input/webpage/url=http%3A%2F%2Fmenu.ha.ucla.edu%2Ffoodpro%2Fdefault.asp%3Flocation%3D01%26date%3D11%252F12%252F2015&_user=22403bda-b7eb-4c87-904a-78de1838426c&_apikey=22403bdab7eb4c87904a78de1838426c6e7d3048637d4bbae71657eb53b31c47d987e5e1cb53206a5fac41e1b938b1abcbb0ed68909ebb9d9e75447cc09546577d6725bd3f2bee95e827ee604fa7d84c";
        }

//        Ion.with(this)
//                .load(url)
//                .asJsonObject()
//                .setCallback(new FutureCallback<JsonObject>()
//                {
//                    @Override
//                    public void onCompleted(Exception e, JsonObject result)
//                    {
//                        if (e != null) {
//                            return;
//                        }
//                        processList(result);
//                    }
//                });
    }
    
    
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
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

    public void processList(JsonObject result)
    {
        JsonArray jsonArray = result.getAsJsonArray("results");
        System.out.println(jsonArray.toString());
        foodItems.add("LUNCH");
        for(JsonElement jsonElement : jsonArray)
        {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            String lunch = jsonObject.get("my_column").getAsString();
            Document doc = Jsoup.parse(lunch);
            Elements ul = doc.select("ul");
            Elements li = ul.select("li"); // select all li from ul
            for(Element element : li)
            {
                foodItems.add(element.select("a").text());
            }

        }
        foodItems.add("DINNER");
        for(JsonElement jsonElement : jsonArray)
        {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            String dinner = jsonObject.get("my_column_2").getAsString();
            Document doc = Jsoup.parse(dinner);
            Elements ul = doc.select("ul");
            Elements li = ul.select("li"); // select all li from ul
            for(Element element : li)
            {
                foodItems.add(element.select("a").text());
            }
        }
        setListAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, foodItems));
    }
}
