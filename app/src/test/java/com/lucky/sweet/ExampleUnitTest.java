package com.lucky.sweet;

import com.google.gson.Gson;
import com.lucky.sweet.activity.MyApplication;
import com.lucky.sweet.entity.FlowPeople;
import com.lucky.sweet.properties.PersonProperties;
import com.lucky.sweet.utility.HttpUtils;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;

import org.greenrobot.eventbus.EventBus;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine
 * (host).
 *
 * @see <test href="http://d.android.com/tools/testing">Testing documentation</test>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

  /*  @Test
    public void test_Session_outtime() throws Exception {
        HashMap<String, String> map = new HashMap<>();
        map.put("session", "111111");
        HttpUtils.sendOkHttpRequest(PersonProperties.FOLW_FRIEND, new com.zhy.http.okhttp.callback.Callback() {
            @Override
            public Object parseNetworkResponse(com.squareup.okhttp.Response response) throws IOException {
                assertEquals(response.body().string(), "250");
                return null;
            }

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(Object response) {

            }
        }, map);
    }*/
 /*   @Test
    public void loginTest() throws Exception {
        assertEquals(true, LoginRegisterManager.UserLogin("2222@163.com",
                "aaaaa"));
    }

    @Test
    public void EmailExistTest() throws Exception {
        assertEquals(true, EmailUtiliy.checkEmail("chinn96@163.com"));
        assertEquals(0, LoginRegisterManager.CheckOutEmail("chinn96@163.com"));
    }

    @Test
    public void CheckOutEmailTest() throws Exception {
        assertEquals(0, LoginRegisterManager.CheckOutEmailFirPsw
                ("chinn96@163.com", "aaaaaa"));
    }

    @Test
    public void CheckOutUserPswTest() throws Exception {
        assertEquals(0, LoginRegisterManager.RegestUser
                ("chinn96@163.com", "aaaaaa"));
    }   */

}