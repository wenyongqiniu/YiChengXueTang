package com.example.yichengxuetang.activitys.learningactivitys;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.yichengxuetang.R;
import com.example.yichengxuetang.bean.WallPaperResponse;
import com.example.yichengxuetang.contract.MainContract;
import com.example.yichengxuetang.fragments.learningcenterfragments.DakeCourseListFragment;
import com.example.yichengxuetang.fragments.learningcenterfragments.DakeNotesFragment;
import com.example.yichengxuetang.utils.MyJieCaoVideoView;
import com.example.yichengxuetang.utils.ScreenUtils;
import com.example.yichengxuetang.utils.TakeNotePopup;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.llw.mvplibrary.mvp.MvpActivity;
import com.llw.mvplibrary.network.utils.StatusBarUtils;
import com.lxj.xpopup.XPopup;

import java.util.ArrayList;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class VideoActivity extends MvpActivity<MainContract.MainPresenter> implements MainContract.IMainView {
    public static MyJieCaoVideoView playerStandard;
    private TextView tv_title_top;
    private ImageView iv_note;
    private RelativeLayout rl_left;
   // public static String videoUrl = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
    public static String videoUrl = "https://vfx.mtime.cn/Video/2019/03/21/mp4/190321153853126488.mp4";
    private AudioManager audioManager;

    private void init() {
        StatusBarUtils.setTransparent(this);
        StatusBarUtils.setTextDark(this, true);
        playerStandard = findViewById(R.id.playerstandard);
        tv_title_top = findViewById(R.id.tv_title_top);
        iv_note = findViewById(R.id.iv_note);
        rl_left = findViewById(R.id.rl_left);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        Glide.with(this)
                .load(videoUrl)
                .into(playerStandard.thumbImageView);
        playerStandard.setUp(videoUrl, MyJieCaoVideoView.SCREEN_LAYOUT_NORMAL, "");
        JCVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;  //横向
        JCVideoPlayer.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;  //纵向

        playerStandard.startVideo();
        Intent intent = getIntent();
        String courseId = intent.getStringExtra("courseId");
        String courseName = intent.getStringExtra("courseName");

        tv_title_top.setText(courseName);
        rl_left.setOnClickListener(v -> finish());

        iv_note.setOnClickListener(v -> showNotePop());
        TabLayout tab_dake = findViewById(R.id.tab_dake);
        ViewPager2 vp2_dake = findViewById(R.id.vp2_dake);
        ArrayList<Fragment> fragments = new ArrayList<>();
        ArrayList<String> title = new ArrayList<>();
        title.add("课程目录");
        title.add("课程笔记");

        fragments.add(new DakeCourseListFragment());
        fragments.add(new DakeNotesFragment());

        vp2_dake.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                Fragment fragment = fragments.get(position);
                return fragment;
            }

            @Override
            public int getItemCount() {
                return fragments.size();
            }
        });


        TabLayoutMediator mediator = new TabLayoutMediator(tab_dake, vp2_dake, (tab, position) -> tab.setText(title.get(position)));
        mediator.attach();
        Bundle bundle = new Bundle();
        bundle.putString("courseId", courseId);
        fragments.get(0).setArguments(bundle);

        for (int i = 0; i < title.size(); i++) {
            tab_dake.getTabAt(i).setCustomView(R.layout.main_top_item);
            TextView toMyTextView = tab_dake.getTabAt(i).getCustomView().findViewById(R.id.tv_top_item);
            //默认选择第一个tab,设置字体大小和默认风格为加粗
            toMyTextView.setText(title.get(i));
        }
        TextView toMyTextView = tab_dake.getTabAt(0).getCustomView().findViewById(R.id.tv_top_item);
        View tab_item_indicator = tab_dake.getTabAt(0).getCustomView().findViewById(R.id.tab_item_indicator);
        tab_item_indicator.setVisibility(View.VISIBLE);
        toMyTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        toMyTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        toMyTextView.setTextColor(Color.parseColor("#2A2A2A"));

        //看这里看这里看这里
        tab_dake.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                TextView tv = tab.getCustomView().findViewById(R.id.tv_top_item);
                tv.setText(title.get(position));
                tv.setTextColor(Color.parseColor("#2A2A2A"));
                tv.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
                tv.setTextSize(20);
                View tab_item_indicator = tab_dake.getTabAt(position).getCustomView().findViewById(R.id.tab_item_indicator);
                tab_item_indicator.setVisibility(View.VISIBLE);
                tv.invalidate();
                tab.getCustomView().findViewById(R.id.tv_top_item).setSelected(true);
                Bundle bundle = new Bundle();
                bundle.putString("courseId", courseId);
                fragments.get(position).setArguments(bundle);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                TextView tv = tab.getCustomView().findViewById(R.id.tv_top_item);
                tv.setText(title.get(position));
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                tv.setTextColor(Color.parseColor("#B5B5B5"));
                View tab_item_indicator = tab_dake.getTabAt(position).getCustomView().findViewById(R.id.tab_item_indicator);
                tab_item_indicator.setVisibility(View.INVISIBLE);
                tv.invalidate();
                tab.getCustomView().findViewById(R.id.tv_top_item).setSelected(false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    //记笔记弹框
    private void showNotePop() {
       new XPopup.Builder(this)
                .maxHeight(ScreenUtils.getScreenHeight(this))
                .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                .enableDrag(true)
                .asCustom(new TakeNotePopup(this))
                .show();

    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    protected MainContract.MainPresenter createPresenter() {
        return new MainContract.MainPresenter();
    }

    @Override
    public void getWallPaper(WallPaperResponse wallPaperResponse) {

    }

    @Override
    public void getFailed(Throwable e) {

    }
    /**
     * 获取视频文件第一帧图
     *
     * @param path 视频文件的路径
     * @return Bitmap 返回获取的Bitmap
     */
    public static Bitmap getVideoThumb(String path) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(path);
        return media.getFrameAtTime();
    }
    @Override
    public void initData(Bundle savedInstanceState) {
        init();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_video;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                //音量键up
                playerStandard.iv_volume.setBackgroundResource(R.drawable.jc_add_volume);
                return false;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                //音量键down
                if (audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)==AudioManager.RINGER_MODE_SILENT){
                    playerStandard.iv_volume.setBackgroundResource(R.drawable.jc_close_volume);
                }
                return false;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}