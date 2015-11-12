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

import java.util.ArrayList;


public class QuickServiceMenuFragment extends ListFragment
{
    private static final String ARG_QS_NUMBER = "qs_number";

    private static final int RENDEZVOUS = 0;
    private static final int CAFE1919 = 1;
    private static final int BCAFE = 2;

    private ArrayList<String> breakfastFoodItems = new ArrayList<>();

    public static QuickServiceMenuFragment newInstance(int hallNumber)
    {
        QuickServiceMenuFragment fragment = new QuickServiceMenuFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_QS_NUMBER, hallNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public QuickServiceMenuFragment()
    {
        //Mandatory empty constructor
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Ion.with(this)
                .load("https://api.import.io/store/data/b69025cd-7b2e-4f66-9bad-e82675963a67/_query?input/webpage/url=http%3A%2F%2Fmenu.ha.ucla.edu%2Ffoodpro%2Fdefault.asp%3Fdate%3D11%252F2%252F2015&_user=22403bda-b7eb-4c87-904a-78de1838426c&_apikey=22403bdab7eb4c87904a78de1838426c6e7d3048637d4bbae71657eb53b31c47d987e5e1cb53206a5fac41e1b938b1abcbb0ed68909ebb9d9e75447cc09546577d6725bd3f2bee95e827ee604fa7d84c")
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
        for (JsonElement jsonElement : jsonArray) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            if (jsonObject.has("food_items/_text")) {
                if (jsonObject.get("food_items/_text").isJsonArray()) {
                    JsonArray food = jsonObject.get("food_items/_text").getAsJsonArray();
                    for (JsonElement item : food) {
                        String title = item.getAsString();
                        breakfastFoodItems.add(title);
                    }
                }
                else {
                    try {
                        String title = jsonObject.get("food_items/_text").getAsString();
                        breakfastFoodItems.add(title);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        setListAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, breakfastFoodItems));
    }
}
