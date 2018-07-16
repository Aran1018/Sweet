package com.lucky.sweet.entity;

/**
 * Created by chn on 2018/1/19.
 * (╯°□°）╯︵ ┻━┻ MMP好气啊！
 * (╯‵□′)╯︵┻━┻ 老子怒掀桌子！
 * ┻━┻︵╰(‵□′)╯︵┻━┻老子双手掀桌！
 * ┬─┬﻿ ノ( ゜-゜ノ) 算了，我不生气了！日子还得过老老实实敲吧~
 */

public class PerdetermingEntity {
    private String[] data;
    private String[][] time;

    public String[] getData() {
        return data;
    }

    public String[][] getTime() {
        return time;
    }

    public PerdetermingEntity setTime(String[][] time) {
        this.time = time;
        return this;
    }

    public PerdetermingEntity setData(String[] data) {
        this.data = data;
        return this;

    }


}
