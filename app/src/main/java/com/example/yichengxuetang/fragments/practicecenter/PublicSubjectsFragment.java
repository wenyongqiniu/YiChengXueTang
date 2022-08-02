package com.example.yichengxuetang.fragments.practicecenter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.yichengxuetang.R;
import com.example.yichengxuetang.activitys.questionbankactivitys.OnlyActivity;
import com.example.yichengxuetang.activitys.questionbankactivitys.OnlyResultActivity;
import com.example.yichengxuetang.activitys.questionbankactivitys.SimulationActivity;
import com.example.yichengxuetang.activitys.questionbankactivitys.SimultionResultActivity;
import com.example.yichengxuetang.activitys.questionbankactivitys.SmartDoHomeworkctivity;
import com.example.yichengxuetang.activitys.questionbankactivitys.SmartTopicActivity;
import com.example.yichengxuetang.adapter.IconAdapter;
import com.example.yichengxuetang.adapter.Only2Adapter;
import com.example.yichengxuetang.adapter.OnlyAdapter;
import com.example.yichengxuetang.bean.BatchMenuListSimluResponse;
import com.example.yichengxuetang.bean.BindingWechatResponse;
import com.example.yichengxuetang.bean.CollectQuestionSuccessResponse;
import com.example.yichengxuetang.bean.CommitSuccessResponse;
import com.example.yichengxuetang.bean.ExamBranchRsponse;
import com.example.yichengxuetang.bean.GroupInfo;
import com.example.yichengxuetang.bean.GroupNoticeBean;
import com.example.yichengxuetang.bean.IconResponse;
import com.example.yichengxuetang.bean.InterruptOnlyResponse;
import com.example.yichengxuetang.bean.LearningCenterResponse;
import com.example.yichengxuetang.bean.QuestionBankResponse;
import com.example.yichengxuetang.bean.QuestionBankTypeResponse;
import com.example.yichengxuetang.bean.QuestionFirstResponse;
import com.example.yichengxuetang.bean.QuestionInfoResponse;
import com.example.yichengxuetang.bean.QuestionListResponse;
import com.example.yichengxuetang.bean.QuestionMuluResponse;
import com.example.yichengxuetang.bean.QuestionSecondkResponse;
import com.example.yichengxuetang.bean.WallPaperResponse;
import com.example.yichengxuetang.contract.MainContract;
import com.example.yichengxuetang.contract.QuestionBankContract;
import com.example.yichengxuetang.utils.ArcProgressBar;
import com.king.view.circleprogressview.CircleProgressView;
import com.llw.mvplibrary.mvp.MvpFragment;

import java.util.ArrayList;
import java.util.List;

public class PublicSubjectsFragment extends MvpFragment<QuestionBankContract.QuestionBankPresenter> implements QuestionBankContract.QuestionBankView {

    private String parentId = "";
    public static Integer busiTypeCode;//业务类型
    public static String courseType;//业务类型
    public static Integer queryType = 2;//	查询类型1查询一级类型 2查次级分类
    private RecyclerView rv_only;
    private List<QuestionBankResponse.DataBean> listOnly;
    private OnlyAdapter onlyAdapter;
    public static int onFirst;
    private RecyclerView rv_icon;
    private int gradePosition;


    private ArrayList<IconResponse> iconResponses = new ArrayList<>();
    private ArrayList<Integer> needHiddenIconlist;

    public PublicSubjectsFragment() {
        // Required empty public constructor
    }


    @Override
    public void getFailed(Throwable e) {

    }

    @Override
    public void onResume() {
        super.onResume();
        //listOnly.clear();

    }


