package com.vanshgandhi.ucladining.Models;

import java.io.Serializable;

/**
 * Created by vanshgandhi on 11/17/15.
 */
public class Nutrition implements Serializable
{
    private String servingSize;
    private String amountPerServing;
    private String calories;
    private String fatCalories;
    private String totalFatPercent;
    private String totalFatGrams;
    private String saturatedFatPercent;
    private String saturatedFatGrams;
    private String transFatPercent;
    private String cholesterolPercent;
    private String cholesterolGrams;
    private String sodiumPercent;
    private String sodiumGrams;
    private String totalCarbohydratesPercent;
    private String totalCarbohydratesGrams;
    private String fiberPercent;
    private String fiberGrams;
    private String sugarsGrams;
    private String proteinGrams;
    private String vitaminAPercent;
    private String vitaminCPercent;
    private String calciumPercent;
    private String ironPercent;

    public Nutrition()
    {
        vitaminAPercent = "0%";
        vitaminCPercent = "0%";
        calciumPercent = "0%";
        ironPercent = "0%";
    }

    public String getServingSize()
    {
        return servingSize;
    }

    public void setServingSize(String servingSize)
    {
        this.servingSize = servingSize;
    }

    public String getAmountPerServing()
    {
        return amountPerServing;
    }

    public void setAmountPerServing(String amountPerServing)
    {
        this.amountPerServing = amountPerServing;
    }

    public String getCalories()
    {
        return calories;
    }

    public void setCalories(String calories)
    {
        this.calories = calories;
    }

    public String getFatCalories()
    {
        return fatCalories;
    }

    public void setFatCalories(String fatCalories)
    {
        this.fatCalories = fatCalories;
    }

    public String getTotalFatPercent()
    {
        return totalFatPercent;
    }

    public void setTotalFatPercent(String totalFatPercent)
    {
        this.totalFatPercent = totalFatPercent;
    }

    public String getTotalFatGrams()
    {
        return totalFatGrams;
    }

    public void setTotalFatGrams(String totalFatGrams)
    {
        this.totalFatGrams = totalFatGrams;
    }

    public String getSaturatedFatPercent()
    {
        return saturatedFatPercent;
    }

    public void setSaturatedFatPercent(String saturatedFatPercent)
    {
        this.saturatedFatPercent = saturatedFatPercent;
    }

    public String getSaturatedFatGrams()
    {
        return saturatedFatGrams;
    }

    public void setSaturatedFatGrams(String saturatedFatGrams)
    {
        this.saturatedFatGrams = saturatedFatGrams;
    }

    public String getTransFatPercent()
    {
        return transFatPercent;
    }

    public void setTransFatPercent(String transFatPercent)
    {
        this.transFatPercent = transFatPercent;
    }

    public String getCholesterolPercent()
    {
        return cholesterolPercent;
    }

    public void setCholesterolPercent(String cholesterolPercent)
    {
        this.cholesterolPercent = cholesterolPercent;
    }

    public String getCholesterolGrams()
    {
        return cholesterolGrams;
    }

    public void setCholesterolGrams(String cholesterolGrams)
    {
        this.cholesterolGrams = cholesterolGrams;
    }

    public String getSodiumPercent()
    {
        return sodiumPercent;
    }

    public void setSodiumPercent(String sodiumPercent)
    {
        this.sodiumPercent = sodiumPercent;
    }

    public String getSodiumGrams()
    {
        return sodiumGrams;
    }

    public void setSodiumGrams(String sodiumGrams)
    {
        this.sodiumGrams = sodiumGrams;
    }

    public String getTotalCarbohydratesPercent()
    {
        return totalCarbohydratesPercent;
    }

    public void setTotalCarbohydratesPercent(String totalCarbohydratesPercent)
    {
        this.totalCarbohydratesPercent = totalCarbohydratesPercent;
    }

    public String getTotalCarbohydratesGrams()
    {
        return totalCarbohydratesGrams;
    }

    public void setTotalCarbohydratesGrams(String totalCarbohydratesGrams)
    {
        this.totalCarbohydratesGrams = totalCarbohydratesGrams;
    }

    public String getFiberPercent()
    {
        return fiberPercent;
    }

    public void setFiberPercent(String fiberPercent)
    {
        this.fiberPercent = fiberPercent;
    }

    public String getFiberGrams()
    {
        return fiberGrams;
    }

    public void setFiberGrams(String fiberGrams)
    {
        this.fiberGrams = fiberGrams;
    }

    public String getSugarsGrams()
    {
        return sugarsGrams;
    }

    public void setSugarsGrams(String sugarsGrams)
    {
        this.sugarsGrams = sugarsGrams;
    }

    public String getProteinGrams()
    {
        return proteinGrams;
    }

    public void setProteinGrams(String proteinGrams)
    {
        this.proteinGrams = proteinGrams;
    }

    public String getVitaminAPercent()
    {
        return vitaminAPercent;
    }

    public void setVitaminAPercent(String vitaminAPercent)
    {
        this.vitaminAPercent = vitaminAPercent;
    }

    public String getVitaminCPercent()
    {
        return vitaminCPercent;
    }

    public void setVitaminCPercent(String vitaminCPercent)
    {
        this.vitaminCPercent = vitaminCPercent;
    }

    public String getCalciumPercent()
    {
        return calciumPercent;
    }

    public void setCalciumPercent(String calciumPercent)
    {
        this.calciumPercent = calciumPercent;
    }

    public String getIronPercent()
    {
        return ironPercent;
    }

    public void setIronPercent(String ironPercent)
    {
        this.ironPercent = ironPercent;
    }
}
