package com.example.yichengxuetang.activitys.questionbankactivitys;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yichengxuetang.R;
import com.example.yichengxuetang.adapter.SmartDohomeworkAdapter;
import com.example.yichengxuetang.bean.GroupInfo;
import com.example.yichengxuetang.bean.GroupNoticeBean;
import com.example.yichengxuetang.bean.WallPaperResponse;
import com.example.yichengxuetang.contract.MainContract;
import com.example.yichengxuetang.utils.CountTimeUtils;
import com.llw.mvplibrary.mvp.MvpActivity;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;

import java.util.ArrayList;

public class SmartDoHomeworkctivity extends MvpActivity<MainContract.MainPresenter> implements MainContract.IMainView {


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
        TextView tv_time = findViewById(R.id.tv_time);
        ViewPager mVp = findViewById(R.id.vp);

        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            strings.add("下面哪个是我们要关注的人生投资呢？" + i);
        }

        //倒计时
        CountTimeUtils countTimeUtils = new CountTimeUtils(3610) {
            @Override
            public void onTick(long millisUntilFinished) {
                String time = getTime(millisUntilFinished);
                tv_time.setText(time);
            }

            @Override
            public void onFinish() {

            }
        };
        countTimeUtils.start();

       /* SmartDohomeworkAdapter welcomeAdapter = new SmartDohomeworkAdapter(strings, context);
        mVp.setAdapter(welcomeAdapter);
        welcomeAdapter.notifyDataSetChanged();*/

        new XPopup.Builder(context)
                .enableDrag(true)
                .asCustom(new handPop(context))
                .show();

    }

    //根据秒数转化为时分秒   00:00:00
    public static String getTime(long second) {
        if (second < 10) {
            return "00:0" + second;
        }
        if (second < 60) {
            return "00:" + second;
        }
        if (second < 3600) {
            long minute = second / 60;
            second = second - minute * 60;
            if (minute < 10) {
                if (second < 10) {
                    return "0" + minute + ":0" + second;
                }
                return "0" + minute + ":" + second;
            }
            if (second < 10) {
                return minute + ":0" + second;
            }
            return minute + ":" + second;
        }
        long hour = second / 3600;
        long minute = (second - hour * 3600) / 60;
        second = second - hour * 3600 - minute * 60;
        if (hour < 10) {
            if (minute < 10) {
                if (second < 10) {
                    return "0" + hour + ":0" + minute + ":0" + second;
                }
                return "0" + hour + ":0" + minute + ":" + second;
            }
            if (second < 10) {
                return "0" + hour + ":" + minute + ":0" + second;
            }
            return "0" + hour + ":" + minute + ":" + second;
        }
        if (minute < 10) {
            if (second < 10) {
                return hour + ":0" + minute + ":0" + second;
            }
            return hour + ":0" + minute + ":" + second;
        }
        if (second < 10) {
            return hour + minute + ":0" + second;
        }
        return hour + minute + ":" + second;
    }

    public static class handPop extends BasePopupView {
        private Context context;

        public handPop(@NonNull Context context) {
            super(context);
            this.context = context;
        }

        @Override
        protected int getPopupLayoutId() {
            return R.layout.pop_hand;
        }

        @Override
        protected void onCreate() {
            super.onCreate();
            RelativeLayout rle = findViewById(R.id.rle);

            rle.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_smart_do_homeworkctivity;
    }
}