package com.yolohealth.lunngmonitor.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.yolohealth.lunngmonitor.widget.AppConstant;

public class SharedPrefUtils {

    public static String getToken(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences(AppConstant.PERSONAL_PREF, Context.MODE_PRIVATE);
        return prefs.getString(AppConstant.TOKEN, "");
    }


    public static void setToken(Context ctx, String token) {
        SharedPreferences prefs = ctx.getSharedPreferences(AppConstant.PERSONAL_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(AppConstant.TOKEN, token);
        editor.apply();
    }

    public static String getPhone(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences(AppConstant.PERSONAL_PREF, Context.MODE_PRIVATE);
        return prefs.getString(AppConstant.PHONE, "");
    }


    public static void setPhone(Context ctx, String phone) {
        SharedPreferences prefs = ctx.getSharedPreferences(AppConstant.PERSONAL_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(AppConstant.PHONE, phone);
        editor.apply();
    }


    public static String getUsername(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences(AppConstant.PERSONAL_PREF, Context.MODE_PRIVATE);
        return prefs.getString(AppConstant.USERNAME, "");
    }


    public static void setUsername(Context ctx, String username) {
        SharedPreferences prefs = ctx.getSharedPreferences(AppConstant.PERSONAL_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(AppConstant.USERNAME, username);
        editor.apply();
    }


    public static String getUserpic(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences(AppConstant.PERSONAL_PREF, Context.MODE_PRIVATE);
        return prefs.getString(AppConstant.PIC, "");
    }


    public static void setUserpic(Context ctx, String pic) {
        SharedPreferences prefs = ctx.getSharedPreferences(AppConstant.PERSONAL_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(AppConstant.PIC, pic);
        editor.apply();
    }


    public static String getUsermail(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences(AppConstant.PERSONAL_PREF, Context.MODE_PRIVATE);
        return prefs.getString(AppConstant.MAIL, "");
    }


    public static void setUsermail(Context ctx, String mail) {
        SharedPreferences prefs = ctx.getSharedPreferences(AppConstant.PERSONAL_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(AppConstant.MAIL, mail);
        editor.apply();
    }


    public static boolean isLoggedIn(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences(AppConstant.PREF, Context.MODE_PRIVATE);
        return prefs.getBoolean(AppConstant.IsLoggedIn, false);
    }


    public static void setLoggedIn(Context ctx, boolean state) {
        SharedPreferences prefs = ctx.getSharedPreferences(AppConstant.PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(AppConstant.IsLoggedIn, state);
        editor.apply();
    }

    public static String getProfileId(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences(AppConstant.PERSONAL_PREF, Context.MODE_PRIVATE);
        return prefs.getString(AppConstant.PROFILE_ID, "");
    }


    public static void setProfileId(Context ctx, String profile_id) {
        SharedPreferences prefs = ctx.getSharedPreferences(AppConstant.PERSONAL_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(AppConstant.PROFILE_ID, profile_id);
        editor.apply();
    }

 /*   public static ProfileData getProfile(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences(AppConstant.PERSONAL_PREF, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(AppConstant.PROFILE_DATA, "");
        ProfileData obj = gson.fromJson(json, ProfileData.class);
        return obj;
    }

    public static void setProfile(Context ctx, Example profilesItem) {
        SharedPreferences prefs = ctx.getSharedPreferences(AppConstant.PERSONAL_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(profilesItem);
        if (profilesItem != null) {
            editor.putString(AppConstant.PROFILE_DATA, json);
        } else {
            editor.putString(AppConstant.PROFILE_DATA, null);
        }

        editor.apply();
    }*/
}
