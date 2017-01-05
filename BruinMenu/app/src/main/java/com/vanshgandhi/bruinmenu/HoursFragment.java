package com.vanshgandhi.bruinmenu;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Sahil on 12/12/2016.
 */

public class HoursFragment extends Fragment {

    private MainActivity mainActivity;
    private GridView gridView;
    private TextView test;
    private String hoursText;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Calendar rightNow;
    private Boolean restoredSavedPreferences = false;

    private String[] restaurants = {"Bruin Café", "Bruin Plate", "Covel", "Café 1919", "De Neve", "De Neve Grab 'N Go", "FEAST at Rieber", "Rendezvous"};

    public HoursFragment() {
    }

    public static HoursFragment newInstance() {
        return new HoursFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sharedPreferences.edit();

        final View rootView = inflater.inflate(R.layout.fragment_hours, container, false);
        gridView = (GridView) rootView.findViewById(R.id.gridview);
        rightNow = mainActivity.getCurrentCal();
        String url = "https://bruinmenu.herokuapp.com/hours?date=";
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        String param1 = df.format(rightNow.getTime());
        url += param1;
        restoreSavedPreferences();
        if (!restoredSavedPreferences) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).get().build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("Test", "Oh noez! Error!!", e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    hoursText = response.body().string();
                    response.body().close();
                    parseHourText();

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // This code will always run on the UI thread, therefore is safe to modify UI elements.
                            gridView.setAdapter(new hoursAdapter(getActivity(), HoursFragment.this));
                        }
                    });
                }
            });
        } else
            gridView.setAdapter(new hoursAdapter(getActivity(), HoursFragment.this));
//        Log.d("test", "The url is " + url);
        return rootView;
    }

    private void parseHourText() {
        for (int i = 0; i < restaurants.length; i++) {
            String pattern = "\"" + restaurants[i] + "\",\"breakfast\":\"(.*?)\",\"lunch\":\"(.*?)\",\"dinner\":\"(.*?)\",\"late_night\":\"(.*?)\"";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(hoursText);

            if (m.find()) {
                restaurants[i] = "<strong>" + restaurants[i] + "<strong>" + "\n" + "Breakfast: " + m.group(1) + "\nLunch: " + m.group(2) + "\nDinner: " + m.group(3) + "\nLate Night: " + m.group(4);
            } else
                restaurants[i] = null;
        }
        editor.putString("BCafe" + rightNow.get(Calendar.DAY_OF_WEEK), restaurants[0]).apply();
        editor.putString("BPlate" + rightNow.get(Calendar.DAY_OF_WEEK), restaurants[1]).apply();
        editor.putString("Covel" + rightNow.get(Calendar.DAY_OF_WEEK), restaurants[2]).apply();
        editor.putString("Cafe1919" + rightNow.get(Calendar.DAY_OF_WEEK), restaurants[3]).apply();
        editor.putString("DeNeve" + rightNow.get(Calendar.DAY_OF_WEEK), restaurants[4]).apply();
        editor.putString("Grab" + rightNow.get(Calendar.DAY_OF_WEEK), restaurants[5]).apply();
        editor.putString("Feast" + rightNow.get(Calendar.DAY_OF_WEEK), restaurants[6]).apply();
        editor.putString("Rendez" + rightNow.get(Calendar.DAY_OF_WEEK), restaurants[7]).apply();
        editor.putBoolean("savedOn" + rightNow.get(Calendar.DAY_OF_WEEK), true).apply();
    }

    public String getRestaurant(int pos) {
        return restaurants[pos];
    }

    private void restoreSavedPreferences() {
        restaurants[0] = sharedPreferences.getString("BCafe" + rightNow.get(Calendar.DAY_OF_WEEK), restaurants[0]);
        restaurants[1] = sharedPreferences.getString("BPlate" + rightNow.get(Calendar.DAY_OF_WEEK), restaurants[1]);
        restaurants[2] = sharedPreferences.getString("Covel" + rightNow.get(Calendar.DAY_OF_WEEK), restaurants[2]);
        restaurants[3] = sharedPreferences.getString("Cafe1919" + rightNow.get(Calendar.DAY_OF_WEEK), restaurants[3]);
        restaurants[4] = sharedPreferences.getString("DeNeve" + rightNow.get(Calendar.DAY_OF_WEEK), restaurants[4]);
        restaurants[5] = sharedPreferences.getString("Grab" + rightNow.get(Calendar.DAY_OF_WEEK), restaurants[5]);
        restaurants[6] = sharedPreferences.getString("Feast" + rightNow.get(Calendar.DAY_OF_WEEK), restaurants[6]);
        restaurants[7] = sharedPreferences.getString("Rendez" + rightNow.get(Calendar.DAY_OF_WEEK), restaurants[7]);
        restoredSavedPreferences = sharedPreferences.getBoolean("savedOn" + rightNow.get(Calendar.DAY_OF_WEEK), false);
    }

}