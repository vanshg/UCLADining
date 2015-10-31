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

    public SectionsPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        // getItem is called to instantiate the fragment for the given page.
        // Return a DiningHallMenuFragment (defined as a static inner class below).
        return DiningHallMenuFragment.newInstance(position + 1);
    }

    @Override
    public int getCount()
    {
        // Show 4 total pages.
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        switch (position) {
            case 0:
                return "COVEL";
            case 1:
                return "DE NEVE";
            case 2:
                return "FEAST";
            case 3:
                return "BPLATE";
        }
        return null;
    }
}