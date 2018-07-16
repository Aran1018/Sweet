package com.lucky.sweet.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lucky.sweet.R;
import com.lucky.sweet.entity.PersonCollectInfo;
import com.lucky.sweet.views.CircleImageView;

import java.util.List;

/**
 * Created by Qiuyue on 2018/3/8.
 */

// ╭︿︿︿╮  ╭︿︿︿╮  ╭︿︿︿╮
// {/ o o /} {/ . . /} {/ ︿︿ /}
// ( (oo) )  ( (oo) )  ( (oo) )
//   ︶︶︶     ︶︶︶     ︶︶︶

public class MyLikeStoreAdapter extends BaseAdapter {
    private List<PersonCollectInfo.CollectListBean> datas;
    private Context context;

    public MyLikeStoreAdapter(List<PersonCollectInfo.CollectListBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    static public class ViewHolder {
        CircleImageView imv_storeHead;
        TextView name;
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
        PersonCollectInfo.CollectListBean collectListBean = datas.get(position);
        MyLikeStoreAdapter.ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_mylikestore, null);
            viewHolder = new ViewHolder();
            viewHolder.imv_storeHead = convertView.findViewById(R.id.imv_storeHead);
            viewHolder.name = convertView.findViewById(R.id.tv_person_collec_shop);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyLikeStoreAdapter.ViewHolder) convertView.getTag();
        }
        viewHolder.imv_storeHead.setmDrawShapeType(CircleImageView.SHAPE_CIRCLE);
        Glide.with(context).load(collectListBean.getPhoto()).into(viewHolder.imv_storeHead);
        viewHolder.name.setText(collectListBean.getName());
        return convertView;
    }


}
