package com.nice.Life.Common.API.WeatherForCity;

public class WFCHourlyForecastItem {
    private WFCCond cond;
    private String date;
    private String hum;
    private String pop;
    private String pres;
    private String tmp;
    private WFCWind wind;

    public WFCCond getCond() {
        return cond;
    }

    public void setCond(WFCCond cond) {
        this.cond = cond;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getPop() {
        return pop;
    }

    public void setPop(String pop) {
        this.pop = pop;
    }

    public String getPres() {
        return pres;
    }

    public void setPres(String pres) {
        this.pres = pres;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    public WFCWind getWind() {
        return wind;
    }

    public void setWind(WFCWind wind) {
        this.wind = wind;
    }
}
