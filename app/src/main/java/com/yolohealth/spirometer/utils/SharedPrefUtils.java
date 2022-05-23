package com.yolohealth.spirometer.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.yolohealth.spirometer.widget.AppConstant;

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


    public static void setDeviceAddress(Context ctx, String device_name, String device_address) {
        SharedPreferences prefs = ctx.getSharedPreferences(AppConstant.DEVICE_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(device_name, device_address);
        editor.commit();
    }

    public static String getDeviceAddress(Context ctx, String device_name) {
        SharedPreferences prefs = ctx.getSharedPreferences(AppConstant.DEVICE_PREF, MODE_PRIVATE);
        return prefs.getString(device_name, "");
    }



   /* public static void setUserId(Context ctx, int userId) {
        SharedPreferences prefs = ctx.getSharedPreferences(AppConstant.PERSONAL_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(AppConstant.USER_ID, userId);
        editor.apply();
    }


    public static int getUserId(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences(AppConstant.PERSONAL_PREF, Context.MODE_PRIVATE);
        return prefs.getInt(AppConstant.USER_ID, 0);
    }*/


    public static int getKioskId(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences(AppConstant.PERSONAL_PREF, Context.MODE_PRIVATE);
        return prefs.getInt(AppConstant.KIOSK_ID, 0);
    }


    public static void setKioksId(Context ctx, int kiosk) {
        SharedPreferences prefs = ctx.getSharedPreferences(AppConstant.PERSONAL_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(AppConstant.KIOSK_ID, kiosk);
        editor.commit();
    }


    public static void setTestType(Context ctx, String test_type, String test_value) {

        Log.d("SPD", "setting " + test_type + " to " + test_value);
        SharedPreferences prefs = ctx.getSharedPreferences(AppConstant.DEVICE_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(test_type, test_value);
        editor.commit();
    }

    public static String getTestType(Context ctx, String test_type) {
        SharedPreferences prefs = ctx.getSharedPreferences(AppConstant.DEVICE_PREF, MODE_PRIVATE);
        return prefs.getString(test_type, "");
    }
}
