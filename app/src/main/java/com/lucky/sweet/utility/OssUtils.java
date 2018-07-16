package com.lucky.sweet.utility;

import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.model.GetObjectRequest;
import com.alibaba.sdk.android.oss.model.GetObjectResult;
import com.lucky.sweet.activity.MyApplication;

import java.io.InputStream;

import static com.lucky.sweet.properties.ServiceProperties.TEST_BUCKET;

/**
 * Created by chn on 2018/1/18.
 * (╯°□°）╯︵ ┻━┻ MMP好气啊！
 * (╯‵□′)╯︵┻━┻ 老子怒掀桌子！
 * ┻━┻︵╰(‵□′)╯︵┻━┻老子双手掀桌！
 * ┬─┬﻿ ノ( ゜-゜ノ) 算了，我不生气了！日子还得过老老实实敲吧~
 */

public class OssUtils {

    public static String getOSSExtranetPath(String objectKey) {
        String path="";
        try {
            path = MyApplication.getOSSClient().presignConstrainedObjectURL(TEST_BUCKET, objectKey, 30);
        } catch (ClientException e) {
            e.printStackTrace();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return path;
    }
}
