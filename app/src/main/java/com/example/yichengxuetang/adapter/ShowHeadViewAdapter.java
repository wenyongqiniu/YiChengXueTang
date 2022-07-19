package com.example.yichengxuetang.adapter;

import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.yichengxuetang.R;
import com.example.yichengxuetang.activitys.learningactivitys.DakeCourseListActivity;
import com.example.yichengxuetang.bean.ShowCourseListResponse;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ShowHeadViewAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ShowHeadViewAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String url) {
        ImageView head_view = baseViewHolder.getView(R.id.iv_headview);
        RelativeLayout rl_head = baseViewHolder.getView(R.id.rl_head);
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) rl_head.getLayoutParams();
        if (baseViewHolder.getAdapterPosition()==0){
            layoutParams.setMarginStart(0);
            rl_head.setLayoutParams(layoutParams);
        }
        if (baseViewHolder.getAdapterPosition()==3){
            layoutParams.setMarginEnd(0);
            rl_head.setLayoutParams(layoutParams);
        }
        Glide.with(getContext()).load(url).apply(new RequestOptions().circleCrop()).into(head_view);
    }

}
