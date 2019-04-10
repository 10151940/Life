package com.nice.Life;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.BuildConfig;
import com.orhanobut.logger.CsvFormatStrategy;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

public class APP extends Application {
    private volatile static APP instance=null;//防止多个线程同时访问
    @Override
    public void onCreate() {
        super.onCreate();
        //      以下是打印自定义日志到控制台
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .tag("MyCustomTag")   // 自定义TAG全部标签，默认PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy){
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }

        });
//      以下是保存自定义日志
        FormatStrategy formatStrateg = CsvFormatStrategy.newBuilder()
                .tag("custom")
                .build();
        Logger.addLogAdapter(new DiskLogAdapter(formatStrateg));

    }
    //应用层的单例模式
    public static APP getInstance() {
        if (instance==null){
            synchronized (APP.class){
                if (instance==null){
                    instance=new APP();
                }
            }
        }
        return instance;
    }
}
