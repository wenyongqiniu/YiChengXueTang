package com.example.yichengxuetang.activitys.questionbankactivitys;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.yichengxuetang.R;
import com.example.yichengxuetang.bean.GroupInfo;
import com.example.yichengxuetang.bean.GroupNoticeBean;
import com.example.yichengxuetang.bean.WallPaperResponse;
import com.example.yichengxuetang.contract.MainContract;
import com.example.yichengxuetang.utils.ArcProgressBar;
import com.llw.mvplibrary.mvp.MvpActivity;

public class SimultionResultActivity extends MvpActivity<MainContract.MainPresenter> implements MainContract.IMainView {



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
        View decorView = getWindow().getDecorView();
        //保持View属性改变View不会重新绘制
        int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE

                //隐藏状态栏
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

        decorView.setSystemUiVisibility(option);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ArcProgressBar circleProgressView = findViewById(R.id.cpv);

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
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_simultion_result;
    }
}