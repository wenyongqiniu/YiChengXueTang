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
import com.llw.mvplibrary.mvp.MvpActivity;

public class OnlyResultActivity extends MvpActivity<QuestionBankContract.QuestionBankPresenter> implements QuestionBankContract.QuestionBankView {


    private TextView tv_title;
    private TextView tv_time;
    private TextView tv_right_rate;
    private ArcProgressBar circleProgressView;
    private String questionTitle;

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
            InterruptOnlyResponse.DataBean data = wallPaperResponse.getData();

            //设置当前进度
            circleProgressView.setMaxProgress(data.getTotalNum());
            circleProgressView.setProgress(data.getRightNum());
            circleProgressView.setFirstText("答对题数");
            int rightNum = data.getRightNum();
            circleProgressView.setSecondText(rightNum + "");
            circleProgressView.setThirdText("共" + data.getTotalNum() + "道");
            circleProgressView.setProgressColor(Color.parseColor("#FFFFFF"));
            circleProgressView.setArcBgColor(Color.parseColor("#FFEEDD"));

            tv_title.setText("练习专项：" + questionTitle);
            tv_right_rate.setText("正确率：" + data.getRightRate());
            tv_time.setText("用时：" + data.getTotalTime());
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
        String questionTypeId = intent.getStringExtra("questionTypeId");
        questionTitle = intent.getStringExtra("questionTitle");
        mPresenter.getOnlyResult(questionTypeId);

        circleProgressView = findViewById(R.id.cpv);
        tv_title = findViewById(R.id.tv_title);
        tv_time = findViewById(R.id.tv_time);
        tv_right_rate = findViewById(R.id.tv_right_rate);
        TextView tv_exercise_other = findViewById(R.id.tv_exercise_other);
        TextView tv_find_error_questioin = findViewById(R.id.tv_find_error_questioin);
        RelativeLayout rl_left_out = findViewById(R.id.rl_left_out);
        rl_left_out.setOnClickListener(v -> finish());
        tv_exercise_other.setOnClickListener(v -> finish());
        tv_find_error_questioin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(context, AnalysisActivity.class);
                intent.putExtra("batchNo",batchNo);
                intent.putExtra("typeAnalysis",1+"");
                context.startActivity(intent);*/
            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_only_result;
    }
}