package com.example.yichengxuetang.activitys.logins;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.example.yichengxuetang.R;
import com.example.yichengxuetang.application.MyApplication;
import com.example.yichengxuetang.bean.BindingWechatResponse;
import com.example.yichengxuetang.bean.LoginWxResponse;
import com.example.yichengxuetang.contract.BindingWechatContract;
import com.llw.mvplibrary.mvp.MvpActivity;

import cn.jiguang.verifysdk.api.JVerificationInterface;
import cn.jiguang.verifysdk.api.RequestCallback;

public class BindingWechatActivity  extends MvpActivity<BindingWechatContract.BindingWechatPresenter> implements BindingWechatContract.BindingWechatView  {


    @Override
    protected BindingWechatContract.BindingWechatPresenter createPresenter() {
        return new BindingWechatContract.BindingWechatPresenter();
    }

    @Override
    public void getWallPaper(BindingWechatResponse wallPaperResponse) {

    }

    @Override
    public void getWxLogin(LoginWxResponse loginWxResponse) {

    }

    @Override
    public void getFailed(Throwable e) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }
        JVerificationInterface.dismissLoginAuthActivity(true, (code, desc) -> {

        });
        return false;

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        TextView tv_bind_now = findViewById(R.id.tv_bind_now);
        tv_bind_now.setOnClickListener(v -> MyApplication.wxBinding());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_binding_wechat;
    }
}
