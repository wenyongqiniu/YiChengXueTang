package com.example.yichengxuetang.contract;

import android.annotation.SuppressLint;

import com.example.yichengxuetang.api.ApiService;
import com.example.yichengxuetang.bean.CheckVcCodeResponse;
import com.example.yichengxuetang.bean.GetPhoneResponse;
import com.example.yichengxuetang.bean.GetVcCodeResponse;
import com.example.yichengxuetang.bean.VcLoginResponse;
import com.example.yichengxuetang.bean.WallPaperResponse;
import com.llw.mvplibrary.base.BasePresenter;
import com.llw.mvplibrary.base.BaseView;
import com.llw.mvplibrary.network.NetworkApi;
import com.llw.mvplibrary.network.observer.BaseObserver;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.RequestBody;


/**
 * 将V与M订阅起来
 * @author llw
 */
public class VcLoginContract {

    public static class VcLoginPresenter extends BasePresenter<VcLoginView> {

        @SuppressLint("CheckResult")
        public void getVcResponse(String phone){

            ApiService service  = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();
            try {
                json.put("mobile", phone);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
            service.getVcResponse(body).compose(NetworkApi.applySchedulers(new BaseObserver<VcLoginResponse>() {
                @Override
                public void onSuccess(VcLoginResponse vcLoginResponse) {
                    if (getView() != null) {
                        getView().getVcLogin(vcLoginResponse);
                    }
                }

                @Override
                public void onFailure(Throwable e) {
                    if (getView() != null) {
                        getView().getFailed(e);
                    }
                }
            }));
        }

        @SuppressLint("CheckResult")
        public void getVcCode(String phone,int loginType,String smsCode,String password){

            ApiService service  = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();
            try {
                json.put("mobile", phone);
                json.put("loginType", loginType);
                json.put("smsCode", smsCode);
                json.put("password", password);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
            service.getVcLoginResponse(body).compose(NetworkApi.applySchedulers(new BaseObserver<GetVcCodeResponse>() {
                @Override
                public void onSuccess(GetVcCodeResponse vcLoginResponse) {
                    if (getView() != null) {
                        getView().getLogin(vcLoginResponse);
                    }
                }

                @Override
                public void onFailure(Throwable e) {
                    if (getView() != null) {
                        getView().getFailed(e);
                    }
                }
            }));
        }
        @SuppressLint("CheckResult")
        public void getPhone(String content) {
            ApiService service  = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();
            try {
                json.put("loginToken", content);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
            service.getPhone(body).compose(NetworkApi.applySchedulers(new BaseObserver<GetPhoneResponse>() {
                @Override
                public void onSuccess(GetPhoneResponse vcLoginResponse) {
                    if (getView() != null) {
                        getView().getPhone(vcLoginResponse);
                    }
                }

                @Override
                public void onFailure(Throwable e) {
                    if (getView() != null) {
                        getView().getFailed(e);
                    }
                }
            }));
        }
        @SuppressLint("CheckResult")
        public void getCheckVcCode(String phone, String vcCode) {
            ApiService service  = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();
            try {
                json.put("mobile", phone);
                json.put("smsCode", vcCode);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
            service.getCheckVcCode(body).compose(NetworkApi.applySchedulers(new BaseObserver<CheckVcCodeResponse>() {
                @Override
                public void onSuccess(CheckVcCodeResponse vcLoginResponse) {
                    if (getView() != null) {
                        getView().getCheckVcCode(vcLoginResponse);
                    }
                }

                @Override
                public void onFailure(Throwable e) {
                    if (getView() != null) {
                        getView().getFailed(e);
                    }
                }
            }));
        }
    }

    public interface VcLoginView extends BaseView {
        void getVcLogin(VcLoginResponse vcLoginResponse);
        void getLogin(GetVcCodeResponse vcLoginResponse);
        void getPhone(GetPhoneResponse vcLoginResponse);
        void getCheckVcCode(CheckVcCodeResponse vcLoginResponse);
    }
}
