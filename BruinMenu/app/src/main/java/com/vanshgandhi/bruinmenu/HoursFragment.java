package com.vanshgandhi.bruinmenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by Sahil on 12/12/2016.
 */

public class HoursFragment extends Fragment {

    public HoursFragment(){}

    public static HoursFragment newInstance(){
        HoursFragment hf = new HoursFragment();
        return hf;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootview = inflater.inflate(R.layout.fragment_hours, container, false);
        return rootview;
    }
}
