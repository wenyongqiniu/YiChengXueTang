package com.example.yichengxuetang.activitys.learningactivitys;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;

import com.example.yichengxuetang.R;
import com.example.yichengxuetang.application.MyApplication;
import com.example.yichengxuetang.bean.CommitSuccessResponse;
import com.example.yichengxuetang.bean.NoteDetailResponse;
import com.example.yichengxuetang.bean.SectionDetailResponse;
import com.example.yichengxuetang.contract.SectionDetailContract;
import com.example.yichengxuetang.mediaplayer.FloatWindow;
import com.example.yichengxuetang.mediaplayer.OpenMusicActivity;
import com.example.yichengxuetang.utils.CustomerToastUtils;
import com.example.yichengxuetang.utils.PlaybackInfoListener;
import com.example.yichengxuetang.utils.TakeNotePopup;
import com.example.yichengxuetang.utils.ToastUtils;
import com.example.yichengxuetang.utils.WebViewSeetings;
import com.example.yichengxuetang.websocket.JWebSocketClient;
import com.google.android.material.textfield.TextInputEditText;
import com.just.agentweb.AgentWebView;
import com.ljb.page.PageState;
import com.ljb.page.PageStateLayout;
import com.llw.mvplibrary.BaseApplication;
import com.llw.mvplibrary.mvp.MvpActivity;
import com.llw.mvplibrary.network.utils.SpUtils;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.core.BottomPopupView;
import com.wanglu.photoviewerlibrary.PhotoViewer;

import org.greenrobot.eventbus.EventBus;

import java.net.URI;
import java.util.HashMap;

import io.rong.imkit.picture.tools.ScreenUtils;

import static com.example.yichengxuetang.application.MyApplication.loadingPopupView;
import static com.example.yichengxuetang.application.MyApplication.mContext;
import static com.example.yichengxuetang.application.MyApplication.mediaPlayerIngHolder;


public class XbSectionDetailActivity extends MvpActivity<SectionDetailContract.SectionDetailPresenter> implements SectionDetailContract.SectionDetailView, PlaybackInfoListener {

    private AgentWebView web_section;
    private TextView tv_title_top;
    private TextView tv_dohomework;
    private TextView tv_remmber_note;
    private ImageView iv_big_img;
    private RelativeLayout rl_left;
    private RelativeLayout rl_home;
    private RelativeLayout rl_home2;
    private PageStateLayout page_layout;
    private TextView retry;
    private View view_homework;
    private ImageView iv_pause_start;
    private SectionDetailResponse.DataBean data;
    private String title;
    public static String sectionId = "";//当前小节id
    public static String lastSectionId = "";//上一次的id
    public static String finishSectionId = "";//回退的id
    public static HashMap<String, String> stringStringHashMap;
    public static JWebSocketClient client;

    public static TextInputEditText ed_note;
    public static ImageView iv_close;
    public static TextView tv_keep_note;
    public static TextView tv_now_lesson;
    public static String noteId = "";
    public static String edContent = "";
    public static BasePopupView noteShow;

