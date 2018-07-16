package com.lucky.sweet.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.lucky.sweet.R;
import com.lucky.sweet.entity.DeletRoomInfo;
import com.lucky.sweet.entity.JoinRoomInfo;
import com.lucky.sweet.entity.MuliiOrderInfo;
import com.lucky.sweet.entity.ShopCarEntity;
import com.lucky.sweet.entity.ShopCarSingleInformation;
import com.lucky.sweet.model.shoppingcar.fragment.ProductsFragment;
import com.lucky.sweet.model.shoppingcar.mode.ProductType;
import com.lucky.sweet.model.shoppingcar.mode.ShopProduct;
import com.lucky.sweet.service.CommunicationService;
import com.lucky.sweet.utility.TimeUtil;
import com.lucky.sweet.views.MyToast;
import com.lucky.sweet.widgets.ToolBar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.jar.Attributes;

/**
 * Created by Qiuyue on 2018/1/3.
 */

// ╭︿︿︿╮  ╭︿︿︿╮  ╭︿︿︿╮
// {/ o o /} {/ . . /} {/ ︿︿ /}
// ( (oo) )  ( (oo) )  ( (oo) )
//   ︶︶︶     ︶︶︶     ︶︶︶

public class MerchantActivity extends BaseActivity {


    private ProductsFragment fg_shop_car;
    private Button btn_back;
    private CommunicationService.MyBinder myBinder;
    private static Boolean isSingleOrder = true;
    private String room_id = "";
    private String mer_id = "1";
    private final static String SHOPNAME = "SHOPNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant);
        setIsBindEventBus();

        initToolBar();

        initView();

        initEvent();

    }

    public static void newSingleOrderInStance(Activity activity, String mer_id, String shopName) {
        Intent intent = new Intent(activity, MerchantActivity.class);
        intent.putExtra("mer_id", mer_id);
        intent.putExtra(SHOPNAME, shopName);
        activity.startActivity(intent);
    }

    public static void newMoreOrderInStance(Activity activity, String mer_id, String room_id, String shopName) {
        isSingleOrder = false;
        Intent intent = new Intent(activity, MerchantActivity.class);
        intent.putExtra("mer_id", mer_id);
        intent.putExtra("room_id", room_id);
        intent.putExtra(SHOPNAME, shopName);
        activity.startActivity(intent);
    }


    @Override
    void onServiceBind(CommunicationService.MyBinder myBinder) {
        mer_id = getIntent().getStringExtra("mer_id");
        if (this.myBinder == null) {
            if (isSingleOrder) {
                myBinder.shopCarRequest(mer_id);
            } else {
                room_id = getIntent().getStringExtra("room_id");
                myBinder.shopMultCarRequest(room_id, new JoinRoomInfo(MyApplication.USER_ID));
            }
        }

        this.myBinder = myBinder;
    }


    private void initEvent() {
        fg_shop_car.setOnClickListener(new ProductsFragment.OnClickListener() {
            @Override
            public void onClickSubimt(ShopCarSingleInformation shopCarSingleInformation) {
                if (!isSingleOrder) {
                    myBinder.closeOrder(new DeletRoomInfo(MyApplication.USER_ID, TimeUtil.getCurrentTime()), room_id);

                }
                shopCarSingleInformation.setMer_id(mer_id);
                shopCarSingleInformation.setShopName(getIntent().getStringExtra(SHOPNAME));
                OrderSeatActivity.newInstance(MerchantActivity.this, shopCarSingleInformation);
            }

            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(MerchantActivity.this,
                        DischesViewShowActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("data", shopProduct);
                startActivity(intent);
                overridePendingTransition(R.anim.act_left_in, R.anim.act_left_out);
            }
        });
        btn_back.setOnClickListener(view -> {
            finish();
            goPreAnim();
        });

    }

    private void initToolBar() {
        ToolBar toolBar = new ToolBar(this);
        toolBar.setStatusBarDarkMode();

    }

    private void initView() {

        fg_shop_car = (ProductsFragment) getSupportFragmentManager().findFragmentById(R.id.fg_shop_car);
        fg_shop_car.setCurrenType(!isSingleOrder);
        btn_back = findViewById(R.id.btn_back);
    }

    private ArrayList<ShopProduct> shopProduct = new ArrayList();

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(ShopCarEntity entity) {

        ArrayList<ProductType> productCategorizes = new ArrayList<>();
        for (ShopCarEntity.ItemListBean entiy : entity.getItem_list()) {
            ProductType productCategorize = new ProductType();
            productCategorize.setType(entiy.getName());
            ArrayList shopProductsAll = new ArrayList<>();

            for (ShopCarEntity.ItemListBean.ItemBean item : entiy.getItem()) {
                ShopProduct product = new ShopProduct();
                product.setId(Integer.parseInt(item.getItem_id()));
                product.setGoods(item.getName());
                product.setPrice(item.getUnivalence());
                product.setPicture(item.getPhoto());
                product.setDes(item.getDescription());
                shopProductsAll.add(product);
                shopProduct.add(product);
            }
            productCategorize.setProduct(shopProductsAll);
            productCategorizes.add(productCategorize);

        }

        fg_shop_car.initData(productCategorizes);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(JoinRoomInfo joinRoomInfo) {

        String name = joinRoomInfo.getName();
        if (!name.equals(MyApplication.USER_ID))
            MyToast.showShort("感谢这位老铁：" + name + "加入房间");

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MuliiOrderInfo info) {

        fg_shop_car.multiOrderUpdata(info);

    }

    public void upMenuInfo(MuliiOrderInfo muliiOrderInfo) {

        String num;
        if (muliiOrderInfo.isaddDis()) {
            num = "1";
        } else {
            num = "-1";
        }

        myBinder.dishesMenuUpdata(getIntent().getStringExtra("room_id"), muliiOrderInfo.getItem_id() + "", num, muliiOrderInfo.toString());

    }
}
