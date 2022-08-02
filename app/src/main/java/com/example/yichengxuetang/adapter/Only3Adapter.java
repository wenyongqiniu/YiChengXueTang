package com.example.yichengxuetang.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.yichengxuetang.R;
import com.example.yichengxuetang.activitys.questionbankactivitys.OnlyActivity;
import com.example.yichengxuetang.activitys.questionbankactivitys.OnlyResultActivity;
import com.example.yichengxuetang.bean.QuestionBankResponse;
import com.example.yichengxuetang.contract.QuestionBankContract;
import com.example.yichengxuetang.utils.PixelTool;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.example.yichengxuetang.fragments.practicecenter.PublicSubjectsFragment.busiTypeCode;
import static com.example.yichengxuetang.fragments.practicecenter.PublicSubjectsFragment.queryType;


public class Only3Adapter extends BaseQuickAdapter<QuestionBankResponse.DataBean.TotalNumQBean.List1Bean, BaseViewHolder> {


    public static RecyclerView rv_second;
    private String courseType;
    private QuestionBankContract.QuestionBankPresenter mPresenter;
    private List<QuestionBankResponse.DataBean.TotalNumQBean.List1Bean> list;

    public Only3Adapter(int layoutResId, @Nullable List<QuestionBankResponse.DataBean.TotalNumQBean.List1Bean> data, String courseType, QuestionBankContract.QuestionBankPresenter mPresenter) {
        super(layoutResId, data);
        this.courseType = courseType;
        this.mPresenter = mPresenter;
        this.list = data;
    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, QuestionBankResponse.DataBean.TotalNumQBean.List1Bean conversation) {

        TextView tv_first = baseViewHolder.getView(R.id.tv_first);
        TextView tv_state = baseViewHolder.getView(R.id.tv_state);
        TextView tv_lates = baseViewHolder.getView(R.id.tv_lates);
        ImageView iv_zhankai = baseViewHolder.getView(R.id.iv_zhankai);
        ImageView iv_fill = baseViewHolder.getView(R.id.iv_fill);
        RelativeLayout rl_only = baseViewHolder.getView(R.id.rl_only);

        rv_second = baseViewHolder.getView(R.id.rv_second);
        tv_first.setText(conversation.getName());

        rv_second = baseViewHolder.getView(R.id.rv_second);
        tv_first.setText(conversation.getName());


        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) iv_fill.getLayoutParams();
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) iv_zhankai.getLayoutParams();
        RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) tv_state.getLayoutParams();
        RelativeLayout.LayoutParams layoutParamsRv = (RelativeLayout.LayoutParams) rv_second.getLayoutParams();

      /*  if (baseViewHolder.getLayoutPosition() == list.size() - 1) {
            layoutParams3.bottomMargin = 0;
            tv_state.setLayoutParams(layoutParams3);
        } else {
            layoutParams3.bottomMargin = 22;
            tv_state.setLayoutParams(layoutParams3);
        }*/
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

    }

}
