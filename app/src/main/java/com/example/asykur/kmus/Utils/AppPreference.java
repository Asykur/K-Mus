package com.example.asykur.kmus.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.asykur.kmus.R;

public class AppPreference {

    private SharedPreferences preferences;
    private Context context;

    public AppPreference(Context context){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    public void setFirstRun (Boolean flag){
        SharedPreferences.Editor editor = preferences.edit();
        String key = context.getString(R.string.first_run);
        editor.putBoolean(key,flag);
        editor.apply();
    }

    public Boolean getFirstRun(){
        String key = context.getResources().getString(R.string.first_run);
        return preferences.getBoolean(key,true);
    }
}
