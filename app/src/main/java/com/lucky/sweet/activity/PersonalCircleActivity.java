package com.lucky.sweet.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lucky.sweet.R;
import com.lucky.sweet.adapter.CircleCommentAdapter;
import com.lucky.sweet.adapter.PersonalCircleAdapter;
import com.lucky.sweet.entity.CircleComment;
import com.lucky.sweet.entity.CircleDetail;
import com.lucky.sweet.entity.CircleMainInfo;
import com.lucky.sweet.entity.CircleUpDataInfo;
import com.lucky.sweet.service.CommunicationService;
import com.lucky.sweet.views.CircleImageView;
import com.lucky.sweet.views.MyToast;
import com.lucky.sweet.widgets.ImageViewWatcher.MessagePicturesLayout;
import com.lucky.sweet.widgets.Title;
import com.lucky.sweet.widgets.ToolBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Qiuyue on 2018/1/16.
 */

// ╭︿︿︿╮  ╭︿︿︿╮  ╭︿︿︿╮
// {/ o o /} {/ . . /} {/ ︿︿ /}
// ( (oo) )  ( (oo) )  ( (oo) )
//   ︶︶︶     ︶︶︶     ︶︶︶

public class PersonalCircleActivity extends BaseActivity implements View.OnClickListener {
    private Title title = null;
    private CircleImageView imv_head;
    private CircleMainInfo.CircleListBean circle_info;
    private TextView tv_content;
    private TextView tv_name;
    private static final String CIRCLE_INFO = "100000";
    private CommunicationService.MyBinder myBinder;
    private PersonalCircleAdapter personalCircleAdapter;
    private MessagePicturesLayout l_pictures;

    private ImageView comment;
    private TextView hide_down;
    private EditText comment_content;
    private Button comment_send;

    private LinearLayout rl_enroll;
    private RelativeLayout rl_comment;

    private ListView comment_list;
    private CircleCommentAdapter adapterComment;
    private List<CircleComment> data;
    private ListView lv_comments;
    private String mer_id;
    private String user_id;
    private String circle_id;


    public static void newInStance(FragmentActivity activity, CircleMainInfo.CircleListBean circleListBean) {
        Intent intent = new Intent(activity, PersonalCircleActivity.class);
        intent.putExtra(CIRCLE_INFO, circleListBean);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_circle);
        circle_info = (CircleMainInfo.CircleListBean) getIntent().getSerializableExtra(CIRCLE_INFO);


        setIsBindEventBus();
        initTitle();
        initViews();
        initDatas();
        setListener();

    }

    private void initDatas() {
        tv_name.setText(circle_info.getNikcname());
        tv_content.setText(circle_info.getContent());
        l_pictures.set(circle_info.getPhoto_url());
        circle_id = circle_info.getCircle_id();
        mer_id = circle_info.getMer_id();
        user_id = circle_info.getUser_id();
    }

    @Override
    void onServiceBind(CommunicationService.MyBinder myBinder) {
        if (circle_id != null) {

            myBinder.sendCircledetailsInfo(circle_id);
        }
        this.myBinder = myBinder;
    }

    private void initViews() {
        ToolBar toolBar = new ToolBar(this);
        toolBar.setStatusBarDarkMode();
        imv_head = findViewById(R.id.imv_head);
        imv_head.setmDrawShapeType(CircleImageView.SHAPE_CIRCLE);

        tv_name = findViewById(R.id.tv_name);
        tv_content = findViewById(R.id.tv_content);


        // 初始化评论列表
        //  comment_list = findViewById(R.id.comment_list);
        // 初始化数据
        //  data = ;
        // 初始化适配器
        //adapterComment = new CircleCommentAdapter(getApplicationContext(), new ArrayList<>());
        // 为评论列表设置适配器
        // comment_list.setAdapter(adapterComment);

        comment = (ImageView) findViewById(R.id.comment);
        lv_comments = findViewById(R.id.lv_comments);
        hide_down = (TextView) findViewById(R.id.hide_down);
        comment_content = (EditText) findViewById(R.id.comment_content);
        comment_send = (Button) findViewById(R.id.comment_send);
        rl_enroll = (LinearLayout) findViewById(R.id.rl_enroll);
        rl_comment = (RelativeLayout) findViewById(R.id.rl_comment);
        l_pictures = findViewById(R.id.l_pictures);
    }

    public void setListener() {
        comment.setOnClickListener(this);
        hide_down.setOnClickListener(this);
        comment_send.setOnClickListener(this);
        findViewById(R.id.btn_click).setOnClickListener(this);

    }

    private void initTitle() {
        title = findViewById(R.id.title);
        title.setTitleNameStr("评论");
        title.setTheme(Title.Theme.THEME_TRANSLATE);
        Title.ButtonInfo buttonLeft = new Title.ButtonInfo(true, Title
                .BUTTON_LEFT, R.drawable.selector_btn_titleback, null);

        title.setOnTitleButtonClickListener((id, viewHolder) -> {
            switch (id) {
                case Title.BUTTON_LEFT:
                    finish();
                    goPreAnim();
                    break;
            }
        });

        title.mSetButtonInfo(buttonLeft);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(CircleDetail info) {
        personalCircleAdapter = new PersonalCircleAdapter(info.getComment(), this);
        personalCircleAdapter.setRequestPeople(new PersonalCircleAdapter.RequestPeople() {
            @Override
            public void onClicked(String circleId, String reqlyId) {
                upKeyBoard();
                circle_id = circleId;
                user_id = reqlyId;
            }
        });
        lv_comments.setAdapter(personalCircleAdapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.comment:
                // 弹出输入法
                upKeyBoard();
                break;
            case R.id.hide_down:
                // 隐藏评论框
                rl_enroll.setVisibility(View.VISIBLE);
                rl_comment.setVisibility(View.GONE);
                // 隐藏输入法，然后暂存当前输入框的内容，方便下次使用
                InputMethodManager im = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(comment_content.getWindowToken(), 0);
                break;
            case R.id.comment_send:
                sendComment();
                break;
            case R.id.btn_click:
                circle_id = circle_info.getCircle_id();
                mer_id = circle_info.getMer_id();
                user_id = circle_info.getUser_id();
                upKeyBoard();
                break;
            default:
                break;
        }
    }

    private void upKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        // 显示评论框
        rl_enroll.setVisibility(View.GONE);
        rl_comment.setVisibility(View.VISIBLE);
    }

    public void sendComment() {
        if (comment_content.getText().toString().equals("")) {
            MyToast.showShort("评论不能为空！");

        } else {
            // 生成评论数据
            myBinder.commentCircle(user_id, circle_id, mer_id, comment_content.getText().toString());

            // CircleComment comment = new CircleComment();
            // comment.setName("评论者" + (data.size() + 1) + "：");
            //comment.setContent(comment_content.getText().toString());
            // adapterComment.addComment(comment);
            // 发送完，清空输入框
            comment_content.setText("");

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(CircleUpDataInfo info) {
        if (info.getAttr()) {
//            Toast.makeText(getApplicationContext(), "评论成功！", Toast.LENGTH_SHORT).show();
            MyToast.showShort("评论成功！");

            myBinder.sendCircledetailsInfo(circle_id);
        } else {

            MyToast.showShort("评论失败！");

        }
    }
}
