package com.vanshgandhi.ucladining.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.vanshgandhi.ucladining.Fragments.DiningHallMenusHolderFragment;
import com.vanshgandhi.ucladining.Fragments.SwipesFragment;
import com.vanshgandhi.ucladining.R;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    DrawerLayout          drawer;
    ActionBarDrawerToggle toggle;
    private final String[][] titles = {
            {"COVEL", "DE NEVE", "FEAST", "BPLATE"},
            {"RENDEZVOUS", "CAFé 1919", "BCAFé"}};

    static Calendar c;

    private static final String YEAR_KEY  = "YEAR";
    private static final String MONTH_KEY = "MONTH";
    private static final String DAY_KEY   = "DAY";

    public SharedPreferences        preferences;
    public SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//            //TODO: Proper Transitions
//            //getWindow().setEnterTransition(new Slide());
//            //getWindow().setExitTransition(new Slide());
//        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        c = Calendar.getInstance();

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        editor.putInt(YEAR_KEY, c.get(Calendar.YEAR));
        editor.putInt(MONTH_KEY, c.get(Calendar.MONTH));
        editor.putInt(DAY_KEY, c.get(Calendar.DAY_OF_MONTH));
        editor.apply();

        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                DiningHallMenusHolderFragment.newInstance()).commit();
        navigationView.getMenu().getItem(0).setChecked(true);
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        
//        if (id == R.id.action_select_date) {
//            new DatePickerFragment().show(getFragmentManager(), "datePicker");
//            return true;
//        }
        
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.dining_hall) {
            if (!item.isChecked()) {
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                        DiningHallMenusHolderFragment.newInstance()).commit();
            }
        }
//        else if (id == R.id.quick_service) {
//            if (!item.isChecked()) {
//                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
//                        QuickServiceMenusHolderFragment.newInstance()).commit();
//            }
//        }
//        else if (id == R.id.hours) {
//            if (!item.isChecked()) {
//                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
//                        HoursFragment.newInstance()).commit();
//            }
//        }
        else if (id == R.id.swipe_manager) {
            if (!item.isChecked()) {
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                        SwipesFragment.newInstance()).commit();
            }
        }
//        else if (id == R.id.settings) {
//            startActivity(new Intent(this, SettingsActivity.class));
//        }
        else if (id == R.id.share) {
            String text = "Download this app from " +
                    "https://play.google.com/store/apps/details?id=com.vanshgandhi.ucladining";
            try {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, text);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
            catch (Exception e) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(getString(R.string.app_name), text);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(this, "Link copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        }
        else if (id == R.id.about) {
            startActivity(new Intent(this, AboutActivity.class));
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setupNavigationDrawer(Toolbar toolbar)
    {
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        {
            @Override
            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView)
            {
                super.onDrawerClosed(drawerView);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    public String[][] getTitles()
    {
        return titles;
    }

    public int getYear()
    {
        if(preferences.contains(YEAR_KEY)) {
            return preferences.getInt(YEAR_KEY, -1);
        }
        else
        {
            Calendar calendar = Calendar.getInstance();
            return calendar.get(Calendar.YEAR);
        }
    }

    public int getDay()
    {
        if(preferences.contains(DAY_KEY)) {
            return preferences.getInt(DAY_KEY, -1);
        }
        else
        {
            Calendar calendar = Calendar.getInstance();
            return calendar.get(Calendar.DAY_OF_MONTH);
        }
    }

    public int getMonth()
    {
        if(preferences.contains(MONTH_KEY)) {
            return preferences.getInt(MONTH_KEY, -1);
        }
        else
        {
            Calendar calendar = Calendar.getInstance();
            return calendar.get(Calendar.MONTH);
        }
    }

    public int getDayOfWeek()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getYear(), getMonth(), getDay());
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener
    {

        public SharedPreferences        preferences;
        public SharedPreferences.Editor editor;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState)
        {
            preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            // Use the current date as the default date in the picker
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, preferences.getInt(YEAR_KEY, 0), preferences.getInt(MONTH_KEY, 0), preferences.getInt(DAY_KEY, 0));
            DatePicker datePicker = dialog.getDatePicker();
            c.add(Calendar.DAY_OF_MONTH, 7); //View menus 1 week in advance
            datePicker.setMaxDate(c.getTimeInMillis());
            c.add(Calendar.DAY_OF_MONTH, -10); //View menus from 3 days ago
            datePicker.setMinDate(c.getTimeInMillis());
            c = Calendar.getInstance(); //Revert back to today's date
            return dialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day)
        {
            editor = preferences.edit();
            editor.putInt(YEAR_KEY, year);
            editor.putInt(MONTH_KEY, month);
            editor.putInt(DAY_KEY, day);
            editor.apply();

        }
    }
}
