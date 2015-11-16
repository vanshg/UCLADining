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
import com.vanshgandhi.ucladining.Adapters.SectionsPagerAdapter;
import com.vanshgandhi.ucladining.R;

public class DiningHallMenusHolderFragment extends Fragment
{

    private MainActivity mainActivity;
    private Toolbar      toolbar;

    public static DiningHallMenusHolderFragment newInstance()
    {
        return new DiningHallMenusHolderFragment();
    }

    public DiningHallMenusHolderFragment()
    {
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
        TabLayout tabLayout;
        ViewPager viewPager;             //Hosts the section contents
        SectionsPagerAdapter mSectionsPagerAdapter; //provides fragments for each section

        View rootView = inflater.inflate(R.layout.fragment_dining_hall_menus_holder, container, false);

        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.dining_hall));
        mainActivity.setSupportActionBar(toolbar);

        viewPager = (ViewPager) rootView.findViewById(R.id.container);
        mSectionsPagerAdapter =
                new SectionsPagerAdapter(getChildFragmentManager(), 0, mainActivity.getTitles());
        viewPager.setAdapter(mSectionsPagerAdapter);

//        AppBarLayout appBarLayout = (AppBarLayout) rootView.findViewById(R.id.appbar);
//        TabLayout test = new TabLayout(getContext());


        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mainActivity.setupNavigationDrawer(toolbar);
    }
}