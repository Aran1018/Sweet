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
import com.lucky.sweet.entity.SearchFriendInfo;
import com.lucky.sweet.views.CircleImageView;

import java.util.List;

public class DetaileSearchFriendAdapter extends BaseAdapter {
    private List<SearchFriendInfo.UserListBean> listBean;
    private Context context;
    private DetaileSearchFriendAdapter.OnInvitationClick onInvitationClick;

    public DetaileSearchFriendAdapter(List<SearchFriendInfo.UserListBean> listBean, Context context) {
        this.listBean = listBean;
        this.context = context;
    }

    @Override

    public int getCount() {
        return listBean.size();
    }

    @Override
    public Object getItem(int position) {
        return listBean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        SearchFriendInfo.UserListBean userListBean = listBean.get(position);
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_select_user, null);
            viewHolder = new ViewHolder();
            viewHolder.name = convertView.findViewById(R.id.tv_flow_friend);
            viewHolder.invitation = convertView.findViewById(R.id.btn_in_frinde);
            viewHolder.title = convertView.findViewById(R.id.imv_flow_friend);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(userListBean.getNickname());
        viewHolder.title.setmDrawShapeType(CircleImageView.SHAPE_CIRCLE);
        String photo = userListBean.getPhoto();
        Glide.with(context).load(photo).into(     viewHolder.title);
        viewHolder.invitation.setOnClickListener(v -> {
            if (onInvitationClick != null) {
                onInvitationClick.onClick(userListBean.getId());
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
        Button invitation;
        CircleImageView title;

    }

}
