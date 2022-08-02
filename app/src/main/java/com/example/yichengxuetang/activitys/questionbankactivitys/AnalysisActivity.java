package com.example.yichengxuetang.activitys.questionbankactivitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yichengxuetang.R;
import com.example.yichengxuetang.adapter.AnalysisiAdapter;
import com.example.yichengxuetang.adapter.MuluSlimuTitleAdapter;
import com.example.yichengxuetang.adapter.SmartDohomeworkAdapter;
import com.example.yichengxuetang.bean.BatchMenuListSimluResponse;
import com.example.yichengxuetang.bean.CollectQuestionSuccessResponse;
import com.example.yichengxuetang.bean.CommitSuccessResponse;
import com.example.yichengxuetang.bean.ExamBranchRsponse;
import com.example.yichengxuetang.bean.InterruptOnlyResponse;
import com.example.yichengxuetang.bean.QuestionBankResponse;
import com.example.yichengxuetang.bean.QuestionBankTypeResponse;
import com.example.yichengxuetang.bean.QuestionInfoResponse;
import com.example.yichengxuetang.bean.QuestionListResponse;
import com.example.yichengxuetang.bean.QuestionMuluResponse;
import com.example.yichengxuetang.contract.QuestionBankContract;
import com.example.yichengxuetang.utils.CustomViewPager;
import com.example.yichengxuetang.utils.RotationPageTransformer;
import com.example.yichengxuetang.utils.ScreenUtils;
import com.llw.mvplibrary.mvp.MvpActivity;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;

import java.util.ArrayList;
import java.util.List;

import static com.example.yichengxuetang.activitys.questionbankactivitys.OnlyActivity.questionList;
import static com.example.yichengxuetang.activitys.questionbankactivitys.SimultionResultActivity.dataResult;

public class AnalysisActivity extends MvpActivity<QuestionBankContract.QuestionBankPresenter> implements QuestionBankContract.QuestionBankView {

    public static ArrayList<QuestionListResponse.DataBean.DataListBean> questionListAnlaysis;
    List<BatchMenuListSimluResponse.DataBean> dataMulu;
    private AnalysisiAdapter welcomeAdapter;
    private int number = 1;
    private int totalNumAll;
    private int mulu_position;
    private int operate = 1;//	1收藏题目 2取消收藏
    private TextView tv_collect_question;
    private String typeAnalysis;
    private CustomViewPager mVp;
    private TextView tv_mulu;
    private TextView tv_num;

    @Override
    protected QuestionBankContract.QuestionBankPresenter createPresenter() {
        return new QuestionBankContract.QuestionBankPresenter();
    }

