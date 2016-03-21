package com.vanshgandhi.ucladining.Fragments;


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

import com.vanshgandhi.ucladining.Activities.MainActivity;
import com.vanshgandhi.ucladining.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SwipesFragment extends Fragment implements View.OnClickListener//implements CompoundButton.OnCheckedChangeListener
{
    static final String STATE_MEAL_PLAN = "mealPlan";

    static final String MEAL_PLAN_11  = "11";
    static final String MEAL_PLAN_14  = "14";
    static final String MEAL_PLAN_19  = "19";
    static final String MEAL_PLAN_14P = "14P";
    static final String MEAL_PLAN_19P = "19P";

    static final int TOTAL_19P = 214;
    static final int TOTAL_14P = 158;
    static final int TOTAL_19  = 19;
    static final int TOTAL_14  = 14;
    static final int TOTAL_11  = 11;

    private String currentPlan;

    private MainActivity mainActivity;
    private Toolbar      toolbar;
    private TextView     swipes;
    private TextView     dateText;

    Calendar rightNow;
    Calendar quarterStart;

    int weeksElapsed;


    public static SwipesFragment newInstance()
    {
        return new SwipesFragment();
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
        ToggleButton meal19P;
        ToggleButton meal14P;
        ToggleButton meal19;
        ToggleButton meal14;
        ToggleButton meal11;

        View rootView = inflater.inflate(R.layout.fragment_swipes, container, false);

        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.swipes));
        mainActivity.setSupportActionBar(toolbar);

        swipes = (TextView) rootView.findViewById(R.id.swipes);
        dateText = (TextView) rootView.findViewById(R.id.date);

        meal19P = (ToggleButton) rootView.findViewById(R.id.toggle_19p);
        meal14P = (ToggleButton) rootView.findViewById(R.id.toggle_14p);
        meal19 = (ToggleButton) rootView.findViewById(R.id.toggle_19);
        meal14 = (ToggleButton) rootView.findViewById(R.id.toggle_14);
        meal11 = (ToggleButton) rootView.findViewById(R.id.toggle_11);

        meal19P.setOnClickListener(this);
        meal14P.setOnClickListener(this);
        meal19.setOnClickListener(this);
        meal14.setOnClickListener(this);
        meal11.setOnClickListener(this);

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
        rightNow.set(mainActivity.getYear(), mainActivity.getMonth(), mainActivity.getDay());
        quarterStart = Calendar.getInstance();
        quarterStart.set(2015, Calendar.SEPTEMBER, 20);
        int nowWeek = rightNow.get(Calendar.WEEK_OF_YEAR);
        int quarterStartWeek = quarterStart.get(Calendar.WEEK_OF_YEAR);
        weeksElapsed = nowWeek - quarterStartWeek;
        updateSwipeCount();


        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
