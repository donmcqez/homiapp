package com.tikay.homitest.presentation.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.DimenRes;
import androidx.annotation.IdRes;
import androidx.annotation.RequiresApi;

import com.google.android.material.snackbar.Snackbar;
import com.tikay.homitest.HomiApp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Utils {

    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public static int getStatusBarHeight(Context context) {
        return getStatusBarHeight2(context);
    }

    public static int getStatusBarHeight2(Context context) {
        Resources resources = context.getResources();
        int statusBarHeightId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (statusBarHeightId > 0) {
            return resources.getDimensionPixelSize(statusBarHeightId);
        } else {
            return 0;
        }
    }

    public static Spanned fromHtml(String source) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(source);
        }
    }

    public static float spFromRes(Context context,@DimenRes int res) {
        return context.getResources().getDimension(res);
    }

    public static float dpFromRes(Context context,@DimenRes int res) {
        return context.getResources().getDimension(res) / HomiApp.self().getResources().getDisplayMetrics().density;
    }

    public static float dp2px(Context context,float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public static float sp2px(float sp) {
        final float scale = HomiApp.self().getResources().getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    public static <T> boolean notEmpty(List<T> list) {
        return !isEmpty(list);
    }

    public static <T> boolean isEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }

    public static <T> boolean notEmpty(T[] list) {
        return !isEmpty(list);
    }

    public static <T> boolean isEmpty(T[] list) {
        return list == null || list.length <= 0;
    }

    public static boolean notEmpty(String text) {
        return !isEmpty(text);
    }

    public static boolean isEmpty(String text) {
        return TextUtils.isEmpty(text);
    }

    public static boolean equals(Object a, Object b) {
//        return (a == b) || (a != null && a.equals(b));
        return Objects.equals(a, b);
    }

    public static String getDateAndTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        String date = simpleDateFormat.format(calendar.getTime());
        Log.d("TAG", "DATE AND TIME: ========@@@@@@@@@@@======== $date");
        return date;
    }

    public static String getDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String getDateTimeFromString(String inputDate) {
        String dateToReturn = "";
        long millisIn24Hours = 1000 * 60 * 60 * 24L ; // 24hrs
        long millisIn48Hours = 1000 * 60 * 60 * 48L; // 48hrs
        // val millis = seconds * 1000

//        DateFormat inputDateFormat = new  SimpleDateFormat("yyyy-MM-dd H:mm:ss", Locale.getDefault());
        DateFormat inputDateFormat = new  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        DateFormat dateFormat = new  SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
//        DateFormat timeFormat = new  SimpleDateFormat("h:mm a", Locale.getDefault());
//        DateFormat simpleDateFormat = new  SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        //DateFormat dateFormat2 = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.UK);

//        Calendar calendar = Calendar.getInstance();
//        calendar.get(Calendar.HOUR_OF_DAY) = 0;
//        calendar[Calendar.MINUTE] = 0;
//        calendar[Calendar.SECOND] = 0;
//        val twelveAm = calendar.timeInMillis
        try {
            //Date date = simpleDateFormat.format(millis);
            //Date now = dateFormat.parse(date);
            Date date = inputDateFormat.parse(inputDate);
            dateToReturn  = dateFormat.format(date != null ? date : "");
//            dateToReturn = date != null ? date.toString() : "";
            Log.e("TAG", "getDateTimeFromString: ============>  "+dateToReturn );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateToReturn;
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private static String convert(String publishedDay) {
        // 2022-11-02T23:56:52.878Z

//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ss.aaa'Z'", Locale.getDefault());


        DateTimeFormatter formatPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime publishedAt = LocalDateTime.parse(publishedDay, formatPattern);
//            LocalDateTime publishedAt1 = LocalDateTime.parse(publishedDay);

        LocalDateTime currentDate = LocalDateTime.now().withNano(0);

        long differenceInSeconds = ChronoUnit.SECONDS.between(publishedAt, currentDate);
        long differenceInDays = ChronoUnit.DAYS.between(publishedAt, currentDate);
        long differenceInMonths = ChronoUnit.MONTHS.between(publishedAt, currentDate);
        return findDifference(differenceInSeconds, differenceInDays, differenceInMonths);

    }

    public static String viewsCount(int views) {
        String view = "";
        if (views >= 1000000000) {
            int formattedViews = views / 1000000;
            view = formattedViews + "B views";
        } else if (views >= 1000000) {
            int formattedViews = views / 10000;
            view = formattedViews + "M views";
        } else if (views >= 1000) {
            int formattedViews = views / 10000;
            view = formattedViews + "K views";
        } else {
            view = views + " views";
        }
        return view;
    }

    public static String findDifference(long differenceInSeconds, long differenceInDays, long differenceInMonths) {
        long hours = differenceInSeconds / 3600;
        if (differenceInDays >= 21 && differenceInDays <= 31) {
            return "3 weeks ago";
        } else if (differenceInDays >= 14 && differenceInDays <= 20) {
            return "2 weeks ago";
        } else if (differenceInDays >= 2 && differenceInDays <= 13) {
            return "2 weeks ago";
        } else if (differenceInDays >= 0 && differenceInDays <= 1) {
            return "2 weeks ago";
        }
        if (differenceInMonths >= 0 && differenceInMonths <= 1) {
            return differenceInMonths + " month ago";
        }
        return differenceInMonths + " month ago";
    }

    public static void showShortToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    public static void showLongToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
    public static void showSnackBar(View view, @IdRes int id, String message){
        Snackbar.make(view.findViewById(id),message,Snackbar.LENGTH_LONG).show();
    }

    // A placeholder username validation check
    public static boolean isEmailValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    public static boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}
