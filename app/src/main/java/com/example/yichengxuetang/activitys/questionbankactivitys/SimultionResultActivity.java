package com.example.yichengxuetang.activitys.questionbankactivitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yichengxuetang.R;
import com.example.yichengxuetang.adapter.MuluResultTitleAdapter;
import com.example.yichengxuetang.adapter.MuluSlimuTitleAdapter;
import com.example.yichengxuetang.application.MyApplication;
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
import com.example.yichengxuetang.utils.ArcProgressBar;
import com.llw.mvplibrary.mvp.MvpActivity;

import java.util.List;

public class SimultionResultActivity extends MvpActivity<QuestionBankContract.QuestionBankPresenter> implements QuestionBankContract.QuestionBankView {


    private RecyclerView rv_mulu_result;
    public static List<BatchMenuListSimluResponse.DataBean> dataResult;


    @Override
    protected QuestionBankContract.QuestionBankPresenter createPresenter() {
        return new QuestionBankContract.QuestionBankPresenter();
    }


    @Override
    public void getFailed(Throwable e) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        MyApplication.getActivityManager().addActivity(this);
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

        mPresenter.getQuestionMuluSimlu(batchNo);
        ArcProgressBar circleProgressView = findViewById(R.id.cpv);
        RelativeLayout rl_left_out = findViewById(R.id.rl_left_out);
        TextView tv_error_anlisis = findViewById(R.id.tv_error_anlisis);
        TextView tv_all_anlisis = findViewById(R.id.tv_all_anlisis);
        rv_mulu_result = findViewById(R.id.rv_mulu_result);
        rl_left_out.setOnClickListener(v -> finish());

        //设置当前进度
        circleProgressView.setMaxProgress(100);
        circleProgressView.setProgress(80);
        circleProgressView.setFirstText("总分数（100分）");
        circleProgressView.setSecondText("79.5");
        circleProgressView.setThirdText("难度4.8");
      /*  circleProgressView.setFirstTextSize(9);
        circleProgressView.setSecondTextSize(20);
        circleProgressView.setThirdTextSize(12);*/
        circleProgressView.setProgressColor(Color.parseColor("#FFFFFF"));
        circleProgressView.setArcBgColor(Color.parseColor("#FFEEDD"));

        tv_error_anlisis.setOnClickListener(new View.OnClickListener() {//查看错题
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(context, AnalysisActivity.class);
                intent1.putExtra("typeAnalysis", 1 + "");
                intent1.putExtra("batchNo", batchNo);
                startActivity(intent1);
            }
        });
        tv_all_anlisis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(context, AnalysisActivity.class);
                intent1.putExtra("typeAnalysis", 2 + "");
                intent1.putExtra("batchNo", batchNo);
                startActivity(intent1);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_simultion_result;
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

        if (wallPaperResponse.getCode() == 0) {
            dataResult = wallPaperResponse.getData();
            MuluResultTitleAdapter muluSlimuTitleAdapter = new MuluResultTitleAdapter(R.layout.item_simlu_title, dataResult, 1);
            rv_mulu_result.setLayoutManager(new LinearLayoutManager(context));
            rv_mulu_result.setAdapter(muluSlimuTitleAdapter);
            muluSlimuTitleAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void getInterrputOnly(InterruptOnlyResponse wallPaperResponse) {

    }
}