package com.lucky.sweet.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lucky.sweet.R;
import com.lucky.sweet.adapter.HisOrderAdapter;
import com.lucky.sweet.adapter.MyCommentAdapter;
import com.lucky.sweet.entity.AlterOrderInfo;
import com.lucky.sweet.entity.DetailOrderInfo;
import com.lucky.sweet.service.CommunicationService;
import com.lucky.sweet.views.PinnedHeaderListView;
import com.lucky.sweet.widgets.Title;
import com.lucky.sweet.widgets.ToolBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * Created by Qiuyue on 2018/3/2.
 */

// ╭︿︿︿╮  ╭︿︿︿╮  ╭︿︿︿╮
// {/ o o /} {/ . . /} {/ ︿︿ /}
// ( (oo) )  ( (oo) )  ( (oo) )
//   ︶︶︶     ︶︶︶     ︶︶︶

public class HistoricalOderActivity extends BaseActivity {

    private Title title = null;
    private ListView lv_hisOrder;
    private HisOrderAdapter hisOrderAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historicalorder);
        setIsBindEventBus();
        initTitle();
        initViews();
        initEvent();
    }

    private void initEvent() {
        lv_hisOrder.setOnItemClickListener((parent, view, position, id) -> {

            DetailOrderActivity.newInitialize(HistoricalOderActivity.this, hisOrderAdapter.getItem(position).getIndent_id());
        });
    }

    private void initViews() {
        lv_hisOrder = findViewById(R.id.lv_hisOrder);

    }

    private void initTitle() {
        ToolBar toolBar = new ToolBar(this);
        toolBar.setColorNewBar(getResources().getColor(R.color.white), 0);
        title = (Title) findViewById(R.id.title);
        title.setTitleNameStr("历史订单");

        Title.ButtonInfo buttonLeft = new Title.ButtonInfo(true, Title
                .BUTTON_LEFT, R.drawable.selector_btn_titleback, null);

        title.setOnTitleButtonClickListener((id, viewHolder) -> {
            switch (id) {
                case Title.BUTTON_LEFT:
                    finish();
                    goPreAnim();
                    break;
            }
        });

        title.mSetButtonInfo(buttonLeft);
    }

    @Override
    void onServiceBind(CommunicationService.MyBinder myBinder) {
        myBinder.getAlterOrder();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(AlterOrderInfo orderInfo) {
        hisOrderAdapter = new HisOrderAdapter(orderInfo.getUnfinish_indent_list(), this);
        lv_hisOrder.setAdapter(hisOrderAdapter);

    }

}
