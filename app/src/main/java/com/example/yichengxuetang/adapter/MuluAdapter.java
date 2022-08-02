package com.example.yichengxuetang.adapter;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.yichengxuetang.R;
import com.example.yichengxuetang.bean.IconResponse;
import com.example.yichengxuetang.bean.QuestionMuluResponse;
import com.example.yichengxuetang.utils.ScreenUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class MuluAdapter extends BaseQuickAdapter<QuestionMuluResponse.DataBean.MenusBean, BaseViewHolder> {

    public static int number;

    public MuluAdapter(int layoutResId, @Nullable List<QuestionMuluResponse.DataBean.MenusBean> data, int number) {
        super(layoutResId, data);
        this.number = number;
    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, QuestionMuluResponse.DataBean.MenusBean conversation) {

        TextView tv_num = baseViewHolder.getView(R.id.tv_num);
        RelativeLayout rl_mulu = baseViewHolder.getView(R.id.rl_mulu);
        tv_num.setText(baseViewHolder.getLayoutPosition() + 1 + "");
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) rl_mulu.getLayoutParams();

        layoutParams.width = ScreenUtils.getScreenWidth(getContext()) / 6;
        rl_mulu.setLayoutParams(layoutParams);

        if (conversation.getQueationStatus() == 0) {//题目状态：0未做 1正确 2错误
            tv_num.setBackgroundResource(R.drawable.mulu_empty_shape);
        } else if (conversation.getQueationStatus() == 1) {
            tv_num.setBackgroundResource(R.drawable.mulu_right_shape);
        } else if (conversation.getQueationStatus() == 2) {
            tv_num.setBackgroundResource(R.drawable.mulu_error_shape);
        }
        if (number - 1 == baseViewHolder.getLayoutPosition()) {
            tv_num.setBackgroundResource(R.drawable.mulu_now_shape);
        }

    }

}
