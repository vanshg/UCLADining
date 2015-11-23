package com.vanshgandhi.ucladining.Activities;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.vanshgandhi.ucladining.R;

import org.json.JSONException;
import org.json.JSONObject;

public class FoodDetailActivity extends AppCompatActivity
{
    
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
        RequestQueue queue = Volley.newRequestQueue(this);
        Bundle args = getIntent().getExtras();
        String title = "Name of Food";
        String recipeNumber = "000000";
        String portionSize = "1";
        if (args != null) {
            title = args.getString("TITLE", "Name of Food");
            recipeNumber = args.getString("RECIPE_NUMBER", "000000");
            portionSize = args.getString("PORTION_SIZE", "1");
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Saved to Favorites", Snackbar.LENGTH_LONG).show();
            }
        });
        String url = "https://api.import.io/store/data/eacba959-1feb-4119-9388-bbb5cd4fdfff/_query?input/webpage/url=http%3A%2F%2Fmenu.ha.ucla.edu%2Ffoodpro%2Frecipedetail.asp%3FRecipeNumber%3D" + recipeNumber + "%26PortionSize%3D" + portionSize + "&_user=22403bda-b7eb-4c87-904a-78de1838426c&_apikey=22403bdab7eb4c87904a78de1838426c6e7d3048637d4bbae71657eb53b31c47d987e5e1cb53206a5fac41e1b938b1abcbb0ed68909ebb9d9e75447cc09546577d6725bd3f2bee95e827ee604fa7d84c";
        //String url = URLEncoder.encode("http://menu.ha.ucla.edu/foodpro/recipedetail.asp?RecipeNumber=979485&PortionSize=1");

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, "", new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                try {
                    String ingredients = processResponse(response);
                    ((TextView) findViewById(R.id.ingredients)).setText(ingredients);
                }
                catch (JSONException e) {
                    ((TextView) findViewById(R.id.ingredients)).setText("Error");
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                ((TextView) findViewById(R.id.ingredients)).setText("Error");
            }
        });

        queue.add(request);
    }


    private String processResponse(JSONObject response) throws JSONException
    {
        String ingredients = "";
        if (response.has("results")) {
            ingredients = response.getJSONArray("results").getJSONObject(0).getString("ingredients");
        }
        return ingredients;
    }
}
