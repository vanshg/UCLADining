package com.vanshgandhi.ucladining.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vanshgandhi.ucladining.Models.FoodItem;
import com.vanshgandhi.ucladining.R;

import java.util.ArrayList;

/**
 * Created by vanshgandhi on 10/29/15.
 */
public class MenuAdapter extends ArrayAdapter<FoodItem>
{
    //private Menu menu;
    ArrayList<FoodItem> foodItems;

    private static class ViewHolder
    {
        public TextView  textView;
        public ImageView imageView;

        public ViewHolder(View v)
        {
            textView = (TextView) v.findViewById(R.id.title);
            imageView = (ImageView) v.findViewById(R.id.indicator);
        }
    }

    public MenuAdapter(Context context, int resource, ArrayList<FoodItem> objects)
    {
        super(context, resource, objects);
        foodItems = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        ViewHolder holder;

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_food, null);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        }
        else {
            // view already exists, get the holder instance from the view
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textView.setText(foodItems.get(position).getTitle());

        return convertView;
    }

}
