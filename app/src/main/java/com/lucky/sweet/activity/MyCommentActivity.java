package com.lucky.sweet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lucky.sweet.R;
import com.lucky.sweet.adapter.MyCommentAdapter;
import com.lucky.sweet.adapter.PersonalCircleAdapter;
import com.lucky.sweet.entity.MyCommentEntiy;
import com.lucky.sweet.service.CommunicationService;
import com.lucky.sweet.views.ShopCarPopWindow;
import com.lucky.sweet.widgets.Title;
import com.lucky.sweet.widgets.ToolBar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Qiuyue on 2018/1/31.
 */

// ╭︿︿︿╮  ╭︿︿︿╮  ╭︿︿︿╮
// {/ o o /} {/ . . /} {/ ︿︿ /}
// ( (oo) )  ( (oo) )  ( (oo) )
//   ︶︶︶     ︶︶︶     ︶︶︶

public class MyCommentActivity extends BaseActivity {

    private Title title = null;
    private ListView lv_myComment;

    @Override
    void onServiceBind(CommunicationService.MyBinder myBinder) {
        myBinder.getPersonComment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycomment);
        setIsBindEventBus();
        initTitle();
        initViews();
    }

    private void initViews() {
        lv_myComment = (ListView)findViewById(R.id.lv_myComment);

        lv_myComment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int i, long l) {
                Intent intent = new Intent(MyCommentActivity.this, PersonalCircleActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.act_left_in, R.anim.act_left_out);
            }
        });

    }

    private void initTitle() {
        ToolBar toolBar = new ToolBar(this);
        toolBar.setColorNewBar(getResources().getColor(R.color.white), 0);
        title = (Title) findViewById(R.id.title);
        title.setTitleNameStr("我的评论");

        Title.ButtonInfo buttonLeft = new Title.ButtonInfo(true, Title
                .BUTTON_LEFT,R.drawable.selector_btn_titleback, null);

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
    public void Event(MyCommentEntiy entiy){
        List<MyCommentEntiy.CircleReplyLsitBean> circle_reply_lsit = entiy.getCircle_reply_lsit();
        lv_myComment.setAdapter(new MyCommentAdapter(circle_reply_lsit,this));
        lv_myComment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }
}
