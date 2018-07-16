package com.lucky.sweet.model.wholedishes.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.lucky.sweet.model.shoppingcar.mode.ShopProduct;
import com.lucky.sweet.model.wholedishes.lib.fragments.ExpandingFragment;


/**
 * this is control fragment , Top and Bottom is child in it.
 *
 * Created by florentchampigny on 21/06/2016.
 */
public class TravelExpandingFragment extends ExpandingFragment {

    static final String ARG_TRAVEL = "ARG_TRAVEL";
    ShopProduct product;
    public TravelExpandingFragment(){

    }
    public static TravelExpandingFragment newInstance(ShopProduct product){
        TravelExpandingFragment fragment = new TravelExpandingFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_TRAVEL, product);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args != null) {
            product = args.getParcelable(ARG_TRAVEL);
        }
    }

    /**
     * include TopFragment
     * @return
     */
    @Override
    public Fragment getFragmentTop() {
        return FragmentTop.newInstance(product.getPicture());
    }

    /**
     * include BottomFragment
     * @return
     */
    @Override
    public Fragment getFragmentBottom() {
        return FragmentBottom.newInstance(product);
    }
}
