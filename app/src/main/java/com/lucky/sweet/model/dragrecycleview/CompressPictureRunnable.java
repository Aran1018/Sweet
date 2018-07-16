package com.lucky.sweet.model.dragrecycleview;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

import com.hys.utils.DensityUtils;
import com.hys.utils.ImageUtils;
import com.hys.utils.SdcardUtils;
import com.lucky.sweet.activity.MyApplication;
import com.lucky.sweet.activity.SendCircleActivity;

import java.util.ArrayList;


/**
 * Created by c on 2017/11/29.
 * (╯°□°）╯︵ ┻━┻ MMP好气啊！
 * (╯‵□′)╯︵┻━┻ 老子怒掀桌子！
 * ┻━┻︵╰(‵□′)╯︵┻━┻老子双手掀桌！
 * ┬─┬﻿ ノ( ゜-゜ノ) 算了，我不生气了！日子还得过老老实实敲吧~
 */

public class CompressPictureRunnable implements Runnable {


    ArrayList<String> images;
    ArrayList<String> originImages;
    ArrayList<String> dragImages;
    Handler handler;
    boolean add;//是否为添加图片

    public CompressPictureRunnable(ArrayList<String> images, ArrayList<String> originImages, ArrayList<String> dragImages, Handler handler, boolean add) {
        this.images = images;
        this.originImages = originImages;
        this.dragImages = dragImages;
        this.handler = handler;
        this.add = add;
    }

    @Override
    public void run() {
        SdcardUtils sdcardUtils = new SdcardUtils();
        String filePath;
        Bitmap newBitmap;
        int addIndex;
        if (originImages.size() == 0)
            addIndex = 0;
         else
            addIndex = originImages.size() - 1;
        for (int i = 0; i < images.size(); i++) {
            //压缩
            newBitmap = ImageUtils.compressScaleByWH(images.get(i),
                    DensityUtils.dp2px(MyApplication.getContext(), 100),
                    DensityUtils.dp2px(MyApplication.getContext(), 100));
            //文件地址
            filePath = sdcardUtils.getSDPATH() + SendCircleActivity
                    .FILE_DIR_NAME + "/"
                    + SendCircleActivity.FILE_IMG_NAME + "/" + String.format("img_%d.jpg", System.currentTimeMillis());
            //保存图片
            ImageUtils.save(newBitmap, filePath, Bitmap.CompressFormat.JPEG, true);
            //设置值
            if (!add) {
                images.set(i, filePath);
            } else {//添加图片，要更新
                dragImages.add(addIndex, filePath);
                originImages.add(addIndex++, filePath);
            }
        }
        Message message = new Message();
        message.what = 1;
        handler.sendMessage(message);
    }
}
