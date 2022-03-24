package com.example.yichengxuetang.activitys.logins;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
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
import com.example.yichengxuetang.utils.CustomerToastUtils;
import com.example.yichengxuetang.utils.PhoneFormatCheckUtils;
import com.example.yichengxuetang.utils.RegexUtils;
import com.example.yichengxuetang.utils.ToastUtils;
import com.llw.mvplibrary.mvp.MvpActivity;
import com.llw.mvplibrary.network.utils.SpUtils;


public class VcLoginActivity extends MvpActivity<VcLoginContract.VcLoginPresenter> implements VcLoginContract.VcLoginView {

    private String phone = "";
    private String vcCode = "";
    private boolean isChecked;
    private int loginType;

    private void initView() {
        TextView tv_get_vc_code = findViewById(R.id.tv_get_vc_code);
        TextView tv_login_sure = findViewById(R.id.tv_login_sure);
        TextView tv_use_password = findViewById(R.id.tv_use_password);
        RelativeLayout rl_out = findViewById(R.id.rl_out);
        ImageView iv_fork = findViewById(R.id.iv_fork);
        ImageView iv_wx_login = findViewById(R.id.iv_wx_login);
        EditText ed_phone = findViewById(R.id.ed_phone);
        EditText ed_vc_code = findViewById(R.id.ed_vc_code);
        CheckBox check = findViewById(R.id.check);

        iv_fork.setOnClickListener(v -> {
            vcCode = "";
            ed_vc_code.setText(vcCode);
        });
        tv_use_password.setOnClickListener(v -> startActivity(new Intent(VcLoginActivity.this, PasswordLoginLoActivity.class)));


        CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tv_get_vc_code.setText("重新发送(" + (millisUntilFinished / 1000) + "S)");
                tv_get_vc_code.setEnabled(false);
                tv_get_vc_code.setTextColor(Color.parseColor("#C7C7C7"));
            }

            @Override
            public void onFinish() {
                tv_get_vc_code.setEnabled(true);
                tv_get_vc_code.setText("获取验证码");
                tv_get_vc_code.setTextColor(Color.parseColor("#FE8000"));
            }
        };

        check.setOnClickListener(v -> {
            if (!isChecked) {
                check.setChecked(true);
                check.setBackgroundResource(R.drawable.checked);
                isChecked = true;
            } else {
                check.setChecked(false);
                check.setBackgroundResource(R.drawable.unchecked);
                isChecked = false;
            }
        });
        ed_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                phone = s.toString();
                if (PhoneFormatCheckUtils.isChinaPhoneLegal(phone)) {
                    tv_get_vc_code.setTextColor(Color.parseColor("#FE8000"));

                } else {
                    tv_get_vc_code.setTextColor(Color.parseColor("#C7C7C7"));

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ed_vc_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                vcCode = s.toString();
                if (vcCode.length() > 0) {
                    iv_fork.setVisibility(View.VISIBLE);
                    tv_login_sure.setBackgroundResource(R.drawable.login_phone_shape);

                } else {
                    tv_login_sure.setBackgroundResource(R.drawable.unlogin_shape);
                    iv_fork.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        rl_out.setOnClickListener(v ->
                finish()
        );

        //获取验证码
        tv_get_vc_code.setOnClickListener(v -> {
            if (RegexUtils.isBasePhone(phone)) {
                countDownTimer.start();
                mPresenter.getVcResponse(phone);
                ed_vc_code.setFocusable(true);
                ed_vc_code.setFocusableInTouchMode(true);
                ed_vc_code.requestFocus();//获取焦点 光标出现
            } else {
                ToastUtils.showShort(VcLoginActivity.this, getString(R.string.input_correct_phone));
            }
        });
        //登录,loginType 2密码登录，1验证码登录
        tv_login_sure.setOnClickListener(v -> {
            if (PhoneFormatCheckUtils.isChinaPhoneLegal(phone)) {
                if (vcCode.length()>0){
                    loginType = 1;
                    mPresenter.getVcCode(phone, loginType, vcCode, "");
                }else{
                    ToastUtils.showShort(VcLoginActivity.this, getString(R.string.vccode_rule));
                }
            } else {
                ToastUtils.showShort(VcLoginActivity.this, getString(R.string.input_phone));
            }

        });

        //微信登录
        iv_wx_login.setOnClickListener(v -> {
            MyApplication.wxLogin();
        });

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
                startActivity(new Intent(VcLoginActivity.this, MainActivity.class));
                finish();
            } else {
                startActivity(new Intent(VcLoginActivity.this, BindingWechatActivity.class));
            }
        } else {
            ToastUtils.showShort(VcLoginActivity.this, getString(R.string.login_failed));
        }
    }

    @Override
    public void getPhone(GetPhoneResponse vcLoginResponse) {

    }

    @Override
    public void getCheckVcCode(CheckVcCodeResponse vcLoginResponse) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_vc_login;
    }

    @Override
    public void getFailed(Throwable e) {

    }
}