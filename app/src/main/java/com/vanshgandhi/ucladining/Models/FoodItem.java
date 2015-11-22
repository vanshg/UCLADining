package com.vanshgandhi.ucladining.Models;

/**
 * Created by vanshgandhi on 10/29/15.
 */
public class FoodItem
{
    private String title;
    private int recipeNumber;
    private int portionSize;
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

    public int getRecipeNumber()
    {
        return recipeNumber;
    }

    public int getPortionSize()
    {
        return portionSize;
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

    public void setRecipeNumber(int recipeNumber)
    {
        this.recipeNumber = recipeNumber;
    }

    public void setPortionSize(int portionSize)
    {
        this.portionSize = portionSize;
    }

    public void setNutrition(Nutrition nutrition)
    {
        this.nutrition = nutrition;
    }
}