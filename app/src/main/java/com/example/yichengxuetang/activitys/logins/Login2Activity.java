package com.example.yichengxuetang.activitys.logins;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yichengxuetang.R;
import com.example.yichengxuetang.activitys.MainActivity;
import com.example.yichengxuetang.api.ApiService;
import com.example.yichengxuetang.application.MyApplication;
import com.example.yichengxuetang.bean.CheckVcCodeResponse;
import com.example.yichengxuetang.bean.GetPhoneResponse;
import com.example.yichengxuetang.bean.GetVcCodeResponse;
import com.example.yichengxuetang.bean.VcLoginResponse;
import com.example.yichengxuetang.contract.VcLoginContract;
import com.example.yichengxuetang.utils.CustomerToastUtils;
import com.llw.mvplibrary.mvp.MvpActivity;


import cn.jiguang.verifysdk.api.AuthPageEventListener;
import cn.jiguang.verifysdk.api.JVerificationInterface;
import cn.jiguang.verifysdk.api.JVerifyUIConfig;
import cn.jiguang.verifysdk.api.LoginSettings;



public class Login2Activity extends MvpActivity<VcLoginContract.VcLoginPresenter> implements VcLoginContract.VcLoginView {

    private static final String TAG = "JIGUANG";
    private JVerifyUIConfig uiConfig;
    private String msgCode;
    private boolean isChecked;

   /* @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setColor(this, 0Xfffffff);
        StatusBarUtils.setTextDark(this, true);
        boolean verifyEnable = JVerificationInterface.checkVerifyEnable(this);
        if (!verifyEnable) {
            setContentView(R.layout.activity_no_sms);
            initViewNoSms();
            ToastUtils.showShort(this, "当前网络环境不支持认证");
        } else {
            setContentView(R.layout.activity_login);
            initOneClickLogin();
        }
    }*/


    private void initViewNoSms() {

        TextView tv_phone_login = findViewById(R.id.tv_phone_login);
        CheckBox check = findViewById(R.id.check);
        ImageView iv_wx_login = findViewById(R.id.iv_wx_login);

        //check是否选中监听
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
        //微信登录
        iv_wx_login.setOnClickListener(v -> {
            if (isChecked) {
                MyApplication.wxLogin();
            } else {
                CustomerToastUtils.toastShow(Login2Activity.this);
            }

        });

        //跳转手机号登录
        tv_phone_login.setOnClickListener(v -> {
            if (isChecked) {
                startActivity(new Intent(Login2Activity.this, VcLoginActivity.class));
            } else {
                CustomerToastUtils.toastShow(Login2Activity.this);
            }

        });

    }

