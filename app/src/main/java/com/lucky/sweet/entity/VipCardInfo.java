package com.lucky.sweet.entity;

import java.util.List;

/**
 * Created by C on 2018/2/27.
 */

public class VipCardInfo {

    private List<VipListBean> vip_list;

    public List<VipListBean> getVip_list() {
        return vip_list;
    }

    public void setVip_list(List<VipListBean> vip_list) {
        this.vip_list = vip_list;
    }

    public static class VipListBean {
        /**
         * mer_id : 11
         * name : 啦啦啦
         * photo : sweet/circle/a/20180222051943/0jpg
         * vip_describe : 这是会员卡描述信息
         * phone : 15831456245
         * vip_date_id : 110501
         * integral : 40
         * VIP_name : bb
         */

        private String mer_id;
        private String name;
        private String photo;
        private String vip_describe;
        private String phone;
        private String vip_date_id;
        private String integral;
        private String VIP_name;

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

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getVip_describe() {
            return vip_describe;
        }

        public void setVip_describe(String vip_describe) {
            this.vip_describe = vip_describe;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getVip_date_id() {
            return vip_date_id;
        }

        public void setVip_date_id(String vip_date_id) {
            this.vip_date_id = vip_date_id;
        }

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }

        public String getVIP_name() {
            return VIP_name;
        }

        public void setVIP_name(String VIP_name) {
            this.VIP_name = VIP_name;
        }
    }
}
