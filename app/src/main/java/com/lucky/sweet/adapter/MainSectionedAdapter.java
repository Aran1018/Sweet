package com.lucky.sweet.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lucky.sweet.R;
import com.lucky.sweet.views.QuantityIndicatorView;


/**
 * 基本功能：右侧Adapter
 * 创建：王杰
 * 创建时间：16/4/14
 * 邮箱：w489657152@gmail.com
 */
public class MainSectionedAdapter extends SectionedBaseAdapter {

    private Context mContext;
    private String[] leftStr;
    private String[][] rightStr;

    public MainSectionedAdapter(Context context, String[] leftStr, String[][]
            rightStr) {
        this.mContext = context;
        this.leftStr = leftStr;
        this.rightStr = rightStr;

    }

    @Override
    public Object getItem(int section, int position) {
        return rightStr[section][position];
    }

    @Override
    public long getItemId(int section, int position) {
        return position;
    }

    @Override
    public int getSectionCount() {
        return leftStr.length;
    }

    @Override
    public int getCountForSection(int section) {
        return rightStr[section].length;
    }

    @Override
    public View getItemView(final int section, final int position, View
            convertView, ViewGroup parent) {
        RelativeLayout layout = null;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (RelativeLayout) inflator.inflate(R.layout.item_right_list, null);
        } else {
            layout = (RelativeLayout) convertView;
        }
        ((TextView) layout.findViewById(R.id.textItem)).setText(rightStr[section][position]);
        QuantityIndicatorView qi_con = layout.findViewById(R.id.qi_con);

        qi_con.setNumberListener(new QuantityIndicatorView.NumberListener() {
            @Override
            public void onDataChange(String num) {
                if (null != orderNumListener) {
                    orderNumListener.onDataChange(section, position, Integer.parseInt(num));
                }
            }
        });
        layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Toast.makeText(mContext, rightStr[section][position], Toast.LENGTH_SHORT).show();
            }
        });

        return layout;
    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        LinearLayout layout = null;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) parent.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) inflator.inflate(R.layout.header_item,
                    null);
        } else {
            layout = (LinearLayout) convertView;
        }
        layout.setClickable(false);
        ((TextView) layout.findViewById(R.id.textItem)).setText(leftStr[section]);
        return layout;
    }

    public interface OrderNumListener {
        void onDataChange(int section, int position, int num);
    }

    private OrderNumListener orderNumListener;

    public void setOrderNumListener(OrderNumListener orderNumListener) {
        this.orderNumListener = orderNumListener;
    }
}
