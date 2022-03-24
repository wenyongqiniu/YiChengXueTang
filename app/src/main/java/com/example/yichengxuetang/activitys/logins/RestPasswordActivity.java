package com.example.yichengxuetang.activitys.logins;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yichengxuetang.R;
import com.example.yichengxuetang.activitys.MainActivity;
import com.example.yichengxuetang.bean.ResetPasswordResponse;
import com.example.yichengxuetang.contract.ResetPasswordContract;
import com.example.yichengxuetang.utils.PhoneFormatCheckUtils;
import com.example.yichengxuetang.utils.ToastUtils;
import com.llw.mvplibrary.mvp.MvpActivity;
import com.llw.mvplibrary.network.utils.SpUtils;

public class RestPasswordActivity extends MvpActivity<ResetPasswordContract.ResetPasswordPresenter> implements ResetPasswordContract.ResetPasswordView {
    private boolean isVisibility;
    private String newPassword="";

    private void initView() {
        ImageView iv_see = findViewById(R.id.iv_see);
        ImageView left_out = findViewById(R.id.left_out);
        EditText ed_vc_code = findViewById(R.id.ed_vc_code);
        TextView tv_reset_password_sure = findViewById(R.id.tv_reset_password_sure);
        Intent intent = getIntent();
        String mobile = intent.getStringExtra("mobile");

        //查看密码
        iv_see.setOnClickListener(v -> {
            Hidden(ed_vc_code);
            if (!isVisibility) {
                iv_see.setBackgroundResource(R.mipmap.hide);
                isVisibility = true;
            } else {
                iv_see.setBackgroundResource(R.mipmap.see_password);
                isVisibility = false;
            }
        });
        //新密码监听
        ed_vc_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newPassword=s.toString();
                if (newPassword.length()>=8){
                    tv_reset_password_sure.setBackgroundResource(R.drawable.login_phone_shape);
                }else{
                    tv_reset_password_sure.setBackgroundResource(R.drawable.unlogin_shape);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //确认修改密码
        tv_reset_password_sure.setOnClickListener(v -> {
            if (PhoneFormatCheckUtils.isChinaPhoneLegal(mobile)&&newPassword.length()>=8&&isContainAll(newPassword)){
                mPresenter.getResetPassword(mobile, newPassword);
            }else{
                ToastUtils.showShort(context,"密码必须包含数字和字母");
            }
        });
        //回退
        left_out.setOnClickListener(v -> finish());
    }


    /**
     * 规则3：必须同时包含大小写字母及数字
     * 是否包含
     *
     * @param str
     * @return
     */
    public static boolean isContainAll(String str) {
        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLowerCase = false;//定义一个boolean值，用来表示是否包含字母
        boolean isUpperCase = false;
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            } else if (Character.isLowerCase(str.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
                isLowerCase = true;
            } else if (Character.isUpperCase(str.charAt(i))) {
                isUpperCase = true;
            }
        }
        String regex = "^[a-zA-Z0-9]+$";
        boolean isRight = isDigit && (isLowerCase || isUpperCase )&& str.matches(regex);
        return isRight;
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
    protected ResetPasswordContract.ResetPasswordPresenter createPresenter() {
        return new ResetPasswordContract.ResetPasswordPresenter();
    }

    @Override
    public void getResetPasswordResponse(ResetPasswordResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {
            SpUtils.putSpString(getApplication(),"token",wallPaperResponse.getData().getToken());
            if (wallPaperResponse.getData().getHasbindWechat() == 1) {//已经绑定过微信
                startActivity(new Intent(RestPasswordActivity.this, MainActivity.class));
                finish();
            } else {
                startActivity(new Intent(RestPasswordActivity.this, BindingWechatActivity.class));
            }
        } else {
            ToastUtils.showShort(RestPasswordActivity.this, getString(R.string.login_failed));
        }

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
        return R.layout.activity_rest_password;
    }
}