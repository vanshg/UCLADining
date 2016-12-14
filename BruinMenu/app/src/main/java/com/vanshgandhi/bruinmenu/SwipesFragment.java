package com.vanshgandhi.bruinmenu;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Sahil on 12/11/2016.
 */

public class SwipesFragment extends Fragment {

    private MainActivity mainActivity;
    private TextView swipesText;
    private TextView dateText;

    enum MealPlan{
        MEAL_11, MEAL_14, MEAL_19, MEAL_14P, MEAL_19P
    }

    private final static int TOT_19P = 214;
    private final static int TOT_14P = 158;
    private final static int TOT_19 = 19;
    private final static int TOT_14 = 14;
    private final static int TOT_11 = 11;

    private MealPlan mealPlan;

    private int weeksSinceStart;

    Calendar rightNow;
    Calendar startOfQuarter;

    public SwipesFragment() {
    }

    public static SwipesFragment newInstance() {
        SwipesFragment fragment = new SwipesFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
        }
    }

    @Override
    public void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_swipes, container, false);

//        mainActivity = (MainActivity)getActivity();
        rightNow = mainActivity.getCurrentCal();

        swipesText = (TextView)rootView.findViewById(R.id.swipes);
        dateText = (TextView)rootView.findViewById(R.id.date);

        swipesText.setText("0");
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy ", Locale.US);
        String formattedDate = df.format(rightNow.getTime());
        dateText.setText(formattedDate);


        return rootView;
    }

}
