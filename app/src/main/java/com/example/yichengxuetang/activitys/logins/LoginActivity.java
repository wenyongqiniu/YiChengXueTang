package com.example.yichengxuetang.activitys.logins;

import androidx.annotation.RequiresApi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.example.yichengxuetang.utils.RSADecrypt;
import com.example.yichengxuetang.utils.ToastUtils;
import com.llw.mvplibrary.BaseApplication;
import com.llw.mvplibrary.mvp.MvpActivity;
import com.llw.mvplibrary.network.utils.SpUtils;
import com.llw.mvplibrary.network.utils.StatusBarUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.jiguang.verifysdk.api.AuthPageEventListener;
import cn.jiguang.verifysdk.api.JVerificationInterface;
import cn.jiguang.verifysdk.api.JVerifyUIConfig;
import cn.jiguang.verifysdk.api.LoginSettings;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends MvpActivity<VcLoginContract.VcLoginPresenter> implements VcLoginContract.VcLoginView {

    private static final String TAG = "JIGUANG";
    private JVerifyUIConfig.Builder uiConfig;
    private String msgCode="";
    private boolean isChecked;
    private LoginSettings settings;


    private void initViewNoSms() {

        TextView tv_phone_login = findViewById(R.id.tv_phone_login);
        CheckBox check = findViewById(R.id.check);
        ImageView iv_wx_login = findViewById(R.id.iv_wx_login);
        String msgCode2 = SpUtils.getSpString(LoginActivity.this, "msgCode", "");
        if ("checkbox checked.".equals(msgCode2)){
           isChecked=true;
        }else{
            isChecked=false;
        }

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
                CustomerToastUtils.toastShow(LoginActivity.this).show();
            }

        });

        //跳转手机号登录
        tv_phone_login.setOnClickListener(v -> {
            if (isChecked) {
                startActivity(new Intent(LoginActivity.this, VcLoginActivity.class));
            } else {
                CustomerToastUtils.toastShow(LoginActivity.this).show();
            }

        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initOneClickLogin() {
        StatusBarUtils.setColor(this,Color.parseColor("#000000"));
        settings = new LoginSettings();
        settings.setAutoFinish(false);//设置登录完成后是否自动关闭授权页
        settings.setTimeout(15 * 1000);//设置超时时间，单位毫秒。 合法范围（0，30000],范围以外默认设置为10000
        settings.setAuthPageEventListener(new AuthPageEventListener() {
            @Override
            public void onEvent(int cmd, String msg) {
                //do something...
                String msgCode2 = SpUtils.getSpString(LoginActivity.this, "msgCode", "");
                if ("checkbox checked.".equals(msgCode2)){
                    msgCode="checkbox checked.";
                }else{
                    msgCode = msg;
                }
                if ("checkbox checked.".equals(msg)){
                    SpUtils.putSpString(LoginActivity.this,"msgCode",msgCode);
                }
                if (!"checkbox checked.".equals(msg)&&"checkbox unchecked.".equals(msg)){
                    SpUtils.remove(LoginActivity.this,"msgCode");
                    msgCode = msg;
                }
            }
        });//设置授权页事件监听
        JVerificationInterface.loginAuth(this, settings, (code, content, operator) -> {
            if (code == 6000) {
                    Log.d(TAG, "code=" + code + ", token=" + content + " ,operator=" + operator);
                    igniteGetPhone(content);
            } else {
                Log.d(TAG, "code=" + code + ", message=" + content);
            }
        });

        Button mBtn = new Button(this);
        mBtn.setText("其他手机号登录");
        mBtn.setTextColor(Color.parseColor("#FE8000"));
        mBtn.setBackgroundResource(R.drawable.login_shape);
        RelativeLayout.LayoutParams mLayoutParams1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        mLayoutParams1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        mLayoutParams1.height = 150;
        mLayoutParams1.setMargins(85, 0, 85, 1000);
        mBtn.setLayoutParams(mLayoutParams1);

        TextView textView1 = new TextView(this);
        textView1.setText("《服务协议》");
        textView1.setTextColor(Color.parseColor("#FE8000"));
        textView1.setTextSize(14);
        RelativeLayout.LayoutParams mLayoutParamsT1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        mLayoutParamsT1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        mLayoutParamsT1.bottomMargin = 700;
        mLayoutParamsT1.setMarginStart(120);
        textView1.setLayoutParams(mLayoutParamsT1);

        TextView textView2 = new TextView(this);
        textView2.setText("《隐私协议》");
        textView2.setTextColor(Color.parseColor("#FE8000"));
        textView2.setTextSize(14);
        RelativeLayout.LayoutParams mLayoutParamsT2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        mLayoutParamsT2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        mLayoutParamsT2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        mLayoutParamsT2.bottomMargin = 700;
        mLayoutParamsT2.leftMargin = 340;
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
        layoutParamImg3.topMargin = 80;
        layoutParamImg3.width = 480;
        layoutParamImg3.height = 171;
        imageView3.setLayoutParams(layoutParamImg3);

        uiConfig = new JVerifyUIConfig.Builder()
                .setLogBtnHeight(50)
                .setLogBtnImgPath("login_phone_shape")
                .setSloganHidden(false)
                .setLogoHidden(true)
                .setStatusBarDarkMode(true)
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
                .setNumFieldOffsetY(180)
                .setLogBtnBottomOffsetY(400)
                .setSloganOffsetY(230)
                .setLogBtnOffsetY(254)
                .setNumberSize(28)
                .setNumberTextBold(true)
                .setNumberFieldOffsetBottomY(520)
                .setSloganBottomOffsetY(500)
                .setPrivacyState(false)
                .setNavTransparent(false)
                .setCheckedImgPath("checked")
                .setUncheckedImgPath("unchecked")
                .setPrivacyTextSize(14)
                .setPrivacyTextCenterGravity(true)
                .setPrivacyWithBookTitleMark(true)
                .setPrivacyOffsetY(250)
                .setPrivacyOffsetX(35)
                .enableHintToast(true, CustomerToastUtils.toastShow(this))
                .setAppPrivacyColor(Color.parseColor("#9E9E9E"), Color.parseColor("#FD9503"))
                .setPrivacyText("已阅读并同意", "", "", "")
                .setPrivacyCheckboxSize(16)
                .setPrivacyCheckboxHidden(false)
                .addCustomView(mBtn, false, (context, view) -> {
                    if (msgCode.equals("checkbox checked.")) {
                        SpUtils.putSpString(LoginActivity.this,"msgCode",msgCode);
                        startActivity(new Intent(LoginActivity.this, VcLoginActivity.class));
                    } else {
                        CustomerToastUtils.toastShow(this).show();
                    }
                })
                .addCustomView(lines, false, (context, view) -> {

                }).addCustomView(lines2, false, (context, view) -> {

                }).addCustomView(textView, false, (context, view) -> {

                }).addCustomView(imageView, false, (context, view) -> {
                    if (msgCode.equals("checkbox checked.")) {
                        MyApplication.wxLogin();
                    } else {

                        CustomerToastUtils.toastShow(this).show();
                    }

                }).addCustomView(imageView3, false, (context, view) -> {

                }).addCustomView(textView1, false, (context, view) -> {

                }).addCustomView(textView2, false, (context, view) -> {

                });
        uiConfig.build();


        JVerificationInterface.setCustomUIWithConfig(uiConfig.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void igniteGetPhone(String content) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(chain -> {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder();
            requestBuilder.addHeader("Authorization", "Basic " + Base64.getUrlEncoder().encodeToString(("11b0645c978128cea97843e9" + ":" + "f4df8977e3e67d82adc1955a").getBytes()));
            Request request = requestBuilder.build();
            return chain.proceed(request);
        });

        Retrofit mRetrofit = new Retrofit.Builder()
                .client(builder.build())
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
                        if (getPhoneResponse != null) {
                            try {
                                OneClickLogin(getPhoneResponse.getPhone());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void OneClickLogin(String phone) throws Exception {
        String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANYXjIrzIZLRH8AyBYdX7f95Kt0q913Oi9TQGvjGmmyP0h0fNWAZS4iZFBCGsjxygyrVQzUgiVmeRmXtiBWqI69f+7W51dvEcSWqs81SroDbYU/ORT9o0b8+NwcQyc6hPDdfONhmf+tjyUOjcN5U4fj5QQ6jYp/9K316821tF4nxAgMBAAECgYAr/+j9gZxrzRjmaiFiwHqEX8WkcLkoCVQJp79zU3XXS/OV0p0oo+o3J6bNArHM5If6nJvUZlx0E6hKOHgYY8AzYVrxuecKZbYIP2qouGO8mYnS/utC+823OXzeEOQ06coUHB1DFV4SCE6Ff5Ox9uoUeAOPPXEc8FJe0LhcBI11QQJBAPwsC0B2ODBy3WhL6NJPJemWLDRzyU6vCzkT6eSnRqYMPEveVZellck1DipPWwFW6NJhC3XxwngJ3nsXFXhpAFkCQQDZV4YKlqhL5J/Gu0p9b9CvahTH1AxPYPfjiB+g+3nvdeOUa5z32OHdjDX3KPjonHdCy5NS1tQaf5ZPzJeSlGNZAkBdzxSwekoU2+Y5smN+OStlZhQzvw8YUk3egeX0xbWmd87GeoNk0piSLUaDvtmf+fDKsodM7Q7nEOTO7ZtzG7yhAkEA0JOsMZEv5dV8eE3ZSNP747vcQYGAZtVaT+Oc5vMvq9zpENDNjRV4fRNH3vBVH3a5BUM1GTTNzLhQ2jgv5h290QJAZ46t6nMhXFwlyXKYwXiwqIN5x9yz0YOBZQyrVydnkXka83mgxaq9lHE8JkIgrdDDEkoVH84M5hMFQvKCOPMvFw==";
        String decrypt = RSADecrypt.decrypt(phone, privateKey);
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(decrypt);
        String mobile = m.replaceAll("").trim();
        String substring = mobile.substring(mobile.length() - 11, mobile.length());
        int loginType = 3;
        mPresenter.getVcCode(substring, loginType, "", "");

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
            SpUtils.putSpString(getApplication(), "token", vcLoginResponse.getData().getToken());
            if (vcLoginResponse.getData().getHasbindWechat() == 1) {//已经绑定过微信
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } else {
                startActivity(new Intent(LoginActivity.this, BindingWechatActivity.class));
            }
        } else {
            ToastUtils.showShort(LoginActivity.this, getString(R.string.login_failed));
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
        ToastUtils.showShort(this, getString(R.string.login_failed));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void initData(Bundle savedInstanceState) {
        BaseApplication.getActivityManager().addActivity(this);
        boolean verifyEnable = JVerificationInterface.checkVerifyEnable(this);
        if (!verifyEnable) {
            initViewNoSms();
            ToastUtils.showShort(this, "当前网络环境不支持认证");
        } else {
            initOneClickLogin();
        }
    }

    @Override
    public int getLayoutId() {

        boolean verifyEnable = JVerificationInterface.checkVerifyEnable(this);
        if (!verifyEnable) {
            return R.layout.activity_no_sms;
        } else {
            return R.layout.activity_login;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // SpUtils.remove(this,"msgCode");
    }

}