package com.lucky.sweet.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.lucky.sweet.R;
import com.lucky.sweet.entity.GetMailVerInfo;
import com.lucky.sweet.entity.MailValiInfo;
import com.lucky.sweet.entity.UserLoginInfo;
import com.lucky.sweet.entity.UserRegestInfo;
import com.lucky.sweet.fragment.EmailSubmitFragment;
import com.lucky.sweet.fragment.PassWordSubimitFragment;
import com.lucky.sweet.service.CommunicationService;
import com.lucky.sweet.views.MyToast;
import com.lucky.sweet.widgets.Title;
import com.lucky.sweet.widgets.ToolBar;
import com.lucky.sweet.widgets.noscrollview.DepthPageTransformer;
import com.lucky.sweet.widgets.noscrollview.FragAdapter;
import com.lucky.sweet.widgets.noscrollview.NoScrollViewPager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class UserRegisterActivity extends BaseActivity {
    private Title title = null;
    private ToolBar toolBar;

    private NoScrollViewPager vp_reg;
    private PassWordSubimitFragment passWordSubimitFragment = new PassWordSubimitFragment();
    private EmailSubmitFragment emailSubmitFragment = new EmailSubmitFragment();
    //注册true ，找回flase
    private Boolean isRegister;
    private CommunicationService.MyBinder myBinder;
    private SharedPreferences.Editor edit;
    private final static String INTENTTAG = "isRegister";

    public static void newInstance(Activity activity, boolean isRegister, int result) {
        Intent intent = new Intent(activity, UserRegisterActivity.class);
        intent.putExtra(INTENTTAG, isRegister);
        activity.startActivityForResult(intent, result);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        setIsBindEventBus();

        isRegister = getIntent().getBooleanExtra(INTENTTAG, true);

        initView();

        initTitle();

        initData();

        initEvent();

    }

    @Override
    void onServiceBind(CommunicationService.MyBinder myBinder) {
        this.myBinder = myBinder;
    }

    @SuppressLint("CommitPrefEdits")
    private void initData() {

        edit = getSharedPreferences("config", Activity.MODE_PRIVATE).edit();

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(emailSubmitFragment);
        fragments.add(passWordSubimitFragment);

        vp_reg.setAdapter(new FragAdapter(getSupportFragmentManager(), fragments));
        vp_reg.setPageTransformer(true, new DepthPageTransformer());
    }

    private void initView() {
        toolBar = new ToolBar(this);
        toolBar.setImmersionBar();
        vp_reg = findViewById(R.id.vp_reg);


    }

    private void initTitle() {
        title = (Title) findViewById(R.id.title);
        if (isRegister)
            title.setTitleNameStr("注册");
        else
            title.setTitleNameStr("找回密码");
        title.setTheme(Title.Theme.THEME_TRANSLATE_GRAYDIVIDER);

//        Title.ButtonInfo buttonLeft=new Title.ButtonInfo(true,Title.BUTTON_LEFT,0,"立即注册");
        Title.ButtonInfo buttonLeft = new Title.ButtonInfo(true, Title
                .BUTTON_LEFT, R.drawable.selector_btn_titlebackblack, null);
        title.setOnTitleButtonClickListener((id, viewHolder) -> {
            switch (id) {
                case Title.BUTTON_LEFT:
                    hintInputKb();
                    if (vp_reg.getCurrentItem() != 0) {
                        vp_reg.setCurrentItem(vp_reg.getCurrentItem() - 1, true);
                        return;
                    }
                    finish();
                    break;

            }
        });
        title.mSetButtonInfo(buttonLeft);

    }

    private void initEvent() {
        emailSubmitFragment.setOnEmailVerListener(new EmailSubmitFragment.OnEmailVer() {
            @Override
            public void onEmailVer(String email, String verPsw) {
                myBinder.emailVer(email, verPsw, isRegister);
            }

            @Override
            public void checkOutEmail(String email) {
                myBinder.checkOutEmail(email, isRegister);
            }
        });
        passWordSubimitFragment.setOnUserUpdataInfo((email, psw) -> myBinder.userRegister(email, psw, isRegister));
    }

    protected void hintInputKb() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context
                .INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }


    public void moveToNextStep() {
        vp_reg.setCurrentItem(vp_reg.getCurrentItem() + 1, true);
    }

    @Override
    public void onBackPressed() {
        if (vp_reg.getCurrentItem() != 0) {
            vp_reg.setCurrentItem(vp_reg.getCurrentItem() - 1, true);
            return;
        }
        super.onBackPressed();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(GetMailVerInfo info) {
        if (info.getAttr()) {
            MyToast.showShort("请耐心等待邮件");
        } else {
            MyToast.showShort("邮箱已存在或操作过于频繁");

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MailValiInfo info) {
        if (info.getAttr()) {
            passWordSubimitFragment.setCurrentEmail(emailSubmitFragment.getEmail());
            MyToast.showShort("验证成功请进行下一步");

            moveToNextStep();
        } else
            MyToast.showShort("验证失败");


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(UserRegestInfo info) {
        if (isRegister) {
            if (info.getAttr()) {
                edit.putBoolean("logined", true);
                edit.putString("Id", info.getUserID());
                edit.putString("Psw", info.getPassword());
                if (edit.commit()) {
                    MyToast.showShort("注册成功");

                    MyApplication.initSession(flag -> {
                        if (flag) {
                            Intent intent = new Intent();
                            intent.putExtra("Id", info.getUserID());
                            intent.putExtra("Psw", info.getPassword());
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    });
                }
            } else
                MyToast.showShort("注册失败");


        } else {
            if (info.getAttr()) {
                MyToast.showShort("修改成功");
                finish();
            } else
                MyToast.showShort("原密码不能重复");


        }

    }
}
