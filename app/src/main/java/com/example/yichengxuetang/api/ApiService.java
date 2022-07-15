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
     * 用户信息
     */
    @POST("api/chat/getUserInfo")
    Observable<WallPaperResponse> getWallPaper(@Body RequestBody response);

    //群聊信息
    @GET("api/chat/getGroupInfo")
    @Headers({"Content-Type: application/json", "Cache-Control:public,max-age=120"})
    Observable<GroupInfo> groupInfomation(@QueryMap HashMap<String, String> hashMap);

    //群公告
    @GET("api/chat/getGroupAnnouncement")
    @Headers({"Content-Type: application/json", "Cache-Control:public,max-age=120"})
    Observable<GroupNoticeBean> groupNotice(@QueryMap HashMap<String, String> hashMap);

    //查询毕业考试情况
    @POST("api/studycenter/getOverExamInfo")
    Observable<ExamInfoResponse> getExamInfo(@Body RequestBody body);

    //提交作业答案
    @POST("api/studycenter/resetExamInfo")
    Observable<ResetExamResponse> getResetExam(@Body RequestBody body);

    //查询我的合同
    @POST("api/personal/getContractList")
    Observable<HetongResponse> getHetong(@Body RequestBody body);

    //查询我的班级
    @POST("api/personal/getClassList")
    Observable<MyClassResponse> getMyclass(@Body RequestBody body);

    //查询课后作业信息
    @POST("api/studycenter/getTaskList")
    Observable<HomeWorkResponse> getHomeWork(@Body RequestBody body);

    //提交作业答案
    @POST("api/studycenter/submitTask")
    Observable<CommitSuccessResponse> getSubmitAnswer(@Body RequestBody body);

    //查看答案解析
    @POST("api/studycenter/getOverExamAnalysis")
    Observable<HomeWorkResponse> getExamAnswer(@Body RequestBody body);

    //群聊信息
    @POST("api/chat/groupQueryUser")
    @Headers({"Content-Type: application/json", "Cache-Control:public,max-age=120"})
    Observable<GroupNumberResponse> groupList(@Body RequestBody body);

    //记笔记/删除笔记
    @POST("api/studycenter/deleteNote")
    Observable<CommitSuccessResponse> getDeleteNote(@Body RequestBody body);

    //查询笔记列表
    @POST("api/studycenter/getNoteList")
    Observable<FindNoteResponse> getFindNote(@Body RequestBody body);

    //提交考试答案
    @POST("api/studycenter/submitOverExamTask")
    Observable<CommitSuccessResponse> getSubmitExam(@Body RequestBody body);

    //查询题目
    @POST("api/studycenter/getOverExamTaskInfo")
    Observable<ExamTaskInfoResponse> getExamTaskInfo(@Body RequestBody body);

    /**
     * 获取验证码
     */
    @POST("api/common/sendSmsCode")
    Observable<VcLoginResponse> getVcResponse(@Body RequestBody response);

    //手机号登录
    @POST("api/customer/mobileLogin")
    Observable<GetVcCodeResponse> getVcLoginResponse(@Body RequestBody response);

    //微信登录
    @POST("api/customer/wechatLogin")
    Observable<LoginWxResponse> getLoginWecahtResponse(@Body RequestBody response);

    //绑定微信
    @POST("api/customer/bindWechat")
    Observable<BindingWechatResponse> getBindindWecahtResponse(@Body RequestBody response);

    //重置密码
    @POST("api/customer/resetPassword")
    Observable<ResetPasswordResponse> getResetPasswordResponse(@Body RequestBody response);

    //极光获取手机号接口
    @POST("v1/web/loginTokenVerify")
    Observable<GetPhoneResponse> getPhone(@Body RequestBody body);

    //校验验证码
    @POST("api/common/checkSmsCode")
    Observable<CheckVcCodeResponse> getCheckVcCode(@Body RequestBody body);

    //首页-查询直播和业务列表
    @POST("api/studycenter/index")
    Observable<LearningCenterResponse> getLearningCenter(@Body RequestBody body);

    //首页-查询课程列表
    @POST("api/studycenter/getCourse")
    Observable<ShowCourseListResponse> getShowCourseListCenter(@Body RequestBody body);

    //getContract
    @POST("api/studycenter/getProcessPageInfo")
    Observable<DakeStepResponse> getDakeStep(@Body RequestBody body);

    //合同-查询合同模板
    @POST("api/studycenter/getContractTemlate")
    Observable<ContractResponse> getContract(@Body RequestBody body);

    //合同-签署合同
    @POST("api/studycenter/sealContract")
    Observable<SealContractResponse> getSealContract(@Body RequestBody body);

    //地址-查询用户是否有地址
    @POST("api/studycenter/getAddress")
    Observable<AddressResponse> getAddress(@Body RequestBody body);

    //地址-新增地址
    @POST("api/studycenter/addAddress")
    Observable<AddAddressResponse> getEditAddress(@Body RequestBody body);

    //地址-新增/编辑地址
    @POST("api/studycenter/editAddress")
    Observable<AddAddressResponse> getChangeAddress(@Body RequestBody body);

    //地址-选择收获地址
    @POST("api/studycenter/selectAddress")
    Observable<SubmitAddressResponse> getSubmitAddress(@Body RequestBody body);

    //地址-查询地址列表
    @POST("api/studycenter/getAddressList")
    Observable<AddressListResponse> getAddressList(@Body RequestBody body);

    //地址-删除地址
    @POST("api/studycenter/delAddress")
    Observable<CommitSuccessResponse> getDeleteAddress(@Body RequestBody body);

    //记笔记/修改笔记
    @POST("api/studycenter/editNote")
    Observable<CommitSuccessResponse> getKeepNote(@Body RequestBody body);

    //查询笔记详情（记笔记回显用）
    @POST("api/studycenter/getNoteDetail")
    Observable<NoteDetailResponse> getNoteDetail(@Body RequestBody body);

    //选时间-查询可用时间
    @POST("api/studycenter/getCouseTime")
    Observable<CourseTimeResponse> getCourseTime(@Body RequestBody body);

    //选时间-选择时间
    @POST("api/studycenter/selectCourseTime")
    Observable<ConfirmCourseTineResponse> getSelecttime(@Body RequestBody body);

    //体验课-查询课程信息
    @POST("api/studycenter/getXbCourseInfo")
    Observable<XbCourseListResponse> getXbCourseList(@Body RequestBody body);

    //体验课|进阶课-查询图文小节详情
    @POST("api/studycenter/getTWSectionDetail")
    Observable<SectionDetailResponse> getSectionDetail(@Body RequestBody body);

    //进阶课-查询课程信息
    @POST("api/studycenter/getBigCourseInfo")
    Observable<DkCourseListResponse> getDkCourseList(@Body RequestBody body);

    //手机号登录
    @POST("api/customer/loginForCheck")
    Observable<PhoneLoginResponse> getPhoneLogin(@Body RequestBody body);

    //领取毕业证
    @POST("api/studycenter/getExamCard")
    Observable<StudyCardResponse> getStudyCard(@Body RequestBody body);

}
