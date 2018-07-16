package com.lucky.sweet.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lucky.sweet.R;
import com.lucky.sweet.entity.StoreDisplayInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c on 2017/12/10.
 * (╯°□°）╯︵ ┻━┻ MMP好气啊！
 * (╯‵□′)╯︵┻━┻ 老子怒掀桌子！
 * ┻━┻︵╰(‵□′)╯︵┻━┻老子双手掀桌！
 * ┬─┬﻿ ノ( ゜-゜ノ) 算了，我不生气了！日子还得过老老实实敲吧~
 */

public class ShowInfoListViewAdapter extends BaseAdapter {
    private List<StoreDisplayInfo.MerListBean> data;
    private Context context;

    public ShowInfoListViewAdapter(List<StoreDisplayInfo.MerListBean> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_storeinfo, null);
            viewHolder = new ViewHolder();
            viewHolder.imv_shop_sur = convertView.findViewById(R.id.imv_shop_sur);
            viewHolder.imv_shop_dis_fir = convertView.findViewById(R.id.imv_shop_dis_fir);
            viewHolder.imv_shop_dis_sec = convertView.findViewById(R.id.imv_shop_dis_sec);
            viewHolder.imv_shop_dis_the = convertView.findViewById(R.id.imv_shop_dis_the);
            viewHolder.tv_shop_title = convertView.findViewById(R.id.tv_shop_title);
            viewHolder.tv_shop_des = convertView.findViewById(R.id.tv_shop_des);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.onBindInfo(data.get(position), context);
        return convertView;
    }

    static public class ViewHolder {
        ImageView imv_shop_sur;
        ImageView imv_shop_dis_fir;
        ImageView imv_shop_dis_sec;
        ImageView imv_shop_dis_the;
        TextView tv_shop_title;
        TextView tv_shop_des;


        public void onBindInfo(StoreDisplayInfo.MerListBean info, Context context) {
            ImageView[] imageViews = new ImageView[]{imv_shop_dis_fir, imv_shop_dis_sec, imv_shop_dis_the};
            Glide.with(context).load(info.getSurface()).into(imv_shop_sur);
            List<String> environment = info.getEnvironment();
            if (environment != null) {
                for (int i = 0; i < environment.size(); i++) {
                    Glide.with(context).load(environment.get(i)).into(imageViews[i]);
                }
            }
            tv_shop_title.setText(info.getName());

        }
    }
}
