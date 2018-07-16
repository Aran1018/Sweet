package com.lucky.sweet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.model.GetObjectResult;
import com.google.gson.Gson;
import com.lucky.sweet.R;
import com.lucky.sweet.entity.CircleLikePoint;
import com.lucky.sweet.entity.CircleMainInfo;
import com.lucky.sweet.entity.FlowPeople;
import com.lucky.sweet.entity.MainStoreInfo;
import com.lucky.sweet.entity.PersonInfo;
import com.lucky.sweet.entity.WeatherInfo;
import com.lucky.sweet.fragment.ImCircleFragment;
import com.lucky.sweet.fragment.ImMeFragment;
import com.lucky.sweet.fragment.ImStoreFragment;
import com.lucky.sweet.service.CommunicationService;
import com.lucky.sweet.views.MyToast;
import com.lucky.sweet.widgets.Tab.TabContainerView;
import com.lucky.sweet.widgets.Tab.TabFragmentAdapter;
import com.lucky.sweet.widgets.ToolBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class MainActivity extends BaseActivity {

    final ToolBar toolBar = new ToolBar(MainActivity.this);
    private TabContainerView mTabLayout;
    private CommunicationService.MyBinder myBinder = null;
    private ImStoreFragment storeFragment = new ImStoreFragment();
    private ImCircleFragment circleFragment = new ImCircleFragment();
    private ImMeFragment meFragment = new ImMeFragment();
    private Fragment[] fragments = {storeFragment, circleFragment, meFragment};
    private final String USER_PORTRAIT_PATH = "sweet/person/portrait/" + MyApplication.USER_ID + ".png";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setIsBindEventBus();

    }

    @Override
    void onServiceBind(CommunicationService.MyBinder myBinder) {
        if (!MyApplication.sessionId.equals("")) {
            myBinder.getFlowFriends();
            myBinder.requestCircleInfo("朋友", 0);
            myBinder.getPersonInfo();
        }
        myBinder.requestImStoreInfo(MainActivity.this);
        this.myBinder = myBinder;

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (resultCode == RESULT_OK && requestCode == ImMeFragment.CROP_PHOTO) {

            myBinder.getPersonPortrait(USER_PORTRAIT_PATH);

        } else super.onActivityResult(requestCode, resultCode, data);

    }


    @Override
    protected boolean enableSliding() {
        return false;
    }

    private final int ICONS_RES[][] = {
            {
                    R.mipmap.tab_store,
                    R.mipmap.tab_store_pressed
            },
            {
                    R.mipmap.tab_circle,
                    R.mipmap.tab_circle_pressed
            },

            {
                    R.mipmap.tab_me,
                    R.mipmap.tab_me_pressed
            }
    };

    /**
     * tab 颜色值
     */
    private final int[] TAB_COLORS = new int[]{
            R.color.gray4,
            R.color.Myappcolor};


    private void initViews() {

        TabFragmentAdapter mAdapter = new TabFragmentAdapter(getSupportFragmentManager(), fragments);
        ViewPager mPager = findViewById(R.id.tab_pager);
        //设置当前可见Item左右可见page数，次范围内不会被销毁
        mPager.setOffscreenPageLimit(3);
        mPager.setAdapter(mAdapter);

        mTabLayout = findViewById(R.id.ll_tab_container);
        mTabLayout.setOnItemClick(new TabContainerView.OnItemClick() {
            @Override
            public boolean onItemClickListener(int position) {
                if (!MyApplication.config.getBoolean("logined", false)) {
                    MyToast.showShort("请先登陆");

                    startActivity(new Intent(MainActivity.this, UserLoginActivity.class));
                    return false;
                } else {
                    return true;
                }
            }
        });
        mTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int index = 0, len = fragments.length; index < len; index++) {
                    fragments[index].onHiddenChanged(index != position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mTabLayout.initContainer(getResources().getStringArray(R.array.tab_name), ICONS_RES, TAB_COLORS, true);

        int width = getResources().getDimensionPixelSize(R.dimen.tab_icon_width);
        int height = getResources().getDimensionPixelSize(R.dimen.tab_icon_height);
        mTabLayout.setContainerLayout(R.layout.layout_tab_container, R.id.iv_tab_icon, R.id.tv_tab_text, width, height);
        mTabLayout.setViewPager(mPager);
        //mPager.setCurrentItem(getIntent().getIntExtra("tab", 0));
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }

    public void sendCircleLikeIt(String mer_id, int position) {
        myBinder.circleLikeIt(mer_id, position);
    }

    public void flowPeople(String userId, boolean flag) {
        myBinder.flowPeople(userId, flag);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void hideTabView() {
        mTabLayout.setVisibility(View.GONE);
    }

    public void showTabView() {
        mTabLayout.setVisibility(View.VISIBLE);
    }

    public void ChangeCircleDate(String type, int start) {

        myBinder.requestCircleInfo(type, start);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(WeatherInfo info) {

        storeFragment.updataWeather(info);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(String info) {
        if (info.contains("http")) {
            meFragment.upPersonPortrait(info);
        } else
            storeFragment.updataLocation(info);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MainStoreInfo mainStoreInfo) {
        storeFragment.setShowInfo(mainStoreInfo);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(CircleMainInfo info) {
        circleFragment.initData(info);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(CircleLikePoint circleLikePoint) {
        circleFragment.likeCallback(circleLikePoint);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(PersonInfo info) {
        meFragment.upDataPersonInfo(info);
    }


}
