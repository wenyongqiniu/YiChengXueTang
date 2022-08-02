package com.example.yichengxuetang.adapter;

import android.graphics.Color;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.yichengxuetang.R;
import com.example.yichengxuetang.activitys.questionbankactivitys.OnlyActivity;
import com.example.yichengxuetang.bean.CommitQuestionAnswerResponse;
import com.example.yichengxuetang.bean.HomeWorkResponse;
import com.example.yichengxuetang.bean.QuestionInfoResponse;
import com.example.yichengxuetang.bean.QuestionListResponse;
import com.example.yichengxuetang.contract.QuestionBankContract;
import com.example.yichengxuetang.utils.WebViewSeetings;
import com.google.gson.Gson;
import com.just.agentweb.AgentWebView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.example.yichengxuetang.activitys.questionbankactivitys.OnlyActivity.chjTimer;
import static com.example.yichengxuetang.activitys.questionbankactivitys.OnlyActivity.mVp;
import static com.example.yichengxuetang.activitys.questionbankactivitys.OnlyActivity.totalTime;

public class QuestionAnswerAdapter extends BaseQuickAdapter<QuestionListResponse.DataBean.DataListBean.AnswerListBean, BaseViewHolder> {

    private int checkedPosition = -1;
    private QuestionListResponse.DataBean.DataListBean dataBeans;
    private QuestionBankContract.QuestionBankPresenter mPresenter;
    private RelativeLayout mrl;
    private TextView tv_your_answer;
    private String questionTitle;
    private int position;
    //声明一个boolean，因为addOnGlobalLayoutListener会重复执行，控制它启动后只执行一次
    private boolean isCompletedDraw = false;

