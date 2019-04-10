package com.nice.Life.Utils.OkHTTP;

import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Logger.d(String.format("Sending request %s on %s%n%s", request.url(),  chain.connection(), request.headers()));

        long t1 = System.nanoTime();
        okhttp3.Response response = chain.proceed(chain.request());
        long t2 = System.nanoTime();
        Logger.d(String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));

        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        Logger.json(content);
        Response response1=chain.proceed(request);
        Logger.d("返回response：",response1.toString());
        return response.newBuilder()
                .header("Authorization", "请求头授权信息拦截")
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }
}
