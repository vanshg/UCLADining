package com.vanshgandhi.bruinmenu;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.RadioGroup;

import java.lang.reflect.Array;
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

    private final static int TOT_19P = 209;
    private final static int TOT_14P = 154;

//    private int [] regular_19 = new int[] {2, 19, 16, 13, 10, 7, 4};
//    private int [] regular_14 = new int[] {2, 14, 12, 10, 8, 6, 4};
//    private int [] regular_11 = new int[] {0, 11, 9, 7, 5, 3, 1};

    private int weeksSinceStart;
    private int swipesLeft;

    private int id;

    Calendar rightNow;
    Calendar startOfQuarter;

    public SwipesFragment() {
    }

    public static SwipesFragment newInstance() {
        SwipesFragment fragment = new SwipesFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_swipes, container, false);

        id = mainActivity.getPreviousSwipeToggle();

        rightNow = mainActivity.getCurrentCal();                    // TODO: somehow make a listener or something to change this date appropriately instead of relying on erasing the current fragment and replacing it with the new date

        swipesText = (TextView) rootView.findViewById(R.id.swipes);
        dateText = (TextView) rootView.findViewById(R.id.date);

        SimpleDateFormat df = new SimpleDateFormat("MMMM d, yyyy ", Locale.US);
        String formattedDate = df.format(rightNow.getTime());
        dateText.setText(formattedDate);

        startOfQuarter = Calendar.getInstance();
        startOfQuarter.set(2017, Calendar.JUNE, 26);                // TODO: Find way to not hardcode this -> look at jaunt API, then grep for Instruction begins ... probably best way to do it http://jaunt-api.com/
        int currentWeek = rightNow.get(Calendar.WEEK_OF_YEAR);
        int quarterWeek = startOfQuarter.get(Calendar.WEEK_OF_YEAR);
        weeksSinceStart = currentWeek - quarterWeek;
        swipesLeft = 0;

        RadioGroup radioGroup = (RadioGroup) rootView.findViewById(R.id.meal_selector);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                id = checkedId;
                swipesLeft = refresh(checkedId);
                swipesText.setText(new StringBuilder().append(swipesLeft));
                mainActivity.setPreviousSwipeToggle(id);
            }
        });
        radioGroup.check(id);

        return rootView;
    }

    private int refresh(int mealPlanType) {
        rightNow = mainActivity.getCurrentCal();
        SimpleDateFormat df = new SimpleDateFormat("MMMM dd, yyyy ", Locale.US);
        String formattedDate = df.format(rightNow.getTime());
        dateText.setText(formattedDate);

        startOfQuarter = Calendar.getInstance();
        startOfQuarter.set(2017, Calendar.JANUARY, 9);      // TODO: Find way to not hardcode this -> look at jaunt API, then grep for Instruction begins ... probably best way to do it http://jaunt-api.com/
        int currentWeek = rightNow.get(Calendar.WEEK_OF_YEAR);
        int quarterWeek = startOfQuarter.get(Calendar.WEEK_OF_YEAR);
        weeksSinceStart = currentWeek - quarterWeek;

        int weekSwipesRemoved = 0;

        weekSwipesRemoved = removeWeekSwipes(mealPlanType);
        weekSwipesRemoved = removeDaySwipes(weekSwipesRemoved, mealPlanType);
        weekSwipesRemoved = removeHourSwipes(weekSwipesRemoved, mealPlanType);

        return weekSwipesRemoved;
    }

    private int removeHourSwipes(int currSwipes, int mealPlanType) {
        int currHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int day = rightNow.get(Calendar.DAY_OF_WEEK);

        if (day == Calendar.SATURDAY) {
            if (currHour >= 17 && currHour < 21)
                currSwipes -= 1;
            else if (currHour >= 21) {
                if (mealPlanType != R.id.toggle_11)
                    currSwipes -= 2;
            }
        } else if (day == Calendar.SUNDAY) {
            if (mealPlanType != R.id.toggle_11) {
                if (currHour >= 17 && currHour < 21)
                    currSwipes -= 1;
                else if (currHour >= 21) {
                    currSwipes -= 2;
                }
            }
        } else {
            if (currHour >= 11 && currHour < 16)     // remove 1 swipe for 19 and 19p
            {
                if (mealPlanType == R.id.toggle_19p || mealPlanType == R.id.toggle_19)
                    currSwipes -= 1;
            } else if (currHour >= 16 && currHour < 21)    // remove 2 swipes for 19 and 19p, remove 1 for the rest
            {
                if (mealPlanType == R.id.toggle_19p || mealPlanType == R.id.toggle_19)
                    currSwipes -= 2;
                else
                    currSwipes -= 1;
            } else if (currHour >= 21) {            // remove 3 swipes for 19 and 19p, remove 2 for the rest
                if (mealPlanType == R.id.toggle_19p || mealPlanType == R.id.toggle_19)
                    currSwipes -= 3;
                else
                    currSwipes -= 2;
            }
        }
        return currSwipes;
    }

    private int removeWeekSwipes(int mealPlanType) {
        int swipesLeft = 0;
        switch (mealPlanType) {
            case R.id.toggle_14p:
                swipesLeft = TOT_14P;
                swipesLeft -= 14 * weeksSinceStart;
                break;
            case R.id.toggle_19p:
                swipesLeft = TOT_19P;
                swipesLeft -= 19 * weeksSinceStart;
                break;
            case R.id.toggle_11:
                swipesLeft = 11;
                break;
            case R.id.toggle_14:
                swipesLeft = 14;
                break;
            case R.id.toggle_19:
                swipesLeft = 19;
                break;
        }
        return swipesLeft;
    }

    private int removeDaySwipes(int currSwipes, int mealPlanType) {
        int day = rightNow.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case Calendar.MONDAY:           // monday is beginning of day so as of monday morning, you should have ur full amount of swipes
                return currSwipes;
            case Calendar.TUESDAY:
                if (mealPlanType == R.id.toggle_19 || mealPlanType == R.id.toggle_19p)
                    currSwipes -= 3;
                else
                    currSwipes -= 2;
                break;
            case Calendar.WEDNESDAY:
                if (mealPlanType == R.id.toggle_19 || mealPlanType == R.id.toggle_19p)
                    currSwipes -= 6;
                else
                    currSwipes -= 4;
                break;
            case Calendar.THURSDAY:
                if (mealPlanType == R.id.toggle_19 || mealPlanType == R.id.toggle_19p)
                    currSwipes -= 9;
                else
                    currSwipes -= 6;
                break;
            case Calendar.FRIDAY:
                if (mealPlanType == R.id.toggle_19 || mealPlanType == R.id.toggle_19p)
                    currSwipes -= 12;
                else
                    currSwipes -= 8;
                break;
            case Calendar.SATURDAY:
                if (mealPlanType == R.id.toggle_19 || mealPlanType == R.id.toggle_19p)
                    currSwipes -= 15;
                else
                    currSwipes -= 10;
                break;
            case Calendar.SUNDAY:
                if (mealPlanType == R.id.toggle_19 || mealPlanType == R.id.toggle_19p)
                    currSwipes -= 17;
                else if (mealPlanType == R.id.toggle_14 || mealPlanType == R.id.toggle_14p)
                    currSwipes -= 12;
                else
                    currSwipes -= 11;       // no more swipes left for 11 ppl
                break;
        }
        return currSwipes;
    }
}
