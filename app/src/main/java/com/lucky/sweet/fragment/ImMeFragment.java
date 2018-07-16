package com.lucky.sweet.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lucky.sweet.R;
import com.lucky.sweet.activity.CropIwaActivity;
import com.lucky.sweet.activity.HistoricalOderActivity;
import com.lucky.sweet.activity.MainActivity;
import com.lucky.sweet.activity.MyApplication;
import com.lucky.sweet.activity.MyCommentActivity;
import com.lucky.sweet.activity.MyLikeStoreActivity;
import com.lucky.sweet.activity.SetUserInfoActivity;
import com.lucky.sweet.activity.SettingActivity;
import com.lucky.sweet.activity.VipCardActiviry;
import com.lucky.sweet.entity.PersonInfo;
import com.lucky.sweet.views.CircleImageView;
import com.lucky.sweet.widgets.ToolBar;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.InputStream;

/**
 * Created by Qiuyue on 2017/11/15.
 */

// ╭︿︿︿╮  ╭︿︿︿╮  ╭︿︿︿╮
// {/ o o /} {/ . . /} {/ ︿︿ /}
// ( (oo) )  ( (oo) )  ( (oo) )
//   ︶︶︶     ︶︶︶     ︶︶︶

public class ImMeFragment extends Fragment implements View.OnClickListener {


    private CircleImageView imv_head;
    private MainActivity activity;
    public final static int CROP_PHOTO = 1000;
    private Bitmap bitmap;
    private TextView flowNum;
    private TextView funsNum;
    private TextView userName;

    private TextView vipCardNum;
    private TextView orderNum;
    private TextView talNum;
    private TextView colletCount;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        initView(getView());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_imme, container, false);

        activity = (MainActivity) getActivity();
        return view;
    }

    public void upPersonPortrait(String info) {
        Glide.with(this).load(info).into(imv_head);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            switch (requestCode) {
                case REQUEST_CODE_CHOOSE:
                    Uri uri = Matisse.obtainResult(data).get(0);
                    Intent intent = new Intent(getActivity(), CropIwaActivity.class);
                    intent.putExtra("img_uri", uri.toString());
                    activity.startActivityForResult(intent, CROP_PHOTO);
                    break;
                default:
                    break;
            }

        }

    }


    private void initView(View view) {


        orderNum = view.findViewById(R.id.tv_historical_order);
        flowNum = view.findViewById(R.id.tv_person_show_flow_num);
        funsNum = view.findViewById(R.id.tv_person_show_funs_num);

        vipCardNum = view.findViewById(R.id.tv_person_vip_card_count);
        colletCount = view.findViewById(R.id.tv_person_colloct_num);
        talNum = view.findViewById(R.id.tv_person_tal_count);
        userName = view.findViewById(R.id.tv_userName);
        imv_head = view.findViewById(R.id.imv_head);

        view.findViewById(R.id.rl_vipcard).setOnClickListener(this);
        view.findViewById(R.id.btn_setUserInfo).setOnClickListener(this);
        view.findViewById(R.id.rl_mycomment).setOnClickListener(this);
        view.findViewById(R.id.rl_hisorder).setOnClickListener(this);
        view.findViewById(R.id.myLikeStore).setOnClickListener(this);
        imv_head.setOnClickListener(this);


        if (bitmap != null) {
            imv_head.setImageBitmap(bitmap);
        }
        imv_head.setmDrawShapeType(CircleImageView.SHAPE_CIRCLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ToolBar toolBar = new ToolBar(getActivity());
            toolBar.setStatusBarDarkMode();
            int statusBarHeight = toolBar.getStatusBarHeight(getActivity());
            View view_margin = view.findViewById(R.id.view_margin);
            ViewGroup.LayoutParams lp;
            lp = view_margin.getLayoutParams();
            lp.height = statusBarHeight;
            view_margin.setLayoutParams(lp);
        } else {
        }

    }

    private final static int REQUEST_CODE_CHOOSE = 10000;


    public void upDataPersonInfo(PersonInfo info) {
        userName.setText(info.getNickname());
        funsNum.setText(info.getAttention_to_me_num());
        flowNum.setText(info.getMe_to_attention_num());
        vipCardNum.setText(info.getVip_card_num());
        talNum.setText(info.getComment_num());
        orderNum.setText(info.getIndent_num());
        colletCount.setText(info.getCollect());
        if (!info.getPhoto().equals("")) {
            Glide.with(this).load(info.getPhoto()).diskCacheStrategy(DiskCacheStrategy.ALL).into(imv_head);

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_vipcard:
                startActivity(new Intent(getActivity(), VipCardActiviry.class));
                getActivity().overridePendingTransition(R.anim.act_left_in, R.anim.act_left_out);
                break;
            case R.id.myLikeStore:
                startActivity(new Intent(getActivity(), MyLikeStoreActivity.class));
                getActivity().overridePendingTransition(R.anim.act_left_in, R.anim.act_left_out);
                break;
            case R.id.rl_hisorder:
                startActivity(new Intent(getActivity(), HistoricalOderActivity.class));
                getActivity().overridePendingTransition(R.anim.act_left_in, R.anim.act_left_out);
                break;
            case R.id.rl_mycomment:
                startActivity(new Intent(getActivity(), MyCommentActivity.class));
                getActivity().overridePendingTransition(R.anim.act_left_in, R.anim.act_left_out);
                break;
            case R.id.btn_setUserInfo:
                SettingActivity.newInstance(userName.getText().toString(), getActivity());
                getActivity().overridePendingTransition(R.anim.act_left_in, R.anim.act_left_out);
                break;
            case R.id.imv_head:
                if (!MyApplication.sessionId.equals("")) {
                    Matisse.from(ImMeFragment.this)
                            .choose(MimeType.allOf())
                            .countable(true)
                            .maxSelectable(1)
                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                            .thumbnailScale(0.85f)
                            .imageEngine(new GlideEngine())
                            .forResult(REQUEST_CODE_CHOOSE);
                } else {
                    Toast.makeText(activity, "请先登陆", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
