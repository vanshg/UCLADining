package com.vanshgandhi.ucladining.Adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vanshgandhi.ucladining.Activities.FoodDetailActivity;
import com.vanshgandhi.ucladining.Models.FoodItem;
import com.vanshgandhi.ucladining.R;

import java.util.List;

/**
 * Created by vanshgandhi on 10/29/15.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder>
{

    List<FoodItem> food;

    public MenuAdapter(List<FoodItem> food)
    {
        this.food = food;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_item_food, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        FoodItem item = food.get(position);
        holder.setItem(item);
        holder.textView.setText(item.getTitle());
        if (item.isVegetarian()) {
            holder.imageView.setImageResource(R.drawable.vegetarian);
        } else if (item.isVegan()) {
            holder.imageView.setImageResource(R.drawable.vegan);
        }

    }

    @Override
    public int getItemCount()
    {
        return food.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView  textView;
        public ImageView imageView;
        FoodItem item;
        public ViewHolder(View v)
        {
            super(v);
            textView = (TextView) v.findViewById(R.id.title);
            imageView = (ImageView) v.findViewById(R.id.indicator);
            v.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(v.getContext(), FoodDetailActivity.class);
                    intent.putExtra("RECIPE_NUMBER", item.getRecipeNumber());
                    intent.putExtra("PORTION_SIZE", item.getPortionSize());
                    intent.putExtra("TITLE", item.getTitle());
                    v.getContext().startActivity(intent);
                }
            });
        }
        public void setItem(FoodItem foodItem) {
            item = foodItem;
        }
    }
}
