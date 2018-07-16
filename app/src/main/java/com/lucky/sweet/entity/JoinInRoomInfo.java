package com.lucky.sweet.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chn on 2018/2/19.
 * (╯°□°）╯︵ ┻━┻ MMP好气啊！
 * (╯‵□′)╯︵┻━┻ 老子怒掀桌子！
 * ┻━┻︵╰(‵□′)╯︵┻━┻老子双手掀桌！
 * ┬─┬﻿ ノ( ゜-゜ノ) 算了，我不生气了！日子还得过老老实实敲吧~
 */

public class JoinInRoomInfo {
    private String info;

    public JoinInRoomInfo(String info) {
        this.info = info;
    }

    public boolean getState() {
        switch (info) {
            case "1":
                return true;

            case "0":
                return false;
            default:
                return false;
        }
    }
}
