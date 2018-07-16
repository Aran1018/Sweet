package com.lucky.sweet.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by chn on 2018/2/17.
 * (╯°□°）╯︵ ┻━┻ MMP好气啊！
 * (╯‵□′)╯︵┻━┻ 老子怒掀桌子！
 * ┻━┻︵╰(‵□′)╯︵┻━┻老子双手掀桌！
 * ┬─┬﻿ ノ( ゜-゜ノ) 算了，我不生气了！日子还得过老老实实敲吧~
 */

public class MuliiOrderInfo {
    public static final String UPDATA = "DATA_CHANGE";
    /**
     * type : 操作类型
     * name : 用户名
     * do : boolean用户增加/删除
     * section : 左位置
     * position : 右位置
     */

    private String type;
    private String name;
    @SerializedName("do")
    private boolean addDis;
    private int section;
    private int position;
    private int item_id;

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isaddDis() {
        return addDis;
    }

    public void setaddDis(boolean addDis) {
        this.addDis = addDis;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "{\"type\":\"" + type + "\",\"name\":\"" + name + "\",\"do\":" + addDis +
                ",\"section\":\"" + section +
                "\",\"position\":\"" + position + "\"}";
    }
}
