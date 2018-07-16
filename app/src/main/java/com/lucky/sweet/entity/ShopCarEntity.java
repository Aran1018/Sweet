package com.lucky.sweet.entity;

import com.lucky.sweet.utility.OssUtils;

import java.util.List;

/**
 * Created by chn on 2018/2/7.
 * (╯°□°）╯︵ ┻━┻ MMP好气啊！
 * (╯‵□′)╯︵┻━┻ 老子怒掀桌子！
 * ┻━┻︵╰(‵□′)╯︵┻━┻老子双手掀桌！
 * ┬─┬﻿ ノ( ゜-゜ノ) 算了，我不生气了！日子还得过老老实实敲吧~
 */

public class ShopCarEntity {

    /**
     * item_list : [{"class_id":"43","name":"热菜","item":[{"item_id":"53","name":"小米粥","photo":"merchant/1151803550@qq.com/item/20180213_110455.jpg","univalence":"3","stick_time":null,"description":"小米粥是以小米作为主要食材熬制而成的粥，口味清淡，清香味，具有简单易制","discount_singe":"0","discount":"1","discount_result":"3"},{"item_id":"60","name":"清蒸鲈鱼","photo":"merchant/1151803550@qq.com/item/20180213_110455.jpg","univalence":"1","stick_time":null,"description":"168","discount_singe":"0","discount":"1","discount_result":"1"},{"item_id":"61","name":"爆炒猪肝","photo":"merchant/1151803550@qq.com/item/20180213_110455.jpg","univalence":"2","stick_time":null,"description":"58","discount_singe":"0","discount":"1","discount_result":"2"},{"item_id":"62","name":"爆炒猪肝","photo":"merchant/1151803550@qq.com/item/20180213_110455.jpg","univalence":"3","stick_time":null,"description":"58","discount_singe":"0","discount":"1","discount_result":"3"},{"item_id":"63","name":"红烧鱼","photo":"merchant/1151803550@qq.com/item/20180213_110455.jpg","univalence":"1.5","stick_time":null,"description":"39","discount_singe":"1","discount":"0.8","discount_result":"1.2"},{"item_id":"111","name":"寿司","photo":"merchant/1151803550@qq.com/item/20180213_110455.jpg","univalence":"180","stick_time":null,"description":"","discount_singe":"0","discount":"1","discount_result":"180"},{"item_id":"113","name":"清蒸西兰花","photo":"merchant/1151803550@qq.com/item/20180213_110455.jpg","univalence":"15","stick_time":null,"description":"","discount_singe":"0","discount":"1","discount_result":"15"}]},{"class_id":"44","name":"饮料","item":[{"item_id":"66","name":"红烧鱼","photo":"merchant/1151803550@qq.com/item/20180213_110455.jpg","univalence":"1","stick_time":null,"description":"美味多汁","discount_singe":"1","discount":"0.5","discount_result":"0.5"},{"item_id":"67","name":"红烧肉","photo":"merchant/1151803550@qq.com/item/20180213_110455.jpg","univalence":"2","stick_time":null,"description":"30","discount_singe":"1","discount":"0.5","discount_result":"1"},{"item_id":"68","name":"红烧排骨","photo":"merchant/1151803550@qq.com/item/20180213_110455.jpg","univalence":"3","stick_time":null,"description":"40","discount_singe":"1","discount":"0.5","discount_result":"1.5"},{"item_id":"70","name":"土豆泥","photo":"merchant/1151803550@qq.com/item/20180213_110455.jpg","univalence":"1","stick_time":null,"description":"2","discount_singe":"1","discount":"0.8","discount_result":"0.8"},{"item_id":"109","name":"海参","photo":"merchant/1151803550@qq.com/item/20180213_110455.jpg","univalence":"80","stick_time":null,"description":"鲜美不油腻","discount_singe":"0","discount":"1","discount_result":"80"},{"item_id":"110","name":"碳烤腰果","photo":"merchant/1151803550@qq.com/item/20180213_110455.jpg","univalence":"20","stick_time":null,"description":"35","discount_singe":"0","discount":"1","discount_result":"20"}]},{"class_id":"48","name":"主食","item":[{"item_id":"72","name":"炭烧腰果","photo":"merchant/1151803550@qq.com/item/20180213_110455.jpg","univalence":"3","stick_time":null,"description":"","discount_singe":"0","discount":"1","discount_result":"3"},{"item_id":"73","name":"红烧土豆","photo":"merchant/1151803550@qq.com/item/20180213_110455.jpg","univalence":"1.5","stick_time":null,"description":"","discount_singe":"0","discount":"1","discount_result":"1.5"}]}]
     * VIP_name : vip1
     * vip_discount : 0.8
     */

