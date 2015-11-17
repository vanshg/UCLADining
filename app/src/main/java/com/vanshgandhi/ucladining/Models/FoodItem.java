package com.vanshgandhi.ucladining.Models;

/**
 * Created by vanshgandhi on 10/29/15.
 */
public class FoodItem
{
    private String title;
    private boolean isVegetarian;
    private boolean isVegan;
    private Nutrition nutrition;

    public FoodItem(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }

    public boolean isVegetarian()
    {
        return isVegetarian;
    }

    public boolean isVegan()
    {
        return isVegan;
    }

    public Nutrition getNutrition()
    {
        return nutrition;
    }

    public void setNutrition(Nutrition nutrition)
    {
        this.nutrition = nutrition;
    }
}
