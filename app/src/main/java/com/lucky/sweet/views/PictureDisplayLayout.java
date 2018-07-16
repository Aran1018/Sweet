package com.lucky.sweet.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chn on 2018/1/14.
 * (╯°□°）╯︵ ┻━┻ MMP好气啊！
 * (╯‵□′)╯︵┻━┻ 老子怒掀桌子！
 * ┻━┻︵╰(‵□′)╯︵┻━┻老子双手掀桌！
 * ┬─┬﻿ ノ( ゜-゜ノ) 算了，我不生气了！日子还得过老老实实敲吧~
 */

public class PictureDisplayLayout extends LinearLayout {
    public PictureDisplayLayout(Context context) {
        super(context);
    }

    public PictureDisplayLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PictureDisplayLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initDatas(List<String> data) {
        removeAllViews();
        ArrayList<String> countList = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            countList.add(data.get(i));
            if (countList.size() == PictureDisplay.MAX_SIZE) {
                PictureDisplay pictureDisplay = new PictureDisplay(getContext());
                addView(pictureDisplay);
                pictureDisplay.initDatas(countList);
                countList = new ArrayList<>();
                continue;
            }
        }
        if (countList.size() > 0) {
            PictureDisplay pictureDisplay = new PictureDisplay(getContext());
            pictureDisplay.initDatas(countList);
            addView(pictureDisplay);
        }


    }
}
