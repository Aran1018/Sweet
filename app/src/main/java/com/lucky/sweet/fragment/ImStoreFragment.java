package com.lucky.sweet.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lucky.sweet.R;
import com.lucky.sweet.activity.CaptureActivity;
import com.lucky.sweet.activity.FloatPageActivity;
import com.lucky.sweet.activity.MainActivity;
import com.lucky.sweet.activity.MainSearchActiviy;
import com.lucky.sweet.activity.StoreDisplayActivity;
import com.lucky.sweet.activity.StoreParticularInfoActivity;
import com.lucky.sweet.adapter.AdViewPagerAdapter;
import com.lucky.sweet.adapter.RecFoodRecommendAdapter;
import com.lucky.sweet.entity.MainStoreInfo;
import com.lucky.sweet.entity.WeatherInfo;
import com.lucky.sweet.utility.BlurBitmapUtil;
import com.lucky.sweet.utility.HiddenAnimUtils;
import com.lucky.sweet.views.GradualChangeLinearLayout;
import com.lucky.sweet.views.IndicatorView;
import com.lucky.sweet.views.MySearchView;
import com.lucky.sweet.viewsexpand.AdViewPagerTransformer;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Qiuyue on 2017/11/15.
 */

// ╭︿︿︿╮  ╭︿︿︿╮  ╭︿︿︿╮
// {/ o o /} {/ . . /} {/ ︿︿ /}
// ( (oo) )  ( (oo) )  ( (oo) )
//   ︶︶︶     ︶︶︶     ︶︶︶

public class ImStoreFragment extends Fragment implements View.OnClickListener {

    private ViewPager vp_ad;
    private MySearchView msv_search;

    private TextView tv_location;
    private TextView tv_weathertype;
    private GradualChangeLinearLayout ll_se;

    private TextView tv_moreFood;
    private ImageView imv_weather;
    private TextView tv_moreRelax;
    /*    private RecyclerView rec_ad_show;*/
    private RecyclerView rec_foodStore;
    private RecyclerView rec_funStore;


    private Context context;
    private HiddenAnimUtils hiddenAnimUtils;
    // private List<MainStoreInfo.foodBean> showInfo;
    private Button btn_qrcodescan;


