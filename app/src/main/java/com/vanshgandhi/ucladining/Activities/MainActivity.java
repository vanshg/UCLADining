package com.vanshgandhi.ucladining.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabClickListener;
import com.vanshgandhi.ucladining.Fragments.DiningHallMenusHolderFragment;
import com.vanshgandhi.ucladining.Fragments.HoursFragment;
import com.vanshgandhi.ucladining.Fragments.QuickServiceMenusHolderFragment;
import com.vanshgandhi.ucladining.Fragments.SwipesFragment;
import com.vanshgandhi.ucladining.R;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    BottomBar bottomBar;
    private final String[][] titles = {{"COVEL", "DE NEVE", "FEAST", "BPLATE"}, {"RENDEZVOUS", "CAFé 1919", "BCAFé"}};

    private static Calendar c = null;
    private static int calMonth;
    private static int calYear;
    private static int calDay;

    private static final String YEAR_KEY   = "YEAR";
    private static final String MONTH_KEY  = "MONTH";
    private static final String DAY_KEY    = "DAY";
    private static final String DINING_KEY = "DINING";
    private static final String CAFE_KEY   = "CAFE";
    private static final String HOURS_KEY  = "HOURS";
    private static final String SWIPES_KEY = "SWIPES";

    private static SharedPreferences        preferences;
    private static SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//            //TODO: Proper Transitions
//            //getWindow().setEnterTransition(new Slide());
//            //getWindow().setExitTransition(new Slide());
//        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        c = Calendar.getInstance();
        calDay = c.get(Calendar.DAY_OF_MONTH);
        calMonth = c.get(Calendar.MONTH);
        calYear = c.get(Calendar.YEAR);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        dateChanged();

        bottomBar = BottomBar.attachShy((CoordinatorLayout) findViewById(R.id.main_content),
                findViewById(R.id.content_frame), savedInstanceState);
        bottomBar.setActiveTabColor(ContextCompat.getColor(this, R.color.colorAccent));
        bottomBar.setItems(new BottomBarTab(R.drawable.ic_dining_hall, R.string.halls),
                new BottomBarTab(R.drawable.ic_quick_eat, R.string.cafes),
                new BottomBarTab(R.drawable.ic_time, R.string.hours),
                new BottomBarTab(R.drawable.ic_swipes, R.string.swipes));
        bottomBar.setOnTabClickListener(new OnTabClickListener() {
            @Override
            public void onTabSelected(int position) {
                Fragment f;
                String tag;
                switch (position) {
                    default:
                    case 0:
                        f = DiningHallMenusHolderFragment.newInstance();
                        tag = DINING_KEY;
                        break;
                    case 1:
                        f = QuickServiceMenusHolderFragment.newInstance();
                        tag = CAFE_KEY;
                        break;
                    case 2:
                        f = HoursFragment.newInstance();
                        tag = HOURS_KEY;
                        break;
                    case 3:
                        f = SwipesFragment.newInstance();
                        tag = SWIPES_KEY;
                        break;
                }
                getFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, f, tag)
                        .commit();
            }

            @Override
            public void onTabReSelected(int position) {
                //TODO: scroll to top
            }
        });
    }

    private static void dateChanged() {
        editor.putInt(YEAR_KEY, calYear);
        editor.putInt(MONTH_KEY, calYear);
        editor.putInt(DAY_KEY, calDay);
        editor.apply();
        //TODO: REFRESH ALL ACTIVE FRAGMENTS WITH CURRENT DATE
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_select_date) {
            new DatePickerFragment().show(getFragmentManager(), "datePicker");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public String[][] getTitles() {
        return titles;
    }

    public Calendar getCalendarFromSelectedDate() {
        return c;
    }

    public int getYear() {
        return calYear;
    }

    public int getDay() {
        return calDay;
    }

    public int getMonth() {
        return calMonth;
    }

    public int getDayOfWeek() {
        return c.get(Calendar.DAY_OF_WEEK);
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        public DatePickerFragment() {
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            // Use the current date as the default date in the picker
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, calYear, calMonth,
                    calDay);
            DatePicker datePicker = dialog.getDatePicker();
            Calendar temp = Calendar.getInstance();
            temp.add(Calendar.DAY_OF_MONTH, 7); //View menus 1 week in advance
            datePicker.setMaxDate(c.getTimeInMillis());
            temp.add(Calendar.DAY_OF_MONTH, -10); //View menus from 3 days ago
            datePicker.setMinDate(temp.getTimeInMillis());
            return dialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            calDay = day;
            calYear = year;
            calMonth = month;
            dateChanged();
        }
    }

}
