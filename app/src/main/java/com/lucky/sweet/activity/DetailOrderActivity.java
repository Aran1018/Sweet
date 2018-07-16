package com.lucky.sweet.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.ParcelUuid;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lucky.sweet.R;
import com.lucky.sweet.entity.DetailOrderInfo;
import com.lucky.sweet.service.CommunicationService;
import com.lucky.sweet.views.MyToast;
import com.lucky.sweet.views.StepView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class DetailOrderActivity extends BaseActivity {
    public static final String INDENT_ID = "indent_id";
    String[] stepTexts = new String[]{"订单已提交", "商家已接单", "配送中", "已送达"};
    private TextView shopName;
    private TextView createTime;
    private TextView detailCost;

    public static void newInitialize(Activity activity, String indent_id) {
        Intent intent = new Intent(activity, DetailOrderActivity.class);
        intent.putExtra(INDENT_ID, indent_id);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);
        initView();
        setIsBindEventBus();
    }

    private void initView() {
        shopName = findViewById(R.id.tv_detail_shop_name);
        createTime = findViewById(R.id.tv_detail_create_time);
        detailCost = findViewById(R.id.tv_detail_cost);
        StepView step_view = findViewById(R.id.step_view);
        step_view.setStepTexts(stepTexts);
        step_view.setCurrentStep(2);
    }

    @Override
    void onServiceBind(CommunicationService.MyBinder myBinder) {

        myBinder.getDetailedOrderInfo(getIntent().getStringExtra(INDENT_ID));
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Evnet(DetailOrderInfo info) {

        createTime.setText(info.getCreate_time());
        shopName.setText(info.getMer_name());
        if (info.getMoney().equals("")) {
            info.setMoney("无消费金额");
        }
        detailCost.setText(info.getMoney());
//        Toast.makeText(this, info.toString(), Toast.LENGTH_SHORT).show();
        MyToast.showShort(info.toString());

    }
}
