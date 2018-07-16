package com.lucky.sweet.widgets.cardstack;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lucky.sweet.R;
import com.lucky.sweet.activity.StoreParticularInfoActivity;
import com.lucky.sweet.entity.VipCardInfo;
import com.lucky.sweet.views.CircleImageView;

import java.util.List;

public class VipCardAdapter extends
        StackAdapter<VipCardAdapter.viewHolder> {

    private List<VipCardInfo.VipListBean> arrayList;
    private Context context;

    public static Integer[] TEST_DATAS = new Integer[]{
            R.color.bg_vip_card_color_first,
            R.color.bg_vip_card_color_sec,
            R.color.bg_vip_card_color_third,
            R.color.bg_vip_card_color_fourth,
            R.color.bg_vip_card_color_fifth,
    };

    public VipCardAdapter(Context context, List<VipCardInfo.VipListBean> arrayList) {
        super(context);
        this.context = context;
        this.arrayList = arrayList;
        updateData(arrayList.size());
    }

    @Override
    public void bindView(int position, CardStackView.ViewHolder holder) {

        viewHolder viewHolder = (viewHolder) holder;
        viewHolder.mLayout.getBackground().setColorFilter(ContextCompat.getColor(getContext(), TEST_DATAS[position % TEST_DATAS.length]), PorterDuff.Mode.SRC_IN);
        final VipCardInfo.VipListBean vipListBean = arrayList.get(position);

        viewHolder.id.setText(vipListBean.getVip_date_id());
        viewHolder.shopName.setText(vipListBean.getName());
        viewHolder.vipdes.setText(vipListBean.getVip_describe());
        viewHolder.phone.setText(vipListBean.getPhone());
        viewHolder.desLevel.setText(vipListBean.getVIP_name());
        String vip_name = vipListBean.getVIP_name();

        if (!vip_name.equals("")) {
            viewHolder.level.setText(vip_name);
            viewHolder.desLevel.setText(vip_name);

        } else {
            viewHolder.level.setText("暂无");
            viewHolder.desLevel.setText("暂无");
        }
        viewHolder.inter.setText(vipListBean.getIntegral());
        viewHolder.enterShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, StoreParticularInfoActivity
                        .class);
                intent.putExtra("shopid", vipListBean.getMer_id());
                context.startActivity(intent);
            }
        });
    }

    @Override
    protected CardStackView.ViewHolder onCreateView(ViewGroup parent, int viewType) {
        return new viewHolder(getLayoutInflater().inflate(R
                        .layout.item_card_list_with_no_header,
                parent, false));
    }


    static class viewHolder extends CardStackView.ViewHolder {
        View mLayout;
        View mContainerContent;


        TextView shopName;
        TextView shopLocation;
        TextView id;
        TextView level;
        TextView enterShop;
        TextView vipdes;
        TextView phone;
        TextView inter;
        TextView desLevel;

        CircleImageView pic;

        public viewHolder(View view) {
            super(view);

            mLayout = view.findViewById(R.id.frame_list_card_item);
            mContainerContent = view.findViewById(R.id.container_list_content);

            pic = view.findViewById(R.id.imv_list_card_title);
            pic.setmDrawShapeType(CircleImageView.SHAPE_CIRCLE);

            shopName = view.findViewById(R.id.tv_list_card_shopname);
            shopLocation = view.findViewById(R.id.tv_list_card_shoplocation);

            level = view.findViewById(R.id.tv_list_card_userlevel);
            id = view.findViewById(R.id.tv_list_card_user_id);
            vipdes = view.findViewById(R.id.tv_person_vip_des);
            desLevel = view.findViewById(R.id.tv_person_vip_des_level);
            enterShop = view.findViewById(R.id.tv_person_vip_enter_shop);
            inter = view.findViewById(R.id.tv_person_vip_inter);
            phone = view.findViewById(R.id.tv_person_vip_phone);
        }

        @Override
        public void onItemExpand(boolean b) {
            mContainerContent.setVisibility(b ? View.VISIBLE : View.GONE);
        }

    }


}
