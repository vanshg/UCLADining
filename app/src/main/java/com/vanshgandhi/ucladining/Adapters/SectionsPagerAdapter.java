package com.vanshgandhi.ucladining.Adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import com.vanshgandhi.ucladining.Activities.MainActivity.DiningHall;
import com.vanshgandhi.ucladining.Fragments.DiningHallMenuFragment;

import java.util.ArrayList;

public class SectionsPagerAdapter extends FragmentPagerAdapter
{
    int        type;
    String[][] titles; // [type of eatery][# of eatery]
    ArrayList<Fragment> fragments;

    public SectionsPagerAdapter(FragmentManager fm, int type, String[][] titles)
    {
        super(fm);
        this.type = type;
        this.titles = titles;
        fragments = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position)
    {
        // getItem is called to instantiate the fragment for the given page.
        // Return a DiningHallMenusHolderFragment
        //TODO:CHANGE THIS TO RETURN MEAL NOT POSITION BASED ON TIME OF DAY
        Fragment f = DiningHallMenuFragment.newInstance(DiningHall.values()[position]);
        fragments.add(f);
        return f;
//        if (type == 0)
//            return DiningHallMenuFragment.newInstance(position);
//        else if (type == 1)
//            return QuickServiceMenuFragment.newInstance(position);
//        else
//            return null;

    }

    public Fragment getFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount()
    {
        return titles[type].length;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        if (position < titles[type].length) {
            return titles[type][position];
        }
        return null;
    }
}