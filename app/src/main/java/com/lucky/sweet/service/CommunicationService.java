package com.lucky.sweet.service;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.lucky.sweet.activity.MyApplication;
import com.lucky.sweet.activity.OrderSeatActivity;
import com.lucky.sweet.entity.AlterOrderInfo;
import com.lucky.sweet.entity.ChangeNameRequstInfo;
import com.lucky.sweet.entity.CircleDetail;
import com.lucky.sweet.entity.CircleLikePoint;
import com.lucky.sweet.entity.CircleMainInfo;
import com.lucky.sweet.entity.CircleUpDataInfo;
import com.lucky.sweet.entity.CollectStoreEntitiy;
import com.lucky.sweet.entity.DeletRoomInfo;
import com.lucky.sweet.entity.DetailOrderInfo;
import com.lucky.sweet.entity.FlowPeople;
import com.lucky.sweet.entity.GetMailVerInfo;
import com.lucky.sweet.entity.InternetTime;
import com.lucky.sweet.entity.InvitationInfo;
import com.lucky.sweet.entity.JoinInRoomInfo;
import com.lucky.sweet.entity.JoinRoomInfo;
import com.lucky.sweet.entity.LikeShopEntity;
import com.lucky.sweet.entity.OrderDisInfo;
import com.lucky.sweet.entity.ShopCarSingleInformation;
import com.lucky.sweet.entity.StatueCheckBaseEntitiy;
import com.lucky.sweet.entity.MailValiInfo;
import com.lucky.sweet.entity.MainStoreInfo;
import com.lucky.sweet.entity.MyCommentEntiy;
import com.lucky.sweet.entity.PerdetermingEntity;
import com.lucky.sweet.entity.PersonCollectInfo;
import com.lucky.sweet.entity.PersonInfo;
import com.lucky.sweet.entity.SearchFriendInfo;
import com.lucky.sweet.entity.ShopCarEntity;
import com.lucky.sweet.entity.ShopDetailPicInfo;
import com.lucky.sweet.entity.StoreDetailedInfo;
import com.lucky.sweet.entity.StoreDisplayInfo;
import com.lucky.sweet.entity.StoreDisplaySearchEntity;
import com.lucky.sweet.entity.UpServiceDesInfo;
import com.lucky.sweet.entity.UserRegestInfo;
import com.lucky.sweet.entity.VipCardInfo;
import com.lucky.sweet.entity.WeatherInfo;
import com.lucky.sweet.handler.OrderSeatHandler;
import com.lucky.sweet.model.shoppingcar.mode.ShopProduct;
import com.lucky.sweet.properties.CircleProperties;
import com.lucky.sweet.properties.PersonProperties;
import com.lucky.sweet.properties.Properties;
import com.lucky.sweet.properties.ReserveProperties;
import com.lucky.sweet.properties.ServiceProperties;
import com.lucky.sweet.utility.HttpUtils;
import com.lucky.sweet.utility.MyOkhttpHelper;
import com.lucky.sweet.utility.OssUtils;
import com.lucky.sweet.utility.PanduanNet;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.lucky.sweet.properties.ServiceProperties.TEST_BUCKET;


/**
 * Created by chn on 2018/1/17.
 * (╯°□°）╯︵ ┻━┻ MMP好气啊！
 * (╯‵□′)╯︵┻━┻ 老子怒掀桌子！
 * ┻━┻︵╰(‵□′)╯︵┻━┻老子双手掀桌！
 * ┬─┬﻿ ノ( ゜-゜ノ) 算了，我不生气了！日子还得过老老实实敲吧~
 */

public class CommunicationService extends Service {

