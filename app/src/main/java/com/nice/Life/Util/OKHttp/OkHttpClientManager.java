package com.nice.Life.Util.OKHttp;

import com.google.gson.Gson;

import java.util.logging.Handler;
import okhttp3.OkHttpClient;

public class OkHttpClientManager {
    private static OkHttpClientManager mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;
    private Gson mGson;

    private static final String TAG = "OkHttpClientManager";

    private OkHttpClientManager() {
        mOkHttpClient = new OkHttpClient();
    }

}
