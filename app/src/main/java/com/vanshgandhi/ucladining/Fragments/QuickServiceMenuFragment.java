package com.vanshgandhi.ucladining.Fragments;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;

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
    public void onAttach(Context context)
    {
        super.onAttach(context);
    }
    
    @Override
    public void onDetach()
    {
        super.onDetach();
    }


}
