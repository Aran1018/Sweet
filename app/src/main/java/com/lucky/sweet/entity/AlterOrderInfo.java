package com.lucky.sweet.entity;

import com.lucky.sweet.utility.OssUtils;

import java.util.List;

/**
 * Created by C on 2018/3/17.
 */

public class AlterOrderInfo {
    private List<UnfinishIndentListBean> unfinish_indent_list;

    public List<UnfinishIndentListBean> getUnfinish_indent_list() {
        return unfinish_indent_list;
    }

    public void setUnfinish_indent_list(List<UnfinishIndentListBean> unfinish_indent_list) {
        this.unfinish_indent_list = unfinish_indent_list;
    }

    public static class UnfinishIndentListBean {
        /**
         * indent_id : 10501
         * create_time : 2018-03-11 15:22:25
         * mer_id : 1
         * type_sign : 0
         * money :
         * mer_name : 老板娘烤肉店
         * photo :
         */

        private String indent_id;
        private String create_time;
        private String mer_id;
        private String type_sign;
        private String money;
        private String mer_name;
        private String photo;

        public String getIndent_id() {
            return indent_id;
        }

        public void setIndent_id(String indent_id) {
            this.indent_id = indent_id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getMer_id() {
            return mer_id;
        }

        public void setMer_id(String mer_id) {
            this.mer_id = mer_id;
        }

        public String getType_sign() {
            return type_sign;
        }

        public void setType_sign(String type_sign) {
            this.type_sign = type_sign;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getMer_name() {
            return mer_name;
        }

        public void setMer_name(String mer_name) {
            this.mer_name = mer_name;
        }

        public String getPhoto() {

            if (photo == null)
                return photo;
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
