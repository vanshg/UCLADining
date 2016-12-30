package com.vanshgandhi.bruinmenu;

import android.content.Context;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
        final View rootView = inflater.inflate(R.layout.fragment_hours, container, false);
        gridView = (GridView)rootView.findViewById(R.id.gridview);
       // test = (TextView) rootView.findViewById(R.id.textView);
        Calendar rightNow = mainActivity.getCurrentCal();
        String url = "https://bruinmenu.herokuapp.com/hours?date=";
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        String param1 = df.format(rightNow.getTime());
        url += param1;

        Log.d("test", "The url is " + url);
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
                Log.d("Test", "Yay! Got response: " + hoursText);

                parseHourText();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // This code will always run on the UI thread, therefore is safe to modify UI elements.
                        gridView.setAdapter(new hoursAdapter(getActivity(), HoursFragment.this));
                        // test.setText(hoursText);
                    }
                });
            }
        });
        return rootView;
    }

    private void parseHourText() {
        for (int i = 0; i < restaurants.length; i++) {
            String pattern = "\"" + restaurants[i] + "\",\"breakfast\":\"(.*?)\",\"lunch\":\"(.*?)\",\"dinner\":\"(.*?)\",\"late_night\":\"(.*?)\"";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(hoursText);

            if (m.find()) {
                restaurants[i] = restaurants[i] + "\n" + "Breakfast: " + m.group(1) + "\nLunch: " + m.group(2) + "\nDinner: " + m.group(3) + "\nLate Night: " + m.group(4);
            } else
                restaurants[i] = null;

            //Log.d("Testing", "The value of the restaurants is: " + restaurants[i]);
        }
    }

    public String getRestaurant(int pos)
    {
        return restaurants[pos];
    }

}