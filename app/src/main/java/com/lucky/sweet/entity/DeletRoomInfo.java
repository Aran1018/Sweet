package com.lucky.sweet.entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by C on 2018/3/24.
 */

public class DeletRoomInfo {
    private String userId;
    private String time;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public DeletRoomInfo(String userId, String time) {

        this.userId = userId;
        this.time = time;
    }

    public String toJsonString() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", userId);
            jsonObject.put("time", time);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}
