package com.example.yichengxuetang.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yichengxuetang.R;
import com.example.yichengxuetang.adapter.ShowCourseListAdapter;
import com.example.yichengxuetang.bean.ShowCourseListResponse;
import com.example.yichengxuetang.contract.ShowCourseListContract;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.llw.mvplibrary.mvp.MvpFragment;

public class ShowFragment extends MvpFragment<ShowCourseListContract.ShowCourseListPresenter> implements ShowCourseListContract.ShowCourseListView {

    private RecyclerView rv_experience_class;
    private RecyclerView rv_advanced_course;
    private RelativeLayout rl_have_data;
    private RelativeLayout rl_no_data;

    @Override
    public void onResume() {
        super.onResume();
        assert getArguments() != null;
        String typeCode = getArguments().getString("typeCode");
        mPresenter.getShowCourseListPaper(typeCode);
    }

    @Override
    public void getShowCourseListCenter(ShowCourseListResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {
            ShowCourseListResponse.DataBean data = wallPaperResponse.getData();
            if (data.getXbCourseList() == null && data.getBigCourse()== null) {
                rl_have_data.setVisibility(View.INVISIBLE);
                rl_no_data.setVisibility(View.VISIBLE);
            }else{
                ShowCourseListAdapter showCourseListAdapter = new ShowCourseListAdapter(R.layout.item_xb, wallPaperResponse.getData().getXbCourseList());
                rv_experience_class.setLayoutManager(new LinearLayoutManager(context));
                rv_experience_class.setAdapter(showCourseListAdapter);
                showCourseListAdapter.notifyDataSetChanged();
                rl_have_data.setVisibility(View.VISIBLE);
                rl_no_data.setVisibility(View.INVISIBLE);

            }
        }

    }

    @Override
    public void getFailed(Throwable e) {

    }

    @Override
    protected ShowCourseListContract.ShowCourseListPresenter createPresent() {
        return new ShowCourseListContract.ShowCourseListPresenter();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        rv_experience_class = rootView.findViewById(R.id.rv_experience_class);
        rv_advanced_course = rootView.findViewById(R.id.rv_advanced_course);
        rl_have_data = rootView.findViewById(R.id.rl_have_data);
        rl_no_data = rootView.findViewById(R.id.rl_no_data);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_show;
    }
}
