package com.vanshgandhi.bruinmenu;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by Sahil on 12/11/2016.
 */

public class SwipesFragment extends Fragment {
    private MainActivity mainActivity;

    private TextView dateText;

    Calendar rightNow;
    Calendar startOfQuarter;

    public SwipesFragment() {
    }

    public static SwipesFragment newInstance() {
        SwipesFragment fragment = new SwipesFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_swipes, container, false);
        return rootView;
    }

}
