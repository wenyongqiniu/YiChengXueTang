package com.example.yichengxuetang.application;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;


import com.example.yichengxuetang.R;
import com.example.yichengxuetang.mediaplayer.FloatWindow;
import com.example.yichengxuetang.mediaplayer.OpenMusicActivity;
import com.example.yichengxuetang.utils.MediaPlayerHolder;
import com.llw.mvplibrary.ActivityManager;
import com.llw.mvplibrary.network.NetworkApi;
import com.llw.mvplibrary.network.utils.SpUtils;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import cn.jiguang.verifysdk.api.JVerificationInterface;
import io.rong.imkit.RongIM;
import io.rong.imkit.config.RongConfigCenter;
import io.rong.imkit.conversation.extension.RongExtensionManager;
import io.rong.imkit.conversation.messgelist.provider.TextMessageItemProvider;
import io.rong.push.RongPushClient;
import io.rong.push.pushconfig.PushConfig;
import io.rong.sight.SightExtensionModule;

public class MyApplication extends Application {


    public static Context mContext;
    private String appKey = "3argexb63fbie";//融云appkey
    public static MediaPlayerHolder mediaPlayerIngHolder;
    public static LoadingPopupView loadingPopupView;
    public static IWXAPI api;
    public static final String APP_ID = "wxf39af9ce5edcad58";//微信

    public static final String WEB_SOCKET = "ws://testycapi.xicaishe.com/studyrecord/";
    private static ActivityManager activityManager;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        activityManager = new ActivityManager();
        //初始化
        NetworkRequiredInfo networkRequiredInfo = new NetworkRequiredInfo(this);
        boolean debug = networkRequiredInfo.isDebug();
        NetworkApi.isFormal = !debug;
        NetworkApi.init(new NetworkRequiredInfo(this));
        //极光一键登录初始化,短信验证码
        JVerificationInterface.setDebugMode(true);
        JVerificationInterface.init(this, (code, result) -> Log.d("MyApp", "[init] code = " + code + " result = " + result));
        regToWx();
        SpUtils.remove(this, "msgCode");
        //JRecycleViewManager.getInstance().setLoadMoreView(new LoadFooterView(this));
        //JRecycleViewManager.getInstance().setRefreshLoadView(new RefreshHeadView(this));

        //悬浮播放器
        FloatWindow.with(this)//application上下文
                .setLayoutId(R.layout.easy_float)//悬浮布局
                .setFilter(OpenMusicActivity.class)
                .build();

        mediaPlayerIngHolder = new MediaPlayerHolder();

    }


    private void regToWx() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, APP_ID, true);

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
        api.sendReq(req);
    }

    //微信绑定
    public static void wxBinding() {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_binding";
        api.sendReq(req);
    }

    public static ActivityManager getActivityManager() {
        return activityManager;
    }
}
