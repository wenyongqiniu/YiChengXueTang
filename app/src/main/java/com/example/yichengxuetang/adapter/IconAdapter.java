package com.example.yichengxuetang.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.yichengxuetang.R;
import com.example.yichengxuetang.activitys.questionbankactivitys.OnlyActivity;
import com.example.yichengxuetang.activitys.questionbankactivitys.SimulationActivity;
import com.example.yichengxuetang.activitys.questionbankactivitys.SmartTopicActivity;
import com.example.yichengxuetang.bean.IconResponse;
import com.example.yichengxuetang.bean.QuestionBankResponse;
import com.example.yichengxuetang.utils.PixelTool;
import com.llw.mvplibrary.network.utils.SpUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class IconAdapter extends BaseQuickAdapter<IconResponse, BaseViewHolder> {

    private String courseType;

    public IconAdapter(int layoutResId, @Nullable List<IconResponse> data, String courseType) {
        super(layoutResId, data);
        this.courseType = courseType;
    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, IconResponse conversation) {

        TextView tv_name = baseViewHolder.getView(R.id.tv_name);
        tv_name.setText(conversation.getName());
        tv_name.setCompoundDrawablesWithIntrinsicBounds(null, getContext().getDrawable(conversation.getIcon()), null, null);

        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (conversation.getId() == 2) {//智能刷题
                    String remember = SpUtils.getSpString(getContext(), "remember", "");
                    if ("".equals(remember)){
                        Intent intent = new Intent(getContext(), SmartTopicActivity.class);
                        intent.putExtra("typeId", "");
                        intent.putExtra("courseType", courseType);
                        intent.putExtra("examType", conversation.getId());
                        intent.putExtra("questionTitle", "智能刷题");
                        getContext().startActivity(intent);
                    }else{
                        Intent intent = new Intent(getContext(), OnlyActivity.class);
                        intent.putExtra("typeId", "");
                        intent.putExtra("courseType", courseType);
                        intent.putExtra("examType", conversation.getId());
                        intent.putExtra("questionTitle", "智能刷题");
                        getContext().startActivity(intent);
                    }
                } else if (conversation.getId() == 3) {//模拟考试
                    Intent intent = new Intent(getContext(), SimulationActivity.class);
                    intent.putExtra("typeId", "");
                    intent.putExtra("courseType", courseType);
                    intent.putExtra("examType", conversation.getId());
                    intent.putExtra("questionTitle", "模拟考试");
                    getContext().startActivity(intent);
                } else if (conversation.getId() == 4) {//历年真题

                }
            }
        });

    }

}
