package com.vanshgandhi.bruinmenu;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Sahil on 12/29/2016.
 */

public class hoursAdapter extends BaseAdapter {
    private Context mContext;
    private HoursFragment frag;

    public hoursAdapter(Context c, HoursFragment fragment) {
        mContext = c;
        frag = fragment;
    }

    public int getCount() {
        return 8;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new textView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            textView = new TextView(mContext);
            textView.setText(frag.getRestaurant(position));
        } else {
            textView = (TextView) convertView;
        }
        return textView;
    }

}
