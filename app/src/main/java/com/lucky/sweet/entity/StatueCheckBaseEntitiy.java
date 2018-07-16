package com.lucky.sweet.entity;

/**
 * Created by C on 2018/3/18.
 */

public class StatueCheckBaseEntitiy {

    private final static String SUCCESSFUL = "1";
    private final static String FAILED = "0";
    private String info;

    public StatueCheckBaseEntitiy(String info) {
        this.info = info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean getAttr() {
        if (info.equals(FAILED)) return false;

        if (info.equals(SUCCESSFUL)) return true;

        return false;
    }
}
