package com.nice.Life.Common.API.WeatherForCity;

public class WFCAqi {
    private WFCCity city;

    public WFCCity getCity() {
        return city;
    }

    public void setCity(WFCCity city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "WFCAqi{" +
                "city=" + city +
                '}';
    }
}
