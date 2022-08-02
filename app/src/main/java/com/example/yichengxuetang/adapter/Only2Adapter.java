package com.example.yichengxuetang.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.yichengxuetang.R;
import com.example.yichengxuetang.activitys.questionbankactivitys.OnlyActivity;
import com.example.yichengxuetang.activitys.questionbankactivitys.OnlyResultActivity;
import com.example.yichengxuetang.bean.QuestionBankResponse;
import com.example.yichengxuetang.contract.QuestionBankContract;
import com.example.yichengxuetang.fragments.practicecenter.PublicSubjectsFragment;
import com.example.yichengxuetang.utils.PixelTool;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.example.yichengxuetang.fragments.practicecenter.PublicSubjectsFragment.busiTypeCode;
import static com.example.yichengxuetang.fragments.practicecenter.PublicSubjectsFragment.queryType;


public class Only2Adapter extends BaseQuickAdapter<QuestionBankResponse.DataBean.TotalNumQBean, BaseViewHolder> {


    public static RecyclerView rv_second;
    private String courseType;
    private QuestionBankContract.QuestionBankPresenter mPresenter;
    public static int position;

    public Only2Adapter(int layoutResId, @Nullable List<QuestionBankResponse.DataBean.TotalNumQBean> data, String courseType, QuestionBankContract.QuestionBankPresenter mPresenter) {
        super(layoutResId, data);
        this.courseType = courseType;
        this.mPresenter = mPresenter;
    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, QuestionBankResponse.DataBean.TotalNumQBean conversation) {

        TextView tv_first = baseViewHolder.getView(R.id.tv_first);
        TextView tv_state = baseViewHolder.getView(R.id.tv_state);
        TextView tv_lates = baseViewHolder.getView(R.id.tv_lates);
        ImageView iv_zhankai = baseViewHolder.getView(R.id.iv_zhankai);
        ImageView iv_fill = baseViewHolder.getView(R.id.iv_fill);
        RelativeLayout rl_only = baseViewHolder.getView(R.id.rl_only);
        View view = baseViewHolder.getView(R.id.view);

        rv_second = baseViewHolder.getView(R.id.rv_second);
        tv_first.setText(conversation.getName());

        tv_first.setTextSize(15f);


        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) iv_fill.getLayoutParams();
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) iv_zhankai.getLayoutParams();
        RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) tv_state.getLayoutParams();
        RelativeLayout.LayoutParams layoutParamsF = (RelativeLayout.LayoutParams) tv_first.getLayoutParams();
        RelativeLayout.LayoutParams layoutParamsRv = (RelativeLayout.LayoutParams) rv_second.getLayoutParams();


        if (baseViewHolder.getLayoutPosition()==0){
            view.setVisibility(View.GONE);
        }else{
            view.setVisibility(View.VISIBLE);
        }
        if (conversation.getOpen() == 1) {//未展开状态
            iv_zhankai.setBackgroundResource(R.mipmap.open_two);
            if (conversation.getList1() != null) {//有二级的时候，展示样式设置
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
            iv_zhankai.setBackgroundResource(R.mipmap.open_two_up);
            rv_second.setVisibility(View.GONE);
        }

        if (conversation.isHasChildren()) {//是否有二级
            iv_zhankai.setVisibility(View.VISIBLE);
            layoutParams3.bottomMargin = PixelTool.dip2px(getContext(), 20);
            tv_state.setLayoutParams(layoutParams3);
        } else {
            iv_zhankai.setVisibility(View.INVISIBLE);
            layoutParams3.bottomMargin = PixelTool.dip2px(getContext(), 20);
            tv_state.setLayoutParams(layoutParams3);
            tv_first.setLayoutParams(layoutParamsF);
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


        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (conversation.getTotalNum() != conversation.getDoneNum()) {
                    Intent intent = new Intent(getContext(), OnlyActivity.class);
                    intent.putExtra("typeId", conversation.getId());
                    intent.putExtra("courseType", courseType);
                    intent.putExtra("examType", 1);
                    intent.putExtra("questionTitle", conversation.getName());
                    getContext().startActivity(intent);
                } else {
                    Intent intent = new Intent(getContext(), OnlyResultActivity.class);
                    intent.putExtra("questionTypeId", conversation.getId());
                    getContext().startActivity(intent);
                }
            }
        });
        Only3Adapter onlyAdapter = new Only3Adapter(R.layout.item_only3, conversation.getList1(), courseType, mPresenter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rv_second.setLayoutManager(linearLayoutManager);
        rv_second.setAdapter(onlyAdapter);
        iv_zhankai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (conversation.getOpen() == 0) {
                    PublicSubjectsFragment.onFirst = 2;
                    position = baseViewHolder.getLayoutPosition();
                    mPresenter.getQuestionBank(queryType + "", busiTypeCode + "", conversation.getId());
                    conversation.setOpen(1);
                } else {
                    conversation.setOpen(0);
                }

                onlyAdapter.notifyDataSetChanged();
                Only2Adapter.this.notifyDataSetChanged();

            }
        });

    }

}
