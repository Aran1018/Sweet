package com.lucky.sweet.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lucky.sweet.R;
import com.lucky.sweet.entity.VipCardInfo;
import com.lucky.sweet.service.CommunicationService;
import com.lucky.sweet.widgets.Title;
import com.lucky.sweet.widgets.ToolBar;
import com.lucky.sweet.widgets.cardstack.CardStackView;
import com.lucky.sweet.widgets.cardstack.VipCardAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;


public class VipCardActiviry extends BaseActivity {

    private CardStackView cardStackView;
    private TextView tv_vip_null;
    private TextView tv_person_vip_null;


    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_card_activiry);

        initTitle();

        initView();

    }

    @Override
    void onServiceBind(CommunicationService.MyBinder myBinder) {
        myBinder.requestPersonVipCard();
    }

    private void initTitle() {
        Title title = findViewById(R.id.title);
        title.setTitleNameStr("我的卡包");
        Title.ButtonInfo buttonLeft = new Title.ButtonInfo(true, Title
                .BUTTON_LEFT, R.drawable.selector_btn_titleback, null);
        title.setOnTitleButtonClickListener(new Title
                .OnTitleButtonClickListener() {
            @Override
            public void onClick(int id, Title.ButtonViewHolder viewHolder) {
                switch (id) {
                    case Title.BUTTON_LEFT:
                        finish();
                        goPreAnim();
                        break;
                }
            }
        });
        title.mSetButtonInfo(buttonLeft);
    }

    private void initView() {
        ToolBar toolBar = new ToolBar(this);
        toolBar.setColorNewBar(getResources().getColor(R.color.white), 0);
        cardStackView = findViewById(R.id.stackview_vip_card);
        tv_person_vip_null = findViewById(R.id.tv_person_vip_null);

        cardStackView.setItemExpendListener(new CardStackView.ItemExpendListener() {
            @Override
            public void onItemExpend(boolean expend) {

            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(VipCardInfo info) {
        List<VipCardInfo.VipListBean> vip_list = info.getVip_list();
        if (vip_list.size()!=0) {
            Log.i("size",vip_list.size()+"");
            VipCardAdapter objectStackAdapter = new VipCardAdapter(this,vip_list);
            cardStackView.setAdapter(objectStackAdapter);
            tv_person_vip_null.setVisibility(View.GONE);
        }else cardStackView.setVisibility(View.GONE);


    }
}
