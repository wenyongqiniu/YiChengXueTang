package com.example.yichengxuetang.activitys.messageactivitys;


import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yichengxuetang.R;
import com.example.yichengxuetang.adapter.ConversationAdapter;
import com.example.yichengxuetang.bean.GroupInfo;
import com.example.yichengxuetang.bean.GroupNoticeBean;
import com.example.yichengxuetang.bean.WallPaperResponse;
import com.example.yichengxuetang.contract.MainContract;
import com.llw.mvplibrary.mvp.MvpActivity;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;

import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;
import io.rong.message.TextMessage;


public class PrivateConvesationActivity extends MvpActivity<MainContract.MainPresenter> implements MainContract.IMainView {

    private String myContent = "";
    private Message message;

    @Override
    protected MainContract.MainPresenter createPresenter() {
        return new MainContract.MainPresenter();
    }

    @Override
    public void getWallPaper(WallPaperResponse wallPaperResponse) {

    }

    @Override
    public void getGroupInfo(GroupInfo groupInfo) {

    }

    @Override
    public void getGroupNotice(GroupNoticeBean groupInfo) {

    }

    @Override
    public void getFailed(Throwable e) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        RecyclerView rv_conversation = findViewById(R.id.rv_conversation);
        MaterialEditText ed_input = findViewById(R.id.ed_input);
        TextView tv_send_message = findViewById(R.id.tv_send_message);

        UserInfo u = new UserInfo("123456", "java大神",
                Uri.parse("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fup.enterdesk.com%2Fedpic_source%2Fc7%2F8a%2F03%2Fc78a030abf9940543004b4fea7ef3902.jpeg&refer=http%3A%2F%2Fup.enterdesk.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1651039631&t=010add2bebbe3b2d1601e3a808fe67c3"));

        ArrayList<Conversation> conversationsList = new ArrayList<>();
        ConversationAdapter conversationAdapter = new ConversationAdapter(R.layout.item_conversation, conversationsList);
        rv_conversation.setLayoutManager(new LinearLayoutManager(this));
        rv_conversation.setAdapter(conversationAdapter);

        RongIMClient.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageWrapperListener() {
            @Override
            public boolean onReceived(Message message, int i, boolean b, boolean b1) {
                if (message != null) {
                    runOnUiThread(() -> {
                        MessageContent content = message.getContent();
                        Conversation conversation = new Conversation();
                        conversation.setPortraitUrl("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fup.enterdesk.com%2Fedpic_source%2Fc7%2F8a%2F03%2Fc78a030abf9940543004b4fea7ef3902.jpeg&refer=http%3A%2F%2Fup.enterdesk.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1651039631&t=010add2bebbe3b2d1601e3a808fe67c3");
                        conversation.setSenderUserName(u.getName());
                        conversation.setLatestMessage(content);
                        conversation.setSenderUserId(message.getSenderUserId());
                        conversationsList.add(conversation);
                        conversationAdapter.notifyDataSetChanged();
                    });
                }

                return true;
            }
        });
        ed_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                myContent = s.toString();
                TextMessage messageContent = TextMessage.obtain(myContent);
                Conversation.ConversationType conversationType = Conversation.ConversationType.PRIVATE;
                message = Message.obtain("123456", conversationType, messageContent);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        tv_send_message.setOnClickListener(v -> RongIMClient.getInstance().sendMessage(message, null, null, new IRongCallback.ISendMessageCallback() {
            @Override
            public void onAttached(Message message1) {

            }

            @Override
            public void onSuccess(Message message1) {
                MessageContent content = message1.getContent();
                Conversation conversation = new Conversation();
                conversation.setPortraitUrl("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fup.enterdesk.com%2Fedpic_source%2Fc7%2F8a%2F03%2Fc78a030abf9940543004b4fea7ef3902.jpeg&refer=http%3A%2F%2Fup.enterdesk.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1651039631&t=010add2bebbe3b2d1601e3a808fe67c3");
                conversation.setSenderUserName(u.getName());
                conversation.setLatestMessage(content);
                conversation.setSenderUserId("1234567");
                conversationsList.add(conversation);
                conversationAdapter.notifyDataSetChanged();

            }

            @Override
            public void onError(Message message1, RongIMClient.ErrorCode errorCode) {

            }
        }));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_private_convesation;
    }
}