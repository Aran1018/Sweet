package com.lucky.sweet.entity;

import java.util.List;

/**
 * Create by chn on 2018/4/5.
 * 第一句唱前半生：走马西风长路
 * 第二句唱莫回首：十载飘荡已无亲故
 * 第三句唱这江湖路：晃晃悠悠的一朝一暮
 */
public class UpServiceDesInfo {
    private String merId;
    private String pay;
    private List<Diskesinfo> list;

    public String getMerId() {

        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String  pay) {
        this.pay = pay;
    }

    public List<Diskesinfo> getList() {
        return list;
    }

    public void setList(List<Diskesinfo> list) {
        this.list = list;
    }

    public static class Diskesinfo {
        private String goodsName;
        private int disNum;
        private String pay;

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String  goodsName) {
            this.goodsName = goodsName;
        }

        public int getDisNum() {
            return disNum;
        }

        public void setDisNum(int disNum) {
            this.disNum = disNum;
        }

        public String getPay() {
            return pay;
        }

        public void setPay(String  pay) {
            this.pay = pay;
        }
    }
}
