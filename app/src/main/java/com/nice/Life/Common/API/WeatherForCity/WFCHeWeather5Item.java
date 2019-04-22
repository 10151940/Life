package com.nice.Life.Common.API.WeatherForCity;

import java.util.ArrayList;

public class WFCHeWeather5Item {
    private WFCAqi                       aqi;
    private WFCBasic                     basic;
    private ArrayList<WFCDailyForecastItem>  daily_forecast;
    private ArrayList<WFCHourlyForecastItem> hourly_forecast;
    private WFCNow                       now;
    private String                       status;
    private WFCSuggestion                suggestion;

    public WFCAqi getAqi() {
        return aqi;
    }

    public void setAqi(WFCAqi aqi) {
        this.aqi = aqi;
    }

    public WFCBasic getBasic() {
        return basic;
    }

    public void setBasic(WFCBasic basic) {
        this.basic = basic;
    }

    public ArrayList<WFCDailyForecastItem> getDaily_forecast() {
        return daily_forecast;
    }

    public void setDaily_forecast(ArrayList<WFCDailyForecastItem> daily_forecast) {
        this.daily_forecast = daily_forecast;
    }

    public ArrayList<WFCHourlyForecastItem> getHourly_forecast() {
        return hourly_forecast;
    }

    public void setHourly_forecast(ArrayList<WFCHourlyForecastItem> hourly_forecast) {
        this.hourly_forecast = hourly_forecast;
    }

    public WFCNow getNow() {
        return now;
    }

    public void setNow(WFCNow now) {
        this.now = now;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public WFCSuggestion getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(WFCSuggestion suggestion) {
        this.suggestion = suggestion;
    }

//    @Override
//    public String toString() {
//        return "WFCHeWeather5Item{" +
//                "aqi=" + aqi +
//                ", basic=" + basic +
//                ", daily_forecast=" + daily_forecast +
//                ", hourly_forecast=" + hourly_forecast +
//                ", now=" + now +
//                ", status='" + status + '\'' +
//                ", suggestion=" + suggestion +
//                '}';
//    }
}
