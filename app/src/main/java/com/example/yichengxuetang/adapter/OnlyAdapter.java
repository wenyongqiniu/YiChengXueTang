package com.example.yichengxuetang.adapter;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.yichengxuetang.R;
import com.example.yichengxuetang.bean.QuestionBankResponse;
import com.example.yichengxuetang.bean.QuestionBankTypeResponse;
import com.example.yichengxuetang.contract.QuestionBankContract;
import com.example.yichengxuetang.utils.PixelTool;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class OnlyAdapter extends BaseQuickAdapter<QuestionBankResponse.DataBean, BaseViewHolder> {


    public static RecyclerView rv_second;
    private String courseType;
    private QuestionBankContract.QuestionBankPresenter mPresenter;

    public OnlyAdapter(int layoutResId, @Nullable List<QuestionBankResponse.DataBean> data, String courseType, QuestionBankContract.QuestionBankPresenter mPresenter) {
        super(layoutResId, data);
        this.courseType = courseType;
        this.mPresenter = mPresenter;
    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, QuestionBankResponse.DataBean conversation) {

        TextView tv_first = baseViewHolder.getView(R.id.tv_first);
        TextView tv_state = baseViewHolder.getView(R.id.tv_state);
        TextView tv_lates = baseViewHolder.getView(R.id.tv_lates);
        ImageView iv_zhankai = baseViewHolder.getView(R.id.iv_zhankai);
        ImageView iv_fill = baseViewHolder.getView(R.id.iv_fill);
        RelativeLayout rl_open = baseViewHolder.getView(R.id.rl_open);
        rv_second = baseViewHolder.getView(R.id.rv_second);
        tv_first.setText(conversation.getName());
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) iv_fill.getLayoutParams();
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) iv_zhankai.getLayoutParams();
        RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) tv_state.getLayoutParams();
        RelativeLayout.LayoutParams layoutParamsRv = (RelativeLayout.LayoutParams) rv_second.getLayoutParams();

        if (conversation.getOpen() == 1) {//未展开状态
            iv_zhankai.setBackgroundResource(R.mipmap.zhankai);
            if (conversation.getTotalNumQ() != null) {//有二级的时候，展示样式设置
                layoutParamsRv.bottomMargin = PixelTool.dip2px(getContext(), 30);
                rv_second.setLayoutParams(layoutParamsRv);
                rv_second.setVisibility(View.VISIBLE);
                conversation.setOpen(1);
            } else {
                layoutParamsRv.bottomMargin = PixelTool.dip2px(getContext(), 0);
                rv_second.setLayoutParams(layoutParamsRv);
                rv_second.setVisibility(View.GONE);
            }
        } else {
            iv_zhankai.setBackgroundResource(R.mipmap.zhnakai_up);
            rv_second.setVisibility(View.GONE);
        }

        if (conversation.isHasChildren()) {//是否有二级
            iv_zhankai.setVisibility(View.VISIBLE);
            layoutParams3.bottomMargin = PixelTool.dip2px(getContext(), 20);
            tv_state.setLayoutParams(layoutParams3);
        } else {
            iv_zhankai.setVisibility(View.GONE);
            layoutParams3.bottomMargin = PixelTool.dip2px(getContext(), 20);
            tv_state.setLayoutParams(layoutParams3);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            layoutParams2.addRule(RelativeLayout.CENTER_VERTICAL);
            iv_fill.setLayoutParams(layoutParams);
            iv_zhankai.setLayoutParams(layoutParams2);

        }
        if (conversation.isIfLatestExam()) {//是否上次练习标志
            tv_lates.setVisibility(View.VISIBLE);
        } else {
            tv_lates.setVisibility(View.INVISIBLE);
        }

        if (conversation.getRightRate() == null) {
            tv_state.setText("已完成：" + conversation.getDoneNum() + "/" + conversation.getTotalNum());
        } else {
            tv_state.setText("已完成：" + conversation.getDoneNum() + "/" + conversation.getTotalNum() + "     正确率：" + conversation.getRightRate());
        }

        Only2Adapter onlyAdapter = new Only2Adapter(R.layout.item_only2, conversation.getTotalNumQ(), courseType,mPresenter);
        rv_second.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_second.setAdapter(onlyAdapter);

    }

}
