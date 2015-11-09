package com.vanshgandhi.ucladining.Fragments;


import android.app.Activity;
import android.support.v4.app.Fragment;
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
        mainActivity.setupNavigationDrawer(toolbar);
    }
    
}