    private CollapsingToolbarLayout collapsingToolbarLayout;
    private IndicatorView in_vp_ad;
    /*    private RecycleViewRefresh ref_recy_food;
        private RecycleViewRefresh ref_recy_fun;*/
    private RelativeLayout rl_store_method;
    private FloatingActionButton float_btn;
    private CoordinatorLayout cdl_cover;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_imstore, container, false);
        this.context = getContext();
        initView(view);

        initData();

        initEvent();

        return view;
    }

    public void updataLocation(String location) {
        tv_location.setText(location);
    }

    public void updataWeather(WeatherInfo info) {
        if (tv_weathertype != null) {
            try {
                String cond_txt = info.getHeWeather6().get(0).getNow
                        ().getCond_txt();
                String tmp = info.getHeWeather6().get(0).getNow
                        ().getTmp();
                tv_weathertype.setText(cond_txt + " " + tmp);
                InputStream open = context.getAssets().open("weather/w" + info.getHeWeather6().get(0).getNow().getCond_code() + ".png");
                imv_weather.setImageBitmap(BitmapFactory.decodeStream(open));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void initData() {


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        hiddenAnimUtils = HiddenAnimUtils.newInstance(getContext(), ll_se, msv_search, displayMetrics.heightPixels);

        vp_ad.setPageTransformer(true, new AdViewPagerTransformer());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rec_foodStore.setLayoutManager(linearLayoutManager);


        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rec_funStore.setLayoutManager(linearLayoutManager);


    }


    private void initView(View view) {

        vp_ad = view.findViewById(R.id.vp_ad);
        imv_weather = view.findViewById(R.id.imv_weather);
        tv_weathertype = view.findViewById(R.id.tv_weathertype);
        msv_search = view.findViewById(R.id.msv_search);
        tv_location = view.findViewById(R.id.tv_location);
        tv_moreFood = view.findViewById(R.id.tv_moreFood);
        tv_moreRelax = view.findViewById(R.id.tv_moreRelax);
        rec_foodStore = view.findViewById(R.id.rec_foodStore);
        rec_funStore = view.findViewById(R.id.rec_funStore);
        //rec_ad_show = view.findViewById(R.id.rec_ad_show);
        btn_qrcodescan = view.findViewById(R.id.btn_qrcodescan);
/*        ref_recy_food = view.findViewById(R.id.ref_recy_food);*/
   /*       View inc_recycle_more_food = view.findViewById(R.id.pb_food_loading);
      ref_recy_food.setMoveViews(inc_recycle_more_food);*/
/*        ref_recy_fun = view.findViewById(R.id.ref_recy_fun);*/
   /*       View pb_fun_loading = view.findViewById(R.id.pb_fun_loading);
      ref_recy_fun.setMoveViews(pb_fun_loading);*/
        collapsingToolbarLayout = view.findViewById(R.id.collapsingToolbarLayout);
        in_vp_ad = view.findViewById(R.id.in_vp_ad);
        float_btn = view.findViewById(R.id.float_btn);
        cdl_cover = view.findViewById(R.id.cdl_cover);
        rl_store_method = view.findViewById(R.id.rl_store_method);
        collapsingToolbarLayout.setContentScrim(getResources().getDrawable(R.drawable.search_bg));

        float_btn = view.findViewById(R.id.float_btn);
        float_btn.setImageResource(R.mipmap.floatbtn_bg);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            ToolBar toolBar = new ToolBar(getActivity());
//            toolBar.setStatusBarDarkMode();
//            int statusBarHeight = toolBar.getStatusBarHeight(getActivity());
//            View view_margin = view.findViewById(R.id.view_margin);
//            ViewGroup.LayoutParams lp;
//            lp = view_margin.getLayoutParams();
//            lp.height = statusBarHeight;
//            view_margin.setLayoutParams(lp);
//        }
    }

    private void initEvent() {
        float_btn.setOnClickListener(this);
        tv_moreFood.setOnClickListener(this);
        tv_moreRelax.setOnClickListener(this);
        btn_qrcodescan.setOnClickListener(this);
        btn_qrcodescan.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CaptureActivity.class);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.act_left_in, R.anim.act_left_out);
        });
        msv_search.setOnSearchClickListener(v -> {
            Intent intent = new Intent(getActivity(), MainSearchActiviy.class);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.act_up, R.anim.act_down);
        });
        float_btn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), FloatPageActivity.class);
            startActivity(intent);
        });
        vp_ad.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
                in_vp_ad.playByStartPointToNext(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        /*ref_recy_food.setOnPullToLeftListener(new RecycleViewRefresh.OnPullToLeftListener() {
            @Override
            public void onReleaseFingerToUpload() {

                Intent intent = new Intent(context, StoreDisplayActivity.class);
                intent.putExtra("tv_moreFood", "Food");
                intent.putExtra("city", tv_location.getText().toString().trim());

                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(R.anim.act_left_in, R.anim.act_left_out);
                ref_recy_food.completeToUpload();
            }

            @Override
            public void onStartToUpload() {

            }
        });
        ref_recy_fun.setOnPullToLeftListener(new RecycleViewRefresh.OnPullToLeftListener() {
            @Override
            public void onReleaseFingerToUpload() {

                Intent intent = new Intent(context, StoreDisplayActivity.class);
                intent.putExtra("tv_moreRelax", "Relax");
                intent.putExtra("city", tv_location.getText().toString().trim());

                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(R.anim.act_left_in, R.anim.act_left_out);

            }

            @Override
            public void onStartToUpload() {

            }
        });*/
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    public void setShowInfo(MainStoreInfo showInfo) {
        if (vp_ad != null) {
            vp_ad.setPageMargin(15);//设置page间间距，自行根据需求设置
            vp_ad.setOffscreenPageLimit(3);//>=3
            vp_ad.setAdapter(new AdViewPagerAdapter(getContext(), showInfo
                    .getAdvertising()));
            in_vp_ad.initIndicator(showInfo.getAdvertising().size());
            RecFoodRecommendAdapter foodAdapter = new RecFoodRecommendAdapter
                    (context, RecFoodRecommendAdapter.FOOD, showInfo.getFood());
            RecFoodRecommendAdapter funAdapter = new RecFoodRecommendAdapter
                    (context, RecFoodRecommendAdapter.FUN, showInfo.getRecreation());

            foodAdapter.setOnItemClickListener(shopId -> StoreParticularInfoActivity.newInStance(context, shopId));
            funAdapter.setOnItemClickListener(shopId -> StoreParticularInfoActivity.newInStance(context, shopId));
            rec_foodStore.setAdapter(foodAdapter);
            rec_funStore.setAdapter(funAdapter);

        }

    }

    @Override
    public void onClick(View v) {
        //获得父控件的对象，然后获得父控件的id
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_moreFood:
                intent = new Intent(getActivity(), StoreDisplayActivity.class);
                intent.putExtra("tv_moreFood", "Food");
                intent.putExtra("city", tv_location.getText().toString().trim());

                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.act_left_in, R.anim.act_left_out);
                break;
            case R.id.tv_moreRelax:
                intent = new Intent(getActivity(), StoreDisplayActivity.class);
                intent.putExtra("tv_moreRelax", "Relax");
                intent.putExtra("city", tv_location.getText().toString().trim());

                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.act_left_in, R.anim.act_left_out);
                break;
            case R.id.float_btn:

                Bitmap bitmap = BlurBitmapUtil.blurBitmap(getContext(), BlurBitmapUtil.takeScreenshot((MainActivity) getActivity(), cdl_cover), 25);

                rl_store_method.setBackground(new BitmapDrawable(getResources(), bitmap));
                if (rl_store_method.getVisibility() == View.VISIBLE) {
                    rl_store_method.setVisibility(View.GONE);
                } else
                    rl_store_method.setVisibility(View.VISIBLE);
                break;
        }

    }

}

