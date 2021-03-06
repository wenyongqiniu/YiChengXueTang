package com.example.yichengxuetang.api;


import com.example.yichengxuetang.bean.AddAddressResponse;
import com.example.yichengxuetang.bean.AddressListResponse;
import com.example.yichengxuetang.bean.AddressResponse;
import com.example.yichengxuetang.bean.BindingWechatResponse;
import com.example.yichengxuetang.bean.CheckVcCodeResponse;
import com.example.yichengxuetang.bean.CommitSuccessResponse;
import com.example.yichengxuetang.bean.ConfirmCourseTineResponse;
import com.example.yichengxuetang.bean.ContractResponse;
import com.example.yichengxuetang.bean.CourseTimeResponse;
import com.example.yichengxuetang.bean.DakeStepResponse;
import com.example.yichengxuetang.bean.DkCourseListResponse;
import com.example.yichengxuetang.bean.ExamBranchRsponse;
import com.example.yichengxuetang.bean.ExamInfoResponse;
import com.example.yichengxuetang.bean.ExamTaskInfoResponse;
import com.example.yichengxuetang.bean.FindNoteResponse;
import com.example.yichengxuetang.bean.GetPhoneResponse;
import com.example.yichengxuetang.bean.GetVcCodeResponse;
import com.example.yichengxuetang.bean.GroupInfo;
import com.example.yichengxuetang.bean.GroupNoticeBean;
import com.example.yichengxuetang.bean.GroupNumberResponse;
import com.example.yichengxuetang.bean.HetongResponse;
import com.example.yichengxuetang.bean.HomeWorkResponse;
import com.example.yichengxuetang.bean.LearningCenterResponse;
import com.example.yichengxuetang.bean.LoginWxResponse;
import com.example.yichengxuetang.bean.MyClassResponse;
import com.example.yichengxuetang.bean.NoteDetailResponse;
import com.example.yichengxuetang.bean.PhoneLoginResponse;
import com.example.yichengxuetang.bean.QuestionBankResponse;
import com.example.yichengxuetang.bean.QuestionBankTypeResponse;
import com.example.yichengxuetang.bean.QuestionInfoResponse;
import com.example.yichengxuetang.bean.ResetExamResponse;
import com.example.yichengxuetang.bean.ResetPasswordResponse;
import com.example.yichengxuetang.bean.SealContractResponse;
import com.example.yichengxuetang.bean.SectionDetailResponse;
import com.example.yichengxuetang.bean.ShowCourseListResponse;
import com.example.yichengxuetang.bean.StudyCardResponse;
import com.example.yichengxuetang.bean.SubmitAddressResponse;
import com.example.yichengxuetang.bean.VcLoginResponse;
import com.example.yichengxuetang.bean.WallPaperResponse;
import com.example.yichengxuetang.bean.XbCourseListResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface ApiService {

    /**
     * ????????????
     */
    @POST("api/chat/getUserInfo")
    Observable<WallPaperResponse> getWallPaper(@Body RequestBody response);

    //????????????
    @GET("api/chat/getGroupInfo")
    @Headers({"Content-Type: application/json", "Cache-Control:public,max-age=120"})
    Observable<GroupInfo> groupInfomation(@QueryMap HashMap<String, String> hashMap);

    //?????????
    @GET("api/chat/getGroupAnnouncement")
    @Headers({"Content-Type: application/json", "Cache-Control:public,max-age=120"})
    Observable<GroupNoticeBean> groupNotice(@QueryMap HashMap<String, String> hashMap);

    //????????????????????????
    @POST("api/studycenter/getOverExamInfo")
    Observable<ExamInfoResponse> getExamInfo(@Body RequestBody body);

    //??????????????????
    @POST("api/studycenter/resetExamInfo")
    Observable<ResetExamResponse> getResetExam(@Body RequestBody body);

    //??????????????????
    @POST("api/personal/getContractList")
    Observable<HetongResponse> getHetong(@Body RequestBody body);

    //??????????????????
    @POST("api/personal/getClassList")
    Observable<MyClassResponse> getMyclass(@Body RequestBody body);

    //????????????????????????
    @POST("api/studycenter/getTaskList")
    Observable<HomeWorkResponse> getHomeWork(@Body RequestBody body);

    //??????????????????
    @POST("api/studycenter/submitTask")
    Observable<CommitSuccessResponse> getSubmitAnswer(@Body RequestBody body);

    //??????????????????
    @POST("api/studycenter/getOverExamAnalysis")
    Observable<HomeWorkResponse> getExamAnswer(@Body RequestBody body);

    //????????????
    @POST("api/chat/groupQueryUser")
    @Headers({"Content-Type: application/json", "Cache-Control:public,max-age=120"})
    Observable<GroupNumberResponse> groupList(@Body RequestBody body);

    //?????????/????????????
    @POST("api/studycenter/deleteNote")
    Observable<CommitSuccessResponse> getDeleteNote(@Body RequestBody body);

    //??????????????????
    @POST("api/studycenter/getNoteList")
    Observable<FindNoteResponse> getFindNote(@Body RequestBody body);

    //??????????????????
    @POST("api/studycenter/submitOverExamTask")
    Observable<CommitSuccessResponse> getSubmitExam(@Body RequestBody body);

    //????????????
    @POST("api/studycenter/getOverExamTaskInfo")
    Observable<ExamTaskInfoResponse> getExamTaskInfo(@Body RequestBody body);

    /**
     * ???????????????
     */
    @POST("api/common/sendSmsCode")
    Observable<VcLoginResponse> getVcResponse(@Body RequestBody response);

    //???????????????
    @POST("api/customer/mobileLogin")
    Observable<GetVcCodeResponse> getVcLoginResponse(@Body RequestBody response);

    //????????????
    @POST("api/customer/wechatLogin")
    Observable<LoginWxResponse> getLoginWecahtResponse(@Body RequestBody response);

    //????????????
    @POST("api/customer/bindWechat")
    Observable<BindingWechatResponse> getBindindWecahtResponse(@Body RequestBody response);

    //????????????
    @POST("api/customer/resetPassword")
    Observable<ResetPasswordResponse> getResetPasswordResponse(@Body RequestBody response);

    //???????????????????????????
    @POST("v1/web/loginTokenVerify")
    Observable<GetPhoneResponse> getPhone(@Body RequestBody body);

    //???????????????
    @POST("api/common/checkSmsCode")
    Observable<CheckVcCodeResponse> getCheckVcCode(@Body RequestBody body);

    //??????-???????????????????????????
    @POST("api/studycenter/index")
    Observable<LearningCenterResponse> getLearningCenter(@Body RequestBody body);

    //??????-????????????
    @POST("api/exam/getCourseTypeList")
    Observable<QuestionBankTypeResponse> getQuestionBankType(@Body RequestBody body);

    //??????-??????????????????
    @POST("api/exam/getExamBatch")
    Observable<ExamBranchRsponse> getExamBatch(@Body RequestBody body);

    //??????-??????????????????
    @POST("api/exam/getQusetionInfo")
    Observable<QuestionInfoResponse> getQuestionInfo(@Body RequestBody body);

    //??????-??????????????????
    @POST("api/studycenter/getCourse")
    Observable<ShowCourseListResponse> getShowCourseListCenter(@Body RequestBody body);

    //getContract
    @POST("api/studycenter/getProcessPageInfo")
    Observable<DakeStepResponse> getDakeStep(@Body RequestBody body);

    //??????-??????????????????
    @POST("api/studycenter/getContractTemlate")
    Observable<ContractResponse> getContract(@Body RequestBody body);

    //??????-????????????
    @POST("api/studycenter/sealContract")
    Observable<SealContractResponse> getSealContract(@Body RequestBody body);

    //??????-???????????????????????????
    @POST("api/studycenter/getAddress")
    Observable<AddressResponse> getAddress(@Body RequestBody body);

    //??????-????????????
    @POST("api/studycenter/addAddress")
    Observable<AddAddressResponse> getEditAddress(@Body RequestBody body);

    //??????-??????/????????????
    @POST("api/studycenter/editAddress")
    Observable<AddAddressResponse> getChangeAddress(@Body RequestBody body);

    //??????-??????????????????
    @POST("api/studycenter/selectAddress")
    Observable<SubmitAddressResponse> getSubmitAddress(@Body RequestBody body);

    //??????-??????????????????
    @POST("api/studycenter/getAddressList")
    Observable<AddressListResponse> getAddressList(@Body RequestBody body);

    //??????-????????????
    @POST("api/studycenter/delAddress")
    Observable<CommitSuccessResponse> getDeleteAddress(@Body RequestBody body);

    //?????????/????????????
    @POST("api/studycenter/editNote")
    Observable<CommitSuccessResponse> getKeepNote(@Body RequestBody body);

    //??????????????????????????????????????????
    @POST("api/studycenter/getNoteDetail")
    Observable<NoteDetailResponse> getNoteDetail(@Body RequestBody body);

    //?????????-??????????????????
    @POST("api/studycenter/getCouseTime")
    Observable<CourseTimeResponse> getCourseTime(@Body RequestBody body);

    //?????????-????????????
    @POST("api/studycenter/selectCourseTime")
    Observable<ConfirmCourseTineResponse> getSelecttime(@Body RequestBody body);

    //?????????-??????????????????
    @POST("api/studycenter/getXbCourseInfo")
    Observable<XbCourseListResponse> getXbCourseList(@Body RequestBody body);

    //?????????|?????????-????????????????????????
    @POST("api/studycenter/getTWSectionDetail")
    Observable<SectionDetailResponse> getSectionDetail(@Body RequestBody body);

    //?????????-??????????????????
    @POST("api/studycenter/getBigCourseInfo")
    Observable<DkCourseListResponse> getDkCourseList(@Body RequestBody body);

    //???????????????
    @POST("api/customer/loginForCheck")
    Observable<PhoneLoginResponse> getPhoneLogin(@Body RequestBody body);

    //???????????????
    @POST("api/studycenter/getExamCard")
    Observable<StudyCardResponse> getStudyCard(@Body RequestBody body);

    //??????????????????
    @POST("api/exam/getExamQuestionTypeList")
    Observable<QuestionBankResponse> getQuestionBankResponse(@Body RequestBody body);

}
