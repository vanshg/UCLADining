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
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.vanshgandhi.ucladining.Activities.MainActivity;
import com.vanshgandhi.ucladining.Adapters.MealTypeSpinnerAdapter;
import com.vanshgandhi.ucladining.Adapters.SectionsPagerAdapter;
import com.vanshgandhi.ucladining.R;

import java.util.Calendar;

public class DiningHallMenusHolderFragment extends Fragment
{
    public static final  String[] twoMealSpinnerArray   = {"Lunch", "Dinner"};
    public static final  String[] threeMealSpinnerArray = {"Breakfast", "Lunch", "Dinner"};
    private static final int      COVEL                 = 0;
    private static final int      DENEVE                = 1;
    private static final int      FEAST                 = 2;
    private static final int      BPLATE                = 3;
    private static int currentHall;

    public enum Meal
    {
        Breakfast, Lunch, Dinner
    }

    ;
    Meal current;
    private MainActivity mainActivity;
    private Toolbar      toolbar;
    private Spinner      spinner;

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

        View rootView = inflater.inflate(R.layout.fragment_dining_hall_menus_holder, container,
                false);
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        current = recommendedMealPeriod();
        spinner = (Spinner) rootView.findViewById(R.id.spinner);
        spinner.setAdapter(new MealTypeSpinnerAdapter(toolbar.getContext(),
                getArrayBasedOnSelectedDiningHall()));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if (parent.getAdapter().getCount() == 3) {
                    switch (position) {
                        case 0:
                            current = Meal.Breakfast;
                            return;
                        case 1:
                            current = Meal.Lunch;
                            return;
                        case 2:
                            current = Meal.Dinner;
                            return;
                    }
                }
                switch (position) {
                    case 0:
                        current = Meal.Lunch;
                        return;
                    case 1:
                        current = Meal.Dinner;
                        return;
                }
                refresh();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                Toast.makeText(getContext(), "?", Toast.LENGTH_SHORT).show();
            }
        });
        mainActivity.setSupportActionBar(toolbar);
        viewPager = (ViewPager) rootView.findViewById(R.id.container);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager(), 0,
                mainActivity.getTitles());
        viewPager.setAdapter(mSectionsPagerAdapter);

//        AppBarLayout appBarLayout = (AppBarLayout) rootView.findViewById(R.id.appbar);
//        TabLayout test = new TabLayout(getContext());

        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < 4; i++) {
            View view = getLayoutInflater(savedInstanceState).inflate(R.layout.tab_layout, null);
            TextView textView = (TextView) view.findViewById(R.id.open);
            textView.setText(isHallOpen(i) ? "Open" : "Closed");
            tabLayout.getTabAt(i).setCustomView(view);
        }
        tabLayout.getTabAt(0).select();
        return rootView;
    }

    private void refresh()
    {
        //TODO: Refresh the menu!!
    }

    private String[] getArrayBasedOnSelectedDiningHall() //And Day of week
    {
        if (currentHall == COVEL || currentHall == FEAST || mainActivity.getDayOfWeek() == Calendar.SATURDAY || mainActivity
                .getDayOfWeek() == Calendar.SUNDAY) {
            return twoMealSpinnerArray;
        }
        return threeMealSpinnerArray;
    }

    public void setCurrentHall(int hall)
    {
        currentHall = hall;
    }

    private Meal recommendedMealPeriod()
    {
        //TODO: Use time to figure out what meal period it should be
        return Meal.Lunch;
    }

    private boolean isHallOpen(int hallNumber)
    {
        //TODO: Figure out how to see if each hall is open
        return true;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }
}