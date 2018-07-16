package com.lucky.sweet.model.shoppingcar.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lucky.sweet.R;
import com.lucky.sweet.model.shoppingcar.ShopMenuAttr;
import com.lucky.sweet.model.shoppingcar.assistant.onCallBackListener;
import com.lucky.sweet.model.shoppingcar.mode.ProductType;
import com.lucky.sweet.model.shoppingcar.mode.ShopProduct;
import com.lucky.sweet.utility.OssUtils;

import java.io.InputStream;
import java.util.List;

public class TestSectionedAdapter extends SectionedBaseAdapter {

    List<ProductType> pruductCagests;
    private HolderClickListener mHolderClickListener;
    private Context context;
    private LayoutInflater mInflater;


    private onCallBackListener callBackListener;

    public void setCallBackListener(onCallBackListener callBackListener) {
        this.callBackListener = callBackListener;
    }


    public TestSectionedAdapter(Context context, List<ProductType> pruductCagests) {
        this.context = context;
        this.pruductCagests = pruductCagests;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public Object getItem(int section, int position) {
        return pruductCagests.get(section).getProduct().get(position);
    }

    @Override
    public long getItemId(int section, int position) {
        return position;
    }

    @Override
    public int getSectionCount() {
        return pruductCagests.size();
    }

    @Override
    public int getCountForSection(int section) {
        return pruductCagests.get(section).getProduct().size();
    }

    @Override
    public View getItemView(final int section, final int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_product, null);
            viewHolder = new ViewHolder();
            viewHolder.head = convertView.findViewById(R.id.head);
            viewHolder.name = convertView.findViewById(R.id.name);
            viewHolder.prise = convertView.findViewById(R.id.tv_prise);
            viewHolder.increase = convertView.findViewById(R.id.increase);
            viewHolder.reduce = convertView.findViewById(R.id.reduce);
            viewHolder.shoppingNum = convertView.findViewById(R.id.shoppingNum);
            viewHolder.back = convertView.findViewById(R.id.ll_productInfo);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final ShopProduct product = pruductCagests.get(section).getProduct().get(position);
        viewHolder.name.setText(product.getGoods());
        viewHolder.prise.setText(String.valueOf(product.getPrice()));
        viewHolder.shoppingNum.setText(String.valueOf(product.getNumber()));
        viewHolder.back.setOnClickListener(v -> {
            if (onItemDesClickListener != null) {
                onItemDesClickListener.onClick(section, position);
            }
        });
        String picture = product.getPicture();

        if (picture != null) {
            Glide.with(context).load(picture).diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.head);
        }


        viewHolder.increase.setOnClickListener(v -> {
            int num = product.getNumber();
            num++;
            product.setNumber(num);
            viewHolder.shoppingNum.setText(product.getNumber() + "");

            if (callBackListener != null) callBackListener.updateProduct(product, "1");

            if (mHolderClickListener != null) {
                int[] start_location = new int[2];
                viewHolder.shoppingNum.getLocationInWindow(start_location);//获取点击商品图片的位置
                Drawable drawable = context.getResources().getDrawable(R.mipmap.add_product);//复制一个新的商品图标
                //TODO:解决方案，先监听到左边ListView的Item中，然后在开始动画添加
                mHolderClickListener.onHolderClick(drawable, start_location);
            }
            if (onMenuChangedListener != null)
                onMenuChangedListener.onChanged(product.getId(), section, position, ShopMenuAttr.ADD);

        });
        viewHolder.reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = product.getNumber();
                if (num > 0) {
                    num--;
                    product.setNumber(num);
                    viewHolder.shoppingNum.setText(product.getNumber() + "");

                    if (callBackListener != null) callBackListener.updateProduct(product, "2");

                    if (onMenuChangedListener != null)
                        onMenuChangedListener.onChanged(product.getId(), section, position, ShopMenuAttr.DELETE);

                }
            }
        });

        viewHolder.shoppingNum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                } else {
                    // 此处为失去焦点时的处理内容
                    int shoppingNum = Integer.parseInt(viewHolder.shoppingNum.getText().toString());
                }
            }
        });

        return convertView;
    }

    private OnItemDesClickListener onItemDesClickListener;
    private OnMenuChangedListener onMenuChangedListener;

    public void setOnMenuChangedListener(OnMenuChangedListener onMenuChangedListener) {
        this.onMenuChangedListener = onMenuChangedListener;

    }

    public void setOnItemDesClickListener(OnItemDesClickListener onItemDesClickListener) {
        this.onItemDesClickListener = onItemDesClickListener;
    }

    public interface OnItemDesClickListener {
        void onClick(int section, int position);
    }

    public interface OnMenuChangedListener {
        void onChanged(int id, int section, int position, ShopMenuAttr add);
    }

    class ViewHolder {
        /**
         * 商品图片
         */
        public ImageView head;
        /**
         * 商品名称
         */
        public TextView name;
        /**
         * 商品价格
         */
        public TextView prise;
        /**
         * 增加
         */
        public TextView increase;
        /**
         * 商品数目
         */
        public TextView shoppingNum;
        /**
         * 减少
         */
        public TextView reduce;
        public LinearLayout back;
    }

    public void SetOnSetHolderClickListener(HolderClickListener holderClickListener) {
        this.mHolderClickListener = holderClickListener;
    }

    public interface HolderClickListener {
        void onHolderClick(Drawable drawable, int[] start_location);
    }


    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        LinearLayout layout = null;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) inflator.inflate(R.layout.item_lable, null);
        } else {
            layout = (LinearLayout) convertView;
        }
        layout.setClickable(false);
        ((TextView) layout.findViewById(R.id.textItem)).setText(pruductCagests.get(section).getType());
        return layout;
    }

}
