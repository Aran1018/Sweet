package com.lucky.sweet.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lucky.sweet.R;

import java.util.ArrayList;

/**
 * Created by chn on 2018/1/5.
 * (╯°□°）╯︵ ┻━┻ MMP好气啊！
 * (╯‵□′)╯︵┻━┻ 老子怒掀桌子！
 * ┻━┻︵╰(‵□′)╯︵┻━┻老子双手掀桌！
 * ┬─┬﻿ ノ( ゜-゜ノ) 算了，我不生气了！日子还得过老老实实敲吧~
 */

public class StarLevelIndicatorView extends LinearLayout {
    private ArrayList<ImageView> imageViews = new ArrayList<>();

    public StarLevelIndicatorView(Context context) {
        super(context);
        initView(context);
    }

    public StarLevelIndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public StarLevelIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.item_start_indicator,
                this);
        imageViews.add((ImageView) findViewById(R.id.imv_start_1));
        imageViews.add((ImageView) findViewById(R.id.imv_start_2));
        imageViews.add((ImageView) findViewById(R.id.imv_start_3));
        imageViews.add((ImageView) findViewById(R.id.imv_start_4));
        imageViews.add((ImageView) findViewById(R.id.imv_start_5));
    }

    public void initStarNumber(int number) {
        for (int i = 0; i < number; i++) {
            imageViews.get(i).setImageResource(R.mipmap.shopstarfull);
        }
    }
}
