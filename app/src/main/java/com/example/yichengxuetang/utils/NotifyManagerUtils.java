package com.example.yichengxuetang.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.yichengxuetang.R;
import com.example.yichengxuetang.activitys.MainActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * @auth: njb
 * @date: 2021/7/28 10:54
 * @desc: 手机通知设置工具类
 */
public class NotifyManagerUtils {
    static long[] vibrate = {100, 200, 300, 400, 500, 400, 300, 200, 400};

    /**
     * 打开通知权限
     *
     * @param context
     */
    public static void openNotificationSettingsForApp(Context context) {
        // Links to this app's notification settings.
        Intent intent = new Intent();
        intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
        intent.putExtra("app_package", context.getPackageName());
        intent.putExtra("app_uid", context.getApplicationInfo().uid);
        // for Android 8 and above
        intent.putExtra("android.provider.extra.APP_PACKAGE", context.getPackageName());
        context.startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void notificationActions(Context context, String content, String title) {
       /* RemoteViews view = new RemoteViews(context.getPackageName(), R.layout.lock_screen);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);

        mBuilder.setSmallIcon(R.mipmap.logo);
        mBuilder.setTicker("");

        Notification notification = mBuilder.build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notification.flags |= Notification.FLAG_NO_CLEAR;
        notification.priority = Notification.PRIORITY_MAX;
        notification.bigContentView = view;
        notificationManager.notify(1, notification);*/


       /* NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.lock_screen);
        remoteViews.setTextViewText(R.id.tv_title, title);
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.xb);
        builder.setColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setContent(remoteViews);
        builder.setWhen(System.currentTimeMillis());
        builder.setAutoCancel(true);
        builder.setOngoing(true);
        builder.setContentIntent(pendingIntent);
        // 用户可以看到的通知渠道的名字.
        CharSequence name = "notification channel";
        // 用户可以看到的通知渠道的描述
        NotificationChannel mChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel("1", name, NotificationManager.IMPORTANCE_HIGH);
            // 配置通知渠道的属性
            mChannel.setDescription(content);
            // 设置通知出现时的闪灯（如果 android 设备支持的话）
            mChannel.enableLights(true);
            // 设置不可手动清除，除非app死掉或者在代码中取消
            mChannel.setLightColor(Color.RED);
            // 设置通知出现时的震动（如果 android 设备支持的话）
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(vibrate);
            //最后在notificationmanager中创建该通知渠道
            notificationManager.createNotificationChannel(mChannel);
        }
        notificationManager.notify(1, builder.build());*/
        Notification notification = null;
        NotificationManager manager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String id = "channelId";
            String name = "channelName";
            NotificationChannel channel = new NotificationChannel(id,name,NotificationManager.IMPORTANCE_LOW);
            manager.createNotificationChannel(channel);
            notification = new Notification.Builder(context)
                    .setChannelId(id)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.logo)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.logo))
                    .build();
        }else{
            notification = new NotificationCompat.Builder(context)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.logo)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.logo))
                    .build();
        }
        manager.notify(1,notification);
    }
}
