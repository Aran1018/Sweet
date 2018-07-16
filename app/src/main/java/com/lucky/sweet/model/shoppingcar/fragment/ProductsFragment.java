package com.lucky.sweet.model.shoppingcar.fragment;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lucky.sweet.R;
import com.lucky.sweet.activity.MerchantActivity;
import com.lucky.sweet.activity.MyApplication;
import com.lucky.sweet.entity.MuliiOrderInfo;
import com.lucky.sweet.entity.ShopCarSingleInformation;
import com.lucky.sweet.model.shoppingcar.DoubleUtil;
import com.lucky.sweet.model.shoppingcar.ShopMenuAttr;
import com.lucky.sweet.model.shoppingcar.adapter.ShopAdapter;
import com.lucky.sweet.model.shoppingcar.adapter.TestSectionedAdapter;
import com.lucky.sweet.model.shoppingcar.assistant.ShopToDetailListener;
import com.lucky.sweet.model.shoppingcar.assistant.onCallBackListener;
import com.lucky.sweet.model.shoppingcar.mode.ProductType;
import com.lucky.sweet.model.shoppingcar.mode.ShopProduct;
import com.lucky.sweet.model.shoppingcar.view.PinnedHeaderListView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 服务商品列表
 */
public class ProductsFragment extends Fragment implements View.OnClickListener, onCallBackListener, ShopToDetailListener {
    private boolean isScroll = true;
    private ListView mainlist;
    private PinnedHeaderListView morelist;
    private TestSectionedAdapter sectionedAdapter;
    /**
     * 保存购物车对象到List
     * TODO:考虑保存购物车缓存
     */
    private List<ShopProduct> productList;
    /**
     * 购物车价格
     */
    private TextView shoppingPrise;
    /**
     * 购物车件数
     */
    private TextView shoppingNum;
    /**
     * 去结算
     */
    private TextView settlement;
    /**
     * 购物车View
     */
    private FrameLayout cardLayout;

    private LinearLayout cardShopLayout;
    /**
     * 背景View
     */
    private View bg_layout;
    /**
     * 购物车Logo
     */
    private ImageView shopping_cart;
    // 动画时间
    private int number = 0;
    // 是否完成清理
    private boolean isClean = false;
    private FrameLayout animation_viewGroup;

    private TextView defaultText;

    private List<String> strings;

    //父布局
    private RelativeLayout parentLayout;

    private TextView noData;
    /**
     * 确认按键
     */
    private TextView settlement1;

    /**
     * 分类列表
     */
    private List<ProductType> productCategorizes;

    private List<ShopProduct> shopProductsAll;

    private ListView shoppingListView;

    private ShopAdapter shopAdapter;


    private Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    // 用来清除动画后留下的垃圾
                    try {
                        animation_viewGroup.removeAllViews();
                    } catch (Exception e) {

                    }
                    isClean = false;

