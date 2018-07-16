package com.lucky.sweet.adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lucky.sweet.R;
import com.lucky.sweet.entity.ShopCarInfo;
import com.lucky.sweet.views.QuantityIndicatorView;

import java.util.List;

/**
 * Created by chn on 2017/12/25.
 * (╯°□°）╯︵ ┻━┻ MMP好气啊！
 * (╯‵□′)╯︵┻━┻ 老子怒掀桌子！
 * ┻━┻︵╰(‵□′)╯︵┻━┻老子双手掀桌！
 * ┬─┬﻿ ノ( ゜-゜ノ) 算了，我不生气了！日子还得过老老实实敲吧~
 */

public class ShopCarListAdapter extends BaseAdapter {

    private List<ShopCarInfo> datas;
    private Context context;

    public ShopCarListAdapter(List<ShopCarInfo> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_list_shopcar, null);
            viewHolder.sale = convertView.findViewById(R.id.tv_sale);
            viewHolder.dishes = convertView.findViewById(R.id.tv_dishes);
            viewHolder.indicator = convertView.findViewById(R.id.qv_con);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ShopCarInfo shopCarInfo = datas.get(position);
        viewHolder.sale.setText(shopCarInfo.getSale() + "");
        viewHolder.dishes.setText(shopCarInfo.getDishesName());
        viewHolder.indicator.setNumber(shopCarInfo.getNum());
        viewHolder.indicator.setNumberListener(new QuantityIndicatorView.NumberListener() {
            @Override
            public void onDataChange(String num) {

                if (null != onOrderNumChangedListener) {
                    onOrderNumChangedListener.dataChanged(position, num);
                }
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    static public class ViewHolder {
        TextView dishes;
        TextView sale;
        QuantityIndicatorView indicator;
    }

    public interface OnOrderNumChanged {
        void dataChanged(int position, String num);
    }

    private OnOrderNumChanged onOrderNumChangedListener;

    public void setOnOrderNumChangedListener(OnOrderNumChanged onOrderNumChangedListener) {
        this.onOrderNumChangedListener = onOrderNumChangedListener;
    }
}
