package com.vanshgandhi.bruinmenu;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Vansh Gandhi on 11/13/16.
 * Copyright © 2016
 */

public class MenuSectionsPagerAdapter extends FragmentPagerAdapter {

    private boolean displayCafe = true;

    public MenuSectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return MenuFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        if (displayCafe) {
            switch (position) {
                case 0:
                    return "CAFE 1919";
                case 1:
                    return "RENDEZVOUS EAST";
                case 2:
                    return "RENDEZVOUS WEST";
                case 3:
                    return "BRUIN CAFE";
            }
        } else {
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
        }
        return "";
    }

    void setDisplayCafe(boolean b) {
        displayCafe = b;
    }
}