    private String VIP_name;
    private String vip_discount;
    private List<ItemListBean> item_list;

    public String getVIP_name() {
        return VIP_name;
    }

    public void setVIP_name(String VIP_name) {
        this.VIP_name = VIP_name;
    }

    public String getVip_discount() {
        return vip_discount;
    }

    public void setVip_discount(String vip_discount) {
        this.vip_discount = vip_discount;
    }

    public List<ItemListBean> getItem_list() {
        return item_list;
    }

    public void setItem_list(List<ItemListBean> item_list) {
        this.item_list = item_list;
    }

    public static class ItemListBean {
        /**
         * class_id : 43
         * name : 热菜
         * item : [{"item_id":"53","name":"小米粥","photo":"merchant/1151803550@qq.com/item/20180213_110455.jpg","univalence":"3","stick_time":null,"description":"小米粥是以小米作为主要食材熬制而成的粥，口味清淡，清香味，具有简单易制","discount_singe":"0","discount":"1","discount_result":"3"},{"item_id":"60","name":"清蒸鲈鱼","photo":"merchant/1151803550@qq.com/item/20180213_110455.jpg","univalence":"1","stick_time":null,"description":"168","discount_singe":"0","discount":"1","discount_result":"1"},{"item_id":"61","name":"爆炒猪肝","photo":"merchant/1151803550@qq.com/item/20180213_110455.jpg","univalence":"2","stick_time":null,"description":"58","discount_singe":"0","discount":"1","discount_result":"2"},{"item_id":"62","name":"爆炒猪肝","photo":"merchant/1151803550@qq.com/item/20180213_110455.jpg","univalence":"3","stick_time":null,"description":"58","discount_singe":"0","discount":"1","discount_result":"3"},{"item_id":"63","name":"红烧鱼","photo":"merchant/1151803550@qq.com/item/20180213_110455.jpg","univalence":"1.5","stick_time":null,"description":"39","discount_singe":"1","discount":"0.8","discount_result":"1.2"},{"item_id":"111","name":"寿司","photo":"merchant/1151803550@qq.com/item/20180213_110455.jpg","univalence":"180","stick_time":null,"description":"","discount_singe":"0","discount":"1","discount_result":"180"},{"item_id":"113","name":"清蒸西兰花","photo":"merchant/1151803550@qq.com/item/20180213_110455.jpg","univalence":"15","stick_time":null,"description":"","discount_singe":"0","discount":"1","discount_result":"15"}]
         */

        private String class_id;
        private String name;
        private List<ItemBean> item;

        public String getClass_id() {
            return class_id;
        }

        public void setClass_id(String class_id) {
            this.class_id = class_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<ItemBean> getItem() {
            return item;
        }

        public void setItem(List<ItemBean> item) {
            this.item = item;
        }

        public static class ItemBean {
            /**
             * item_id : 53
             * name : 小米粥
             * photo : merchant/1151803550@qq.com/item/20180213_110455.jpg
             * univalence : 3
             * stick_time : null
             * description : 小米粥是以小米作为主要食材熬制而成的粥，口味清淡，清香味，具有简单易制
             * discount_singe : 0
             * discount : 1
             * discount_result : 3
             */

            private String item_id;
            private String name;
            private String photo;
            private String univalence;
            private Object stick_time;
            private String description;
            private String discount_singe;
            private String discount;
            private String discount_result;

            public String getItem_id() {
                return item_id;
            }

            public void setItem_id(String item_id) {
                this.item_id = item_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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

            public String getUnivalence() {
                return univalence;
            }

            public void setUnivalence(String univalence) {
                this.univalence = univalence;
            }

            public Object getStick_time() {
                return stick_time;
            }

            public void setStick_time(Object stick_time) {
                this.stick_time = stick_time;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getDiscount_singe() {
                return discount_singe;
            }

            public void setDiscount_singe(String discount_singe) {
                this.discount_singe = discount_singe;
            }

            public String getDiscount() {
                return discount;
            }

            public void setDiscount(String discount) {
                this.discount = discount;
            }

            public String getDiscount_result() {
                return discount_result;
            }

            public void setDiscount_result(String discount_result) {
                this.discount_result = discount_result;
            }
        }
    }
}
