package com.nice.Life.Utils.OkHTTP;

import android.os.Environment;

import com.google.gson.Gson;
import com.nice.Life.Utils.Thread.ThreadPoolUtil;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpManager {

    private static OkHttpClient okHttpClient;
    private volatile static OkHttpManager instance;//防止多个线程同时访问
    //提交json数据
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    //提交字符串数据
    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown;charset=utf-8");
    private static String responseStrGETAsyn;

    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
    //使用getCacheDir（）来作为缓存文件的存放路径（/data/data/包名/cache）
    //如果想看到缓存文件可以临时使用getExternalCacheDir（）（/sdcard/Android/data/包名/cache）
    private static File cacheFile;
    private static Cache cache;

    public OkHttpManager() {
        okHttpClient = new OkHttpClient();
        okHttpClient.newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        cookieStore.put(url.host(), cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(url.host());
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                });
    }

    /**
     * 懒汉加锁单例模式
     * @return
     */
    public static OkHttpManager getInstance() {
        if (instance == null) {
            synchronized (OkHttpManager.class) {
                if (instance == null) {
                    instance = new OkHttpManager();
                }
            }
        }
        return instance;
    }

    /**
     * get同步请求无需传参数
     * 通过response.body().string()获取返回的字符串
     * @param url
     * @return
     */
    public String getSyncBackString(String url) {
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            //将response转化成String
            String responseStr = response.body().string();
            return responseStr;
        } catch (IOException e) {
            Logger.e("GET同步请求解析为String异常" + e.toString());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get同步请求
     * 通过response。body（）。bytes（）获取返回二进制字节数据
     * @param url
     * @return
     */
    public byte[] getSyncBackByTeArray(String url) {
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            byte[] responseStr = response.body().bytes();
            return responseStr;
        } catch (IOException e) {
            Logger.e("GET同步请求解析为byte数组异常" + e.toString());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get同步请求
     * 返回byteStream的二进制字节流。
     * @param url
     * @return
     */
    public InputStream getSyncBackByteStream(String url) {
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            InputStream responseStr = response.body().byteStream();
            return responseStr;
        } catch (IOException e) {
            Logger.e("GET同步请求解析为String异常" + e.toString());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get异步请求不传参数
     * 获取字符串返回值
     * 异步返回不能更新UI，要开启新的线程
     * @param url
     * @param myDataCallBack
     * @return
     */
    public String getAsynBackStringWithoutParms(String url, final MyDataCallBack myDataCallBack) {
        final Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        try {
            myDataCallBack.onBefore(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    myDataCallBack.requestFailure(request, e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    responseStrGETAsyn = response.body().string();
                    try {
                        myDataCallBack.requestSuccess(responseStrGETAsyn);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Logger.e("GET异步请求带参数解析返回异常" + e.toString());
                    }
                }
            });
        } catch (Exception e) {
            Logger.e("GET异步请求解析异常" + e.toString());
            e.printStackTrace();
        }
        return responseStrGETAsyn;
    }

    /**
     * get异步请求传参数（参数可以传null）
     * 返回字符串
     * 异步返回不能更新UI，要开启新线程
     * @param url
     * @param params
     * @param myDataCallBack
     * @return
     */
    public String getAsynBackStringWithParms(String url, Map<String, String> params, final MyDataCallBack myDataCallBack) {
        if (params == null) {
            params = new HashMap<>();
        }
        //请求url（baseURL + 参数）
        String doUrl = urlJoint(url, params);
        final Request request = new Request.Builder().url(doUrl)
//                .header("Cookie","自动管理更新需要携带的Cookie")
                    .build();
        Call call = okHttpClient.newCall(request);
        try {
            myDataCallBack.onBefore(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    myDataCallBack.requestFailure(request, e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    responseStrGETAsyn = response.body().string();
                    try {
                        myDataCallBack.requestSuccess(responseStrGETAsyn);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Logger.e("GET异步请求带参数解析返回异常" + e.toString());
                    }
                }
            });
            myDataCallBack.onAfter();
        } catch (Exception e) {
            Logger.e("GET异步请求带参数解析返回异常" + e.toString());
            e.printStackTrace();
        }
        return responseStrGETAsyn;
    }

    /**
     * 拼接get请求的参数
     * @param url
     * @param params
     * @return
     */
    private static String urlJoint(String url, Map<String, String> params) {
        StringBuilder realURL = new StringBuilder(url);
        boolean isFirst = true;
        if (params == null) {
            params = new HashMap<>();
        } else {
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                if (isFirst && !url.contains("?")){
                    isFirst = false;
                    realURL.append("?");
                } else {
                    realURL.append("&");
                }
                realURL.append(entry.getKey());
                realURL.append("=");
                if (entry.getValue() == null) {
                    realURL.append("");
                } else {
                    realURL.append(entry.getValue());
                }
            }
        }
        return realURL.toString();
    }

    /**
     * post异步请求map传参
     * 返回字符串
     * 异步返回值不能更新UI线程，要开启新线程
     * @param url
     * @param params
     * @param myDataCallBack
     * @return
     */
    public String postAsynBackString(String url, Map<String, String> params, final MyDataCallBack myDataCallBack) {
        RequestBody requestBody;
        if (params == null) {
            params = new HashMap<>();
        }
        FormBody.Builder builder = new FormBody.Builder();
        addMapParmsToFromBody(params, builder);
        requestBody = builder.build();
        String realURL = urlJoint(url, null);
        final Request request = new Request.Builder().url(realURL).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        try {
            myDataCallBack.onBefore(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    myDataCallBack.requestFailure(request, e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    responseStrGETAsyn = response.body().string();//此处也可以解析成byte[] Reader，InputStream
                    try {
                        myDataCallBack.requestSuccess(responseStrGETAsyn);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Logger.e("POST异步请求为String解析异常失败" + e.toString());
                    }
                }
            });
            myDataCallBack.onAfter();
        } catch (Exception e) {
            Logger.e("POST异步请求解析为String异常" + e.toString());
            e.printStackTrace();
        }
        return responseStrGETAsyn;
    }

    /**
     * post请求拼接参数方法
     * @param params
     * @param builder
     */
    private void addMapParmsToFromBody(Map<String, String> params, FormBody.Builder builder) {
        for (Map.Entry<String, String> map : params.entrySet()) {
            String key = map.getKey();
            String value;
            if (map.getValue() == null) {
                value = "";
            } else {
                value = map.getValue();
            }
            builder.add(key,value);
        }
    }

    /**
     * post异步请求json传参
     * @param url
     * @param params
     * @param myDataCallBack
     * @return
     */
    public String postAsynRequireJson(String url, Map<String, String> params, final MyDataCallBack myDataCallBack) {
        if (params == null) {
            params = new HashMap<>();
        }
        //将map转换成json，需引入Gson包
        String mapToJson = new Gson().toJson(params);
        final String realURL = urlJoint(url, null);
        final Request request = buildJsonPostRequest(realURL, mapToJson);
        Call call = okHttpClient.newCall(request);
        try {
            myDataCallBack.onBefore(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    myDataCallBack.requestFailure(request, e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    responseStrGETAsyn = response.body().string();
                    try {
                        myDataCallBack.requestSuccess(responseStrGETAsyn);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Logger.e("POST异步请求为String解析异常" + e.toString());
                    }
                }
            });
            myDataCallBack.onAfter();
        } catch (Exception e) {
            Logger.e("POST异步请求解析为String异常" + e.toString());
            e.printStackTrace();
        }
        return responseStrGETAsyn;
    }

    /**
     * Json_POST请求参数
     * @param url
     * @param json
     * @return
     */
    private Request buildJsonPostRequest(String url, String json) {
        RequestBody requestBody = RequestBody.create(JSON, json);
        return new Request.Builder().url(url).post(requestBody).build();
    }

    /**
     * 基于http的文件上传（传入文件名和key）
     * 通过addFormDataPart
     * @param url           URL的Path部分
     * @param fileName      "pic.png"
     * @param fileKey       文件传入服务器的健"image"
     * @param myDataCallBack 自定义回调接口，将file作为请求体传入到服务器
     */
    private void upLoadMultiFileSimple(String url, String fileName, String fileKey, final MyDataCallBack myDataCallBack) {
        File file = new File(Environment.getExternalStorageDirectory(), fileName);
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(fileKey, fileName, fileBody)
                .build();
        final String realURL = urlJoint(url, null);
        final Request request = new Request.Builder().url(realURL).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        try {
            myDataCallBack.onBefore(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    myDataCallBack.requestFailure(request, e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        myDataCallBack.requestSuccess(responseStrGETAsyn);
                    } catch (Exception e) {
                        Logger.e("POST异步文件上传失败" + e.toString());
                        e.printStackTrace();
                    }
                }
            });
            myDataCallBack.onAfter();
        } catch (Exception e) {
            Logger.e("POST异步文件上传异常" + e.toString());
            e.printStackTrace();
        }
    }

    /**
     * 基于http的文件上传（传入文件数组和key）混合参数和文件请求
     * 通过addFormDataPart可以可以添加多个文件上传
     * @param url
     * @param files
     * @param fileKeys
     * @param params
     * @param myDataCallBack
     */
    private void upLoadMultiFile(String url, File[] files, String[] fileKeys, Map<String, String> params, final MyDataCallBack myDataCallBack) {
        if (params == null) {
            params = new HashMap<>();
        }
        final String realURL = urlJoint(url, null);
        FormBody.Builder builder = new FormBody.Builder();
        addMapParmsToFromBody(params, builder);
        RequestBody requestBody = builder.build();
        MultipartBody.Builder multipartBody = new MultipartBody.Builder();
        multipartBody.setType(MultipartBody.ALTERNATIVE).addPart(requestBody);
        if (files != null) {
            RequestBody fileBody = null;
            for (int i=0; i<files.length; i++) {
                File file = files[i];
                String fileName = file.getName();
                fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileName)), file);
                //TODO 根据文件名设置contentType
                multipartBody.addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"" + fileKeys[i] + "\"; filename=\"" + fileName + "\""),
                        fileBody);
            }
        }
        final Request request = new Request.Builder().url(realURL)
                .post(multipartBody.build())
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            myDataCallBack.onBefore(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    myDataCallBack.requestFailure(request, e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        myDataCallBack.requestSuccess(response.body().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                        Logger.e("POST异步文件上传异常" + e.toString());
                    }
                }
            });
            myDataCallBack.onAfter();
        } catch (Exception e) {
            Logger.e("POST异步文件上传异常", e.toString());
            e.printStackTrace();
        }
    }

    private String guessMimeType(String fileName) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(fileName);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }


    /**
     * 文件下载
     * @param url               path路径
     * @param destFileDir       本地存储的文件夹路径
     * @param myDataCallBack    自定义回调接口
     */
    private void downLoadFileAsyn(final String url, final String destFileDir, final MyDataCallBack myDataCallBack) {
        String realURL = urlJoint(url, null);
        final Request request = new Request.Builder().url(realURL).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                myDataCallBack.requestFailure(request, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                is = response.body().byteStream();
                File file = new File(destFileDir, getFileName(url));
                try {
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    fos.flush();
                } catch (IOException e) {
                    Logger.e("文件下载异常", e.getMessage());
                    e.printStackTrace();
                } finally {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            Logger.e("文件流关闭异常", e.getMessage());
                            e.printStackTrace();
                        }
                    }
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Logger.e("文件流关闭异常", e.getMessage());
                        }
                    }
                    //如果下载文件成功，第一个参数为文件的绝对路径。
                    sendSuccessResultCallback(file.getAbsolutePath(), myDataCallBack);
                    myDataCallBack.requestSuccess(response.body().toString());
                }
            }
        });
    }

    private String getFileName(String url) {
        int separatorIndex = url.lastIndexOf("/");
        return separatorIndex < 0 ? url : url.substring(separatorIndex + 1, url.length());
    }

    private void sendSuccessResultCallback(final String absolutePath, final MyDataCallBack myDataCallBack) {
        ThreadPoolUtil.execute(new Runnable() {
            @Override
            public void run() {
                if (myDataCallBack != null) {
                    myDataCallBack.requestSuccess(absolutePath);
                }
            }
        });
    }

}