                    break;
                default:
                    break;
            }
        }
    };
    private Button btn_back;
    private TextView tv_shopcar_int;
    private boolean currenType;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_classify, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

    }

    public void initData(ArrayList<ProductType> data) {
        productCategorizes = data;

        productList = new ArrayList<>();
        strings = new ArrayList<>();
        sectionedAdapter = new TestSectionedAdapter(getActivity(), productCategorizes);
        sectionedAdapter.setOnItemDesClickListener((section, position) -> {
            if (onClickListener != null) {
                onClickListener.onItemClick(calculateListPositon(section,
                        position, (ArrayList<ProductType>) productCategorizes));
            }
        });
        if (currenType)
            sectionedAdapter.setOnMenuChangedListener((id, section, position, add) -> {
                FragmentActivity activity = getActivity();
                if (activity instanceof MerchantActivity) {
                    System.out.println(id + "id" + section + "section" + position + "position" + add + "add");
                    MuliiOrderInfo muliiOrderInfo = new MuliiOrderInfo();
                    muliiOrderInfo.setSection(section);
                    muliiOrderInfo.setPosition(position);
                    muliiOrderInfo.setName(MyApplication.USER_ID);
                    muliiOrderInfo.setType(MuliiOrderInfo.UPDATA);
                    muliiOrderInfo.setItem_id(id);
                    if (add.equals(ShopMenuAttr.ADD)) {
                        muliiOrderInfo.setaddDis(true);
                    } else {
                        muliiOrderInfo.setaddDis(false);
                    }

                     ((MerchantActivity) activity).upMenuInfo(muliiOrderInfo);
                }
            });
        sectionedAdapter.SetOnSetHolderClickListener((drawable, start_location) -> doAnim(drawable, start_location));

        for (ProductType type : productCategorizes) {
            strings.add(type.getType());
        }

        morelist.setAdapter(sectionedAdapter);

        sectionedAdapter.setCallBackListener(this);
        mainlist.setAdapter(new ArrayAdapter<String>(getActivity(),
                R.layout.categorize_item, strings));

        shopAdapter = new ShopAdapter(getActivity(), productList);
        shoppingListView.setAdapter(shopAdapter);
        shopAdapter.setShopToDetailListener(this);

        mainlist.setOnItemClickListener((arg0, view, position, arg3) -> {
            isScroll = false;

            for (int i = 0; i < mainlist.getChildCount(); i++) {
                if (i == position) {
                    mainlist.getChildAt(i).setBackgroundColor(
                            Color.rgb(255, 255, 255));
                } else {
                    mainlist.getChildAt(i).setBackgroundColor(
                            Color.TRANSPARENT);
                }
            }

            int rightSection = 0;
            for (int i = 0; i < position; i++) {
                rightSection += sectionedAdapter.getCountForSection(i) + 1;
            }
            morelist.setSelection(rightSection);

        });

        morelist.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView arg0, int arg1) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if (isScroll) {
                    for (int i = 0; i < mainlist.getChildCount(); i++) {

                        if (i == sectionedAdapter
                                .getSectionForPosition(firstVisibleItem)) {
                            mainlist.getChildAt(i).setBackgroundColor(
                                    Color.rgb(255, 255, 255));
                        } else {
                            mainlist.getChildAt(i).setBackgroundColor(
                                    Color.TRANSPARENT);

                        }
                    }

                } else {
                    isScroll = true;
                }
            }
        });

        btn_back.setOnClickListener(view -> {
            getActivity().finish();
            getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        });

        bg_layout.setOnClickListener(this);
