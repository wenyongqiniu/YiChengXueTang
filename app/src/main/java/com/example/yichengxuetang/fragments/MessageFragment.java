package com.example.yichengxuetang.fragments;

import android.os.Bundle;


import androidx.fragment.app.FragmentManager;

import com.example.yichengxuetang.R;

import com.example.yichengxuetang.bean.GroupInfo;
import com.example.yichengxuetang.bean.GroupNoticeBean;
import com.example.yichengxuetang.bean.WallPaperResponse;
import com.example.yichengxuetang.contract.MainContract;
import com.llw.mvplibrary.mvp.MvpFragment;

import io.rong.imkit.conversationlist.ConversationListFragment;


public class MessageFragment extends MvpFragment<MainContract.MainPresenter> implements MainContract.IMainView {


    public MessageFragment() {
        // Required empty public constructor
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
        context=getActivity().getApplicationContext();
        //FrameLayout fr_layout = rootView.findViewById(R.id.fr_layout);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fr_layout, new ConversationListFragment()).commit();

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_message;
    }
}