    private void initOneClickLogin() {
        LoginSettings settings = new LoginSettings();
        settings.setAutoFinish(true);//设置登录完成后是否自动关闭授权页
        settings.setTimeout(15 * 1000);//设置超时时间，单位毫秒。 合法范围（0，30000],范围以外默认设置为10000
        settings.setAuthPageEventListener(new AuthPageEventListener() {
            @Override
            public void onEvent(int cmd, String msg) {
                //do something...
                msgCode = msg;
            }
        });//设置授权页事件监听
        JVerificationInterface.loginAuth(this, settings, (code, content, operator) -> {
            if (code == 6000) {
                Log.d(TAG, "code=" + code + ", token=" + content + " ,operator=" + operator);
                igniteGetPhone(content);
                startActivity(new Intent(Login2Activity.this, MainActivity.class));
                finish();
            } else {
                Log.d(TAG, "code=" + code + ", message=" + content);
            }
        });

        Button mBtn = new Button(this);
        mBtn.setText("其他手机号登录");
        mBtn.setTextColor(Color.parseColor("#FE8000"));
        mBtn.setBackgroundResource(R.drawable.login_shape);
        RelativeLayout.LayoutParams mLayoutParams1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        mLayoutParams1.height = 150;
        mLayoutParams1.setMargins(85, 1000, 85, 0);
        mBtn.setLayoutParams(mLayoutParams1);

        TextView textView1 = new TextView(this);
        textView1.setText("《服务协议》");
        textView1.setTextColor(Color.parseColor("#FE8000"));
        textView1.setTextSize(11);
        RelativeLayout.LayoutParams mLayoutParamsT1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        mLayoutParamsT1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        mLayoutParamsT1.bottomMargin = 700;
        mLayoutParamsT1.setMarginStart(100);
        textView1.setLayoutParams(mLayoutParamsT1);

        TextView textView2 = new TextView(this);
        textView2.setText("《隐私协议》");
        textView2.setTextColor(Color.parseColor("#FE8000"));
        textView2.setTextSize(11);
        RelativeLayout.LayoutParams mLayoutParamsT2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        mLayoutParamsT2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        mLayoutParamsT2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        mLayoutParamsT2.bottomMargin = 750;
        mLayoutParamsT2.leftMargin = 800;
        textView2.setLayoutParams(mLayoutParamsT2);


        View lines = new View(this);
        lines.setBackgroundColor(Color.parseColor("#E1E1E1"));
        RelativeLayout.LayoutParams layoutParamLines = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParamLines.height = 2;
        layoutParamLines.width = 250;
        layoutParamLines.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        layoutParamLines.bottomMargin = 310;
        layoutParamLines.setMarginStart(150);
        lines.setLayoutParams(layoutParamLines);

        View lines2 = new View(this);
        lines2.setBackgroundColor(Color.parseColor("#E1E1E1"));
        RelativeLayout.LayoutParams layoutParamLines2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParamLines2.height = 2;
        layoutParamLines2.width = 250;
        layoutParamLines2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        layoutParamLines2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        layoutParamLines2.bottomMargin = 310;
        layoutParamLines2.setMarginEnd(150);
        lines2.setLayoutParams(layoutParamLines2);


        TextView textView = new TextView(this);
        textView.setText("其他登录方式");
        textView.setTextColor(Color.parseColor("#212121"));
        textView.setTextSize(13);
        RelativeLayout.LayoutParams layoutParamText = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParamText.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        layoutParamText.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        layoutParamText.bottomMargin = 290;
        textView.setLayoutParams(layoutParamText);

        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(R.mipmap.wxlogin);
        RelativeLayout.LayoutParams layoutParamImg = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParamImg.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        layoutParamImg.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        layoutParamImg.bottomMargin = 150;
        layoutParamImg.width = 100;
        layoutParamImg.height = 100;
        imageView.setLayoutParams(layoutParamImg);


        ImageView imageView3 = new ImageView(this);
        imageView3.setBackgroundResource(R.mipmap.top_login);
        RelativeLayout.LayoutParams layoutParamImg3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParamImg3.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        layoutParamImg3.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        layoutParamImg3.topMargin = 150;
        layoutParamImg3.width = 480;
        layoutParamImg3.height = 171;
        imageView3.setLayoutParams(layoutParamImg3);

        uiConfig = new JVerifyUIConfig.Builder()
                .setLogBtnHeight(50)
                .setLogBtnImgPath("login_phone_shape")
                .setSloganHidden(false)
                .setLogoHidden(true)
                .setStatusBarTransparent(true)
                .setAuthBGImgPath("")
                .setNavColor(0xfffffff)
                .setNavText("")
                .setNavTextColor(0xffffffff)
                .setNumberColor(0xff333333)
                .setLogBtnText("本机号码一键登录")
                .setLogBtnTextColor(0xffffffff)
                .setSloganTextColor(Color.parseColor("#9E9E9E"))
                .setLogoOffsetY(50)
                .setNumFieldOffsetY(170)
                .setSloganOffsetY(230)
                .setLogBtnOffsetY(254)
                .setNumberSize(28)
                .setNumberTextBold(true)
                .setNumberFieldOffsetBottomY(520)
                .setSloganBottomOffsetY(500)
                .setPrivacyState(false)
                .setNavTransparent(false)
                .setPrivacyOffsetY(30)
                .setCheckedImgPath("checked")
                .setUncheckedImgPath("unchecked")
                .setPrivacyTextSize(11)
                .setPrivacyTextCenterGravity(true)
                .setPrivacyWithBookTitleMark(true)
                .setPrivacyOffsetY(250)
                .setPrivacyOffsetX(20)
                //.enableHintToast(true, CustomerToastUtils.toastShow(this))
                .setAppPrivacyColor(Color.parseColor("#9E9E9E"), Color.parseColor("#FD9503"))
                .setPrivacyText("我已阅读并同意", "和", "、", "")
                .setPrivacyCheckboxSize(12)
                .setPrivacyCheckboxHidden(false)
                .addCustomView(mBtn, false, (context, view) -> {
                    if (msgCode.equals("checkbox checked.")) {
                        startActivity(new Intent(Login2Activity.this, VcLoginActivity.class));
                    } else {
                        CustomerToastUtils.toastShow(this);
                    }
                })
                .addCustomView(lines, false, (context, view) -> {

                }).addCustomView(lines2, false, (context, view) -> {

                }).addCustomView(textView, false, (context, view) -> {

                }).addCustomView(imageView, false, (context, view) -> {
                    if (msgCode.equals("checkbox checked.")) {
                        MyApplication.wxLogin();
                    } else {
                        CustomerToastUtils.toastShow(this);
                    }

                }).addCustomView(imageView3, false, (context, view) -> {

                }).addCustomView(textView1, false, (context, view) -> {

                }).addCustomView(textView2, false, (context, view) -> {

                })
                .build();

        JVerificationInterface.setCustomUIWithConfig(uiConfig);
    }

    private void igniteGetPhone(String content) {

       /* Retrofit mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(" https://api.verification.jpush.cn/")
                .build();

        ApiService apiService = mRetrofit.create(ApiService.class);
        JSONObject json = new JSONObject();
        try {
            json.put("loginToken", content);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
        Observable<GetPhoneResponse> observable = apiService.getPhone(body);

        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GetPhoneResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GetPhoneResponse getPhoneResponse) {
                        if (getPhoneResponse!=null){

                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });*/
        mPresenter.getPhone(content);
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

    }

    @Override
    public void getFailed(Throwable e) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initOneClickLogin();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }
}