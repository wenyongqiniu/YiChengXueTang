package com.example.yichengxuetang.fragments;


import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.example.yichengxuetang.R;
import com.example.yichengxuetang.bean.WallPaperResponse;
import com.example.yichengxuetang.contract.MainContract;
import com.llw.mvplibrary.mvp.MvpFragment;

import io.rong.imkit.RongIM;
import io.rong.imkit.config.ConversationListBehaviorListener;
import io.rong.imkit.config.RongConfigCenter;
import io.rong.imkit.conversationlist.ConversationListFragment;
import io.rong.imkit.conversationlist.model.BaseUiConversation;
import io.rong.imkit.userinfo.UserDataProvider;
import io.rong.imkit.utils.RouteUtils;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;


/**
 * A simple {@link Fragment} subclass.
 */
public class RYMessageFragment extends Fragment{

    public RYMessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_rymessage, container, false);
        initView(inflate);
        return inflate;
    }



    private void initView(View inflate) {

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fr_contanier_ry, new ConversationListFragment()).commit();



        RongConfigCenter.conversationListConfig().setBehaviorListener(new ConversationListBehaviorListener() {
            @Override
            public boolean onConversationPortraitClick(Context context, Conversation.ConversationType conversationType, String s) {
                return false;
            }

            @Override
            public boolean onConversationPortraitLongClick(Context context, Conversation.ConversationType conversationType, String s) {
                return false;
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public boolean onConversationLongClick(Context context, View view, BaseUiConversation baseUiConversation) {
                return false;
            }

            @Override
            public boolean onConversationClick(Context context, View view, BaseUiConversation baseUiConversation) {
                String name = baseUiConversation.mCore.getConversationType().getName();
                String targetId = baseUiConversation.mCore.getTargetId();
                String conversationTitle = baseUiConversation.mCore.getConversationTitle();
                if (name.equals("group")) {//这里代表是群聊
                    // RouteUtils.registerActivity(RouteUtils.RongActivityType.ConversationActivity, GroupConversationActivity.class);//需要注册在此activity
                    Conversation.ConversationType conversationType = Conversation.ConversationType.GROUP;
                    Bundle bundle = new Bundle();
                    if (!TextUtils.isEmpty(conversationTitle)) {
                        bundle.putString(RouteUtils.TITLE, conversationTitle); //会话页面标题
                    }
                    RouteUtils.routeToConversationActivity(context, conversationType, targetId, bundle);
                } else {
                    // RouteUtils.registerActivity(RouteUtils.RongActivityType.ConversationActivity, PersonalConversationActivity.class);
                    Conversation.ConversationType conversationType = Conversation.ConversationType.PRIVATE;
                    Bundle bundle = new Bundle();
                    if (!TextUtils.isEmpty(conversationTitle)) {
                        bundle.putString(RouteUtils.TITLE, conversationTitle); //会话页面标题
                    }
                    RouteUtils.routeToConversationActivity(context, conversationType, targetId, bundle);
                }

                RongIM.setGroupInfoProvider(new UserDataProvider.GroupInfoProvider() {
                    /**
                     * 群信息回调
                     * @param groupId 群组 ID
                     * @return 群组信息
                     */
                    @Override
                    public Group getGroupInfo(String groupId) {
                        // 异步请求逻辑.
                       // mGroupId = groupId;
                        //GetRyTokenPresenter.getInstance().getGroupInfo(groupId);
                        return null;
                    }
                }, true);

                return true;
            }
        });
    }
}