//        settlement.setOnClickListener(this);
        shopping_cart.setOnClickListener(this);
        settlement1.setOnClickListener(this);

    }

    private void initView() {

        animation_viewGroup = createAnimLayout();
        noData = getView().findViewById(R.id.noData);
        tv_shopcar_int = getView().findViewById(R.id.tv_shopcar_int);
        settlement1 = getView().findViewById(R.id.settlement1);
        parentLayout = getView().findViewById(R.id.parentLayout);
        shoppingPrise = getView().findViewById(R.id.shoppingPrise);
        shoppingNum = getView().findViewById(R.id.shoppingNum);

        mainlist = getView().findViewById(R.id.classify_mainlist);
        morelist = getView().findViewById(R.id.classify_morelist);
        shopping_cart = getView().findViewById(R.id.shopping_cart);
        defaultText = getView().findViewById(R.id.defaultText);

        shoppingListView = getView().findViewById(R.id.shopproductListView);
        cardLayout = getView().findViewById(R.id.cardLayout);
        cardShopLayout = getView().findViewById(R.id.cardShopLayout);
        bg_layout = getView().findViewById(R.id.bg_layout);
        btn_back = getView().findViewById(R.id.btn_back);

    }

    private static int calculateListPositon(int section, int position, ArrayList<ProductType> data) {

        if (section == 0)
            return position;

        int count = 0;
        for (int i = 0; i < section; i++) {
            if (i == section) {
                for (int a = 0; a < position; a++) {
                    count++;
                }
                continue;
            }
            List<ShopProduct> product = data.get(i).getProduct();
            for (int a = 0; a < product.size(); a++) {
                count++;
            }

        }

        return count;
    }

    public final String ADD = "1";
    public final String DELETE = "2";

    public void multiOrderUpdata(MuliiOrderInfo info) {


        String name = info.getName();
        if (!name.equals(MyApplication.USER_ID)) {
            ShopProduct shopProduct = productCategorizes.get(info.getSection()).getProduct().get(info
                    .getPosition());

            int number = shopProduct.getNumber();
            if (info.isaddDis()) {
                number = number + 1;
                Toast.makeText(getActivity(), "感谢这位老铁：" + name + "添加了一份" + shopProduct.getGoods(), Toast.LENGTH_SHORT).show();

                updateProduct(shopProduct, ADD);
            } else if (number != 0) {
                number = number - 1;
                Toast.makeText(getActivity(), "感谢这位老铁：" + name + "取消了一份" + shopProduct.getGoods(), Toast.LENGTH_SHORT).show();

                updateProduct(shopProduct, DELETE);

            }

            shopProduct.setNumber(number);
            productCategorizes.get(info.getSection()).getProduct().set(info.getPosition(), shopProduct);
            sectionedAdapter.notifyDataSetChanged();
            morelist.notify();

        }


    }

    /**
     * 回调函数更新购物车和价格显示状态
     *
     * @param product
     * @param type
     */
    @Override
    public void updateProduct(ShopProduct product, String type) {
        switch (type) {
            case ADD:
                if (!productList.contains(product)) {
                    product.setNumber(1);
                    productList.add(product);
                } else {
                    for (ShopProduct shopProduct : productList) {
                        if (product.getId() == shopProduct.getId()) {
                            shopProduct.setNumber(shopProduct.getNumber());
                        }
                    }
                }
                break;
            case DELETE:
                if (productList.contains(product)) {
                    if (product.getNumber() == 0) {
                        productList.remove(product);
                    } else {
                        for (ShopProduct shopProduct : productList) {
                            if (product.getId() == shopProduct.getId()) {
                                shopProduct.setNumber(shopProduct.getNumber());
                            }
                        }
                    }

                }
                break;
        }

        shopAdapter.notifyDataSetChanged();
        setPrise();
    }

    /**
     * type     1：加菜 2：减菜
     *
     * @param product
     * @param type
     */
    @Override
    public void onUpdateDetailList(ShopProduct product, String type) {
        switch (type) {
            case ADD:

                for (int i = 0; i < productCategorizes.size(); i++) {
                    shopProductsAll = productCategorizes.get(i).getProduct();
                    for (ShopProduct shopProduct : shopProductsAll) {
                        if (product.getId() == shopProduct.getId()) {
                            shopProduct.setNumber(product.getNumber());
                        }
                    }
                }
                break;
            case DELETE:

                for (int i = 0; i < productCategorizes.size(); i++) {
                    shopProductsAll = productCategorizes.get(i).getProduct();
                    for (ShopProduct shopProduct : shopProductsAll) {
                        if (product.getId() == shopProduct.getId()) {
                            shopProduct.setNumber(product.getNumber());
                        }
                    }
                }
                break;
        }

        sectionedAdapter.notifyDataSetChanged();
        setPrise();
    }

    @Override
    public void onRemovePriduct(ShopProduct product) {
        for (int i = 0; i < productCategorizes.size(); i++) {
            shopProductsAll = productCategorizes.get(i).getProduct();
            for (ShopProduct shopProduct : shopProductsAll) {
                if (product.getId() == shopProduct.getId()) {
                    productList.remove(product);
                    shopAdapter.notifyDataSetChanged();
                    shopProduct.setNumber(shopProduct.getNumber());
                }
            }
        }
        sectionedAdapter.notifyDataSetChanged();
        shopAdapter.notifyDataSetChanged();
        setPrise();
    }


    /**
     * 更新购物车价格
     */
    public void setPrise() {
        float sum = 0;
        int shopNum = 0;
        for (ShopProduct pro : productList) {
            sum = (float) DoubleUtil.sum(sum, DoubleUtil.mul((double) pro.getNumber(), Double.parseDouble(pro.getPrice())));
            shopNum = shopNum + pro.getNumber();
        }

        if (shopNum != 0) {
            if (tv_shopcar_int.getVisibility() == View.GONE) {
                tv_shopcar_int.setVisibility(View.VISIBLE);
            }
            tv_shopcar_int.setText(shopNum + "");
        } else {
            if (tv_shopcar_int.getVisibility() == View.VISIBLE) {
                tv_shopcar_int.setVisibility(View.GONE);
            }
        }

        if (shopNum > 0) {
            shoppingNum.setVisibility(View.VISIBLE);
        } else {
            shoppingNum.setVisibility(View.GONE);
        }
        if (sum > 0) {
            shoppingPrise.setVisibility(View.VISIBLE);
        } else {
            shoppingPrise.setVisibility(View.GONE);
        }
        shoppingPrise.setText("¥" + " " + (new DecimalFormat("0.00")).format(sum));
        shoppingNum.setText(String.valueOf(shopNum));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shopping_cart:
                if (productList.isEmpty() || productList == null) {
                    defaultText.setVisibility(View.VISIBLE);
                } else {
                    defaultText.setVisibility(View.GONE);
                }

                if (cardLayout.getVisibility() == View.GONE) {
                    cardLayout.setVisibility(View.VISIBLE);

                    // 加载动画
                    Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.push_bottom_in);
                    // 动画开始
                    cardShopLayout.setVisibility(View.VISIBLE);
                    cardShopLayout.startAnimation(animation);
                    bg_layout.setVisibility(View.VISIBLE);

                } else {
                    cardLayout.setVisibility(View.GONE);
                    bg_layout.setVisibility(View.GONE);
                    cardShopLayout.setVisibility(View.GONE);
                }
                break;

            case R.id.bg_layout:
                cardLayout.setVisibility(View.GONE);
                bg_layout.setVisibility(View.GONE);
                cardShopLayout.setVisibility(View.GONE);
                break;
            case R.id.settlement1:
                if (onClickListener != null) {
                    if (shoppingNum.getVisibility() == View.GONE) {
                        Toast.makeText(getActivity(), "请添加菜品", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    onClickListener.onClickSubimt(new ShopCarSingleInformation(shoppingPrise.getText()
                            .toString(), "aaaaa", "1", productList));
                }
                break;
        }
    }


    /**
     * @param
     * @return void
     * @throws
     * @Description: 创建动画层
     */
    private FrameLayout createAnimLayout() {
        ViewGroup rootView = (ViewGroup) getActivity().getWindow().getDecorView();
        FrameLayout animLayout = new FrameLayout(getActivity());
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;

    }

    private void doAnim(Drawable drawable, int[] start_location) {
        if (!isClean) {
            setAnim(drawable, start_location);
        } else {
            try {
                animation_viewGroup.removeAllViews();
                isClean = false;
                setAnim(drawable, start_location);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                isClean = true;
            }
        }
    }

    /**
     * 动画效果设置
     *
     * @param drawable       将要加入购物车的商品
     * @param start_location 起始位置
     */
    @SuppressLint("NewApi")
    private void setAnim(Drawable drawable, int[] start_location) {
        Animation mScaleAnimation = new ScaleAnimation(1.2f, 0.6f, 1.2f, 0.6f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mScaleAnimation.setFillAfter(true);

        final ImageView iview = new ImageView(getActivity());
        iview.setImageDrawable(drawable);
        final View view = addViewToAnimLayout(animation_viewGroup, iview,
                start_location);


        view.setAlpha(0.6f);

        int[] end_location = new int[2];
        shopping_cart.getLocationInWindow(end_location);

        // 计算位移
        int endX = end_location[0] - start_location[0];// 动画位移的X坐标
        int endY = end_location[1] - start_location[1];// 动画位移的y坐标
        TranslateAnimation translateAnimationX = new TranslateAnimation(0,
                endX, 0, 0);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0,
                0, endY);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);


        Animation mRotateAnimation = new RotateAnimation(0, 180,
                Animation.RELATIVE_TO_SELF, 0.3f, Animation.RELATIVE_TO_SELF,
                0.3f);
        mRotateAnimation.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(mRotateAnimation);
        set.addAnimation(mScaleAnimation);
        set.addAnimation(translateAnimationY);
        set.addAnimation(translateAnimationX);
        set.setDuration(500);// 动画的执行时间
        view.startAnimation(set);
        set.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                number++;
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                number--;
                if (number == 0) {
                    isClean = true;
                    myHandler.sendEmptyMessage(0);
                }

                ObjectAnimator.ofFloat(shopping_cart, "translationY", 0, 4, -2, 0).setDuration(400).start();
                ObjectAnimator.ofFloat(shoppingNum, "translationY", 0, 4, -2, 0).setDuration(400).start();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

        });

    }

    /**
     * @param vg       动画运行的层 这里是frameLayout
     * @param view     要运行动画的View
     * @param location 动画的起始位置
     * @return
     * @deprecated 将要执行动画的view 添加到动画层
     */
    private View addViewToAnimLayout(ViewGroup vg, View view, int[] location) {
        int x = location[0];
        int y = location[1];
        vg.addView(view);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setPadding(5, 5, 5, 5);
        view.setLayoutParams(lp);

        return view;
    }

    /**
     * 内存过低时及时处理动画产生的未处理冗余
     */
    @Override
    public void onLowMemory() {
        isClean = true;
        try {
            animation_viewGroup.removeAllViews();
        } catch (Exception e) {
            e.printStackTrace();
        }
        isClean = false;
        super.onLowMemory();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void setCurrenType(boolean currenType) {
        this.currenType = currenType;
    }


    public interface OnClickListener {
        void onClickSubimt(ShopCarSingleInformation shopCarSingleInformation);

        void onItemClick(int positon);
    }

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {

        this.onClickListener = onClickListener;

    }


}