    public QuestionAnswerAdapter(int layoutResId, @Nullable List<QuestionListResponse.DataBean.DataListBean.AnswerListBean> data, QuestionListResponse.DataBean.DataListBean dataBean, QuestionBankContract.QuestionBankPresenter mPresenter, RelativeLayout rl_anlisy, TextView tv_your_answer, String questionTitle, int position) {
        super(layoutResId, data);
        dataBeans = dataBean;
        this.mPresenter = mPresenter;
        this.mrl = rl_anlisy;
        this.tv_your_answer = tv_your_answer;
        this.questionTitle = questionTitle;
        this.position = position;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, QuestionListResponse.DataBean.DataListBean.AnswerListBean dataBean) {

        //ImageView iv_checked = baseViewHolder.getView(R.id.iv_checked);
        AgentWebView tv_title_question = baseViewHolder.getView(R.id.tv_title_question);
        ImageView iv_checked = baseViewHolder.getView(R.id.iv_checked);
        RelativeLayout rl_check = baseViewHolder.getView(R.id.rl_check);
        TextView tv_title_question1 = baseViewHolder.getView(R.id.tv_title_question1);
        tv_title_question.loadDataWithBaseURL(null, WebViewSeetings.setWebVIewImage(dataBean.getAnswerCode() + "." + dataBean.getAnswer()), "text/html", "utf-8", null);
        tv_title_question.setBackgroundColor(Color.parseColor("#FFFFFF"));
        rl_check.setBackgroundColor(Color.parseColor("#FFFFFF"));

     /*   String substring = dataBean.getAnswer().substring(0, 2);
        String s1 = substring + dataBean.getAnswerCode() + dataBean.getAnswer().substring(2);
        tv_title_question.loadDataWithBaseURL(null, WebViewSeetings.setWebVIewImage(s1), "text/html", "utf-8", null);*/


        if ("".equals(questionTitle)) {//代表模拟考试不要出答案样式
            //	1单选 2多选 3填空
            if (dataBeans.getQuestionType() == 2) {//多选
                iv_checked.setBackgroundResource(R.mipmap.un_checked_single);
                rl_check.setBackgroundResource(R.drawable.answer_shape2);
                tv_title_question.setBackgroundColor(Color.parseColor("#FFFFFF"));
                if (dataBean.isChecked()) {
                    dataBean.setSelected(true);
                    iv_checked.setBackgroundResource(R.mipmap.checked_single);
                    rl_check.setBackgroundResource(R.drawable.answer_shape);
                    tv_title_question.setBackgroundColor(Color.parseColor("#FFFBF6"));
                } else {
                    iv_checked.setBackgroundResource(R.mipmap.un_checked_single);
                    rl_check.setBackgroundResource(R.drawable.answer_shape2);
                    tv_title_question.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    dataBean.setSelected(false);
                }
                if (dataBeans.getCustomerAnswer() != null) {
                    String[] split = dataBeans.getCustomerAnswer().split(",");
                    for (String s : split) {
                        if (s.equals(dataBean.getAnswerCode())) {
                            iv_checked.setBackgroundResource(R.mipmap.checked_single);
                            rl_check.setBackgroundResource(R.drawable.answer_shape);
                            tv_title_question.setBackgroundColor(Color.parseColor("#FFFBF6"));
                        }

                    }
                }
                rl_check.setVisibility(View.VISIBLE);

            } else {//单选
                iv_checked.setBackgroundResource(R.mipmap.un_checked_single);
                rl_check.setBackgroundResource(R.drawable.answer_shape2);
                tv_title_question.setBackgroundColor(Color.parseColor("#FFFFFF"));
                if (checkedPosition != -1) {
                    if (baseViewHolder.getLayoutPosition() == checkedPosition) {
                        iv_checked.setBackgroundResource(R.mipmap.checked_single);
                        rl_check.setBackgroundResource(R.drawable.answer_shape);
                        tv_title_question.setBackgroundColor(Color.parseColor("#FFFBF6"));
                        dataBean.setSelected(true);
                        dataBean.setChecked(true);
                    } else {
                        iv_checked.setBackgroundResource(R.mipmap.un_checked_single);
                        rl_check.setBackgroundResource(R.drawable.answer_shape2);
                        tv_title_question.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        dataBean.setSelected(false);
                        dataBean.setChecked(false);
                    }
                } else {
                    if (dataBean.isChecked() || dataBean.getAnswerCode().equals(dataBeans.getCustomerAnswer())) {
                        iv_checked.setBackgroundResource(R.mipmap.checked_single);
                        rl_check.setBackgroundResource(R.drawable.answer_shape);
                        tv_title_question.setBackgroundColor(Color.parseColor("#FFFBF6"));

                    } else {
                        iv_checked.setBackgroundResource(R.mipmap.un_checked_single);
                        rl_check.setBackgroundResource(R.drawable.answer_shape2);
                        tv_title_question.setBackgroundColor(Color.parseColor("#FFFFFF"));

                    }
                }
                rl_check.setVisibility(View.VISIBLE);
            }
        } else {
            if (dataBeans.getCustomerAnswer() != null) {//代表用户做过此题，需要渲染答案解析
                if (dataBeans.getQuestionType() == 1) {//单选
                    if (dataBeans.getCustomerAnswer().equals(dataBean.getAnswerCode())) {
                        if (dataBeans.getCustomerAnswer().equals(dataBeans.getRightAnswer())) {
                            iv_checked.setBackgroundResource(R.mipmap.question_single_true);
                            rl_check.setBackgroundResource(R.drawable.question_answer_shape);
                            tv_title_question.setBackgroundColor(Color.parseColor("#F1FCE2"));
                        } else {
                            iv_checked.setBackgroundResource(R.mipmap.question_error);
                            rl_check.setBackgroundResource(R.drawable.question_answer_error_shape);
                            tv_title_question.setBackgroundColor(Color.parseColor("#FFF6F6"));
                        }
                    } else {
                        iv_checked.setBackgroundResource(R.mipmap.un_checked_single);
                        rl_check.setBackgroundResource(R.drawable.answer_shape2);
                        tv_title_question.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    }

                } else {
                    iv_checked.setBackgroundResource(R.mipmap.un_checked_single);
                    rl_check.setBackgroundResource(R.drawable.answer_shape2);
                    tv_title_question.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    String[] split = dataBeans.getRightAnswer().split(",");
                    String[] split1 = dataBeans.getCustomerAnswer().split(",");

                    for (String s : split) {
                        if (s.equals(dataBean.getAnswerCode())) {
                            iv_checked.setBackgroundResource(R.mipmap.question_single_true);
                            rl_check.setBackgroundResource(R.drawable.question_answer_shape);
                            tv_title_question.setBackgroundColor(Color.parseColor("#F1FCE2"));

                        }

                    }
                    if (dataBean.isChecked()) {
                        for (String s : split1) {
                            if (s.equals(dataBean.getAnswerCode()) && dataBeans.getRightAnswer().contains(s)) {
                                iv_checked.setBackgroundResource(R.mipmap.question_single_true);
                                rl_check.setBackgroundResource(R.drawable.question_answer_shape);
                                tv_title_question.setBackgroundColor(Color.parseColor("#F1FCE2"));
                                break;
                            } else {
                                iv_checked.setBackgroundResource(R.mipmap.question_error);
                                rl_check.setBackgroundResource(R.drawable.question_answer_error_shape);
                                tv_title_question.setBackgroundColor(Color.parseColor("#FFF6F6"));
                            }

                        }
                    }

                }
                mrl.setVisibility(View.VISIBLE);
            } else {
                //	1单选 2多选 3填空
                if (dataBeans.getQuestionType() == 2) {//多选
                    iv_checked.setBackgroundResource(R.mipmap.un_checked_single);
                    rl_check.setBackgroundResource(R.drawable.answer_shape2);
                    tv_title_question.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    if (dataBean.isChecked()) {
                        dataBean.setSelected(true);
                        iv_checked.setBackgroundResource(R.mipmap.checked_single);
                        rl_check.setBackgroundResource(R.drawable.answer_shape);
                        tv_title_question.setBackgroundColor(Color.parseColor("#FFFBF6"));
                    } else {
                        iv_checked.setBackgroundResource(R.mipmap.un_checked_single);
                        rl_check.setBackgroundResource(R.drawable.answer_shape2);
                        tv_title_question.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        dataBean.setSelected(false);
                    }
                    rl_check.setVisibility(View.VISIBLE);

                } else {//单选
                    iv_checked.setBackgroundResource(R.mipmap.un_checked_single);
                    rl_check.setBackgroundResource(R.drawable.answer_shape2);
                    tv_title_question.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    if (checkedPosition != -1) {
                        if (baseViewHolder.getLayoutPosition() == checkedPosition) {
                            iv_checked.setBackgroundResource(R.mipmap.checked_single);
                            rl_check.setBackgroundResource(R.drawable.answer_shape);
                            tv_title_question.setBackgroundColor(Color.parseColor("#FFFBF6"));
                            dataBean.setSelected(true);
                            dataBean.setChecked(true);
                        } else {
                            iv_checked.setBackgroundResource(R.mipmap.un_checked_single);
                            rl_check.setBackgroundResource(R.drawable.answer_shape2);
                            tv_title_question.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            dataBean.setSelected(false);
                            dataBean.setChecked(false);
                        }
                    } else {
                        if (dataBean.isChecked()) {
                            iv_checked.setBackgroundResource(R.mipmap.checked_single);
                            rl_check.setBackgroundResource(R.drawable.answer_shape);
                            tv_title_question.setBackgroundColor(Color.parseColor("#FFFBF6"));

                        } else {
                            iv_checked.setBackgroundResource(R.mipmap.un_checked_single);
                            rl_check.setBackgroundResource(R.drawable.answer_shape2);
                            tv_title_question.setBackgroundColor(Color.parseColor("#FFFFFF"));

                        }
                    }
                    rl_check.setVisibility(View.VISIBLE);
                }
            }
        }


        //	题目类型1单选 2多选 3填空题
        tv_title_question1.setOnClickListener(view ->

        {
            if (dataBeans.getCustomerAnswer() == null) {//题目未作答才需要
                if (dataBeans.getQuestionType() == 1) {
                    totalTime = String.valueOf(chjTimer.stopTimer());
                    checkedPosition = baseViewHolder.getLayoutPosition();
                    QuestionAnswerAdapter.this.notifyDataSetChanged();
                    CommitQuestionAnswerResponse commitQuestionAnswerResponse = new CommitQuestionAnswerResponse();
                    commitQuestionAnswerResponse.setBatchNo(OnlyActivity.batchNo);
                    commitQuestionAnswerResponse.setQuestionId(OnlyActivity.questionId);
                    commitQuestionAnswerResponse.setTotalTime(totalTime);
                    ArrayList<String> answerList = new ArrayList<>();
                    answerList.add(dataBean.getAnswerCode());
                    commitQuestionAnswerResponse.setAnswerCodeList(answerList);
                    mPresenter.getSubmitQuestionAnswer(commitQuestionAnswerResponse);
                    mrl.setVisibility(View.VISIBLE);
                    dataBeans.setCustomerAnswer(dataBean.getAnswerCode());
                    String content = "你的答案：" + "<font color=\"#EC1111\">" + dataBean.getAnswerCode() + "</font>";
                    tv_your_answer.setText(Html.fromHtml(content));
                } else if (dataBeans.getQuestionType() == 2) {
                    dataBean.setChecked(!dataBean.isChecked());
                    QuestionAnswerAdapter.this.notifyDataSetChanged();
                }
            }
        });
        if ("".equals(questionTitle)) {//代表是模拟考试，不需要出解析
            mrl.setVisibility(View.GONE);
        }
    }

    @Override
    public void onViewAttachedToWindow(@NotNull BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        AgentWebView tv_title_question = holder.getView(R.id.tv_title_question);
        int height = tv_title_question.getHeight();
        TextView tv_title_question1 = holder.getView(R.id.tv_title_question1);
        tv_title_question1.setHeight(height);
    }
}
