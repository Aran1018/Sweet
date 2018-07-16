package com.lucky.sweet.entity;

import com.lucky.sweet.activity.MyApplication;
import com.lucky.sweet.utility.OssUtils;

import java.util.List;

public class PersonCollectInfo {
    private List<CollectListBean> collect_list;

    public List<CollectListBean> getCollect_list() {
        return collect_list;
    }

    public void setCollect_list(List<CollectListBean> collect_list) {
        this.collect_list = collect_list;
    }

    public static class CollectListBean {
        /**
         * mer_id : 1
         * name : 这是一号店铺
         * photo : sweet/circle/a/20180222051943/0jpg
         */

        private String mer_id;
        private String name;
        private String photo;

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
            if (photo!=null) {
                if (!photo.contains("http"))
                    photo = OssUtils.getOSSExtranetPath(photo);
            }
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }
    }
}
