package com.lucky.sweet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lucky.sweet.R;
import com.lucky.sweet.service.CommunicationService;
import com.lucky.sweet.widgets.ToolBar;

/**
 * Created by Qiuyue on 2018/3/7.
 */

// ╭︿︿︿╮  ╭︿︿︿╮  ╭︿︿︿╮
// {/ o o /} {/ . . /} {/ ︿︿ /}
// ( (oo) )  ( (oo) )  ( (oo) )
//   ︶︶︶     ︶︶︶     ︶︶︶

public class FloatPageActivity extends BaseActivity {

    private View view_null1;
    private View view_null2;
    private Button btn_wait;
    private Button btn_join;

    @Override
    void onServiceBind(CommunicationService.MyBinder myBinder) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floatpage);
        initViews();
        initEvent();
    }

    private void initEvent() {
        view_null1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                goFadeAnim();
            }
        });
        view_null2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                goFadeAnim();
            }
        });
        btn_wait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FloatPageActivity.this,
                        WaitOrderActivity.class);
                startActivity(intent);
                goNextAnim();
            }
        });
        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FloatPageActivity.this,CreateRoomActivity.class);
                startActivity(intent);
                goNextAnim();

            }
        });
    }

    private void initViews() {
        ToolBar toolBar = new ToolBar(this);
        toolBar.setImmersionBar();
        view_null1 = (View) findViewById(R.id.view_null1);
        view_null2 = (View) findViewById(R.id.view_null2);
        btn_wait = (Button) findViewById(R.id.btn_wait);
        btn_join = (Button) findViewById(R.id.btn_join);
    }

}
