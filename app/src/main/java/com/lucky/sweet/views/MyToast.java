package com.lucky.sweet.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lucky.sweet.R;
import com.lucky.sweet.activity.MyApplication;


/**
 * Created by Qiuyue on 2017/9/15.
 */

/*
 ╭︿︿︿╮  ╭︿︿︿╮  ╭︿︿︿╮
 {/ o o /} {/ . . /} {/ ︿︿ /}
 ( (oo) )  ( (oo) )  ( (oo) )
   ︶︶︶    ︶︶︶     ︶︶︶
*/

public class MyToast {
    private Toast mToast;
    private MyToast(Context context, String text, int duration) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_mytoast, null);
        TextView textView = (TextView) v.findViewById(R.id.tv_toastContent);
        textView.setText(text);
        mToast = new Toast(context);
        mToast.setDuration(duration);
        mToast.setView(v);
        mToast.show();
    }

    public static void showShort(String text) {
        new MyToast(MyApplication.getContext(), text, Toast.LENGTH_SHORT);
    }

    public static void showLong(String text) {
        new MyToast(MyApplication.getContext(), text, Toast.LENGTH_LONG);
    }

}

