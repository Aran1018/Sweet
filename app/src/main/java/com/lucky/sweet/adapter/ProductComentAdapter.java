package com.lucky.sweet.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.lucky.sweet.R;
import com.lucky.sweet.views.CircleImageView;

import java.util.List;

/**
 * Created by Qiuyue on 2018/1/18.
 */

// ╭︿︿︿╮  ╭︿︿︿╮  ╭︿︿︿╮
// {/ o o /} {/ . . /} {/ ︿︿ /}
// ( (oo) )  ( (oo) )  ( (oo) )
//   ︶︶︶     ︶︶︶     ︶︶︶

public class ProductComentAdapter extends BaseAdapter {
    private List<String> datas;
    private Context context;
    private ListView view;


    public ProductComentAdapter(List<String> datas, Context context, ListView view) {
        this.datas = datas;
        this.context = context;
        this.view = view;
        setListViewHeightBasedOnChildren(view);
    }

    static public class ViewHolder {
        CircleImageView imv_head;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_product_comment, null);
            viewHolder = new ViewHolder();
            viewHolder.imv_head = convertView.findViewById(R.id.imv_head);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.imv_head.setmDrawShapeType(CircleImageView.SHAPE_CIRCLE);

        return convertView;
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        int totalHeight = 0;
        for (int i = 0; i < getCount(); i++) {
            View listItem = getView(i, null, listView);
            listItem.measure(0, 0);
            //计算总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        //计算分割线高度
        params.height = totalHeight + (listView.getDividerHeight() * (getCount() - 1));
        //给listview设置高度
        listView.setLayoutParams(params);
    }

}
