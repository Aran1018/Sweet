package com.lucky.sweet.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lucky.sweet.R;
import com.lucky.sweet.entity.MyCommentEntiy;
import com.lucky.sweet.views.CircleImageView;

import java.util.List;

/**
 * Created by Qiuyue on 2018/1/31.
 */

// ╭︿︿︿╮  ╭︿︿︿╮  ╭︿︿︿╮
// {/ o o /} {/ . . /} {/ ︿︿ /}
// ( (oo) )  ( (oo) )  ( (oo) )
//   ︶︶︶     ︶︶︶     ︶︶︶

public class MyCommentAdapter extends BaseAdapter {

    private   List<MyCommentEntiy.CircleReplyLsitBean> datas;
    private Context context;

    public MyCommentAdapter (List<MyCommentEntiy.CircleReplyLsitBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    static public class ViewHolder {
        ImageView imv_my_comment;
        TextView contant;
        TextView getRepert;

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
        MyCommentEntiy.CircleReplyLsitBean circleReplyLsitBean = datas.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new MyCommentAdapter.ViewHolder();
            convertView = View.inflate(context, R.layout.item_mycomment, null);
            viewHolder.imv_my_comment=convertView.findViewById(R.id.imv_my_comment);
            viewHolder.contant=convertView.findViewById(R.id.tv_comment_content);
            viewHolder.getRepert=convertView.findViewById(R.id.tv_comment_repert_pero);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.getRepert.setText(circleReplyLsitBean.getReply_user_nickname());
        viewHolder.contant.setText(circleReplyLsitBean.getContent());
        Glide.with(context).load( circleReplyLsitBean.getPublish_photo()).into(viewHolder.imv_my_comment);
        return convertView;
    }
}
