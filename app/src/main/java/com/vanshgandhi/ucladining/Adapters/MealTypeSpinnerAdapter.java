package com.vanshgandhi.ucladining.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vanshgandhi.ucladining.R;

/**
 * Created by vanshgandhi on 3/23/16.
 */
public class MealTypeSpinnerAdapter extends ArrayAdapter<String> implements ThemedSpinnerAdapter
{
    private final ThemedSpinnerAdapter.Helper mDropDownHelper;

    public MealTypeSpinnerAdapter(Context context, String[] objects)
    {
//        super(context, android.R.layout.simple_selectable_list_item, objects);
        super(context, R.layout.spinner_text, objects);
        mDropDownHelper = new ThemedSpinnerAdapter.Helper(context);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        View view;

        if (convertView == null) {
            // Inflate the drop down using the helper's LayoutInflater
            LayoutInflater inflater = mDropDownHelper.getDropDownViewInflater();
            view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        } else {
            view = convertView;
        }

        TextView textView = (TextView) view.findViewById(android.R.id.text1);
        textView.setText(getItem(position));
        return view;
    }

    @Override
    public void setDropDownViewTheme(Resources.Theme theme)
    {
        mDropDownHelper.setDropDownViewTheme(theme);
    }

    @Override
    public Resources.Theme getDropDownViewTheme()
    {
        return mDropDownHelper.getDropDownViewTheme();
    }
}
