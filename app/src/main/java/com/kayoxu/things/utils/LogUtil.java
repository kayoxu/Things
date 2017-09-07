package com.kayoxu.things.utils;

import android.util.Log;


public class LogUtil {

    public static void d(String tag, String msg) {
        if (GlobalDefine.debug) {
            Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (GlobalDefine.debug) {
            Log.e(tag, msg);
        }
    }

    public static void d(String tag, String format, Object... args) {
        d(tag, String.format(format, args));
    }

    public static void e(String tag, String format, Object... args) {
        e(tag, String.format(format, args));
    }
}
