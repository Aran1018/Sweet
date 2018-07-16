package com.lucky.sweet.activity;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.google.gson.Gson;
import com.lucky.sweet.reciver.NetBroadcastReceiver;
import com.lucky.sweet.entity.OssBean;
import com.lucky.sweet.properties.Properties;
import com.lucky.sweet.utility.HttpUtils;
import com.lucky.sweet.views.MyToast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.lucky.sweet.properties.ServiceProperties.END_POINT;
import static com.lucky.sweet.properties.ServiceProperties.REQUEST_PATH;

/**
 * Created by chn on 2018/1/10.
 * (╯°□°）╯︵ ┻━┻ MMP好气啊！
 * (╯‵□′)╯︵┻━┻ 老子怒掀桌子！
 * ┻━┻︵╰(‵□′)╯︵┻━┻老子双手掀桌！
 * ┬─┬﻿ ノ( ゜-゜ノ) 算了，我不生气了！日子还得过老老实实敲吧~
 */

public class MyApplication extends Application {
    //todo 选取session并且判读当为“”时，则视为暂无登陆
    private static Context context;
    private static OSSClient ossClient;

    public static String sessionId = "";

    public static String CURRENT_CITY = "大连市";
    public static String USER_ID = "";
    public static SharedPreferences config;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        config = context.getSharedPreferences("config", MODE_PRIVATE);
        initOssClient();

        initBroadCastReceiver();

        initCloudChannel(this);
    }

    public static double longi = 0;
    public static double lat = 0;

    public static void setCurrentLatAndLon(double latitude, double longitude) {
        longi = longitude;
        lat = latitude;
    }

    private void initBroadCastReceiver() {
        NetBroadcastReceiver netBroadcastReceiver = new NetBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netBroadcastReceiver, intentFilter);
        netBroadcastReceiver.setNetEvent(netMobile -> {
            switch (netMobile) {
                case NetBroadcastReceiver.NETWORK_MOBILE:

                    break;
                case NetBroadcastReceiver.NETWORK_NONE:
                    MyToast.showShort("暂无网络");

                    break;
                case NetBroadcastReceiver.NETWORK_WIFI:

                    break;

                default:
                    break;
            }

        });
    }

    /**
     * 获取全局上下文
     */
    public static Context getContext() {
        return context;
    }

    public static OSSClient getOSSClient() {
        return ossClient;
    }

    public static void setSessionID(String id) {
        sessionId = id;
    }

    public static void setCurrenCity(String city) {
        CURRENT_CITY = city;
    }

    public static void initSession(final OnOssClient onOssClient) {
        if (config.getBoolean("logined", false)) {
            USER_ID = config.getString("Id", "");
            final String psw = config.getString("Psw", "");
            new Thread() {
                @Override
                public void run() {

                    try {
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder().url
                                (Properties.LOGINPATH).post(new FormBody
                                .Builder().add("username", USER_ID).add
                                ("password", psw).build()).build();
                        Response response = client.newCall(request).execute();
                        if (response.isSuccessful()) {
                            sessionId = response.body().string();
                            Log.i("Session", sessionId);
                            if (onOssClient != null) {

                                onOssClient.onClient(true);
                            }
                        } else {
                            if (onOssClient != null) {

                                onOssClient.onClient(false);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }.start();

        }


    }

    public static void initOssClient() {

        HttpUtils.sendOkHttpRequest(REQUEST_PATH, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                OssBean ossBean = gson.fromJson(response.body().string
                        (), OssBean.class);
                String KEY_ID = ossBean.getCredentials().getAccessKeyId();
                String TOKEN = ossBean.getCredentials().getSecurityToken();
                String SECRET_KEY_ID = ossBean.getCredentials().getAccessKeySecret();
                OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(KEY_ID, SECRET_KEY_ID, TOKEN);
                ClientConfiguration conf = new ClientConfiguration();
                conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
                conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
                conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个
                conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次

                ossClient = new OSSClient(context, END_POINT, credentialProvider);

            }
        });
    }

    public static boolean upDataPersonLoginInfo(String id, String psw) {
        SharedPreferences.Editor edit = config.edit();
        edit.putString("Id", id);
        edit.putString("Psw", psw);
        edit.putBoolean("logined", true);
        return edit.commit();
    }
    public static boolean userOutLogin(){
        SharedPreferences.Editor edit = config.edit();
        edit.putString("Id","");
        edit.putString("Psw","");
        edit.putBoolean("logined", false);
        return edit.commit();
    }

    public interface OnOssClient {
        void onClient(Boolean flag);
    }

    private static final String TAG = "ALI_PUSH_SERVICE";

    private static CloudPushService pushService;

    private void initCloudChannel(Context applicationContext) {
        PushServiceFactory.init(applicationContext);
        pushService = PushServiceFactory.getCloudPushService
                ();
        pushService.register(applicationContext, new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                Log.d(TAG, "init cloudchannel success");
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                Log.d(TAG, "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
            }
        });
        if (config.getBoolean("logined", false)) {
            String username = config.getString("Id", "");
            if (!username.equals(""))
                pushService.bindAccount(username, new CommonCallback() {
                    @Override
                    public void onSuccess(String s) {
                        Log.i("ClodePush", "Success");
                    }

                    @Override
                    public void onFailed(String s, String s1) {
                        Log.i("ClodePush", "onFailed");

                    }
                });

        }

    }

    public static void setOnCloundPush(String email) {
        pushService.bindAccount(email, new CommonCallback() {
            @Override
            public void onSuccess(String s) {
                Log.i("ClodePush", "Success");
            }

            @Override
            public void onFailed(String s, String s1) {
                Log.i("ClodePush", "onFailed");

            }
        });
    }
}
