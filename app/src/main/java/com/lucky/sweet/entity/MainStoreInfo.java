package com.lucky.sweet.entity;

import java.util.List;

/**
 * Created by c on 2017/12/10.
 * (╯°□°）╯︵ ┻━┻ MMP好气啊！
 * (╯‵□′)╯︵┻━┻ 老子怒掀桌子！
 * ┻━┻︵╰(‵□′)╯︵┻━┻老子双手掀桌！
 * ┬─┬﻿ ノ( ゜-゜ノ) 算了，我不生气了！日子还得过老老实实敲吧~
 */

public class MainStoreInfo {

    private List<FoodBean> food;
    private List<RecreationBean> recreation;
    private List<AdvertisingBean> advertising;

    public List<FoodBean> getFood() {
        return food;
    }

    public void setFood(List<FoodBean> food) {
        this.food = food;
    }

    public List<RecreationBean> getRecreation() {
        return recreation;
    }

    public void setRecreation(List<RecreationBean> recreation) {
        this.recreation = recreation;
    }

    public List<AdvertisingBean> getAdvertising() {
        return advertising;
    }

    public void setAdvertising(List<AdvertisingBean> advertising) {
        this.advertising = advertising;
    }

    public static class FoodBean {
        /**
         * mer_id : 6
         * name : 动感新天地
         * classify : KTV
         * time : 2
         * grade : 7
         * longitude : 121.309904
         * latitude : 38.812218
         * pho_url : https://thethreestooges.cn/merchant/img/photo_mer/6/surface.png
         * stars : 3
         * distance : 0.96
         */

        private String mer_id;
        private String name;
        private String classify;
        private String time;
        private String grade;
        private String longitude;
        private String latitude;
        private String pho_url;
        private int stars;
        private double distance;

        public String getMer_id() {
            return mer_id;
        }

        public void setMer_id(String mer_id) {
            this.mer_id = mer_id;
        }

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

        public String getPho_url() {
            return pho_url;
        }

        public void setPho_url(String pho_url) {
            this.pho_url = pho_url;
        }

        public int getStars() {
            return stars;
        }

        public void setStars(int stars) {
            this.stars = stars;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }
    }

    public static class RecreationBean {
        /**
         * mer_id : 1
         * name : 老板娘烤肉店
         * classify : 聚餐宴请
         * time : 2
         * grade : 7
         * longitude : 121.309904
         * latitude : 38.812218
         * pho_url : https://thethreestooges.cn/merchant/img/photo_mer/1/surface.jpg
         * stars : 3
         * distance : 0.96
         */

        private String mer_id;
        private String name;
        private String classify;
        private String time;
        private String grade;
        private String longitude;
        private String latitude;
        private String pho_url;
        private int stars;
        private double distance;

        public String getMer_id() {
            return mer_id;
        }

        public void setMer_id(String mer_id) {
            this.mer_id = mer_id;
        }

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

        public String getPho_url() {
            return pho_url;
        }

        public void setPho_url(String pho_url) {
            this.pho_url = pho_url;
        }

        public int getStars() {
            return stars;
        }

        public void setStars(int stars) {
            this.stars = stars;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }
    }

    public static class AdvertisingBean {
        /**
         * url : https://thethreestooges.cn/merchant/img/photo_mer/advertising/ad_one.png
         */

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
