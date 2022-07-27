package com.example.yichengxuetang.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.yichengxuetang.R;
import com.example.yichengxuetang.adapter.ExchangeAdapter;
import com.example.yichengxuetang.bean.BatchMenuListSimluResponse;
import com.example.yichengxuetang.bean.CollectQuestionSuccessResponse;
import com.example.yichengxuetang.bean.CommitSuccessResponse;
import com.example.yichengxuetang.bean.ExamBranchRsponse;
import com.example.yichengxuetang.bean.InterruptOnlyResponse;
import com.example.yichengxuetang.bean.LearningCenterResponse;
import com.example.yichengxuetang.bean.QuestionBankResponse;
import com.example.yichengxuetang.bean.QuestionBankTypeResponse;
import com.example.yichengxuetang.bean.QuestionInfoResponse;
import com.example.yichengxuetang.bean.QuestionListResponse;
import com.example.yichengxuetang.bean.QuestionMuluResponse;
import com.example.yichengxuetang.contract.QuestionBankContract;
import com.example.yichengxuetang.fragments.practicecenter.PostSkillsFragment;
import com.example.yichengxuetang.fragments.practicecenter.PublicSubjectsFragment;
import com.example.yichengxuetang.utils.ScreenUtils;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ljb.page.PageState;
import com.ljb.page.PageStateLayout;
import com.llw.mvplibrary.mvp.MvpFragment;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class QuestionBankFragment extends MvpFragment<QuestionBankContract.QuestionBankPresenter> implements QuestionBankContract.QuestionBankView {
    private TabLayoutMediator mediator;
    private Integer busiTypeCode;//业务类型
    private Integer queryType = 1;//	查询类型1查询一级类型 2查次级分类
    private List<QuestionBankTypeResponse.DataBean> courseTypeList;
    private ViewPager2 vp2_exercise;
    private TabLayout tab_exercise;
    private TextView tv_exchange;

    private PageStateLayout page_layout;

    public QuestionBankFragment() {
        // Required empty public constructor

    }


    @Override
    public void getFailed(Throwable e) {
        page_layout.setPage(PageState.STATE_ERROR);
    }

    @Override
    protected QuestionBankContract.QuestionBankPresenter createPresent() {
        return new QuestionBankContract.QuestionBankPresenter();
    }

    @Override
    public void getWallPaper(QuestionBankResponse wallPaperResponse) {

        if (wallPaperResponse.getCode() == 0) {
            List<QuestionBankResponse.DataBean> data = wallPaperResponse.getData();
            ArrayList<Fragment> fragments = new ArrayList<>();

            for (int i = 0; i < data.size(); i++) {
                fragments.add(new PublicSubjectsFragment());
            }

            vp2_exercise.setAdapter(new FragmentStateAdapter(this) {
                @NonNull
                @Override
                public Fragment createFragment(int position) {
                    return fragments.get(position);
                }

                @Override
                public int getItemCount() {
                    return fragments.size();
                }
            });

            mediator = new TabLayoutMediator(tab_exercise, vp2_exercise, new TabLayoutMediator.TabConfigurationStrategy() {
                @Override
                public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                    tab.setText(data.get(position).getName());
                }
            });
            mediator.attach();
            for (int i = 0; i < fragments.size(); i++) {
                Objects.requireNonNull(tab_exercise.getTabAt(i)).setCustomView(R.layout.main_top_item);
                TextView toMyTextView = Objects.requireNonNull(Objects.requireNonNull(tab_exercise.getTabAt(i)).getCustomView()).findViewById(R.id.tv_top_item);
                //默认选择第一个tab,设置字体大小和默认风格为加粗
                toMyTextView.setText(data.get(i).getName());
            }

            TextView toMyTextView = Objects.requireNonNull(Objects.requireNonNull(tab_exercise.getTabAt(0)).getCustomView()).findViewById(R.id.tv_top_item);
            View tab_item_indicator = Objects.requireNonNull(Objects.requireNonNull(tab_exercise.getTabAt(0)).getCustomView()).findViewById(R.id.tab_item_indicator);
            tab_item_indicator.setVisibility(View.VISIBLE);
            toMyTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            toMyTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            toMyTextView.setTextColor(Color.parseColor("#2A2A2A"));
            Bundle bundle = new Bundle();
            bundle.putString("parentId", data.get(0).getId() + "");
            bundle.putString("courseType", busiTypeCode + "");
            bundle.putIntegerArrayList("needHiddenIconlist", data.get(0).getNeedHiddenIcon());
            fragments.get(0).setArguments(bundle);
            //看这里看这里看这里
            tab_exercise.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    if (tab.getCustomView() != null) {
                        int position = tab.getPosition();
                        TextView tv = Objects.requireNonNull(tab.getCustomView()).findViewById(R.id.tv_top_item);
                        tv.setText(data.get(position).getName());
                        tv.setTextColor(Color.parseColor("#2A2A2A"));
                        tv.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
                        tv.setTextSize(20);
                        View tab_item_indicator = Objects.requireNonNull(Objects.requireNonNull(tab_exercise.getTabAt(position)).getCustomView()).findViewById(R.id.tab_item_indicator);
                        tab_item_indicator.setVisibility(View.VISIBLE);
                        tv.invalidate();
                        Bundle bundle = new Bundle();
                        bundle.putString("parentId", data.get(position).getId() + "");
                        bundle.putString("courseType", busiTypeCode + "");
                        bundle.putIntegerArrayList("needHiddenIconlist", data.get(position).getNeedHiddenIcon());
                        fragments.get(position).setArguments(bundle);
                        tab.getCustomView().findViewById(R.id.tv_top_item).setSelected(true);
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                    int position = tab.getPosition();
                    TextView tv = Objects.requireNonNull(tab.getCustomView()).findViewById(R.id.tv_top_item);
                    tv.setText(data.get(position).getName());
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                    tv.setTextColor(Color.parseColor("#B5B5B5"));
                    View tab_item_indicator = Objects.requireNonNull(Objects.requireNonNull(tab_exercise.getTabAt(position)).getCustomView()).findViewById(R.id.tab_item_indicator);
                    tab_item_indicator.setVisibility(View.INVISIBLE);
                    tv.invalidate();
                    tab.getCustomView().findViewById(R.id.tv_top_item).setSelected(false);
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
            page_layout.setPage(PageState.STATE_SUCCESS);
        } else {
            page_layout.setPage(PageState.STATE_EMPTY);
        }
    }


    @Override
    public void getLearningCenter(QuestionBankTypeResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {
            courseTypeList = wallPaperResponse.getData();

            tv_exchange.setText(courseTypeList.get(0).getName());
            busiTypeCode = courseTypeList.get(0).getType();
            courseTypeList.get(0).setChoice(1);
            mPresenter.getQuestionBank(queryType + "", busiTypeCode + "", "");

        }
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

    public class ExchangePopup extends BottomPopupView {
        private Context context;
        private List<QuestionBankTypeResponse.DataBean> listBeans;

        public ExchangePopup(@NonNull Context context, List<QuestionBankTypeResponse.DataBean> courseTypeList) {
            super(context);
            this.context = context;
            this.listBeans = courseTypeList;
        }

        @Override
        protected int getImplLayoutId() {
            return R.layout.exchange_pop;
        }

        @Override
        protected void onCreate() {
            super.onCreate();
            ImageView iv_close = findViewById(R.id.iv_close);
            RecyclerView rv_exchange = findViewById(R.id.rv_exchange);
            iv_close.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });


            ExchangeAdapter exchangeAdapter = new ExchangeAdapter(R.layout.exchange_item, listBeans);
            rv_exchange.setLayoutManager(new LinearLayoutManager(context));
            rv_exchange.setAdapter(exchangeAdapter);

            exchangeAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                    busiTypeCode = listBeans.get(position).getType();
                    tv_exchange.setText(listBeans.get(position).getName());
                    listBeans.get(0).setChoice(0);
                    listBeans.get(position).setChoice(1);
                    mPresenter.getQuestionBank(queryType + "", busiTypeCode + "", "");
                    dismiss();
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

   /* @Override
    protected int getMaxHeight() {
        return (int) (ScreenUtils.getScreenHeight(getContext()) * .5f);
    }*/
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        page_layout = rootView.findViewById(R.id.page_layout);
        page_layout.setContentView(R.layout.fragment_question_bank);

        vp2_exercise = rootView.findViewById(R.id.vp2_exercise);
        tab_exercise = rootView.findViewById(R.id.tab_exercise);
        tv_exchange = rootView.findViewById(R.id.tv_exchange);

        mPresenter.getQuestionBankType();
        page_layout.setPage(PageState.STATE_LOADING);

        //切换课程
        tv_exchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new XPopup.Builder(context)
                        .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                        .enableDrag(true)
                        .dismissOnBackPressed(true)
                        .maxHeight(ScreenUtils.getScreenHeight(context) * 2 / 3)
                        .asCustom(new ExchangePopup(context, courseTypeList))
                        .show();
                //data.clear();
                // mPresenter.getQuestionBank(queryType + "", busiTypeCode + "", "");

            }
        });
        TextView retry = rootView.findViewById(R.id.retry);

        //点击重试
        retry.setOnClickListener(v -> {
            page_layout.setPage(PageState.STATE_LOADING);
            mPresenter.getQuestionBankType();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.base_layout;
    }
}