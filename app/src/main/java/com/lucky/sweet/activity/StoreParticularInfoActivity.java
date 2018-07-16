package com.lucky.sweet.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lucky.sweet.R;
import com.lucky.sweet.adapter.CircleListViewAdapter;
import com.lucky.sweet.entity.CircleMainInfo;
import com.lucky.sweet.entity.CollectStoreEntitiy;
import com.lucky.sweet.entity.LikeShopEntity;
import com.lucky.sweet.entity.StatueCheckBaseEntitiy;
import com.lucky.sweet.entity.StoreDetailedInfo;
import com.lucky.sweet.service.CommunicationService;
import com.lucky.sweet.thread.BlurBitmapThread;
import com.lucky.sweet.views.MyToast;
import com.lucky.sweet.widgets.Title;
import com.lucky.sweet.widgets.ToolBar;
import com.tencent.lbssearch.TencentSearch;
import com.tencent.lbssearch.httpresponse.BaseObject;
import com.tencent.lbssearch.httpresponse.HttpResponseListener;
import com.tencent.lbssearch.object.Location;
import com.tencent.lbssearch.object.param.WalkingParam;
import com.tencent.lbssearch.object.result.WalkingResultObject;
import com.tencent.mapsdk.raster.model.BitmapDescriptorFactory;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.Marker;
import com.tencent.mapsdk.raster.model.MarkerOptions;
import com.tencent.mapsdk.raster.model.PolylineOptions;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.TencentMap;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c on 2017/12/13.
 * (╯°□°）╯︵ ┻━┻ MMP好气啊！
 * (╯‵□′)╯︵┻━┻ 老子怒掀桌子！
 * ┻━┻︵╰(‵□′)╯︵┻━┻老子双手掀桌！
 * ┬─┬﻿ ノ( ゜-゜ノ) 算了，我不生气了！日子还得过老老实实敲吧~
 */

public class StoreParticularInfoActivity extends BaseActivity {

    private Title title = null;

    private ImageView imv_back;
    private ImageView imv_show_fir;
    private ImageView imv_show_sec;
    private ImageView imv_show_sed;

    private ImageView imv_shop_one;
    private ImageView imv_shop_two;
    private ImageView imv_shop_three;

    private TextView tv_shop_title;
    private TextView tv_shop_int;
    private TextView tv_shop_worktime;
    private TextView tv_shop_des;
    private MapView map;
    private Button btn_map_position;
    private Button btn_more_map_detal;
    private TencentMap maps;
    private LatLng latLng;
    private TextView tv_moreFood;
    private TextView tv_moreevr;

    private String mer_id;
    private ImageButton ibtn_back;
    private LinearLayout ll_null;
    private LinearLayout ll_share;
    private TextView tv_weichat;
    private TextView tv_circle;
    private TextView tv_weibo;
    private ImageButton ibtn_order_seat;
    private ImageButton ibtn_orderSingle;
    private ImageButton ibtn_orderMulti;
    private LinearLayout layout_order_content;
    private LinearLayout ll_orderbtn;
    public int num = 0;
    private ListView lv_circle;

    private CommunicationService.MyBinder myBinder;
    private ImageButton imb_store_collect;
    private NestedScrollView sc_shop_show;
    private TextView tv_store_ave;
    private Button btn_order;
    public static void newInStance(Context context, String shopId) {

        Intent intent = new Intent(context, StoreParticularInfoActivity.class);
        intent.putExtra("shopid", shopId);
        context.startActivity(intent);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storeinfo);
        setIsBindEventBus();
        initView();

        initEvent();

        initTitle();

    }


    @Override
    void onServiceBind(CommunicationService.MyBinder myBinder) {
        Intent intent = getIntent();
        mer_id = intent.getStringExtra("shopid");
        myBinder.requestShopDisplay(mer_id);
        myBinder.getParticularCircleInfo(mer_id, num);
        this.myBinder = myBinder;
    }


    private void initEvent() {
        btn_more_map_detal.setOnClickListener(v -> {

            MapWebAcivity.Companion.InStance(StoreParticularInfoActivity.this, String.valueOf(MyApplication.lat), String.valueOf(MyApplication.longi), latLng.getLongitude() + "", "" + latLng.getLatitude());
        });
        btn_map_position.setOnClickListener(v -> maps.setCenter(latLng));
        tv_moreFood.setOnClickListener(v -> {
            RecruitmentDisplayActivity.newStance(this, RecruitmentDisplayActivity.DISC_SHOW, mer_id);
            goUpAnim();
        });
        tv_moreevr.setOnClickListener(v -> {
            RecruitmentDisplayActivity.newStance(this, RecruitmentDisplayActivity.AROUND_SHOW, mer_id);
            goUpAnim();
        });
    }

    private Boolean collect = true;

    public void onButtonClick(View view) {
        if (MyApplication.sessionId.equals("")) {
//                Toast.makeText(StoreParticularInfoActivity.this, "请先登陆",
//                        Toast.LENGTH_SHORT).show();
            MyToast.showShort("请先登陆");

            return;
        }
        myBinder.collectShop(mer_id, !collect);

    }

    private void initView() {
        imv_back = findViewById(R.id.imv_back);
        imv_show_fir = findViewById(R.id.imv_show_fir);
        tv_moreFood = findViewById(R.id.tv_moreFood);
        tv_moreevr = findViewById(R.id.tv_moreevr);
        btn_more_map_detal = findViewById(R.id.btn_more_map_detal);
        imv_show_sec = findViewById(R.id.imv_show_sec);
        imv_show_sed = findViewById(R.id.imv_show_sed);
        tv_shop_title = findViewById(R.id.tv_shop_title);
        tv_shop_int = findViewById(R.id.tv_shop_int);
        tv_shop_worktime = findViewById(R.id.tv_shop_worktime);
        tv_shop_des = findViewById(R.id.tv_shop_des);
        btn_map_position = findViewById(R.id.btn_map_position);
        tv_store_ave = findViewById(R.id.tv_store_ave);
        imv_shop_one = findViewById(R.id.imv_shop_one);
        imv_shop_two = findViewById(R.id.imv_shop_two);
        imv_shop_three = findViewById(R.id.imv_shop_three);
        ibtn_back = findViewById(R.id.ibtn_back);
        ll_null = findViewById(R.id.ll_null);
        ll_share = findViewById(R.id.ll_share);
        ibtn_order_seat = findViewById(R.id.ibtn_order_seat);
        ibtn_orderSingle = findViewById(R.id.ibtn_orderSingle);
        ibtn_orderMulti = findViewById(R.id.ibtn_orderMulti);
        tv_weichat = findViewById(R.id.tv_weichat);
        tv_circle = findViewById(R.id.tv_circle);
        tv_weibo = findViewById(R.id.tv_weibo);
        layout_order_content = findViewById(R.id.layout_order_content);
        ll_orderbtn = findViewById(R.id.ll_orderbtn);
        imb_store_collect = findViewById(R.id.imb_store_collect);
        sc_shop_show = findViewById(R.id.sc_shop_show);
        btn_order = findViewById(R.id.btn_order);

//        StarLevelIndicatorView startIndicator = findViewById(R.id.startIndicator);
//        startIndicator.initStarNumber(4);
        map = findViewById(R.id.map);
        btn_order.setOnClickListener(v -> {
            layout_order_content.setVisibility(View.VISIBLE);
            btn_order.setVisibility(View.GONE);
            startbtnAnim();

        });

        lv_circle = findViewById(R.id.lv_circle);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);


        ibtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hintInputKb();
                finish();
                goPreAnim();
            }
        });
        ll_null.setOnClickListener(view -> cancelbtnAnim());
        ibtn_order_seat.setOnClickListener(view -> {
            if (MyApplication.sessionId.equals("")) {

                MyToast.showShort("请先登陆");

                return;
            }
            OrderSeatActivity.newInstance(mer_id,tv_shop_title.getText().toString().trim(),this);

            goNextAnim();

        });
        ibtn_orderSingle.setOnClickListener(view -> {
            if (MyApplication.sessionId.equals("")) {

                MyToast.showShort("请先登陆");

                return;
            }
            MerchantActivity.newSingleOrderInStance(StoreParticularInfoActivity.this, mer_id,tv_shop_title.getText().toString().trim());
            goNextAnim();
        });

        ibtn_orderMulti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyApplication.sessionId.equals("")) {

                    MyToast.showShort("请先登陆");

                    return;
                }
                CreateRoomActivity.newInStrance(StoreParticularInfoActivity.this, mer_id,tv_shop_title.getText().toString().trim());
                goNextAnim();

            }
        });

