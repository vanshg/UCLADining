package com.vanshgandhi.ucladining.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.vanshgandhi.ucladining.Fragments.DiningHallMenuFragment;
import com.vanshgandhi.ucladining.Fragments.QuickServiceMenuFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter
{
    int      type;
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
            return DiningHallMenuFragment.newInstance();
        else if (type == 1)
            return QuickServiceMenuFragment.newInstance();
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