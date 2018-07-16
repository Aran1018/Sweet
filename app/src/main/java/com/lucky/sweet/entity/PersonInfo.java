package com.lucky.sweet.entity;

import com.lucky.sweet.utility.OssUtils;

/**
 * Created by C on 2018/3/17.
 */

public class PersonInfo {
    /**
     * nickname : 请加我万能小黑
     * exp : 500
     * photo : null
     * attention_to_me_num : 3
     * me_to_attention_num : 3
     * collect : 0
     * comment_num : 3
     * vip_card_num : 2
     * indent_num : 21
     */

    private String nickname;
    private String exp;
    private String photo;
    private String attention_to_me_num;
    private String me_to_attention_num;
    private String collect;
    private String comment_num;
    private String vip_card_num;
    private String indent_num;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
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

    public String getAttention_to_me_num() {
        return attention_to_me_num;
    }

    public void setAttention_to_me_num(String attention_to_me_num) {
        this.attention_to_me_num = attention_to_me_num;
    }

    public String getMe_to_attention_num() {
        return me_to_attention_num;
    }

    public void setMe_to_attention_num(String me_to_attention_num) {
        this.me_to_attention_num = me_to_attention_num;
    }

    public String getCollect() {
        return collect;
    }

    public void setCollect(String collect) {
        this.collect = collect;
    }

    public String getComment_num() {
        return comment_num;
    }

    public void setComment_num(String comment_num) {
        this.comment_num = comment_num;
    }

    public String getVip_card_num() {
        return vip_card_num;
    }

    public void setVip_card_num(String vip_card_num) {
        this.vip_card_num = vip_card_num;
    }

    public String getIndent_num() {
        return indent_num;
    }

    public void setIndent_num(String indent_num) {
        this.indent_num = indent_num;
    }
}
