package com.lucky.sweet.entity;

import com.lucky.sweet.utility.OssUtils;

import java.util.List;

/**
 * Created by C on 2018/3/12.
 */

public class FlowPeople {
    private List<AttentListBean> attent_list;

    public List<AttentListBean> getAttent_list() {
        return attent_list;
    }

    public void setAttent_list(List<AttentListBean> attent_list) {
        this.attent_list = attent_list;
    }

    public static class AttentListBean {
        /**
         * user_id : 4
         * nickname : 大外只有一个浩南
         * photo : sweet/circle/a/20180321165609/3jpg
         */

        private String user_id;
        private String nickname;
        private String photo;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPhoto() {
            if (!photo.contains("http")) {
                photo = OssUtils.getOSSExtranetPath(photo);
            }
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }
    }
}
