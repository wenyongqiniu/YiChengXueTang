package com.example.yichengxuetang.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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


public class ConversationAdapter extends BaseQuickAdapter<Conversation, BaseViewHolder> {


    public ConversationAdapter(int layoutResId, @Nullable List<Conversation> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, Conversation conversation) {

        ImageView iv_other_head = baseViewHolder.getView(R.id.iv_other_head);
        TextView tv_other_content = baseViewHolder.getView(R.id.tv_other_content);
        ImageView iv_mine_head = baseViewHolder.getView(R.id.iv_mine_head);
        TextView tv_mine_content = baseViewHolder.getView(R.id.tv_mine_content);
        RelativeLayout rl_other = baseViewHolder.getView(R.id.rl_other);
        RelativeLayout rl_mine = baseViewHolder.getView(R.id.rl_mine);
        MessageContent latestMessage = conversation.getLatestMessage();

        String s = new Gson().toJson(latestMessage);

        try {
            String content = new JSONObject(s).getString("content");
            tv_mine_content.setText(content);
            tv_other_content.setText(content);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Glide.with(getContext()).load(conversation.getPortraitUrl()).apply(new RequestOptions().circleCrop()).into(iv_mine_head);
        Glide.with(getContext()).load(conversation.getPortraitUrl()).apply(new RequestOptions().circleCrop()).into(iv_other_head);

        if (conversation.getSenderUserId().equals("1234567")) {
            rl_mine.setVisibility(View.VISIBLE);
            rl_other.setVisibility(View.GONE);
        } else {
            rl_mine.setVisibility(View.GONE);
            rl_other.setVisibility(View.VISIBLE);
        }

    }

    private interface onItemClickListener {
        void position();
    }
}
