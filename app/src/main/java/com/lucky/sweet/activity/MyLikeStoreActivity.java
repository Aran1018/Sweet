package com.lucky.sweet.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.lucky.sweet.R;
import com.lucky.sweet.adapter.HisOrderAdapter;
import com.lucky.sweet.adapter.MyLikeStoreAdapter;
import com.lucky.sweet.entity.PersonCollectInfo;
import com.lucky.sweet.service.CommunicationService;
import com.lucky.sweet.widgets.Title;
import com.lucky.sweet.widgets.ToolBar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Qiuyue on 2018/3/8.
 */

// ╭︿︿︿╮  ╭︿︿︿╮  ╭︿︿︿╮
// {/ o o /} {/ . . /} {/ ︿︿ /}
// ( (oo) )  ( (oo) )  ( (oo) )
//   ︶︶︶     ︶︶︶     ︶︶︶

public class MyLikeStoreActivity extends BaseActivity {

    private Title title = null;
    private ListView lv_likeStore;
    private TextView collect_name;


    @Override
    void onServiceBind(CommunicationService.MyBinder myBinder) {
        myBinder.getPersonCollect();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylikestore);
        setIsBindEventBus();
        initTitle();
        initViews();
    }

    private void initViews() {
        lv_likeStore =  findViewById(R.id.lv_likeStore);
        collect_name = findViewById(R.id.tv_person_collec_shop_show);
    }

    private void initTitle() {
        ToolBar toolBar = new ToolBar(this);
        toolBar.setColorNewBar(getResources().getColor(R.color.white), 0);
        title = (Title) findViewById(R.id.title);
        title.setTitleNameStr("收藏店铺");
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(PersonCollectInfo info) {
        List<PersonCollectInfo.CollectListBean> collect_list = info.getCollect_list();
        if (collect_list.size()!=0) {
            collect_name.setVisibility(View.GONE);
        }
        lv_likeStore.setAdapter(new MyLikeStoreAdapter(info.getCollect_list(), this));

    }
}
