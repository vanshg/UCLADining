package com.vanshgandhi.ucladining.Adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import com.vanshgandhi.ucladining.Fragments.DiningHallMenuFragment;
import com.vanshgandhi.ucladining.Fragments.QuickServiceMenuFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter
{
    int        type;
    String[][] titles; // [type of eatery][# of eatery]

    public SectionsPagerAdapter(FragmentManager fm, int type, String[][] titles)
    {
        super(fm);
        this.type = type;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position)
    {
        // getItem is called to instantiate the fragment for the given page.
        // Return a DiningHallMenusHolderFragment
        if (type == 0)
            return DiningHallMenuFragment.newInstance(position);
        else if (type == 1)
            return QuickServiceMenuFragment.newInstance(position);
        else
            return null;

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