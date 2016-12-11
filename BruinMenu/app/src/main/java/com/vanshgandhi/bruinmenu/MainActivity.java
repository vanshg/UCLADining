package com.vanshgandhi.bruinmenu;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private int mDay, mMonth, mYear;
    private Calendar c;
    private MenuItem date;
    private Menu menu;

    DialogFragment dateFragment;
    DialogFragment timeFragment;

    @BindView (R.id.container) ViewPager viewPager;
    @BindView (R.id.tabs) TabLayout tabLayout;
    @BindView (R.id.bottom_navigation) BottomBar bottomBar;


    MenuSectionsPagerAdapter sectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sectionsPagerAdapter = new MenuSectionsPagerAdapter(
                getSupportFragmentManager());
        viewPager.setAdapter(sectionsPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mMonth = c.get(Calendar.MONTH);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

            }
        });
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
            showDatePickerDialog(id);

            //showDialog(R.id.action_calendar);
        }
        return super.onOptionsItemSelected(item);
    }

    public void showDatePickerDialog(int id)
    {
        dateFragment = new DatePickerFragment();
        dateFragment.show(getFragmentManager(), "datePicker");
    }

    public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener
    {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, mYear, mMonth, mDay);
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

    private void updateDisplay()
    {
        date.setTitle(new StringBuilder().append(mMonth + 1).append("-").append(mDay).append("-").append(mYear).append(""));
    }
}
