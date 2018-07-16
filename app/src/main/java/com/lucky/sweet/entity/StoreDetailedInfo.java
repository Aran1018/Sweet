package com.lucky.sweet.entity;

import java.util.List;

/**
 * Created by chn on 2017/12/19.
 * (╯°□°）╯︵ ┻━┻ MMP好气啊！
 * (╯‵□′)╯︵┻━┻ 老子怒掀桌子！
 * ┻━┻︵╰(‵□′)╯︵┻━┻老子双手掀桌！
 * ┬─┬﻿ ノ( ゜-゜ノ) 算了，我不生气了！日子还得过老老实实敲吧~
 */

public class StoreDetailedInfo {


    /**
     * info : {"surface":"https://thethreestooges.cn/merchant/img/photo_mer/1/surface.jpg","recommend":["https://thethreestooges.cn/merchant/img/photo_mer/1/recommend/1.jpg","https://thethreestooges.cn/merchant/img/photo_mer/1/recommend/2.jpg","https://thethreestooges.cn/merchant/img/photo_mer/1/recommend/3.jpg"],"environment":["https://thethreestooges.cn/merchant/img/photo_mer/1/environment/1.jpg","https://thethreestooges.cn/merchant/img/photo_mer/1/environment/2.jpg","https://thethreestooges.cn/merchant/img/photo_mer/1/environment/3.jpg"],"shopdes":{"name":"老板娘烤肉店","classify":"聚餐宴请 ","introduce":"罗宝宝⊙ω⊙","phone":"15831454246","address":"辽宁省大连市旅顺口区旅顺盐场海鲜街45号（大连医科大学、大连外国语学院）","business_hours":"09:00-20:04","status":"true","reserve":"true","avecon":"50","time":"2","grade":"7","longitude":"121.309904","latitude":"38.812218"}}
     * circle : null
     */

    private InfoBean info;
    private Object circle;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public Object getCircle() {
        return circle;
    }

    public void setCircle(Object circle) {
        this.circle = circle;
    }

    public static class InfoBean {
        /**
         * surface : https://thethreestooges.cn/merchant/img/photo_mer/1/surface.jpg
         * recommend : ["https://thethreestooges.cn/merchant/img/photo_mer/1/recommend/1.jpg","https://thethreestooges.cn/merchant/img/photo_mer/1/recommend/2.jpg","https://thethreestooges.cn/merchant/img/photo_mer/1/recommend/3.jpg"]
         * environment : ["https://thethreestooges.cn/merchant/img/photo_mer/1/environment/1.jpg","https://thethreestooges.cn/merchant/img/photo_mer/1/environment/2.jpg","https://thethreestooges.cn/merchant/img/photo_mer/1/environment/3.jpg"]
         * shopdes : {"name":"老板娘烤肉店","classify":"聚餐宴请 ","introduce":"罗宝宝⊙ω⊙","phone":"15831454246","address":"辽宁省大连市旅顺口区旅顺盐场海鲜街45号（大连医科大学、大连外国语学院）","business_hours":"09:00-20:04","status":"true","reserve":"true","avecon":"50","time":"2","grade":"7","longitude":"121.309904","latitude":"38.812218"}
         */

        private String surface;
        private ShopdesBean shopdes;
        private List<String> recommend;
        private List<String> environment;

        public String getSurface() {
            return surface;
        }

        public void setSurface(String surface) {
            this.surface = surface;
        }

        public ShopdesBean getShopdes() {
            return shopdes;
        }

        public void setShopdes(ShopdesBean shopdes) {
            this.shopdes = shopdes;
        }

        public List<String> getRecommend() {
            return recommend;
        }

        public void setRecommend(List<String> recommend) {
            this.recommend = recommend;
        }

        public List<String> getEnvironment() {
            return environment;
        }

        public void setEnvironment(List<String> environment) {
            this.environment = environment;
        }

        public static class ShopdesBean {
            /**
             * name : 老板娘烤肉店
             * classify : 聚餐宴请
             * introduce : 罗宝宝⊙ω⊙
             * phone : 15831454246
             * address : 辽宁省大连市旅顺口区旅顺盐场海鲜街45号（大连医科大学、大连外国语学院）
             * business_hours : 09:00-20:04
             * status : true
             * reserve : true
             * avecon : 50
             * time : 2
             * grade : 7
             * longitude : 121.309904
             * latitude : 38.812218
             */

            private String name;
            private String classify;
            private String introduce;
            private String phone;
            private String address;
            private String business_hours;
            private String status;
            private String reserve;
            private String avecon;
            private String time;
            private String grade;
            private String longitude;
            private String latitude;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getClassify() {
                return classify;
            }

            public void setClassify(String classify) {
                this.classify = classify;
            }

            public String getIntroduce() {
                return introduce;
            }

            public void setIntroduce(String introduce) {
                this.introduce = introduce;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getBusiness_hours() {
                return business_hours;
            }

            public void setBusiness_hours(String business_hours) {
                this.business_hours = business_hours;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getReserve() {
                return reserve;
            }

            public void setReserve(String reserve) {
                this.reserve = reserve;
            }

            public String getAvecon() {
                return avecon;
            }

            public void setAvecon(String avecon) {
                this.avecon = avecon;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getGrade() {
                return grade;
            }

            public void setGrade(String grade) {
                this.grade = grade;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }
        }
    }
}