    @Override
    protected SectionDetailContract.SectionDetailPresenter createPresenter() {
        return new SectionDetailContract.SectionDetailPresenter();
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void getSectionDetail(SectionDetailResponse wallPaperResponse) {
        loadingPopupView.dismiss();
        if (wallPaperResponse.getCode() == 0) {
            page_layout.setPage(PageState.STATE_SUCCESS);
            data = wallPaperResponse.getData();
            if (data.getHasTask() == 0) {//无作业
                rl_home.setVisibility(View.GONE);
                view_homework.setVisibility(View.GONE);

            } else {
                if (data.getTaskStatus() == 0) {//作业未解锁
                    rl_home.setBackgroundResource(R.drawable.homework_unlock_shape);
                } else {
                    rl_home.setBackgroundResource(R.drawable.login_phone_shape);
                }
                view_homework.setVisibility(View.VISIBLE);

            }
            web_section.addJavascriptInterface(new MJavascriptInterface(), "imagelistener");
            web_section.setWebViewClient(new WebViewSeetings.MyWebViewClient());

            if (!"".equals(finishSectionId)) {
                tv_title_top.setText(data.getSectionName());
                web_section.loadDataWithBaseURL(null, WebViewSeetings.setWebVIewImage(data.getContent()), "text/html", "utf-8", null);
            } else {
                if (data.getAudioUrl() != null && !"".equals(data.getAudioUrl())) {
                    FloatWindow.get().show();
                    if (!lastSectionId.equals(sectionId)) {
                        mediaPlayerIngHolder.loadMedia(data.getAudioUrl());
                        lastSectionId = sectionId;
                    }
                    mediaPlayerIngHolder.setmPlaybackInfoListener(this);
                } else {
                    FloatWindow.get().hide();
                    mediaPlayerIngHolder.release();
                }
                title = data.getSectionName();
                tv_title_top.setText(data.getSectionName());
                web_section.loadDataWithBaseURL(null, WebViewSeetings.setWebVIewImage(data.getContent()), "text/html", "utf-8", null);

            }

        } else {
            page_layout.setPage(PageState.STATE_EMPTY);
        }

    }

    @Override
    public void getNoteDetail(NoteDetailResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {
            NoteDetailResponse.DataBean data = wallPaperResponse.getData();
            if (data != null) {
                tv_now_lesson.setText("当前课程：" + data.getSectionName());
                ed_note.setText(data.getContent());
                noteId = data.getId();
            }
        }
    }

    @Override
    public void getKeepNote(CommitSuccessResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {
            CustomerToastUtils.toastShow(context).show();
            CustomerToastUtils.tv_toast.setText("保存成功");
            CustomerToastUtils.iv_warm.setBackgroundResource(R.drawable.ic_baseline_check_circle_outline_24);
            noteShow.dismiss();
        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        if (mediaPlayerIngHolder.isPlaying()) {
            iv_pause_start.setBackgroundResource(R.mipmap.out_continiu);
        } else {
            iv_pause_start.setBackgroundResource(R.mipmap.out_pause);
        }
    }

    @Override
    public void getFailed(Throwable e) {
        loadingPopupView.dismiss();
        page_layout.setPage(PageState.STATE_ERROR);
        ToastUtils.showLong(context, e.getMessage());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayerIngHolder.setmPlaybackInfoListener(this);
        if (!"".equals(finishSectionId)) {
            lastSectionId = finishSectionId;
            loadingPopupView.show();
            mPresenter.geSectionDetail(finishSectionId);
        }
    }


    //记笔记弹框
    public void showNotePop() {
        TakeNotePopup takeNotePopup = new TakeNotePopup(context);
        noteShow = new XPopup.Builder(context)
                .maxHeight(ScreenUtils.getScreenHeight(context) * 12 / 13)
                .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                .enableDrag(true)
                .asCustom(takeNotePopup)
                .show();
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

            tv_now_lesson.setText("当前课程：" + title);
            mPresenter.noteDetail(sectionId);
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

            //保存笔记
            tv_keep_note.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ("".equals(edContent)) {
                        CustomerToastUtils.toastShow(context).show();
                        CustomerToastUtils.tv_toast.setText("笔记为空");
                        CustomerToastUtils.iv_warm.setBackgroundResource(R.mipmap.warm);
                    } else {
                        mPresenter.keepNote(sectionId, edContent, noteId);
                        EventBus.getDefault().post("刷新");//在fragment里面刷新
                    }
                }
            });

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


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initData(Bundle savedInstanceState) {
        stringStringHashMap = new HashMap<>();
        Intent intent = getIntent();
        sectionId = intent.getStringExtra("sectionId");
        String contentType = intent.getStringExtra("contentType");
        finishSectionId = "";
        title = intent.getStringExtra("title");
        loadingPopupView = new XPopup.Builder(this)
                .dismissOnBackPressed(true)
                .asLoading("加载中");
        page_layout = findViewById(R.id.page_layout);
        page_layout.setPage(PageState.STATE_LOADING);
        page_layout.setContentView(R.layout.activity_section_detail);
        web_section = findViewById(R.id.web_section);
        iv_big_img = findViewById(R.id.iv_big_img);
        tv_title_top = findViewById(R.id.tv_title_top);
        view_homework = findViewById(R.id.view_homework);
        retry = findViewById(R.id.retry);
        rl_left = findViewById(R.id.rl_left);
        rl_home = findViewById(R.id.rl_home);
        rl_home2 = findViewById(R.id.rl_home2);
        tv_dohomework = findViewById(R.id.tv_dohomework);
        tv_remmber_note = findViewById(R.id.tv_remmber_note);
        rl_left.setOnClickListener(v -> finish());
        mPresenter.geSectionDetail(sectionId);
        View view = FloatWindow.get().getView();
        iv_pause_start = view.findViewById(R.id.iv_pause_start);

        tv_remmber_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotePop();
            }
        });

        //写作业
        tv_dohomework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHomeWork = new Intent(XbSectionDetailActivity.this, DoHomeWorkActivity.class);
                intentHomeWork.putExtra("sectionId", sectionId);
                intentHomeWork.putExtra("examType", "");
                startActivity(intentHomeWork);
            }
        });

        if (contentType.equals("dake")) {
            rl_home2.setVisibility(View.VISIBLE);
            rl_home.setVisibility(View.INVISIBLE);
        } else {
            rl_home.setVisibility(View.VISIBLE);
            rl_home2.setVisibility(View.INVISIBLE);
        }
        FloatWindow.get().setOnClickListener(viewId -> {
            switch (viewId.getId()) {
                case R.id.iv_easy_float:
                    Intent intentMusic = new Intent(mContext, OpenMusicActivity.class);
                    intentMusic.putExtra("title", tv_title_top.getText() + "");
                    intentMusic.putExtra("audioList", data.getAudioList());
                    startActivity(intentMusic);
                    overridePendingTransition(R.anim.anim_bottom_in, 0);
                    break;
                case R.id.iv_pause_start:
                    if (mediaPlayerIngHolder.isPlaying()) {
                        mediaPlayerIngHolder.pause();
                        iv_pause_start.setBackgroundResource(R.mipmap.out_pause);
                    } else {
                        iv_pause_start.setBackgroundResource(R.mipmap.out_continiu);
                        mediaPlayerIngHolder.play();
                    }
                    break;
                case R.id.iv_close:
                    mediaPlayerIngHolder.release();
                    FloatWindow.get().hide();
                    break;
            }
        });
        initMusicNotification();
        //加载出错点击重试
        retry.setOnClickListener(v -> {
            page_layout.setPage(PageState.STATE_LOADING);
            mPresenter.geSectionDetail(sectionId);
        });

        //写作业
        rl_home.setOnClickListener(view1 -> {
            if (data.getTaskStatus() == 0) {//作业未解锁
                CustomerToastUtils.toastShow(context).show();
                CustomerToastUtils.tv_toast.setText("作业未解锁");
            } else {
                Intent intentHomeWork = new Intent(XbSectionDetailActivity.this, DoHomeWorkActivity.class);
                intentHomeWork.putExtra("sectionId", sectionId);
                intentHomeWork.putExtra("examType", "");
                startActivity(intentHomeWork);
            }
        });

        stringStringHashMap.put("token", BaseApplication.token);
        stringStringHashMap.put("sectionId", sectionId);
        StringBuffer stringBuffer = new StringBuffer();
        for (String o : stringStringHashMap.keySet()) {
            System.out.println("key" + o + " value" + stringStringHashMap.get(o));
            stringBuffer.append(o + "=" + stringStringHashMap.get(o) + "&");
        }
        StringBuffer delete = stringBuffer.delete(stringBuffer.length() - 1, stringBuffer.length());
        Log.e("delete", "getDkCourseList: " + MyApplication.WEB_SOCKET + delete);

        //连接websocket
        URI uri = URI.create(MyApplication.WEB_SOCKET + delete);
        client = new JWebSocketClient(uri) {
            @Override
            public void onMessage(String message) {
                //message就是接收到的消息
                Log.e("JWebSClientService", message);
            }
        };
        try {
            client.connectBlocking();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void finish() {
        super.finish();
        if (client != null) {
            client.close();
        }
    }

    private void initMusicNotification() {

    }


    @Override
    public int getLayoutId() {
        return R.layout.base_layout;
    }

    @Override
    public void onTotalDuration(int duration) {
    }

    @Override
    public void onPositionChanged(int position) {

    }

    @Override
    public void onStateChanged(int state) {

    }

    @Override
    public void onPlaybackCompleted() {
        for (int i = 0; i < data.getAudioList().size(); i++) {
            if (data.getAudioList().get(i).getPlayStatus() == 1 && i < data.getAudioList().size() - 1) {
                // mediaPlayerIngHolder.loadMedia(data.getAudioList().get(i + 1).getAudioUrl());
                finishSectionId = "";
                lastSectionId = "";
                loadingPopupView.show();
                mPresenter.geSectionDetail(data.getAudioList().get(i + 1).getSectionId());
                data.getAudioList().get(i).setPlayStatus(0);
                data.getAudioList().get(i + 1).setPlayStatus(1);
                break;
            }
        }
        ToastUtils.showShort(this, "onPlaybackCompleted: " + "播放完毕");
    }

    @Override
    public void onLoadPrepared() {
        mediaPlayerIngHolder.play();
        if (mediaPlayerIngHolder.isPlaying()) {
            iv_pause_start.setBackgroundResource(R.mipmap.out_continiu);
        } else {
            iv_pause_start.setBackgroundResource(R.mipmap.out_pause);
        }
    }

    public class MJavascriptInterface {
        @android.webkit.JavascriptInterface
        public void openImage(String img) {
            Log.e("img", "openImage: " + img);

            runOnUiThread(() -> PhotoViewer.INSTANCE
                    .setClickSingleImg(img, iv_big_img)
                    .setShowImageViewInterface((imageView, url) -> {
                        //使用Glide显示图片
                        Glide.with(XbSectionDetailActivity.this)
                                .load(img)
                                .into(imageView);
                    }).start(XbSectionDetailActivity.this));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        web_section.destroy();
    }
}