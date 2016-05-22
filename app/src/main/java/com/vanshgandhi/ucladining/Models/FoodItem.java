package com.vanshgandhi.ucladining.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vanshgandhi on 10/29/15.
 */
public class FoodItem implements Parcelable
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(recipeNumber);
        dest.writeString(portionSize);
        dest.writeValue(isVegetarian);
        dest.writeValue(isVegan);
        dest.writeSerializable(nutrition);
    }

    public static final Parcelable.Creator<FoodItem> CREATOR
            = new Parcelable.Creator<FoodItem>() {
        public FoodItem createFromParcel(Parcel in) {
            return new FoodItem(in);
        }

        public FoodItem[] newArray(int size) {
            return new FoodItem[size];
        }
    };

    private FoodItem(Parcel in) {
        title = in.readString();
        recipeNumber = in.readString();
        portionSize = in.readString();
        isVegetarian = (Boolean) in.readValue(null);
        isVegan = (Boolean) in.readValue(null);
        nutrition = (Nutrition) in.readSerializable();
    }
}