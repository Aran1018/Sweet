package com.lucky.sweet.entity;

/**
 * Created by C on 2018/3/18.
 */

public class UserRegestInfo extends StatueCheckBaseEntitiy {
    public UserRegestInfo(String info) {
        super(info);
    }

    private String userID;
    private String password;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
