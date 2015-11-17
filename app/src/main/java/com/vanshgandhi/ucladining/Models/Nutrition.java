package com.vanshgandhi.ucladining.Models;

/**
 * Created by vanshgandhi on 11/17/15.
 */
public class Nutrition
{
    private int servingSize;
    private int amountPerServing;
    private int calories;
    private int fatCalories;
    private int totalFat;
    private int saturatedFat;
    private int transFat;
    private int cholesterol;
    private int sodium;
    private int totalCarbohydrates;
    private int fiber;
    private int sugars;
    private int protein;

    public Nutrition()
    {

    }

    public int getServingSize()
    {
        return servingSize;
    }

    public void setServingSize(int servingSize)
    {
        this.servingSize = servingSize;
    }

    public int getAmountPerServing()
    {
        return amountPerServing;
    }

    public void setAmountPerServing(int amountPerServing)
    {
        this.amountPerServing = amountPerServing;
    }

    public int getCalories()
    {
        return calories;
    }

    public void setCalories(int calories)
    {
        this.calories = calories;
    }

    public int getFatCalories()
    {
        return fatCalories;
    }

    public void setFatCalories(int fatCalories)
    {
        this.fatCalories = fatCalories;
    }

    public int getTotalFat()
    {
        return totalFat;
    }

    public void setTotalFat(int totalFat)
    {
        this.totalFat = totalFat;
    }

    public int getSaturatedFat()
    {
        return saturatedFat;
    }

    public void setSaturatedFat(int saturatedFat)
    {
        this.saturatedFat = saturatedFat;
    }

    public int getTransFat()
    {
        return transFat;
    }

    public void setTransFat(int transFat)
    {
        this.transFat = transFat;
    }

    public int getCholesterol()
    {
        return cholesterol;
    }

    public void setCholesterol(int cholesterol)
    {
        this.cholesterol = cholesterol;
    }

    public int getSodium()
    {
        return sodium;
    }

    public void setSodium(int sodium)
    {
        this.sodium = sodium;
    }

    public int getTotalCarbohydrates()
    {
        return totalCarbohydrates;
    }

    public void setTotalCarbohydrates(int totalCarbohydrates)
    {
        this.totalCarbohydrates = totalCarbohydrates;
    }

    public int getFiber()
    {
        return fiber;
    }

    public void setFiber(int fiber)
    {
        this.fiber = fiber;
    }

    public int getSugars()
    {
        return sugars;
    }

    public void setSugars(int sugars)
    {
        this.sugars = sugars;
    }

    public int getProtein()
    {
        return protein;
    }

    public void setProtein(int protein)
    {
        this.protein = protein;
    }

    //TODO: Add vitamins dynamically somehow
}
