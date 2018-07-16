package com.lucky.sweet.model.shoppingcar.mode;

import java.util.List;

/**
 * 小区商圈商品类型
 */
public class ProductType {

    private int id;
    private String type;
    private String createtime;
    private List<ShopProduct> product;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public List<ShopProduct> getProduct() {
        return product;
    }

    public void setProduct(List<ShopProduct> product) {
        this.product = product;
    }
}
