package com.yolohealth.lunngmonitor.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewParent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textfield.TextInputLayout;
import com.mobsandgeeks.saripaar.ValidationError;
import com.yolohealth.lunngmonitor.LungMonitorApp;
import com.yolohealth.lunngmonitor.R;
import com.yolohealth.lunngmonitor.widget.DelayedProgressDialog;

import java.io.File;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Common_Utils {
    public static DelayedProgressDialog progressDialog;
    private static final String TAG = Common_Utils.class.getSimpleName();
    public static Toast toast;
    private static long lastClickTime = 0;

    public static boolean isNotNullOrEmpty(String string) {

        if (string != null
                && !string.equalsIgnoreCase("null")
                && !string.isEmpty()
                //&& !str.contains("null")
                && !string.equalsIgnoreCase("")
                && !string.equalsIgnoreCase(" ")) {

            return true;
        } else {
            return false;
        }
    }

//    public static boolean isNetworkAvailable() {
//
////        boolean haveConnectedWifi = false;
////        boolean haveConnectedMobile = false;
////
////        ConnectivityManager cm = (ConnectivityManager) YolohealthHomeApp.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
////        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
////        for (NetworkInfo ni : netInfo) {
////            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
////                if (ni.isConnected())
////                    haveConnectedWifi = true;
////            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
////                if (ni.isConnected())
////                    haveConnectedMobile = true;
////        }
////        return haveConnectedWifi || haveConnectedMobile;
//
////        ConnectivityManager cm = (ConnectivityManager) YolohealthHomeApp.getAppContext()
////                .getSystemService(Context.CONNECTIVITY_SERVICE);
////        NetworkInfo netInfo = cm.getActiveNetworkInfo();
////        if (netInfo != null && netInfo.isConnected()) {
////            return true;
////        }
////        return false;
//
//        ConnectivityManager connectivityManager = (ConnectivityManager) YolohealthHomeApp.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//        Network nw = connectivityManager.getActiveNetwork();
//        if (nw == null) return false;
//        NetworkCapabilities actNw = connectivityManager.getNetworkCapabilities(nw);
//        return actNw != null && (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH));
//    }

    public static boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager) LungMonitorApp.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return true;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        return true;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                        return true;
                    }
                }
            } else {

                try {
                    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                    if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                        Log.i("update_statut", "Network is available : true");
                        return true;
                    }
                } catch (Exception e) {
                    Log.i("update_statut", "" + e.getMessage());
                }
            }
        }
        Log.i("update_statut", "Network is available : FALSE ");
        return false;
    }

    public static boolean isBluetoothEnabled() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return mBluetoothAdapter.isEnabled();
    }

    public static float dp2px(Context context, float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

    public static float sp2px(Context context, float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                context.getResources().getDisplayMetrics());
    }

    public static float px2dp(Context context, float px) {
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static SpannableString getUnderline(Context context, String str_temp) {

        TextPaint tp = new TextPaint();

        tp.linkColor = context.getResources().getColor(R.color.colorAccent);
        UnderlineSpan us = new UnderlineSpan();
        us.updateDrawState(tp);

        SpannableString content = new SpannableString(str_temp);
        content.setSpan(us, 0, str_temp.length(), 0);

        return content;
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (!isNotNullOrEmpty(target.toString())) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Method to convert MM to MMM
     */
    public static String getMonthName(int month) {
        return new DateFormatSymbols().getMonths()[month - 1];
    }

    public static Date stringToDate(String dtStart) {
        //  String dtStart = "2010-10-15T09:27:37Z";
        Date dateFinal = null;
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
        try {
            dateFinal = format.parse(dtStart);
            //  System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFinal;
    }

    public static Date stringToDateTime24Hrs(Date dtStart) {
        //  String dtStart = "2010-10-15T09:27:37Z";
        Date dateFinal = null;
        // SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
        SimpleDateFormat format1 = new SimpleDateFormat("MM-dd-yyyy hh:mm aa");

        try {
            //  dateFinal = format.parse(dtStart);
            //  dateFinal = format1.parse(dateFinal.toString());
            String date = format1.format(dtStart);
            dateFinal = format1.parse(date);
            //  System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFinal;
    }


    public static Date stringToDatee(String dtStart) {
        //  String dtStart = "2010-10-15T09:27:37Z";
        Date dateFinal = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            dateFinal = format.parse(dtStart);
            //  System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFinal;
    }

    public static Date stringToDateValidation(String dtStart) {
        //  String dtStart = "2010-10-15T09:27:37Z";
        Date dateFinal = null;
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        try {
            dateFinal = format.parse(dtStart);
            //  System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFinal;
    }

    public static String formateDate(String dateString) throws ParseException {
        // String dateString = "2015-07-16 17:07:21";
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");

        // use SimpleDateFormat to define how to PARSE the INPUT
        Date date = sdf.parse(dateString);

        // at this point you have a Date-Object with the value of
        // 1437059241000 milliseconds
        // It doesn't have a format in the way you think

        // use SimpleDateFormat to define how to FORMAT the OUTPUT
        // System.out.println("+++1 " + sdf.format(date));

        sdf = new SimpleDateFormat("dd/MM/yyyy");
        //  System.out.println("+++1 2" + sdf.format(date));
        return sdf.format(date);
        // ....
    }

    public static String parseServerDate(String dateString) throws ParseException {
        // String dateString = "2015-07-16 17:07:21";
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");

        // use SimpleDateFormat to define how to PARSE the INPUT
        Date date = sdf.parse(dateString);

        // at this point you have a Date-Object with the value of
        // 1437059241000 milliseconds
        // It doesn't have a format in the way you think

        // use SimpleDateFormat to define how to FORMAT the OUTPUT
        //  System.out.println("+++1 " + sdf.format(date));

        sdf = new SimpleDateFormat("MM/dd/yyyy");
        //  System.out.println("+++1 2" + sdf.format(date));
        return sdf.format(date);
        // ....
    }

    public static String formateMM_DD_YYYY(String dateString) throws ParseException {
        // String dateString = "2015-07-16 17:07:21";
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm a");

        // use SimpleDateFormat to define how to PARSE the INPUT
        Date date = sdf.parse(dateString);

        // at this point you have a Date-Object with the value of
        // 1437059241000 milliseconds
        // It doesn't have a format in the way you think

        // use SimpleDateFormat to define how to FORMAT the OUTPUT
        // System.out.println("+++1 " + sdf.format(date));

        sdf = new SimpleDateFormat("MM-dd-yyyy");
        //  System.out.println("+++1 2" + sdf.format(date));
        return sdf.format(date);
        // ....
    }

    public static String formateServerDate(String dateString) throws ParseException {
        // String dateString = "2015-07-16 17:07:21";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        // use SimpleDateFormat to define how to PARSE the INPUT
        Date date = sdf.parse(dateString);

        // at this point you have a Date-Object with the value of
        // 1437059241000 milliseconds
        // It doesn't have a format in the way you think

        // use SimpleDateFormat to define how to FORMAT the OUTPUT
        //  System.out.println("+++1 " + sdf.format(date));

        sdf = new SimpleDateFormat("MM/dd/yyyy");
        //  System.out.println("+++1 2" + sdf.format(date));
        return sdf.format(date);
        // ....
    }


    public static String getTimeFromDate(String dateString) throws ParseException {
        // String dateString = "2015-07-16 17:07:21";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");

        // use SimpleDateFormat to define how to PARSE the INPUT
        Date date = sdf.parse(dateString);

        // at this point you have a Date-Object with the value of
        // 1437059241000 milliseconds
        // It doesn't have a format in the way you think

        // use SimpleDateFormat to define how to FORMAT the OUTPUT
        // System.out.println("+++1 " + sdf.format(date));

        sdf = new SimpleDateFormat("HH:mm:ss a");
        // System.out.println("+++1 2" + sdf.format(date));
        return sdf.format(date);
        // ....
    }

    public static Calendar getCalendar(String dates) {
        SimpleDateFormat sdf1 = new SimpleDateFormat();
        sdf1.applyPattern("MM-dd-yyyy");
        Date date = null;
        // System.out.println("++++ date " + calendarEventsArrayList.get(j));
        try {
            date = sdf1.parse(dates);
        } catch (ParseException e) {
            System.out.println("++++ e " + e.getMessage());
            e.printStackTrace();
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar;
    }

    public static String parseSelectedDate(String dates) {


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // use SimpleDateFormat to define how to PARSE the INPUT
        Date date = null;
        try {
            date = sdf.parse(dates);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // at this point you have a Date-Object with the value of
        // 1437059241000 milliseconds
        // It doesn't have a format in the way you think

        // use SimpleDateFormat to define how to FORMAT the OUTPUT
        // System.out.println("+++1 " + sdf.format(date));

        sdf = new SimpleDateFormat("MM-dd-yyyy");
        // System.out.println("+++1 2" + sdf.format(date));
        return sdf.format(date);

    }
/*
    public static Calendar getCalendarDate(Date dates){
        SimpleDateFormat sdf1 = new SimpleDateFormat();
        sdf1.applyPattern("yyyy-MM-dd");
        java.util.Date date = null;
        //System.out.println("++++ date " + calendarEventsArrayList.get(j).getStart_date());
        try {
            date = sdf1.parse(dates.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar;
    }*/

    public static long getStringToLong(String startDate) {
        // System.out.println("+++++++ startDate " + startDate);
        SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy");
        long milliseconds = 0;
        try {
            Date d = f.parse(startDate);
            milliseconds = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return milliseconds;
    }

    public static String getFirstAndLastDayOfMonth(String formattedDate, int yearpart, int monthPart, int dateDay) {
        Calendar cal = Calendar.getInstance();
        cal.set(yearpart, monthPart, dateDay);
        // System.out.println(" ++++ Calendar.DATE " + Calendar.DATE);
        int res = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        // System.out.println("Today's Date = " + cal.getTime());
        //  System.out.println("Last Date of the current month = " + res);

        String[] split = formattedDate.split("/");

        return split[0] + "/" + res + "/" + split[2];

    }

    public static boolean pathValidation(String path) {
        if (path.contains("http://") || path.contains("https://")) {
            return true;
        } else {
            return false;
        }
    }


    public static void getCircularImageFromServer(ImageView imageview, String url, int drawble) {

       /* if (!isNotNullOrEmpty(url)) {
            imageview.setImageResource(drawble);
            return;
        }

        if (!pathValidation(url)) {
            url = RestClient.SERVER_APIURL_IMAGE + url;
        }

        //System.out.println(("image " + url);
        RequestOptions options = new RequestOptions()
                .placeholder(drawble)
                .error(drawble)
                .centerCrop()
                .dontAnimate()
                .priority(Priority.HIGH);

        Glide.with(AthleteFoundryApp.getAppContext())
                .load(url)
                .apply(options)
                .into(imageview);*/

    }

    public static void getCircularImageFromServer(ImageView imageview, String url, Drawable drawble) {

        if (!isNotNullOrEmpty(url)) {
            imageview.setImageDrawable(drawble);
            return;
        }

        //System.out.println(("image " + url);
        RequestOptions options = new RequestOptions()
                .placeholder(drawble)
                .error(drawble)
                .centerCrop()
                .dontAnimate()
                .priority(Priority.HIGH);

        Glide.with(LungMonitorApp.getAppContext())
                .load(url)
                .apply(options)
                .into(imageview);

    }

   /* public static Drawable getTextDrawable(String text) {
        if (!Common_Utils.isNotNullOrEmpty(text))
            text = "";//AppConstant.App_Name;

        TextDrawable drawable = TextDrawable.builder().beginConfig()
                .toUpperCase()
                .width(160)  // width in px
                .height(160) // height in px
                .endConfig()
                .buildRound(getTagName(text), AthleteFoundryApp.getAppContext().getResources().getColor(R.color.primary));

        return drawable;
    }*/

    public static String getTagName(String x) {
        String tempText = "";
        if (isNotNullOrEmpty(x)) {
            String[] myName = x.split(" ");
            for (int i = 0; i < (myName.length > 2 ? 2 : myName.length); i++) {
                String s = myName[i];
                tempText = tempText + String.valueOf(s.charAt(0));
            }
        }
        return tempText;
    }

    public static String getFullName(String fname, String sname) {
        String name = "";
        if (Common_Utils.isNotNullOrEmpty(fname) &&
                Common_Utils.isNotNullOrEmpty(sname)) {
            name = fname + " " + sname;
        } else if (Common_Utils.isNotNullOrEmpty(fname)) {
            name = fname;
        }

        return Common_Utils.toCapitalise(name);
    }

    public static String getTimeData(String hr, String min) {
        String name = "";
        if (Common_Utils.isNotNullOrEmpty(hr) &&
                !hr.equalsIgnoreCase("0") &&
                Common_Utils.isNotNullOrEmpty(min) &&
                !min.equalsIgnoreCase("0")) {
            name = hr + "Hr " + min + "Min";
        } else if (Common_Utils.isNotNullOrEmpty(hr) &&
                !hr.equalsIgnoreCase("0")) {
            name = hr + "Hr";
        } else if (Common_Utils.isNotNullOrEmpty(min) &&
                !min.equalsIgnoreCase("0")) {
            name = min + "Min";
        }

        return name;
    }

    public static String toCamelCase(final String init) {
        if (!isNotNullOrEmpty(init))
            return "";

        final StringBuilder ret = new StringBuilder(init.length());

        for (final String word : init.split(" ")) {
            if (!word.isEmpty()) {
                ret.append(word.substring(0, 1).toUpperCase());
                ret.append(word.substring(1).toLowerCase());
            }
            if (!(ret.length() == init.length()))
                ret.append(" ");
        }

        return ret.toString();
    }

    public static String toCapitalise(String string) {
        if (!isNotNullOrEmpty(string))
            return "";

        string = removeUnderline(string);

        char[] chars = string.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == '\'') { // You can add other chars here
                found = false;
            }
        }
        return String.valueOf(chars);
    }

    public static String removeUnderline(String string) {
        if (isNotNullOrEmpty(string))
            return string.replaceAll("_", " ");
        else
            return "";
    }


    public static boolean isNotFrequentClick() {

        if (SystemClock.elapsedRealtime() - lastClickTime < 500) {
            return false;
        }

        lastClickTime = SystemClock.elapsedRealtime();
        return true;
    }


    public static long getLongDate(String startdate) {
        try {
            String dateString = "30/09/2014";
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm a");
            Date date = sdf.parse(startdate);
            // System.out.println("+++++ test date " + date);
            return date.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long getLongAllDayDate(String startdate) {
        try {
            String dateString = "30/09/2014";
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
            Date date = sdf.parse(startdate);

            return date.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getCurrentTime() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //sdfDate.setTimeZone(getTimeZone());
        Calendar c = Calendar.getInstance();
        //  System.out.println("Current time => " + c.getTime());
        String strDate = sdfDate.format(c.getTime());
        return strDate;
    }

    public static String getCurrentTimeDisplay() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
        //sdfDate.setTimeZone(getTimeZone());
        Calendar c = Calendar.getInstance();
        //  System.out.println("Current time => " + c.getTime());
        return sdfDate.format(c.getTime());
    }

    public static String getDeviceName() {
        return Build.MODEL;
    }

    public static String getDeviceVersion() {
        return Build.VERSION.RELEASE;
    }

    @SuppressLint("MissingPermission")
    public static String getDeviceID() {
        String m_szDevIDShort = "35" + //we make this look like a valid IMEI
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +
                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +
                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +
                Build.USER.length() % 10; //13 digits

        return m_szDevIDShort;
    }

    public static String getAppVersion(Context context) {
        String result = "";

        try {
            result = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0)
                    .versionName;
            result = result.replaceAll("[a-zA-Z]|-", "");
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }

        return result;
    }

    public static void showError(Activity activity, List<ValidationError> errors) {
        boolean isFirst = false;
        EditText editText;
        for (ValidationError error : errors) {
            View view = error.getView();
            ViewParent viewParent = error.getView().getParent().getParent();
            String message = error.getCollatedErrorMessage(activity);
            if (!isFirst) {
                if (view instanceof EditText) {
                    editText = ((EditText) view);
                    Common_Utils.setSelection(editText);
                    editText.requestFocus();
                    isFirst = true;
                }
            }

            if (viewParent instanceof TextInputLayout) {
                ((TextInputLayout) viewParent).setError(message);
                ((TextInputLayout) viewParent).setErrorEnabled(true);

            } else {
                Common_Utils.showToast(activity, message);
            }
        }
    }

    public static void showToast(Context mContext, String data) {

        try {
            if (!Common_Utils.isNotNullOrEmpty(data))
                return;

            Toast.makeText(mContext, data, Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setSelection(EditText editText) {
        int length = editText.getText().length();
        editText.setSelection(length);
    }

   /* public static void showProgress(AppCompatActivity activity) {
        ProgressDialogManager.Companion.getInstance().show(activity);
    }

    public static void hideProgress() {
        ProgressDialogManager.Companion.getInstance().hide();
    }*/

    public static double convertFeetandInchesToCentimeter(String feet, String inches) {
        double heightInFeet = 0;
        double heightInInches = 0;
        try {
            if (feet != null && feet.trim().length() != 0) {
                heightInFeet = Double.parseDouble(feet);
            }
            if (inches != null && inches.trim().length() != 0) {
                heightInInches = Double.parseDouble(inches);
            }
        } catch (NumberFormatException nfe) {

        }
        return (heightInFeet * 30.48) + (heightInInches * 2.54);
    }

    public static String centimeterToFeet(String centemeter) {
        int feetPart = 0;
        int inchesPart = 0;
        if (!TextUtils.isEmpty(centemeter)) {
            double dCentimeter = Double.valueOf(centemeter);
            feetPart = (int) Math.floor((dCentimeter / 2.54) / 12);
            System.out.println((dCentimeter / 2.54) - (feetPart * 12));
            inchesPart = (int) Math.ceil((dCentimeter / 2.54) - (feetPart * 12));
        }
        return String.format("%d", feetPart);
    }

    public static String centimeterToInch(String centemeter) {
        int feetPart = 0;
        int inchesPart = 0;
        if (!TextUtils.isEmpty(centemeter)) {
            double dCentimeter = Double.valueOf(centemeter);
            feetPart = (int) Math.floor((dCentimeter / 2.54) / 12);
            System.out.println((dCentimeter / 2.54) - (feetPart * 12));
            inchesPart = (int) Math.ceil((dCentimeter / 2.54) - (feetPart * 12));
        }
        return String.format(" %d", inchesPart);
    }

    final static double KG_TO_LBS = 2.20462;
    final static double M_TO_IN = 39.3701;



    public static String getReportsStoragePath() {
        String filePath = Environment.getExternalStorageDirectory()
                .getAbsolutePath()
                + File.separator
                + LungMonitorApp.getAppContext().getResources().getString(R.string.app_name)
                + File.separator + "Media"
                + File.separator + "Documents"
                + File.separator + "Reports";

        return filePath;
    }

}
