package com.lucky.sweet.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.lucky.sweet.R;

/**
 * Created by c on 2017/12/11.
 * (╯°□°）╯︵ ┻━┻ MMP好气啊！
 * (╯‵□′)╯︵┻━┻ 老子怒掀桌子！
 * ┻━┻︵╰(‵□′)╯︵┻━┻老子双手掀桌！
 * ┬─┬﻿ ノ( ゜-゜ノ) 算了，我不生气了！日子还得过老老实实敲吧~
 */

public class GradualChangeLinearLayout extends LinearLayout {


    private MySearchView msv_mysearch;

    public GradualChangeLinearLayout(Context context) {
        super(context);
        initView(context);
    }

    public GradualChangeLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    public GradualChangeLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);

    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout
                .item_imstore_gradualchange, this, true);
        msv_mysearch = findViewById(R.id.msv_mysearch);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


}
