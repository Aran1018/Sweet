package com.lucky.sweet.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.gif.GifDrawableLoadProvider;
import com.lucky.sweet.R;

import java.util.ArrayList;

/**
 * Created by chn on 2018/1/14.
 * (╯°□°）╯︵ ┻━┻ MMP好气啊！
 * (╯‵□′)╯︵┻━┻ 老子怒掀桌子！
 * ┻━┻︵╰(‵□′)╯︵┻━┻老子双手掀桌！
 * ┬─┬﻿ ノ( ゜-゜ノ) 算了，我不生气了！日子还得过老老实实敲吧~
 */

public class PictureDisplay extends LinearLayout {

    private ArrayList<ImageView> imageViews = new ArrayList<ImageView>();
    public static final int MAX_SIZE = 6;

    public PictureDisplay(Context context) {
        super(context);
        initView(context);
    }

    public PictureDisplay(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PictureDisplay(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.item_show_more, this);
        imageViews.add((ImageView) findViewById(R.id.imv_shop_more_fir));
        imageViews.add((ImageView) findViewById(R.id.imv_shop_more_sec));
        imageViews.add((ImageView) findViewById(R.id.imv_shop_more_thr));
        imageViews.add((ImageView) findViewById(R.id.imv_shop_more_for));
        imageViews.add((ImageView) findViewById(R.id.imv_shop_more_fiv));
        imageViews.add((ImageView) findViewById(R.id.imv_shop_more_six));
    }

    public void initDatas(ArrayList<String> countList) {

        for (int i = 0; i < MAX_SIZE; i++) {
            ImageView imageView = imageViews.get(i);
            if (countList.size() <= i) {
                imageView.setVisibility(GONE);
            } else {
                Glide.with(getContext()).load(countList.get(i)).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
            }

        }

    }
}
