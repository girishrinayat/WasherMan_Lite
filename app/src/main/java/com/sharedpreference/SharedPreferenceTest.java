package com.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceTest {

    private static Context context;
    public final static String PREFS_NAME = "profex_prefs";

    public SharedPreferenceTest(Context context){

        this.context=context;
    }
    public static void setInt(String key, int value) {

        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.apply();

    }
    public static int getInt(String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(key, 0);
    }
    public static void setBool(String key, boolean value) {

        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.apply();

    }
    public static boolean getBool(String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(key, false);
    }

    public static void setStr(String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getStr(String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(key,"DNF");
    }

    public static  void logouts(){

        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
         editor.clear();
        editor.commit();

    }

}
