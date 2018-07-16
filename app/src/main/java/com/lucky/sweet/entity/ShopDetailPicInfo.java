package com.lucky.sweet.entity;

import com.lucky.sweet.utility.OssUtils;

import java.util.ArrayList;
import java.util.List;


public class ShopDetailPicInfo {

    private List<String> mer_photo;
    private Boolean tranFlag = true;

    public List<String> getMer_photo() {
        if (tranFlag) {
            tranFlag = false;
            ArrayList<String> objects = new ArrayList<>();
            for (String s : mer_photo) {
                if (!s.contains("http")) {
                    objects.add(OssUtils.getOSSExtranetPath(s));
                }
            }
            mer_photo = objects;
        }

        return mer_photo;
    }

    public void setMer_photo(List<String> mer_photo) {
        this.mer_photo = mer_photo;
    }
}
