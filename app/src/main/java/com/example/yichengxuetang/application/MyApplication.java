package com.example.yichengxuetang.application;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.example.yichengxuetang.utils.LoadFooterView;
import com.example.yichengxuetang.utils.RefreshHeadView;
import com.kingja.loadsir.core.LoadSir;
import com.llw.mvplibrary.ActivityManager;
import com.llw.mvplibrary.BaseApplication;
import com.llw.mvplibrary.network.NetworkApi;
import com.llw.mvplibrary.network.utils.SpUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zinc.jrecycleview.config.JRecycleViewManager;

import cn.jiguang.verifysdk.api.JVerificationInterface;

public class MyApplication extends BaseApplication {
    public Context mContext;
    public static final String APP_ID = "wxf39af9ce5edcad58";
    public static IWXAPI api;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        //初始化
        NetworkApi.init(new NetworkRequiredInfo(this));
        //极光一键登录初始化,短信验证码
        JVerificationInterface.setDebugMode(true);
        JVerificationInterface.init(this, (code, result) -> Log.d("MyApp", "[init] code = " + code + " result = " + result));
        regToWx();
        SpUtils.remove(this,"msgCode");
        //JRecycleViewManager.getInstance().setLoadMoreView(new LoadFooterView(this));
        //JRecycleViewManager.getInstance().setRefreshLoadView(new RefreshHeadView(this));
    }


    private void regToWx() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, APP_ID, true);
        // 将应用的appId注册到微信
        api.registerApp(APP_ID);

        //建议动态监听微信启动广播进行注册到微信
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // 将该app注册到微信
                api.registerApp(APP_ID);
            }
        }, new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));

    }

    //微信登录
    public static void wxLogin() {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        MyApplication.api.sendReq(req);
    }

    //微信绑定

    public static void wxBinding() {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_binding";
        MyApplication.api.sendReq(req);
    }
}
