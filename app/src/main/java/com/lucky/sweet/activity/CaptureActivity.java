package com.lucky.sweet.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lucky.sweet.R;
import com.lucky.sweet.entity.DecodeEntity;
import com.lucky.sweet.model.qrcodescan.ScanningQR;
import com.lucky.sweet.views.MyToast;
import com.lucky.sweet.views.SlidingLayoutView;
import com.lucky.sweet.widgets.ToolBar;


/**
 * 拍照的Activity
 */
public class CaptureActivity extends FragmentActivity {
    private ScanningQR fragment;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
        initView();
        initEvent();
        initToolBar();
        SlidingLayoutView rootView = new SlidingLayoutView(this);
        rootView.bindActivity(this);
    }

    private void initToolBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ToolBar toolBar = new ToolBar(this);
            toolBar.setImmersionBar();
            int statusBarHeight = toolBar.getStatusBarHeight(this);
            View view_margin = findViewById(R.id.view_margin);
            ViewGroup.LayoutParams lp;
            lp = view_margin.getLayoutParams();
            lp.height = statusBarHeight;
            view_margin.setLayoutParams(lp);
        } else {
        }
    }

    private void initView() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        fragment = (ScanningQR) supportFragmentManager.findFragmentById(R.id.fragment_scanningqr);
        findViewById(R.id.mo_scanner_photo);
    }

    private void initEvent() {
        fragment.setBackOnClickListener(new ScanningQR.BackOnClickListener() {
            @Override
            public void setonBackClickListener(View view) {
                finish();
            }
        });
        fragment.setObtainResultCodeListener(new ScanningQR.GetDecode() {
            @Override
            public void getDecodeString(String decode) {

                DecodeEntity decodeEntity;
                try {
                    decodeEntity = new Gson().fromJson(decode, DecodeEntity.class);

                } catch (JsonSyntaxException e) {
                    MyToast.showShort("请勿解析其他二维码");

                    finish();
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(CaptureActivity.this);
                builder.setTitle("提示").setMessage("确认加入购物！").setPositiveButton("确认", (dialogInterface, i) -> {
                    MerchantActivity.newMoreOrderInStance(CaptureActivity.this, decodeEntity.getMerId(), decodeEntity.getRoomId(), decodeEntity.getName() );
                    finish();
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                }).setNegativeButton("取消", (dialogInterface, i) -> finish()).create();
                AlertDialog show = builder.show();
                show.setCanceledOnTouchOutside(false);


            }
        });
    }
}