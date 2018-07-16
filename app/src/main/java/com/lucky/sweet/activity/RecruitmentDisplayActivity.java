package com.lucky.sweet.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lucky.sweet.R;
import com.lucky.sweet.entity.ShopDetailPicInfo;
import com.lucky.sweet.service.CommunicationService;
import com.lucky.sweet.views.MyToast;
import com.lucky.sweet.views.PictureDisplayLayout;
import com.lucky.sweet.views.TitleIndicatorView;
import com.lucky.sweet.widgets.ToolBar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class RecruitmentDisplayActivity extends BaseActivity {

    private CommunicationService.MyBinder myBinder;
    private PictureDisplayLayout photo_show;
    private TitleIndicatorView titie_search;
    private String[] title = {"菜品", "环境"};

    private static final String MER_ID = "MERID";
    private static final String SHOW_TYPE = "TYPE";

    public static final String DISC_SHOW = "1";
    public static final String AROUND_SHOW = "2";

    public static void newStance(Activity activity, String type, String mer_id) {
        Intent intent = new Intent(activity, RecruitmentDisplayActivity.class);
        intent.putExtra(SHOW_TYPE, type);
        intent.putExtra(MER_ID, mer_id);
        activity.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruitment_display);
        ToolBar toolBar = new ToolBar(this);
        toolBar.setColorNewBar(getResources().getColor(R.color.white), 0);
        setIsBindEventBus();
        initView();
        initEvent();
    }

    @Override
    void onServiceBind(CommunicationService.MyBinder myBinder) {
        Intent intent = getIntent();
        String mer_id = intent.getStringExtra(MER_ID);
        String type = intent.getStringExtra(SHOW_TYPE);
        myBinder.getShopDetailInfo(mer_id, type);
        titie_search.setOnClickItem(title[Integer.parseInt(type)-1]);
        this.myBinder = myBinder;


    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(ShopDetailPicInfo info) {
        List<String> mer_photo = info.getMer_photo();
        if (mer_photo.size() != 0) {
            if (photo_show.getVisibility() == View.GONE) {
                photo_show.setVisibility(View.VISIBLE);
            }
            photo_show.initDatas(info.getMer_photo());
        } else {
            photo_show.setVisibility(View.GONE);
            MyToast.showShort("暂无相关图片");


        }
    }

    private void initView() {
        photo_show = findViewById(R.id.ll_cover_photo_show);
        titie_search = findViewById(R.id.titie_search);
        titie_search.setOnTitleListener(type -> {
            switch (type) {
                case "菜品":
                    type = DISC_SHOW;
                    break;
                case "环境":
                    type = AROUND_SHOW;
                    break;
            }

            Intent intent = getIntent();
            String mer_id = intent.getStringExtra(MER_ID);
            myBinder.getShopDetailInfo(mer_id, type);
        });
        titie_search.initializationData(title);
    }

    private void initEvent() {
        findViewById(R.id.btn_back_store).setOnClickListener(v -> {
            finish();
            goDownAnim();
        });

    }


}
