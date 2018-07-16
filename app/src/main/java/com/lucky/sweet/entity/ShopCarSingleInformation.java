package com.lucky.sweet.entity;

import com.lucky.sweet.model.shoppingcar.mode.ShopProduct;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chn on 2018/1/9.
 * (╯°□°）╯︵ ┻━┻ MMP好气啊！
 * (╯‵□′)╯︵┻━┻ 老子怒掀桌子！
 * ┻━┻︵╰(‵□′)╯︵┻━┻老子双手掀桌！
 * ┬─┬﻿ ノ( ゜-゜ノ) 算了，我不生气了！日子还得过老老实实敲吧~
 */

public class ShopCarSingleInformation implements Serializable {
    private String mer_id;

    public String getMer_id() {
        return mer_id;
    }

    public void setMer_id(String mer_id) {
        this.mer_id = mer_id;
    }

    private String saleSum;
    private String shopName;
    private String seatNum;
    private List<ShopProduct> productList;

    public List<ShopProduct> getProductList() {
        return productList;
    }

    public void setProductList(List<ShopProduct> productList) {
        this.productList = productList;
    }

    public ShopCarSingleInformation(String saleSum, String shopName, String seatNum, List<ShopProduct> productList) {
        this.saleSum = saleSum;
        this.shopName = shopName;
        this.seatNum = seatNum;
        this.productList = productList;
    }

    public String getSaleSum() {
        return saleSum;
    }

    public void setSaleSum(String saleSum) {
        this.saleSum = saleSum;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(String seatNum) {
        this.seatNum = seatNum;
    }
}
