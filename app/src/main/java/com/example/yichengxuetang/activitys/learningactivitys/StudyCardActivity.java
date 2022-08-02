package com.example.yichengxuetang.activitys.learningactivitys;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.example.yichengxuetang.R;
import com.example.yichengxuetang.bean.StudyCardResponse;
import com.example.yichengxuetang.contract.StudyCardContract;
import com.ljb.page.PageState;
import com.ljb.page.PageStateLayout;
import com.llw.mvplibrary.mvp.MvpActivity;

public class StudyCardActivity extends MvpActivity<StudyCardContract.StudyCardPresenter> implements StudyCardContract.ExamView {


    private ImageView iv_study_card;
    private PageStateLayout page_layout;

    @Override
    protected StudyCardContract.StudyCardPresenter createPresenter() {
        return new StudyCardContract.StudyCardPresenter();
    }

    @Override
    public void getWallPaper(StudyCardResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {
            Glide.with(context).load(wallPaperResponse.getData()).into(iv_study_card);
            page_layout.setPage(PageState.STATE_SUCCESS);
        } else {
            page_layout.setPage(PageState.STATE_EMPTY);
        }
    }

    @Override
    public void getFailed(Throwable e) {
        page_layout.setPage(PageState.STATE_ERROR);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        page_layout = findViewById(R.id.page_layout);
        Intent intent = getIntent();
        String chapterId = intent.getStringExtra("chapterId");

        page_layout.setContentView(R.layout.activity_study_card);
        mPresenter.getWallPaper(chapterId);

        page_layout.setPage(PageState.STATE_LOADING);
        RelativeLayout rl_left = findViewById(R.id.rl_left);
        TextView tv_title_top = findViewById(R.id.tv_title_top);
        iv_study_card = findViewById(R.id.iv_study_card);
        tv_title_top.setText("毕业证");
        rl_left.setOnClickListener(v -> finish());
        TextView retry = findViewById(R.id.retry);
        //点击重试
        retry.setOnClickListener(v -> mPresenter.getWallPaper(chapterId));

    }

    @Override
    public int getLayoutId() {
        return R.layout.base_layout;
    }
}