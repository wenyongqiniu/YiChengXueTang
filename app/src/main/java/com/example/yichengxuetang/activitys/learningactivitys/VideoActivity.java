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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;

import com.example.yichengxuetang.R;
import com.example.yichengxuetang.application.MyApplication;
import com.example.yichengxuetang.bean.CommitSuccessResponse;
import com.example.yichengxuetang.bean.DkCourseListResponse;
import com.example.yichengxuetang.bean.NoteDetailResponse;
import com.example.yichengxuetang.bean.XbCourseListResponse;
import com.example.yichengxuetang.contract.XbCourseListContract;
import com.example.yichengxuetang.fragments.learningcenterfragments.DakeCourseListFragment;
import com.example.yichengxuetang.fragments.learningcenterfragments.DakeNotesFragment;
import com.example.yichengxuetang.utils.CustomerToastUtils;
import com.example.yichengxuetang.utils.MyJieCaoVideoView;
import com.example.yichengxuetang.websocket.JWebSocketClient;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.textfield.TextInputEditText;
import com.llw.mvplibrary.BaseApplication;
import com.llw.mvplibrary.mvp.MvpActivity;
import com.llw.mvplibrary.network.utils.SpUtils;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.core.BottomPopupView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import io.rong.imkit.picture.tools.ScreenUtils;

public class VideoActivity extends MvpActivity<XbCourseListContract.XbCourseListPresenter> implements XbCourseListContract.XbCourseListView, View.OnTouchListener {
    public static MyJieCaoVideoView playerStandard;
    private TextView tv_title_top;
    private ImageView iv_note;
    private RelativeLayout rl_left;
    // public static String videoUrl = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
    public static String videoUrl = "";
    private AudioManager audioManager;
    private DkCourseListResponse.DataBean data;
    public static TextInputEditText ed_note;
    public static ImageView iv_close;
    public static TextView tv_keep_note;
    public static TextView tv_now_lesson;
    public static String sectionId = "";
    public static String noteId = "";
    public static String edContent = "";
    public static BasePopupView noteShow;
    private String currentSectionName = "";
    float lastRawX, lastRawY;//???????????????????????????????????????
    public static HashMap<String, String> stringStringHashMap;
    public static JWebSocketClient client;
    public static Long durationTime;
    private String videoSectionId="";
    private String contentType="";

    //???????????????
    public void showNotePop() {
        TakeNotePopup takeNotePopup = new TakeNotePopup(context);
        noteShow = new XPopup.Builder(context)
                .maxHeight(ScreenUtils.getScreenHeight(context) * 12 / 13)
                .moveUpToKeyboard(false) //????????????????????????????????????????????????????????????
                .enableDrag(true)
                .asCustom(takeNotePopup)
                .show();


    }

    @Override
    public void finish() {
        super.finish();
        if (client != null) {
            client.close();
        }
    }

    public class TakeNotePopup extends BottomPopupView {

        public TakeNotePopup(@NonNull Context context) {
            super(context);
        }

        @Override
        protected int getImplLayoutId() {
            return R.layout.popup_take_note_layout;
        }