//        mainActivity.setupNavigationDrawer(toolbar);
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
        ((RadioGroup) view.getParent()).check(id);
        switch (id) {
            case R.id.toggle_11:
                currentPlan = MEAL_PLAN_11;
                break;
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
        updateSwipeCount();
    }

    private void updateSwipeCount()
    {
        int swipesLeft = 0;
        switch (currentPlan) {
            case MEAL_PLAN_11:
                swipesLeft = TOTAL_11;
                break;
            case MEAL_PLAN_14:
                swipesLeft = TOTAL_14;
                break;
            case MEAL_PLAN_19:
                swipesLeft = TOTAL_19;
                break;
            case MEAL_PLAN_14P:
                swipesLeft = TOTAL_14P;
                break;
            case MEAL_PLAN_19P:
                swipesLeft = TOTAL_19P;
                break;
        }
        swipesLeft = removeWeekSwipes(swipesLeft);
        swipesLeft = removeDaySwipes(swipesLeft);

        swipes.setText(String.format(Locale.US, "%d", swipesLeft));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM d, yyyy", Locale.US);
        dateText.setText(simpleDateFormat.format(rightNow.getTime()));
    }

    private int removeDaySwipes(int swipes)
    {
        int day = rightNow.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case Calendar.SUNDAY:
                if (currentPlan.equals(MEAL_PLAN_19P) || currentPlan.equals(MEAL_PLAN_19)) {
                    swipes -= 2;
                }
                else if (currentPlan.equals(MEAL_PLAN_14P) || currentPlan.equals(MEAL_PLAN_14)) {
                    swipes -= 2;
                }
                if (currentPlan.equals(MEAL_PLAN_11)) {
                    swipes -= 0; //Assumes person doesn't eat on weekends
                }
                break;
            case Calendar.MONDAY:
                if (currentPlan.equals(MEAL_PLAN_19P) || currentPlan.equals(MEAL_PLAN_19)) {
                    swipes -= 3;
                }
                else if (currentPlan.equals(MEAL_PLAN_14P) || currentPlan.equals(MEAL_PLAN_14)) {
                    swipes -= 2;
                }
                if (currentPlan.equals(MEAL_PLAN_11)) {
                    swipes -= 2;
                }
                break;
            case Calendar.TUESDAY:
                if (currentPlan.equals(MEAL_PLAN_19P) || currentPlan.equals(MEAL_PLAN_19)) {
                    swipes -= 3;
                }
                else if (currentPlan.equals(MEAL_PLAN_14P) || currentPlan.equals(MEAL_PLAN_14)) {
                    swipes -= 2;
                }
                if (currentPlan.equals(MEAL_PLAN_11)) {
                    swipes -= 2;
                }
                break;
            case Calendar.WEDNESDAY:
                if (currentPlan.equals(MEAL_PLAN_19P) || currentPlan.equals(MEAL_PLAN_19)) {
                    swipes -= 3;
                }
                else if (currentPlan.equals(MEAL_PLAN_14P) || currentPlan.equals(MEAL_PLAN_14)) {
                    swipes -= 2;
                }
                if (currentPlan.equals(MEAL_PLAN_11)) {
                    swipes -= 2;
                }
                break;
            case Calendar.THURSDAY:
                if (currentPlan.equals(MEAL_PLAN_19P) || currentPlan.equals(MEAL_PLAN_19)) {
                    swipes -= 3;
                }
                else if (currentPlan.equals(MEAL_PLAN_14P) || currentPlan.equals(MEAL_PLAN_14)) {
                    swipes -= 2;
                }
                if (currentPlan.equals(MEAL_PLAN_11)) {
                    swipes -= 2;
                }
                break;
            case Calendar.FRIDAY:
                if (currentPlan.equals(MEAL_PLAN_19P) || currentPlan.equals(MEAL_PLAN_19)) {
                    swipes -= 3;
                }
                else if (currentPlan.equals(MEAL_PLAN_14P) || currentPlan.equals(MEAL_PLAN_14)) {
                    swipes -= 2;
                }
                if (currentPlan.equals(MEAL_PLAN_11)) {
                    swipes -= 2;
                }
                break;
            case Calendar.SATURDAY:
                if (currentPlan.equals(MEAL_PLAN_19P) || currentPlan.equals(MEAL_PLAN_19)) {
                    swipes -= 2;
                }
                else if (currentPlan.equals(MEAL_PLAN_14P) || currentPlan.equals(MEAL_PLAN_14)) {
                    swipes -= 2;
                }
                if (currentPlan.equals(MEAL_PLAN_11)) {
                    swipes -= 1;
                }
                break;
        }
        return swipes;
    }

    private int removeWeekSwipes(int swipes)
    {
        if (currentPlan.equals(MEAL_PLAN_19P)) {
            for (int i = 0; i < weeksElapsed; i++) {
                swipes -= 19;
            }
        }
        else if (currentPlan.equals(MEAL_PLAN_14P)) {
            for (int i = 0; i < weeksElapsed; i++) {
                swipes -= 14;
            }
        }
        return swipes;
    }
}

//TODO: REWork entire swipes architecture to be more efficient and less explicit