    @Override
    public void getWallPaper(QuestionBankResponse wallPaperResponse) {

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
        if (wallPaperResponse.getCode() == 0) {
            questionList.get(mulu_position).setHasCollected(!questionList.get(mulu_position).isHasCollected());
            if (questionList.get(mulu_position).isHasCollected()) {
                tv_collect_question.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.mipmap.already_collect), null, null, null);
                tv_collect_question.setText("已收藏");
            } else {
                tv_collect_question.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.mipmap.collect_question), null, null, null);
                tv_collect_question.setText("收藏");
            }
        }
    }

    @Override
    public void getQuestionMulu(QuestionMuluResponse wallPaperResponse) {

        if (wallPaperResponse.getCode() == 0) {
            List<QuestionMuluResponse.DataBean.MenusBean> menus1 = wallPaperResponse.getData().getMenus();
            for (int i = 0; i < menus1.size(); i++) {
                if (menus1.get(i).getQueationStatus() == 2) {//错误
                    for (int k = 0; k < questionList.size(); k++) {
                        if (menus1.get(i).getQuestionId().equals(questionList.get(k).getId())) {
                            questionListAnlaysis.add(questionList.get(k));
                        }
                    }
                }

            }

            welcomeAdapter = new AnalysisiAdapter(questionListAnlaysis, context, mPresenter, "错题解析");
            totalNumAll = questionListAnlaysis.size();
            if (questionListAnlaysis.get(mulu_position).isHasCollected()) {
                tv_collect_question.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.mipmap.already_collect), null, null, null);
                tv_collect_question.setText("已收藏");
            } else {
                tv_collect_question.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.mipmap.collect_question), null, null, null);
                tv_collect_question.setText("收藏");
            }

            mVp.setAdapter(welcomeAdapter);
            mVp.setPageTransformer(false, new RotationPageTransformer());
            welcomeAdapter.notifyDataSetChanged();
            String content = "<font color=\"#FE8000\">" + number + "</font>" + "/" + totalNumAll;
            tv_num.setText(Html.fromHtml(content));
        }

    }

    @Override
    public void getQuestionMuluSimlu(BatchMenuListSimluResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {
            for (int i = 0; i < dataResult.size(); i++) {
                for (int j = 0; j < dataResult.get(i).getMenus().size(); j++) {
                    List<BatchMenuListSimluResponse.DataBean.MenusBean> menus = dataResult.get(i).getMenus();
                    if (menus.get(j).getQueationStatus() == 2) {//错误
                        for (int k = 0; k < questionList.size(); k++) {
                            if (menus.get(j).getQuestionId().equals(questionList.get(k).getId())) {
                                questionListAnlaysis.add(questionList.get(k));
                            }
                        }
                    }
                }
            }
            if (typeAnalysis.equals("1")) {//错题解析
                welcomeAdapter = new AnalysisiAdapter(questionListAnlaysis, context, mPresenter, "错题解析");
                totalNumAll = questionListAnlaysis.size();
                if (questionListAnlaysis.get(mulu_position).isHasCollected()) {
                    tv_collect_question.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.mipmap.already_collect), null, null, null);
                    tv_collect_question.setText("已收藏");
                } else {
                    tv_collect_question.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.mipmap.collect_question), null, null, null);
                    tv_collect_question.setText("收藏");
                }

            } else {
                welcomeAdapter = new AnalysisiAdapter(questionList, context, mPresenter, "错题解析");
                totalNumAll = questionList.size();
                if (questionList.get(mulu_position).isHasCollected()) {
                    tv_collect_question.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.mipmap.already_collect), null, null, null);
                    tv_collect_question.setText("已收藏");
                } else {
                    tv_collect_question.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.mipmap.collect_question), null, null, null);
                    tv_collect_question.setText("收藏");
                }
            }
            String content = "<font color=\"#FE8000\">" + number + "</font>" + "/" + totalNumAll;
            tv_num.setText(Html.fromHtml(content));


            mVp.setAdapter(welcomeAdapter);
            mVp.setPageTransformer(false, new RotationPageTransformer());
            welcomeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getInterrputOnly(InterruptOnlyResponse wallPaperResponse) {

    }

    @Override
    public void getFailed(Throwable e) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mVp = findViewById(R.id.vp);
        Intent intent = getIntent();
        typeAnalysis = intent.getStringExtra("typeAnalysis");
        String batchNo = intent.getStringExtra("batchNo");
        String questionTitle = intent.getStringExtra("questionTitle");

        RelativeLayout rl_left = findViewById(R.id.rl_left);
        TextView tv_title_top = findViewById(R.id.tv_title_top);
        tv_num = findViewById(R.id.tv_num);
        tv_collect_question = findViewById(R.id.tv_collect_question);
        tv_mulu = findViewById(R.id.tv_mulu);

        rl_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_title_top.setText("错题解析");

        questionListAnlaysis = new ArrayList<>();
        dataMulu = new ArrayList<>();
        if (dataResult != null) {
           /* for (int i = 0; i < dataResult.size(); i++) {
                for (int j = 0; j < dataResult.get(i).getMenus().size(); j++) {
                    List<BatchMenuListSimluResponse.DataBean.MenusBean> menus = dataResult.get(i).getMenus();
                    if (menus.get(j).getQueationStatus() == 2) {//错误
                        for (int k = 0; k < questionList.size(); k++) {
                            if (menus.get(j).getQuestionId().equals(questionList.get(k).getId())) {
                                questionListAnlaysis.add(questionList.get(k));
                            }
                        }
                    }
                }
            }*/
            mPresenter.getQuestionMuluSimlu(batchNo);
        } else {
            mPresenter.getQuestionMulu(batchNo);
        }


     /*   if (typeAnalysis.equals("1")) {//错题解析
            welcomeAdapter = new AnalysisiAdapter(questionListAnlaysis, context, mPresenter, "错题解析");
            totalNumAll = questionListAnlaysis.size();
            if (questionListAnlaysis.get(mulu_position).isHasCollected()) {
                tv_collect_question.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.mipmap.already_collect), null, null, null);
                tv_collect_question.setText("已收藏");
            } else {
                tv_collect_question.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.mipmap.collect_question), null, null, null);
                tv_collect_question.setText("收藏");
            }


        } else {
            welcomeAdapter = new AnalysisiAdapter(questionList, context, mPresenter, "错题解析");
            totalNumAll = questionList.size();
            if (questionList.get(mulu_position).isHasCollected()) {
                tv_collect_question.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.mipmap.already_collect), null, null, null);
                tv_collect_question.setText("已收藏");
            } else {
                tv_collect_question.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.mipmap.collect_question), null, null, null);
                tv_collect_question.setText("收藏");
            }
        }
        String content = "<font color=\"#FE8000\">" + number + "</font>" + "/" + totalNumAll;
        tv_num.setText(Html.fromHtml(content));


        mVp.setAdapter(welcomeAdapter);
        mVp.setPageTransformer(false, new RotationPageTransformer());
        welcomeAdapter.notifyDataSetChanged();*/


        //收藏题目
        tv_collect_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typeAnalysis.equals("1")) {
                    if (questionListAnlaysis.get(mulu_position).isHasCollected()) {//已收藏
                        operate = 2;
                    } else {
                        operate = 1;
                    }
                    mPresenter.getCollectQuestion(questionListAnlaysis.get(mulu_position).getId(), operate);
                } else {
                    if (questionList.get(mulu_position).isHasCollected()) {//已收藏
                        operate = 2;
                    } else {
                        operate = 1;
                    }
                    mPresenter.getCollectQuestion(questionList.get(mulu_position).getId(), operate);
                }

            }
        });

        //题目目录
        tv_mulu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new XPopup.Builder(context)
                        .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                        .enableDrag(true)
                        .maxHeight(ScreenUtils.getScreenHeight(context) * 3 / 4)
                        .asCustom(new MuluSimluPopup(context, dataMulu, number))
                        .show();
            }
        });


        mVp.setPageChangeListener(new CustomViewPager.PageStateChangeListener() {
            private int currentPosition = 0;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mulu_position = position;
                number = position + 1;
                String content = "<font color=\"#FE8000\">" + number + "</font>" + "/" + totalNumAll;
                tv_num.setText(Html.fromHtml(content));
                if (questionList.get(position).isHasCollected()) {
                    tv_collect_question.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.mipmap.already_collect), null, null, null);
                    tv_collect_question.setText("已收藏");
                } else {
                    tv_collect_question.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.mipmap.collect_question), null, null, null);
                    tv_collect_question.setText("收藏");
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

            @Override
            public void onSlideDirection(int slideState) {

            }
        });
    }


    public class MuluSimluPopup extends BottomPopupView {
        private Context context;
        private List<BatchMenuListSimluResponse.DataBean> listBeans;
        private int number;

        public MuluSimluPopup(@NonNull Context context, List<BatchMenuListSimluResponse.DataBean> courseTypeList, int number) {
            super(context);
            this.context = context;
            this.listBeans = courseTypeList;
            this.number = number;
        }

        @Override
        protected int getImplLayoutId() {
            return R.layout.question_mulu_simlu_pop;
        }

        @Override
        protected void onCreate() {
            super.onCreate();
            RecyclerView rv_mulu_title = findViewById(R.id.rv_mulu_title);

            MuluSlimuTitleAdapter muluSlimuTitleAdapter = new MuluSlimuTitleAdapter(R.layout.item_simlu_title, listBeans, number);
            rv_mulu_title.setLayoutManager(new LinearLayoutManager(context));
            rv_mulu_title.setAdapter(muluSlimuTitleAdapter);

        }

        //完全可见执行
        @Override
        protected void onShow() {
            super.onShow();
            Log.e("tag", "知乎评论 onShow");
        }

        //完全消失执行
        @Override
        protected void onDismiss() {
            Log.e("tag", "知乎评论 onDismiss");
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_analysis;
    }
}