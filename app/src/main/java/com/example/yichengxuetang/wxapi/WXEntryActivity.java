package com.example.yichengxuetang.wxapi;



import android.content.Intent;
import android.os.Bundle;


import com.example.yichengxuetang.R;
import com.example.yichengxuetang.activitys.MainActivity;
import com.example.yichengxuetang.application.MyApplication;
import com.example.yichengxuetang.bean.BindingWechatResponse;
import com.example.yichengxuetang.bean.LoginWxResponse;
import com.example.yichengxuetang.contract.BindingWechatContract;
import com.example.yichengxuetang.utils.ToastUtils;
import com.llw.mvplibrary.mvp.MvpActivity;
import com.llw.mvplibrary.network.utils.SpUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;


public class WXEntryActivity extends MvpActivity<BindingWechatContract.BindingWechatPresenter> implements BindingWechatContract.BindingWechatView , IWXAPIEventHandler {




    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        //登录回调
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                String code = ((SendAuth.Resp) baseResp).code;
                if (((SendAuth.Resp) baseResp).state.equals("wechat_binding")){
                    bindingWx(code);
                }else{
                    loginWx(code);
                }
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED://用户拒绝授权
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL://用户取消
                finish();
                break;
            default:
                finish();
                break;
        }
    }

    private void bindingWx(String code) {
        mPresenter.getWecahtBinding(MyApplication.APP_ID,code);
    }

    private void loginWx(String code) {
        mPresenter.getLoginWecahtResponse(MyApplication.APP_ID,code);
    }



    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected BindingWechatContract.BindingWechatPresenter createPresenter() {
        return new BindingWechatContract.BindingWechatPresenter();
    }

    @Override
    public void getWallPaper(BindingWechatResponse wallPaperResponse) {
        if (wallPaperResponse.getCode()==0){//绑定成功
            startActivity(new Intent(WXEntryActivity.this, MainActivity.class));
            finish();
        }else{
            ToastUtils.showShort(WXEntryActivity.this,wallPaperResponse.getMessage()+"");
        }
    }

    @Override
    public void getWxLogin(LoginWxResponse loginWxResponse) {
        if (loginWxResponse.getCode()==0){//登录成功
            SpUtils.putSpString(getApplication(),"token",loginWxResponse.getData().getToken());
            startActivity(new Intent(WXEntryActivity.this, MainActivity.class));
            finish();
        }else{
            ToastUtils.showShort(WXEntryActivity.this,loginWxResponse.getMessage()+"");
        }
    }

    @Override
    public void getFailed(Throwable e) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        MyApplication.api.handleIntent(getIntent(), this);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }
}
