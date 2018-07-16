package com.lucky.sweet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lucky.sweet.R;
import com.lucky.sweet.service.CommunicationService;
import com.lucky.sweet.widgets.Title;
import com.lucky.sweet.widgets.ToolBar;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Qiuyue on 2018/2/14.
 */

// ╭︿︿︿╮  ╭︿︿︿╮  ╭︿︿︿╮
// {/ o o /} {/ . . /} {/ ︿︿ /}
// ( (oo) )  ( (oo) )  ( (oo) )
//   ︶︶︶     ︶︶︶     ︶︶︶

public class MultiOrderActivity extends BaseActivity {

    @Bind(R.id.tv_room_id)
    TextView tv_room_id;
    @Bind(R.id.edt_create_room)
    EditText edt_create_room;
    @Bind(R.id.btn_create_room)
    Button btn_create_room;

    @Bind(R.id.edt_join_room)
    EditText edt_join_room;
    @Bind(R.id.edt_join_sec)
    EditText edt_join_sec;
    @Bind(R.id.btn_join_room)
    Button btn_join_room;
    private Title title = null;
    private CommunicationService.MyBinder myBinder;
    private String mer_id;
    private String roomId;

    @Override
    void onServiceBind(CommunicationService.MyBinder myBinder) {
        this.myBinder = myBinder;

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordermulti);

        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        mer_id = intent.getStringExtra("mer_id");
        initTitle();
    }


    private void initTitle() {
        ToolBar toolBar = new ToolBar(this);
        toolBar.setColorNewBar(getResources().getColor(R.color.white), 0);
        title = findViewById(R.id.title);
        title.setTitleNameStr("多人订单");
        Title.ButtonInfo buttonLeft = new Title.ButtonInfo(true, Title
                .BUTTON_LEFT, 0, "取消");
        title.setOnTitleButtonClickListener((id, viewHolder) -> {
            switch (id) {
                case Title.BUTTON_LEFT:
                    finish();
                    break;
            }
        });

        title.mSetButtonInfo(buttonLeft);
    }

    @OnClick({R.id.btn_create_room, R.id.btn_join_room})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;

            case R.id.btn_create_room:
                myBinder.createReserveRoom(mer_id);
                break;

            case R.id.btn_join_room:

                roomId = edt_join_room.getText().toString();

                myBinder.joinInReserveRoom(roomId.trim(), edt_join_sec.getText().toString().trim());

                break;

        }
    }


}
