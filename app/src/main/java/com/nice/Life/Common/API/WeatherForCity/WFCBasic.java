package com.nice.Life.Common.API.WeatherForCity;

public class WFCBasic {
    private String city;
    private String cnty;
    private String id;
    private String lat;
    private String lon;
    private WFCUpdate update;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCnty() {
        return cnty;
    }

    public void setCnty(String cnty) {
        this.cnty = cnty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public WFCUpdate getUpdate() {
        return update;
    }

    public void setUpdate(WFCUpdate update) {
        this.update = update;
    }

    @Override
    public String toString() {
        return "WFCBasic{" +
                "city='" + city + '\'' +
                ", cnty='" + cnty + '\'' +
                ", id='" + id + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                ", update=" + update +
                '}';
    }
}
