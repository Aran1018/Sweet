package com.lucky.sweet.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lucky.sweet.R;
import com.lucky.sweet.entity.WeatherInfo;
import com.lucky.sweet.service.CommunicationService;
import com.lucky.sweet.views.MyToast;
import com.lucky.sweet.widgets.ToolBar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class UserLoginActivity extends BaseActivity {

    private ToolBar toolBar;
    private EditText edt_userEmail;
    private EditText edt_password;
    private final int REQUEST_CODE = 1;
    private CommunicationService.MyBinder myBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        setIsBindEventBus();
        initView();

    }

    @Override
    void onServiceBind(CommunicationService.MyBinder myBinder) {
        this.myBinder = myBinder;
    }


    private void initView() {
        toolBar = new ToolBar(this);
        toolBar.setImmersionBar();
        edt_userEmail = findViewById(R.id.edt_userEmail);
        edt_password = findViewById(R.id.edt_password);
    }


    public void login(View view) {
        String userID = edt_userEmail.getText().toString().trim();
        String password = edt_password.getText().toString().trim();
        if (!userID.isEmpty() && !password.isEmpty()) {
            myBinder.userLogin(userID, password);
        } else {
            MyToast.showShort("请输入账号或者密码");
//            Toast.makeText(this, "请输入账号或者密码", Toast.LENGTH_SHORT).show();
        }
    }

    public void forgetPsw(View view) {
        UserRegisterActivity.newInstance(this, false, REQUEST_CODE);
    }

    public void register(View view) {

        UserRegisterActivity.newInstance(this, true, REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE:
                    String id = data.getStringExtra("Id");
                    String Psw = data.getStringExtra("Psw");
                    edt_userEmail.setText(id);
                    edt_password.setText(Psw);
                    myBinder.userLogin(id, Psw);
                    break;
            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(String string) {
        if (string.length() == 32) {
            MyApplication.setSessionID(string);
            MyApplication.setOnCloundPush(edt_userEmail.getText().toString().trim());
            MyApplication.upDataPersonLoginInfo(edt_userEmail.getText().toString(), edt_password.getText().toString());
            MyToast.showShort("登陆成功");
            finish();
        } else {
            MyToast.showShort("登陆失败");

        }

    }

}
