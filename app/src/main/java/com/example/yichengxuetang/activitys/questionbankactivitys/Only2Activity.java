package com.example.yichengxuetang.activitys.questionbankactivitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.yichengxuetang.R;
import com.example.yichengxuetang.adapter.MuluAdapter;
import com.example.yichengxuetang.adapter.MuluSlimuTitleAdapter;
import com.example.yichengxuetang.adapter.SmartDohomeworkAdapter;
import com.example.yichengxuetang.adapter.ViewPager2Adapter;
import com.example.yichengxuetang.adapter.ViewPager2Adapter2;
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
import com.example.yichengxuetang.utils.ChjTimer;
import com.example.yichengxuetang.utils.CountTimeUtils;
import com.example.yichengxuetang.utils.ScreenUtils;
import com.ljb.page.PageState;
import com.ljb.page.PageStateLayout;
import com.llw.mvplibrary.mvp.MvpActivity;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.lxj.xpopup.interfaces.OnCancelListener;
import com.lxj.xpopup.interfaces.OnConfirmListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.yichengxuetang.application.MyApplication.getActivityManager;

public class Only2Activity extends MvpActivity<QuestionBankContract.QuestionBankPresenter> implements QuestionBankContract.QuestionBankView {

    private int examType = 1;
    public static ViewPager2 mVp;
    private SmartDohomeworkAdapter welcomeAdapter;
    private ArrayList<QuestionInfoResponse.DataBean> questionList;
    public static String batchNo = "";
    public static String questionId = "";
    public static String lastQuestionId = "";
    private int number = 0;
    private int totalNum;
    private TextView tv_num;
    private TextView tv_collect_question;
    private TextView tv_mulu;
    private TextView tv_commit_all;
    private boolean isFirst;
    private int slideStates;
    //private QuestionInfoResponse.DataBean questionBean;
    private int operate = 1;//	1收藏题目 2取消收藏
    private String totalTime;
    private ChjTimer chjTimer;
    private BasePopupView show;
    public static boolean quite;
    public static String questionTitle;
    public static int dissmiss;
    private CountTimeUtils countTimeUtils;
    private String time;
    private TextView tv_count_time;
    private int pageNo = 1;
    private final int pageSize = 10;
    private int mulu_position = 0;
    private PageStateLayout page_layout;
    private int pauseTime;

    private TextView tv_title_top;
    private LoadingPopupView loadingPopupView;
    private int totalNumAll;
    private QuestionListResponse.DataBean questionData;

    private int questionNotAnswerNum = 0;
    private int useTime;
    private ViewPager2Adapter2 welcomeAdapter1;
    private boolean newBatch;

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


