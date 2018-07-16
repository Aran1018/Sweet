package com.lucky.sweet.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.lucky.sweet.R;
import com.lucky.sweet.adapter.HisOrderAdapter;
import com.lucky.sweet.entity.AlterOrderInfo;
import com.lucky.sweet.service.CommunicationService;
import com.lucky.sweet.views.MyToast;
import com.lucky.sweet.widgets.Title;
import com.lucky.sweet.widgets.ToolBar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by Qiuyue on 2018/3/7.
 */

// ╭︿︿︿╮  ╭︿︿︿╮  ╭︿︿︿╮
// {/ o o /} {/ . . /} {/ ︿︿ /}
// ( (oo) )  ( (oo) )  ( (oo) )
//   ︶︶︶     ︶︶︶     ︶︶︶

public class WaitOrderActivity extends BaseActivity {

    private Title title = null;
    private ListView lv_wait_order;

    @Override
    void onServiceBind(CommunicationService.MyBinder myBinder) {
        myBinder.getWaitOrder();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waitorder);
        initView();
        setIsBindEventBus();
    }

    private void initView() {
        lv_wait_order = findViewById(R.id.lv_wait_order);
        initTitle();

    }

    private void initTitle() {
        ToolBar toolBar = new ToolBar(this);
        toolBar.setColorNewBar(getResources().getColor(R.color.white), 0);
        title = findViewById(R.id.title);
        title.setTitleNameStr("等待订单");
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(AlterOrderInfo info) {
        List<AlterOrderInfo.UnfinishIndentListBean> listBeans = info.getUnfinish_indent_list();
        if (listBeans.size() != 0) {
            HisOrderAdapter hisOrderAdapter = new HisOrderAdapter(listBeans, this);
            lv_wait_order.setAdapter(hisOrderAdapter);
        } else MyToast.showShort("暂时无等待订单");


    }

}
