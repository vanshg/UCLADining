package com.vanshgandhi.ucladining.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.vanshgandhi.ucladining.Activities.MainActivity;
import com.vanshgandhi.ucladining.Activities.MainActivity.Meal;
import com.vanshgandhi.ucladining.Adapters.MealTypeSpinnerAdapter;
import com.vanshgandhi.ucladining.Adapters.SectionsPagerAdapter;
import com.vanshgandhi.ucladining.Helpers.Refreshable;
import com.vanshgandhi.ucladining.R;

import java.util.Calendar;

public class DiningHallMenusHolderFragment extends Fragment {
    public static final  String[] twoMealSpinnerArray   = {"Lunch", "Dinner"};
    public static final  String[] threeMealSpinnerArray = {"Breakfast", "Lunch", "Dinner"};
    private static final int      COVEL                 = 0;
    private static final int      DENEVE                = 1;
    private static final int      FEAST                 = 2;
    private static final int      BPLATE                = 3;
    private static int currentHall;

    private MainActivity         mainActivity;
    private Toolbar              toolbar;
    private Spinner              spinner;
    private SectionsPagerAdapter sectionsPagerAdapter; //provides fragments for each section

    public static DiningHallMenusHolderFragment newInstance() {
        return new DiningHallMenusHolderFragment();
    }

    public DiningHallMenusHolderFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TabLayout tabLayout;
        ViewPager viewPager;             //Hosts the section contents

        View rootView = inflater.inflate(R.layout.fragment_dining_hall_menus_holder, container,
                false);
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        mainActivity.setCurrentMeal(recommendedMealPeriod());
        spinner = (Spinner) rootView.findViewById(R.id.spinner);
        spinner.setAdapter(new MealTypeSpinnerAdapter(toolbar.getContext(),
                getArrayBasedOnSelectedDiningHall()));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getAdapter().getCount() == 3) {
                    switch (position) {
                        case 0:
                            mainActivity.setCurrentMeal(Meal.Breakfast);
                        case 1:
                            mainActivity.setCurrentMeal(Meal.Lunch);
                        case 2:
                            mainActivity.setCurrentMeal(Meal.Dinner);
                    }
                } else {
                    switch (position) {
                        case 0:
                            mainActivity.setCurrentMeal(Meal.Lunch);
                        case 1:
                            mainActivity.setCurrentMeal(Meal.Dinner);
                    }
                }
                refresh();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity(), "?", Toast.LENGTH_SHORT).show();
            }
        });
        mainActivity.setSupportActionBar(toolbar);
        viewPager = (ViewPager) rootView.findViewById(R.id.container);
        viewPager.setOffscreenPageLimit(3); //
        sectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager(), 0,
                mainActivity.getTitles());
        viewPager.setAdapter(sectionsPagerAdapter);

//        AppBarLayout appBarLayout = (AppBarLayout) rootView.findViewById(R.id.appbar);
//        TabLayout test = new TabLayout(getContext());

        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < 4; i++) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.tab_layout, null);
            TextView textView = (TextView) view.findViewById(R.id.open);
            textView.setText(isHallOpen(i) ? "Open" : "Closed");
            tabLayout.getTabAt(i).setCustomView(view);
        }
        tabLayout.getTabAt(0).select();
        return rootView;
    }

    public void refresh() { //MainActivity calls this to refesh all Dining Halls
        for (int i = 0; i < sectionsPagerAdapter.getCount(); i++) {
            Refreshable refreshable = (Refreshable) sectionsPagerAdapter.getFragment(i);
            refreshable.refresh();
        }
    }

    private String[] getArrayBasedOnSelectedDiningHall() //And Day of week
    {
        if (currentHall == COVEL || currentHall == FEAST || mainActivity.getDayOfWeek() == Calendar.SATURDAY || mainActivity
                .getDayOfWeek() == Calendar.SUNDAY) {
            return twoMealSpinnerArray;
        }
        return threeMealSpinnerArray;
    }

    public SectionsPagerAdapter getSectionsPagerAdapter() {
        return sectionsPagerAdapter;
    }

    public void setCurrentHall(int hall) {
        currentHall = hall;
    }

    private Meal recommendedMealPeriod() {
        //TODO: Use time to figure out what meal period it should be
        return Meal.Lunch;
    }

    private boolean isHallOpen(int hallNumber) {
        //TODO: Figure out how to see if each hall is open
        return true;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}