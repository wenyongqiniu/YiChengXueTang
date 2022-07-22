package com.example.yichengxuetang.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.yichengxuetang.R;
import com.example.yichengxuetang.bean.LearningCenterResponse;
import com.example.yichengxuetang.bean.QuestionBankTypeResponse;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.MessageContent;


public class ExchangeAdapter extends BaseQuickAdapter<QuestionBankTypeResponse.DataBean, BaseViewHolder> {


    public ExchangeAdapter(int layoutResId, @Nullable List<QuestionBankTypeResponse.DataBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, QuestionBankTypeResponse.DataBean conversation) {

        TextView tv_title = baseViewHolder.getView(R.id.tv_title);
        tv_title.setText(conversation.getName());

        if (conversation.getChoice()==1){
            tv_title.setBackgroundResource(R.drawable.excahnge_yes_shape);
            tv_title.setTextColor(Color.parseColor("#FE7400"));
            tv_title.setCompoundDrawablesWithIntrinsicBounds(null,null,getContext().getDrawable(R.mipmap.exchange_right),null);
        }else{
            tv_title.setBackgroundResource(R.drawable.excahnge_no_shape);
            tv_title.setTextColor(Color.parseColor("#989898"));
            tv_title.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
        }

    }

}
