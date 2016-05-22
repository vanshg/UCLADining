package com.vanshgandhi.ucladining;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by vanshgandhi on 4/10/16.
 */
public class API {
    private static API instance = null;
    private OkHttpClient client;
    //All relevant urls
    private static final String baseUrl      = "https://api.import.io/store/connector/";
    private static final String uclaBaseUrl  = "http%3A%2F%2Fmenu.ha.ucla.edu%2Ffoodpro%2F";
    private static final String twoMeal      = "f20fc91a-caf1-409c-9322-efa0ef770223/_query?input/webpage/url=";
    private static final String threeMeal    = "d90c4352-d57c-4773-9064-4af17341beef/_query?input/webpage/url=";
    private static final String ingredients  = "eacba959-1feb-4119-9388-bbb5cd4fdfff/_query?input/webpage/url=";
    //%s is a String format expression
    private static final String hoursWebpage = "https://secure5.ha.ucla.edu/restauranthours/dining-hall-hours-by-day.cfm?serviceDate=%s";  //serviceDate=04%2F18%2F2016
    //%s is a String format expression
    private static final String hoursApi     = "https://api.import.io/store/connector/d396fb12-1087-4215-b642-93082310c824/_query?input=webpage/url:%s&&_apikey=22403bdab7eb4c87904a78de1838426c6e7d3048637d4bbae71657eb53b31c47d987e5e1cb53206a5fac41e1b938b1abcbb0ed68909ebb9d9e75447cc09546577d6725bd3f2bee95e827ee604fa7d84c";
    //%d,%s is a String format expression
    //TODO: -->scroll a little                                                                                                                                                                                                                           vvv 2016 should not be hardcode
    private static final String twoMealUrl = "https://api.import.io/store/connector/f20fc91a-caf1-409c-9322-efa0ef770223/_query?input=webpage/url:http%%3A%%2F%%2Fmenu.ha.ucla.edu%%2Ffoodpro%%2Fdefault.asp%%3Flocation%%3D%s%%26date%%3D5%%252F%d%%252F2016&&_apikey=22403bdab7eb4c87904a78de1838426c6e7d3048637d4bbae71657eb53b31c47d987e5e1cb53206a5fac41e1b938b1abcbb0ed68909ebb9d9e75447cc09546577d6725bd3f2bee95e827ee604fa7d84c";
    //%d,%s is a String format expression
    //TODO: -->scroll a little                                                                                                                                                                                                                              vvv 2016 should not be hardcode
    private static final String threeMealUrl = "https://api.import.io/store/connector/d90c4352-d57c-4773-9064-4af17341beef/_query?input=webpage/url:http%%3A%%2F%%2Fmenu.ha.ucla.edu%%2Ffoodpro%%2Fdefault.asp%%3Flocation%%3D%s%%26date%%3D5%%252F%d%%252F2016&&_apikey=22403bdab7eb4c87904a78de1838426c6e7d3048637d4bbae71657eb53b31c47d987e5e1cb53206a5fac41e1b938b1abcbb0ed68909ebb9d9e75447cc09546577d6725bd3f2bee95e827ee604fa7d84c";

    private static final String fullMenu     = "default.asp%%3Flocation%%3D%s%%26date%%3D%d%%252F%d%%252F%d";
    private static final String apiKey       = "&_user=22403bda-b7eb-4c87-904a-78de1838426c&_apikey=22403bdab7eb4c87904a78de1838426c6e7d3048637d4bbae71657eb53b31c47d987e5e1cb53206a5fac41e1b938b1abcbb0ed68909ebb9d9e75447cc09546577d6725bd3f2bee95e827ee604fa7d84c";

    //TODO: Cache Responses
    protected API() {
        client = new OkHttpClient();
    }

    public static API getInstance() {
        if (instance == null) {
            instance = new API();
        }
        return instance;
    }

    public void loadSummaryForDay() {

    }

    public void loadTwoMealHall(Callback responseCallback, String hallCode, int day, int month, int year)
            throws UnsupportedEncodingException {
        String url = String.format(twoMealUrl, hallCode, month, day);
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(responseCallback);
    }

    public void loadThreeMealHall(Callback responseCallback, String hallCode, int day, int month, int year)
            throws UnsupportedEncodingException {
//        String fullFullMenu = String.format(fullMenu, hallCode, month, day, year);
//        String url = baseUrl + threeMeal + uclaBaseUrl + fullFullMenu + apiKey;
        String url = String.format(threeMealUrl, hallCode, month, day);
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(responseCallback);
    }

    public void loadNutritionAndIngredients() {

    }

    public void loadHours(Calendar cal, Callback responseCallback)
            throws UnsupportedEncodingException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM'%2F'dd'%2F'yyyy'#'",
                Locale.US);
        String date = simpleDateFormat.format(cal.getTime());
        String fullHoursWebpage = URLEncoder.encode(String.format(hoursWebpage, date), "UTF-8");
        String url = String.format(hoursApi,
                fullHoursWebpage); //Uses %s with String.format to replace the %s with the fullHoursWebpage url
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(responseCallback);
    }

    public void loadQuickService() {

    }
}