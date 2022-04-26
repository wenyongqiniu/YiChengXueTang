package com.example.yichengxuetang.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yichengxuetang.R;
import com.example.yichengxuetang.activitys.questionbankactivitys.OrderExerciseActivity;
import com.example.yichengxuetang.activitys.questionbankactivitys.TestExerciseActivity;
import com.example.yichengxuetang.utils.ScreenUtils;
import com.llw.mvplibrary.network.utils.StatusBarUtils;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.necer.ndialog.ChoiceDialog;

import java.util.Objects;


public class QuestionBankFragment extends Fragment implements OnSelectListener {


    public QuestionBankFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question_bank, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            StatusBarUtils.setColor(getActivity(), Color.parseColor("#FFF7ED"));
        } else {
            StatusBarUtils.setColor(getActivity(), Color.parseColor("#F8F8F8"));
        }
        StatusBarUtils.setTextDark(getActivity(), true);
    }


    private void initView(View view) {
        StatusBarUtils.setColor(getActivity(), Color.parseColor("#FFF7ED"));
        StatusBarUtils.setTextDark(getActivity(), true);
        RelativeLayout rl_order_question = view.findViewById(R.id.rl_order_question);
        RelativeLayout rl_test = view.findViewById(R.id.rl_test);
        TextView tv_course_type = view.findViewById(R.id.tv_course_type);
        rl_order_question.setOnClickListener(v -> startActivity(new Intent(getActivity(), OrderExerciseActivity.class)));
        rl_test.setOnClickListener(v -> startActivity(new Intent(getActivity(), TestExerciseActivity.class)));
        tv_course_type.setOnClickListener(v -> showCourseType());

    }

    private void showCourseType() {
        new XPopup.Builder(getActivity())
                .maxHeight(ScreenUtils.getScreenHeight(Objects.requireNonNull(getActivity())) *2/ 5)
                .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                .enableDrag(true)
                .asBottomList("课程选择", new String[]{"家庭教育指导师实操课", "淘宝无货源实操课"}, new int[]{R.mipmap.checked},this)
                .show();
    }

    @Override
    public void onSelect(int position, String text) {

    }
}