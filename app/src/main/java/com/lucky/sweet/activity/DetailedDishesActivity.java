package com.lucky.sweet.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;

import com.lucky.sweet.R;
import com.lucky.sweet.adapter.ProductComentAdapter;
import com.lucky.sweet.service.CommunicationService;
import com.lucky.sweet.widgets.ToolBar;
import com.lucky.sweet.widgets.ZoomInScrollView;

import java.util.ArrayList;

public class DetailedDishesActivity extends BaseActivity {

    private static final String EXTRA_TRAVEL = "DETAILED_DISHES";
    private ListView lv_productcoment;
    private ZoomInScrollView sc_zoom;
    private ImageButton fl_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_dishes);

        initViews();

        initData();

        initEvent();
    }

    private void initData() {
        ArrayList<String> objects = new ArrayList<String>();
        objects.add("");
        objects.add("");
        objects.add("");
        lv_productcoment.setAdapter(new ProductComentAdapter(objects, this,
                lv_productcoment));


    }

    @Override
    protected void onResume() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sc_zoom.fullScroll(ScrollView.FOCUS_UP);
            }
        }, 50);

        super.onResume();
    }



    private void initEvent() {
        fl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                goPreAnim();
            }
        });
    }

    public void initViews() {
        ToolBar toolBar = new ToolBar(this);
        toolBar.setImmersionBar();
        fl_back = findViewById(R.id.fl_back);
        sc_zoom = findViewById(R.id.sc_zoom);
        lv_productcoment = findViewById(R.id.lv_productcoment);


    }


    @Override
    void onServiceBind(CommunicationService.MyBinder myBinder) {

    }


}
