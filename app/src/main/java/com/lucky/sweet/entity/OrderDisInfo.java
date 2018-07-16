package com.lucky.sweet.entity;

import java.io.Serializable;

/**
 * Create by chn on 2018/4/5.
 * 第一句唱前半生：走马西风长路
 * 第二句唱莫回首：十载飘荡已无亲故
 * 第三句唱这江湖路：晃晃悠悠的一朝一暮
 */
public class OrderDisInfo implements Serializable{
    private String time;
    private String peopleNum;
    private String name;
    private String phone;
    private String dis;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(String peopleNum) {
        this.peopleNum = peopleNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDis() {
        return dis;
    }

    public void setDis(String dis) {
        this.dis = dis;
    }
}
