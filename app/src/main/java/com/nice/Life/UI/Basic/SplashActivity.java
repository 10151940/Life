package com.nice.Life.UI.Basic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.life.R;

public class SplashActivity extends Activity implements Runnable {
    final Handler mHandler = new Handler();
    private final String SHAREDPREFERENCES_NAME = "my_pref";
    private final String KEY_GUIDE_ACTIVITY = "guide_activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        mHandler.postDelayed(this, 2000);

    }


    @Override
    public void run() {
        boolean mFirst = isFirstEnter(this, this.getClass().getName());
        Intent intent;
        if (!mFirst) {//第一次来
            intent = new Intent(this, GuideActivity.class);
        } else {
            intent = new Intent(this, MainActivity.class);
        }
        startActivity(intent);

    }

    boolean isFirstEnter(Context context, String className) {
        if (context == null || className == null || "".equalsIgnoreCase(className)) {
            return false;
        }
        String mResultStr = context.getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE)
                .getString(KEY_GUIDE_ACTIVITY, "");
        if (mResultStr.equalsIgnoreCase("")) {
            return  false;
        } else  {
            return true;
        }
    }
}
