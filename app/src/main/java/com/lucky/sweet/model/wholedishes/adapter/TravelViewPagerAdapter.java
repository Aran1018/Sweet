package com.lucky.sweet.model.wholedishes.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.lucky.sweet.model.shoppingcar.mode.ShopProduct;
import com.lucky.sweet.model.wholedishes.fragments.TravelExpandingFragment;
import com.lucky.sweet.model.wholedishes.lib.ExpandingViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Qs on 16/5/30.
 */
public class TravelViewPagerAdapter extends ExpandingViewPagerAdapter {

    List<ShopProduct> shopProductList;

    public TravelViewPagerAdapter(FragmentManager fm, ArrayList<ShopProduct> data) {
        super(fm);
        shopProductList = new ArrayList<>();
        addAll(data);
    }

    private void addAll(List<ShopProduct> travels) {
        this.shopProductList.addAll(travels);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        ShopProduct ShopProduct = shopProductList.get(position);
        return TravelExpandingFragment.newInstance(ShopProduct);
    }

    @Override
    public int getCount() {
        return shopProductList.size();
    }

}
