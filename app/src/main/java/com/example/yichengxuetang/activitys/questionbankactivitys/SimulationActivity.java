package com.example.yichengxuetang.activitys.questionbankactivitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yichengxuetang.R;
import com.example.yichengxuetang.bean.BatchMenuListSimluResponse;
import com.example.yichengxuetang.bean.CollectQuestionSuccessResponse;
import com.example.yichengxuetang.bean.CommitSuccessResponse;
import com.example.yichengxuetang.bean.ExamBranchRsponse;
import com.example.yichengxuetang.bean.GroupInfo;
import com.example.yichengxuetang.bean.GroupNoticeBean;
import com.example.yichengxuetang.bean.InterruptOnlyResponse;
import com.example.yichengxuetang.bean.QuestionBankResponse;
import com.example.yichengxuetang.bean.QuestionBankTypeResponse;
import com.example.yichengxuetang.bean.QuestionInfoResponse;
import com.example.yichengxuetang.bean.QuestionListResponse;
import com.example.yichengxuetang.bean.QuestionMuluResponse;
import com.example.yichengxuetang.bean.WallPaperResponse;
import com.example.yichengxuetang.contract.MainContract;
import com.example.yichengxuetang.contract.QuestionBankContract;
import com.llw.mvplibrary.mvp.MvpActivity;
import com.llw.mvplibrary.network.utils.StatusBarUtils;

public class SimulationActivity extends MvpActivity<QuestionBankContract.QuestionBankPresenter> implements QuestionBankContract.QuestionBankView {


    private TextView tv_start_do;
    private TextView tv_redo;
    private TextView tv_tips3;

    @Override
    public void getFailed(Throwable e) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtils.setColor(this, Color.parseColor("#FFF7F3"));
        StatusBarUtils.setTextDark(this, true);
        Intent intent = getIntent();
        String typeId = intent.getStringExtra("typeId");
        int examType = intent.getIntExtra("examType", 1);
        String courseType = intent.getStringExtra("courseType");
        String questionTitle = intent.getStringExtra("questionTitle");
        RelativeLayout rl_left = findViewById(R.id.rl_left_out);
        tv_start_do = findViewById(R.id.tv_start_do);
        tv_redo = findViewById(R.id.tv_redo);
        tv_tips3 = findViewById(R.id.tv_tips3);

        rl_left.setOnClickListener(v -> finish());
        tv_start_do.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OnlyActivity.class);
                intent.putExtra("typeId", typeId);
                intent.putExtra("courseType", courseType);
                intent.putExtra("examType", 3);
                intent.putExtra("questionTitle", "");
                startActivity(intent);
            }
        });

        mPresenter.getExamBatch(courseType, examType, typeId);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_simu_lation;
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

        if (wallPaperResponse.getCode() == 0) {
            ExamBranchRsponse.DataBean data = wallPaperResponse.getData();
            if (data.isLastHasCompleted() == false) {
                tv_start_do.setText("继续上次模拟");
                tv_redo.setVisibility(View.VISIBLE);
                tv_tips3.setVisibility(View.VISIBLE);
            }
        }
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

    }

    @Override
    protected QuestionBankContract.QuestionBankPresenter createPresenter() {
        return new QuestionBankContract.QuestionBankPresenter();
    }
}