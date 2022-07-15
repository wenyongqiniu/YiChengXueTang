package com.example.yichengxuetang.contract;

import android.annotation.SuppressLint;

import com.example.yichengxuetang.api.ApiService;
import com.example.yichengxuetang.bean.GroupInfo;
import com.example.yichengxuetang.bean.GroupNoticeBean;
import com.example.yichengxuetang.bean.WallPaperResponse;
import com.llw.mvplibrary.base.BasePresenter;
import com.llw.mvplibrary.base.BaseView;
import com.llw.mvplibrary.network.NetworkApi;
import com.llw.mvplibrary.network.observer.BaseObserver;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.RequestBody;


/**
 * 将V与M订阅起来
 *
 * @author llw
 */
public class MainContract {

    public static class MainPresenter extends BasePresenter<IMainView> {

        @SuppressLint("CheckResult")
        public void getWallPaper(String customerId) {
            ApiService service = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();
            try {
                json.put("customerId", customerId);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());

            service.getWallPaper(body).compose(NetworkApi.applySchedulers(new BaseObserver<WallPaperResponse>() {
                @Override
                public void onSuccess(WallPaperResponse wallPaperResponse) {
                    if (getView() != null) {
                        getView().getWallPaper(wallPaperResponse);
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
        public void getGroupInfo(String groupId) {
            ApiService service = NetworkApi.createService(ApiService.class);
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("groupId", groupId);
            service.groupInfomation(hashMap).compose(NetworkApi.applySchedulers(new BaseObserver<GroupInfo>() {
                @Override
                public void onSuccess(GroupInfo wallPaperResponse) {
                    if (getView() != null) {
                        getView().getGroupInfo(wallPaperResponse);
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
        public void getNotice(String groupId) {
            ApiService service = NetworkApi.createService(ApiService.class);
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("groupId", groupId);
            service.groupNotice(hashMap).compose(NetworkApi.applySchedulers(new BaseObserver<GroupNoticeBean>() {
                @Override
                public void onSuccess(GroupNoticeBean wallPaperResponse) {
                    if (getView() != null) {
                        getView().getGroupNotice(wallPaperResponse);
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

    public interface IMainView extends BaseView {
        void getWallPaper(WallPaperResponse wallPaperResponse);

        void getGroupInfo(GroupInfo groupInfo);

        void getGroupNotice(GroupNoticeBean groupInfo);
    }
}
