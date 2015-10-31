package com.vanshgandhi.ucladining;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        QuickServiceMenuFragment.OnFragmentInteractionListener
{
    
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

//    private Spinner spinner;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_main);
        
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each section of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

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
//        });

        getSupportActionBar().setTitle("Dining Halls");

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        
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

        }
        else if (id == R.id.quick_service) {
//            getFragmentManager().beginTransaction().replace(R.id.container,
//                    QuickServiceMenuFragment.newInstance()).addToBackStack(null).commit();
        }
        else if (id == R.id.hours) {

        }
        else if (id == R.id.swipe_manager) {

        }
        else if (id == R.id.settings) {

        }
        else if (id == R.id.share) {

        }
        else if (id == R.id.about) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onFragmentInteraction(String id)
    {

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
}
