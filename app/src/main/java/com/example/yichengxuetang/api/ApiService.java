package com.example.yichengxuetang.api;


import com.example.yichengxuetang.bean.BindingWechatResponse;
import com.example.yichengxuetang.bean.CheckVcCodeResponse;
import com.example.yichengxuetang.bean.GetPhoneResponse;
import com.example.yichengxuetang.bean.GetVcCodeResponse;
import com.example.yichengxuetang.bean.LearningCenterResponse;
import com.example.yichengxuetang.bean.LoginWxResponse;
import com.example.yichengxuetang.bean.ResetPasswordResponse;
import com.example.yichengxuetang.bean.ShowCourseListResponse;
import com.example.yichengxuetang.bean.VcLoginResponse;
import com.example.yichengxuetang.bean.WallPaperResponse;

import java.util.Base64;
import java.util.function.DoubleUnaryOperator;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;


/**
 * ApiService接口 统一管理应用所有的接口
 *
 * @author llw
 */
public interface ApiService {

    /**
     * 获取热门壁纸列表
     */
    @GET("/v1/vertical/vertical?limit=30&skip=180&adult=false&first=0&order=hot")
    Observable<WallPaperResponse> getWallPaper();

    /**
     * 获取验证码
     *
     * @param phone
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


}
