package com.lucky.sweet.handler;

import android.os.Handler;
import android.os.Message;

import com.lucky.sweet.activity.OrderSeatActivity;
import com.lucky.sweet.entity.PerdetermingEntity;

/**
 * Created by chn on 2018/1/19.
 * (╯°□°）╯︵ ┻━┻ MMP好气啊！
 * (╯‵□′)╯︵┻━┻ 老子怒掀桌子！
 * ┻━┻︵╰(‵□′)╯︵┻━┻老子双手掀桌！
 * ┬─┬﻿ ノ( ゜-゜ノ) 算了，我不生气了！日子还得过老老实实敲吧~
 */

public class OrderSeatHandler extends Handler {
    public final static int UPDATE_DIALOGDAT = 0;
    private OrderSeatActivity orderSubmitActivity;

    public OrderSeatHandler(OrderSeatActivity orderSubmitActivity) {
        this.orderSubmitActivity = orderSubmitActivity;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case UPDATE_DIALOGDAT:

                orderSubmitActivity.upDateRequest((PerdetermingEntity) msg.obj);
                break;
            case 1:
                break;
            case 2:
                break;

            default:
                break;
        }

    }
}
