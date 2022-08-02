package com.example.yichengxuetang.utils;

import android.content.Context;
import android.view.View;

import com.zinc.jrecycleview.loadview.base.IBaseRefreshLoadView;
import com.zinc.jrecycleview.loadview.bean.MoveInfo;

public class RefreshHeadView extends IBaseRefreshLoadView {
    public RefreshHeadView(Context context) {
        super(context);
    }

    /**
     * 初始化 刷新 的视图
     */
    protected View initView(Context context) {
        return null;
    }

    /**
     * 获取 刷新 的视图
     */
    protected View getLoadView() {
        return null;
    }

    /**
     * 等待上拉 或 等待下拉的状态 视图表现
     */
    @Override
    protected void onPullToAction() {}

    /**
     * 释放执行（释放刷新 或 释放加载更多）视图表现
     */
    @Override
    protected void onReleaseToAction() {}

    /**
     * 执行中 视图表现
     */
    @Override
    protected void onExecuting() {}

    /**
     * 执行完视图表现
     */
    @Override
    protected void onDone() {}

    /**
     * 初始化视图，用于加载自己的视图
     * 拉动过程中的回调，可以更加细微的处理动画（可替换 onPullToAction 和 onReleaseToAction ）
     */
    @Override
    protected void onMoving(MoveInfo moveInfo) {}

}
