package com.lucky.sweet.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.LoadCallback;
import com.lucky.sweet.R;
import com.lucky.sweet.properties.Properties;
import com.lucky.sweet.service.CommunicationService;
import com.lucky.sweet.utility.BlurBitmapUtil;


public class CropIwaActivity extends BaseActivity {
    public final String cachePath = Properties.ORDER_SEAT_BACKGROUND_PATH + "/personPerCache.png";
    private Button submitCrop;
    private CropImageView cropView;
    private Uri img_uri;
    private final String USER_PORTRAIT_PATH = "sweet/person/portrait/" + MyApplication.USER_ID + ".png";
    private CommunicationService.MyBinder myBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_iwa);

        img_uri = Uri.parse(getIntent().getStringExtra("img_uri"));

        initView();

        initEvent();
    }


    @Override
    void onServiceBind(CommunicationService.MyBinder myBinder) {
        this.myBinder = myBinder;
    }

    private void initEvent() {
        submitCrop.setOnClickListener(v -> cropView.crop(img_uri)
                .execute(new CropCallback() {
                    @Override
                    public void onSuccess(final Bitmap cropped) {
                        new Thread() {
                            @Override
                            public void run() {
                                BlurBitmapUtil.saveFile(cropped, cachePath);
                                myBinder.upDataPersonPortrait(USER_PORTRAIT_PATH, cachePath, () -> {
                                    myBinder.upPersonPic(USER_PORTRAIT_PATH);
                                    Intent intent = new Intent();
                                    intent.putExtra("path", cachePath);
                                    setResult(RESULT_OK, intent);
                                    finish();
                                });

                            }

                        }.start();
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                }));
    }

    private void initView() {
        submitCrop = findViewById(R.id.btn_submit_crop);
        cropView = findViewById(R.id.crop_view);
        cropView.setOutputHeight(320);
        cropView.setOutputWidth(320);
        cropView.setCompressFormat(Bitmap.CompressFormat.JPEG);
        cropView.setHandleShowMode(CropImageView.ShowMode.SHOW_ALWAYS);
        cropView.setGuideShowMode(CropImageView.ShowMode.SHOW_ON_TOUCH);
        cropView.setCropMode(CropImageView.CropMode.CIRCLE);
        cropView.load(img_uri).execute(new LoadCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Throwable e) {

            }
        });

    }
}
