package com.vanshgandhi.bruinmenu;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


/**
 * Created by Sahil on 12/12/2016.
 */

public class HoursFragment extends Fragment {

    private MainActivity mainActivity;
    private Calendar rightNow;

    private TextView test;

    public HoursFragment(){}

    public static HoursFragment newInstance(){
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootview = inflater.inflate(R.layout.fragment_hours, container, false);
        test = (TextView) rootview.findViewById(R.id.textView);
        rightNow = mainActivity.getCurrentCal();
        String url = "https://salty-shelf-63361.herokuapp.com/hours";
        String charset = "UTF-8";
        SimpleDateFormat df = new SimpleDateFormat("MM/DD/yyyy", Locale.US);
        String param1 = df.format(rightNow.getTime());
        StringBuilder content = new StringBuilder();

        try {
            (HttpURLConnection) connection = (HttpURLConnection) new URL(url + "?" + param1).openConnection();
            connection.setRequestProperty("Accept-Charset", charset);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null)
            {
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        test.setText(content);

        return rootview;
    }
}
