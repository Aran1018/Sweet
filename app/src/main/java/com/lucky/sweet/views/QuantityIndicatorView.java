package com.lucky.sweet.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lucky.sweet.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chn on 2017/12/23.
 * (╯°□°）╯︵ ┻━┻ MMP好气啊！
 * (╯‵□′)╯︵┻━┻ 老子怒掀桌子！
 * ┻━┻︵╰(‵□′)╯︵┻━┻老子双手掀桌！
 * ┬─┬﻿ ノ( ゜-゜ノ) 算了，我不生气了！日子还得过老老实实敲吧~
 */

public class QuantityIndicatorView extends LinearLayout {

    private TextView tv_num;
    private Button btn_subtract;


    public QuantityIndicatorView(Context context) {
        super(context);
        initView(context);
    }

    public QuantityIndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public QuantityIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.item_recorder_view, this, true);
        tv_num = findViewById(R.id.tv_num);
        btn_subtract = findViewById(R.id.btn_subtract);

        Button btn_add = findViewById(R.id.btn_add);

        btn_add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_num.getVisibility() == View.INVISIBLE) {

                    tv_num.setVisibility(View.VISIBLE);
                    btn_subtract.setVisibility(View.VISIBLE);
                    startAlpAnimation(btn_subtract, tv_num);

                }
                String text = tv_num.getText().toString().trim();
                text = Integer.parseInt(text) + 1 + "";
                if (numberListener != null)
                    numberListener.onDataChange(text);
                tv_num.setText(text);
            }
        });


        btn_subtract.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = tv_num.getText().toString().trim();
                if (text.equals("1")) {
                    tv_num.setVisibility(View.INVISIBLE);
                    btn_subtract.setVisibility(View.INVISIBLE);
                    text = "0";
                } else {
                    text = Integer.parseInt(text) + -1 + "";
                }
                if (numberListener != null)
                    numberListener.onDataChange(text);
                tv_num.setText(text);
            }
        });
    }

    private void startAlpAnimation(View view1, View view2) {
        List<View> views = new ArrayList<View>();
        views.add(view1);
        views.add(view2);
        for (int i = 0; i < views.size(); i++) {
            View view = views.get(i);
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
            alphaAnimation.setDuration(300);
            view.setAnimation(alphaAnimation);
        }
    }

    public interface NumberListener {
        void onDataChange(String num);
    }

    private NumberListener numberListener;

    public void setNumberListener(NumberListener numberListener) {
        this.numberListener = numberListener;
    }

    public void setNumber(int num){
        tv_num.setText(num+"");
        tv_num.setVisibility(VISIBLE);
        btn_subtract.setVisibility(VISIBLE);
    }
}
