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


}
