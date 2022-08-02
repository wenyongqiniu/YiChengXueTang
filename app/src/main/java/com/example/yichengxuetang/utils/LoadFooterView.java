package com.example.yichengxuetang.utils;

import android.content.Context;
import android.view.View;

import com.zinc.jrecycleview.loadview.base.IBaseLoadMoreView;
import com.zinc.jrecycleview.loadview.base.IBaseRefreshLoadView;
import com.zinc.jrecycleview.loadview.bean.MoveInfo;

public class LoadFooterView extends IBaseLoadMoreView {
    public LoadFooterView(Context context) {
        super(context);
    }

    /**
     * 获取 加载更多 的视图
     */
    @Override
    protected View getLoadView() {
        return null;
    }

    /**
     * 初始化 加载更多 的视图
     */
    @Override
    protected View initView(Context context) {
        return null;
    }

    /**
     * 上拉加载（上拉超过视图高度前）
     */
    @Override
    protected void onPullToAction() {}

    /**
     * 释放刷新（上拉超过视图高度后）
     */
    @Override
    protected void onReleaseToAction() {}

    /**
     * 执行中
     */
    @Override
    protected void onExecuting() {}

    /**
     * 执行完
     */
    @Override
    protected void onDone() {}

    /**
     * 加载出错
     */
    @Override
    protected void onError() {}

    /**
     * 没有更多数据
     */
    @Override
    protected void onNoMore() {}



}
