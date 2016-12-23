package com.vanshgandhi.bruinmenu;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private int mDay, mMonth, mYear;
    private int previousSwipeToggle = R.id.toggle_19p;
    private Calendar c;
    private Calendar minDate;
    private Calendar maxDate;

    private MenuItem date;
    private Menu menu;

    DialogFragment dateFragment;

    @BindView(R.id.container)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.bottom_navigation)
    BottomBar bottomBar;


    MenuSectionsPagerAdapter sectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sectionsPagerAdapter = new MenuSectionsPagerAdapter(getSupportFragmentManager());
        sectionsPagerAdapter.setDisplayCafe(false);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mMonth = c.get(Calendar.MONTH);

        minDate = (Calendar) c.clone();
        maxDate = (Calendar) c.clone();
        minDate.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        maxDate.set(Calendar.DATE, minDate.get(Calendar.DATE) + 6);


        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                } else {
                    getSupportFragmentManager().popBackStack();
                }
                // write things with the bottom tabs
                if (tabId == R.id.action_swipe) {
                    tabLayout.setVisibility(View.GONE);
                    viewPager.setVisibility(View.GONE);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, SwipesFragment.newInstance(), "SwipesFrag").addToBackStack(null).commit();
                } else if (tabId == R.id.action_hall) {
                    sectionsPagerAdapter.setDisplayCafe(false);
                    viewPager.setAdapter(sectionsPagerAdapter);
                    tabLayout.setupWithViewPager(viewPager);
                    tabLayout.setVisibility(View.VISIBLE);
                    viewPager.setVisibility(View.VISIBLE);
                } else if (tabId == R.id.action_cafe) {
                    sectionsPagerAdapter.setDisplayCafe(true);
                    viewPager.setAdapter(sectionsPagerAdapter);
                    tabLayout.setupWithViewPager(viewPager);
                    tabLayout.setVisibility(View.VISIBLE);
                    viewPager.setVisibility(View.VISIBLE);
                } else if (tabId == R.id.action_hours) {
                    tabLayout.setVisibility(View.GONE);
                    viewPager.setVisibility(View.GONE);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, HoursFragment.newInstance(), "HoursFrag").addToBackStack(null).commit();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        // overriden so back button pressed does not do anything now!!
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_main, menu);
        date = menu.findItem(R.id.user_id_label);
        updateDisplay();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_calendar) {
            showDatePickerDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    public void showDatePickerDialog() {
        dateFragment = new DatePickerFragment();
        dateFragment.show(getFragmentManager(), "datePicker");
    }

    @SuppressLint("ValidFragment")
    public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Create a new instance of DatePickerDialog and return it
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, mYear, mMonth, mDay);
            dialog.getDatePicker().setMinDate(minDate.getTimeInMillis());
            dialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());
            return dialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, day);
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            updateDisplay();
        }
    }

    private void updateDisplay() {
        date.setTitle(new StringBuilder().append(mMonth + 1).append("-").append(mDay).append("-").append(mYear).append(""));
        SwipesFragment sf = (SwipesFragment) getSupportFragmentManager().findFragmentByTag("SwipesFrag");
        HoursFragment hf = (HoursFragment) getSupportFragmentManager().findFragmentByTag("HoursFrag");
        if (sf != null && sf.isVisible()) {
            getSupportFragmentManager().popBackStack();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, SwipesFragment.newInstance(), "SwipesFrag").addToBackStack(null).commit();
        } else if (hf != null && hf.isVisible()) {
            getSupportFragmentManager().popBackStack();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, HoursFragment.newInstance(), "HoursFrag").addToBackStack(null).commit();
        }
    }

    ////////////////////////// Getters and Setters //////////////////////////

    public Calendar getCurrentCal() {
        return c;
    }

    public void setPreviousSwipeToggle(int id) {
        previousSwipeToggle = id;
    }

    public int getPreviousSwipeToggle() {
        return previousSwipeToggle;
    }
}


//        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {        // we don't need to reselect anything but this is how you do it!
//            @Override
//            public void onTabReSelected(@IdRes int tabId) {
//                // write things with the bottom tabs on they are re-selected
//                if (tabId == R.id.action_swipe)
//                    Log.d("myapp", "you have tried to re-select to go to the swipes");
//            }
//        });
