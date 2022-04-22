package com.example.yichengxuetang.activitys;

import android.graphics.Color;
import android.os.Bundle;


import com.example.yichengxuetang.R;
import com.example.yichengxuetang.activitys.logins.LoginActivity;
import com.example.yichengxuetang.bean.WallPaperResponse;
import com.example.yichengxuetang.contract.MainContract;
import com.example.yichengxuetang.fragments.DiscoverFragment;
import com.example.yichengxuetang.fragments.LearningCenterFragment;
import com.example.yichengxuetang.fragments.MineFragment;
import com.example.yichengxuetang.fragments.QuestionBankFragment;
import com.example.yichengxuetang.utils.BottomBar;
import com.llw.mvplibrary.ActivityManager;
import com.llw.mvplibrary.BaseApplication;
import com.llw.mvplibrary.mvp.MvpActivity;
import com.llw.mvplibrary.network.utils.KLog;
import com.llw.mvplibrary.network.utils.StatusBarUtils;

import cn.jiguang.verifysdk.api.JVerificationInterface;


/**
 * @author llw
 */
public class MainActivity extends MvpActivity<MainContract.MainPresenter> implements MainContract.IMainView {

    @Override
    public void initData(Bundle savedInstanceState) {
        BaseApplication.getActivityManager().finishActivity(LoginActivity.class);
        JVerificationInterface.dismissLoginAuthActivity(true, (code, desc) -> {

        });
        //显示加载弹窗
        showLoadingDialog();
        //初始化列表
        initView();
    }

    /**
     * 初始化列表
     */
    private void initView() {
        StatusBarUtils.setColor(this, Color.parseColor("#F8F8F8"));
        StatusBarUtils.setTextDark(this, true);
        BottomBar bottomBar = findViewById(R.id.bottom_navigation);
        bottomBar.setContainer(R.id.fl_container)
                .setTitleSize(10)
                .setTitleIconMargin(8)
                .setIconWidth(25)
                .setIconHeight(25)
                .setTitleBeforeAndAfterColor("#8E8F90", "#FE8000")
                .addItem(LearningCenterFragment.class,
                        "学习中心", R.drawable.no_learning_center, R.drawable.learning_center)
                .addItem(DiscoverFragment.class,
                        "发现", R.drawable.no_discover, R.drawable.discover)
                .addItem(QuestionBankFragment.class,
                        "题库", R.drawable.no_question_bank, R.drawable.question_bank)
                .addItem(MineFragment.class,
                        "我的", R.drawable.no_mine, R.drawable.mine)
                .build();
        hideLoadingDialog();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainContract.MainPresenter createPresenter() {
        return new MainContract.MainPresenter();
    }

    /**
     * 获取壁纸返回
     *
     * @param wallPaperResponse
     */

    @Override
    public void getWallPaper(WallPaperResponse wallPaperResponse) {

    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void getFailed(Throwable e) {
        KLog.e("TAG", e.toString());
        showMsg("获取列表数据异常。");
        hideLoadingDialog();
    }
}