        @Override
        public void onCreate() {
            super.onCreate();

            ed_note = findViewById(R.id.ed_note);
            tv_keep_note = findViewById(R.id.tv_keep_note);
            tv_now_lesson = findViewById(R.id.tv_now_lesson);
            iv_close = findViewById(R.id.iv_close);
            iv_close.setOnClickListener(v -> dismiss());


            ed_note.setFocusableInTouchMode(true);
            ed_note.requestFocus();
            InputMethodManager inputMethodManager = (InputMethodManager) ed_note.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(ed_note, 0);

            currentSectionName = SpUtils.getSpString(context, "currentSectionName", "");

            mPresenter.noteDetail(sectionId);
            videoSectionId = SpUtils.getSpString(context, "videoSectionId", "");
            tv_now_lesson.setText("???????????????" + currentSectionName);
            ed_note.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    edContent = charSequence.toString().trim();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            //????????????
            tv_keep_note.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ("".equals(edContent)) {
                        CustomerToastUtils.toastShow(context).show();
                        CustomerToastUtils.tv_toast.setText("????????????");
                        CustomerToastUtils.iv_warm.setBackgroundResource(R.mipmap.warm);
                    } else {
                        mPresenter.keepNote(sectionId, edContent, noteId);
                        EventBus.getDefault().post("??????");//???fragment????????????
                    }
                }
            });

        }

        //??????????????????
        @Override
        protected void onShow() {
            super.onShow();
        }

        //??????????????????
        @Override
        protected void onDismiss() {
            Log.e("tag", "???????????? onDismiss");
        }


    }


    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }


    @Override
    public void getFailed(Throwable e) {

    }

    /**
     * ??????????????????????????????
     *
     * @param path ?????????????????????
     * @return Bitmap ???????????????Bitmap
     */
    public static Bitmap getVideoThumb(String path) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(path);
        return media.getFrameAtTime();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    @Subscribe
    public void initData(Bundle savedInstanceState) {

        EventBus.getDefault().register(this);
        stringStringHashMap = new HashMap<>();
        playerStandard = findViewById(R.id.playerstandard);
        rl_left = findViewById(R.id.rl_left);
        tv_title_top = findViewById(R.id.tv_title_top);
        iv_note = findViewById(R.id.iv_note);
        iv_note.setOnTouchListener(this);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        Glide.with(this)
                .load(videoUrl)
                .into(playerStandard.thumbImageView);
        JCVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;  //??????
        JCVideoPlayer.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;  //??????


        Intent intent = getIntent();
        String courseId = intent.getStringExtra("courseId");
        String courseName = intent.getStringExtra("courseName");
        contentType = intent.getStringExtra("contentType");

        tv_title_top.setText(courseName);
        rl_left.setOnClickListener(v -> finish());


        iv_note.setOnClickListener(v -> showNotePop());

        mPresenter.getDkCourseList(courseId);
        TabLayout tab_dake = findViewById(R.id.tab_dake);
        ViewPager2 vp2_dake = findViewById(R.id.vp2_dake);
        ArrayList<Fragment> fragments = new ArrayList<>();
        ArrayList<String> title = new ArrayList<>();
        title.add("????????????");
        title.add("????????????");

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
        bundle.putString("contentType", contentType);
        fragments.get(0).setArguments(bundle);

        for (int i = 0; i < title.size(); i++) {
            tab_dake.getTabAt(i).setCustomView(R.layout.main_top_item);
            TextView toMyTextView = tab_dake.getTabAt(i).getCustomView().findViewById(R.id.tv_top_item);
            //?????????????????????tab,??????????????????????????????????????????
            toMyTextView.setText(title.get(i));
        }
        TextView toMyTextView = tab_dake.getTabAt(0).getCustomView().findViewById(R.id.tv_top_item);
        View tab_item_indicator = tab_dake.getTabAt(0).getCustomView().findViewById(R.id.tab_item_indicator);
        tab_item_indicator.setVisibility(View.VISIBLE);
        toMyTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        toMyTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        toMyTextView.setTextColor(Color.parseColor("#2A2A2A"));

        //???????????????????????????
        tab_dake.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                TextView tv = tab.getCustomView().findViewById(R.id.tv_top_item);
                tv.setText(title.get(position));
                tv.setTextColor(Color.parseColor("#2A2A2A"));
                tv.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//??????
                tv.setTextSize(20);
                View tab_item_indicator = tab_dake.getTabAt(position).getCustomView().findViewById(R.id.tab_item_indicator);
                tab_item_indicator.setVisibility(View.VISIBLE);
                tv.invalidate();
                tab.getCustomView().findViewById(R.id.tv_top_item).setSelected(true);
                Bundle bundle = new Bundle();
                bundle.putString("courseId", courseId);
                bundle.putString("contentType", contentType);
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

    @Override
    public int getLayoutId() {
        return R.layout.activity_video;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                //?????????up
                playerStandard.iv_volume.setBackgroundResource(R.drawable.jc_add_volume);
                return false;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                //?????????down
                if (audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) == AudioManager.RINGER_MODE_SILENT) {
                    playerStandard.iv_volume.setBackgroundResource(R.drawable.jc_close_volume);
                }
                return false;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected XbCourseListContract.XbCourseListPresenter createPresenter() {
        return new XbCourseListContract.XbCourseListPresenter();
    }

    @Override
    public void getWallPaper(XbCourseListResponse wallPaperResponse) {

    }

    @Override
    public void getDkCourseList(DkCourseListResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {
            data = wallPaperResponse.getData();
            // if (sectionId.equals("")) {
            sectionId = data.getDefaultSectionId();
            // }
            videoUrl = data.getDefaultVideoUrl();

            if (videoUrl != null) {
                playerStandard.setUp(videoUrl, MyJieCaoVideoView.SCREEN_LAYOUT_NORMAL, "");
                playerStandard.startVideo();
                long playTime = getPlayTime(videoUrl + "");
                stringStringHashMap.put("token", BaseApplication.token);
                stringStringHashMap.put("sectionId", sectionId);
                stringStringHashMap.put("totalTime", playTime / 1000 + "");
                StringBuffer stringBuffer = new StringBuffer();
                for (String o : stringStringHashMap.keySet()) {
                    System.out.println("key" + o + " value" + stringStringHashMap.get(o));
                    stringBuffer.append(o + "=" + stringStringHashMap.get(o) + "&");
                }
                StringBuffer delete = stringBuffer.delete(stringBuffer.length() - 1, stringBuffer.length());
                Log.e("delete", "getDkCourseList: " + MyApplication.WEB_SOCKET + delete);

                //??????websocket
                URI uri = URI.create(MyApplication.WEB_SOCKET + delete);
                client = new JWebSocketClient(uri) {
                    @Override
                    public void onMessage(String message) {
                        //message????????????????????????
                        Log.e("JWebSClientService", message);
                    }
                };
                try {
                    client.connectBlocking();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                CustomerToastUtils.toastShow(context).show();
                CustomerToastUtils.tv_toast.setText("??????????????????");
                CustomerToastUtils.iv_warm.setBackgroundResource(R.mipmap.warm);
            }

        }

    }

    /**
     * ??????url???????????????????????????
     *
     * @param url
     * @return
     */
    public static long getPlayTime(String url) {
        MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();
        try {
            if (url != null) {
                HashMap<String, String> headers;
                headers = new HashMap<>();
                headers.put("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.4.2; zh-CN; MW-KW-001 Build/JRO03C) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 UCBrowser/1.0.0.001 U4/0.8.0 Mobile Safari/533.1");
                metadataRetriever.setDataSource(url, headers);
            } else {
                //mmr.setDataSource(mFD, mOffset, mLength);
            }
            //??????(??????)
            durationTime = Long.valueOf(metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
            String width = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);//???
            String height = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);//???
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            metadataRetriever.release();
        }
        return durationTime;
    }


    @Override
    public void getKeepNote(CommitSuccessResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {
            CustomerToastUtils.toastShow(context).show();
            CustomerToastUtils.tv_toast.setText("????????????");
            CustomerToastUtils.iv_warm.setBackgroundResource(R.drawable.ic_baseline_check_circle_outline_24);
            noteShow.dismiss();
        }

    }

    @Override
    public void getNoteDetail(NoteDetailResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {
            NoteDetailResponse.DataBean data = wallPaperResponse.getData();
            if (data != null) {
                tv_now_lesson.setText("???????????????" + data.getSectionName());
                ed_note.setText(data.getContent());
                noteId = data.getId();
            } else {
                if (videoSectionId.equals("")) {
                    sectionId = this.data.getDefaultSectionId();
                }
                if (currentSectionName.equals("")) {
                    currentSectionName = this.data.getDefaultSectionName();
                }
                noteId = "";
                tv_now_lesson.setText("???????????????" + currentSectionName);
            }
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        boolean isMove = false;

        switch (v.getId()) {
            case R.id.iv_note:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastRawX = event.getRawX();
                        lastRawY = event.getRawY();
                        iv_note.bringToFront();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int dx = (int) (event.getRawX() - lastRawX);//????????????
                        int dy = (int) (event.getRawY() - lastRawY);//????????????
                        setRelativeViewLocation(iv_note, iv_note.getLeft() + dx, iv_note.getTop() + dy, iv_note.getRight() + dx, iv_note.getBottom() + dy);//??????????????????
                        lastRawX = event.getRawX();
                        lastRawY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
                        int dx1 = (int) (event.getRawX() - lastRawX);//????????????
                        int dy1 = (int) (event.getRawY() - lastRawY);//????????????
                        if (Math.abs(dx1) > 2 || Math.abs(dy1) > 2) {//??????????????????,????????????????????????????????????????????????????????????
                            //????????????????????????????????????????????????????????????????????????????????????????????????
                            setRelativeViewLocation(iv_note, iv_note.getLeft() + dx1, iv_note.getTop() + dy1, iv_note.getRight() + dx1, iv_note.getBottom() + dy1);//??????????????????
                            //???????????????
                            isMove = true;
                        }
                    case MotionEvent.ACTION_CANCEL:

                        break;
                }
                break;
            default:

                break;
        }


        return isMove ? true : super.onTouchEvent(event);//??????????????????false,??????true,?????????????????????????????????
    }

    private void setRelativeViewLocation(View view, int left, int top, int right, int bottom) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(right - left, bottom - top);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        ViewParent parent = view.getParent();
        View p = (View) parent;
        int marginRight = p.getWidth() - right;
        int marginBottom = p.getHeight() - bottom;
        params.setMargins(left, top, marginRight, marginBottom);
        view.setLayoutParams(params);
    }

}