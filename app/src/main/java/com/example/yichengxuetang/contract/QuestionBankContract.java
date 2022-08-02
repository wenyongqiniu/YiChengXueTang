package com.example.yichengxuetang.contract;

import android.annotation.SuppressLint;

import com.example.yichengxuetang.api.ApiService;
import com.example.yichengxuetang.bean.BatchMenuListSimluResponse;
import com.example.yichengxuetang.bean.BindingWechatResponse;
import com.example.yichengxuetang.bean.CollectQuestionSuccessResponse;
import com.example.yichengxuetang.bean.CommitQuestionAnswerResponse;
import com.example.yichengxuetang.bean.CommitSuccessResponse;
import com.example.yichengxuetang.bean.ExamBranchRsponse;
import com.example.yichengxuetang.bean.InterruptOnlyResponse;
import com.example.yichengxuetang.bean.LearningCenterResponse;
import com.example.yichengxuetang.bean.LoginWxResponse;
import com.example.yichengxuetang.bean.QuestionBankResponse;
import com.example.yichengxuetang.bean.QuestionBankTypeResponse;
import com.example.yichengxuetang.bean.QuestionInfoResponse;
import com.example.yichengxuetang.bean.QuestionListResponse;
import com.example.yichengxuetang.bean.QuestionMuluResponse;
import com.google.gson.Gson;
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
public class QuestionBankContract {

    public static class QuestionBankPresenter extends BasePresenter<QuestionBankView> {