    /**
     * 监听Back键按下事件,方法2:
     * 注意:
     * 返回值表示:是否能完全处理该事件
     * 在此处返回false,所以会继续传播该事件.
     * 在具体项目中此处的返回值视情况而定.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (quite) {//只有做过题才会弹出
                if ("".equals(questionTitle)) {
                    new XPopup.Builder(context)
                            .dismissOnTouchOutside(false)
                            .autoDismiss(true)
                            .asConfirm("提示", "是否保存本次记录并退出？", "继续答题", "确定退出", new OnConfirmListener() {
                                @Override
                                public void onConfirm() {
                                    mPresenter.getPauseBatch(batchNo, pauseTime + "");
                                    finish();
                                }
                            }, new OnCancelListener() {
                                @Override
                                public void onCancel() {

                                }
                            }, false)
                            .show();

                } else if ("智能刷题".equals(questionTitle)) {
                    totalTime = String.valueOf(chjTimer.stopTimer());
                    Intent intent = new Intent(context, SmartResultActivity.class);
                    intent.putExtra("batchNo", batchNo);
                    intent.putExtra("totalTime", totalTime);
                    startActivity(intent);
                    finish();

                } else {
                    totalTime = String.valueOf(chjTimer.stopTimer());
                    mPresenter.getInterruptOnly(batchNo, totalTime);
                }
            } else {
                finish();
            }
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void getQuestionInfo(QuestionInfoResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {
            QuestionInfoResponse.DataBean data = wallPaperResponse.getData();
            questionId = data.getNextId();
            QuestionInfoResponse.DataBean dataBean = questionList.get(number);
            dataBean.setQuestion(data.getQuestion());
            dataBean.setQuestionType(data.getQuestionType());
            dataBean.setAnalysis(data.getAnalysis());
            dataBean.setHasCollected(data.isHasCollected());
            dataBean.setRightAnswer(data.getRightAnswer());
            dataBean.setAnswerList(data.getAnswerList());
            dataBean.setId(data.getId());
            totalNumAll = data.getTotalNum();
            String content = "<font color=\"#FE8000\">" + number + "</font>" + "/" + totalNumAll;
            tv_num.setText(Html.fromHtml(content));
            welcomeAdapter1.notifyDataSetChanged();
            mVp.setCurrentItem(number);
            page_layout.setPage(PageState.STATE_SUCCESS);

            /*if (questionList.size() < totalNumAll) {//请求完s所有分页数据
                mPresenter.getQuestionInfo(batchNo, questionId);
                //loadingPopupView.show();
            } else {//所有数据加载完毕，如果有记录需要跳到指定题目
                for (int i = 0; i < questionList.size(); i++) {
                    if (questionList.get(i).getId().equals(lastQuestionId)) {
                        //loadingPopupView.dismiss();
                        mVp.setCurrentItem(i);
                    }

                }
            }*/
        }

    }

    @Override
    public void getQuestionInfoList(QuestionListResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {
/*
            questionData = wallPaperResponse.getData();
            questionList.addAll(questionData.getDataList());
            questionId = questionList.get(number - 1).getId();
            welcomeAdapter.notifyDataSetChanged();
            totalNum = questionList.size();
            totalNumAll = Integer.parseInt(questionData.getTotalCount());
            String content = "<font color=\"#FE8000\">" + number + "</font>" + "/" + questionData.getTotalCount();
            tv_num.setText(Html.fromHtml(content));
            if (questionList.get(number - 1).isHasCollected()) {
                tv_collect_question.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.mipmap.already_collect), null, null, null);
                tv_collect_question.setText("已收藏");
            } else {
                tv_collect_question.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.mipmap.collect_question), null, null, null);
                tv_collect_question.setText("收藏");
            }
            page_layout.setPage(PageState.STATE_SUCCESS);
            if (questionList.size() < totalNumAll) {//请求完s所有分页数据
                pageNo++;
                mPresenter.getQuestionInfoList(batchNo, pageNo, pageSize);
                //loadingPopupView.show();
            } else {//所有数据加载完毕，如果有记录需要跳到指定题目
                for (int i = 0; i < questionList.size(); i++) {
                    if (questionList.get(i).getId().equals(lastQuestionId)) {
                        //loadingPopupView.dismiss();
                        mVp.setCurrentItem(i);
                    }
                }
            }
        } else {
            page_layout.setPage(PageState.STATE_EMPTY);*/
        }

    }

    @Override
    public void getExamBranch(ExamBranchRsponse wallPaperResponse) {
        if ("".equals(questionTitle)) {//

        } else {
            ExamBranchRsponse.DataBean data = wallPaperResponse.getData();
            questionId = data.getQuestionId();
            lastQuestionId = data.getQuestionId();
            batchNo = data.getBatchNo();
            mPresenter.getQuestionInfoList(batchNo, pageNo, pageSize);
        }
    }

    @Override
    public void getQuestionSubmit(CommitSuccessResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {//答案提交成功，自动跳下一题
            if ("".equals(questionTitle)) {
               /* questionId = questionList.get(position).getId();
                lastQuestionId = questionList.get(position).getId();
                number = position + 1;
                mulu_position = position*/
                ;
                mPresenter.getQuestionInfo(batchNo, questionId);
                //number++;
                // mVp.setCurrentItem(number);
            } else {
                if (questionList.get(number - 1).getCustomerAnswer().equals(questionList.get(number - 1).getRightAnswer())) {//如果做对了自动跳下一题
                    mVp.setCurrentItem(number);
                }
            }
            quite = true;
            if (number == questionList.size()) {//最后一题直接跳结果页
                if ("".equals(questionTitle)) {//模拟考试
                    for (int i = 0; i < questionList.size(); i++) {
                        if (questionList.get(i).getCustomerAnswer() == null) {
                            questionNotAnswerNum++;
                        }
                    }

                    String content = "您还有" + "<font color=\"#FE8000\">" + questionNotAnswerNum + "</font>" + "道题目未作答现在交卷吗？";

                    new XPopup.Builder(context)
                            .dismissOnTouchOutside(false)
                            .autoDismiss(true)
                            .asConfirm("提示", Html.fromHtml(content), "继续答题", "现在交卷", new OnConfirmListener() {
                                @Override
                                public void onConfirm() {
                                    Intent intent = new Intent(context, SimultionResultActivity.class);
                                    startActivity(intent);
                                }
                            }, new OnCancelListener() {
                                @Override
                                public void onCancel() {
                                    questionNotAnswerNum = 0;

                                }
                            }, false)
                            .show();
                } else {
                    Intent intent = new Intent(context, OnlyResultActivity.class);
                    startActivity(intent);
                }
            }
        }
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
            QuestionMuluResponse.DataBean data = wallPaperResponse.getData();
            new XPopup.Builder(context)
                    .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                    .enableDrag(true)
                    .maxHeight(ScreenUtils.getScreenHeight(context) * 2 / 3)
                    .asCustom(new MuluPopup(context, data, number))
                    .show();
        }

    }

    @Override
    public void getQuestionMuluSimlu(BatchMenuListSimluResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {
            List<BatchMenuListSimluResponse.DataBean> data = wallPaperResponse.getData();
            ArrayList<BatchMenuListSimluResponse.DataBean.MenusBean> strings = new ArrayList<>();

            for (int i = 0; i < data.size(); i++) {
                List<BatchMenuListSimluResponse.DataBean.MenusBean> menus = data.get(i).getMenus();
                strings.addAll(menus);
            }
            for (int i = 0; i < strings.size(); i++) {
                QuestionInfoResponse.DataBean dataBean = new QuestionInfoResponse.DataBean();
                dataBean.setId(strings.get(i).getQuestionId());
                questionList.add(dataBean);
            }
            mPresenter.getQuestionInfo(batchNo, questionId);

            new XPopup.Builder(context)
                    .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                    .enableDrag(true)
                    .maxHeight(ScreenUtils.getScreenHeight(context) * 2 / 3)
                    .asCustom(new MuluSimluPopup(context, data, number));
            //.show();
        }
    }

    @Override
    public void getInterrputOnly(InterruptOnlyResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {
            if (show == null) {
                InterruptOnlyResponse.DataBean data = wallPaperResponse.getData();
                show = new XPopup.Builder(context)
                        .dismissOnTouchOutside(false)
                        .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                        .enableDrag(false)
                        .maxHeight(ScreenUtils.getScreenHeight(context) * 12 / 13)
                        .asCustom(new InterruptOnlyPopup(context, data))
                        .show();
            } else {
                show.show();
            }
        }

    }

    @Override
    public void getFailed(Throwable e) {
        page_layout.setPage(PageState.STATE_ERROR);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        page_layout = findViewById(R.id.page_layout);

        page_layout.setContentView(R.layout.activity_only);
        chjTimer = new ChjTimer();
        chjTimer.startTimer();
        quite = false;

        getActivityManager().addActivity(this);
        RelativeLayout rl_left = findViewById(R.id.rl_left);
        tv_title_top = findViewById(R.id.tv_title_top);
        TextView retry = findViewById(R.id.retry);

        loadingPopupView = new XPopup.Builder(this)
                .asLoading();
        rl_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quite) {//只有做过题才会弹出
                    if ("".equals(questionTitle)) {
                        new XPopup.Builder(context)
                                .dismissOnTouchOutside(false)
                                .autoDismiss(true)
                                .asConfirm("提示", "是否保存本次记录并退出？", "继续答题", "确定退出", new OnConfirmListener() {
                                    @Override
                                    public void onConfirm() {
                                        mPresenter.getPauseBatch(batchNo, pauseTime + "");
                                        finish();
                                    }
                                }, new OnCancelListener() {
                                    @Override
                                    public void onCancel() {

                                    }
                                }, false)
                                .show();

                    } else if ("智能刷题".equals(questionTitle)) {
                        totalTime = String.valueOf(chjTimer.stopTimer());
                        Intent intent = new Intent(context, SmartResultActivity.class);
                        intent.putExtra("batchNo", batchNo);
                        intent.putExtra("totalTime", totalTime);
                        startActivity(intent);
                        finish();

                    } else {
                        totalTime = String.valueOf(chjTimer.stopTimer());
                        mPresenter.getInterruptOnly(batchNo, totalTime);
                    }
                } else {
                    finish();
                }
            }
        });
        tv_num = findViewById(R.id.tv_num);
        tv_collect_question = findViewById(R.id.tv_collect_question);
        tv_mulu = findViewById(R.id.tv_mulu);
        tv_commit_all = findViewById(R.id.tv_commit_all);
        Intent intent = getIntent();
        String typeId = intent.getStringExtra("typeId");
        examType = intent.getIntExtra("examType", 1);
        String courseType = intent.getStringExtra("courseType");
        questionTitle = intent.getStringExtra("questionTitle");
        questionId = intent.getStringExtra("questionId");
        batchNo = intent.getStringExtra("batchNo");
        pauseTime = intent.getIntExtra("pauseTime", 7200);


        mVp = findViewById(R.id.vp);
        questionList = new ArrayList<>();

        mPresenter.getQuestionMuluSimlu(batchNo);
        welcomeAdapter1 = new ViewPager2Adapter2(questionList, context, mPresenter, questionTitle);
        mVp.setAdapter(welcomeAdapter1);
        welcomeAdapter1.notifyDataSetChanged();

        page_layout.setPage(PageState.STATE_LOADING);
        lastQuestionId = questionId;
        if ("".equals(questionTitle)) {//代表是模拟考试
            //pauseTime = data.getTotalTime();
            // mPresenter.getQuestionInfo(batchNo, questionId);
            countTimeUtils = new CountTimeUtils(pauseTime) {//倒计时
                @Override
                public void onTick(long millisUntilFinished) {
                    time = getTime(millisUntilFinished);
                    tv_title_top.setText(time);
                    pauseTime = (int) (millisUntilFinished);
                    if (tv_count_time != null) {
                        tv_count_time.setText(time);
                    }

                }

                @Override
                public void onFinish() {//计时完成需要自动交卷

                    new XPopup.Builder(context)
                            .dismissOnTouchOutside(false)
                            .autoDismiss(false)
                            .dismissOnBackPressed(false)
                            .asConfirm("提示", "答题时间已结束", "继续答题", "现在交卷", new OnConfirmListener() {
                                @Override
                                public void onConfirm() {
                                    Intent intent = new Intent(context, SimultionResultActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }, new OnCancelListener() {
                                @Override
                                public void onCancel() {

                                }
                            }, true)
                            .show();

                }
            };
            countTimeUtils.start();
            tv_commit_all.setVisibility(View.VISIBLE);
        } else {
            mPresenter.getExamBatch(courseType, examType, typeId,newBatch);
            tv_title_top.setText(questionTitle);
            tv_commit_all.setVisibility(View.GONE);
        }

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getExamBatch(courseType, examType, typeId,newBatch);
                page_layout.setPage(PageState.STATE_LOADING);
            }
        });


        //收藏题目
        tv_collect_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionList.get(mulu_position).isHasCollected()) {//已收藏
                    operate = 2;
                } else {
                    operate = 1;
                }
                mPresenter.getCollectQuestion(questionId, operate);
            }
        });

        //题目目录
        tv_mulu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("".equals(questionTitle)) {//模拟考试，目录不一样
                    mPresenter.getQuestionMuluSimlu(batchNo);
                } else {
                    mPresenter.getQuestionMulu(batchNo);
                }
            }
        });

        //点击交卷
        tv_commit_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < questionList.size(); i++) {
                    if (questionList.get(i).getCustomerAnswer() == null) {
                        questionNotAnswerNum++;
                    }
                }
                String content = "您还有" + "<font color=\"#FE8000\">" + questionNotAnswerNum + "</font>" + "道题目未作答现在交卷吗？";
                new XPopup.Builder(context)
                        .dismissOnTouchOutside(false)
                        .autoDismiss(true)
                        .asConfirm("提示", Html.fromHtml(content), "继续答题", "现在交卷", new OnConfirmListener() {
                            @Override
                            public void onConfirm() {
                                Intent intent = new Intent(context, SimultionResultActivity.class);
                                startActivity(intent);
                            }
                        }, new OnCancelListener() {
                            @Override
                            public void onCancel() {
                                questionNotAnswerNum = 0;
                            }
                        }, false)
                        .show();
            }
        });


        mVp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                isFirst = true;
                if (questionList.get(position).isHasCollected()) {
                    tv_collect_question.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.mipmap.already_collect), null, null, null);
                    tv_collect_question.setText("已收藏");
                } else {
                    tv_collect_question.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.mipmap.collect_question), null, null, null);
                    tv_collect_question.setText("收藏");
                }
               // questionId = questionList.get(position).getId();
                lastQuestionId = questionList.get(position).getId();
                number = position + 1;
                mulu_position = position;
                String content = "<font color=\"#FE8000\">" + number + "</font>" + "/" + totalNum;
                tv_num.setText(Html.fromHtml(content));
                if (questionList.get(position).getQuestion() == null) {
                    mPresenter.getQuestionInfo(batchNo, questionId);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
     /*   mVp.setPageChangeListener(new CustomViewPager.PageStateChangeListener() {
            private int currentPosition = 0;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                isFirst = true;
                if (position > currentPosition) {
                    //右滑
                    currentPosition = position;
                    slideStates = 0;
                    questionId = questionBean.getNextId();
                    mPresenter.getQuestionInfo(batchNo, questionId);
                } else if (position < currentPosition) {
                    //左滑
                    slideStates = 1;
                    currentPosition = position;
                    questionId = questionBean.getPrevId();
                    mPresenter.getQuestionInfo(batchNo, questionId);
                }
                if (questionList.get(position).isHasCollected()) {
                    tv_collect_question.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.mipmap.already_collect), null, null, null);
                    tv_collect_question.setText("已收藏");
                } else {
                    tv_collect_question.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.mipmap.collect_question), null, null, null);
                    tv_collect_question.setText("收藏");
                }
                questionId = questionList.get(position).getId();
                lastQuestionId = questionList.get(position).getId();
                number = position + 1;
                mulu_position = position;
                String content = "<font color=\"#FE8000\">" + number + "</font>" + "/" + totalNum;
                tv_num.setText(Html.fromHtml(content));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

            @Override
            public void onSlideDirection(int slideState) {

            }
        });*/
    }


    public class MuluPopup extends BottomPopupView {
        private Context context;
        private QuestionMuluResponse.DataBean listBeans;
        private int number;

        public MuluPopup(@NonNull Context context, QuestionMuluResponse.DataBean courseTypeList, int number) {
            super(context);
            this.context = context;
            this.listBeans = courseTypeList;
            this.number = number;
        }

        @Override
        protected int getImplLayoutId() {
            return R.layout.question_mulu_pop;
        }

        @Override
        protected void onCreate() {
            super.onCreate();
            RecyclerView rv_mulu = findViewById(R.id.rv_mulu);
            TextView tv_totle_mnum = findViewById(R.id.tv_totle_mnum);

            String content = "<font color=\"#FE8000\">" + listBeans.getDoneNum() + "</font>" + "/" + listBeans.getTotalNum();
            tv_totle_mnum.setText(Html.fromHtml(content));


            MuluAdapter muluAdapter = new MuluAdapter(R.layout.item_mulu_list, listBeans.getMenus(), number);
            rv_mulu.setLayoutManager(new GridLayoutManager(context, 6));
            rv_mulu.setAdapter(muluAdapter);
            muluAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                    mVp.setCurrentItem(position);
                    mulu_position = position;
                    MuluAdapter.number = position + 1;
                    muluAdapter.notifyDataSetChanged();
                }
            });

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
            tv_count_time = findViewById(R.id.tv_count_time);
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

    public static class InterruptOnlyPopup extends BottomPopupView {
        private Context context;
        private InterruptOnlyResponse.DataBean listBeans;

        public InterruptOnlyPopup(@NonNull Context context, InterruptOnlyResponse.DataBean courseTypeList) {
            super(context);
            this.context = context;
            this.listBeans = courseTypeList;
        }

        @Override
        protected int getImplLayoutId() {
            return R.layout.activity_interrupt_only;

        }

        @Override
        protected void onCreate() {
            super.onCreate();
            TextView tv_right_rate_big = findViewById(R.id.tv_right_rate_big);
            TextView tv_total_num = findViewById(R.id.tv_total_num);
            TextView tv_error_num = findViewById(R.id.tv_error_num);
            TextView tv_title = findViewById(R.id.tv_title);
            TextView tv_time = findViewById(R.id.tv_time);
            TextView tv_not_done = findViewById(R.id.tv_not_done);
            TextView tv_exercise_other = findViewById(R.id.tv_exercise_other);
            TextView tv_find_error_questioin = findViewById(R.id.tv_find_error_questioin);
            RelativeLayout rl_goon = findViewById(R.id.rl_goon);
            RelativeLayout rl_redo = findViewById(R.id.rl_redo);

            tv_right_rate_big.setText(listBeans.getRightRate());
            tv_total_num.setText(listBeans.getDoneNum() + "");
            tv_error_num.setText(listBeans.getWrongNum() + "");
            tv_title.setText("练习专项：" + questionTitle);
            tv_time.setText("");


            String content = "还有" + "<font color=\"#FE8000\">" + listBeans.getNotDoneNum() + "</font>" + "道题未作答，继续答题";
            tv_not_done.setText(Html.fromHtml(content));

            //继续答题
            rl_goon.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    dissmiss = 1;//表示不退出页面
                }
            });

            //返回专项训练
            tv_exercise_other.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

        }

        //完全可见执行
        @Override
        protected void onShow() {
            super.onShow();
        }

        //完全消失执行
        @Override
        protected void onDismiss() {
            if (dissmiss == 0) {
                getActivityManager().finishActivity(OnlyActivity.class);
            } else {
                dissmiss = 0;
            }
        }


    }

    @Override
    public int getLayoutId() {
        return R.layout.base_layout;
    }
}