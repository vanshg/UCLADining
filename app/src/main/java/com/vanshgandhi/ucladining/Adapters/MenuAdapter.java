package com.vanshgandhi.ucladining.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import com.vanshgandhi.ucladining.Models.FoodItem;
import com.vanshgandhi.ucladining.R;

import java.util.List;
import java.util.Map;

/**
 * Created by vanshgandhi on 10/29/15.
 */
public class MenuAdapter extends SimpleExpandableListAdapter
{
    private static final String KEY = "KEY";

    Context context;
    List<Map<String, String>> meals;
    int groupLayout;
    String[] groupFrom;
    int[] groupTo;
    List<List<Map<String, FoodItem>>> food;
    int childLayout;
    String[] childFrom;
    int[] childTo;

    private static class ViewHolder
    {
        public TextView  textView;
        public ImageView imageView;

        public ViewHolder(View v)
        {
            //textView = (TextView) v.findViewById(R.id.title);
            textView = (TextView) v.findViewById(android.R.id.text1);
            imageView = (ImageView) v.findViewById(R.id.indicator);
        }
    }

    public MenuAdapter(Context context, List<Map<String, String>> meals, int groupLayout, String[] groupFrom, int[] groupTo,
                       List<List<Map<String, FoodItem>>> food, int childLayout, String[] childFrom,
                       int[] childTo)
    {
        super(context,
                meals, groupLayout, groupFrom, groupTo,
                food, childLayout, childFrom, childTo);
        this.context = context;
        this.meals = meals;
        this.groupLayout = groupLayout;
        this.groupFrom = groupFrom;
        this.groupTo = groupTo;
        this.food = food;
        this.childLayout = childLayout;
        this.childFrom = childFrom;
        this.childTo = childTo;
    }

    public MenuAdapter(Context context)
    {
        this(context, null,
                android.R.layout.simple_expandable_list_item_1, new String[] {KEY},
                new int[] {android.R.id.text1}, null, android.R.layout.simple_expandable_list_item_2,
                new String[] {KEY}, new int[] {android.R.id.text1});

//        this.context = context;
//        meals = new ArrayList<Map<String, String>>();
//        Map<String, String> error = new HashMap<>();
//        error.put(KEY, "Error");
//        meals.add(error);
//        this.meals = new ArrayList<>();
//        this.groupLayout = android.R.layout.simple_expandable_list_item_1;
//        this.groupFrom = new String[] {KEY};
//        this.groupTo = new int[] {android.R.id.text1};
//        this.food = null;
//        this.childLayout = android.R.layout.simple_expandable_list_item_2;
//        this.childFrom = new String[] {KEY};
//        this.childTo = new int[] {android.R.id.text1};
//        super(context, meals, groupLayout, groupFrom, groupTo, food, childLayout, childFrom, childTo);
    }

    @Override
    public int getGroupCount()
    {
        return meals.size();
    }

    @Override
    public int getChildrenCount(int groupPosition)
    {
        return food.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition)
    {
        return meals.get(groupPosition).get(KEY);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
        return food.get(groupPosition).get(childPosition).get(KEY).getTitle();
    }

    @Override
    public long getGroupId(int groupPosition)
    {
        return groupLayout;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return 0;
    }


    @Override
    public boolean hasStableIds()
    {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        ViewHolder holder;

        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(groupLayout, null);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        }
        else {
            // view already exists, get the holder instance from the view
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textView.setText(meals.get(groupPosition).get(KEY));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        ViewHolder holder;

        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(childLayout, null);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        }
        else {
            // view already exists, get the holder instance from the view
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textView.setText(food.get(groupPosition).get(childPosition).get(KEY).getTitle());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return true;
    }


//    @Override
//    public View getView(int position, View convertView, ViewGroup parent)
//    {
//
//        ViewHolder holder;
//
//        if (convertView == null) {
//
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_food, null);
//            holder = new ViewHolder(convertView);
//
//            convertView.setTag(holder);
//        }
//        else {
//            // view already exists, get the holder instance from the view
//            holder = (ViewHolder) convertView.getTag();
//        }
//
//        holder.textView.setText(foodItems.get(position).getTitle());
//
//        return convertView;
//    }

}
