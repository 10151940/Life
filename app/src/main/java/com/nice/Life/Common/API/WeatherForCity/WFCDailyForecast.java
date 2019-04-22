package com.nice.Life.Common.API.WeatherForCity;

import java.util.ArrayList;

public class WFCDailyForecast {
    private ArrayList<WFCDailyForecastItem> daily_forecast;

    public ArrayList<WFCDailyForecastItem> getDaily_forecast() {
        return daily_forecast;
    }

    public void setDaily_forecast(ArrayList<WFCDailyForecastItem> daily_forecast) {
        this.daily_forecast = daily_forecast;
    }
}
