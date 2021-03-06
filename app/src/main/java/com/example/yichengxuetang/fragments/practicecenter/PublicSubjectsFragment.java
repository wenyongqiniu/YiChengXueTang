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
import com.example.yichengxuetang.R;
import com.example.yichengxuetang.activitys.questionbankactivitys.OnlyActivity;
import com.example.yichengxuetang.activitys.questionbankactivitys.SimulationActivity;
import com.example.yichengxuetang.activitys.questionbankactivitys.SimultionResultActivity;
import com.example.yichengxuetang.activitys.questionbankactivitys.SmartDoHomeworkctivity;
import com.example.yichengxuetang.activitys.questionbankactivitys.SmartTopicActivity;
import com.example.yichengxuetang.adapter.IconAdapter;
import com.example.yichengxuetang.adapter.OnlyAdapter;
import com.example.yichengxuetang.bean.BindingWechatResponse;
import com.example.yichengxuetang.bean.ExamBranchRsponse;
import com.example.yichengxuetang.bean.GroupInfo;
import com.example.yichengxuetang.bean.GroupNoticeBean;
import com.example.yichengxuetang.bean.IconResponse;
import com.example.yichengxuetang.bean.LearningCenterResponse;
import com.example.yichengxuetang.bean.QuestionBankResponse;
import com.example.yichengxuetang.bean.QuestionBankTypeResponse;
import com.example.yichengxuetang.bean.QuestionFirstResponse;
import com.example.yichengxuetang.bean.QuestionInfoResponse;
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
    private Integer busiTypeCode;//????????????
    private Integer queryType = 2;//	????????????1?????????????????? 2???????????????
    private RecyclerView rv_only;
    private List<QuestionBankResponse.DataBean> listOnly;
    private OnlyAdapter onlyAdapter;
    private int onFirst;
    private RecyclerView rv_icon;
    private int gradePosition;


    private ArrayList<IconResponse> iconResponses = new ArrayList<>();

    public PublicSubjectsFragment() {
        // Required empty public constructor
    }


    @Override
    public void getFailed(Throwable e) {

    }

    @Override
    public void onResume() {
        super.onResume();
        listOnly.clear();
        iconResponses.clear();

        parentId = getArguments().getString("parentId");
        ArrayList<Integer> needHiddenIconlist = getArguments().getIntegerArrayList("needHiddenIconlist");
        mPresenter.getQuestionBank(queryType + "", busiTypeCode + "", parentId);

        iconResponses = new ArrayList<>();

        IconResponse iconResponse = new IconResponse();
        iconResponse.setName("????????????");
        iconResponse.setIcon(R.mipmap.samrt_ai);

        IconResponse iconResponse2 = new IconResponse();
        iconResponse2.setName("????????????");
        iconResponse2.setIcon(R.mipmap.simulation);

        IconResponse iconResponse3 = new IconResponse();
        iconResponse3.setName("????????????");
        iconResponse3.setIcon(R.mipmap.real_topic);

        IconResponse iconResponse4 = new IconResponse();
        iconResponse4.setName("????????????");
        iconResponse4.setIcon(R.mipmap.learn_report);

        IconResponse iconResponse5 = new IconResponse();
        iconResponse5.setName("????????????");
        iconResponse5.setIcon(R.mipmap.error_redo);

        IconResponse iconResponse6 = new IconResponse();
        iconResponse6.setName("????????????");
        iconResponse6.setIcon(R.mipmap.collect_ti);
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


        IconAdapter iconAdapter = new IconAdapter(R.layout.item_icon, iconResponses);
        rv_icon.setLayoutManager(new GridLayoutManager(context, 4));
        rv_icon.setAdapter(iconAdapter);
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        //1???????????? 2 ???????????? 3???????????? 4???????????? 5 ???????????????  6?????????????????? 7????????????
        rv_icon = rootView.findViewById(R.id.rv_icon);
        rv_only = rootView.findViewById(R.id.rv_only);

        //mPresenter.getQuestionBank(1 + "", 6 + "", parentId);
        listOnly = new ArrayList<>();

        onlyAdapter = new OnlyAdapter(R.layout.item_only, listOnly);
        rv_only.setLayoutManager(new LinearLayoutManager(context));
        rv_only.setAdapter(onlyAdapter);


        onlyAdapter.addChildClickViewIds(R.id.iv_zhankai, R.id.rl_only);
        onlyAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                if (view.getId() == R.id.iv_zhankai) {//??????????????????
                    gradePosition = position;
                    onFirst = 1;
                    parentId = listOnly.get(position).getId();
                    mPresenter.getQuestionBank(queryType + "", busiTypeCode + "", parentId + "");
                } else {
                    Intent intent = new Intent(getActivity(), OnlyActivity.class);
                    intent.putExtra("typeId", listOnly.get(position).getId());
                    startActivity(intent);
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

        QuestionBankResponse wallPaperResponse1 = wallPaperResponse;
        if (wallPaperResponse.getCode() == 0) {
            if (onFirst == 0) {
                listOnly.addAll(wallPaperResponse1.getData());
            } else {
                //listOnly.get(gradePosition).setTotalNumQ();
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
    public void getExamBranch(ExamBranchRsponse wallPaperResponse) {

    }

    @Override
    protected QuestionBankContract.QuestionBankPresenter createPresent() {
        return new QuestionBankContract.QuestionBankPresenter();
    }
}