package com.vanshgandhi.ucladining.Models;

/**
 * Created by vanshgandhi on 10/29/15.
 */
public class FoodItem
{
    private String title;
    private String recipeNumber;
    private String portionSize;
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

    public String getRecipeNumber()
    {
        return recipeNumber;
    }

    public String getPortionSize()
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

    public void setRecipeNumber(String recipeNumber)
    {
        this.recipeNumber = recipeNumber;
    }

    public void setPortionSize(String portionSize)
    {
        this.portionSize = portionSize;
    }

    public void setNutrition(Nutrition nutrition)
    {
        this.nutrition = nutrition;
    }
}