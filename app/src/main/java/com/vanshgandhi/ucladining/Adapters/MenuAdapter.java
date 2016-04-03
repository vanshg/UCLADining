package com.vanshgandhi.ucladining.Adapters;

import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.vanshgandhi.ucladining.Helpers.JsonObjectRequestWithCache;
import com.vanshgandhi.ucladining.Models.FoodItem;
import com.vanshgandhi.ucladining.Models.Nutrition;
import com.vanshgandhi.ucladining.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        String   ingredients = "";
        static Nutrition nutrition = new Nutrition();
        TableLayout tableLayout;
        TextView ingredientsTextView;


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
                    AlertDialog dialog = new AlertDialog.Builder(v.getContext()).setView(
                            R.layout.food_detail).show();
                    tableLayout = (TableLayout) dialog.findViewById(R.id.nutrition_table);
                    ingredientsTextView = (TextView) dialog.findViewById(R.id.ingredients);
                    setInfo(dialog);
                }
            });
        }

        private void setInfo(AlertDialog dialog)
        {
            RequestQueue queue = Volley.newRequestQueue(dialog.getContext());
            String baseUrl = "https://api.import.io/store/connector/";
            String ingredientsApi = "29f05f43-f776-4eae-ab15-324ec44cfcf8/_query?input=webpage/url:";
            String uclaBaseUrl = "http%3A%2F%2Fmenu.ha.ucla.edu%2Ffoodpro%2F";
            String ingredientsPage = "recipedetail.asp%3FRecipeNumber%3D" + item.getRecipeNumber() + "%26PortionSize%3D" + item.getPortionSize();
            String apiKey = "&&_apikey=22403bdab7eb4c87904a78de1838426c6e7d3048637d4bbae71657eb53b31c47d987e5e1cb53206a5fac41e1b938b1abcbb0ed68909ebb9d9e75447cc09546577d6725bd3f2bee95e827ee604fa7d84c";
            String url = baseUrl + ingredientsApi + uclaBaseUrl + ingredientsPage + apiKey;
            Cache.Entry entry = queue.getCache().get(url);
            if (entry != null) {
                String data = new String(entry.data);
                try {
                    processResponse(new JSONObject(data));
                    ingredientsTextView.setText(ingredients);
                    populateNutritionFacts();
                } catch (JSONException e) {
                    ingredientsTextView.setText("Error");
                }
            } else {
                JsonObjectRequestWithCache request = new JsonObjectRequestWithCache(Request.Method.GET,
                        url, new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try {
                            processResponse(response);
                            ingredientsTextView.setText(ingredients);
                            populateNutritionFacts();
                        } catch (JSONException e) {
                            ingredientsTextView.setText("Error");
                        }
                    }
                }, new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        ingredientsTextView.setText("Error");
                    }
                });

                request.setShouldCache(true);
                queue.add(request);
            }
        }

        private void processResponse(JSONObject response) throws JSONException
        {
            ingredients = "";
            if (response.has("results")) {
                JSONArray array = response.getJSONArray("results");
                ingredients = array.getJSONObject(1).getString("ingredients");
                JSONArray arrayNutrition = array.getJSONObject(0).getJSONArray("nutrition_facts");
                for (int i = 0; i < arrayNutrition.length(); i++) {
                    String temp = arrayNutrition.getString(i);
                    String splitTemp[] = temp.split(" ");
                    if (temp.startsWith("Calories")) {
                        nutrition.setCalories(splitTemp[1]);
                    } else if (temp.startsWith("Total Fat")) {
                        nutrition.setTotalFatGrams(splitTemp[2]);
                        nutrition.setTotalFatPercent(splitTemp[3]);
                    } else if (temp.startsWith("Saturated Fat")) {
                        nutrition.setSaturatedFatGrams(splitTemp[2]);
                        nutrition.setSaturatedFatPercent(splitTemp[3]);
                    } else if (temp.startsWith("Trans Fat")) {
                        nutrition.setTransFatPercent(splitTemp[2]);
                    } else if (temp.startsWith("Cholesterol")) {
                        nutrition.setCholesterolGrams(splitTemp[1]);
                        nutrition.setCholesterolPercent(splitTemp[2]);
                    } else if (temp.startsWith("Sodium")) {
                        nutrition.setSodiumGrams(splitTemp[1]);
                        nutrition.setSodiumPercent(splitTemp[2]);
                    } else if (temp.startsWith("Total Carbohydrate")) {
                        nutrition.setTotalCarbohydratesGrams(splitTemp[2]);
                        nutrition.setTotalCarbohydratesPercent(splitTemp[3]);
                    } else if (temp.startsWith("Dietary Fiber")) {
                        nutrition.setFiberGrams(splitTemp[2]);
                        nutrition.setFiberPercent(splitTemp[3]);
                    } else if (temp.startsWith("Sugars")) {
                        nutrition.setSugarsGrams(splitTemp[1]);
                    } else if (temp.startsWith("Protein")) {
                        nutrition.setProteinGrams(splitTemp[1]);
                    } else if (temp.startsWith("Vitamin A")) {
                        nutrition.setVitaminAPercent(splitTemp[1]);
                    } else if (temp.startsWith("Vitamin C")) {
                        nutrition.setVitaminCPercent(splitTemp[1]);
                    } else if (temp.startsWith("Calcium")) {
                        nutrition.setCalciumPercent(splitTemp[1]);
                    } else if (temp.startsWith("Iron")) {
                        nutrition.setIronPercent(splitTemp[1]);
                    }
                }

            }
        }

        public void setItem(FoodItem foodItem)
        {
            item = foodItem;
        }

        private TextView getTextViewFromResource(@IdRes int res)
        {
            return (TextView) tableLayout.findViewById(res);
        }

        private void populateNutritionFacts()
        {
            TextView servingSize = getTextViewFromResource(R.id.serving_size);
            TextView calories = getTextViewFromResource(R.id.calories);
            TextView fatCalories = getTextViewFromResource(R.id.fat_calories);
            TextView totalFatGrams = getTextViewFromResource(R.id.total_fat);
            TextView totalFatPercent = getTextViewFromResource(R.id.total_fat_percentage);
            TextView saturatedFatGrams = getTextViewFromResource(R.id.saturated_fat);
            TextView saturatedFatPercent = getTextViewFromResource(R.id.saturated_fat_percentage);
            TextView transFatGrams = getTextViewFromResource(R.id.trans_fat);
            TextView cholesterolGrams = getTextViewFromResource(R.id.cholesterol);
            TextView cholesterolPercent = getTextViewFromResource(R.id.cholesterol_percentage);
            TextView sodiumGrams = getTextViewFromResource(R.id.sodium);
            TextView sodiumPercent = getTextViewFromResource(R.id.sodium_percentage);
            TextView carbGrams = getTextViewFromResource(R.id.total_carbohydrate);
            TextView carbPercent = getTextViewFromResource(R.id.total_carbohydrate_percentage);
            TextView fiber = getTextViewFromResource(R.id.dietary_fiber);
            TextView sugars = getTextViewFromResource(R.id.sugars);
            TextView protein = getTextViewFromResource(R.id.protein);
            TextView vitA = getTextViewFromResource(R.id.vitamin_a_percentage);
            TextView vitC = getTextViewFromResource(R.id.vitamin_c_percentage);
            TextView calcium = getTextViewFromResource(R.id.calcium_percentage);
            TextView iron = getTextViewFromResource(R.id.iron_percentage);

            servingSize.setText(nutrition.getServingSize());
            calories.setText(nutrition.getCalories());
            fatCalories.setText(nutrition.getFatCalories());
            totalFatGrams.setText(nutrition.getTotalFatGrams());
            totalFatPercent.setText(nutrition.getTotalFatPercent());
            saturatedFatGrams.setText(nutrition.getSaturatedFatGrams());
            saturatedFatPercent.setText(nutrition.getSaturatedFatPercent());
            transFatGrams.setText(nutrition.getTransFatPercent());
            cholesterolGrams.setText(nutrition.getCholesterolGrams());
            cholesterolPercent.setText(nutrition.getCalciumPercent());
            sodiumGrams.setText(nutrition.getSodiumGrams());
            sodiumPercent.setText(nutrition.getSodiumPercent());
            carbGrams.setText(nutrition.getTotalCarbohydratesGrams());
            carbPercent.setText(nutrition.getTotalCarbohydratesPercent());
            fiber.setText(nutrition.getFiberGrams());
            sugars.setText(nutrition.getSugarsGrams());
            protein.setText(nutrition.getProteinGrams());
            vitA.setText(nutrition.getVitaminAPercent());
            vitC.setText(nutrition.getVitaminCPercent());
            calcium.setText(nutrition.getCalciumPercent());
            iron.setText(nutrition.getIronPercent());
        }
    }
}
