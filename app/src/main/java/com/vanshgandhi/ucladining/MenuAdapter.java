package com.vanshgandhi.ucladining;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by vanshgandhi on 10/29/15.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder>
{

    private ArrayList<FoodItem> items;

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        // each data item is just a string in this case
        public TextView mTextView;

        public ViewHolder(View v)
        {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.title);
        }
    }

    public MenuAdapter(ArrayList<FoodItem> items)
    {
        this.items = items;
    }

    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MenuAdapter.ViewHolder holder, int position)
    {
        // - get element from dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(items.get(position).getTitle());
    }

    @Override
    public int getItemCount()
    {
        return items.size();
    }
}
