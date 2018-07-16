package com.lucky.sweet.entity;

import com.lucky.sweet.utility.OssUtils;

import java.util.List;

public class MyCommentEntiy {

    private List<CircleReplyLsitBean> circle_reply_lsit;

    public List<CircleReplyLsitBean> getCircle_reply_lsit() {
        return circle_reply_lsit;
    }

    public void setCircle_reply_lsit(List<CircleReplyLsitBean> circle_reply_lsit) {
        this.circle_reply_lsit = circle_reply_lsit;
    }

    public static class CircleReplyLsitBean {
        /**
         * reply_circle_id : 5_28
         * circle_id : 6_7
         * content : AAAAA
         * reply_user : 5
         * reply_user_nickname :
         * publish_photo : sweet/circle/a/20180321165609/0jpg
         */

        private String reply_circle_id;
        private String circle_id;
        private String content;
        private String reply_user;
        private String reply_user_nickname;
        private String publish_photo;

        public String getReply_circle_id() {
            return reply_circle_id;
        }

        public void setReply_circle_id(String reply_circle_id) {
            this.reply_circle_id = reply_circle_id;
        }

        public String getCircle_id() {
            return circle_id;
        }

        public void setCircle_id(String circle_id) {
            this.circle_id = circle_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getReply_user() {
            return reply_user;
        }

        public void setReply_user(String reply_user) {
            this.reply_user = reply_user;
        }

        public String getReply_user_nickname() {
            return reply_user_nickname;
        }

        public void setReply_user_nickname(String reply_user_nickname) {
            this.reply_user_nickname = reply_user_nickname;
        }

        public String getPublish_photo() {
            if (!publish_photo.contains("http")) {
                publish_photo= OssUtils.getOSSExtranetPath(publish_photo);
            }
            return publish_photo;
        }

        public void setPublish_photo(String publish_photo) {
            this.publish_photo = publish_photo;
        }
    }
}
