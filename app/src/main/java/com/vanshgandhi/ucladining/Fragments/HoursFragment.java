package com.vanshgandhi.ucladining.Fragments;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vanshgandhi.ucladining.Activities.MainActivity;
import com.vanshgandhi.ucladining.R;

public class HoursFragment extends Fragment
{
    private MainActivity mainActivity;
    private Toolbar      toolbar;

    private static final String[][] regularHours = {
            {"7-11", "11-5", "5-9", "9-2"}, //Bcafe
            {"7-9", "11-2", "5-8", null},   //Bplate
            {null, "11-4", "5-9", "9-12"},  //1919
            {null, "11-2", "5-9", null},    //Covel
            {"7-10", "11-2", "5-8", "9-12"},//DeNeve
            {"7-11", "11-3", null, null},   //Deneve on the go
            {null, "11-2", "5-8", null},    //Feat
            {"7-11", "12-4", "5-9", "9-12"} //Rendez
    };


    public static HoursFragment newInstance()
    {
        HoursFragment fragment = new HoursFragment();
        return fragment;
    }
    
    public HoursFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        mainActivity = (MainActivity) activity;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_hours, container, false);

        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.hours));
        mainActivity.setSupportActionBar(toolbar);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }
    
}
