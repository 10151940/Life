package com.nice.Life.Common.API.WeatherForCity;

public class WFCSuggestion {
    private WFCSuggestionItem air;
    private WFCSuggestionItem comf;
    private WFCSuggestionItem cw;
    private WFCSuggestionItem drsg;
    private WFCSuggestionItem flu;
    private WFCSuggestionItem sport;
    private WFCSuggestionItem trav;
    private WFCSuggestionItem uv;

    public WFCSuggestionItem getAir() {
        return air;
    }

    public void setAir(WFCSuggestionItem air) {
        this.air = air;
    }

    public WFCSuggestionItem getComf() {
        return comf;
    }

    public void setComf(WFCSuggestionItem comf) {
        this.comf = comf;
    }

    public WFCSuggestionItem getCw() {
        return cw;
    }

    public void setCw(WFCSuggestionItem cw) {
        this.cw = cw;
    }

    public WFCSuggestionItem getDrsg() {
        return drsg;
    }

    public void setDrsg(WFCSuggestionItem drsg) {
        this.drsg = drsg;
    }

    public WFCSuggestionItem getFlu() {
        return flu;
    }

    public void setFlu(WFCSuggestionItem flu) {
        this.flu = flu;
    }

    public WFCSuggestionItem getSport() {
        return sport;
    }

    public void setSport(WFCSuggestionItem sport) {
        this.sport = sport;
    }

    public WFCSuggestionItem getTrav() {
        return trav;
    }

    public void setTrav(WFCSuggestionItem trav) {
        this.trav = trav;
    }

    public WFCSuggestionItem getUv() {
        return uv;
    }

    public void setUv(WFCSuggestionItem uv) {
        this.uv = uv;
    }
}
