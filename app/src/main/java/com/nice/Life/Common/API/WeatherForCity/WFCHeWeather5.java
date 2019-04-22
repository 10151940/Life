package com.nice.Life.Common.API.WeatherForCity;

import java.util.ArrayList;

public class WFCHeWeather5 {
    private ArrayList<WFCHeWeather5Item> HeWeather5;

    public ArrayList<WFCHeWeather5Item> getHeWeather5() {
        return HeWeather5;
    }

    public void setHeWeather5(ArrayList<WFCHeWeather5Item> heWeather5) {
        HeWeather5 = heWeather5;
    }

    @Override
    public String toString() {
        return "WFCHeWeather5{" +
                "HeWeather5=" + HeWeather5 +
                '}';
    }
}
