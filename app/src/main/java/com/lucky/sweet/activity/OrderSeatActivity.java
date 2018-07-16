package com.lucky.sweet.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.lucky.sweet.R;
import com.lucky.sweet.entity.OrderDisInfo;
import com.lucky.sweet.entity.PerdetermingEntity;
import com.lucky.sweet.entity.ShopCarSingleInformation;
import com.lucky.sweet.service.CommunicationService;
import com.lucky.sweet.utility.RegularUtils;
import com.lucky.sweet.views.DishesOrderDialog;
import com.lucky.sweet.views.MyToast;
import com.lucky.sweet.widgets.Title;
import com.lucky.sweet.widgets.ToolBar;

/**
 * Created by Qiuyue on 2017/12/19.
 */

// ╭︿︿︿╮  ╭︿︿︿╮  ╭︿︿︿╮
// {/ o o /} {/ . . /} {/ ︿︿ /}
// ( (oo) )  ( (oo) )  ( (oo) )
//   ︶︶︶     ︶︶︶     ︶︶︶

public class OrderSeatActivity extends BaseActivity {
    private CommunicationService.MyBinder myBinder;
    private Title title = null;
    private TextView tv_timeSelect;
    private TextView edt_num;
    private EditText et_input_phone;
    private EditText edt_people_nam;
    private EditText edt_order_des;
    private String[] data;
    private String[][] time;
    private static boolean nullShopCar = false;
    private static String SHOP_CAR_INFO = "DATA";
    private static String MER_ID = "1";
    private static String SHOP_NAME = "1";

    public static void newInstance(Activity activity, ShopCarSingleInformation shopCarSingleInformation) {
        nullShopCar = false;
        Intent intent = new Intent(activity, OrderSeatActivity.class);
        intent.putExtra(SHOP_CAR_INFO, shopCarSingleInformation);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.act_left_in, R.anim.act_left_out);
    }

    public static void newInstance(String mer_id, String shopName, Activity activity) {
        nullShopCar = true;
        Intent intent = new Intent(activity, OrderSeatActivity.class);
        intent.putExtra(MER_ID, mer_id);
        intent.putExtra(SHOP_NAME, shopName);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderseat);

        initView();

        initData();

        initEvent();

    }

    private void initData() {

    }

    @Override
    void onServiceBind(CommunicationService.MyBinder myBinder) {
        this.myBinder = myBinder;
        myBinder.requestPerdeterming(this);
    }

    private void initView() {
        tv_timeSelect = findViewById(R.id.tv_timeSelect);
        edt_num = findViewById(R.id.edt_num);
        et_input_phone = findViewById(R.id.et_input_phone);
        edt_people_nam = findViewById(R.id.edt_people_nam);
        edt_order_des = findViewById(R.id.edt_order_des);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        initTitle();
    }

    public void upDateRequest(PerdetermingEntity entity) {
        data = entity.getData();
        time = entity.getTime();
    }

    private void initEvent() {

        //  findViewById(R.id.btn_order_commit).setOnClickListener(v -> verifyOrder());
    }

    private void verifyOrder() {/*
        String phone = et_input_phone.getText().toString().trim();
        boolean result = RegularUtils.isPhoneNumber(phone);
        if (result == true) {
            AlertDialog dialog = new AlertDialog.Builder(OrderSeatActivity.this)
                    .setMessage("需要提前点菜吗？")
                    .setNegativeButton("直接订座", (dialog12, which) -> {
                        sendOrderSeatInfo();
                        dialog12.dismiss();
                    })
                    .setPositiveButton("提前点菜", (dialog1, which) -> {
                        sendOrderSeatInfo();
                        MerchantActivity.newSingleOrderInStance(OrderSeatActivity.this, mer_id);
                        goNextAnim();

                    })
                    .create();
            dialog.show();
        } else {
            MyToast.showShort("手机号不对哦，请重新输入");

        }*/
    }

    private void initTitle() {
        ToolBar toolBar = new ToolBar(this);
        toolBar.setColorNewBar(getResources().getColor(R.color.white), 0);
        title = (Title) findViewById(R.id.title);
        title.setTitleNameStr("预订座位");
        Title.ButtonInfo buttonLeft = new Title.ButtonInfo(true, Title
                .BUTTON_LEFT, 0, "取消");
        Title.ButtonInfo buttonRight = new Title.ButtonInfo(true, Title.BUTTON_RIGHT1, 0, "完成");
        title.setOnTitleButtonClickListener((id, viewHolder) -> {
            switch (id) {
                case Title.BUTTON_LEFT:
                    hintInputKb();
                    finish();
                    goPreAnim();
                    break;
                case Title.BUTTON_RIGHT1:
                    //verifyOrder();
                    //startActivity(new Intent(OrderSeatActivity.this, OrderSubmitActivity.class));
                    break;
            }
        });

        title.mSetButtonInfo(buttonLeft);
        title.mSetButtonInfo(buttonRight);
    }

    private OrderDisInfo sendOrderSeatInfo() {
        String time = tv_timeSelect.getText().toString().trim();
        String num = edt_num.getText().toString().trim();
        String name = edt_people_nam.getText().toString().trim();
        String phone = et_input_phone.getText().toString().trim();
        String des = edt_order_des.getText().toString().trim();
        if (TextUtils.isEmpty(time)) {
            MyToast.showShort("时间不能为空");
            return null;
        }
        if (TextUtils.isEmpty((num))) {
            MyToast.showShort("人数不能为空");
            return null;
        }
        if (TextUtils.isEmpty(name)) {
            MyToast.showShort("名字不能为空");
            return null;
        }
        if (TextUtils.isEmpty(phone)) {
            MyToast.showShort("电话不能为空");
            return null;
        }
        OrderDisInfo orderDisInfo = new OrderDisInfo();
        orderDisInfo.setDis(des);
        orderDisInfo.setName(name);
        orderDisInfo.setPeopleNum(num);
        orderDisInfo.setTime(time);
        orderDisInfo.setPhone(phone);
        return orderDisInfo;
    }


    public void onButtonClick(View view) {
        switch (view.getId()) {
            case R.id.btn_order_commit:
           /*     if (MORE_PEOPLE) {

                    OrderSubmitActivity.newInstance( getIntent().getStringExtra(SHOP_CAR_INFO));

                } else {*/
                OrderDisInfo orderDisInfo = sendOrderSeatInfo();
                if (orderDisInfo != null) {
                    if (!nullShopCar) {
                        OrderSubmitActivity.newInstance(OrderSeatActivity.this, (ShopCarSingleInformation) getIntent().getSerializableExtra(SHOP_CAR_INFO), orderDisInfo);
                    }else {
                        String merId = getIntent().getStringExtra(MER_ID);
                        String shopName = getIntent().getStringExtra(SHOP_NAME);
                        OrderSubmitActivity.newInstance(OrderSeatActivity.this, merId ,shopName  );
                    }

                }
                /*         }*/
                break;
            case R.id.tv_timeSelect:

                DishesOrderDialog dishesOrderDialog = new DishesOrderDialog
                        (OrderSeatActivity.this, data, time);
                dishesOrderDialog.setDateSelectListener
                        (dates -> tv_timeSelect.setText(dates));
                break;
        }
    }


}
