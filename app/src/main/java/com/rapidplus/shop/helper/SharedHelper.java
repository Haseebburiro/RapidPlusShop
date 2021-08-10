package com.rapidplus.shop.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedHelper {

    private static SharedPreferences sharedPreferences;
    private static String PREF_NAME = "tomoeats_restaurant";

    public static void putKey(Context context, String Key, String Value) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Key, Value);
        editor.apply();
    }

    public static String getKey(Context contextGetKey, String Key) {
        sharedPreferences = contextGetKey.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Key, "");

    }

    public static String getKey(Context contextGetKey, String Key, String defVal) {
        sharedPreferences = contextGetKey.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Key, defVal);
    }

    public static void clearSharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }
}
