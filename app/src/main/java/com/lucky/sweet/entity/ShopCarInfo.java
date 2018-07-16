package com.lucky.sweet.entity;

/**
 * Created by chn on 2017/12/25.
 * (╯°□°）╯︵ ┻━┻ MMP好气啊！
 * (╯‵□′)╯︵┻━┻ 老子怒掀桌子！
 * ┻━┻︵╰(‵□′)╯︵┻━┻老子双手掀桌！
 * ┬─┬﻿ ノ( ゜-゜ノ) 算了，我不生气了！日子还得过老老实实敲吧~
 */

public class ShopCarInfo {
    private String dishesName;
    private int num;
    private int sale;

    public ShopCarInfo(String dishesName, int num, int sale) {
        this.dishesName = dishesName;
        this.num = num;
        this.sale = sale;
    }

    public String getDishesName() {
        return dishesName;
    }

    public int getNum() {
        return num;
    }

    public int getSale() {
        return sale;
    }

    public void setNum(int num) {

        this.num = num;
    }
}
