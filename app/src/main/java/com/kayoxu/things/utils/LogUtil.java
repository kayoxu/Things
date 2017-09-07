package com.kayoxu.things.utils;


import android.util.Log;

import com.kayoxu.things.BuildConfig;

public class LogUtil {

    private static boolean debug = BuildConfig.DEBUG;

    public static void d(String tag, String msg) {
        if (debug) {
            Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (debug) {
            Log.e(tag, msg);
        }
    }


}
