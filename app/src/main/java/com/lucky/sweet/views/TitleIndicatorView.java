package com.lucky.sweet.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lucky.sweet.R;

import java.util.ArrayList;


/**
 * Created by chn on 2018/1/14.
 * (╯°□°）╯︵ ┻━┻ MMP好气啊！
 * (╯‵□′)╯︵┻━┻ 老子怒掀桌子！
 * ┻━┻︵╰(‵□′)╯︵┻━┻老子双手掀桌！
 * ┬─┬﻿ ノ( ゜-゜ノ) 算了，我不生气了！日子还得过老老实实敲吧~
 */

public class TitleIndicatorView extends LinearLayout implements View.OnClickListener {

    private ArrayList<TextView> arrayList = new ArrayList<>();

    private static final int FIRST_ITEM = 0;
    private static final int SECOND_ITEM = 1;
    private static final int THIRD_ITEM = 2;

    private static final int CURRENT_ITEM = 25;
    private static final int OTHER_ITEM = 15;
    private TextView tv_dishes_title;
    private TextView tv_dishes_over;
    private TextView tv_other;

    public TitleIndicatorView(Context context) {
        super(context);
        initView(context);
    }

    public TitleIndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);


    }


    public TitleIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout
                .item_title_indicator_view, this);
        tv_dishes_title = findViewById(R.id.tv_dishes_title);
        tv_dishes_over = findViewById(R.id.tv_dishes_over);
        tv_other = findViewById(R.id.tv_other);

        tv_dishes_title.getPaint().setFakeBoldText(true);

        tv_dishes_title.setTag(FIRST_ITEM);
        tv_dishes_over.setTag(SECOND_ITEM);
        tv_other.setTag(THIRD_ITEM);

        tv_dishes_title.setOnClickListener(this);
        tv_dishes_over.setOnClickListener(this);
        tv_other.setOnClickListener(this);

        arrayList.add(tv_dishes_title);
        arrayList.add(tv_dishes_over);
        arrayList.add(tv_other);

    }

    public void initializationData(String[] title) {
        if (title.length == 3) {
            tv_dishes_title.setText(title[0]);
            tv_dishes_over.setText(title[1]);
            tv_other.setText(title[2]);
        }
        if (title.length == 2) {
            tv_dishes_title.setText(title[0]);
            tv_dishes_over.setText(title[1]);
            tv_other.setVisibility(GONE);
        }
        if (title.length == 1) {
            tv_dishes_title.setText(title[0]);
            tv_other.setVisibility(GONE);
            tv_dishes_over.setVisibility(GONE);

        }

    }

    public interface OnTitleListener {
        void onCurrentTitleChange(String type);
    }

    private OnTitleListener onTitleListener;

    public void setOnTitleListener(OnTitleListener onTitleListener) {
        this.onTitleListener = onTitleListener;
    }

    public void setOnClickItem(String item) {
        for (TextView textView : arrayList) {
            if (textView.getText().equals(item)) {
                inStanceTextSize(textView);
                return;
            }
        }

    }

    @Override
    public void onClick(View v) {

        inStanceTextSize(v);
        if (onTitleListener != null) {
            onTitleListener.onCurrentTitleChange(((TextView) v).getText().toString());
        }
    }

    private void inStanceTextSize(View v) {
        for (TextView textview : arrayList) {
            if (textview.getTag() == v.getTag()) {

                textview.getPaint().setFakeBoldText(true);
                textview.setTextSize(CURRENT_ITEM);

            } else {

                textview.getPaint().setFakeBoldText(false);
                textview.setTextSize(OTHER_ITEM);

            }

        }
    }

}
