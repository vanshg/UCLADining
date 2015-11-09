package com.vanshgandhi.ucladining.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
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
import com.vanshgandhi.ucladining.Fragments.HoursFragment;
import com.vanshgandhi.ucladining.Fragments.QuickServiceMenusHolderFragment;
import com.vanshgandhi.ucladining.R;
import com.vanshgandhi.ucladining.Fragments.SwipesFragment;

import org.json.JSONObject;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    JSONObject jsonObject = null;
    DrawerLayout          drawer;
    ActionBarDrawerToggle toggle;
    private final String[][] titles = {
            {"COVEL", "DE NEVE", "FEAST", "BPLATE"},
            {"RENDEZVOUS", "CAFé 1919", "BCAFé"}};

    public static int selectedDay;
    public static int selectedYear;
    public static int selectedMonth;


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
        
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_select_date) {
            new DatePickerFragment().show(getFragmentManager(), "datePicker");
            return true;
        }
        
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
        else if (id == R.id.quick_service) {
            if (!item.isChecked()) {
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                        QuickServiceMenusHolderFragment.newInstance()).commit();
            }
        }
        else if (id == R.id.hours) {
            if (!item.isChecked()) {
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                        HoursFragment.newInstance()).commit();
            }
        }
        else if (id == R.id.swipe_manager) {
            if (!item.isChecked()) {
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                        SwipesFragment.newInstance()).commit();
            }
        }
        else if (id == R.id.settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        }
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

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener
    {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState)
        {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day)
        {
            selectedYear = year;
            selectedDay = day;
            selectedMonth = month;
        }
    }
}
