package com.lucky.sweet.widgets.ImageViewWatcher;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.common.utils.OSSUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lucky.sweet.R;
import com.lucky.sweet.utility.OssUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * QQ 517309507
 * 至尊流畅;daLao专用;/斜眼笑
 */
public class MessagePicturesLayout extends FrameLayout implements View.OnClickListener {

    public static final int MAX_DISPLAY_COUNT = 9;
    private final LayoutParams lpChildImage = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    private final int mSingleMaxSize;
    private final int mSpace;
    private final List<ImageView> iPictureList = new ArrayList<>();
    private final List<ImageView> mVisiblePictureList = new ArrayList<>();


    private Callback mCallback;
    private boolean isInit;
    private List<String> mDataList;


    public MessagePicturesLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        //获取屏幕参数
        DisplayMetrics mDisplayMetrics = context.getResources().getDisplayMetrics();
        mSingleMaxSize = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 216, mDisplayMetrics) + 0.5f);
        mSpace = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, mDisplayMetrics) + 0.5f);
//负责腾地方，所有的图片需要放在这个控件里


//        for (int i = 0; i < CirclePicData.get().size(); i++) {
//                  mDataList.add( CirclePicData.get().get(i).getPictureList())
//        }
        for (int i = 0; i < 9; i++) {
            ImageView squareImageView = new SquareImageView(context);
            squareImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            squareImageView.setOnClickListener(this);
            addView(squareImageView);
            iPictureList.add(squareImageView);
        }


    }

    public void set(List<String> urlList) {
        mDataList = urlList;
        if (isInit) {
            notifyDataChanged();
        }

    }

    private void notifyDataChanged() {
        final List<String> dataList = mDataList;
        final int urlListSize = dataList != null ? mDataList.size() : 0;

        /*if (mDataList == null || mDataList.size() != urlListSize) {
            throw new IllegalArgumentException("dataList.size != thumbDataList.size");
        }*/

        //控制显示行数列数
        int column = 3;
        if (urlListSize == 1) {
            column = 1;
        } else if (urlListSize == 4) {
            column = 2;
        }
        int row = 0;
        if (urlListSize > 6) {
            row = 3;
        } else if (urlListSize > 3) {
            row = 2;
        } else if (urlListSize > 0) {
            row = 1;
        }

        final int imageSize = urlListSize == 1 ? mSingleMaxSize :
                (int) ((getWidth() * 1f - mSpace * (column - 1)) / column);

        lpChildImage.width = imageSize;
        lpChildImage.height = lpChildImage.width;


        mVisiblePictureList.clear();
        for (int i = 0; i < iPictureList.size(); i++) {
            final ImageView iPicture = iPictureList.get(i);
            if (i < urlListSize) {

                mVisiblePictureList.add(iPicture);
                iPicture.setLayoutParams(lpChildImage);
                iPicture.setBackgroundResource(R.drawable.default_picture);
                Glide.with(getContext()).load(dataList.get(i)).diskCacheStrategy(DiskCacheStrategy.ALL).into(iPicture);
                iPicture.setTranslationX((i % column) * (imageSize + mSpace));
                iPicture.setTranslationY((i / column) * (imageSize + mSpace));
            } else {
                iPicture.setVisibility(View.GONE);
            }

        }
        getLayoutParams().height = imageSize * row + mSpace * (row - 1);
    }

    @Override
    public void onClick(View v) {
        if (mCallback != null) {
            mCallback.onThumbPictureClick((ImageView) v, mVisiblePictureList, mDataList);
        }
    }

    public interface Callback {
        void onThumbPictureClick(ImageView i, List<ImageView> imageGroupList,
                                 List<String> urlList);
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        isInit = true;
        notifyDataChanged();
    }
}
