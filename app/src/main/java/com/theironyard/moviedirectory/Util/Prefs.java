package com.theironyard.moviedirectory.Util;

import android.app.Activity;
import android.content.SharedPreferences;

public class Prefs {
    SharedPreferences sharedPreferences;

    public Prefs(Activity activity) {
        sharedPreferences = activity.getPreferences(activity.MODE_PRIVATE);
    }

    public void setShared(String search) {
        sharedPreferences.edit().putString("search", search).commit();
    }

    public String getSearch() {
        return sharedPreferences.getString("search", "Batman");
    }
}
