package com.vanshgandhi.ucladining;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.vanshgandhi.ucladining.dummy.DummyContent;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class DiningHallMenuFragment extends ListFragment
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

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";


    private ArrayList<String> foodTitles = new ArrayList<>();


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static DiningHallMenuFragment newInstance()
    {
        DiningHallMenuFragment fragment = new DiningHallMenuFragment();
        return fragment;
    }

    public DiningHallMenuFragment()
    {
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_dining_hall_menu, container, false);
        Ion.with(this)
                .load("https://api.import.io/store/data/b69025cd-7b2e-4f66-9bad-e82675963a67/_query?input/webpage/url=http%3A%2F%2Fmenu.ha.ucla.edu%2Ffoodpro%2Fdefault.asp%3Fdate%3D11%252F2%252F2015&_user=22403bda-b7eb-4c87-904a-78de1838426c&_apikey=22403bdab7eb4c87904a78de1838426c6e7d3048637d4bbae71657eb53b31c47d987e5e1cb53206a5fac41e1b938b1abcbb0ed68909ebb9d9e75447cc09546577d6725bd3f2bee95e827ee604fa7d84c")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>()
                {
                    @Override
                    public void onCompleted(Exception e, JsonObject result)
                    {
                        if(e != null) {
                            return;
                        }
                        processList(result);
                    }
                });
//        setListAdapter(new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
//                android.R.layout.simple_list_item_2, android.R.id.text1, DummyContent.ITEMS));
//        ArrayList<FoodItem> items = new ArrayList<>();
//        items.add(new FoodItem("Test 1"));
//        items.add(new FoodItem("Test 2"));
//
//        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.menuList);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(layoutManager);
//
//        MenuAdapter menuAdapter = new MenuAdapter(items);
//
//        recyclerView.setAdapter(menuAdapter);

        return rootView;
    }

    public void processList(JsonObject result)
    {
        JsonArray jsonArray = result.getAsJsonArray("results");
        for(JsonElement jsonElement : jsonArray)
        {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            if(jsonObject.has("food_items/_text"))
            {
                if(jsonObject.get("food_items/_text").isJsonArray()) {
                    Log.v("TAG", jsonObject.toString());
                    JsonArray food = jsonObject.get("food_items/_text").getAsJsonArray();
                    for (JsonElement item : food) {
                        String title = item.getAsString();
                        foodTitles.add(title);
                    }
                }
                else {
                    try {
                        String title = jsonObject.get("food_items/_text").getAsString();
                        foodTitles.add(title);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        setListAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_2, android.R.id.text1, foodTitles));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);

        if (mListener != null) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }

        Intent intent = new Intent(getContext(), FoodDetailActivity.class);
        startActivity(intent);
        //TODO: Proper Transitions
        // ActivityCompat.startActivity(getActivity(), intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());

        /**
         * TODO: When a menu item is selected, a new activity must be launched, containing details
         * TODO: about that food item (identfied through a URL?).
         * TODO: TBD is how information will be passed from fragment to Activity
         * TODO: Probably through some sort of identifier and the actual activity will get details
         * TODO: such as Nutrition info, A Picture, and Ingredients
         */
        //FoodItem item = getListAdapter().getItem(position);
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }
}