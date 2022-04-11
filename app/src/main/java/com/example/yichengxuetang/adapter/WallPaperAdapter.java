package com.example.yichengxuetang.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.yichengxuetang.R;
import com.example.yichengxuetang.bean.WallPaperResponse;

import java.util.List;

/**
 * 壁纸适配器
 * @author llw
 */
public class WallPaperAdapter extends BaseQuickAdapter<WallPaperResponse.ResBean.VerticalBean, BaseViewHolder> {

    public WallPaperAdapter(List<WallPaperResponse.ResBean.VerticalBean> data) {
        super(R.layout.item_wallpaper, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WallPaperResponse.ResBean.VerticalBean item) {
        ImageView imageView = helper.getView(R.id.image);
    }
}
