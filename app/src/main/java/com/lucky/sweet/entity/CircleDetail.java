package com.lucky.sweet.entity;

import java.util.List;

/**
 * Created by chn on 2018/2/20.
 * (╯°□°）╯︵ ┻━┻ MMP好气啊！
 * (╯‵□′)╯︵┻━┻ 老子怒掀桌子！
 * ┻━┻︵╰(‵□′)╯︵┻━┻老子双手掀桌！
 * ┬─┬﻿ ノ( ゜-゜ノ) 算了，我不生气了！日子还得过老老实实敲吧~
 */

public class CircleDetail {
    private List<CommentBean> comment;

    public List<CommentBean> getComment() {
        return comment;
    }

    public void setComment(List<CommentBean> comment) {
        this.comment = comment;
    }

    public static class CommentBean {
        /**
         * circle_id : 5_4
         * user_id : 4
         * mer_id : 1
         * content : 我是一个笨蛋，但是请相信我，我并不是有意的。能原谅我吗？友爱的。
         * create_time : 2018-02-12 14:41:12
         * reply_user : 5
         * nikcname_user : 大外只有一个浩南
         * nikcname_reply_user : aaaa
         */

        private String circle_id;
        private String user_id;
        private String mer_id;
        private String content;
        private String create_time;
        private String reply_user;
        private String nikcname_user;
        private String nikcname_reply_user;

        public String getCircle_id() {
            return circle_id;
        }

        public void setCircle_id(String circle_id) {
            this.circle_id = circle_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getMer_id() {
            return mer_id;
        }

        public void setMer_id(String mer_id) {
            this.mer_id = mer_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getReply_user() {
            return reply_user;
        }

        public void setReply_user(String reply_user) {
            this.reply_user = reply_user;
        }

        public String getNikcname_user() {
            return nikcname_user;
        }

        public void setNikcname_user(String nikcname_user) {
            this.nikcname_user = nikcname_user;
        }

        public String getNikcname_reply_user() {
            return nikcname_reply_user;
        }

        public void setNikcname_reply_user(String nikcname_reply_user) {
            this.nikcname_reply_user = nikcname_reply_user;
        }
    }
}
