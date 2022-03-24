package com.example.yichengxuetang.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yichengxuetang.R;


public class CustomerToastUtils extends Toast{

    public static TextView tv_toast;
    public static ImageView iv_warm;
    public static RelativeLayout tv_rl_toast;

    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link Application}
     *                or {@link Activity} object.
     */
    public CustomerToastUtils(Context context) {
        super(context);
    }

    public static Toast toastShow(Context context) {
        Toast toast2 = null;
        if (toast2 == null) {
            toast2 = new Toast(context);
            View view = LayoutInflater.from(context).inflate(R.layout.toast_custom, null);
            tv_toast = view.findViewById(R.id.tv_warm);
            iv_warm = view.findViewById(R.id.iv_warm);
            tv_rl_toast = view.findViewById(R.id.tv_rl_toast);
            toast2.setView(view);
            toast2.setGravity(Gravity.CENTER, 0, 0);
        }
        return toast2;
    }
}
