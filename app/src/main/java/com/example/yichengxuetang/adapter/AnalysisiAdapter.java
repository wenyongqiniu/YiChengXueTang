package com.example.yichengxuetang.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.example.yichengxuetang.R;
import com.example.yichengxuetang.activitys.questionbankactivitys.OnlyActivity;
import com.example.yichengxuetang.bean.CommitQuestionAnswerResponse;
import com.example.yichengxuetang.bean.QuestionListResponse;
import com.example.yichengxuetang.contract.QuestionBankContract;
import com.example.yichengxuetang.utils.WebViewSeetings;
import com.just.agentweb.AgentWebView;

import java.util.ArrayList;

/*

欢迎页适配器
 */
public class AnalysisiAdapter extends PagerAdapter {

    private ArrayList<QuestionListResponse.DataBean.DataListBean> list;
    private Context context;
    private QuestionBankContract.QuestionBankPresenter mPresenter;
    private String answerCode = "";
    private String questionTitle;
    public static RelativeLayout rl_anlisy;


    public AnalysisiAdapter(ArrayList<QuestionListResponse.DataBean.DataListBean> list, Context context, QuestionBankContract.QuestionBankPresenter mPresenter, String questionTitle) {
        this.list = list;
        this.context = context;
        this.mPresenter = mPresenter;
        this.questionTitle = questionTitle;

    }

   /* @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }*/

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = View.inflate(context, R.layout.item_vp_exam, null);
        QuestionListResponse.DataBean.DataListBean dataBean = list.get(position);

        WebView mTvTitle = view.findViewById(R.id.tv_quest_title);
        RecyclerView rv_answer_list = view.findViewById(R.id.rv_answer_list);
        TextView tv_right_answer = view.findViewById(R.id.tv_right_answer);
        TextView tv_your_answer = view.findViewById(R.id.tv_your_answer);
        TextView tv_choice_type = view.findViewById(R.id.tv_choice_type);
        AgentWebView tv_anli = view.findViewById(R.id.tv_anli);
        rl_anlisy = view.findViewById(R.id.rl_anlisy);
        TextView tv_confirm = view.findViewById(R.id.tv_confirm);
        if (dataBean.getQuestion() != null) {
            mTvTitle.loadDataWithBaseURL(null, WebViewSeetings.setWebVIewImage(dataBean.getQuestion()), "text/html", "utf-8", null);
        }

        String content = "正确答案：" + "<font color=\"#50AE0C\">" + dataBean.getRightAnswer() + "</font>";
        String content1 = "你的答案：" + "<font color=\"#EC1111\">" + dataBean.getCustomerAnswer() + "</font>";

        tv_right_answer.setText(Html.fromHtml(content));
        tv_your_answer.setText(Html.fromHtml(content1));
        tv_anli.loadDataWithBaseURL(null, WebViewSeetings.setWebVIewImage(dataBean.getAnalysis()), "text/html", "utf-8", null);

        QuestionAnalysisAdapter questionAnswerAdapter = new QuestionAnalysisAdapter(R.layout.item_question_list, dataBean.getAnswerList(), dataBean, mPresenter, rl_anlisy, tv_your_answer, questionTitle, position);
        rv_answer_list.setLayoutManager(new LinearLayoutManager(context));
        rv_answer_list.setAdapter(questionAnswerAdapter);

        if (dataBean.getQuestionType() == 2) {//多选需要确认按钮
            tv_confirm.setVisibility(View.VISIBLE);
            tv_choice_type.setText("多选");
            tv_choice_type.setTextColor(Color.parseColor("#4C8EEE"));
            tv_choice_type.setBackgroundResource(R.drawable.double_stype_shape);
        } else {
            tv_confirm.setVisibility(View.GONE);
            tv_choice_type.setText("单选");
            tv_choice_type.setTextColor(Color.parseColor("#E96A49"));
            tv_choice_type.setBackgroundResource(R.drawable.single_stype_shape);
        }
        tv_confirm.setVisibility(View.GONE);

        //确认答案
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CommitQuestionAnswerResponse commitQuestionAnswerResponse = new CommitQuestionAnswerResponse();
                commitQuestionAnswerResponse.setBatchNo(OnlyActivity.batchNo);
                commitQuestionAnswerResponse.setQuestionId(OnlyActivity.questionId);
                ArrayList<String> answerList = new ArrayList<>();
                for (int i = 0; i < dataBean.getAnswerList().size(); i++) {
                    if (dataBean.getAnswerList().get(i).isChecked()) {
                        answerList.add(dataBean.getAnswerList().get(i).getAnswerCode());
                        if (answerCode.equals("")) {
                            answerCode = dataBean.getAnswerList().get(i).getAnswerCode();
                        } else {
                            answerCode = answerCode + "," + dataBean.getAnswerList().get(i).getAnswerCode();

                        }
                        dataBean.setCustomerAnswer(answerCode);

                    }
                }
                commitQuestionAnswerResponse.setAnswerCodeList(answerList);
                mPresenter.getSubmitQuestionAnswer(commitQuestionAnswerResponse);
                tv_your_answer.setText("你的答案：" + answerCode);
                answerCode = "";

                tv_confirm.setVisibility(View.GONE);
                questionAnswerAdapter.notifyDataSetChanged();
            }
        });
        container.addView(view);
        return view;
    }
}

