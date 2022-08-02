package com.example.yichengxuetang.activitys.learningactivitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import com.example.yichengxuetang.R;
import com.example.yichengxuetang.adapter.AnswerAdapter;
import com.example.yichengxuetang.adapter.HomeWorkAdapter;
import com.example.yichengxuetang.adapter.HomeWorkHeaderAdapter;
import com.example.yichengxuetang.adapter.HomeWorkListAdapter;
import com.example.yichengxuetang.bean.CommitAnswerResponse;
import com.example.yichengxuetang.bean.CommitSuccessResponse;
import com.example.yichengxuetang.bean.HomeWorkResponse;
import com.example.yichengxuetang.contract.HomeWorkContract;
import com.example.yichengxuetang.utils.CustomerToastUtils;
import com.google.gson.Gson;
import com.ljb.page.PageState;
import com.ljb.page.PageStateLayout;
import com.llw.mvplibrary.mvp.MvpActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.yichengxuetang.application.MyApplication.loadingPopupView;


public class DoHomeWorkActivity extends MvpActivity<HomeWorkContract.HomeWorkPresenter> implements HomeWorkContract.HomeWorkView {


    private RecyclerView rv_homework_head;
    private RecyclerView rv_homework;
    private List<HomeWorkResponse.DataBean.TaskListBean> taskList;

    private String sectionId;
    private HomeWorkHeaderAdapter homeWorkHeaderAdapter;
    private HomeWorkAdapter homeWorkAdapter;
    private CommitAnswerResponse commitAnswerResponse;
    private ArrayList<CommitAnswerResponse.TaskListBean> listBean = new ArrayList<>();
    private List<HomeWorkResponse.DataBean.TaskRecordListBean> taskRecordList;
    private ArrayList<HomeWorkResponse.DataBean.TaskRecordListBean> taskRecordListBeans;
    private PageStateLayout page_layout;
    private TextView tv_commit_answer;
    private String examType;


    @Override
    protected HomeWorkContract.HomeWorkPresenter createPresenter() {
        return new HomeWorkContract.HomeWorkPresenter();
    }

    @Override
    public void getHomeWork(HomeWorkResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {
            taskList = wallPaperResponse.getData().getTaskList();
            if (examType.equals("")) {//代表课后作业有作业头部完成情况
                taskRecordListBeans.clear();
                taskRecordList = wallPaperResponse.getData().getTaskRecordList();
                taskRecordListBeans.addAll(taskRecordList);
                rv_homework_head.setLayoutManager(new LinearLayoutManager(DoHomeWorkActivity.this, RecyclerView.HORIZONTAL, false));
                rv_homework_head.setAdapter(homeWorkHeaderAdapter);
                homeWorkHeaderAdapter.notifyDataSetChanged();
                for (int i = 0; i < taskRecordListBeans.size(); i++) {
                    if (taskRecordListBeans.get(i).getSectionId().equals(sectionId)) {
                        if (taskRecordListBeans.get(i).getStatus() == 1) {//已做完
                            AnswerAdapter answerAdapter = new AnswerAdapter(R.layout.item_homework_answer, taskList);
                            rv_homework.setLayoutManager(new LinearLayoutManager(context));
                            rv_homework.setAdapter(answerAdapter);
                            answerAdapter.notifyDataSetChanged();
                            tv_commit_answer.setVisibility(View.GONE);
                        } else {
                            homeWorkAdapter = new HomeWorkAdapter(R.layout.item_homework, taskList);
                            rv_homework.setLayoutManager(new LinearLayoutManager(DoHomeWorkActivity.this));
                            rv_homework.setAdapter(homeWorkAdapter);
                            homeWorkAdapter.notifyDataSetChanged();
                            tv_commit_answer.setVisibility(View.VISIBLE);
                        }
                        break;
                    }
                }
            } else {//课后练习，没有头部
                if (wallPaperResponse.getData().getCompleteStatus() == 1) {//已做完
                    AnswerAdapter answerAdapter = new AnswerAdapter(R.layout.item_homework_answer, taskList);
                    rv_homework.setLayoutManager(new LinearLayoutManager(context));
                    rv_homework.setAdapter(answerAdapter);
                    answerAdapter.notifyDataSetChanged();
                    tv_commit_answer.setVisibility(View.GONE);
                } else {
                    homeWorkAdapter = new HomeWorkAdapter(R.layout.item_homework, taskList);
                    rv_homework.setLayoutManager(new LinearLayoutManager(DoHomeWorkActivity.this));
                    rv_homework.setAdapter(homeWorkAdapter);
                    homeWorkAdapter.notifyDataSetChanged();
                    tv_commit_answer.setVisibility(View.VISIBLE);
                }
            }

            page_layout.setPage(PageState.STATE_SUCCESS);
        } else {
            page_layout.setPage(PageState.STATE_EMPTY);
        }
       // loadingPopupView.dismiss();

    }

