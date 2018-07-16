package com.lucky.sweet.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.lucky.sweet.R;

import java.util.ArrayList;

import cn.bingoogolapple.qrcode.core.DisplayUtils;

/**
 * Created by chn on 2018/1/14.
 * (╯°□°）╯︵ ┻━┻ MMP好气啊！
 * (╯‵□′)╯︵┻━┻ 老子怒掀桌子！
 * ┻━┻︵╰(‵□′)╯︵┻━┻老子双手掀桌！
 * ┬─┬﻿ ノ( ゜-゜ノ) 算了，我不生气了！日子还得过老老实实敲吧~
 */

public class IndicatorView extends LinearLayout {
    private ArrayList<View> mImageViews;//所有指示器集合
    private Context mContext;
    private int size = 6;
    private int marginSize = 15;
    private int pointSize;//指示器的大小
    private int marginLeft;//间距

    public IndicatorView(Context context) {
        super(context);
        initView(context);

    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);

    }

    private void initView(Context context) {
        mContext = context;
        pointSize = DisplayUtils.dp2px(context, size);
        marginLeft = DisplayUtils.dp2px(context, marginSize);
    }

    /**
     * 初始化指示器
     *
     * @param count 指示器的数量
     */
    public void initIndicator(int count) {
        mImageViews = new ArrayList<>();
        this.removeAllViews();
        LayoutParams lp;
        for (int i = 0; i < count; i++) {
            View v = new View(mContext);
            lp = new LayoutParams(pointSize, pointSize);
            if (i != 0)
                lp.leftMargin = marginLeft;
            v.setLayoutParams(lp);
            if (i == 0) {
                v.setBackgroundResource(R.drawable.shape_bg_indicator_point_select);
            } else {
                v.setBackgroundResource(R.drawable.shape_bg_indicator_point_nomal);
            }
            mImageViews.add(v);
            this.addView(v);
        }
    }

    private int startPosition = 0;

    public void playByStartPointToNext(int nextPosition) {

        final View ViewStrat = mImageViews.get(startPosition);
        final View ViewNext = mImageViews.get(nextPosition);

        startPosition = nextPosition;

        ViewStrat.setBackgroundResource(R.drawable.shape_bg_indicator_point_nomal);
        ViewNext.setBackgroundResource(R.drawable.shape_bg_indicator_point_select);
    }

}


