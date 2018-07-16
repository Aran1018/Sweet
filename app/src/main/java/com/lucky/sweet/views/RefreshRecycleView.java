package com.lucky.sweet.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.Toast;

/**
 * Created by C on 2018/3/13.
 */

public class RefreshRecycleView extends RecyclerView {
    private static final float SCROLL_RATIO = 0.5f;// 阻尼系数

    private LinearLayoutManager layoutManager;

    public RefreshRecycleView(Context context) {
        super(context);
        initView();
    }

    public RefreshRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RefreshRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        float density = displayMetrics.density;

    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
        if (layout instanceof LinearLayoutManager)
            layoutManager = (LinearLayoutManager) layout;
    }

    boolean isShowBottom = false;

    @Override
    public void scrollBy(int x, int y) {
        x = (int) (x * 0.01f);
        y = (int) (y * 0.01f);
        super.scrollBy(x, y);

    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        if (layoutManager != null) {
            if (layoutManager.findLastCompletelyVisibleItemPosition() == getAdapter().getItemCount() - 1) {
                if (!isShowBottom) {
                    Toast.makeText(getContext(), "滑动到底部", Toast.LENGTH_SHORT).show();
                    isShowBottom = true;
                }
            }
        }

    }


}