//        ibtn_orderMulti.setOnClickListener(view -> {
//            if (MyApplication.sessionId.equals("")) {
////                Toast.makeText(StoreParticularInfoActivity.this, "请先登陆",
////                        Toast.LENGTH_SHORT).show();
//                MyToast.showShort("请先登陆");
//
//                return;
//            }
//
//            new AlertDialog.Builder(StoreParticularInfoActivity.this)
//                    .setMessage("请选择加入方式")
//                    .setPositiveButton("加入房间", (dialog, which) -> {
//                        Intent intent = new Intent(StoreParticularInfoActivity.this, JoinRoomActivity.class);
//                        intent.putExtra("mer_id", mer_id);
//                        startActivityForResult(intent, 0);
//                        goNextAnim();
//                    })
//                    .setNegativeButton("创建房间", (dialog, which) -> {
//                        CreateRoomActivity.newInStrance(StoreParticularInfoActivity.this, mer_id);
//                        goNextAnim();
//                    })
//                    .create()
//                    .show();
//        });

    }

    private void initTitle() {
        ToolBar toolBar = new ToolBar(this);
        toolBar.setStatusBarDarkMode();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(CircleMainInfo info) {
         lv_circle.setAdapter(new CircleListViewAdapter(this, info.getCircle_list()));
        //setListViewHeightBasedOnChildren(lv_circle);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(CollectStoreEntitiy collectStoreEntitiy) {
        String log;
        if (collectStoreEntitiy.getAttr()) {
            if (collectStoreEntitiy.isCollect()) {
                Glide.with(this).load(R.mipmap.shopstarfull).into(imb_store_collect);
                log = "收藏成功";
                collect = Boolean.TRUE;
            } else {
                Glide.with(this).load(R.mipmap.shopstar).into(imb_store_collect);
                log = "取关成功";
                collect = Boolean.FALSE;
            }
        } else {
            log = "操作失败";
        }
        MyToast.showShort(log);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(LikeShopEntity base) {
        if (base.getAttr()) {
            collect = Boolean.FALSE;
            Glide.with(this).load(R.mipmap.shopstar).into(imb_store_collect);
        } else {
            collect = Boolean.TRUE;
            Glide.with(this).load(R.mipmap.shopstarfull).into(imb_store_collect);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(StoreDetailedInfo entity) {
        StoreDetailedInfo.InfoBean info = entity.getInfo();

        Glide.with(this).load(info.getSurface()).into(imv_back);

        List<String> environment = info.getEnvironment();

        Glide.with(this).load(environment.get(0)).into
                (imv_show_fir);
        Glide.with(this).load(environment.get(1)).into
                (imv_show_sec);
        Glide.with(this).load(environment.get(2)).into
                (imv_show_sed);
        List<String> recommend = info.getRecommend();
        Glide.with(this).load(recommend.get(0)).into
                (imv_shop_one);
        Glide.with(this).load(recommend.get(1)).into
                (imv_shop_two);
        Glide.with(this).load(recommend.get(2)).into
                (imv_shop_three);


        StoreDetailedInfo.InfoBean.ShopdesBean shopdes = info.getShopdes();

        tv_shop_int.setText(shopdes.getClassify());
        tv_shop_title.setText(shopdes.getName());
        tv_shop_des.setText(shopdes.getIntroduce());
        tv_shop_worktime.setText(shopdes.getBusiness_hours());
        tv_store_ave.setText("￥" + shopdes.getAvecon());
        latLng = new LatLng(Double.valueOf(shopdes.getLatitude()),
                Double.valueOf(shopdes.getLongitude()));
        maps = map.getMap();

        // maps.setCenter(latLng);

        Marker marker = this.map.addMarker(new MarkerOptions().title(shopdes.getName()).anchor(0.5f, 0.5f).position(latLng));
        marker.showInfoWindow();
        double longitude = MyApplication.longi;
        double latitude = MyApplication.lat;
        if (longitude != 0 && latitude != 0) {
            LatLng current = new LatLng(latitude, longitude);
            marker = this.map.addMarker(new MarkerOptions().title("您当前位置").anchor(0.5f, 0.5f).position(current));
            marker.showInfoWindow();
            btn_more_map_detal.setVisibility(View.VISIBLE);
            maps.zoomToSpan(new LatLng(latLng.getLatitude() - 0.0013, latLng.getLongitude() - 0.0013), new LatLng(latitude + 0.0013, longitude + 0.0013));
            TencentSearch tencentSearch = new TencentSearch(this);
            WalkingParam walkingParam = new WalkingParam();
            walkingParam.from(new Location(Float.valueOf(shopdes.getLatitude()), Float.valueOf(shopdes.getLongitude())));
            walkingParam.to(new Location((float) MyApplication.lat, (float) MyApplication.longi));
            ArrayList<LatLng> latLngs = new ArrayList<>();
            tencentSearch.getDirection(walkingParam, new HttpResponseListener() {
                @Override
                public void onSuccess(int i, BaseObject baseObject) {
                    if (baseObject instanceof WalkingResultObject) {
                        WalkingResultObject result = (WalkingResultObject) baseObject;
                        List<WalkingResultObject.Route> routes = result.result.routes;
                        if (routes.size() > 0) {
                            WalkingResultObject.Route route = routes.get(0);
                            for (Location location : route.polyline) {
                                latLngs.add(new LatLng(location.lat, location.lng));
                            }
                            map.addPolyline(new PolylineOptions().
                                    addAll(latLngs).
                                    color(0xff0066cc).
                                    width(10f).
                                    //为 polyline 添加纹理, 通常用于标记路线
                                            arrowTexture(BitmapDescriptorFactory.fromAsset("texture_arrow.png")).
                                            arrowGap(30).
                                            edgeColor(0xff0072E3).
                                            edgeWidth(5));

                        }


                    }
                }

                @Override
                public void onFailure(int i, String s, Throwable throwable) {

                }
            });

        }

        tv_shop_title.setFocusable(true);
        tv_shop_title.setFocusableInTouchMode(true);
        tv_shop_title.requestFocus();
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        //获得adapter
        CircleListViewAdapter adapter = (CircleListViewAdapter) listView.getAdapter();
        if (adapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(0, 0);
            //计算总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        //计算分割线高度
        params.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount()));
        //给listview设置高度
        listView.setLayoutParams(params);
    }


    private void startbtnAnim() {
        //阴影遮罩透明度
        ll_null.setBackgroundColor((Color.parseColor("#6e000000")));
        int btnNum = 4;
        final Animation[] T_Anim = new Animation[btnNum];
        for (int i = 0; i < btnNum; i++) {
            T_Anim[i] = new TranslateAnimation(0f, 0, 350f, 0f);
            T_Anim[i].setDuration(85);
            T_Anim[i].setInterpolator(new BounceInterpolator());
            T_Anim[i].setFillAfter(true);
        }
        ll_share.startAnimation(T_Anim[0]);
        T_Anim[0].setAnimationListener(new AnimationListerAdapter() {

            @Override
            public void onAnimationEnd(Animation animation) {

                ibtn_order_seat.setVisibility(View.VISIBLE);
                tv_circle.setVisibility(View.VISIBLE);
                ibtn_order_seat.startAnimation(T_Anim[1]);
                tv_circle.startAnimation(T_Anim[1]);
            }
        });

        T_Anim[1].setAnimationListener(new AnimationListerAdapter() {

            @Override
            public void onAnimationEnd(Animation animation) {

                ibtn_orderSingle.setVisibility(View.VISIBLE);
                tv_weichat.setVisibility(View.VISIBLE);
                ibtn_orderSingle.startAnimation(T_Anim[2]);
                tv_weichat.startAnimation(T_Anim[2]);
            }
        });
        T_Anim[2].setAnimationListener(new AnimationListerAdapter() {

            @Override
            public void onAnimationEnd(Animation animation) {

                ibtn_orderMulti.setVisibility(View.VISIBLE);
                tv_weibo.setVisibility(View.VISIBLE);
                ibtn_orderMulti.startAnimation(T_Anim[3]);
                tv_weibo.startAnimation(T_Anim[3]);
            }
        });

    }

    private void cancelbtnAnim() {
        ll_null.setBackgroundColor((Color.parseColor("#00000000")));
        int btnNum = 4;
        final Animation[] T_Anim = new TranslateAnimation[btnNum];
        for (int i = 0; i < btnNum; i++) {
            T_Anim[i] = new TranslateAnimation(0f, 0, 0f, 350f);
            T_Anim[i].setDuration(80);
        }

        ibtn_order_seat.startAnimation(T_Anim[0]);
        T_Anim[0].setAnimationListener(new AnimationListerAdapter() {

            @Override
            public void onAnimationEnd(Animation animation) {
                ibtn_order_seat.setVisibility(View.GONE);
                tv_circle.setVisibility(View.GONE);
                ibtn_order_seat.clearAnimation();
                tv_circle.clearAnimation();
                ibtn_orderSingle.startAnimation(T_Anim[1]);
                tv_circle.startAnimation(T_Anim[1]);
            }
        });

        T_Anim[1].setAnimationListener(new AnimationListerAdapter() {

            @Override
            public void onAnimationEnd(Animation animation) {
                ibtn_orderSingle.setVisibility(View.GONE);
                tv_weichat.setVisibility(View.GONE);
                ibtn_orderSingle.clearAnimation();
                tv_weichat.clearAnimation();
                ibtn_orderMulti.startAnimation(T_Anim[2]);
                tv_weibo.startAnimation(T_Anim[2]);

            }
        });
        T_Anim[2].setAnimationListener(new AnimationListerAdapter() {

            @Override
            public void onAnimationEnd(Animation animation) {
                ibtn_orderMulti.setVisibility(View.GONE);
                tv_weibo.setVisibility(View.GONE);
                ibtn_orderMulti.clearAnimation();
                tv_weibo.clearAnimation();
                ll_share.startAnimation(T_Anim[3]);
                btn_order.setVisibility(View.VISIBLE);
                layout_order_content.setVisibility(View.GONE);

            }
        });

    }

    private abstract class AnimationListerAdapter implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

        @Override
        public abstract void onAnimationEnd(Animation animation);

    }


}
