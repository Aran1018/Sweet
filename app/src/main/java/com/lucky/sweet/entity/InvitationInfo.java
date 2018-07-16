package com.lucky.sweet.entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by C on 2018/3/18.
 */

public class InvitationInfo {

    private String userId;
    private String roomId;
    private String merId;
    private String shopName;

    public InvitationInfo(String userId, String roomId, String merId,String shopName) {
        this.userId = userId;
        this.roomId = roomId;
        this.merId = merId;
        this.shopName = shopName;

    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getUserId() {

        return userId;
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }


}
