package com.lucky.sweet.model.shoppingcar.assistant;


import com.lucky.sweet.model.shoppingcar.mode.ShopProduct;

/**
 * 购物车添加接口回调
 */
public interface ShopToDetailListener {
    /**
     * Type表示添加或减少
     * @param product
     * @param type
     */
    void onUpdateDetailList(ShopProduct product, String type);

    void onRemovePriduct(ShopProduct product);
}
