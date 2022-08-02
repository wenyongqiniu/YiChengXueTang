package com.example.yichengxuetang.activitys.learningactivitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yichengxuetang.R;
import com.example.yichengxuetang.fragments.learningcenterfragments.DakeCourseListFragment;
import com.example.yichengxuetang.fragments.learningcenterfragments.DakeNotesFragment;
import com.example.yichengxuetang.utils.ObservableScrollView;
import com.example.yichengxuetang.utils.ScreenUtils;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class DakeCourseListActivity extends AppCompatActivity implements  ObservableScrollView.ScrollViewListener{

    private ObservableScrollView myscrollview;
    private CollapsingToolbarLayout cool_hight;
    private RelativeLayout rl_left_chu;
    private RelativeLayout rl_left;
    private ImageView iv_back;
    private ImageView iv_dake_top;
    private int imageHeight;
    private TabLayout tab_dake;
    private String contentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dake_course_list);
        View decorView = getWindow().getDecorView();
        //保持View属性改变View不会重新绘制
        int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE

                //隐藏状态栏
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

        decorView.setSystemUiVisibility(option);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        initView();
    }

    private void initView() {

        Intent intent = getIntent();
        String courseId = intent.getStringExtra("courseId");
        contentType = intent.getStringExtra("contentType");
        tab_dake = findViewById(R.id.tab_dake);
        ViewPager2 vp2_dake = findViewById(R.id.vp2_dake);
        myscrollview = findViewById(R.id.myscrollview);
        cool_hight = findViewById(R.id.cool_hight);
        rl_left_chu = findViewById(R.id.rl_left_chu);
        iv_back = findViewById(R.id.iv_back);
        iv_dake_top = findViewById(R.id.iv_dake_top);
        rl_left = findViewById(R.id.rl_left);
        rl_left.setOnClickListener(v -> finish());
        rl_left_chu.setOnClickListener(v -> finish());

        //iv_dake_top.bringToFront();
        rl_left_chu.setBackgroundColor(Color.argb((int) 0, 227, 29, 26));

        ViewTreeObserver vto = rl_left_chu.getViewTreeObserver();

        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rl_left_chu.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                imageHeight = rl_left_chu.getHeight();
                myscrollview.setScrollViewListener(DakeCourseListActivity.this);
            }
        });
        ArrayList<Fragment> fragments = new ArrayList<>();
        ArrayList<String> title = new ArrayList<>();
        title.add("课程目录");
        title.add("课程笔记");

        fragments.add(new DakeCourseListFragment());
        fragments.add(new DakeNotesFragment());

        vp2_dake.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                Fragment fragment = fragments.get(position);
                return fragment;
            }

            @Override
            public int getItemCount() {
                return fragments.size();
            }
        });


        TabLayoutMediator mediator = new TabLayoutMediator(tab_dake, vp2_dake, (tab, position) -> tab.setText(title.get(position)));
        mediator.attach();
        Bundle bundle = new Bundle();
        bundle.putString("courseId", courseId);
        bundle.putString("contentType", contentType);
        fragments.get(0).setArguments(bundle);

        for (int i = 0; i < title.size(); i++) {
            tab_dake.getTabAt(i).setCustomView(R.layout.main_top_item);
            TextView toMyTextView = tab_dake.getTabAt(i).getCustomView().findViewById(R.id.tv_top_item);
            //默认选择第一个tab,设置字体大小和默认风格为加粗
            toMyTextView.setText(title.get(i));
        }
        TextView toMyTextView = tab_dake.getTabAt(0).getCustomView().findViewById(R.id.tv_top_item);
        View tab_item_indicator = tab_dake.getTabAt(0).getCustomView().findViewById(R.id.tab_item_indicator);
        tab_item_indicator.setVisibility(View.VISIBLE);
        toMyTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        toMyTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        toMyTextView.setTextColor(Color.parseColor("#2A2A2A"));

        //看这里看这里看这里
        tab_dake.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                TextView tv = tab.getCustomView().findViewById(R.id.tv_top_item);
                tv.setText(title.get(position));
                tv.setTextColor(Color.parseColor("#2A2A2A"));
                tv.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
                tv.setTextSize(20);
                View tab_item_indicator = tab_dake.getTabAt(position).getCustomView().findViewById(R.id.tab_item_indicator);
                tab_item_indicator.setVisibility(View.VISIBLE);
                tv.invalidate();
                tab.getCustomView().findViewById(R.id.tv_top_item).setSelected(true);
                Bundle bundle = new Bundle();
                bundle.putString("courseId", courseId);
                bundle.putString("contentType", contentType);
                fragments.get(position).setArguments(bundle);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                TextView tv = tab.getCustomView().findViewById(R.id.tv_top_item);
                tv.setText(title.get(position));
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                tv.setTextColor(Color.parseColor("#B5B5B5"));
                View tab_item_indicator = tab_dake.getTabAt(position).getCustomView().findViewById(R.id.tab_item_indicator);
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
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) tab_dake.getLayoutParams();

        if (y <= 0) {
            rl_left_chu.setBackgroundColor(Color.argb((int) 0, 227, 29, 26));//AGB由相关工具获得，或者美工提供
            rl_left_chu.setVisibility(View.INVISIBLE);
            layoutParams.setMarginEnd(100+imageHeight);
            tab_dake.setLayoutParams(layoutParams);
        } else if (y > 0 && y <= imageHeight) {
            float scale = (float) y / imageHeight;
            float alpha = (255 * scale);
            // 只是layout背景透明(仿知乎滑动效果)
            rl_left_chu.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
            layoutParams.setMarginEnd(100-imageHeight);
            tab_dake.setLayoutParams(layoutParams);
        } else {
            rl_left_chu.setBackgroundColor(Color.argb((int) 255, 255, 255, 255));
            rl_left_chu.setVisibility(View.VISIBLE);
        }
    }
}