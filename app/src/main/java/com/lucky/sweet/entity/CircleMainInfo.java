package com.lucky.sweet.entity;

import com.lucky.sweet.utility.OssUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chn on 2018/2/20.
 * (╯°□°）╯︵ ┻━┻ MMP好气啊！
 * (╯‵□′)╯︵┻━┻ 老子怒掀桌子！
 * ┻━┻︵╰(‵□′)╯︵┻━┻老子双手掀桌！
 * ┬─┬﻿ ノ( ゜-゜ノ) 算了，我不生气了！日子还得过老老实实敲吧~
 */

public class CircleMainInfo {


    private List<CircleListBean> circle_list;

    public List<CircleListBean> getCircle_list() {
        return circle_list;
    }

    public void setCircle_list(List<CircleListBean> circle_list) {
        this.circle_list = circle_list;
    }

    public static class CircleListBean implements Serializable {
        private boolean transHttpPath = false;

        /**
         * circle_id : 4_7
         * user_id : 4
         * mer_id : 1
         * content : 女孩让我帮她寄快递，给了我一个空纸箱让我打包，我好奇的问她：“这是寄给谁的”她说：“我喜欢很久的一个男生”我懵了一下：“可是里面没有东西啊”她说：有些东西只有我自己能看见。我一听更懵逼了，问她到底是什么，她说：“一箱情愿”。
         * create_time : 2018-02-13 14:50:07
         * like_num : 0
         * nikcname : 大外只有一个浩南
         * exp : 200
         * relation_sign : 2
         * mer_name : 老板娘烤肉店
         * photo_url : ["sweet/circle/chinn96@163.com/20180213143508/0.png","sweet/circle/chinn96@163.com/20180213143508/0.png"]
         * comment_num : 0
         */

        private String circle_id;
        private String user_id;
        private String mer_id;
        private String content;
        private String create_time;
        private String like_num;
        private String nikcname;
        private String exp;
        private String relation_sign;
        private String mer_name;
        private int comment_num;
        private List<String> photo_url;

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

        public String getLike_num() {
            return like_num;
        }

        public void setLike_num(String like_num) {
            this.like_num = like_num;
        }

        public String getNikcname() {
            return nikcname;
        }

        public void setNikcname(String nikcname) {
            this.nikcname = nikcname;
        }

        public String getExp() {
            return exp;
        }

        public void setExp(String exp) {
            this.exp = exp;
        }

        public String getRelation_sign() {
            return relation_sign;
        }

        public void setRelation_sign(String relation_sign) {
            this.relation_sign = relation_sign;
        }

        public String getMer_name() {
            return mer_name;
        }

        public void setMer_name(String mer_name) {
            this.mer_name = mer_name;
        }

        public int getComment_num() {
            return comment_num;
        }

        public void setComment_num(int comment_num) {
            this.comment_num = comment_num;
        }


        public List<String> getPhoto_url() {

            if (!transHttpPath) {
                transHttpPath = true;
                for (int i = 0; i < photo_url.size(); i++) {
                    String path = photo_url.get(i);
                    if (!path.contains("http")) {
                        photo_url.set(i, OssUtils.getOSSExtranetPath(path));
                    }
                }
            }
            return photo_url;
        }

        public void setPhoto_url(List<String> photo_url) {
            this.photo_url = photo_url;
        }
    }
}
