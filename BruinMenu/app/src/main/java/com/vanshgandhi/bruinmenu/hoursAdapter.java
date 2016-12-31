package com.vanshgandhi.bruinmenu;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.CardView;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout.LayoutParams;
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
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        height = height/5;

        CardView cardView = new CardView(mContext);
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                height
        );
        cardView.setLayoutParams(params);
        cardView.setRadius(15);
        cardView.setPadding(25, 25, 25, 25);
        cardView.setMaxCardElevation(30);


        TextView textView = new TextView(mContext);
        textView.setText(frag.getRestaurant(position));
        cardView.addView(textView);

        return cardView;
    }

}
