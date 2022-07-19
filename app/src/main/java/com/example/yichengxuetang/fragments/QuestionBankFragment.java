package com.example.yichengxuetang.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.yichengxuetang.R;
import com.example.yichengxuetang.activitys.logins.LoginActivity;
import com.example.yichengxuetang.activitys.questionbankactivitys.OrderExerciseActivity;
import com.example.yichengxuetang.activitys.questionbankactivitys.TestExerciseActivity;
import com.example.yichengxuetang.adapter.ExchangeAdapter;
import com.example.yichengxuetang.bean.GroupInfo;
import com.example.yichengxuetang.bean.GroupNoticeBean;
import com.example.yichengxuetang.bean.WallPaperResponse;
import com.example.yichengxuetang.contract.MainContract;
import com.example.yichengxuetang.fragments.practicecenter.PostSkillsFragment;
import com.example.yichengxuetang.fragments.practicecenter.PublicSubjectsFragment;
import com.example.yichengxuetang.utils.ScreenUtils;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.llw.mvplibrary.mvp.MvpFragment;
import com.llw.mvplibrary.network.utils.StatusBarUtils;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.necer.ndialog.ChoiceDialog;

import java.util.ArrayList;
import java.util.Objects;


public class QuestionBankFragment extends MvpFragment<MainContract.MainPresenter> implements MainContract.IMainView {
    private TabLayoutMediator mediator;

    public QuestionBankFragment() {
        // Required empty public constructor

    }


    @Override
    public void getWallPaper(WallPaperResponse wallPaperResponse) {

    }

    @Override
    public void getGroupInfo(GroupInfo groupInfo) {

    }

    @Override
    public void getGroupNotice(GroupNoticeBean groupInfo) {

    }

    @Override
    public void getFailed(Throwable e) {

    }

    @Override
    protected MainContract.MainPresenter createPresent() {
        return new MainContract.MainPresenter();
    }

    public class ExchangePopup extends BottomPopupView {
        private Context context;

        public ExchangePopup(@NonNull Context context) {
            super(context);
            this.context = context;
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

            ArrayList<String> strings = new ArrayList<>();
            strings.add("家庭教育指导师");
            strings.add("淘宝无货源");
            strings.add("军队文职");
            strings.add("短视频带货直播实操课");

            ExchangeAdapter exchangeAdapter = new ExchangeAdapter(R.layout.exchange_item, strings);
            rv_exchange.setLayoutManager(new LinearLayoutManager(context));
            rv_exchange.setAdapter(exchangeAdapter);

            exchangeAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

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

        ViewPager2 vp2_exercise = rootView.findViewById(R.id.vp2_exercise);
        TabLayout tab_exercise = rootView.findViewById(R.id.tab_exercise);
        TextView tv_exchange = rootView.findViewById(R.id.tv_exchange);

        //切换课程
        tv_exchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new XPopup.Builder(context)
                        .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                        .enableDrag(true)
                        .dismissOnBackPressed(true)
                        .maxHeight(ScreenUtils.getScreenHeight(context) * 2 / 3)
                        .asCustom(new ExchangePopup(context))
                        .show();
            }
        });

        ArrayList<String> titles = new ArrayList<>();
        titles.add("公共科目");
        titles.add("岗位技能");

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new PublicSubjectsFragment());
        fragments.add(new PostSkillsFragment());

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
                tab.setText(titles.get(position));
            }
        });
        mediator.attach();
        for (int i = 0; i < fragments.size(); i++) {
            Objects.requireNonNull(tab_exercise.getTabAt(i)).setCustomView(R.layout.main_top_item);
            TextView toMyTextView = Objects.requireNonNull(Objects.requireNonNull(tab_exercise.getTabAt(i)).getCustomView()).findViewById(R.id.tv_top_item);
            //默认选择第一个tab,设置字体大小和默认风格为加粗
            toMyTextView.setText(titles.get(i));
        }

        TextView toMyTextView = Objects.requireNonNull(Objects.requireNonNull(tab_exercise.getTabAt(0)).getCustomView()).findViewById(R.id.tv_top_item);
        View tab_item_indicator = Objects.requireNonNull(Objects.requireNonNull(tab_exercise.getTabAt(0)).getCustomView()).findViewById(R.id.tab_item_indicator);
        tab_item_indicator.setVisibility(View.VISIBLE);
        toMyTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        toMyTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        toMyTextView.setTextColor(Color.parseColor("#2A2A2A"));
        /*Bundle bundle = new Bundle();
        bundle.putString("typeCode", fragments.get(0).getType() + "");
        fragments.get(0).setArguments(bundle);*/
        //看这里看这里看这里
        tab_exercise.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getCustomView() != null) {
                    int position = tab.getPosition();
                    TextView tv = Objects.requireNonNull(tab.getCustomView()).findViewById(R.id.tv_top_item);
                    tv.setText(titles.get(position));
                    tv.setTextColor(Color.parseColor("#2A2A2A"));
                    tv.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
                    tv.setTextSize(20);
                    View tab_item_indicator = Objects.requireNonNull(Objects.requireNonNull(tab_exercise.getTabAt(position)).getCustomView()).findViewById(R.id.tab_item_indicator);
                    tab_item_indicator.setVisibility(View.VISIBLE);
                    tv.invalidate();
                   /* Bundle bundle = new Bundle();
                    bundle.putString("typeCode", fragments.get(position).getType() + "");
                    fragments.get(position).setArguments(bundle);*/
                    tab.getCustomView().findViewById(R.id.tv_top_item).setSelected(true);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                TextView tv = Objects.requireNonNull(tab.getCustomView()).findViewById(R.id.tv_top_item);
                tv.setText(titles.get(position));
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

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_question_bank;
    }
}