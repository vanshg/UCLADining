package com.vanshgandhi.ucladining;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Calendar;

public class SwipesFragment extends Fragment implements View.OnClickListener//implements CompoundButton.OnCheckedChangeListener
{
    static final String STATE_MEAL_PLAN = "mealPlan";

    static final String MEAL_PLAN_11    = "11";
    static final String MEAL_PLAN_14    = "14";
    static final String MEAL_PLAN_19    = "19";
    static final String MEAL_PLAN_14P   = "14P";
    static final String MEAL_PLAN_19P   = "19P";

    static final int TOTAL_19P = 214;
    static final int TOTAL_14P = 158;

    private String       currentPlan;

    private MainActivity mainActivity;
    private Toolbar      toolbar;
    private TextView     swipes;

    private ToggleButton meal19P;
    private ToggleButton meal14P;
    private ToggleButton meal19;
    private ToggleButton meal14;

    Calendar rightNow;
    Calendar quarterStart;

    int difference;


    public static SwipesFragment newInstance()
    {
        SwipesFragment fragment = new SwipesFragment();
        return fragment;
    }
    
    public SwipesFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        mainActivity = (MainActivity) activity;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        currentPlan = (savedInstanceState != null) ?
                savedInstanceState.getString(STATE_MEAL_PLAN, MEAL_PLAN_19P) : MEAL_PLAN_19P;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_swipes, container, false);

        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.swipes));
        mainActivity.setSupportActionBar(toolbar);

        swipes = (TextView) rootView.findViewById(R.id.swipes);
        swipes.setText("94");

        meal19P = (ToggleButton) rootView.findViewById(R.id.toggle_19p);
        meal14P = (ToggleButton) rootView.findViewById(R.id.toggle_14p);
        meal19 = (ToggleButton) rootView.findViewById(R.id.toggle_19);
        meal14 = (ToggleButton) rootView.findViewById(R.id.toggle_14);

        meal19P.setOnClickListener(this);
        meal14P.setOnClickListener(this);
        meal19.setOnClickListener(this);
        meal14.setOnClickListener(this);

        ((RadioGroup) rootView.findViewById(R.id.meal_selector)).setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener()
                {
                    @Override
                    public void onCheckedChanged(final RadioGroup radioGroup, final int i)
                    {
                        for (int j = 0; j < radioGroup.getChildCount(); j++) {
                            final ToggleButton view = (ToggleButton) radioGroup.getChildAt(j);
                            //Log.v("TAG", ""+view.isChecked()); TODO: sometimes, 2 buttons are marked as true
                            view.setChecked(view.getId() == i);
                        }
                    }
                });

        rightNow = Calendar.getInstance();
        quarterStart = Calendar.getInstance();
        quarterStart.set(2015, Calendar.SEPTEMBER, 20);
        int nowWeek = rightNow.WEEK_OF_YEAR;
        int quarterStartWeek = quarterStart.WEEK_OF_YEAR;
        difference = nowWeek - quarterStartWeek;
        updateSwipeCount(difference);


        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mainActivity.setupNavigationDrawer(toolbar);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        savedInstanceState.putString(STATE_MEAL_PLAN, currentPlan);

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onClick(View view)
    {
        int id = view.getId();
        ((RadioGroup)view.getParent()).check(id);
        switch(id)
        {
            case R.id.toggle_14:
                currentPlan = MEAL_PLAN_14;
                break;
            case R.id.toggle_19:
                currentPlan = MEAL_PLAN_19;
                break;
            case R.id.toggle_14p:
                currentPlan = MEAL_PLAN_14P;
                break;
            case R.id.toggle_19p:
                currentPlan = MEAL_PLAN_19P;
                break;
        }
        updateSwipeCount(difference);
    }

    private void updateSwipeCount(int weeks)
    {
        int swipesLeft;
        int perWeek;
        if(currentPlan.equals(MEAL_PLAN_19P))
        {
            swipesLeft = TOTAL_19P;
            perWeek = 19;
        }
        else if(currentPlan.equals(MEAL_PLAN_14P))
        {
            swipesLeft = TOTAL_14P;
            perWeek = 14;
        }
        else {
            swipesLeft = 19;
            perWeek = 19;
        }
        for(int i = 0; i < weeks; i++)
        {
            swipesLeft -= perWeek;
        }
        swipes.setText(Integer.toString(swipesLeft));
    }
}

//TODO: Work on entire swipes algorithm