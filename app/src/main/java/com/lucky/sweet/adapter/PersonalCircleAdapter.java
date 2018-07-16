package com.lucky.sweet.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.lucky.sweet.R;
import com.lucky.sweet.entity.CircleDetail;
import com.lucky.sweet.views.CircleImageView;

import java.util.List;

/**
 * Created by Qiuyue on 2018/1/18.
 */

// ╭︿︿︿╮  ╭︿︿︿╮  ╭︿︿︿╮
// {/ o o /} {/ . . /} {/ ︿︿ /}
// ( (oo) )  ( (oo) )  ( (oo) )
//   ︶︶︶     ︶︶︶     ︶︶︶

public class PersonalCircleAdapter extends BaseAdapter {
    List<CircleDetail.CommentBean> datas;
    private Context context;

    public PersonalCircleAdapter(List<CircleDetail.CommentBean> datas, Context context) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        CircleDetail.CommentBean commentBean = datas.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_personalcircle, null);
            viewHolder = new ViewHolder();
            viewHolder.imv_head = convertView.findViewById(R.id.imv_head);
            viewHolder.name = convertView.findViewById(R.id.tv_name);
            viewHolder.con = convertView.findViewById(R.id.tv_content);
            viewHolder.time = convertView.findViewById(R.id.tv_time);
            viewHolder.repName = convertView.findViewById(R.id.tv_circle_rep_name);
            viewHolder.rep = convertView.findViewById(R.id.btn_circlr_repest);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(commentBean.getNikcname_user());
        viewHolder.con.setText(commentBean.getContent());
        viewHolder.time.setText(commentBean.getCreate_time());
        viewHolder.repName.setText(commentBean.getNikcname_reply_user());
        viewHolder.imv_head.setmDrawShapeType(CircleImageView.SHAPE_CIRCLE);
        viewHolder.rep.setOnClickListener(view -> {
            if (requestPeople != null)
                requestPeople.onClicked(commentBean.getCircle_id(), commentBean.getUser_id());
        });
        return convertView;
    }

    public interface RequestPeople {
        void onClicked(String circleId, String reqlyId);
    }

    private RequestPeople requestPeople;

    public void setRequestPeople(RequestPeople requestPeople) {
        this.requestPeople = requestPeople;
    }

    static public class ViewHolder {
        CircleImageView imv_head;
        TextView name;
        TextView con;
        TextView time;
        TextView repName;
        Button rep;

    }


}
