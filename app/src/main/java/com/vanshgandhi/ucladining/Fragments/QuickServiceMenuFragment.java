package com.vanshgandhi.ucladining.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

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
}
