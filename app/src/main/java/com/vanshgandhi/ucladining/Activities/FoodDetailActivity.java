package com.vanshgandhi.ucladining.Activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.widget.TableLayout;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.vanshgandhi.ucladining.Helpers.JsonObjectRequestWithCache;
import com.vanshgandhi.ucladining.Models.Nutrition;
import com.vanshgandhi.ucladining.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FoodDetailActivity extends AppCompatActivity
{
    String    ingredients;
    static Nutrition nutrition = new Nutrition();
    TableLayout tableLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            //TODO: Proper Transitions
//            getWindow().setEnterTransition(new Slide());
//            getWindow().setExitTransition(new Slide());
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        final TextView ingredientsTextView = (TextView) findViewById(R.id.ingredients);
        tableLayout = (TableLayout) findViewById(R.id.nutrition_table);
        RequestQueue queue = Volley.newRequestQueue(this);
        Bundle args = getIntent().getExtras();
        String title = null;
        String recipeNumber = null;
        String portionSize = null;
        if (args != null) {
            title = args.getString("TITLE");
            recipeNumber = args.getString("RECIPE_NUMBER");
            portionSize = args.getString("PORTION_SIZE");
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        String baseUrl = "https://api.import.io/store/connector/";
        String ingredientsApi = "29f05f43-f776-4eae-ab15-324ec44cfcf8/_query?input=webpage/url:";
        String uclaBaseUrl = "http%3A%2F%2Fmenu.ha.ucla.edu%2Ffoodpro%2F";
        String ingredientsPage = "recipedetail.asp%3FRecipeNumber%3D" + recipeNumber + "%26PortionSize%3D" + portionSize;
        String apiKey = "&&_apikey=22403bdab7eb4c87904a78de1838426c6e7d3048637d4bbae71657eb53b31c47d987e5e1cb53206a5fac41e1b938b1abcbb0ed68909ebb9d9e75447cc09546577d6725bd3f2bee95e827ee604fa7d84c";
        String url = baseUrl + ingredientsApi + uclaBaseUrl + ingredientsPage + apiKey;
        Cache.Entry entry = queue.getCache().get(url);
        if (entry != null) {
            String data = new String(entry.data);
            String ingredients = null;
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

    private TextView getTextViewFromResource(@IdRes int res)
    {
        return (TextView) tableLayout.findViewById(res);
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
}
