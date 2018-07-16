package com.lucky.sweet.model.wholedishes.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lucky.sweet.R;
import com.lucky.sweet.model.shoppingcar.mode.ShopProduct;
import com.lucky.sweet.model.wholedishes.model.Travel;




public class FragmentBottom extends Fragment {

    static final String ARG_TRAVEL = "ARG_TRAVEL";
    private ShopProduct shopProduct;

    private FragmentBottom(){}

    public static FragmentBottom newInstance(ShopProduct shopProduct) {
        Bundle args = new Bundle();
        FragmentBottom fragmentBottom = new FragmentBottom();
        args.putParcelable(ARG_TRAVEL, shopProduct);
        fragmentBottom.setArguments(args);
        return fragmentBottom;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bottom, container, false);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();

        if (args != null) {
            shopProduct = (ShopProduct) args.get(ARG_TRAVEL);

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        TextView desAll = getView().findViewById(R.id.tv_order_des_all);
        TextView desName = getView().findViewById(R.id.tv_order_des_name);
        TextView cost = getView().findViewById(R.id.tv_order_cost);
        if (shopProduct!=null) {
            desName.setText(shopProduct.getGoods());
            cost.setText(shopProduct.getNumber()+"");
            desAll.setText(shopProduct.getDes());
        }
    }
}
