package com.nice.Life.Common.API;

public class ResponseBase {

    public String code;
    public boolean charge;
    public String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isCharge() {
        return charge;
    }

    public void setCharge(boolean charge) {
        this.charge = charge;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ResponseBase{" +
                "code='" + code + '\'' +
                ", charge=" + charge +
                ", msg='" + msg + '\'' +
                '}';
    }
}
