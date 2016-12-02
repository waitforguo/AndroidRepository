package com.fausgoal.repository.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.fausgoal.repository.R;
import com.fausgoal.repository.common.GLConst;
import com.fausgoal.repository.modules.GLApp;
import com.fausgoal.repository.modules.activites.GLLaunchActivity;

import org.json.JSONException;

/**
 * Description：推送处理工具类
 * <br/><br/>Created by Fausgoal on 16/1/13.
 * <br/><br/>
 */
public class GLPushUtil {
    private static final String TAG = "GLPushUtil";

    /**
     * 标识是否打开推送过来的内容
     */
    public static boolean isOpenPushContent = false;
    /**
     * 标识推送的内容
     */
    public static String mPushContent = null;

    public static final String PUSH_NOTIF_CLICK = "push_notif_click";
    public static final String INTENT_JSON = "intentJson";
    /**
     * 记录正确设置cid的次数
     */
    public static int mClientIdNUmber = GLConst.NONE;

    private static NotificationManager mNotificationManager = null;
    /**
     * 有的可能要设置id
     */
    public final static int NOTIFICATION_ID = 520;

    private static Context mContext = null;

    private GLPushUtil() {
    }

    public static void onPush(Context context, String strJson) throws JSONException {
//        JSONObject jsonObject = new JSONObject(strJson);
//        if (jsonObject.has("title") && jsonObject.has("body")) {
//            String json = jsonObject.toString();
//            GLLog.trace(TAG, "推送json-->" + json);
//
//            if (GLAppLifeCycle.isInApp()) {
////                //如果在应用使用时收到广播，则弹出对话框
////                Intent intent = new Intent(context, DialogActivity.class);
////                intent.putExtra(GLPushUtil.INTENT_JSON, json);
////                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                context.startActivity(intent);
//            } else {
//                // APP没启动时，在通知栏显示
//                String title = jsonObject.getString("title");
//                String body = jsonObject.getString("body");
//                GLPushUtil.createMessageNotification(title, body, json);
//            }
//        }
        // TODO: 2016/12/2
        // APP没启动时，在通知栏显示
        String title = "收到一条推送";
        String body = "推送内容";

        GLPushUtil.createMessageNotification(title, body, strJson);
    }

    private static void initNotificationManager() {
        if (null == mContext) {
            mContext = GLApp.getIns().getApplicationContext();
        }
        if (null == mNotificationManager) {
            mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        }
    }

    public static void createMessageNotification(String title, String body, String json) throws JSONException {
        initNotificationManager();

        //通知之后点击跳转用到的Intent
        Intent intent = new Intent(mContext, GLLaunchActivity.class); // 点击通知栏跳转页面
        intent.putExtra(PUSH_NOTIF_CLICK, true);
        intent.putExtra(INTENT_JSON, json);
        //一定要Intent.FLAG_ACTIVITY_NEW_TASK
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);//Activity启动方式

        //PendingIntent 是Intent的包装类
        PendingIntent contentIntent = PendingIntent.getActivity(mContext, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder ncb = new NotificationCompat.Builder(mContext);
        ncb.setTicker(GLResourcesUtil.getString(R.string.app_name)); // 设置在statusBar上显示的提示文字
        ncb.setAutoCancel(true); // 被点击后取消掉通知提醒
        ncb.setContentIntent(contentIntent);
        ncb.setDefaults(Notification.DEFAULT_LIGHTS); // 设置通知的声音，震动，LED
        ncb.setContentTitle(title);
        ncb.setContentText(body);
        ncb.setSmallIcon(R.mipmap.ic_launcher);
        Notification notification = ncb.build();

        // 去调用手机系统及应用怎么设置的通知状态，是否有声音、振动or静音了
        GLNotificationUtil.getIns(mContext).setCurrentNotification(notification);
        mNotificationManager.notify(NOTIFICATION_ID, notification); // 第一个参数是用来标识
    }

    /**
     * 清除通知
     */
    public static void clearNotification() {
        initNotificationManager();
        mNotificationManager.cancel(NOTIFICATION_ID);
    }
}
