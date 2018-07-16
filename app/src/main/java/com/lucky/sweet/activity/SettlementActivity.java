package com.lucky.sweet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gospelware.liquidbutton.LiquidButton;
import com.lucky.sweet.R;
import com.lucky.sweet.service.CommunicationService;

public class SettlementActivity extends BaseActivity {

    private LiquidButton btn_liquid;
    private TextView tv_settle_title;
    private Button btn_finishOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settlement);

        initView();

        initEvent();
    }

    private void initView() {
        btn_liquid = findViewById(R.id.btn_liquid);
        tv_settle_title = findViewById(R.id.tv_settle_title);
        btn_finishOrder = findViewById(R.id.btn_finishOrder);


    }

    private void initEvent() {
        findViewById(R.id.btn_finishOrder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettlementActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));

            }
        });
        btn_liquid.setFillAfter(true);
        btn_liquid.setAutoPlay(true);
        btn_liquid.startPour();
        btn_liquid.setPourFinishListener(new LiquidButton.PourFinishListener() {
            @Override
            public void onPourFinish() {
                tv_settle_title.setVisibility(View.VISIBLE);
                btn_finishOrder.setVisibility(View.VISIBLE);
            }

            @Override
            public void onProgressUpdate(float progress) {

            }
        });
    }

    @Override
    void onServiceBind(CommunicationService.MyBinder myBinder) {

    }
}
