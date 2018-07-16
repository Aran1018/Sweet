package com.lucky.sweet.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lucky.sweet.R;
import com.lucky.sweet.entity.FlowPeople;
import com.lucky.sweet.views.CircleImageView;

import java.util.List;

import javax.microedition.khronos.opengles.GL;

/**
 * Created by C on 2018/3/14.
 */

public class FlowFriendAdapter extends BaseAdapter {
    private List<FlowPeople.AttentListBean> attent_list;
    private Context context;
    private OnInvitationClick onInvitationClick;

    public FlowFriendAdapter(List<FlowPeople.AttentListBean> attent_list, Context context) {
        this.attent_list = attent_list;
        this.context = context;
    }

    @Override

    public int getCount() {
        return attent_list.size();
    }

    @Override
    public Object getItem(int position) {
        return attent_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        FlowPeople.AttentListBean attentListBean = attent_list.get(position);
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_flow_friend, null);
            viewHolder = new ViewHolder();
            viewHolder.name = convertView.findViewById(R.id.tv_flow_friend);
            viewHolder.pic = convertView.findViewById(R.id.imv_flow_friend);
//            viewHolder.invitation = convertView.findViewById(R.id.btn_in_frinde);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(attentListBean.getNickname());
        viewHolder.pic.setmDrawShapeType(CircleImageView.SHAPE_CIRCLE);

        Glide.with(context).load(attentListBean.getPhoto()).into(viewHolder.pic);
        viewHolder.pic.setOnClickListener(v -> {
            if (onInvitationClick != null) {
                onInvitationClick.onClick(attent_list.get(position).getUser_id());
            }
        });
        return convertView;
    }

    public interface OnInvitationClick {
        void onClick(String userId);
    }


    public void setOnInvitationClick(OnInvitationClick onInvitationClick) {
        this.onInvitationClick = onInvitationClick;
    }

    static public class ViewHolder {
        TextView name;
//        Button invitation;
        CircleImageView pic;
    }

}
