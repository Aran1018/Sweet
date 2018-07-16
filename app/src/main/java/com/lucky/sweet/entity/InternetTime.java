package com.lucky.sweet.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by chn on 2018/1/9.
 * (╯°□°）╯︵ ┻━┻ MMP好气啊！
 * (╯‵□′)╯︵┻━┻ 老子怒掀桌子！
 * ┻━┻︵╰(‵□′)╯︵┻━┻老子双手掀桌！
 * ┬─┬﻿ ノ( ゜-゜ノ) 算了，我不生气了！日子还得过老老实实敲吧~
 */

public class InternetTime {

    /**
     * sysTime1 : 20180109235234
     * sysTime2 : 2018-01-09 23:52:34
     */

    @SerializedName("sysTime1")
    private String sysTimeFormatOne;
    @SerializedName("sysTime2")
    private String sysTimeFormatTwo;

    public String getSysTimeFormatOne() {
        return sysTimeFormatOne;
    }

    public void setSysTimeFormatOne(String sysTimeFormatOne) {
        this.sysTimeFormatOne = sysTimeFormatOne;
    }

    public String getSysTimeFormatTwo() {
        return sysTimeFormatTwo;
    }

    public void setSysTimeFormatTwo(String sysTimeFormatTwo) {
        this.sysTimeFormatTwo = sysTimeFormatTwo;
    }
}
