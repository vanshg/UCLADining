package com.vanshgandhi.ucladining.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.vanshgandhi.ucladining.API;
import com.vanshgandhi.ucladining.Activities.MainActivity;
import com.vanshgandhi.ucladining.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HoursFragment extends Fragment implements Callback {
    private MainActivity mainActivity = null;

    private final String breakfastJsonKey = "breakfast";
    private final String lunchJsonKey     = "lunch";
    private final String dinnerJsonKey    = "dinner";
    private final String latenightJsonKey = "late_night";
    Map<Integer, String> halls;
    TableLayout          hallHoursTable;
    TableLayout          cafeHoursTable;
    private API api;

    public static HoursFragment newInstance() {
        return new HoursFragment();
    }

    public HoursFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hours, container, false);
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.hours));
        mainActivity.setSupportActionBar(toolbar);
        halls = new HashMap<>(8);
        halls.put(2, "Bruin Café");
        halls.put(3, "Bruin Plate");
        halls.put(4, "Café 1919");
        halls.put(5, "Covel");
        halls.put(6, "De Neve");
        halls.put(7, "De Neve Lunch on the Go");
        halls.put(8, "FEAST");
        halls.put(9, "Rendezvous");
        hallHoursTable = (TableLayout) rootView.findViewById(R.id.hallHours);
        cafeHoursTable = (TableLayout) rootView.findViewById(R.id.cafeHours);
        api = API.getInstance();
        refresh();
        return rootView;
    }

    public void refresh() {
        if (api == null) {
            api = API.getInstance();
        }
        try {
            api.loadHours(mainActivity.getCalendarFromSelectedDate(), this);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            showError();
        }
    }

    @Override
    public void onFailure(Call call, IOException e) {
        showError();
        e.printStackTrace();
    }

    @Override
    public void onResponse(Call call, final Response response) throws IOException {
        if (!response.isSuccessful()) {
            showError();
            return;
        }
        final String responseData = response.body().string();
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                populateHours(responseData);
            }
        });

    }

    public void populateHours(String response) {
        try {
            JSONArray array = new JSONObject(response).getJSONArray("results");
            for (int i = 1; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                //^[0-9][0-9]?:[0-9][0-9][ap]m - [0-9][0-9]?:[0-9][0-9][ap]m
                String breakfast = object.getString(breakfastJsonKey);
                String lunch = object.getString(lunchJsonKey);
                String dinner = object.getString(dinnerJsonKey);
                String night = object.getString(latenightJsonKey);
                int breakfastIndex = breakfast.indexOf("(");
                int lunchIndex = lunch.indexOf("(");
                int dinnerIndex = dinner.indexOf("(");
                int nightIndex = night.indexOf("(");
                if (breakfastIndex != -1) {
                    breakfast = breakfast.substring(0, breakfastIndex);
                }
                if (lunchIndex != -1) {
                    lunch = lunch.substring(0, lunchIndex);
                }
                if (dinnerIndex != -1) {
                    dinner = dinner.substring(0, dinnerIndex);
                }
                if (nightIndex != -1) {
                    night = night.substring(0, nightIndex);
                }
                switch (i) {
                    case 1: //Bcafe
                        setMealPeriodHoursText(cafeHoursTable, 3, breakfast, lunch, dinner, night);
                        break;
                    case 2: //Bplate
                        setMealPeriodHoursText(hallHoursTable, 1, breakfast, lunch, dinner, night);
                        break;
                    case 3: //1919
                        setMealPeriodHoursText(cafeHoursTable, 2, breakfast, lunch, dinner, night);
                        break;
                    case 4: //Covel
                        setMealPeriodHoursText(hallHoursTable, 3, breakfast, lunch, dinner, night);
                        break;
                    case 5: //De Neve
                        setMealPeriodHoursText(hallHoursTable, 4, breakfast, lunch, dinner, night);
                        break;
                    case 6: //DN On the Go
                        setMealPeriodHoursText(cafeHoursTable, 4, breakfast, lunch, dinner, night);
                        break;
                    case 7:
                        setMealPeriodHoursText(hallHoursTable, 2, breakfast, lunch, dinner, night);
                        break;
                    case 8:
                        setMealPeriodHoursText(cafeHoursTable, 1, breakfast, lunch, dinner, night);
                }
            }
        } catch (JSONException | IllegalStateException e) {
            e.printStackTrace();
            showError();
        }
    }

    public void setMealPeriodHoursText(TableLayout table, int index, String breakfast, String lunch,
                                       String dinner, String lateNight) {
        for (int j = 1; j < table.getChildCount(); j++) {
            TableRow row = (TableRow) table.getChildAt(j);
            TextView textView = (TextView) row.getChildAt(index);
            switch (j) {
                case 1: //Breakfast
                    textView.setText(breakfast);
                    break;
                case 2: //Lunch
                    textView.setText(lunch);
                    break;
                case 3: //dinner
                    textView.setText(dinner);
                    break;
                case 4: //late night
                    textView.setText(lateNight);
                    break;
            }
        }
    }

    public void showError() {
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mainActivity, "Error in loading Hours", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
