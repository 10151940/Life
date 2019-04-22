package com.example.life;

import android.app.Application;

public class MainActivity extends Application {
    //单例模式
    private static MainActivity mainActivity = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mainActivity = this;
    }

    public static MainActivity getInstance() {
        return mainActivity;
    }
}
