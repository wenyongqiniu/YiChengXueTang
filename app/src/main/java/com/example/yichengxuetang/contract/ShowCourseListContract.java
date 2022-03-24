package com.example.yichengxuetang.contract;

import android.annotation.SuppressLint;

import com.example.yichengxuetang.api.ApiService;
import com.example.yichengxuetang.bean.LearningCenterResponse;
import com.example.yichengxuetang.bean.ShowCourseListResponse;
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
public class ShowCourseListContract {

    public static class ShowCourseListPresenter extends BasePresenter<ShowCourseListView> {

        @SuppressLint("CheckResult")
        public void getShowCourseListPaper(String typeCode){
            ApiService service  = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();
            try {
                json.put("typeCode",typeCode);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
            service.getShowCourseListCenter(body).compose(NetworkApi.applySchedulers(new BaseObserver<ShowCourseListResponse>() {
                @Override
                public void onSuccess(ShowCourseListResponse wallPaperResponse) {
                    if (getView() != null) {
                        getView().getShowCourseListCenter(wallPaperResponse);
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

    public interface ShowCourseListView extends BaseView {
        void getShowCourseListCenter(ShowCourseListResponse wallPaperResponse);
    }
}
