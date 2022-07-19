package com.example.yichengxuetang.fragments.practicecenter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yichengxuetang.R;
import com.example.yichengxuetang.activitys.questionbankactivitys.SimulationActivity;
import com.example.yichengxuetang.activitys.questionbankactivitys.SimultionResultActivity;
import com.example.yichengxuetang.activitys.questionbankactivitys.SmartTopicActivity;
import com.example.yichengxuetang.bean.GroupInfo;
import com.example.yichengxuetang.bean.GroupNoticeBean;
import com.example.yichengxuetang.bean.WallPaperResponse;
import com.example.yichengxuetang.contract.MainContract;
import com.example.yichengxuetang.utils.ArcProgressBar;
import com.king.view.circleprogressview.CircleProgressView;
import com.llw.mvplibrary.mvp.MvpFragment;

public class PublicSubjectsFragment extends MvpFragment<MainContract.MainPresenter> implements MainContract.IMainView {


    public PublicSubjectsFragment() {
        // Required empty public constructor
    }


    private void initView(View view) {


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
    protected MainContract.MainPresenter createPresent() {
        return new MainContract.MainPresenter();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        TextView tv_smart = rootView.findViewById(R.id.tv_smart);
        TextView tv_simulation = rootView.findViewById(R.id.tv_simulation);
        //设置进度改变监听
        tv_smart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, SmartTopicActivity.class));
            }
        });
        //设置进度改变监听
        tv_simulation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, SimultionResultActivity.class));
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_public_subjects;
    }
}