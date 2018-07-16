package com.lucky.sweet.entity;

import com.lucky.sweet.utility.OssUtils;

import java.util.List;

/**
 * Created by C on 2018/3/21.
 */

public class SearchFriendInfo {

    private List<UserListBean> user_list;

    public List<UserListBean> getUser_list() {
        return user_list;
    }

    public void setUser_list(List<UserListBean> user_list) {
        this.user_list = user_list;
    }

    public static class UserListBean {
        /**
         * nickname : 22阿元测试
         * user : 22
         * id : 3
         * photo : sweet/circle/a/20180321165609/3jpg
         */

        private String nickname;
        private String user;
        private String id;
        private String photo;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPhoto() {
            if (!photo.contains("http")) {
                photo= OssUtils.getOSSExtranetPath(photo);
            }
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }
    }
}
