package com.vanshgandhi.ucladining.Models;

import java.util.ArrayList;

/**
 * Created by vanshgandhi on 11/8/15.
 */
public class Menu
{
    private static final int COVEL  = 0;
    private static final int DENEVE = 1;
    private static final int FEAST  = 2;
    private static final int BPLATE = 3;

    private boolean quickEat;
    private int quickEatNumber = -1;
    private int hallNumber = -1;
    private ArrayList<FoodItem> allFood = new ArrayList<>();

    public Menu(int hallNumber)
    {
        this.quickEat = false;
        this.hallNumber = hallNumber;
    }

    public Menu(boolean quickEat, int quickEatNumber)
    {
        this.quickEat = true; //If a boolean is passed in, I want it to automatically mean quickEat
        this.quickEatNumber = quickEatNumber;
    }

    public String getName()
    {
        if(!quickEat) {
            switch (hallNumber) {
                case COVEL:
                    return "Covel";
                case DENEVE:
                    return "DeNeve";
                case BPLATE:
                    return "BPlate";
                case FEAST:
                    return "Feast";
            }
        }
        //TODO: Return name of QuickEat Places
        return "";
    }

    public boolean isQuickEat()
    {
        return quickEat;
    }

    public ArrayList<FoodItem> getAllFood()
    {
        return allFood;
    }

    public void add(FoodItem item)
    {
        allFood.add(item);
    }
}
