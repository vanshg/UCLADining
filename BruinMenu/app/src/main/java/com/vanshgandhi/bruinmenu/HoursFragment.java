package com.vanshgandhi.bruinmenu;

import android.content.Context;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
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
    private Calendar rightNow;

    private TextView test;

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
        test = (TextView) rootView.findViewById(R.id.textView);
        rightNow = mainActivity.getCurrentCal();
        String url = "https://salty-shelf-63361.herokuapp.com/hours";
        //String charset = "UTF-8";
        //SimpleDateFormat df = new SimpleDateFormat("MM/DD/yyyy", Locale.US);
        //String param1 = df.format(rightNow.getTime());

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://salty-shelf-63361.herokuapp.com/hours").get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("Test", "Oh noez! Error!!", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String stringResponse = response.body().string();
                response.body().close();
                Log.v("Test", "Yay! Got response: " + stringResponse);
            }
        });
        return rootView;
    }
}

//        StringBuilder content = new StringBuilder();
//
//        try {
//            HttpURLConnection connection = (HttpURLConnection) (new URL(url)).openConnection();
//            //connection.setRequestProperty("Accept-Charset", charset);
//            connection.setReadTimeout(10000);
//            connection.connect();
//
//            InputStream in = connection.getInputStream();
//
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
//
//            for (String line; (line = bufferedReader.readLine()) != null; ) {
//                content.append(line);
//            }
//            in.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        test.setText(content.toString());