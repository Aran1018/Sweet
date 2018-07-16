package com.lucky.sweet.utility;

import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.lucky.sweet.activity.MyApplication;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

/**
 * Created by chn on 2018/2/18.
 * (╯°□°）╯︵ ┻━┻ MMP好气啊！
 * (╯‵□′)╯︵┻━┻ 老子怒掀桌子！
 * ┻━┻︵╰(‵□′)╯︵┻━┻老子双手掀桌！
 * ┬─┬﻿ ノ( ゜-゜ノ) 算了，我不生气了！日子还得过老老实实敲吧~
 */

public abstract class MyOkhttpHelper extends Callback {
    @Override
    public Object parseNetworkResponse(Response response) throws IOException {
        String stringResponse = response.body().string();

        try {
            if (!stringResponse.equals("")) {
                if (stringResponse.equals("250")) {
                    MyApplication.initSession(flag -> {
                        if (flag) {
                            afterNewRequestSession();
                        }
                    });
                } else onResponseSuccessfulString(stringResponse.trim());
            }
        } catch (JsonSyntaxException e) {
            Log.e("JsonSyntaxException", "jSON解析错误/n"+stringResponse);
        }
        return null;
    }

    @Override
    public void onError(Request request, Exception e) {

    }

    @Override
    public void onResponse(Object response) {

    }

    public abstract void onResponseSuccessfulString(String string);

    public abstract void afterNewRequestSession();
}
