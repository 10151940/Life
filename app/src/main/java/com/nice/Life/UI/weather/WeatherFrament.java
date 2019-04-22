package com.nice.Life.UI.weather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.life.R;
import com.google.gson.Gson;
import com.nice.Life.Common.API.WFCResponse;
import com.nice.Life.Common.API.WeatherForCity.WFCDailyForecastItem;
import com.nice.Life.Common.API.WeatherForCity.WFCHeWeather5Item;
import com.nice.Life.Common.Constant;
import com.nice.Life.Utils.OkHTTP.MyDataCallBack;
import com.nice.Life.Utils.OkHTTP.OkHttpManager;
import com.nice.Life.Utils.ToastUtil;
import com.roger.gifloadinglibrary.GifLoadingView;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Request;


public class WeatherFrament extends Fragment {
    //下面的4个按钮相关
    private ArrayList<View> buttonArrayList;
    private int currentIndex = 0;
    //显示弹窗的Toast
    private Toast toast;
    //加载的gitloadingview
    private GifLoadingView gifLoadingView;
    //定位按钮
    private ImageButton positionButton;
    //分享按钮
    private ImageButton shareButton;
    //城市名字textview
    private TextView cityNameTextView;
    //城市微风之类的
    private TextView cityWindTextView;
    //城市大字温度
    private TextView cityTemperTextView;
    //城市湿度
    private TextView cityHumidityTextView;
    //城市小字温度
    private TextView cityTemperSmallTextView;
    //城市空气质量？树叶
    private TextView cityLeadTextView;
    //穿衣服指数按钮
    //网络请求获取到的数据
    private WFCResponse wfcResponse;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //隐藏状态栏
//        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(), "weather", Toast.LENGTH_SHORT).show();
//            }
//        });
        //初始化页面
        initView();
        //net
        getData();
    }
    //初始化UI
    public void initView() {
        View layout1 = getActivity().findViewById(R.id.wf_mybutton_one);
        layout1.setTag(0);
        TextView titleTextView = layout1.findViewById(R.id.wmv_title_text_view);
        ImageView imageView = layout1.findViewById(R.id.wmv_image_view);
        TextView containTextView = layout1.findViewById(R.id.wmv_temperature_text_view);
        titleTextView.setSelected(true);
        imageView.setSelected(true);
        containTextView.setSelected(true);
        layout1.setOnClickListener(new WeatherButtonClick());
        View layout2 = getActivity().findViewById(R.id.wf_mybutton_two);
        layout2.setTag(1);
        layout2.setOnClickListener(new WeatherButtonClick());
        View layout3 = getActivity().findViewById(R.id.wf_mybutton_three);
        layout3.setTag(2);
        layout3.setOnClickListener(new WeatherButtonClick());
        View layout4 = getActivity().findViewById(R.id.wf_mybutton_four);
        layout4.setTag(3);
        layout4.setOnClickListener(new WeatherButtonClick());
        buttonArrayList = new ArrayList<>();
        buttonArrayList.add(layout1);
        buttonArrayList.add(layout2);
        buttonArrayList.add(layout3);
        buttonArrayList.add(layout4);
        //加载动画
        gifLoadingView = new GifLoadingView();
        //分享和定位按钮
        positionButton = getActivity().findViewById(R.id.weather_position_button);
        positionButton.setOnClickListener(new PositionButtonClick());
        shareButton = getActivity().findViewById(R.id.weather_share_button);
        shareButton.setOnClickListener(new ShareButtonClick());
        //城市的信息
        cityNameTextView = getActivity().findViewById(R.id.city_name);
        cityWindTextView = getActivity().findViewById(R.id.city_wind);
        cityTemperTextView = getActivity().findViewById(R.id.city_temper);
        cityHumidityTextView = getActivity().findViewById(R.id.city_text_humidity);
        cityTemperSmallTextView = getActivity().findViewById(R.id.city_text_temperature);
        cityLeadTextView = getActivity().findViewById(R.id.city_text_leaf);

    }
    //获取网络数据的请求方法
    private void getData() {
        //网络请求
        OkHttpManager.getInstance().postAsynRequireJson(Constant.WeatherOfCity, null, new MyDataCallBack() {
            @Override
            public void onBefore(Request request) {
                gifLoadingView.setImageResource(R.drawable.cat_load_anim);
                gifLoadingView.show(getActivity().getFragmentManager(), "");
            }

            @Override
            public void requestSuccess(Object result) {
                Log.e("1", result.toString());
                Gson gson = new Gson();
                wfcResponse = gson.fromJson(result.toString(), WFCResponse.class);
                if (wfcResponse.code.equalsIgnoreCase("10000")) {
                    reloadData();
                } else {
                    ToastUtil.showShort(getActivity(), wfcResponse.msg);
                }

            }

            @Override
            public void requestFailure(Request request, IOException e) {
                ToastUtil.showLong(getActivity(),e.getMessage());
            }

            @Override
            public void onAfter() {
                gifLoadingView.dismiss();
            }
        });
    }
    //刷新页面上的城市固定数据
    private void reloadData() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                WFCHeWeather5Item item = wfcResponse.getResult().getHeWeather5().get(0);
                cityNameTextView.setText(item.getBasic().getCity());
                WFCDailyForecastItem forecastItem = item.getDaily_forecast().get(0);
                cityWindTextView.setText(item.getNow().getWind().getDir());
                cityTemperTextView.setText(item.getNow().getTmp() + "º");
                cityHumidityTextView.setText(item.getNow().getHum()+ "%");
                cityTemperSmallTextView.setText(forecastItem.getTmp().getMin() + "º/" + forecastItem.getTmp().getMax() + "º");
                cityLeadTextView.setText(item.getAqi().getCity().getQlty());
                for (int i=0; i<buttonArrayList.size(); i++) {
                    View buttonView = buttonArrayList.get(i);
                    TextView dateTextView = buttonView.findViewById(R.id.wmv_title_text_view);
                    ImageView imageView = buttonView.findViewById(R.id.wmv_image_view);
                    TextView tempTextView = buttonView.findViewById(R.id.wmv_temperature_text_view);
                    WFCDailyForecastItem dataItem = item.getDaily_forecast().get(i);
                    dateTextView.setText(dataItem.getDate());
                    tempTextView.setText(dataItem.getTmp().getMin() + "º/" + dataItem.getTmp().getMax() + "º");
                }
            }
        });
    }
    //定位按钮点击
    private class PositionButtonClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Log.e("click", "positionButtonClick");
        }
    }
    //分享按钮点击
    private class ShareButtonClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Log.e("click", "shareButtonClick");
        }
    }

    //下面的天气按钮点击的触发方法
    private class WeatherButtonClick implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (currentIndex == Integer.parseInt(String.valueOf(view.getTag()))) {
                return;
            }
            View currentSelected = buttonArrayList.get(currentIndex);
            TextView befTitleTextView = currentSelected.findViewById(R.id.wmv_title_text_view);
            befTitleTextView.setSelected(false);
            TextView curTitleTextView = view.findViewById(R.id.wmv_title_text_view);
            curTitleTextView.setSelected(true);
            ImageView befImageView = currentSelected.findViewById(R.id.wmv_image_view);
            befImageView.setSelected(false);
            ImageView curImageView = view.findViewById(R.id.wmv_image_view);
            curImageView.setSelected(true);
            TextView befTemperatureTextView = currentSelected.findViewById(R.id.wmv_temperature_text_view);
            befTemperatureTextView.setSelected(false);
            TextView curTexperatureTextView = view.findViewById(R.id.wmv_temperature_text_view);
            curTexperatureTextView.setSelected(true);
            currentIndex = Integer.parseInt(String.valueOf(view.getTag()));
//            if (toast != null) {
//                toast.cancel();
//            }
//            toast = Toast.makeText(getActivity(), view.getTag().toString(), Toast.LENGTH_SHORT);
//            toast.show();
        }
    }

    //获取屏幕的宽度
    private int getWindowWidth() {
        DisplayMetrics outMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int widthPixels = outMetrics.widthPixels;
        return widthPixels;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_frament, container, false);
        return  view;
    }

}
