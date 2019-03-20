package com.example.life;

import android.app.Application;
import android.util.Log;

public class MainActivity extends Application {
    //单例模式
    private static MainActivity mainActivity = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mainActivity = this;
        String showText = "代码启动了";
        Log.e(showText, showText);
    }

    public static MainActivity getInstance() {
        return mainActivity;
    }
}
