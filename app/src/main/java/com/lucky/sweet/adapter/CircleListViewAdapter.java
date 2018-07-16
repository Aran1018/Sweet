package com.lucky.sweet.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lucky.sweet.R;
import com.lucky.sweet.entity.CircleMainInfo;
import com.lucky.sweet.views.CircleImageView;
import com.lucky.sweet.widgets.ImageViewWatcher.MessagePicturesLayout;
import com.lucky.sweet.widgets.ImageViewWatcher.SquareImageView;
import com.sackcentury.shinebuttonlib.ShineButton;

import java.util.List;

/**
 * Created by c on 2017/12/13.
 * (╯°□°）╯︵ ┻━┻ MMP好气啊！
 * (╯‵□′)╯︵┻━┻ 老子怒掀桌子！
 * ┻━┻︵╰(‵□′)╯︵┻━┻老子双手掀桌！
 * ┬─┬﻿ ノ( ゜-゜ノ) 算了，我不生气了！日子还得过老老实实敲吧~
 */

public class CircleListViewAdapter extends BaseAdapter {
    private List<CircleMainInfo.CircleListBean> datas;
    private Context context;
    private MessagePicturesLayout.Callback mcallback;

    public CircleListViewAdapter(Context context, List<CircleMainInfo.CircleListBean> circle_list) {
        this.context = context;
        datas = circle_list;

    }

    public void setCallBack(MessagePicturesLayout.Callback mcallback) {
        this.mcallback = mcallback;

    }

    @Override
    public int getCount() {
        return datas.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        CircleMainInfo.CircleListBean circleListBean = datas.get(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_circle, null);
            viewHolder = new ViewHolder();
            viewHolder.imv_head = convertView.findViewById(R.id.imv_head);
            viewHolder.messPicLayout = convertView.findViewById(R.id.l_pictures);
            viewHolder.i_picture = convertView.findViewById(R.id.i_picture);
            viewHolder.messPicLayout.setCallback(mcallback);
            viewHolder.person_name = convertView.findViewById(R.id.tv_circle_person_name);
            viewHolder.content = convertView.findViewById(R.id.tv_circle_content);
            viewHolder.sendText = convertView.findViewById(R.id.tv_circle_send_time);
            viewHolder.merName = convertView.findViewById(R.id.tv_circle_mername);
            viewHolder.flowShow = convertView.findViewById(R.id.tv_circle_likenum);
            viewHolder.button = convertView.findViewById(R.id.po_image3);
            viewHolder.likeNum = convertView.findViewById(R.id.tv_circle_like_num);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.merName.setText(datas.get(position).getMer_name());
        viewHolder.likeNum.setText(datas.get(position).getLike_num());
        viewHolder.imv_head.setmDrawShapeType(CircleImageView.SHAPE_CIRCLE);
        viewHolder.content.setText(datas.get(position).getContent());
        viewHolder.person_name.setText(datas.get(position).getNikcname());
        viewHolder.sendText.setText(datas.get(position).getCreate_time());
        viewHolder.messPicLayout.set(datas.get(position).getPhoto_url());
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.button.setOnClickListener(v -> {
            if (onLikeItClickListener != null) {

                    finalViewHolder.likeNum.setText(Integer.parseInt(circleListBean.getLike_num()) + 1 + "");
                    onLikeItClickListener.meridBack(circleListBean.getCircle_id(), position);
                    viewHolder.button.setClickable(false);
                    viewHolder.button.setFocusable(false);

            }
        });
        viewHolder.flowShow.setText("关注");
        viewHolder.flowShow.setOnClickListener(v -> {
            TextView tv_flow = (TextView) v;
            if (tv_flow.getText().equals("关注")) {
                tv_flow.setText("已关注");
            } else {
                tv_flow.setText("关注");
            }
            boolean flag;
            String att = tv_flow.getText().toString();
            if (att.equals("已关注")) {
                flag = true;
            } else {
                flag = false;
            }
            if (onLikeItClickListener != null) {
                onLikeItClickListener.onFlowPeople(circleListBean.getUser_id(), flag);
            }

        });
        return convertView;

    }

    public interface OnLikeItClickListener {
        void meridBack(String mer_id, int position);

        void onFlowPeople(String userId, boolean flag);
    }

    private OnLikeItClickListener onLikeItClickListener;

    public void setOnLikeItClickListener(OnLikeItClickListener onLikeItClickListener) {
        this.onLikeItClickListener = onLikeItClickListener;
    }


    static public class ViewHolder {
        CircleImageView imv_head;
        MessagePicturesLayout messPicLayout;
        SquareImageView i_picture;
        TextView person_name;
        TextView content;
        TextView sendText;
        TextView merName;
        TextView flowShow;
        ShineButton button;
        TextView likeNum;
    }

}
