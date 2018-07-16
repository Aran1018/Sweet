package com.lucky.sweet;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.lucky.sweet.properties.Properties;

import org.junit.Test;
import org.junit.runner.RunWith;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <test href="http://d.android.com/tools/testing">Testing documentation</test>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.lucky.sweet", appContext.getPackageName());
    }

    @Test
    public void initializeWeatherTest() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.lucky.sweet", appContext.getPackageName());


    }

    //todo 测试用户登陆。详细返回码借鉴Properties
    @Test
    public void TestUserLogin() throws Exception {
        String userName = "chinn96@163.com";
        String psw = "123456a";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(Properties.LOGINPATH)
                .post(new FormBody.Builder().add("username", userName).add("password", psw).build()).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            assertEquals("1", response.body().string());
        }
    }

    //todo 测试用户找回密码。对照返回测试码
    @Test
    public void TestUserForgetPsw() throws Exception {
        String userName = "chinn96@163.com";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(Properties.FORGETVALIDATEPATH)
                .post(new FormBody.Builder().add("username", userName).build()).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            assertEquals("1", response.body().string());
        }
    }


}
