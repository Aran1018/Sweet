package com.lucky.sweet.widgets.cardstack;


import android.content.Context;
import android.view.LayoutInflater;

public abstract class StackAdapter<T> extends CardStackView.Adapter<CardStackView.ViewHolder> {

    private final Context mContext;
    private final LayoutInflater mInflater;
    private int size = 0;

    public StackAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);

    }

    public void updateData(int size) {
        this.size = size;
        this.notifyDataSetChanged();
    }


    public LayoutInflater getLayoutInflater() {
        return this.mInflater;
    }

    public Context getContext() {
        return this.mContext;
    }

    @Override
    public void onBindViewHolder(CardStackView.ViewHolder holder, int position) {
        this.bindView(position, holder);
    }

    public abstract void bindView(int position, CardStackView.ViewHolder holder);

    @Override
    public int getItemCount() {
        return size;
    }


}
