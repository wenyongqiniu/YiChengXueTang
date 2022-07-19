package com.example.yichengxuetang.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.yichengxuetang.R;
import com.example.yichengxuetang.bean.SectionDetailResponse;
import com.example.yichengxuetang.utils.MusicListPopup;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.example.yichengxuetang.application.MyApplication.mediaPlayerIngHolder;

public class MusicListAdapter extends BaseQuickAdapter<SectionDetailResponse.AudioList, BaseViewHolder> {

    private int selectedPosition = -1;

    public MusicListAdapter(int layoutResId, @Nullable List<SectionDetailResponse.AudioList> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SectionDetailResponse.AudioList dataBean) {
        TextView tv_music_name = baseViewHolder.getView(R.id.tv_music_name);
        tv_music_name.setText(dataBean.getSectionName());

        if (dataBean.getPlayStatus() == 1 ) {
            tv_music_name.setTextColor(Color.parseColor("#FEA400"));
        } else {
            tv_music_name.setTextColor(Color.parseColor("#000000"));
        }


    }

}
