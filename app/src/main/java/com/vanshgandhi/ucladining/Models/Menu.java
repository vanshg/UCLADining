package com.vanshgandhi.ucladining.Models;

import java.util.ArrayList;

/**
 * Created by vanshgandhi on 11/8/15.
 */
public class Menu
{
    private String  name;
    private boolean quickEat;
    private ArrayList<FoodItem> allFood = new ArrayList<>();

    public Menu(String name, boolean quickEat)
    {
        this.name = name;
        this.quickEat = quickEat;
    }

    public String getName()
    {
        return name;
    }

    public boolean isQuickEat()
    {
        return quickEat;
    }

    public ArrayList<FoodItem> getAllFood()
    {
        return allFood;
    }
}
