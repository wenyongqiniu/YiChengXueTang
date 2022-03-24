package com.example.yichengxuetang.activitys.logins;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yichengxuetang.R;
import com.example.yichengxuetang.activitys.MainActivity;
import com.example.yichengxuetang.application.MyApplication;
import com.example.yichengxuetang.bean.CheckVcCodeResponse;
import com.example.yichengxuetang.bean.GetPhoneResponse;
import com.example.yichengxuetang.bean.GetVcCodeResponse;
import com.example.yichengxuetang.bean.VcLoginResponse;
import com.example.yichengxuetang.contract.VcLoginContract;
import com.example.yichengxuetang.utils.PhoneFormatCheckUtils;
import com.example.yichengxuetang.utils.ToastUtils;
import com.llw.mvplibrary.mvp.MvpActivity;
import com.llw.mvplibrary.network.utils.SpUtils;

public class PasswordLoginLoActivity extends MvpActivity<VcLoginContract.VcLoginPresenter> implements VcLoginContract.VcLoginView {

    private boolean isVisibility;
    private String mobile="";
    private String password="";
    private int loginType;

    private void initView() {
        ImageView iv_see = findViewById(R.id.iv_see);
        RelativeLayout rl_out = findViewById(R.id.rl_out);
        TextView tv_vclogin_register = findViewById(R.id.tv_vclogin_register);
        TextView tv_forget_password = findViewById(R.id.tv_forget_password);
        TextView tv_login_sure = findViewById(R.id.tv_login_sure);
        EditText ed_password = findViewById(R.id.ed_password);
        EditText ed_phone = findViewById(R.id.ed_phone);
        ImageView iv_wx_login = findViewById(R.id.iv_wx_login);
        //微信登录
        iv_wx_login.setOnClickListener(v -> {
            MyApplication.wxLogin();
        });

        //验证码。注册
        tv_vclogin_register.setOnClickListener(v -> finish());
        //忘记密码
        tv_forget_password.setOnClickListener(v -> startActivity(new Intent(PasswordLoginLoActivity.this, ForgetPasswordActivity.class)));
        //查看密码
        iv_see.setOnClickListener(v -> {
            Hidden(ed_password);
            if (!isVisibility) {
                iv_see.setBackgroundResource(R.mipmap.hide);
                isVisibility = true;
            } else {
                iv_see.setBackgroundResource(R.mipmap.see_password);
                isVisibility = false;
            }
        });
        ed_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mobile = s.toString();
                if (PhoneFormatCheckUtils.isChinaPhoneLegal(mobile+"")&&(password+"").length()>=8){
                    tv_login_sure.setBackgroundResource(R.drawable.login_phone_shape);
                }else{
                    tv_login_sure.setBackgroundResource(R.drawable.unlogin_shape);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ed_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password = s.toString().trim();
                if (PhoneFormatCheckUtils.isChinaPhoneLegal(mobile+"")&&password.length()>=8){
                    tv_login_sure.setBackgroundResource(R.drawable.login_phone_shape);
                }else{
                    tv_login_sure.setBackgroundResource(R.drawable.unlogin_shape);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_login_sure.setOnClickListener(v -> {
            if (PhoneFormatCheckUtils.isChinaPhoneLegal(mobile)){
                if (password.length()>=8) {
                    loginType = 2;
                    mPresenter.getVcCode(mobile, loginType, "", password);
                }else{
                    ToastUtils.showShort(PasswordLoginLoActivity.this,getString(R.string.password_rule));

                }
            }else{
                ToastUtils.showShort(PasswordLoginLoActivity.this,getString(R.string.input_correct_phone));
            }
        });
        //回退
        rl_out.setOnClickListener(v -> finish());
    }

    /**
     * 查看密码
     *
     * @param v
     */
    public static void Hidden(EditText v) {
        if (v.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            v.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            v.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
    }

    @Override
    protected VcLoginContract.VcLoginPresenter createPresenter() {
        return new VcLoginContract.VcLoginPresenter();
    }

    @Override
    public void getVcLogin(VcLoginResponse vcLoginResponse) {

    }

    @Override
    public void getLogin(GetVcCodeResponse vcLoginResponse) {
        if (vcLoginResponse.getCode() == 0) {
            SpUtils.putSpString(getApplication(),"token",vcLoginResponse.getData().getToken());
            if (vcLoginResponse.getData().getHasbindWechat() == 1) {//已经绑定过微信
                startActivity(new Intent(PasswordLoginLoActivity.this, MainActivity.class));
                finish();
            } else {
                startActivity(new Intent(PasswordLoginLoActivity.this, BindingWechatActivity.class));
            }
        } else {
            ToastUtils.showShort(PasswordLoginLoActivity.this, getString(R.string.login_failed));
        }
    }

    @Override
    public void getPhone(GetPhoneResponse vcLoginResponse) {

    }

    @Override
    public void getCheckVcCode(CheckVcCodeResponse vcLoginResponse) {

    }

    @Override
    public void getFailed(Throwable e) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_password_login_lo;
    }
}