package com.lucky.sweet.model.shoppingcar.mode;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 小区商圈商品属性
 */
public class ShopProduct implements Serializable,Parcelable {
    private int id;
    private String shopName;//店名
    private String price;//单价
    private String goods;//货物名称
    private String picture;//货物图片
    private String type;//货物类型
    private String createTime;

    /**
     * 商品数目
     */
    private int number;

    private String des;


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(shopName);
        dest.writeString(price);
        dest.writeString(goods);
        dest.writeString(picture);
        dest.writeString(type);
        dest.writeString(createTime);
        dest.writeString(des);
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getDes() {
        return des;
    }
    public static final Creator<ShopProduct> CREATOR = new Creator<ShopProduct>() {
        @Override
        public ShopProduct createFromParcel(Parcel in) {
            return new ShopProduct(in);
        }

        @Override
        public ShopProduct[] newArray(int size) {
            return new ShopProduct[size];
        }
    };
    public  ShopProduct(){

    }
    protected ShopProduct(Parcel in) {
        id = in.readInt();
        shopName = in.readString();
        price = in.readString();
        goods = in.readString();
        picture = in.readString();
        type = in.readString();
        des = in.readString();
        createTime = in.readString();
    }
}
