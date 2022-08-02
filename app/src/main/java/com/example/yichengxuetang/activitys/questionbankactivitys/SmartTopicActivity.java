package com.example.yichengxuetang.activitys.questionbankactivitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yichengxuetang.R;
import com.example.yichengxuetang.bean.GroupInfo;
import com.example.yichengxuetang.bean.GroupNoticeBean;
import com.example.yichengxuetang.bean.WallPaperResponse;
import com.example.yichengxuetang.contract.MainContract;
import com.llw.mvplibrary.mvp.MvpActivity;
import com.llw.mvplibrary.network.utils.SpUtils;
import com.llw.mvplibrary.network.utils.StatusBarUtils;

public class SmartTopicActivity extends MvpActivity<MainContract.MainPresenter> implements MainContract.IMainView {

    private boolean isChecked;

    @Override
    protected MainContract.MainPresenter createPresenter() {
        return new MainContract.MainPresenter();
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
    public void initData(Bundle savedInstanceState) {
        StatusBarUtils.setColor(this, Color.parseColor("#fff4fdfd"));
        StatusBarUtils.setTextDark(this, true);
        Intent intent = getIntent();
        String typeId = intent.getStringExtra("typeId");
        int examType = intent.getIntExtra("examType", 1);
        String courseType = intent.getStringExtra("courseType");
        String questionTitle = intent.getStringExtra("questionTitle");

        RelativeLayout rl_left = findViewById(R.id.rl_left_out);
        TextView tv_start_do = findViewById(R.id.tv_start_do);
        TextView tv_remmber = findViewById(R.id.tv_remmber);
        rl_left.setOnClickListener(v -> finish());

        //开始答题
        tv_start_do.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OnlyActivity.class);
                intent.putExtra("typeId", typeId);
                intent.putExtra("courseType", courseType);
                intent.putExtra("examType", 2);
                intent.putExtra("questionTitle", questionTitle);
                startActivity(intent);
            }
        });

        tv_remmber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isChecked){
                    tv_remmber.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.mipmap.checked_single), null, null, null);
                    SpUtils.putSpString(context, "remember", "remember");
                    isChecked=true;
                }else{
                    tv_remmber.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.mipmap.un_checked_single), null, null, null);
                    SpUtils.putSpString(context, "remember", "");
                    isChecked=false;
                }
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_smart_topic;
    }
}