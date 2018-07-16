package com.lucky.sweet.entity;

/**
 * Created by C on 2018/3/17.
 */

public class DetailOrderInfo {

    /**
     * mer_id : 1
     * create_time : 2018-03-11 15:22:25
     * money :
     * trolley_list :
     * type_sign : 0
     * mer_name : 这是一号店铺
     */

    private String mer_id;
    private String create_time;
    private String money;
    private String trolley_list;
    private String type_sign;
    private String mer_name;

    public String getMer_id() {
        return mer_id;
    }

    public void setMer_id(String mer_id) {
        this.mer_id = mer_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getTrolley_list() {
        return trolley_list;
    }

    public void setTrolley_list(String trolley_list) {
        this.trolley_list = trolley_list;
    }

    public String getType_sign() {
        return type_sign;
    }

    public void setType_sign(String type_sign) {
        this.type_sign = type_sign;
    }

    public String getMer_name() {
        return mer_name;
    }

    public void setMer_name(String mer_name) {
        this.mer_name = mer_name;
    }

}
