package com.lucky.sweet.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lucky.sweet.R;

import java.util.List;

/**
 * Created by c on 2017/12/10.
 * (╯°□°）╯︵ ┻━┻ MMP好气啊！
 * (╯‵□′)╯︵┻━┻ 老子怒掀桌子！
 * ┻━┻︵╰(‵□′)╯︵┻━┻老子双手掀桌！
 * ┬─┬﻿ ノ( ゜-゜ノ) 算了，我不生气了！日子还得过老老实实敲吧~
 */

public class SearchSpinnerAdapter extends BaseAdapter {

    private List<String> mDataList;
    private Context context;

    public SearchSpinnerAdapter(List<String> mDataList, Context context) {
        this.mDataList = mDataList;
        this.context = context;

    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //数据不太多，没有使用ViewHolder进行处理。
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_storedisplay, null);
            viewHolder = new ViewHolder();
            viewHolder.type = (TextView) convertView.findViewById(R.id.item_mainx_tv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.type.setText(mDataList.get(position));
        viewHolder.type.setTextColor(context.getResources().getColorStateList(R.color
                .selector_spinner_item));

        return convertView;
    }

    static public class ViewHolder {
        TextView type;

    }

}

