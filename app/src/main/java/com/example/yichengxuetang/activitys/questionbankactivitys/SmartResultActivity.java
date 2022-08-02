package com.example.yichengxuetang.activitys.questionbankactivitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yichengxuetang.R;
import com.example.yichengxuetang.bean.BatchMenuListSimluResponse;
import com.example.yichengxuetang.bean.CollectQuestionSuccessResponse;
import com.example.yichengxuetang.bean.CommitSuccessResponse;
import com.example.yichengxuetang.bean.ExamBranchRsponse;
import com.example.yichengxuetang.bean.InterruptOnlyResponse;
import com.example.yichengxuetang.bean.QuestionBankResponse;
import com.example.yichengxuetang.bean.QuestionBankTypeResponse;
import com.example.yichengxuetang.bean.QuestionInfoResponse;
import com.example.yichengxuetang.bean.QuestionListResponse;
import com.example.yichengxuetang.bean.QuestionMuluResponse;
import com.example.yichengxuetang.contract.QuestionBankContract;
import com.example.yichengxuetang.utils.ArcProgressBar;
import com.example.yichengxuetang.utils.ArcProgressBar2;
import com.example.yichengxuetang.utils.CountTimeUtils;
import com.llw.mvplibrary.mvp.MvpActivity;

import static com.example.yichengxuetang.fragments.practicecenter.PublicSubjectsFragment.courseType;

public class SmartResultActivity extends MvpActivity<QuestionBankContract.QuestionBankPresenter> implements QuestionBankContract.QuestionBankView {

    private TextView tv_time;
    private ArcProgressBar2 circleProgressView;
    private InterruptOnlyResponse.DataBean data;
    private TextView tv_total_num;
    private TextView tv_error_ti;
    private TextView tv_exercise_other;
    private TextView tv_find_error_questioin;

    @Override
    protected QuestionBankContract.QuestionBankPresenter createPresenter() {
        return new QuestionBankContract.QuestionBankPresenter();
    }

    @Override
    public void getWallPaper(QuestionBankResponse wallPaperResponse) {

    }

    @Override
    public void getLearningCenter(QuestionBankTypeResponse wallPaperResponse) {

    }

    @Override
    public void getQuestionInfo(QuestionInfoResponse wallPaperResponse) {

    }

    @Override
    public void getQuestionInfoList(QuestionListResponse wallPaperResponse) {

    }

    @Override
    public void getExamBranch(ExamBranchRsponse wallPaperResponse) {

    }

    @Override
    public void getQuestionSubmit(CommitSuccessResponse wallPaperResponse) {

    }

    @Override
    public void getPauseTime(CommitSuccessResponse wallPaperResponse) {

    }

    @Override
    public void getQuestionCollect(CollectQuestionSuccessResponse wallPaperResponse) {

    }

    @Override
    public void getQuestionMulu(QuestionMuluResponse wallPaperResponse) {

    }

    @Override
    public void getQuestionMuluSimlu(BatchMenuListSimluResponse wallPaperResponse) {

    }

    @Override
    public void getInterrputOnly(InterruptOnlyResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {
            data = wallPaperResponse.getData();

            //设置当前进度
            circleProgressView.setMaxProgress(data.getDoneNum());
            circleProgressView.setProgress(data.getDoneNum()-data.getWrongNum());
            circleProgressView.setFirstText(data.getRightRate());
            circleProgressView.setSecondText("本次正确率");
            circleProgressView.setProgressColor(Color.parseColor("#FFFFFF"));
            circleProgressView.setArcBgColor(Color.parseColor("#FFEEDD"));
            tv_total_num.setText(data.getDoneNum() + "");
            tv_error_ti.setText(data.getWrongNum() + "");
            tv_time.setText(data.getTotalTime() + "");
        }

    }

    @Override
    public void getFailed(Throwable e) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

        View decorView = getWindow().getDecorView();
        //保持View属性改变View不会重新绘制
        int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE

                //隐藏状态栏
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

        decorView.setSystemUiVisibility(option);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        Intent intent = getIntent();
        String batchNo = intent.getStringExtra("batchNo");
        String totalTime = intent.getStringExtra("totalTime");
        tv_total_num = findViewById(R.id.tv_total_num);
        tv_error_ti = findViewById(R.id.tv_error_ti);
        tv_time = findViewById(R.id.tv_time);
        tv_exercise_other = findViewById(R.id.tv_exercise_other);
        tv_find_error_questioin = findViewById(R.id.tv_find_error_questioin);
        RelativeLayout rl_left_out = findViewById(R.id.rl_left_out);
        circleProgressView = findViewById(R.id.cpv);
        mPresenter.getInterruptOnly(batchNo, totalTime);
        rl_left_out.setOnClickListener(v -> finish());
        tv_exercise_other.setOnClickListener(new View.OnClickListener() {//再次刷题
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OnlyActivity.class);
                intent.putExtra("typeId", "");
                intent.putExtra("courseType", courseType);
                intent.putExtra("examType", 2);
                intent.putExtra("questionTitle", "智能刷题");
                context.startActivity(intent);
                finish();
            }
        });
        tv_find_error_questioin.setOnClickListener(new View.OnClickListener() {//查看错题
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AnalysisActivity.class);
                intent.putExtra("batchNo",batchNo);
                intent.putExtra("typeAnalysis",1+"");
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_smart_result;
    }
}