        @SuppressLint("CheckResult")
        public void getQuestionBank(String queryType, String busiTypeCode, String parentId) {
            ApiService service = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();
            try {
                json.put("queryType", queryType);
                json.put("busiTypeCode", busiTypeCode);
                json.put("parentId", parentId);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
            service.getQuestionBankResponse(body).compose(NetworkApi.applySchedulers(new BaseObserver<QuestionBankResponse>() {
                @Override
                public void onSuccess(QuestionBankResponse wallPaperResponse) {
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
        public void getPauseBatch(String batchNo, String totalTime) {
            ApiService service = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();
            try {
                json.put("batchNo", batchNo);
                json.put("totalTime", totalTime);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
            service.getPauseTime(body).compose(NetworkApi.applySchedulers(new BaseObserver<CommitSuccessResponse>() {
                @Override
                public void onSuccess(CommitSuccessResponse wallPaperResponse) {
                    if (getView() != null) {
                        getView().getPauseTime(wallPaperResponse);
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
        public void getOnlyResult(String questionTypeId) {
            ApiService service = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();
            try {
                json.put("questionTypeId", questionTypeId);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
            service.getOnlyResult(body).compose(NetworkApi.applySchedulers(new BaseObserver<InterruptOnlyResponse>() {
                @Override
                public void onSuccess(InterruptOnlyResponse wallPaperResponse) {
                    if (getView() != null) {
                        getView().getInterrputOnly(wallPaperResponse);
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
        public void getInterruptOnly(String batchNo, String totalTime) {
            ApiService service = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();
            try {
                json.put("batchNo", batchNo);
                json.put("totalTime", totalTime);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
            service.getInterruptOnly(body).compose(NetworkApi.applySchedulers(new BaseObserver<InterruptOnlyResponse>() {
                @Override
                public void onSuccess(InterruptOnlyResponse wallPaperResponse) {
                    if (getView() != null) {
                        getView().getInterrputOnly(wallPaperResponse);
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
        public void getQuestionBankType() {
            ApiService service = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();

            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());

            service.getQuestionBankType(body).compose(NetworkApi.applySchedulers(new BaseObserver<QuestionBankTypeResponse>() {
                @Override
                public void onSuccess(QuestionBankTypeResponse wallPaperResponse) {
                    if (getView() != null) {
                        getView().getLearningCenter(wallPaperResponse);
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
        public void getQuestionMulu(String batchNo) {
            ApiService service = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();
            try {
                json.put("batchNo", batchNo);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());

            service.getQuestionMulu(body).compose(NetworkApi.applySchedulers(new BaseObserver<QuestionMuluResponse>() {
                @Override
                public void onSuccess(QuestionMuluResponse wallPaperResponse) {
                    if (getView() != null) {
                        getView().getQuestionMulu(wallPaperResponse);
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
        public void getQuestionMuluSimlu(String batchNo) {
            ApiService service = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();
            try {
                json.put("batchNo", batchNo);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());

            service.getQuestionMuluSimlu(body).compose(NetworkApi.applySchedulers(new BaseObserver<BatchMenuListSimluResponse>() {
                @Override
                public void onSuccess(BatchMenuListSimluResponse wallPaperResponse) {
                    if (getView() != null) {
                        getView().getQuestionMuluSimlu(wallPaperResponse);
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
        public void getExamBatch(String courseType, int examType, String typeId,boolean newBatch) {
            ApiService service = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();
            try {
                json.put("courseType", courseType);
                json.put("examType", examType);
                json.put("typeId", typeId);
                json.put("newBatch", newBatch);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());

            service.getExamBatch(body).compose(NetworkApi.applySchedulers(new BaseObserver<ExamBranchRsponse>() {
                @Override
                public void onSuccess(ExamBranchRsponse wallPaperResponse) {
                    if (getView() != null) {
                        getView().getExamBranch(wallPaperResponse);
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
        public void getQuestionInfo(String batchNo, String questionId) {
            ApiService service = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();
            try {
                json.put("batchNo", batchNo);
                json.put("questionId", questionId);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());

            service.getQuestionInfo(body).compose(NetworkApi.applySchedulers(new BaseObserver<QuestionInfoResponse>() {
                @Override
                public void onSuccess(QuestionInfoResponse wallPaperResponse) {
                    if (getView() != null) {
                        getView().getQuestionInfo(wallPaperResponse);
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
        public void getQuestionInfoList(String batchNo, int pageNo, int pageSize) {
            ApiService service = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();
            try {
                json.put("batchNo", batchNo);
                json.put("pageNo", pageNo);
                json.put("pageSize", pageSize);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());

            service.getQuestionInfoList(body).compose(NetworkApi.applySchedulers(new BaseObserver<QuestionListResponse>() {
                @Override
                public void onSuccess(QuestionListResponse wallPaperResponse) {
                    if (getView() != null) {
                        getView().getQuestionInfoList(wallPaperResponse);
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
        public void getCollectQuestion(String questionId, int operate) {
            ApiService service = NetworkApi.createService(ApiService.class);
            JSONObject json = new JSONObject();
            try {
                json.put("questionId", questionId);
                json.put("operate", operate);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());

            service.getQuestionCollect(body).compose(NetworkApi.applySchedulers(new BaseObserver<CollectQuestionSuccessResponse>() {
                @Override
                public void onSuccess(CollectQuestionSuccessResponse wallPaperResponse) {
                    if (getView() != null) {
                        getView().getQuestionCollect(wallPaperResponse);
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
        public void getSubmitQuestionAnswer(CommitQuestionAnswerResponse commitQuestionAnswerResponse) {
            ApiService service = NetworkApi.createService(ApiService.class);

            String s = new Gson().toJson(commitQuestionAnswerResponse);

            RequestBody body = FormBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), s);

            service.getQuestionSubmit(body).compose(NetworkApi.applySchedulers(new BaseObserver<CommitSuccessResponse>() {
                @Override
                public void onSuccess(CommitSuccessResponse wallPaperResponse) {
                    if (getView() != null) {
                        getView().getQuestionSubmit(wallPaperResponse);
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

    public interface QuestionBankView extends BaseView {
        void getWallPaper(QuestionBankResponse wallPaperResponse);

        void getLearningCenter(QuestionBankTypeResponse wallPaperResponse);

        void getQuestionInfo(QuestionInfoResponse wallPaperResponse);
        void getQuestionInfoList(QuestionListResponse wallPaperResponse);

        void getExamBranch(ExamBranchRsponse wallPaperResponse);

        void getQuestionSubmit(CommitSuccessResponse wallPaperResponse);
        void getPauseTime(CommitSuccessResponse wallPaperResponse);

        void getQuestionCollect(CollectQuestionSuccessResponse wallPaperResponse);

        void getQuestionMulu(QuestionMuluResponse wallPaperResponse);

        void getQuestionMuluSimlu(BatchMenuListSimluResponse wallPaperResponse);

        void getInterrputOnly(InterruptOnlyResponse wallPaperResponse);

    }
}