    @Override
    public void getSubmitAnswer(CommitSuccessResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {//提交成功
            mPresenter.getHomeWork(sectionId);
            //loadingPopupView.show();
            commitAnswerResponse = new CommitAnswerResponse();
            listBean.clear();
            tv_commit_answer.setVisibility(View.GONE);
        }

    }

    @Override
    public void getFailed(Throwable e) {
        page_layout.setPage(PageState.STATE_ERROR);
        loadingPopupView.dismiss();
        Log.e("error", "getFailed: " + e.getMessage());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        sectionId = intent.getStringExtra("sectionId");
        examType = intent.getStringExtra("examType");

        mPresenter.getHomeWork(sectionId);
        page_layout = findViewById(R.id.page_layout);
        page_layout.setPage(PageState.STATE_LOADING);
        page_layout.setContentView(R.layout.activity_do_home_work);
        TextView retry = page_layout.findViewById(R.id.retry);
        rv_homework_head = findViewById(R.id.rv_homework_head);
        rv_homework = findViewById(R.id.rv_homework);
        RelativeLayout rl_left = findViewById(R.id.rl_left);
        TextView tv_title_top = findViewById(R.id.tv_title_top);
        tv_commit_answer = findViewById(R.id.tv_commit_answer);
        tv_title_top.setText("课后作业");

        rl_left.setOnClickListener(v -> finish());
        //点击重试
        retry.setOnClickListener(view -> {
            mPresenter.getHomeWork(sectionId);
            page_layout.setPage(PageState.STATE_LOADING);
            commitAnswerResponse = new CommitAnswerResponse();
            listBean.clear();
        });

        commitAnswerResponse = new CommitAnswerResponse();
        taskRecordListBeans = new ArrayList<>();
        homeWorkHeaderAdapter = new HomeWorkHeaderAdapter(R.layout.item_homework_header, taskRecordListBeans);
        homeWorkHeaderAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                sectionId = taskRecordListBeans.get(position).getSectionId();
                mPresenter.getHomeWork(sectionId);
                loadingPopupView.show();
                commitAnswerResponse = new CommitAnswerResponse();
                listBean.clear();
            }
        });

        //提交答案
        tv_commit_answer.setOnClickListener(view -> {
            for (int i = 0; i < taskList.size(); i++) {
                List<HomeWorkResponse.DataBean.TaskListBean.AnswerListBean> answerList = taskList.get(i).getAnswerList();
                ArrayList<String> answerCode = new ArrayList<>();

                if (taskList.get(i).getTaskType() == 1) {//单选
                    for (int j = 0; j < answerList.size(); j++) {
                        CommitAnswerResponse.TaskListBean taskListBean1 = new CommitAnswerResponse.TaskListBean();
                        taskListBean1.setTaskId(taskList.get(i).getTaskId());
                        if (answerList.get(j).isSelected()) {
                            answerCode.add(answerList.get(j).getAnswerCode());
                            taskListBean1.setAnswerCodeList(answerCode);
                            listBean.add(taskListBean1);
                            commitAnswerResponse.setTaskList(listBean);
                        }
                    }
                } else if (taskList.get(i).getTaskType() == 2) {//多选
                    CommitAnswerResponse.TaskListBean taskListBean = new CommitAnswerResponse.TaskListBean();
                    taskListBean.setTaskId(taskList.get(i).getTaskId());
                    for (int j = 0; j < answerList.size(); j++) {

                        if (answerList.get(j).isSelected()) {
                            answerCode.add(answerList.get(j).getAnswerCode());
                            taskListBean.setAnswerCodeList(answerCode);
                        }
                    }
                    if (taskListBean.getAnswerCodeList() != null) {
                        listBean.add(taskListBean);
                    }
                } else {//填空
                    if (!HomeWorkListAdapter.ed_content.equals("")) {
                        CommitAnswerResponse.TaskListBean taskListBean1 = new CommitAnswerResponse.TaskListBean();
                        taskListBean1.setTaskId(taskList.get(i).getTaskId());
                        taskListBean1.setContent(taskList.get(i).getMyEd());
                        listBean.add(taskListBean1);
                        commitAnswerResponse.setTaskList(listBean);
                    }
                }
            }


            if (listBean.size() != 0) {
                commitAnswerResponse.setTaskList(listBean);
                commitAnswerResponse.setSectionId(sectionId);
            }

            if (commitAnswerResponse.getTaskList() != null) {
                Log.e("json", "onClick: " + new Gson().toJson(commitAnswerResponse));
                if (commitAnswerResponse.getTaskList().size() == taskList.size()) {
                    mPresenter.commitAnswer(commitAnswerResponse);
                } else {
                    commitAnswerResponse = new CommitAnswerResponse();
                    listBean.clear();
                    CustomerToastUtils.toastShow(context).show();
                    CustomerToastUtils.tv_toast.setText("请做答完题目再提交");
                }
            } else {
                commitAnswerResponse = new CommitAnswerResponse();
                listBean.clear();
                CustomerToastUtils.toastShow(context).show();
                CustomerToastUtils.tv_toast.setText("请做答完题目再提交");
            }

        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.base_layout;
    }
}