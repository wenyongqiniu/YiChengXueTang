package com.example.yichengxuetang.activitys.logins;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yichengxuetang.R;
import com.example.yichengxuetang.bean.CheckVcCodeResponse;
import com.example.yichengxuetang.bean.GetPhoneResponse;
import com.example.yichengxuetang.bean.GetVcCodeResponse;
import com.example.yichengxuetang.bean.VcLoginResponse;
import com.example.yichengxuetang.contract.VcLoginContract;
import com.example.yichengxuetang.utils.PhoneFormatCheckUtils;
import com.example.yichengxuetang.utils.RegexUtils;
import com.example.yichengxuetang.utils.ToastUtils;
import com.llw.mvplibrary.mvp.MvpActivity;

public class ForgetPasswordActivity extends MvpActivity<VcLoginContract.VcLoginPresenter> implements VcLoginContract.VcLoginView {
    private String phone = "";
    private String vcCode = "";

    private void initView() {
        TextView tv_get_vc_code = findViewById(R.id.tv_get_vc_code);
        TextView tv_next_step = findViewById(R.id.tv_next_step);
        RelativeLayout rl_out = findViewById(R.id.rl_out);
        ImageView iv_fork = findViewById(R.id.iv_fork);
        EditText ed_phone = findViewById(R.id.ed_phone);
        EditText ed_vc_code = findViewById(R.id.ed_vc_code);

        iv_fork.setOnClickListener(v -> {
            vcCode = "";
            ed_vc_code.setText(vcCode);
        });


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
                if (PhoneFormatCheckUtils.isChinaPhoneLegal(phone)&&vcCode.length() > 0) {
                    tv_next_step.setBackgroundResource(R.drawable.login_phone_shape);
                } else {
                    tv_next_step.setBackgroundResource(R.drawable.unlogin_shape);
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
                if (PhoneFormatCheckUtils.isChinaPhoneLegal(phone)&&vcCode.length() > 0) {
                    iv_fork.setVisibility(View.VISIBLE);
                    tv_next_step.setBackgroundResource(R.drawable.login_phone_shape);
                } else {
                    tv_next_step.setBackgroundResource(R.drawable.unlogin_shape);
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
                ToastUtils.showShort(ForgetPasswordActivity.this, getString(R.string.input_correct_phone));
            }
        });
        //下一步
        tv_next_step.setOnClickListener(v -> {
            if (PhoneFormatCheckUtils.isChinaPhoneLegal(phone)) {
                if (vcCode.length()>0){
                    mPresenter.getCheckVcCode(phone,vcCode);
                }else{
                    ToastUtils.showShort(ForgetPasswordActivity.this, getString(R.string.vccode_rule));
                }
            } else {
                ToastUtils.showShort(ForgetPasswordActivity.this, getString(R.string.input_correct_phone));
            }
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

    }

    @Override
    public void getPhone(GetPhoneResponse vcLoginResponse) {

    }

    @Override
    public void getCheckVcCode(CheckVcCodeResponse vcLoginResponse) {
        if (vcLoginResponse.getCode()==0){
            Intent intent = new Intent(ForgetPasswordActivity.this, RestPasswordActivity.class);
            intent.putExtra("mobile",phone);
            startActivity(intent);
            finish();
        }

    }


    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_forget_password;
    }

    @Override
    public void getFailed(Throwable e) {

    }
}