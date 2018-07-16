package com.lucky.sweet.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lucky.sweet.R;
import com.lucky.sweet.entity.MainStoreInfo;

import java.util.List;

/**
 * Created by chn on 2017/12/20.
 * (╯°□°）╯︵ ┻━┻ MMP好气啊！
 * (╯‵□′)╯︵┻━┻ 老子怒掀桌子！
 * ┻━┻︵╰(‵□′)╯︵┻━┻老子双手掀桌！
 * ┬─┬﻿ ノ( ゜-゜ノ) 算了，我不生气了！日子还得过老老实实敲吧~
 */

public class RecFoodRecommendAdapter extends RecyclerView.Adapter {
    private Context context;
    private final LayoutInflater inflater;
    private int type;

    private List<MainStoreInfo.FoodBean> food;
    private List<MainStoreInfo.RecreationBean> recreation;
    private int AdapterSize;

    public final static int FOOD = 0;
    public final static int FUN = 1;


    public RecFoodRecommendAdapter(Context context, int type, List data) {
        this.context = context;
        this.type = type;
        switch (type) {
            case FOOD:
                food = (List<MainStoreInfo.FoodBean>) data;
                break;
            case FUN:
                recreation = (List<MainStoreInfo.RecreationBean>) data;
                break;
            default:
                break;
        }
        AdapterSize = data.size();
       inflater = LayoutInflater.from(context);

    }


    @Override
    public int getItemViewType(int position) {
        return position;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == AdapterSize - 1) return new moreViewHolder(inflater.inflate(R.layout.item_recycle_more, parent, false));
        return new mViewHolder(inflater.inflate(R.layout.item_viewpager_foodinfo, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof mViewHolder) {

            mViewHolder.FoodBean info = new mViewHolder.FoodBean();
            String mer_id = "";
            switch (type) {
                case FOOD:
                    MainStoreInfo.FoodBean foodBean = food.get(position);
                    mer_id = foodBean.getMer_id();
                    info.setClassify(foodBean.getClassify());
                    info.setGrade(foodBean.getGrade());
                    info.setDis(foodBean.getDistance());
                    info.setName(foodBean.getName());
                    info.setPho_url(foodBean.getPho_url());
                    break;
                case FUN:
                    MainStoreInfo.RecreationBean recreationBean = recreation.get(position);
                    mer_id = recreationBean.getMer_id();
                    info.setClassify(recreationBean.getClassify());
                    info.setGrade(recreationBean.getGrade());
                    info.setDis(recreationBean.getDistance());
                    info.setName(recreationBean.getName());
                    info.setPho_url(recreationBean.getPho_url());
                    break;
                default:
                    break;
            }

            mViewHolder mViewHolder = (mViewHolder) holder;


            mViewHolder.bindData(info, context);
            final String finalMer_id = mer_id;

            mViewHolder.ll_cover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != onItemClickListener) {
                        onItemClickListener.onItemClickListener(finalMer_id);
                    }
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return AdapterSize;
    }

    static class mViewHolder extends RecyclerView.ViewHolder {
        ImageView imv_photo;
        TextView tv_info;
        TextView tv_comment;
        TextView tv_recreation;
        TextView tv_dis;
        LinearLayout ll_cover;

        public mViewHolder(View itemView) {
            super(itemView);
            imv_photo = itemView.findViewById(R.id.imv_photo);
            tv_info = itemView.findViewById(R.id.tv_info);
            tv_comment = itemView.findViewById(R.id.tv_comment);
            tv_recreation = itemView.findViewById(R.id.tv_recreation);
            tv_dis = itemView.findViewById(R.id.tv_dis);
            ll_cover = itemView.findViewById(R.id.ll_cover);
        }

        public void bindData(FoodBean foodBean, Context context) {
            Glide.with(context).load(foodBean.getPho_url()).diskCacheStrategy
                    (DiskCacheStrategy.ALL).into(imv_photo);
            tv_info.setText(foodBean.getName());
            tv_recreation.setText(foodBean.getClassify());
            tv_comment.setText(foodBean.getGrade() + "条评论");
            tv_dis.setText(foodBean.getDis() + "公里");
        }

        public static class FoodBean {
            private String dis;
            private String name;
            private String classify;
            private String grade;
            private String pho_url;

            public String getDis() {
                return dis;
            }

            public void setDis(double dis) {

                this.dis = String.format("%.2f", dis);
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getClassify() {
                return classify;
            }

            public void setClassify(String classify) {
                this.classify = classify;
            }


            public String getGrade() {
                return grade;
            }

            public void setGrade(String grade) {
                this.grade = grade;
            }


            public String getPho_url() {
                return pho_url;
            }

            public void setPho_url(String pho_url) {
                this.pho_url = pho_url;
            }
        }
    }

    static class moreViewHolder extends RecyclerView.ViewHolder {


        public moreViewHolder(View itemView) {
            super(itemView);
        }


    }

    public interface OnItemClickListener {
        void onItemClickListener(String shopId);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
