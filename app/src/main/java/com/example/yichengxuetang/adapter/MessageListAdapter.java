package com.example.yichengxuetang.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.yichengxuetang.R;
import com.google.gson.Gson;
import com.llw.mvplibrary.network.utils.DateUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.MessageContent;


public class MessageListAdapter extends BaseQuickAdapter<Conversation, BaseViewHolder> {


    public MessageListAdapter(int layoutResId, @Nullable List<Conversation> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, Conversation conversation) {

        ImageView iv_head = baseViewHolder.getView(R.id.iv_head);
        TextView tv_name = baseViewHolder.getView(R.id.tv_name);
        TextView tv_content = baseViewHolder.getView(R.id.tv_content);
        TextView tv_time = baseViewHolder.getView(R.id.tv_time);
        TextView tv_message_count = baseViewHolder.getView(R.id.tv_message_count);
        MessageContent latestMessage = conversation.getLatestMessage();

        String s = new Gson().toJson(latestMessage);
        try {
            String content = new JSONObject(s).getString("content");
            tv_content.setText(content);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        tv_time.setText(DateUtil.formatTime(conversation.getReceivedTime()));
        Glide.with(getContext()).load(conversation.getPortraitUrl()).apply(new RequestOptions().circleCrop()).into(iv_head);
        tv_name.setText(conversation.getSenderUserName());
        tv_message_count.setText(conversation.getUnreadMessageCount() + "");


    }

    private interface onItemClickListener {
        void position();
    }
}
