package com.example.yichengxuetang.contract;

import android.annotation.SuppressLint;

import com.example.yichengxuetang.api.ApiService;
import com.example.yichengxuetang.bean.ResetPasswordResponse;
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
public class ResetPasswordContract {

    public static class ResetPasswordPresenter extends BasePresenter<ResetPasswordView> {

        @SuppressLint("CheckResult")
        public void getResetPassword(String mobile,String newPassword){
            ApiService service  = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();
            try {
                json.put("mobile", mobile);
                json.put("newPassword", newPassword);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
            service.getResetPasswordResponse(body).compose(NetworkApi.applySchedulers(new BaseObserver<ResetPasswordResponse>() {
                @Override
                public void onSuccess(ResetPasswordResponse wallPaperResponse) {
                    if (getView() != null) {
                        getView().getResetPasswordResponse(wallPaperResponse);
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

    public interface ResetPasswordView extends BaseView {
        void getResetPasswordResponse(ResetPasswordResponse wallPaperResponse);
    }
}
