package com.lucky.sweet.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.hys.utils.InitCacheFileUtils;
import com.hys.utils.ToastUtils;
import com.lucky.sweet.R;
import com.lucky.sweet.model.dragrecycleview.CompressPictureRunnable;
import com.lucky.sweet.model.dragrecycleview.MyCallBack;
import com.lucky.sweet.model.dragrecycleview.OnRecyclerItemClickListener;
import com.lucky.sweet.model.dragrecycleview.PostArticleImgAdapter;
import com.lucky.sweet.service.CommunicationService;
import com.lucky.sweet.utility.StringFormatUtility;
import com.lucky.sweet.views.MyToast;
import com.lucky.sweet.widgets.Title;
import com.lucky.sweet.widgets.ToolBar;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Qiuyue on 2018/1/17.
 */

// ╭︿︿︿╮  ╭︿︿︿╮  ╭︿︿︿╮
// {/ o o /} {/ . . /} {/ ︿︿ /}
// ( (oo) )  ( (oo) )  ( (oo) )
//   ︶︶︶     ︶︶︶     ︶︶︶

public class SendCircleActivity extends BaseActivity {

    private CommunicationService.MyBinder myBinder;
    private Title title = null;
    private ImageButton seletctPic;
    private int REQUEST_CODE_CHOOSE = 10000;
    public static final String FILE_DIR_NAME = "com.kuyue.wechatpublishimagesdrag";//应用缓存地址
    public static final String FILE_IMG_NAME = "images";//放置图片缓存
    public static final int IMAGE_SIZE = 9;//可添加图片最大数
    private RecyclerView rec_selecter;
    private PostArticleImgAdapter postArticleImgAdapter;

    private ArrayList<String> originImages = new ArrayList<>();//原始图片
    private ArrayList<String> dragImages = new ArrayList<>();//压缩长宽后图片
    private ArrayList<String> getNewImages = new ArrayList<>();//压缩长宽后图片
    private ItemTouchHelper itemTouchHelper;
    private EditText et_description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendcircle);
        InitCacheFileUtils.initImgDir(FILE_DIR_NAME, FILE_IMG_NAME);//清除图片缓存
        initViews();

        initTitle();

        initEvent();
    }

    @Override
    protected void onResume() {
        EventBus.getDefault().register(this);

        super.onResume();
    }

    @Override
    protected void onPause() {
        EventBus.getDefault().unregister(this);

        super.onPause();
    }

    private void initEvent() {

        seletctPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (originImages.size() == 9) {
                    MyToast.showShort("选择图片已到上限");

                } else

                    Matisse.from(SendCircleActivity.this)
                            .choose(MimeType.allOf())
                            .countable(true)
                            .maxSelectable(IMAGE_SIZE - originImages.size())
                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                            .thumbnailScale(0.85f)
                            .imageEngine(new GlideEngine())
                            .forResult(REQUEST_CODE_CHOOSE);
            }
        });
        findViewById(R.id.btn_send_circle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String desc = et_description.getText().toString().trim();
                if (desc.equals("")) {
                    MyToast.showShort("请输入文字描述");
                    return;
                }
                if (originImages.size()==0){
                    MyToast.showShort("请添加图片");
                    return;
                }

                 myBinder.ossCirclePicUpdata(originImages, desc);


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            List<Uri> uris = Matisse.obtainResult(data);
            getNewImages.clear();
            for (Uri uri : uris) {
                getNewImages.add(StringFormatUtility.getRealFilePat(uri));
            }
            new Thread(new CompressPictureRunnable(getNewImages,
                    originImages, dragImages, myHandler, true)).start();
        }
    }

    @Override
    void onServiceBind(CommunicationService.MyBinder myBinder) {
        this.myBinder = myBinder;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(String string) {
        switch (string) {
            case "1":
                MyToast.showShort("发送成功");
                finish();
                //todo 通知用户上传成功
                break;
            case "0":
                MyToast.showShort("发送失败");
                //todo 通知用户上传失败
                break;
            case "250":
                //todo session过期
                break;
        }
    }

    private void initTitle() {
        title = findViewById(R.id.title);
        title.setTitleNameStr("发布圈子");

        Title.ButtonInfo buttonLeft = new Title.ButtonInfo(true, Title
                .BUTTON_LEFT, R.drawable.selector_btn_titleback, null);
//        Title.ButtonInfo buttonRight = new Title.ButtonInfo(true, Title
//                .BUTTON_RIGHT1, 0, "选择相片");
        title.setOnTitleButtonClickListener(new Title
                .OnTitleButtonClickListener() {
            @Override
            public void onClick(int id, Title.ButtonViewHolder viewHolder) {
                switch (id) {
                    case Title.BUTTON_LEFT:
                        finish();
                        goPreAnim();
                        break;


                }
            }
        });

        title.mSetButtonInfo(buttonLeft);
//        title.mSetButtonInfo(buttonRight);
    }

    private void initViews() {
        ToolBar toolBar = new ToolBar(this);
        toolBar.setColorNewBar(getResources().getColor(R.color.white), 0);
        seletctPic = findViewById(R.id.ibtn_album);
        et_description = findViewById(R.id.et_description);
        initRcv();
    }

    private void initRcv() {
        InitCacheFileUtils.initImgDir(FILE_DIR_NAME, FILE_IMG_NAME);//清除图片缓存
        rec_selecter = findViewById(R.id.rev_share_circle);
        rec_selecter.setLayoutManager(new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL));
        postArticleImgAdapter = new PostArticleImgAdapter(this, dragImages);
        rec_selecter.setAdapter(postArticleImgAdapter);
        MyCallBack myCallBack = new MyCallBack(postArticleImgAdapter, dragImages, originImages);
        itemTouchHelper = new ItemTouchHelper(myCallBack);
        itemTouchHelper.attachToRecyclerView(rec_selecter);//绑定RecyclerView
        rec_selecter.addOnItemTouchListener(new OnRecyclerItemClickListener(rec_selecter) {

            @Override
            public void onItemImageClick(int position, View view) {

            }

            @Override
            public void onItemDeleteClick(int position, View view) {

                originImages.remove(position);
                dragImages.remove(position);
                postArticleImgAdapter.notifyDataSetChanged();
            }


            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh) {
                //如果item不是最后一个，则执行拖拽
                if (vh.getLayoutPosition() != dragImages.size() - 1) {
                    itemTouchHelper.startDrag(vh);
                }
            }
        });
    }

    private MyHandler myHandler = new MyHandler(this);

    private static class MyHandler extends Handler {
        private SendCircleActivity activity;

        public MyHandler(SendCircleActivity activity) {
            this.activity = activity;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    activity.postArticleImgAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }

        }
    }
}
