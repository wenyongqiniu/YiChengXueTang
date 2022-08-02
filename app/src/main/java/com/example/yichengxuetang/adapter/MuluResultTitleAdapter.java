package com.example.yichengxuetang.adapter;

import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.yichengxuetang.R;
import com.example.yichengxuetang.bean.BatchMenuListSimluResponse;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class MuluResultTitleAdapter extends BaseQuickAdapter<BatchMenuListSimluResponse.DataBean, BaseViewHolder> {

    private int number;
    private List<BatchMenuListSimluResponse.DataBean> data;
    private int menuSize = 0;
    private boolean isFirst;
    public MuluResultTitleAdapter(int layoutResId, @Nullable List<BatchMenuListSimluResponse.DataBean> data, int number) {
        super(layoutResId, data);
        this.number = number;
        this.data = data;
    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, BatchMenuListSimluResponse.DataBean conversation) {

        TextView tv_simlu_title = baseViewHolder.getView(R.id.tv_simlu_title);
        RecyclerView rv_mulu_list = baseViewHolder.getView(R.id.rv_mulu_list);
        tv_simlu_title.setText(conversation.getTypeName());

        if (!isFirst) {
            if (baseViewHolder.getLayoutPosition() == 0) {
                menuSize = 0;
                MuluSimluListAdapter muluAdapter = new MuluSimluListAdapter(R.layout.item_mulu_list, conversation.getMenus(), menuSize, number);
                rv_mulu_list.setLayoutManager(new GridLayoutManager(getContext(), 6));
                rv_mulu_list.setAdapter(muluAdapter);
            } else {
                menuSize += data.get(baseViewHolder.getLayoutPosition() - 1).getMenus().size();
                MuluSimluListAdapter muluAdapter = new MuluSimluListAdapter(R.layout.item_mulu_list,
                        conversation.getMenus(), menuSize,
                        number);
                rv_mulu_list.setLayoutManager(new GridLayoutManager(getContext(), 6));
                rv_mulu_list.setAdapter(muluAdapter);
            }
        }
        if (baseViewHolder.getLayoutPosition() + 1 >= data.size()) {
            isFirst = true;
        }
    }

}
