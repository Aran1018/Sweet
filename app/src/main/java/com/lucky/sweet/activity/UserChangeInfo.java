package com.lucky.sweet.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.lucky.sweet.R;
import com.lucky.sweet.entity.ChangeNameRequstInfo;
import com.lucky.sweet.entity.UserRegestInfo;
import com.lucky.sweet.service.CommunicationService;
import com.lucky.sweet.views.MyToast;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class UserChangeInfo extends BaseActivity {
    private final static String USERNAME = "NAME";
    private String userName;
    private EditText edt_user_change;
    private String changName;
    private static int code;

    public static void newInstance(Activity activity, String name, int resultCode) {
        Intent intent = new Intent(activity, UserChangeInfo.class);
        intent.putExtra(USERNAME, name);
         code = resultCode;
        activity.startActivityForResult(intent, resultCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_change_info);
        setIsBindEventBus();
        initData();
        initView();
    }

    private void initData() {
        userName = getIntent().getStringExtra(USERNAME);
    }

    private void initView() {
        edt_user_change = findViewById(R.id.edt_user_change);
        edt_user_change.setText(userName);
    }

    private CommunicationService.MyBinder myBinder;

    @Override
    void onServiceBind(CommunicationService.MyBinder myBinder) {
        this.myBinder = myBinder;
    }

    public void onButtonClick(View view) {
        changName = edt_user_change.getText().toString().trim();
        if (!TextUtils.isEmpty(changName)) {
            if (!changName.equals(userName)) {
                myBinder.upPersonNikName(changName);
            } else {
                MyToast.showShort("修改匿名不能和当前一样");
            }
        } else {
            MyToast.showShort("更换不能为空");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Evnet(ChangeNameRequstInfo info) {
        if (info.getAttr()) {
            MyToast.showShort("匿名修改成功");
            Intent intent = new Intent();
            intent.putExtra("change", changName);
            setResult(RESULT_OK,intent);
            finish();
        } else
            MyToast.showShort("匿名修改失败");

    }
}