    @Override
    public void initData(Bundle savedInstanceState) {
        //1专项练习 2 智能刷题 3模拟考试 4历年真题 5 错题库练习  6收藏题库练习 7学习报告
        rv_icon = rootView.findViewById(R.id.rv_icon);
        rv_only = rootView.findViewById(R.id.rv_only);
        assert getArguments() != null;
        parentId = getArguments().getString("parentId");
        courseType = getArguments().getString("courseType");
        needHiddenIconlist = getArguments().getIntegerArrayList("needHiddenIconlist");
        mPresenter.getQuestionBank(queryType + "", busiTypeCode + "", parentId);

        iconResponses = new ArrayList<>();

        IconResponse iconResponse = new IconResponse();
        iconResponse.setName("智能刷题");
        iconResponse.setIcon(R.mipmap.samrt_ai);
        iconResponse.setId(2);

        IconResponse iconResponse2 = new IconResponse();
        iconResponse2.setName("模拟演练");
        iconResponse2.setIcon(R.mipmap.simulation);
        iconResponse2.setId(3);

        IconResponse iconResponse3 = new IconResponse();
        iconResponse3.setName("历年真题");
        iconResponse3.setIcon(R.mipmap.real_topic);
        iconResponse3.setId(4);

        IconResponse iconResponse4 = new IconResponse();
        iconResponse4.setName("学习报告");
        iconResponse4.setIcon(R.mipmap.learn_report);
        iconResponse4.setId(7);

        IconResponse iconResponse5 = new IconResponse();
        iconResponse5.setName("错题重做");
        iconResponse5.setIcon(R.mipmap.error_redo);
        iconResponse5.setId(5);

        IconResponse iconResponse6 = new IconResponse();
        iconResponse6.setName("收藏题目");
        iconResponse6.setIcon(R.mipmap.collect_ti);
        iconResponse6.setId(6);
        iconResponses.add(iconResponse);
        iconResponses.add(iconResponse2);
        iconResponses.add(iconResponse3);
        iconResponses.add(iconResponse4);
        iconResponses.add(iconResponse5);
        iconResponses.add(iconResponse6);

        for (int i = 0; i < needHiddenIconlist.size(); i++) {
            if (needHiddenIconlist.get(i) == 2) {
                iconResponses.remove(iconResponse);
            } else if (needHiddenIconlist.get(i) == 3) {
                iconResponses.remove(iconResponse2);
            } else if (needHiddenIconlist.get(i) == 4) {
                iconResponses.remove(iconResponse3);
            } else if (needHiddenIconlist.get(i) == 5) {
                iconResponses.remove(iconResponse4);
            } else if (needHiddenIconlist.get(i) == 6) {
                iconResponses.remove(iconResponse5);
            } else if (needHiddenIconlist.get(i) == 7) {
                iconResponses.remove(iconResponse6);
            }

        }
        IconAdapter iconAdapter = new IconAdapter(R.layout.item_icon, iconResponses, courseType);
        rv_icon.setLayoutManager(new GridLayoutManager(context, 4));
        rv_icon.setAdapter(iconAdapter);


        listOnly = new ArrayList<>();
        onlyAdapter = new OnlyAdapter(R.layout.item_only, listOnly, courseType, mPresenter);
        rv_only.setLayoutManager(new LinearLayoutManager(context));
        rv_only.setAdapter(onlyAdapter);


        onlyAdapter.addChildClickViewIds(R.id.iv_zhankai, R.id.rl_only, R.id.rl_open);
        onlyAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                if (view.getId() == R.id.rl_open) {//有子集才展开
                    if (listOnly.get(position).getOpen() == 0) {
                        gradePosition = position;
                        onFirst = 1;
                        parentId = listOnly.get(position).getId();
                        mPresenter.getQuestionBank(queryType + "", busiTypeCode + "", parentId + "");
                        listOnly.get(position).setOpen(1);
                    } else {//再次点击收起
                        listOnly.get(position).setOpen(0);
                        onlyAdapter.notifyDataSetChanged();
                    }
                } else {
                    if (listOnly.get(position).getTotalNum() != listOnly.get(position).getDoneNum()) {
                        Intent intent = new Intent(getActivity(), OnlyActivity.class);
                        intent.putExtra("typeId", listOnly.get(position).getId());
                        intent.putExtra("courseType", courseType);
                        intent.putExtra("examType", 1);
                        intent.putExtra("questionTitle", listOnly.get(position).getName());
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getActivity(), OnlyResultActivity.class);
                        intent.putExtra("questionTypeId", listOnly.get(position).getId());
                        intent.putExtra("questionTitle", listOnly.get(position).getName());
                        startActivity(intent);
                    }
                }
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_public_subjects;
    }

    @Override
    public void getWallPaper(QuestionBankResponse wallPaperResponse) {

        List<QuestionBankResponse.DataBean> data = wallPaperResponse.getData();
        if (wallPaperResponse.getCode() == 0) {
            if (onFirst == 0) {//第一级
                listOnly.addAll(data);
            } else if (onFirst == 1) {//展开第二级
                ArrayList<QuestionBankResponse.DataBean.TotalNumQBean> totalNumQBeans = new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                    QuestionBankResponse.DataBean.TotalNumQBean totalNumQBean = new QuestionBankResponse.DataBean.TotalNumQBean();
                    totalNumQBean.setDoneNum(data.get(i).getDoneNum());
                    totalNumQBean.setHasChildren(data.get(i).isHasChildren());
                    totalNumQBean.setId(data.get(i).getId());
                    totalNumQBean.setIfLatestExam(data.get(i).isIfLatestExam());
                    totalNumQBean.setName(data.get(i).getName());
                    totalNumQBean.setRightRate(data.get(i).getRightRate());
                    totalNumQBean.setTotalNum(data.get(i).getTotalNum());
                    totalNumQBeans.add(totalNumQBean);
                    listOnly.get(gradePosition).setTotalNumQ(totalNumQBeans);
                }

            } else {

                ArrayList<QuestionBankResponse.DataBean.TotalNumQBean.List1Bean> totalNumQBeans1 = new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                    QuestionBankResponse.DataBean.TotalNumQBean.List1Bean totalNumQBean = new QuestionBankResponse.DataBean.TotalNumQBean.List1Bean();
                    totalNumQBean.setDoneNum(data.get(i).getDoneNum());
                    totalNumQBean.setHasChildren(data.get(i).isHasChildren());
                    totalNumQBean.setId(data.get(i).getId());
                    totalNumQBean.setIfLatestExam(data.get(i).isIfLatestExam());
                    totalNumQBean.setName(data.get(i).getName());
                    totalNumQBean.setRightRate(data.get(i).getRightRate());
                    totalNumQBean.setTotalNum(data.get(i).getTotalNum());
                    totalNumQBeans1.add(totalNumQBean);
                    listOnly.get(gradePosition).getTotalNumQ().get(Only2Adapter.position).setList1(totalNumQBeans1);
                }
            }
            onlyAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getLearningCenter(QuestionBankTypeResponse wallPaperResponse) {

    }

    @Override
    public void getQuestionInfo(QuestionInfoResponse wallPaperResponse) {

    }

    @Override
    public void getQuestionInfoList(QuestionListResponse wallPaperResponse) {

    }

    @Override
    public void getExamBranch(ExamBranchRsponse wallPaperResponse) {

    }

    @Override
    public void getQuestionSubmit(CommitSuccessResponse wallPaperResponse) {

    }

    @Override
    public void getPauseTime(CommitSuccessResponse wallPaperResponse) {

    }

    @Override
    public void getQuestionCollect(CollectQuestionSuccessResponse wallPaperResponse) {

    }

    @Override
    public void getQuestionMulu(QuestionMuluResponse wallPaperResponse) {

    }

    @Override
    public void getQuestionMuluSimlu(BatchMenuListSimluResponse wallPaperResponse) {

    }

    @Override
    public void getInterrputOnly(InterruptOnlyResponse wallPaperResponse) {

    }

    @Override
    protected QuestionBankContract.QuestionBankPresenter createPresent() {
        return new QuestionBankContract.QuestionBankPresenter();
    }
}