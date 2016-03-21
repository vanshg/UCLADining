package com.vanshgandhi.ucladining.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vanshgandhi.ucladining.Activities.MainActivity;
import com.vanshgandhi.ucladining.R;
import com.vanshgandhi.ucladining.Adapters.SectionsPagerAdapter;


public class QuickServiceMenusHolderFragment extends Fragment
{
    private MainActivity         mainActivity;
    private Toolbar              toolbar;
    private TabLayout            tabLayout;
    private ViewPager            viewPager;             //Hosts the section contents
    private SectionsPagerAdapter mSectionsPagerAdapter; //provides fragments for each section
    private static final String ARG_SECTION_NUMBER = "section_number";


    public static QuickServiceMenusHolderFragment newInstance()
    {
        QuickServiceMenusHolderFragment fragment = new QuickServiceMenusHolderFragment();
        return fragment;
    }


    public QuickServiceMenusHolderFragment()
    {
        //Mandatory empty constructor
    }

    
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        mainActivity = (MainActivity) activity;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_quick_service_menus_holder, container, false);

        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.quick_service));
        mainActivity.setSupportActionBar(toolbar);

        viewPager = (ViewPager) rootView.findViewById(R.id.container);
        mSectionsPagerAdapter =
                new SectionsPagerAdapter(getChildFragmentManager(), 1, mainActivity.getTitles());
        viewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }
    
}
