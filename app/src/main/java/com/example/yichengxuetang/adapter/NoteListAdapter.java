package com.example.yichengxuetang.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.yichengxuetang.R;
import com.example.yichengxuetang.bean.FindNoteResponse;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NoteListAdapter extends BaseQuickAdapter<FindNoteResponse.DataBean, BaseViewHolder> {


    public NoteListAdapter(int layoutResId, @Nullable List<FindNoteResponse.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, FindNoteResponse.DataBean dataBean) {
        TextView tv_note_title = baseViewHolder.getView(R.id.tv_note_title);
        TextView tv_note_content = baseViewHolder.getView(R.id.tv_note_content);
        TextView tv_note_time = baseViewHolder.getView(R.id.tv_note_time);
        TextView tv_delete_note = baseViewHolder.getView(R.id.tv_delete_note);
        tv_note_title.setText(dataBean.getSectionName());
        tv_note_content.setText(dataBean.getContent());
        tv_note_time.setText(dataBean.getCreatedTime());
    }

}
