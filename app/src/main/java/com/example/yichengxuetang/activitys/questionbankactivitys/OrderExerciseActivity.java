package com.example.yichengxuetang.activitys.questionbankactivitys;


import android.graphics.Color;
import android.os.Bundle;

import com.example.yichengxuetang.R;
import com.example.yichengxuetang.bean.GroupInfo;
import com.example.yichengxuetang.bean.GroupNoticeBean;
import com.example.yichengxuetang.bean.WallPaperResponse;
import com.example.yichengxuetang.contract.MainContract;
import com.llw.mvplibrary.mvp.MvpActivity;
import com.llw.mvplibrary.network.utils.StatusBarUtils;

public class OrderExerciseActivity extends MvpActivity<MainContract.MainPresenter> implements MainContract.IMainView {

    @Override
    protected MainContract.MainPresenter createPresenter() {
        return new MainContract.MainPresenter();
    }

    @Override
    public void getWallPaper(WallPaperResponse wallPaperResponse) {

    }

    @Override
    public void getGroupInfo(GroupInfo groupInfo) {

    }

    @Override
    public void getGroupNotice(GroupNoticeBean groupInfo) {

    }

    @Override
    public void getFailed(Throwable e) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtils.setColor(this, Color.parseColor("#E5F9F9"));
        StatusBarUtils.setTextDark(this, true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_exercise;
    }
}