package com.kayoxu.things;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.kayoxu.things.utils.GlobalDefine;


public class ThingsApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context; //尽量不要使用这个Context

    private static ThingsApplication application;
    private static int mainTid;
    private static Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        application = this;
        mainTid = android.os.Process.myTid();
        handler = new Handler();
        init();
    }

    private void init() {

    }

     public final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent(GlobalDefine.ACTION_INTENT_GENERAL);
            intent.putExtra("what", msg.what);
            intent.putExtra("arg1", msg.arg1);
            intent.putExtra("arg2", msg.arg2);
            sendBroadcast(intent);
        }
    };


    public static Context getContext() {
        return context;
    }

    public static Context getApplication() {
        return application;
    }

    /**
     * @return 获取主线程id
     */
    public static int getMainTid() {
        return mainTid;
    }

    /**
     * @return 全局handler
     */
    public static Handler getHandler() {
        return handler;
    }

    /**
     * 把Runnable 方法提交到主线程运行
     *
     * @param runnable runnable
     */
    public static void runOnUiThread(Runnable runnable) {
        // 在主线程运行
        if (android.os.Process.myTid() == ThingsApplication.getMainTid()) {
            runnable.run();
        } else {
            //获取handler
            ThingsApplication.getHandler().post(runnable);
        }
    }
}
