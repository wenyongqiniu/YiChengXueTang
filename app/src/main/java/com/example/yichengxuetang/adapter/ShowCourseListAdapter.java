package com.example.yichengxuetang.adapter;

import android.content.Intent;
import android.text.Html;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.yichengxuetang.R;
import com.example.yichengxuetang.activitys.learningactivitys.DakeCourseListActivity;
import com.example.yichengxuetang.activitys.learningactivitys.XbCourseListActivity;
import com.example.yichengxuetang.bean.ShowCourseListResponse;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ShowCourseListAdapter extends BaseQuickAdapter<ShowCourseListResponse.DataBean.XbCourseListBean, BaseViewHolder> {
    public ShowCourseListAdapter(int layoutResId, @Nullable List<ShowCourseListResponse.DataBean.XbCourseListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ShowCourseListResponse.DataBean.XbCourseListBean showCourseListResponse) {
        baseViewHolder.setText(R.id.tv_course_name, showCourseListResponse.getCourseName());
        //首先是拼接字符串
        String content = "<font color=\"#FE8000\">" + showCourseListResponse.getCurrentDay() + "</font>";
        //然后直接setText()
        String tvContent = "当前第  " + content + "  天 / 共" + showCourseListResponse.getTotalDay() + "天";
        baseViewHolder.setText(R.id.tv_day, Html.fromHtml(tvContent));
        baseViewHolder.setText(R.id.tv_sign_up, showCourseListResponse.getCustomerNum() + "人已报名");

        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), XbCourseListActivity.class);
                intent.putExtra("courseId",showCourseListResponse.getCourseId());
                getContext().startActivity(intent);
            }
        });


        RecyclerView rv_head_view = baseViewHolder.getView(R.id.rv_head_view);


        ArrayList<String> strings = new ArrayList<>();
        strings.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fup.enterdesk.com%2F22%2Fa6%2F5d%2F83%2Fae689318a827319a98788026bb32d99a.jpg&refer=http%3A%2F%2Fup.enterdesk.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1651039722&t=41499b866365fcfeada73f93ccf1a58a");
        strings.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fup.enterdesk.com%2Fedpic_source%2Fc7%2F8a%2F03%2Fc78a030abf9940543004b4fea7ef3902.jpeg&refer=http%3A%2F%2Fup.enterdesk.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1651039631&t=010add2bebbe3b2d1601e3a808fe67c3");
        strings.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fup.enterdesk.com%2Fedpic%2F90%2Fd5%2F13%2F90d513f306c22d4fb218418e12468fc8.jpg&refer=http%3A%2F%2Fup.enterdesk.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1651039629&t=f65450260626c194e0e2df8cd74746c5");
        strings.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fup.enterdesk.com%2Fedpic_source%2F9e%2F32%2F9a%2F9e329acc0c79523b0204f6ed7ea1e45e.jpg&refer=http%3A%2F%2Fup.enterdesk.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1651039639&t=90aba4c7925a4030fb9128c1162fcb7f");

        showCourseListResponse.setImgUrls(strings);
        ShowHeadViewAdapter showCourseListAdapter = new ShowHeadViewAdapter(R.layout.headview_item, showCourseListResponse.getImgUrls());

        rv_head_view.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rv_head_view.setAdapter(showCourseListAdapter);
        showCourseListAdapter.notifyDataSetChanged();

    }

}
