package com.lucky.sweet.viewsexpand;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lucky.sweet.R;
import com.lucky.sweet.activity.StoreDisplayActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by chn on 2018/1/1.
 * (╯°□°）╯︵ ┻━┻ MMP好气啊！
 * (╯‵□′)╯︵┻━┻ 老子怒掀桌子！
 * ┻━┻︵╰(‵□′)╯︵┻━┻老子双手掀桌！
 * ┬─┬﻿ ノ( ゜-゜ノ) 算了，我不生气了！日子还得过老老实实敲吧~
 */

public class AdRecyCleViewOnScrollState extends RecyclerView.OnScrollListener {
    private boolean flag = false;
    private Context context;
    private int type;

    public final static int FUN = 1;
    public final static int FOOD = 2;

    public AdRecyCleViewOnScrollState(Context context, int type) {
        this.context = context;
        this.type = type;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
        // 当不滚动时
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            //获取最后一个完全显示的ItemPosition
            int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
            int totalItemCount = manager.getItemCount();

            // 判断是否滚动到底部，并且是向右滚动
            if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
                //加载更多功能的代码

                if (flag) {
                    flag = false;


                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Intent intent;
                            switch (type) {
                                case FOOD:
                                    intent = new Intent(context,
                                            StoreDisplayActivity.class);
                                    intent.putExtra("tv_moreFood", "Food");
                                    context.startActivity(intent);
                                    ((Activity) context).overridePendingTransition(R.anim.act_left_in, R.anim.act_left_out);
                                    break;
                                case FUN:
                                    intent = new Intent(context,
                                            StoreDisplayActivity.class);
                                    intent.putExtra("tv_moreRelax", "Relax");
                                    context.startActivity(intent);
                                    ((Activity) context).overridePendingTransition(R.anim.act_left_in, R.anim.act_left_out);
                                    break;
                            }

                        }
                    }, 400);

                    return;
                }
                flag = true;
            }
        }
    }

    boolean isSlidingToLast;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
        if (dx > 0 || dy > 0) {
            //大于0表示，正在向右滚动
            isSlidingToLast = true;
        } else {
            //小于等于0 表示停止或向左滚动
            isSlidingToLast = false;
        }

    }

}
