package com.nice.Life.Common.API.WeatherForCity;

public class WFCDailyForecastItem {
    private WFCAstro astro;
    private WFCCond cond;
    private String date;
    private String hum;
    private String pcpn;
    private String pop;
    private String pres;
    private WFCTmp tmp;
    private String uv;
    private String vis;
    private WFCWind wind;

    public WFCAstro getAstro() {
        return astro;
    }

    public void setAstro(WFCAstro astro) {
        this.astro = astro;
    }

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

    public String getPcpn() {
        return pcpn;
    }

    public void setPcpn(String pcpn) {
        this.pcpn = pcpn;
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

    public WFCTmp getTmp() {
        return tmp;
    }

    public void setTmp(WFCTmp tmp) {
        this.tmp = tmp;
    }

    public String getUv() {
        return uv;
    }

    public void setUv(String uv) {
        this.uv = uv;
    }

    public String getVis() {
        return vis;
    }

    public void setVis(String vis) {
        this.vis = vis;
    }

    public WFCWind getWind() {
        return wind;
    }

    public void setWind(WFCWind wind) {
        this.wind = wind;
    }
}
