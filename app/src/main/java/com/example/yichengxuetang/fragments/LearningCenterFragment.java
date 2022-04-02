package com.example.yichengxuetang.fragments;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.yichengxuetang.R;
import com.example.yichengxuetang.bean.LearningCenterResponse;
import com.example.yichengxuetang.contract.LearningCenterContract;
import com.example.yichengxuetang.utils.MyTabLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.llw.mvplibrary.mvp.MvpFragment;
import com.stx.xhb.xbanner.XBanner;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class LearningCenterFragment extends MvpFragment<LearningCenterContract.LearningCenterPresenter> implements LearningCenterContract.LearningCenterView {


    private XBanner mXBanner;
    private TabLayout mTabLayout;
    private ViewPager2 mVp2;
    private TabLayoutMediator mediator;

    public LearningCenterFragment() {
        // Required empty public constructor
    }

    @Override
    public void getLearningCenter(LearningCenterResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {
            List<LearningCenterResponse.DataBean.CourseTypeListBean> courseTypeList = wallPaperResponse.getData().getCourseTypeList();
            ArrayList<Fragment> fragments = new ArrayList<>();
            for (int i = 0; i < courseTypeList.size(); i++) {
                fragments.add(new ShowFragment());
            }
            mVp2.setAdapter(new FragmentStateAdapter(this) {
                @NonNull
                @Override
                public Fragment createFragment(int position) {
                    Fragment fragment = fragments.get(position);
                    return fragment;
                }

                @Override
                public int getItemCount() {
                    return courseTypeList.size();
                }
            });

            mediator = new TabLayoutMediator(mTabLayout, mVp2, new TabLayoutMediator.TabConfigurationStrategy() {
                @Override
                public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                    tab.setText(courseTypeList.get(position).getName());
                }
            });
            mediator.attach();
            for (int i = 0; i < courseTypeList.size(); i++) {
                mTabLayout.getTabAt(i).setCustomView(R.layout.main_top_item);
                TextView toMyTextView = mTabLayout.getTabAt(i).getCustomView().findViewById(R.id.tv_top_item);
                //默认选择第一个tab,设置字体大小和默认风格为加粗 toMyTextView是我自己项目中第一个Tab的TextView,自己看着改。
                toMyTextView.setText(courseTypeList.get(i).getName());
            }
            TextView toMyTextView = mTabLayout.getTabAt(0).getCustomView().findViewById(R.id.tv_top_item);
            View tab_item_indicator = mTabLayout.getTabAt(0).getCustomView().findViewById(R.id.tab_item_indicator);
            tab_item_indicator.setVisibility(View.VISIBLE);
            toMyTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            toMyTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            toMyTextView.setTextColor(Color.parseColor("#2A2A2A"));
            Bundle bundle = new Bundle();
            bundle.putString("typeCode", courseTypeList.get(0).getType()+"");
            fragments.get(0).setArguments(bundle);
            //看这里看这里看这里
            mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    int position = tab.getPosition();
                    TextView tv = tab.getCustomView().findViewById(R.id.tv_top_item);
                    tv.setText(courseTypeList.get(position).getName());
                    tv.setTextColor(Color.parseColor("#2A2A2A"));
                    tv.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
                    tv.setTextSize(20);
                    View tab_item_indicator = mTabLayout.getTabAt(position).getCustomView().findViewById(R.id.tab_item_indicator);
                    tab_item_indicator.setVisibility(View.VISIBLE);
                    tv.invalidate();
                    Bundle bundle = new Bundle();
                    bundle.putString("typeCode", courseTypeList.get(position).getType()+"");
                    fragments.get(position).setArguments(bundle);
                    tab.getCustomView().findViewById(R.id.tv_top_item).setSelected(true);
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                    int position = tab.getPosition();
                    TextView tv = tab.getCustomView().findViewById(R.id.tv_top_item);
                    tv.setText(courseTypeList.get(position).getName());
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                    tv.setTextColor(Color.parseColor("#B5B5B5"));
                    View tab_item_indicator = mTabLayout.getTabAt(position).getCustomView().findViewById(R.id.tab_item_indicator);
                    tab_item_indicator.setVisibility(View.INVISIBLE);
                    tv.invalidate();
                    tab.getCustomView().findViewById(R.id.tv_top_item).setSelected(false);
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }
        hideLoadingDialog();

    }

    @Override
    public void getFailed(Throwable e) {

    }

    @Override
    public void onDestroy() {
        mediator.detach();
        super.onDestroy();
    }

    @Override
    protected LearningCenterContract.LearningCenterPresenter createPresent() {
        return new LearningCenterContract.LearningCenterPresenter();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        showLoadingDialog();
        mPresenter.getLearningCenterPaper();
        initView();
    }

    private void initView() {
        mXBanner = rootView.findViewById(R.id.xbanner);
        mTabLayout = rootView.findViewById(R.id.tab_layout);
        mVp2 = rootView.findViewById(R.id.vp2_lc);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_learning_center;
    }
}