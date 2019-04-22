package com.nice.Life.Common.API;

import com.nice.Life.Common.API.WeatherForCity.WFCHeWeather5;

public class WFCResponse extends ResponseBase {
    private WFCHeWeather5 result;

    public WFCHeWeather5 getResult() {
        return result;
    }

    public void setResult(WFCHeWeather5 result) {
        this.result = result;
    }
}
