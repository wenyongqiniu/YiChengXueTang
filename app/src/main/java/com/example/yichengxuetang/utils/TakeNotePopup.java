package com.example.yichengxuetang.utils;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yichengxuetang.R;
import com.example.yichengxuetang.adapter.MusicListAdapter;
import com.example.yichengxuetang.application.MyApplication;
import com.google.android.material.textfield.TextInputEditText;
import com.lxj.xpopup.core.BottomPopupView;

import java.util.ArrayList;


public class TakeNotePopup extends BottomPopupView {


    public static TextInputEditText ed_note;
    public static ImageView iv_close;

    public TakeNotePopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_take_note_layout;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ed_note = findViewById(R.id.ed_note);
        iv_close = findViewById(R.id.iv_close);
        iv_close.setOnClickListener(v -> dismiss());
    }

    //完全可见执行
    @Override
    protected void onShow() {
        super.onShow();
    }

    //完全消失执行
    @Override
    protected void onDismiss() {
        Log.e("tag", "知乎评论 onDismiss");
    }

   /* @Override
    protected int getMaxHeight() {
        return (int) (ScreenUtils.getScreenHeight(getContext()) * .1f);
    }*/

}