package com.lucky.sweet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.lucky.sweet.R;
import com.lucky.sweet.model.shoppingcar.mode.ShopProduct;
import com.lucky.sweet.model.wholedishes.adapter.TravelViewPagerAdapter;
import com.lucky.sweet.model.wholedishes.lib.ExpandingPagerFactory;
import com.lucky.sweet.model.wholedishes.lib.fragments.ExpandingFragment;
import com.lucky.sweet.service.CommunicationService;
import com.lucky.sweet.widgets.ToolBar;

import java.util.ArrayList;

public class DischesViewShowActivity extends BaseActivity implements
        ExpandingFragment.OnExpandingClickListener {

    private Button btn_cancle;
    private ViewPager vp_dishes;
    private ArrayList<ShopProduct> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disches_view_show);

        ToolBar toolBar = new ToolBar(this);
        toolBar.setStatusBarDarkMode();

        initView();

        initData();

        initEvent();
    }

    @Override
    void onServiceBind(CommunicationService.MyBinder myBinder) {

    }

    private void initEvent() {
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        vp_dishes.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                ExpandingFragment expandingFragment = ExpandingPagerFactory.getCurrentFragment(vp_dishes);
                if (expandingFragment != null && expandingFragment.isOpenend()) {
                    expandingFragment.close();
                }
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initData() {

        Intent intent = getIntent();

        int positon = intent.getIntExtra("position", 0);
        data = (ArrayList<ShopProduct>) intent.getSerializableExtra("data");
        TravelViewPagerAdapter adapter = new TravelViewPagerAdapter(getSupportFragmentManager(), data);


        ExpandingPagerFactory.setupViewPager(vp_dishes);
        vp_dishes.setAdapter(adapter);
        vp_dishes.setCurrentItem(positon);

    }

    @Override
    public void onBackPressed() {
        if (!ExpandingPagerFactory.onBackPressed(vp_dishes)) {
            super.onBackPressed();
        }
    }


    private void initView() {

        vp_dishes = findViewById(R.id.vp_dishes);
        btn_cancle = findViewById(R.id.btn_cancle);

    }

    protected boolean enableSliding() {
        return false;
    }

    @Override
    public void onExpandingClick(View v) {
        ShopProduct shopProduct = data.get(vp_dishes.getCurrentItem());
        Intent intent = new Intent(this, DetailedDishesActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.act_left_in, R.anim.act_left_out);
    }
}
