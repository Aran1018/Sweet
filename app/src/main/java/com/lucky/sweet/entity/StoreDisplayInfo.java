package com.lucky.sweet.entity;

import java.util.List;

/**
 * Created by chn on 2018/1/29.
 * (╯°□°）╯︵ ┻━┻ MMP好气啊！
 * (╯‵□′)╯︵┻━┻ 老子怒掀桌子！
 * ┻━┻︵╰(‵□′)╯︵┻━┻老子双手掀桌！
 * ┬─┬﻿ ノ( ゜-゜ノ) 算了，我不生气了！日子还得过老老实实敲吧~
 */

public class StoreDisplayInfo {


    private List<MerListBean> mer_list;

    public List<MerListBean> getMer_list() {
        return mer_list;
    }

    public void setMer_list(List<MerListBean> mer_list) {
        this.mer_list = mer_list;
    }

    public static class MerListBean {
        /**
         * mer_id : 5
         * mer_address : null
         * name : 老板
         * environment : ["https://thethreestooges.cn/123123.jpg","https://thethreestooges.cn/123123.jpg","https://thethreestooges.cn/123123.jpg"]
         * surface : https://thethreestooges.cn/merchant/img/photo_mer/5/surface.png
         */

        private String mer_id;
        private Object mer_address;
        private String name;
        private String surface;
        private List<String> environment;

        public String getMer_id() {
            return mer_id;
        }

        public void setMer_id(String mer_id) {
            this.mer_id = mer_id;
        }

        public Object getMer_address() {
            return mer_address;
        }

        public void setMer_address(Object mer_address) {
            this.mer_address = mer_address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSurface() {
            return surface;
        }

        public void setSurface(String surface) {
            this.surface = surface;
        }

        public List<String> getEnvironment() {
            return environment;
        }

        public void setEnvironment(List<String> environment) {
            this.environment = environment;
        }
    }
}
