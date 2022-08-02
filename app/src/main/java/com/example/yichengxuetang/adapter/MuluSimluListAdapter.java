package com.example.yichengxuetang.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.yichengxuetang.R;
import com.example.yichengxuetang.activitys.questionbankactivitys.OnlyActivity;
import com.example.yichengxuetang.bean.BatchMenuListSimluResponse;
import com.example.yichengxuetang.utils.ScreenUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class MuluSimluListAdapter extends BaseQuickAdapter<BatchMenuListSimluResponse.DataBean.MenusBean, BaseViewHolder> {

    public static int number;
    private int size;

    public MuluSimluListAdapter(int layoutResId, @Nullable List<BatchMenuListSimluResponse.DataBean.MenusBean> data, int i, int number) {
        super(layoutResId, data);
        this.number = number;
        this.size = i;
    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, BatchMenuListSimluResponse.DataBean.MenusBean conversation) {

        TextView tv_num = baseViewHolder.getView(R.id.tv_num);
        tv_num.setText(baseViewHolder.getLayoutPosition() + 1 + size + "");

        RelativeLayout rl_mulu = baseViewHolder.getView(R.id.rl_mulu);

        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) rl_mulu.getLayoutParams();

        layoutParams.width = ScreenUtils.getScreenWidth(getContext()) / 6;
        rl_mulu.setLayoutParams(layoutParams);


       /* if (OnlyActivity.lastQuestionId.equals(conversation.getQuestionId())) {
            tv_num.setBackgroundResource(R.drawable.mulu_now_shape);
            tv_num.setTextColor(Color.parseColor("#7A7A7A"));
        } else {*/
        if (conversation.getQueationStatus() == 0) {//题目状态：0未做 1正确 2错误
            tv_num.setBackgroundResource(R.drawable.mulu_empty_shape);
            tv_num.setTextColor(Color.parseColor("#7A7A7A"));
        } else {
            tv_num.setBackgroundResource(R.drawable.login_phone_shape);
            tv_num.setTextColor(Color.parseColor("#FFFFFF"));
        }
        //}


        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = tv_num.getText().toString();
                int i = Integer.parseInt(s);
                OnlyActivity.mVp.setCurrentItem(i - 1);
                OnlyActivity.lastQuestionId = conversation.getQuestionId();
                OnlyActivity.questionId = conversation.getQuestionId();
                tv_num.setBackgroundResource(R.drawable.mulu_empty_shape);
                tv_num.setTextColor(Color.parseColor("#7A7A7A"));
                MuluSimluListAdapter.this.notifyDataSetChanged();
            }
        });
    }

}
