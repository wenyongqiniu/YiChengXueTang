package com.example.yichengxuetang.activitys.questionbankactivitys;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.example.yichengxuetang.R;
import com.example.yichengxuetang.bean.GroupInfo;
import com.example.yichengxuetang.bean.GroupNoticeBean;
import com.example.yichengxuetang.bean.WallPaperResponse;
import com.example.yichengxuetang.contract.MainContract;
import com.llw.mvplibrary.mvp.MvpActivity;
import com.llw.mvplibrary.network.utils.StatusBarUtils;

public class SmartTopicActivity extends MvpActivity<MainContract.MainPresenter> implements MainContract.IMainView {


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
        StatusBarUtils.setColor(this, Color.parseColor("#fff4fdfd"));
        StatusBarUtils.setTextDark(this, true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_smart_topic;
    }
}