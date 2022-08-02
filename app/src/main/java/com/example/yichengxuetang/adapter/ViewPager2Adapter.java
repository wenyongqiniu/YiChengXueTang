package com.example.yichengxuetang.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yichengxuetang.R;
import com.example.yichengxuetang.activitys.questionbankactivitys.OnlyActivity;
import com.example.yichengxuetang.bean.CommitQuestionAnswerResponse;
import com.example.yichengxuetang.bean.QuestionListResponse;
import com.example.yichengxuetang.contract.QuestionBankContract;
import com.example.yichengxuetang.utils.WebViewSeetings;
import com.just.agentweb.AgentWebView;

import java.util.ArrayList;
import java.util.List;

public class ViewPager2Adapter extends RecyclerView.Adapter<ViewPager2Adapter.ViewPager2ViewHolder> {
    private static final String TAG = "ViewPager2Adapter";
    private List<QuestionListResponse.DataBean.DataListBean> list_vp2_data;
    private Context context;
    private QuestionBankContract.QuestionBankPresenter mPresenter;
    private String answerCode = "";
    private String questionTitle;
    public static RelativeLayout rl_anlisy;


    public ViewPager2Adapter(ArrayList<QuestionListResponse.DataBean.DataListBean> list, Context context, QuestionBankContract.QuestionBankPresenter mPresenter, String questionTitle) {
        this.list_vp2_data = list;
        this.context = context;
        this.mPresenter = mPresenter;
        this.questionTitle = questionTitle;

    }

    @NonNull
    @Override
    public ViewPager2Adapter.ViewPager2ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_vp_exam, parent, false);
        return new ViewPager2ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPager2Adapter.ViewPager2ViewHolder holder, int position) {

        QuestionListResponse.DataBean.DataListBean dataBean = list_vp2_data.get(position);

        if (dataBean.getQuestion() != null) {
            holder.mTvTitle.loadDataWithBaseURL(null, WebViewSeetings.setWebVIewImage(dataBean.getQuestion()), "text/html", "utf-8", null);
        }

        String content = "正确答案：" + "<font color=\"#50AE0C\">" + dataBean.getRightAnswer() + "</font>";
        String content1 = "你的答案：" + "<font color=\"#EC1111\">" + dataBean.getCustomerAnswer() + "</font>";

        holder.tv_right_answer.setText(Html.fromHtml(content));
        holder.tv_your_answer.setText(Html.fromHtml(content1));
        holder.tv_anli.loadDataWithBaseURL(null, WebViewSeetings.setWebVIewImage(dataBean.getAnalysis()), "text/html", "utf-8", null);

        QuestionAnswerAdapter questionAnswerAdapter = new QuestionAnswerAdapter(R.layout.item_question_list, dataBean.getAnswerList(), dataBean, mPresenter, rl_anlisy, holder.tv_your_answer, questionTitle, position);
        holder.rv_answer_list.setLayoutManager(new LinearLayoutManager(context));
        holder.rv_answer_list.setAdapter(questionAnswerAdapter);

        if (dataBean.getQuestionType() == 2) {//多选需要确认按钮
            holder.tv_confirm.setVisibility(View.VISIBLE);
            holder.tv_choice_type.setText("多选");
            holder.tv_choice_type.setTextColor(Color.parseColor("#4C8EEE"));
            holder.tv_choice_type.setBackgroundResource(R.drawable.double_stype_shape);
        } else {
            holder.tv_confirm.setVisibility(View.GONE);
            holder.tv_choice_type.setText("单选");
            holder.tv_choice_type.setTextColor(Color.parseColor("#E96A49"));
            holder.tv_choice_type.setBackgroundResource(R.drawable.single_stype_shape);
        }

        //确认答案
        holder.tv_confirm.setOnClickListener(new View.OnClickListener() {
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
                holder.tv_your_answer.setText("你的答案：" + answerCode);
                answerCode = "";

                holder.tv_confirm.setVisibility(View.GONE);
                questionAnswerAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_vp2_data.size();
    }

    public class ViewPager2ViewHolder extends RecyclerView.ViewHolder {

        private final WebView mTvTitle;
        private final RecyclerView rv_answer_list;
        private final TextView tv_right_answer;
        private final TextView tv_your_answer;
        private final TextView tv_choice_type;
        private final AgentWebView tv_anli;
        private final TextView tv_confirm;

        public ViewPager2ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvTitle = itemView.findViewById(R.id.tv_quest_title);
            rv_answer_list = itemView.findViewById(R.id.rv_answer_list);
            tv_right_answer = itemView.findViewById(R.id.tv_right_answer);
            tv_your_answer = itemView.findViewById(R.id.tv_your_answer);
            tv_choice_type = itemView.findViewById(R.id.tv_choice_type);
            tv_anli = itemView.findViewById(R.id.tv_anli);
            rl_anlisy = itemView.findViewById(R.id.rl_anlisy);
            tv_confirm = itemView.findViewById(R.id.tv_confirm);
        }
    }
}
