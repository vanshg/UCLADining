package com.vanshgandhi.ucladining;

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

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    JSONObject jsonObject = null;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    private final String[][] titles = {
            {"COVEL", "DE NEVE", "FEAST", "BPLATE"},
            {"RENDEZVOUS", "CAFé 1919", "BCAFé"}};

//    private Spinner spinner;
    
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
                DiningHallMenuFragment.newInstance()).commit();
        navigationView.getMenu().getItem(0).setChecked(true);

        //        spinner = (Spinner) findViewById(R.id.date_spinner);
//
//        spinner.setAdapter(new MyAdapter(
//                toolbar.getContext(),
//                new String[]{
//                        "November 1, 2015",
//                        "November 2, 2015",
//                        "November 3, 2015",
//                        "November 4, 2015",
//                        "November 5, 2015",
//                        "November 6, 2015",
//                        "November 7, 2015",
//                        "November 8, 2015"
//                }));
//
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
//        {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
//            {
//                // When the given dropdown item is selected, show its contents in the
//                // container view.
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.container, DiningHallMenuFragment.newInstance(position + 1))
//                        .commit();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent)
//            {
//            }
//
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
                        DiningHallMenuFragment.newInstance()).commit();
            }
        }
        else if (id == R.id.quick_service) {
            if (!item.isChecked()) {
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                        QuickServiceMenuFragment.newInstance()).commit();
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

        }
        else if (id == R.id.share) {

        }
        else if (id == R.id.about) {

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

//    private static class MyAdapter extends ArrayAdapter<String> implements ThemedSpinnerAdapter
//    {
//        private final ThemedSpinnerAdapter.Helper mDropDownHelper;
//
//        public MyAdapter(Context context, String[] objects)
//        {
//            super(context, android.R.layout.simple_list_item_1, objects);
//            mDropDownHelper = new ThemedSpinnerAdapter.Helper(context);
//        }
//
//        @Override
//        public View getDropDownView(int position, View convertView, ViewGroup parent)
//        {
//            View view;
//
//            if (convertView == null) {
//                // Inflate the drop down using the helper's LayoutInflater
//                LayoutInflater inflater = mDropDownHelper.getDropDownViewInflater();
//                view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
//            }
//            else {
//                view = convertView;
//            }
//
//            TextView textView = (TextView) view.findViewById(android.R.id.text1);
//            textView.setText(getItem(position));
//
//            return view;
//        }
//
//        @Override
//        public void setDropDownViewTheme(Resources.Theme theme)
//        {
//            mDropDownHelper.setDropDownViewTheme(theme);
//        }
//
//        @Override
//        public Resources.Theme getDropDownViewTheme()
//        {
//            return mDropDownHelper.getDropDownViewTheme();
//        }
//    }

    public String[][] getTitles()
    {
        return titles;
    }
}
