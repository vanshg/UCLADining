package com.vanshgandhi.ucladining;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter
{
    int type;
    String[] titles;

    public SectionsPagerAdapter(FragmentManager fm, int type, String[] titles)
    {
        super(fm);
        this.type = type;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position)
    {
        // getItem is called to instantiate the fragment for the given page.
        // Return a DiningHallMenuFragment
        return DiningHallMenuFragment.newInstance();
    }

    @Override
    public int getCount()
    {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        if(position < titles.length)
            return titles[position];
        return null;
    }
}