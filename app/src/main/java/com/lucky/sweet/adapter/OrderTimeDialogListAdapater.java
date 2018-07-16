package com.lucky.sweet.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lucky.sweet.R;

/**
 * Created by chn on 2017/12/22.
 * (╯°□°）╯︵ ┻━┻ MMP好气啊！
 * (╯‵□′)╯︵┻━┻ 老子怒掀桌子！
 * ┻━┻︵╰(‵□′)╯︵┻━┻老子双手掀桌！
 * ┬─┬﻿ ノ( ゜-゜ノ) 算了，我不生气了！日子还得过老老实实敲吧~
 */

public class OrderTimeDialogListAdapater extends BaseAdapter {
    private String[] datas;
    private Context context;
    private int selectPositon=-1;


    public OrderTimeDialogListAdapater(String[] datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return datas.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_dialog_data, null);

            viewHolder.title = (TextView) convertView.findViewById(R.id.tv_data);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (selectPositon==position){
            convertView.setBackgroundColor(Color.rgb(255,255,255));
        }else {
            convertView.setBackgroundColor(Color.TRANSPARENT);

        }
        viewHolder.title.setText(datas[position]);
        return convertView;
    }

    protected static class ViewHolder {
        TextView title;
    }


  public   void setSelectedItem(int position) {
        this.selectPositon = position;
    }
}
