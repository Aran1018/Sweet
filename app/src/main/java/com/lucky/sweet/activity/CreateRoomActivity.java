package com.lucky.sweet.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lucky.sweet.R;
import com.lucky.sweet.adapter.DetaileSearchFriendAdapter;
import com.lucky.sweet.adapter.FlowFriendAdapter;
import com.lucky.sweet.entity.FlowPeople;
import com.lucky.sweet.entity.InvitationInfo;
import com.lucky.sweet.entity.SearchFriendInfo;
import com.lucky.sweet.service.CommunicationService;
import com.lucky.sweet.views.HorizontalListView;
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

public class CreateRoomActivity extends BaseActivity {
    private Title title = null;

    private CommunicationService.MyBinder myBinder;
    private String mer_id;
    private final static String SHOP_NAME = "3";
    private final static String SHOPID = "mer_id";
    private HorizontalListView lv_add_friend;
    private FlowFriendAdapter flowFriendAdapter;
    private ListView lv_search_user_show;
    private EditText edt_serch;
    private String shopName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createroom);
        hintInputKb();
        setIsBindEventBus();
        initView();
        initData();
    }

    public   static void newInStrance(Activity activity, String mer_id, String shopName) {
        Intent intent = new Intent(activity, CreateRoomActivity.class);
        intent.putExtra("mer_id", mer_id);
        intent.putExtra(SHOP_NAME, shopName);
        activity.startActivity(intent);
    }

    private void initData() {
        Intent intent = getIntent();
        shopName = getIntent().getStringExtra(SHOP_NAME);
        mer_id = intent.getStringExtra(SHOPID);
    }

    private void initView() {


        lv_add_friend = findViewById(R.id.lv_add_friend);
        lv_search_user_show = findViewById(R.id.lv_search_detailed_user_show);

        findViewById(R.id.btn_user_search).setOnClickListener(v -> {
            String search = edt_serch.getText().toString();
            if (!TextUtils.isEmpty(search)) {
                myBinder.searchFriend(search);
            }
        });


        edt_serch = findViewById(R.id.edt_search_user);
        initTitle();
    }


    private void initTitle() {
        ToolBar toolBar = new ToolBar(this);
        toolBar.setColorNewBar(getResources().getColor(R.color.white), 0);
        title = findViewById(R.id.title);
        title.setTitleNameStr("创建房间");
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

    public void submitCreate(View v) {

        myBinder.createReserveRoom(mer_id);

    }


    public void addRoom(View v) {
        if (roomID != null) {
            MerchantActivity.newMoreOrderInStance(CreateRoomActivity.this, mer_id, roomID, shopName);
        }

    }


    @Override
    void onServiceBind(CommunicationService.MyBinder myBinder) {
        this.myBinder = myBinder;
        myBinder.getFlowFriends();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(FlowPeople info) {
        List<FlowPeople.AttentListBean> attent_list = info.getAttent_list();
        flowFriendAdapter = new FlowFriendAdapter(attent_list, this);
        lv_add_friend.setAdapter(flowFriendAdapter);

        flowFriendAdapter.setOnInvitationClick(userId -> {

            if (roomID != null) {
                MyToast.showShort("已经为您邀请您的好友");
                myBinder.invitationFriend(userId, new InvitationInfo(MyApplication.USER_ID, roomID, mer_id,shopName));
            } else
                MyToast.showShort("请创建房间");

        });

    }

    private String roomID;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(String info) {
        roomID = info;

        MyToast.showShort("建立房间成功"/*+ info*/);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(SearchFriendInfo info) {

        DetaileSearchFriendAdapter detaileSearchFriendAdapter = new DetaileSearchFriendAdapter(info.getUser_list(), this);
        lv_search_user_show.setAdapter(detaileSearchFriendAdapter);
        detaileSearchFriendAdapter.setOnInvitationClick(userId -> {
            if (roomID != null) {
                MyToast.showShort("已经为您邀请您的好友");
                myBinder.invitationFriend(userId, new InvitationInfo(MyApplication.USER_ID, roomID, mer_id,shopName ));
            } else
                MyToast.showShort("请创建房间");

        });

    }
}
