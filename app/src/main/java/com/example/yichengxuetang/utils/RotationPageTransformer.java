package com.example.yichengxuetang.utils;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.nineoldandroids.view.ViewHelper;

public class RotationPageTransformer extends ABaseTransformer {


    private static final float ROT_MOD = -15f;

    @Override
    protected void onTransform(View view, float position) {
        final float width = view.getWidth();
        final float height = view.getHeight();
        final float rotation = ROT_MOD * position * -1.25f;

        view.setPivotX(width * 0.5f);
        view.setPivotY(height);
        view.setRotation(rotation);
    }

    @Override
    protected boolean isPagingEnabled() {
        return true;
    }
}
