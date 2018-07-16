package com.lucky.sweet.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lucky.sweet.R;
import com.lucky.sweet.adapter.ShopCarListAdapter;
import com.lucky.sweet.entity.ShopCarInfo;

import java.util.ArrayList;


/**
 * Created by chn on 2017/12/24.
 * (╯°□°）╯︵ ┻━┻ MMP好气啊！
 * (╯‵□′)╯︵┻━┻ 老子怒掀桌子！
 * ┻━┻︵╰(‵□′)╯︵┻━┻老子双手掀桌！
 * ┬─┬﻿ ノ( ゜-゜ノ) 算了，我不生气了！日子还得过老老实实敲吧~
 */

public class ShopCarPopWindow extends PopupWindow {
    private Context context;
    private View rootsview;
    private ArrayList<ShopCarInfo> carInfoList;

    public ShopCarPopWindow(Context context, View view, ArrayList<ShopCarInfo>
            carInfoList) {
        this.context = context;
        this.rootsview = view;
        this.carInfoList = carInfoList;
        initPopWindow();
    }

    private void initPopWindow() {
        View view = LayoutInflater.from(context).inflate(R.layout.pop_shop_car,
                null);
        ListView lv_dick_recorder = view.findViewById(R.id.lv_dick_recorder);
        ShopCarListAdapter shopCarListAdapter = new ShopCarListAdapter(carInfoList, context);
        shopCarListAdapter.setOnOrderNumChangedListener(new ShopCarListAdapter.OnOrderNumChanged() {
            @Override
            public void dataChanged(int position, String num) {
                int number = Integer.parseInt(num);
                if (number == 0) {

                    carInfoList.remove(position);

                } else {

                    ShopCarInfo shopCarInfo = carInfoList.get(position);
                    shopCarInfo.setNum(number);

                }

            }
        });
        lv_dick_recorder.setAdapter(shopCarListAdapter);
        TextView tv_li = view.findViewById(R.id.tv_li);
        tv_li.setText(carInfoList.size() + "");
        setContentView(view);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(600);
        showAsDropDown(rootsview, 0, 0, Gravity.BOTTOM);
        setAnimationStyle(R.style.AnimTools);

    }
}
