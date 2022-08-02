package com.example.yichengxuetang.contract;

import android.annotation.SuppressLint;

import com.example.yichengxuetang.api.ApiService;
import com.example.yichengxuetang.bean.CommitSuccessResponse;
import com.example.yichengxuetang.bean.NoteDetailResponse;
import com.example.yichengxuetang.bean.SectionDetailResponse;
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
 *
 * @author llw
 */
public class SectionDetailContract {

    public static class SectionDetailPresenter extends BasePresenter<SectionDetailView> {

        @SuppressLint("CheckResult")
        public void geSectionDetail(String sectionId) {
            ApiService service = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();
            try {
                json.put("sectionId", sectionId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
            service.getSectionDetail(body).compose(NetworkApi.applySchedulers(new BaseObserver<SectionDetailResponse>() {
                @Override
                public void onSuccess(SectionDetailResponse wallPaperResponse) {
                    if (getView() != null) {
                        getView().getSectionDetail(wallPaperResponse);
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
        public void noteDetail(String sectionId) {
            ApiService service = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();
            try {
                json.put("sectionId", sectionId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
            service.getNoteDetail(body).compose(NetworkApi.applySchedulers(new BaseObserver<NoteDetailResponse>() {
                @Override
                public void onSuccess(NoteDetailResponse wallPaperResponse) {
                    if (getView() != null) {
                        getView().getNoteDetail(wallPaperResponse);
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
        public void keepNote(String sectionId, String edContent,String noteId) {
            ApiService service = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();
            try {
                json.put("sectionId", sectionId);
                json.put("noteContent", edContent);
                json.put("noteId", noteId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
            service.getKeepNote(body).compose(NetworkApi.applySchedulers(new BaseObserver<CommitSuccessResponse>() {
                @Override
                public void onSuccess(CommitSuccessResponse wallPaperResponse) {
                    if (getView() != null) {
                        getView().getKeepNote(wallPaperResponse);
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

    public interface SectionDetailView extends BaseView {
        void getSectionDetail(SectionDetailResponse wallPaperResponse);
        void getNoteDetail(NoteDetailResponse wallPaperResponse);
        void getKeepNote(CommitSuccessResponse wallPaperResponse);


    }
}
