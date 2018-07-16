package com.lucky.sweet.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.lucky.sweet.R;
import com.lucky.sweet.entity.DeletRoomInfo;
import com.lucky.sweet.entity.InvitationInfo;
import com.lucky.sweet.service.CommunicationService;
import com.lucky.sweet.views.MyToast;
import com.lucky.sweet.views.SlidingLayoutView;

import org.greenrobot.eventbus.EventBus;

public abstract class BaseActivity extends AppCompatActivity {

    public static String sessionId = "";

    private MyConn conn = new MyConn();
    private Boolean isBindEventBus = false;
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (enableSliding()) {
            SlidingLayoutView rootView = new SlidingLayoutView(this);
            rootView.bindActivity(this);
        }

    }

    public void setIsBindEventBus() {
        isBindEventBus = true;
    }

    private void bindService() {
        bindService(new Intent(this, CommunicationService.class), conn, BIND_AUTO_CREATE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isBindEventBus) {
            EventBus.getDefault().register(this);
        }
        context = this;
        bindService();

    }

    @Override
    protected void onPause() {


        super.onPause();
        if (isBindEventBus) {
            EventBus.getDefault().unregister(this);
        }
        unbindService(conn);
    }


    abstract void onServiceBind(CommunicationService.MyBinder myBinder);


    private class MyConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder
                iBinder) {

            CommunicationService.MyBinder myBinder = (CommunicationService.MyBinder) iBinder;
            onServiceBind(myBinder);

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    }


    public AlertDialog showDialogBaseAct(String title, String message,
                                         String natureName,
                                         DialogInterface.OnClickListener
                                                 onClickNature,
                                         String positiveName, DialogInterface.OnClickListener onClickPositive,
                                         String negativeName,
                                         DialogInterface.OnClickListener
                                                 onClickNegative, Context
                                                 context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle(title)
                .setNegativeButton(natureName, onClickNature).setMessage
                        (message);
        if (null != positiveName)
            builder.setPositiveButton(positiveName, onClickPositive);
        if (null != negativeName)
            builder.setNegativeButton(negativeName, onClickNegative);
        AlertDialog dialog = builder.create();
        dialog.show();
        return dialog;

    }

    protected void hintInputKb() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context
                .INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }


    /**
     * 启动下一个Activity的动画
     */
    public void goNextAnim() {
        overridePendingTransition(R.anim.act_left_in, R.anim.act_left_out);
    }

    /**
     * 返回上一个Activity的动画
     */
    public void goPreAnim() {
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    /*淡入淡出动画*/
    public void goFadeAnim() {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    /*从下至上动画*/
    public void goUpAnim() {
        overridePendingTransition(R.anim.act_up_in, R.anim.act_down_out);
    }

    /*从上至下动画*/
    public void goDownAnim() {
        overridePendingTransition(R.anim.act_up, R.anim.act_down);
    }

    /*关闭侧滑手势*/
    protected boolean enableSliding() {
        return true;
    }

    public static void invitationFriend(InvitationInfo invitationInfo) {
        if (context != null) {
            final AlertDialog.Builder normalDialog = new AlertDialog.Builder(context);
            normalDialog.setTitle("您的好友邀请您一起点餐");
            normalDialog.setMessage("您的好友ID：" + invitationInfo.getUserId()+"在"+invitationInfo.getShopName()+"餐厅邀请您一起点餐");
            normalDialog.setPositiveButton("接受邀请", (dialog, which) -> {

                MerchantActivity.newMoreOrderInStance((Activity) context, invitationInfo.getMerId(), invitationInfo.getRoomId(), invitationInfo.getShopName());
            });
            normalDialog.setNegativeButton("果断拒绝", (dialog, which) -> {
                dialog.dismiss();
            });
            normalDialog.show();

        }
    }

    public static void onMerchantFinish(DeletRoomInfo DeletRoomInfo) {
        if (!DeletRoomInfo.getUserId().equals(MyApplication.USER_ID)) {
            String activityName = context.toString();
            String currentActivityName = activityName.substring(activityName.lastIndexOf(".") + 1, activityName.indexOf("@"));
            if (currentActivityName.equals(MerchantActivity.class.getSimpleName())) {

                final AlertDialog.Builder normalDialog = new AlertDialog.Builder(context);
                normalDialog.setTitle("警告！");
                normalDialog.setMessage("您的好友：" + DeletRoomInfo.getUserId() + "于" + DeletRoomInfo.getTime() + "已经关闭多人购物");
                normalDialog.setPositiveButton("无奈关闭", (dialog, which) -> {
                    ((Activity) context).finish();
                });
                normalDialog.show();
            }
        }
    }
}
