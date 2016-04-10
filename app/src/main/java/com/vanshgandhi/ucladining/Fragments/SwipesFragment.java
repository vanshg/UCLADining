package com.vanshgandhi.ucladining.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.vanshgandhi.ucladining.Activities.MainActivity;
import com.vanshgandhi.ucladining.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SwipesFragment extends Fragment
{
    static final String STATE_MEAL_PLAN = "mealPlan";

    enum MealPlan
    {
        MEAL_11, MEAL_14, MEAL_19, MEAL_14P, MEAL_19P
    }

    private static final int TOTAL_19P = 214;
    private static final int TOTAL_14P = 158;
    private static final int TOTAL_19  = 19;
    private static final int TOTAL_14  = 14;
    private static final int TOTAL_11  = 11;

    private MealPlan currentPlan;

    private MainActivity mainActivity;
    private Toolbar      toolbar;
    private TextView     swipes;
    private TextView     dateText;

    private Calendar rightNow;
    private Calendar quarterStart;

    private int weeksElapsed;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public static SwipesFragment newInstance()
    {
        return new SwipesFragment();
    }
    
    public SwipesFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
        }
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = preferences.edit();
        int ordinal = preferences.getInt(STATE_MEAL_PLAN, MealPlan.MEAL_19P.ordinal());
        currentPlan = MealPlan.values()[ordinal];
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
        dateText = (TextView) rootView.findViewById(R.id.date);

        rightNow = Calendar.getInstance();
        rightNow.set(mainActivity.getYear(), mainActivity.getMonth(), mainActivity.getDay());
        quarterStart = Calendar.getInstance();
        quarterStart.set(2016, Calendar.MARCH, 28); //TODO: Find a way to not hardcode this
        int nowWeek = rightNow.get(Calendar.WEEK_OF_YEAR);
        int quarterStartWeek = quarterStart.get(Calendar.WEEK_OF_YEAR);
        weeksElapsed = nowWeek - quarterStartWeek;

        RadioGroup radioGroup = (RadioGroup) rootView.findViewById(R.id.meal_selector);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch (checkedId) {
                    case R.id.toggle_11:
                        currentPlan = MealPlan.MEAL_11;
                        break;
                    case R.id.toggle_14:
                        currentPlan = MealPlan.MEAL_14;
                        break;
                    case R.id.toggle_19:
                        currentPlan = MealPlan.MEAL_19;
                        break;
                    case R.id.toggle_14p:
                        currentPlan = MealPlan.MEAL_14P;
                        break;
                    case R.id.toggle_19p:
                        currentPlan = MealPlan.MEAL_19P;
                        break;
                }
                editor.putInt(STATE_MEAL_PLAN, currentPlan.ordinal());
                editor.apply();
                updateSwipeCount();
            }
        });
        int id;
        switch (currentPlan) {
            case MEAL_11:
                id = R.id.toggle_11;
                break;
            case MEAL_14:
                id = R.id.toggle_14;
                break;
            case MEAL_19:
                id = R.id.toggle_19;
                break;
            case MEAL_14P:
                id = R.id.toggle_14p;
                break;
            default:
                id = R.id.toggle_19p;
        }
        radioGroup.check(id);
        return rootView;
    }

    private void updateSwipeCount()
    {
        int swipesLeft = 0;
        switch (currentPlan) {
            case MEAL_11:
                swipesLeft = TOTAL_11;
                break;
            case MEAL_14:
                swipesLeft = TOTAL_14;
                break;
            case MEAL_19:
                swipesLeft = TOTAL_19;
                break;
            case MEAL_14P:
                swipesLeft = TOTAL_14P;
                break;
            case MEAL_19P:
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
                if (currentPlan == MealPlan.MEAL_19P || currentPlan == MealPlan.MEAL_19) {
                    swipes -= 2;
                } else if (currentPlan == MealPlan.MEAL_14P || currentPlan == MealPlan.MEAL_14) {
                    swipes -= 2;
                }
                if (currentPlan == MealPlan.MEAL_11) {
                    swipes -= 0; //Assumes person doesn't eat on weekends
                }
                break;
            case Calendar.MONDAY:
                if (currentPlan == MealPlan.MEAL_19P || currentPlan == MealPlan.MEAL_19) {
                    swipes -= 3;
                } else if (currentPlan == MealPlan.MEAL_14P || currentPlan == MealPlan.MEAL_14) {
                    swipes -= 2;
                }
                if (currentPlan == MealPlan.MEAL_11) {
                    swipes -= 2;
                }
                break;
            case Calendar.TUESDAY:
                if (currentPlan == MealPlan.MEAL_19P || currentPlan == MealPlan.MEAL_19) {
                    swipes -= 3;
                } else if (currentPlan == MealPlan.MEAL_14P || currentPlan == MealPlan.MEAL_14) {
                    swipes -= 2;
                }
                if (currentPlan == MealPlan.MEAL_11) {
                    swipes -= 2;
                }
                break;
            case Calendar.WEDNESDAY:
                if (currentPlan == MealPlan.MEAL_19P || currentPlan == MealPlan.MEAL_19) {
                    swipes -= 3;
                } else if (currentPlan == MealPlan.MEAL_14P || currentPlan == MealPlan.MEAL_14) {
                    swipes -= 2;
                }
                if (currentPlan == MealPlan.MEAL_11) {
                    swipes -= 2;
                }
                break;
            case Calendar.THURSDAY:
                if (currentPlan == MealPlan.MEAL_19P || currentPlan == MealPlan.MEAL_19) {
                    swipes -= 3;
                } else if (currentPlan == MealPlan.MEAL_14P || currentPlan == MealPlan.MEAL_14) {
                    swipes -= 2;
                }
                if (currentPlan == MealPlan.MEAL_11) {
                    swipes -= 2;
                }
                break;
            case Calendar.FRIDAY:
                if (currentPlan == MealPlan.MEAL_19P || currentPlan == MealPlan.MEAL_19) {
                    swipes -= 3;
                } else if (currentPlan == MealPlan.MEAL_14P || currentPlan == MealPlan.MEAL_14) {
                    swipes -= 2;
                }
                if (currentPlan == MealPlan.MEAL_11) {
                    swipes -= 2;
                }
                break;
            case Calendar.SATURDAY:
                if (currentPlan == MealPlan.MEAL_19P || currentPlan == MealPlan.MEAL_19) {
                    swipes -= 2;
                } else if (currentPlan == MealPlan.MEAL_14P || currentPlan == MealPlan.MEAL_14) {
                    swipes -= 2;
                }
                if (currentPlan == MealPlan.MEAL_11) {
                    swipes -= 1;
                }
                break;
        }
        return swipes;
    }

    private int removeWeekSwipes(int swipes)
    {
        if (currentPlan == MealPlan.MEAL_19P) {
            for (int i = 0; i < weeksElapsed; i++) {
                swipes -= 19;
            }
        } else if (currentPlan == MealPlan.MEAL_14P) {
            for (int i = 0; i < weeksElapsed; i++) {
                swipes -= 14;
            }
        }
        return swipes;
    }
}

//TODO: REWork entire swipes architecture to be more efficient and less explicit