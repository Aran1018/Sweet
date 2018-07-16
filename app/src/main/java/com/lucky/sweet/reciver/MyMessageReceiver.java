package com.lucky.sweet.reciver;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.notification.CPushMessage;
import com.google.gson.Gson;
import com.lucky.sweet.activity.BaseActivity;
import com.lucky.sweet.entity.DeletRoomInfo;
import com.lucky.sweet.entity.InvitationInfo;
import com.lucky.sweet.entity.JoinRoomInfo;
import com.lucky.sweet.entity.MuliiOrderInfo;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

/**
 * Created by chn on 2018/1/21.
 * (╯°□°）╯︵ ┻━┻ MMP好气啊！
 * (╯‵□′)╯︵┻━┻ 老子怒掀桌子！
 * ┻━┻︵╰(‵□′)╯︵┻━┻老子双手掀桌！
 * ┬─┬﻿ ノ( ゜-゜ノ) 算了，我不生气了！日子还得过老老实实敲吧~
 */

public class MyMessageReceiver extends MessageReceiver {
    // 消息接收部分的LOG_TAG
    public static final String REC_TAG = "receiver";

    private static final String JOIN_ROOM = "join_room";
    private static final String UPDATE_MENU = "update_menu";
    private static final String INVITE_PEOPLE = "invite";
    private static final String DELETE_ROOM = "delete_room";

    @Override
    public void onNotification(Context context, String title, String summary, Map<String, String> extraMap) {
        // TODO 处理推送通知

        Log.e("MyMessageReceiver", "Receive notification, title: " + title + ", summary: " + summary + ", extraMap: " + extraMap);
    }

    @Override
    public void onMessage(Context context, CPushMessage cPushMessage) {
        String content = cPushMessage.getContent();
        Gson gson = new Gson();
        Log.e("MyMessageReceiver", "onMessage, messageId: " + cPushMessage.getMessageId() + ", title: " + cPushMessage.getTitle() + ", content:" + cPushMessage.getContent());

        switch (cPushMessage.getTitle()) {

            case JOIN_ROOM:

                JoinRoomInfo joinRoomInfo = gson.fromJson(content, JoinRoomInfo.class);
                // EventBus.getDefault().post(joinRoomInfo);
                Toast.makeText(context, "感谢这位老铁：" + joinRoomInfo.getName() + "加入房间", Toast.LENGTH_SHORT).show();

                break;
            case UPDATE_MENU:
                MuliiOrderInfo MuliiOrderInfo = gson.fromJson(content, MuliiOrderInfo.class);
                EventBus.getDefault().post(MuliiOrderInfo);
                break;
            case INVITE_PEOPLE:
                System.out.println(content);
                InvitationInfo invitationInfo = gson.fromJson(content, InvitationInfo.class);
                BaseActivity.invitationFriend(invitationInfo);

                Toast.makeText(context, content, Toast.LENGTH_SHORT).show();

                break;
            case DELETE_ROOM:
                DeletRoomInfo DeletRoomInfo = gson.fromJson(content, DeletRoomInfo.class);
                BaseActivity.onMerchantFinish(DeletRoomInfo);
                Toast.makeText(context, content, Toast.LENGTH_SHORT).show();

                break;
        }
    }

    @Override
    public void onNotificationOpened(Context context, String title, String summary, String extraMap) {
        Toast.makeText(context, "onNotificationOpened, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap, Toast.LENGTH_SHORT).show();

        Log.e("MyMessageReceiver", "onNotificationOpened, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap);
    }

    @Override
    protected void onNotificationClickedWithNoAction(Context context, String title, String summary, String extraMap) {
        Log.e("MyMessageReceiver", "onNotificationClickedWithNoAction, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap);
    }

    @Override
    protected void onNotificationReceivedInApp(Context context, String title, String summary, Map<String, String> extraMap, int openType, String openActivity, String openUrl) {
        Log.e("MyMessageReceiver", "onNotificationReceivedInApp, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap + ", openType:" + openType + ", openActivity:" + openActivity + ", openUrl:" + openUrl);
    }

    @Override
    protected void onNotificationRemoved(Context context, String messageId) {
        Log.e("MyMessageReceiver", "onNotificationRemoved");
    }
}