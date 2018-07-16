package com.lucky.sweet.model.dragrecycleview;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.lucky.sweet.R;

/**
 * 手势监听
 * Created by kuyue on 2017/6/20 上午10:45.
 * 邮箱:595327086@qq.com
 */

public abstract class OnRecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

    private GestureDetectorCompat mGestureDetector;
    private RecyclerView recyclerView;

    public OnRecyclerItemClickListener(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        mGestureDetector = new GestureDetectorCompat(recyclerView.getContext(), new ItemTouchHelperGestureListener());
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    private class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (child != null) {
                RecyclerView.ViewHolder vh = recyclerView.getChildViewHolder(child);
                final int layoutPosition = vh.getLayoutPosition();
                child.findViewById(R.id.imb_delete).setOnClickListener(new View
                        .OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemDeleteClick(layoutPosition, v);
                    }
                });
                child.findViewById(R.id.imv_circle_item).setOnClickListener(new View
                        .OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemImageClick(layoutPosition, v);
                    }
                });
            }
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (child != null) {
                RecyclerView.ViewHolder vh = recyclerView.getChildViewHolder(child);
                onItemLongClick(vh);
            }
        }
    }
    public abstract void onItemImageClick(int position, View view);

    public abstract void onItemDeleteClick(int position, View view);

    public abstract void onItemLongClick(RecyclerView.ViewHolder vh);

}