    private TencentLocationManager locationManager;
    private final String TAG = "Service_";

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }


    public class MyBinder extends Binder {
        /**
         * 用户登陆
         *
         * @param email    用户邮箱
         * @param password 密码
         */
        public void userLogin(String email, String password) {

            CommunicationService.this.userLogin(email, password);

        }

        /**
         * 请求验证码
         *
         * @param email      邮箱字符串
         * @param isRegister
         */
        public void checkOutEmail(String email, Boolean isRegister) {
            userRegisterSubmitEmail(email, isRegister);
        }

        /**
         * 提交验证码和邮件地址
         *
         * @param email       邮件地址
         * @param verPassword 验证码
         * @return
         */
        public void emailVer(String email, String verPassword, Boolean isRegtst) {
            mailValidate(email, verPassword, isRegtst);
        }


        /**
         * 提交邮箱和密码
         *
         * @param email      邮箱
         * @param password   密码
         * @param isRegister
         */
        public void userRegister(String email, String password, Boolean isRegister) {
            userWrite(email, password, isRegister);

        }

        public void loginedChanged(String psw) {
            CommunicationService.this.loginedChanged(psw);

        }

        public void requestImStoreInfo(Activity activity) {

            initLocation(activity);

        }


        public void requestShopDisplay(String mer_id) {

            CommunicationService.this.getParticularInfo(mer_id);
            CommunicationService.this.isCollectShopInfo(mer_id);

        }

        public void getParticularCircleInfo(String mer_id, int start) {
            CommunicationService.this.getParticularCircleInfo(mer_id, start);


        }

        public void requestPerdeterming(OrderSeatActivity activity) {
            final OrderSeatHandler orderSeatHandler = new OrderSeatHandler(activity);
            CommunicationService.this.requestPredetermined(entitys -> {

                Message msg = new Message();
                msg.obj = entitys;
                msg.what = OrderSeatHandler.UPDATE_DIALOGDAT;
                orderSeatHandler.sendMessage(msg);
            });
        }

        public void requestStoreDisplayInfo(String project, String circle, String rank, String num) {

            getStoreDisplayInfo(project, circle, rank, num);

        }

        public void requestStoreSearch() {
            getStoreDisplaySearchTitle();

        }

        public void sendOrderSeatInfo(String time, String num, String peopleNum, String photo, String des) {
            requestOrderSeatInfo(time, num, peopleNum, photo, des);
        }

        public void ossUpdata(final String objectkey, final String filepath) {

            CommunicationService.this.ossUpdata(objectkey, filepath, null);
        }

        public void ossCirclePicUpdata(final ArrayList<String> paths, String
                content) {
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");// HH:mm:ss
            //获取当前时间
            final Date date = new Date(System.currentTimeMillis());
            JsonArray jsonElements = new JsonArray();
            for (int i = 0; i < paths.size(); i++) {
                final String path = paths.get(i);
                String[] split = path.split("\\.");
                final String ossPath = CircleProperties.SEND_CIRCLE_PIC_OSS_PAHT +
                        MyApplication.USER_ID + "/" + simpleDateFormat.format(date) + "/" + i + split[split.length - 1];
                jsonElements.add(ossPath);
                new Thread() {
                    @Override
                    public void run() {
                        ossUpdata(ossPath, path);
                    }
                }.start();
            }

            sendCircleInfo(jsonElements.toString().trim(), content);
        }

        public void ossUpdata(final String objectkey, final byte[] bytes) {
            CommunicationService.this.ossUpdata(
                    objectkey, bytes, null);
        }

        public void ossDownload(final String objectKey) {
            ossPicDown(objectKey);

        }

        public void getPersonPortrait(final String objectKey) {
            ossPicDown(objectKey);
        }

        public void upDataPersonPortrait(final String objectkey, final String
                filepath, OnUpdaSuccess onUpdaSuccess) {

            CommunicationService.this.ossUpdata(objectkey, filepath, onUpdaSuccess);
        }

        public void shopCarRequest(String mer_id) {

            requestShopCarInfo(mer_id);
        }

        public void shopMultCarRequest(String room_id, JoinRoomInfo info) {

            requestMoreShopCarInfo(room_id, info);
        }

        public void sendCircleInfo(String photoPath, String content) {
            CommunicationService.this.sendCircleInfo(photoPath, content);
        }

        public void createReserveRoom(String mer_id) {

            CommunicationService.this.createReserveRoom(mer_id);

        }

        public void joinInReserveRoom(String roomId, String psw) {

            CommunicationService.this.joinInReserveRoom(roomId, psw);

        }


        public void dishesMenuUpdata(String roomId, String item,
                                     String incr_decr, String key) {

            CommunicationService.this.dishesMenuUpdata(roomId, item,
                    incr_decr, key);
        }

        public void requestCircleInfo(String type, int loacation) {

            CommunicationService.this.requestCircleInfo(type, loacation);
        }

        public void sendCircledetailsInfo(String circle_id) {
            CommunicationService.this.sendCircledetailsInfo(circle_id);

        }

        public void circleLikeIt(String circle_id, int position) {
            CommunicationService.this.circleLikeIt(circle_id, position);
        }

        public void requestPersonVipCard() {
            CommunicationService.this.requestPersonVipCard();
        }

        public void getFlowFriends() {
            CommunicationService.this.getFlowFriends();
        }

        public void invitationFriend(String user_id, InvitationInfo info) {
            CommunicationService.this.invitationFriend(user_id, info);
        }

        public void getPersonInfo() {
            CommunicationService.this.getPersonInfo();
        }

        public void getAlterOrder() {
            CommunicationService.this.getAlterOrder();
        }

        public void getDetailedOrderInfo(String indent_id) {
            CommunicationService.this.getDetailedOrderInfo(indent_id);
        }

        public void getWaitOrder() {
            CommunicationService.this.getWaitOrder();
        }

        public void cancelOrder(String mer_id, String indent_id) {
            CommunicationService.this.cancelOrder(mer_id, indent_id);
        }

        public void searchFriend(String search) {
            CommunicationService.this.searchFriend(search);
        }


        public void upDickesInfo(String mer_id, DeletRoomInfo entity, String room_id, String indent_info, String trolley_list, String money) {
            CommunicationService.this.upDickesInfo(mer_id, entity, room_id, indent_info, trolley_list, money);
        }

        public void upPersonPic(String photo_path) {
            CommunicationService.this.upPersonPic(photo_path);

        }

        public void upPersonNikName(String nickname) {
            CommunicationService.this.upPersonNikName(nickname);
        }

        public void getCurrentIntentTime() {
            CommunicationService.this.getCurrentIntentTime();
        }

        public void getMultShopCarInfo(String room_id) {
            CommunicationService.this.getMultShopCarInfo(room_id);

        }

        public void getShopDetailInfo(String mer_id, String type) {
            CommunicationService.this.getShopDetailInfo(mer_id, type);
        }

        public void commentCircle(String reply_user, String reply_circle_id, String mer_id, String content) {
            CommunicationService.this.commentCircle(reply_user, reply_circle_id, mer_id, content);

        }

        public void closeOrder(DeletRoomInfo deletRoomInfo, String room_id) {
            CommunicationService.this.deleteRoom(deletRoomInfo, room_id);
        }

        public void flowPeople(String userId, boolean isFlow) {
            CommunicationService.this.flowPeople(userId, isFlow);
        }

        public void getPersonCollect() {
            CommunicationService.this.personCollect();
        }

        public void collectShop(String mer_id, boolean isCollect) {
            CommunicationService.this.collectShop(mer_id, isCollect);
        }

        public void deleteCircle(String circle_id) {
            CommunicationService.this.deleteCircle(circle_id);
        }

        public void getPersonComment() {
            CommunicationService.this.getPersonComment();
        }

        public void upServerInfo(ShopCarSingleInformation info, OrderDisInfo disInfo) {

            UpServiceDesInfo upServiceDesInfo = new UpServiceDesInfo();
            upServiceDesInfo.setPay(info.getSaleSum());
            upServiceDesInfo.setMerId(info.getMer_id());

            List<UpServiceDesInfo.Diskesinfo> list = new ArrayList<>();
            UpServiceDesInfo.Diskesinfo diskesinfo;
            for (ShopProduct entity : info.getProductList()) {
                diskesinfo = new UpServiceDesInfo.Diskesinfo();
                diskesinfo.setGoodsName(entity.getGoods());
                diskesinfo.setPay(entity.getPrice());
                diskesinfo.setDisNum(entity.getNumber());
                list.add(diskesinfo);
            }
            upServiceDesInfo.setList(list);
            CommunicationService.this.upServerInfo(upServiceDesInfo, disInfo);
        }
    }

    private void upServerInfo(UpServiceDesInfo upServiceDesInfo, OrderDisInfo disInfo) {
        HashMap<String, String> map = new HashMap<>();
        map.put("session", MyApplication.sessionId);
        map.put("mer_id", upServiceDesInfo.getMerId());
        map.put("trolley_list", new Gson().toJson(upServiceDesInfo));
        map.put("indent_info", new Gson().toJson(disInfo));
        HttpUtils.sendOkHttpRequest(ReserveProperties.UP_SERVER_INFO, new MyOkhttpHelper() {
            @Override
            public void onResponseSuccessfulString(String string) {

            }

            @Override
            public void afterNewRequestSession() {

            }
        }, map);
    }

    private void loginedChanged(String psw) {
        HashMap<String, String> map = new HashMap<>();
        map.put("session", MyApplication.sessionId);
        map.put("psw", psw);
        HttpUtils.sendOkHttpRequest(PersonProperties.LOGIN_CHANGE_PSW, new MyOkhttpHelper() {
            @Override
            public void onResponseSuccessfulString(String string) {

                EventBus.getDefault().post(new StatueCheckBaseEntitiy(string));

            }

            @Override
            public void afterNewRequestSession() {

            }
        }, map);
    }

    public void getPersonComment() {
        HashMap<String, String> map = new HashMap<>();
        map.put("session", MyApplication.sessionId);
        map.put("start", "0");
        HttpUtils.sendOkHttpRequest(PersonProperties.PERSON_COMMENT_LIST, new MyOkhttpHelper() {
            @Override
            public void onResponseSuccessfulString(String string) {
                EventBus.getDefault().post(new Gson().fromJson(string, MyCommentEntiy.class));

            }

            @Override
            public void afterNewRequestSession() {
                getPersonComment();
            }
        }, map);

    }

    private void deleteCircle(String circle_id) {
        HashMap<String, String> map = new HashMap<>();
        map.put("session", MyApplication.sessionId);
        map.put("circle_id", circle_id);
        HttpUtils.sendOkHttpRequest(CircleProperties.DELET_CIRCLE, new MyOkhttpHelper() {
            @Override
            public void onResponseSuccessfulString(String string) {


            }

            @Override
            public void afterNewRequestSession() {

            }
        }, map);

    }

    private void collectShop(String mer_id, boolean isCollect) {
        String path;
        String type;
        if (isCollect) {
            type = "collect_mer_id";
            path = ReserveProperties.COLLECT_SHOP;
        } else {
            path = ReserveProperties.UN_COLLECT_SHOP;
            type = "uncollect_mer_id";
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("session", MyApplication.sessionId);
        map.put(type, mer_id);
        new Thread() {
            @Override
            public void run() {
                HttpUtils.sendOkHttpRequest(path, new MyOkhttpHelper() {
                    @Override
                    public void onResponseSuccessfulString(String string) {

                        EventBus.getDefault().post(new CollectStoreEntitiy(string, isCollect));

                    }

                    @Override
                    public void afterNewRequestSession() {

                    }
                }, map);
            }
        }.start();

    }

    private void personCollect() {
        HashMap<String, String> map = new HashMap<>();
        map.put("session", MyApplication.sessionId);
        HttpUtils.sendOkHttpRequest(PersonProperties.PERSON_COLLECT, new MyOkhttpHelper() {
            @Override
            public void onResponseSuccessfulString(String string) {

                EventBus.getDefault().post(new Gson().fromJson(string, PersonCollectInfo.class));
            }

            @Override
            public void afterNewRequestSession() {

            }
        }, map);


    }

    private void commentCircle(String reply_user, String reply_circle_id, String mer_id, String content) {
        HashMap<String, String> map = new HashMap<>();
        map.put("reply_user", reply_user);
        map.put("reply_circle_id", reply_circle_id);
        map.put("mer_id", mer_id);
        map.put("content", content);
        map.put("session", MyApplication.sessionId);
        HttpUtils.sendOkHttpRequest(CircleProperties.COMMENT_PEOPLE, new MyOkhttpHelper() {
            @Override
            public void onResponseSuccessfulString(String string) {

                EventBus.getDefault().post(new CircleUpDataInfo(string));
            }

            @Override
            public void afterNewRequestSession() {

            }
        }, map);


    }


    private void getShopDetailInfo(String mer_id, String type) {
        HashMap<String, String> map = new HashMap<>();
        map.put("mer_id", "1");
        map.put("type", type);
        HttpUtils.sendOkHttpRequest(ReserveProperties.SHOP_DETAIL, new MyOkhttpHelper() {
            @Override
            public void onResponseSuccessfulString(String string) {
                EventBus.getDefault().post(new Gson().fromJson(string.trim(), ShopDetailPicInfo.class));
            }

            @Override
            public void afterNewRequestSession() {

            }
        }, map);
    }

    public void getCurrentIntentTime() {
        HttpUtils.sendOkHttpRequest(ServiceProperties.TIMECHECK, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                EventBus.getDefault().post(new Gson().fromJson(response.body().string(), InternetTime.class));

            }
        });
    }

    public void getMultShopCarInfo(String room_id) {
        HashMap<String, String> map = new HashMap<>();
        map.put("room_id", room_id);
        map.put("session", MyApplication.sessionId);
        HttpUtils.sendOkHttpRequest(ReserveProperties.GET_SERVER_DIS, new MyOkhttpHelper() {
            @Override
            public void onResponseSuccessfulString(String string) {
                System.out.println(string);
            }

            @Override
            public void afterNewRequestSession() {

            }
        }, map);

    }

    private void upPersonNikName(String nickname) {
        HashMap<String, String> map = new HashMap<>();
        map.put("nickname", nickname);
        map.put("session", MyApplication.sessionId);
        HttpUtils.sendOkHttpRequest(PersonProperties.CHANGE_NICKNAME, new MyOkhttpHelper() {
            @Override
            public void onResponseSuccessfulString(String string) {
                EventBus.getDefault().post(new ChangeNameRequstInfo(string));
            }

            @Override
            public void afterNewRequestSession() {

            }
        }, map);
    }

    private void upPersonPic(String photo_path) {
        HashMap<String, String> map = new HashMap<>();
        map.put("photo_path", photo_path);
        map.put("session", MyApplication.sessionId);
        HttpUtils.sendOkHttpRequest(PersonProperties.ADD_PERSON_PIC, new MyOkhttpHelper() {
            @Override
            public void onResponseSuccessfulString(String string) {


            }

            @Override
            public void afterNewRequestSession() {

            }
        }, map);
    }

    private void upDickesInfo(String mer_id, DeletRoomInfo entity, String room_id, String indent_info, String trolley_list, String money) {
        HashMap<String, String> map = new HashMap<>();
        map.put("session", MyApplication.sessionId);
        map.put("mer_id", "1");
        map.put("key_value", entity.toJsonString());
        map.put("room_id", room_id);
        map.put("indent_info", indent_info);
        map.put("trolley_list", trolley_list);
        map.put("money", money);
        HttpUtils.sendOkHttpRequest(Properties.UPLOAD_FINISH_ORDER, new MyOkhttpHelper() {
            @Override
            public void onResponseSuccessfulString(String string) {
                Log.i("Shop_delet", string);
            }

            @Override
            public void afterNewRequestSession() {

            }
        }, map);
    }


    private void deleteRoom(DeletRoomInfo deletRoomInfo, String room_id) {
        HashMap<String, String> map = new HashMap<>();
        map.put("session", MyApplication.sessionId);
        map.put("key_value", deletRoomInfo.toJsonString());
        map.put("room_id", room_id);
        HttpUtils.sendOkHttpRequest(ReserveProperties.DELETE_ROOM, new MyOkhttpHelper() {
            @Override
            public void onResponseSuccessfulString(String string) {


            }

            @Override
            public void afterNewRequestSession() {
                deleteRoom(deletRoomInfo, room_id);
            }
        }, map);
    }

    private void searchFriend(String searchName) {
        HashMap<String, String> map = new HashMap<>();
        map.put("session", MyApplication.sessionId);
        map.put("name_part", searchName);
        map.put("start", "0");
        HttpUtils.sendOkHttpRequest(ReserveProperties.SEARCH_FRIEND, new MyOkhttpHelper() {
            @Override
            public void onResponseSuccessfulString(String string) {

                EventBus.getDefault().post(new Gson().fromJson(string, SearchFriendInfo.class));

            }

            @Override
            public void afterNewRequestSession() {
                searchFriend(searchName);
            }
        }, map);

    }

    private void cancelOrder(String mer_id, String indent_id) {
        HashMap<String, String> map = new HashMap<>();
        map.put("session", MyApplication.sessionId);
        map.put("mer_id", mer_id);
        map.put("indent_id", indent_id);
        HttpUtils.sendOkHttpRequest(ReserveProperties.CANCEL_OREDER, new MyOkhttpHelper() {
            @Override
            public void onResponseSuccessfulString(String string) {

            }

            @Override
            public void afterNewRequestSession() {
                getPersonInfo();
            }
        }, map);

    }

    public void getWaitOrder() {
        HashMap<String, String> map = new HashMap<>();
        map.put("session", MyApplication.sessionId);
        HttpUtils.sendOkHttpRequest(ReserveProperties.WAIT_ORDER, new MyOkhttpHelper() {
            @Override
            public void onResponseSuccessfulString(String string) {
                System.out.println(string);
                EventBus.getDefault().post(new Gson().fromJson(string, AlterOrderInfo.class));

            }

            @Override
            public void afterNewRequestSession() {
                getPersonInfo();
            }
        }, map);
    }

    private void getDetailedOrderInfo(String indent_id) {
        HashMap<String, String> map = new HashMap<>();
        map.put("session", MyApplication.sessionId);
        map.put("indent_id", indent_id);
        HttpUtils.sendOkHttpRequest(PersonProperties.DETAILED_ORDER_INFO, new MyOkhttpHelper() {
            @Override
            public void onResponseSuccessfulString(String string) {
                EventBus.getDefault().post(new Gson().fromJson(string, DetailOrderInfo.class));

            }

            @Override
            public void afterNewRequestSession() {
                getPersonInfo();
            }
        }, map);
    }

    public void getAlterOrder() {
        HashMap<String, String> map = new HashMap<>();
        map.put("session", MyApplication.sessionId);
        HttpUtils.sendOkHttpRequest(PersonProperties.ALTER_ORDER, new MyOkhttpHelper() {
            @Override
            public void onResponseSuccessfulString(String string) {
                System.out.println(string);
                EventBus.getDefault().post(new Gson().fromJson(string, AlterOrderInfo.class));
            }

            @Override
            public void afterNewRequestSession() {
                getPersonInfo();
            }
        }, map);
    }

    public void getPersonInfo() {
        HashMap<String, String> map = new HashMap<>();
        map.put("session", MyApplication.sessionId);
        HttpUtils.sendOkHttpRequest(PersonProperties.PERSON_INFO, new MyOkhttpHelper() {
            @Override
            public void onResponseSuccessfulString(String string) {
                EventBus.getDefault().post(new Gson().fromJson(string, PersonInfo.class));
            }

            @Override
            public void afterNewRequestSession() {
                getPersonInfo();
            }
        }, map);
    }

    private void invitationFriend(String inviteUserId, InvitationInfo info) {
        HashMap<String, String> map = new HashMap<>();
        map.put("invite_user_id", inviteUserId);
        map.put("session", MyApplication.sessionId);
        map.put("key_value", new Gson().toJson(info));
        HttpUtils.sendOkHttpRequest(ReserveProperties.INVITATION_FRIEND, new MyOkhttpHelper() {
            @Override
            public void onResponseSuccessfulString(String string) {
                System.out.println(string);
            }

            @Override
            public void afterNewRequestSession() {
                invitationFriend(inviteUserId, info);
            }
        }, map);
    }

    private void getFlowFriends() {
        HashMap<String, String> map = new HashMap<>();
        map.put("session", MyApplication.sessionId);
        HttpUtils.sendOkHttpRequest(PersonProperties.FOLW_FRIEND, new MyOkhttpHelper() {
            @Override
            public void onResponseSuccessfulString(String string) {

                EventBus.getDefault().post(new Gson().fromJson(string, FlowPeople.class));
            }

            @Override
            public void afterNewRequestSession() {

            }
        }, map);
    }

    private void requestPersonVipCard() {
        HashMap<String, String> map = new HashMap<>();
        map.put("session", MyApplication.sessionId);
        HttpUtils.sendOkHttpRequest(PersonProperties.PERSON_VIP_CARD,
                new MyOkhttpHelper() {

                    @Override
                    public void onResponseSuccessfulString(String string) {

                        EventBus.getDefault().post(new Gson().fromJson(string, VipCardInfo.class));
                    }

                    @Override
                    public void afterNewRequestSession() {
                        requestPersonVipCard();
                    }
                }, map);


    }

    private void circleLikeIt(final String circle_id, final int position) {

        HashMap<String, String> map = new HashMap<>();
        map.put("circle_id", circle_id);
        map.put("session", MyApplication.sessionId);
        HttpUtils.sendOkHttpRequest(CircleProperties.LIKE_POINT,
                new MyOkhttpHelper() {

                    @Override
                    public void onResponseSuccessfulString(String string) {
                        CircleLikePoint circleLikePoint = new CircleLikePoint();
                        circleLikePoint.setPosition(position);
                        circleLikePoint.setRequest(string);
                        EventBus.getDefault().post(circleLikePoint);
                    }

                    @Override
                    public void afterNewRequestSession() {
                        circleLikeIt(circle_id, position);
                    }
                }, map);
    }

    private void sendCircledetailsInfo(final String circle_id) {
        HashMap<String, String> map = new HashMap<>();
        map.put("circle_id", circle_id);
        map.put("session", MyApplication.sessionId);
        HttpUtils.sendOkHttpRequest(CircleProperties.CIRCLE_CONTENT_DETAILS,
                new MyOkhttpHelper() {

                    @Override
                    public void onResponseSuccessfulString(String string) {
                        EventBus.getDefault().post(new Gson().fromJson(string, CircleDetail.class));
                    }

                    @Override
                    public void afterNewRequestSession() {
                        sendCircledetailsInfo(circle_id);
                    }
                }, map);
    }

    private void flowPeople(String userId, boolean flag) {
        String type;
        String path;
        if (flag) {
            type = "attention_by_user_id";
            path = CircleProperties.FLOW_PEOPLE;
        } else {
            type = "unattention_by_user_id";
            path = CircleProperties.UN_FLOW_PEOPLE;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put(type, userId);
        map.put("session", MyApplication.sessionId);
        HttpUtils.sendOkHttpRequest(path,
                new MyOkhttpHelper() {
                    @Override
                    public void onResponseSuccessfulString(String string) {
                        System.out.println(string);
                        //EventBus.getDefault().post(new Gson().fromJson(string, CircleMainInfo.class));
                    }

                    @Override
                    public void afterNewRequestSession() {
                        flowPeople(userId, flag);
                    }
                }, map);
    }

    private void requestCircleInfo(final String type, final int location) {
        HashMap<String, String> map = new HashMap<>();
        map.put("type", type);
        map.put("session", MyApplication.sessionId);
        map.put("start", location + "");
        HttpUtils.sendOkHttpRequest(CircleProperties.CIRCLE_MAIN_SHOW,
                new MyOkhttpHelper() {
                    @Override
                    public void onResponseSuccessfulString(String string) {
                        EventBus.getDefault().post(new Gson().fromJson(string, CircleMainInfo.class));
                    }

                    @Override
                    public void afterNewRequestSession() {
                        requestCircleInfo(type, location);
                    }
                }, map);

    }

    private void dishesMenuUpdata(final String roomId, final String item, final String incr_decr, final String key) {
        HashMap<String, String> map = new HashMap<>();
        map.put("session", MyApplication.sessionId);
        map.put("room_id", roomId);
        map.put("item", item);
        map.put("incr_decr", incr_decr);
        map.put("key_value", key);

        HttpUtils.sendOkHttpRequest(ReserveProperties.UPDATA_MENU,
                new MyOkhttpHelper() {
                    @Override
                    public void onResponseSuccessfulString(String string) {
                        System.out.println(string);

                    }

                    @Override
                    public void afterNewRequestSession() {
                        dishesMenuUpdata(roomId, item, incr_decr, key);
                    }
                }, map);
    }

    private void joinInReserveRoom(final String roomId, final String psw) {
        try {
            HashMap<String, String> map = new HashMap<>();
            String userId = MyApplication.USER_ID;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", userId);
            map.put("session", MyApplication.sessionId);
            map.put("room_id", roomId);
            map.put("password", psw);
            map.put("key_value", jsonObject.toString());

            HttpUtils.sendOkHttpRequest(ReserveProperties.JOIN_IN_ROOM,
                    new MyOkhttpHelper() {

                        @Override
                        public void onResponseSuccessfulString(String string) {
                            EventBus.getDefault().post(new JoinInRoomInfo(string));

                        }

                        @Override
                        public void afterNewRequestSession() {
                            joinInReserveRoom(roomId, psw);
                        }
                    }, map);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void createReserveRoom(final String mer_id) {
        HashMap<String, String> map = new HashMap<>();
        map.put("session", MyApplication.sessionId);
        map.put("mer_id", "1");
        HttpUtils.sendOkHttpRequest(ReserveProperties.CREATE_ROOM,
                new MyOkhttpHelper() {

                    @Override
                    public void onResponseSuccessfulString(String string) {
                        EventBus.getDefault().post(string);

                    }

                    @Override
                    public void afterNewRequestSession() {
                        createReserveRoom(mer_id);
                    }
                }, map);
    }

    //todo 确认mer_id
    private void sendCircleInfo(final String photoPath, final String content) {
        HashMap<String, String> map = new HashMap<>();
        map.put("photo", photoPath);
        map.put("session", MyApplication.sessionId);
        map.put("mer_id", "1");
        map.put("content", content);
        HttpUtils.sendOkHttpRequest(CircleProperties.SEND_CIRCLE, new MyOkhttpHelper() {

            @Override
            public void onResponseSuccessfulString(String string) {
                EventBus.getDefault().post(string);
            }

            @Override
            public void afterNewRequestSession() {
                sendCircleInfo(photoPath, content);
            }
        }, map);
    }


    private void ossUpdata(final String objectkey, final
    String filepath, final OnUpdaSuccess onUpdaSuccess) {
        OSSClient ossClient = MyApplication.getOSSClient();
        if (ossClient != null) {
            PutObjectRequest put = new PutObjectRequest(TEST_BUCKET, objectkey, filepath);
            setServiceCallBack(put, ossClient, onUpdaSuccess);
        }


    }

    private void ossUpdata(final String objectkey, final
    byte[] bytes, final OnUpdaSuccess onUpdaSuccess) {
        OSSClient ossClient = MyApplication.getOSSClient();
        if (ossClient != null) {
            PutObjectRequest put = new PutObjectRequest(TEST_BUCKET, objectkey, bytes);
            setServiceCallBack(put, MyApplication.getOSSClient(), onUpdaSuccess);
        }


    }

    private void ossPicDown(final String objectKey) {

        EventBus.getDefault().post(OssUtils.getOSSExtranetPath(objectKey));


    }

    public interface OnUpdaSuccess {
        void success();
    }

    private static void setServiceCallBack(PutObjectRequest put, OSS oss,
                                           final OnUpdaSuccess onUpdaSuccess) {

        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
            }
        });
        oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                if (onUpdaSuccess != null) {
                    onUpdaSuccess.success();
                }
                Log.d("PutObject", "UploadSuccess");
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });
    }


    private void requestOrderSeatInfo(final String time, final String num, final String peopleNum, final String photo, final String des) {
        HashMap<String, String> map = new HashMap<>();
        map.put("tim", time);
        map.put("num", num);
        map.put("peopleNum", peopleNum);
        map.put("photo", photo);
        map.put("des", des);
        map.put("session", MyApplication.sessionId);
        HttpUtils.sendOkHttpRequest(Properties.MAINSHOWPLAYTPATH, new MyOkhttpHelper() {
            @Override
            public void onResponseSuccessfulString(String string) {
                EventBus.getDefault().post(string);
            }

            @Override
            public void afterNewRequestSession() {
                requestOrderSeatInfo(time, num, peopleNum, photo, des);
            }

        }, map);
    }

    private void requestShopCarInfo(final String mer_id) {
        final HashMap<String, String> map = new HashMap<>();
        map.put("mer_id", "1");
        map.put("session", MyApplication.sessionId);
        HttpUtils.sendOkHttpRequest(Properties.SHOP_CAR, new MyOkhttpHelper() {

            @Override
            public void onResponseSuccessfulString(String string) {

                EventBus.getDefault().post(new Gson().fromJson(string, ShopCarEntity.class));

            }

            @Override
            public void afterNewRequestSession() {
                requestShopCarInfo(mer_id);
            }
        }, map);
    }

    private void requestMoreShopCarInfo(String room_id, JoinRoomInfo info) {
        final HashMap<String, String> map = new HashMap<>();
        String jsonKey = info.toJsonString();
        map.put("session", MyApplication.sessionId);
        map.put("room_id", room_id);
        map.put("key_value", jsonKey);
        HttpUtils.sendOkHttpRequest(Properties.SHOP_CAR, new MyOkhttpHelper() {
            @Override
            public void onResponseSuccessfulString(String string) {

                EventBus.getDefault().post(new Gson().fromJson(string, ShopCarEntity.class));

            }

            @Override
            public void afterNewRequestSession() {
                requestMoreShopCarInfo(room_id, info);
            }
        }, map);
    }

    private void getStoreDisplaySearchTitle() {
        HashMap<String, String> map = new HashMap<>();
        map.put("city", MyApplication.CURRENT_CITY);
        HttpUtils.sendOkHttpRequest(Properties.DISPLAYSEARCHTITLE, new MyOkhttpHelper() {
            @Override
            public void onResponseSuccessfulString(String string) {
                System.out.println(string);
                EventBus.getDefault().post(new Gson().fromJson(string, StoreDisplaySearchEntity.class));
            }

            @Override
            public void afterNewRequestSession() {
                getStoreDisplaySearchTitle();
            }
        }, map);
    }

    private interface PerdetermingRequest {
        void getIt(PerdetermingEntity entitys);
    }

    private void requestPredetermined(PerdetermingRequest request) {
        String[] data = {"4-11", "4-12", "4-13", "4-14",
                "4-15", "4-16", "4-17", "4-18", "4-19", "4-20"};
        String[][] time = {
                {
                        "8:00-9:00", "10:00-11:00", "12:00-13:00", "13:00-14:00", "14:00-15:00", "15:00-16:00", "16:00-17:00"
                },
                {
                        "8:00-9:00", "10:00-11:00", "12:00-13:00", "13:00-14:00", "14:00-15:00", "15:00-16:00", "16:00-17:00"

                },
                {
                        "8:00-9:00", "10:00-11:00", "12:00-13:00", "13:00-14:00", "14:00-15:00", "15:00-16:00", "16:00-17:00"
                },
                {
                        "8:00-9:00", "10:00-11:00", "12:00-13:00", "13:00-14:00", "14:00-15:00", "15:00-16:00", "16:00-17:00"

                },
                {
                        "8:00-9:00", "10:00-11:00", "12:00-13:00", "13:00-14:00", "14:00-15:00", "15:00-16:00", "16:00-17:00"

                },
                {
                        "8:00-9:00", "10:00-11:00", "12:00-13:00", "13:00-14:00", "14:00-15:00", "15:00-16:00", "16:00-17:00"

                }, {
                "8:00-9:00", "10:00-11:00", "12:00-13:00", "13:00-14:00", "14:00-15:00", "15:00-16:00", "16:00-17:00"

        }, {
                "8:00-9:00", "10:00-11:00", "12:00-13:00", "13:00-14:00", "14:00-15:00", "15:00-16:00", "16:00-17:00"

        }, {
                "8:00-9:00", "10:00-11:00", "12:00-13:00", "13:00-14:00", "14:00-15:00", "15:00-16:00", "16:00-17:00"

        }, {
                "8:00-9:00", "10:00-11:00", "12:00-13:00", "13:00-14:00", "14:00-15:00", "15:00-16:00", "16:00-17:00"

        }
        };
        request.getIt(new PerdetermingEntity().setData(data).setTime(time));
    }

    private void initShopInfo(final double lat, final double lon) {
        HashMap<String, String> map = new HashMap<>();
        map.put("long", String.valueOf(lon));
        map.put("lat", String.valueOf(lat));
        HttpUtils.sendOkHttpRequest(Properties.MAINSHOWPLAYTPATH, new MyOkhttpHelper() {

            @Override
            public void onResponseSuccessfulString(String string) {
                System.out.println(string);
                EventBus.getDefault().post(new Gson().fromJson(string, MainStoreInfo.class));
            }

            @Override
            public void afterNewRequestSession() {
                initShopInfo(lat, lon);
            }
        }, map);

    }

    private void initLocation(Activity activity) {
        boolean networkState = PanduanNet.detect(activity);

        if (!networkState) {
            Toast.makeText(this, "网络错误，无法获取当前位置", Toast.LENGTH_SHORT).show();
        } else {
            locationManager = TencentLocationManager.getInstance(this);
            locationManager.setCoordinateType(TencentLocationManager.COORDINATE_TYPE_GCJ02);
            TencentLocationRequest request = TencentLocationRequest.create();
            request.setInterval(3000);
            request.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_ADMIN_AREA);
            int requestCode = locationManager.requestLocationUpdates(request, mListener);

        }
    }

    private void initWeather(String city) {
        final String url = ServiceProperties.WEATHERREQUESTBODY + city;
        new Thread(() -> HttpUtils.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Gson gson = new Gson();

                WeatherInfo weatherInfo = gson.fromJson(response.body().string(), WeatherInfo.class);
                EventBus.getDefault().post(weatherInfo);


            }


        })).start();

    }

    private TencentLocationListener mListener = new TencentLocationListener() {
        @Override
        public void onLocationChanged(TencentLocation tencentLocation, int error, String s) {

            if (error == TencentLocation.ERROR_OK) {

                String city = tencentLocation.getCity().trim();
                MyApplication.setCurrenCity(city);
                if (city.length() > 3) {
                    city = city.substring(0, 2) + "...";
                    EventBus.getDefault().post(city);
                }

                if (city.contains("市"))
                    initWeather(city.substring(0, city.length() - 1));
                MyApplication.setCurrentLatAndLon(tencentLocation.getLatitude(), tencentLocation.getLongitude());
                initShopInfo(tencentLocation.getLatitude(), tencentLocation.getLongitude());
            }
            stopLocation();

        }

        @Override
        public void onStatusUpdate(String s, int i, String s1) {

        }
    };

    private void stopLocation() {
        locationManager.removeUpdates(mListener);
    }

    private void isCollectShopInfo(final String shopid) {
        final HashMap<String, String> map = new HashMap<>();
        map.put("mer_id", shopid);
        map.put("session", MyApplication.sessionId);
        new Thread() {
            @Override
            public void run() {
                HttpUtils.sendOkHttpRequest(ReserveProperties.CHECK_SHOP_COLLECT, new
                        MyOkhttpHelper() {

                            @Override
                            public void onResponseSuccessfulString(String string) {

                                EventBus.getDefault().post(new LikeShopEntity(string));
                            }

                            @Override
                            public void afterNewRequestSession() {
                                isCollectShopInfo(shopid);
                            }
                        }, map);
            }
        }.start();
    }

    private void getParticularInfo(final String shopid) {
        final HashMap<String, String> map = new HashMap<>();
        map.put("mer_id", shopid);
        new Thread() {
            @Override
            public void run() {
                HttpUtils.sendOkHttpRequest(Properties.STOREDETAILEDPATH, new
                        MyOkhttpHelper() {

                            @Override
                            public void onResponseSuccessfulString(String string) {
                                EventBus.getDefault().post(new Gson().fromJson(string, StoreDetailedInfo.class));
                            }

                            @Override
                            public void afterNewRequestSession() {
                                getParticularInfo(shopid);
                            }
                        }, map);
            }
        }.start();

    }

    private void getParticularCircleInfo(final String shopId, int start) {
        final HashMap<String, String> map = new HashMap<>();
        map.put("mer_id", "1");
        map.put("session", MyApplication.sessionId);
        map.put("start", start + "");
        new Thread() {
            @Override
            public void run() {
                HttpUtils.sendOkHttpRequest(CircleProperties.SHOP_CIRCLE, new
                        MyOkhttpHelper() {

                            @Override
                            public void onResponseSuccessfulString(String string) {

                                EventBus.getDefault().post(new Gson().fromJson(string, CircleMainInfo.class));
                            }

                            @Override
                            public void afterNewRequestSession() {
                                getParticularCircleInfo(shopId, start);
                            }
                        }, map);
            }
        }.start();

    }

    private void getStoreDisplayInfo(final String project, final String circle, final String rank, final String num) {
        final HashMap<String, String> map = new HashMap<>();
        map.put("small_project", project);
        map.put("circle", circle);
        map.put("city", MyApplication.CURRENT_CITY);
        map.put("rank", rank);
        map.put("num_start", num);
        new Thread() {
            @Override
            public void run() {
                HttpUtils.sendOkHttpRequest(Properties.DISPLAYMAINSHOWPATH, new
                        MyOkhttpHelper() {
                            @Override
                            public void onResponseSuccessfulString(String string) {
                                EventBus.getDefault().post(new Gson().fromJson(string, StoreDisplayInfo.class));
                            }

                            @Override
                            public void afterNewRequestSession() {
                                getStoreDisplayInfo(project, circle, rank, num);
                            }

                        }, map);
            }
        }.start();
    }


    private void userRegisterSubmitEmail(String email, Boolean isRegister) {
        final HashMap<String, String> map = new HashMap<>();
        map.put("mail_address", email);
        String path;
        if (isRegister) path = Properties.MAILSUBMITPATH;
        else path = Properties.FORGETSUBMITPATH;

        new Thread() {
            @Override
            public void run() {
                HttpUtils.sendOkHttpRequest(path, new
                        MyOkhttpHelper() {
                            @Override
                            public void onResponseSuccessfulString(String string) {
                                EventBus.getDefault().post(new GetMailVerInfo(string));
                            }

                            @Override
                            public void afterNewRequestSession() {

                            }

                        }, map);
            }
        }.start();
    }

    private void mailValidate(String email, String ver, boolean regest) {
        final HashMap<String, String> map = new HashMap<>();
        map.put("mail_address", email);
        map.put("mail_ver", ver);
        String path;
        if (regest) {
            path = Properties.MAILVALIDATEPATH;
        } else
            path = Properties.FORGETVALIDATEPATH;
        new Thread() {
            @Override
            public void run() {
                HttpUtils.sendOkHttpRequest(path, new
                        MyOkhttpHelper() {
                            @Override
                            public void onResponseSuccessfulString(String string) {
                                EventBus.getDefault().post(new MailValiInfo(string));
                            }

                            @Override
                            public void afterNewRequestSession() {

                            }

                        }, map);
            }
        }.start();
    }


    private void userWrite(String email, String password, Boolean isRegister) {
        final HashMap<String, String> map = new HashMap<>();

        map.put("username", email);
        map.put("password", password);
        String path;
        if (isRegister)
            path = Properties.USERWRITEPATH;
        else path = Properties.USERFORGETPATH;
        new Thread() {
            @Override
            public void run() {
                HttpUtils.sendOkHttpRequest(path, new
                        MyOkhttpHelper() {
                            @Override
                            public void onResponseSuccessfulString(String string) {
                                UserRegestInfo userRegestInfo = new UserRegestInfo(string);
                                userRegestInfo.setPassword(password);
                                userRegestInfo.setUserID(email);
                                EventBus.getDefault().post(userRegestInfo);
                            }

                            @Override
                            public void afterNewRequestSession() {

                            }

                        }, map);
            }
        }.start();
    }

    private void userLogin(String email, String password) {
        final HashMap<String, String> map = new HashMap<>();
        map.put("username", email);
        map.put("password", password);
        new Thread() {
            @Override
            public void run() {
                HttpUtils.sendOkHttpRequest(Properties.LOGINPATH, new
                        MyOkhttpHelper() {
                            @Override
                            public void onResponseSuccessfulString(String string) {
                                EventBus.getDefault().post(string);

                            }

                            @Override
                            public void afterNewRequestSession() {

                            }

                        }, map);
            }
        }.start();
    }


}
