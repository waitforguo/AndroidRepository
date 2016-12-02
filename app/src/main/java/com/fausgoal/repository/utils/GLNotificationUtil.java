package com.fausgoal.repository.utils;

import android.app.Notification;
import android.content.Context;
import android.media.AudioManager;

import com.fausgoal.repository.common.GLCommonVariables;


/**
 * Description：通知设置，是否需要声音、振动
 * <br/><br/>Created by Fausgoal on 16/1/26.
 * <br/><br/>
 */
public class GLNotificationUtil {
    private static final String TAG = "GLNotificationUtil";

    private static GLNotificationUtil ins;

    public static GLNotificationUtil getIns(final Context context) {
        if (null == ins) {
            synchronized (GLNotificationUtil.class) {
                ins = new GLNotificationUtil(context);
            }
        }
        return ins;
    }

    /**
     * 振动的频率
     */
    public static final long VIBRATE[] = {0, 200, 100, 200};
    private final Context mContext;
    private AudioManager mAudioManager = null;

    private GLNotificationUtil(Context context) {
        mContext = context;
        mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
    }

    /**
     * 设置当前手机的声音、振动、静音状态
     *
     * @param notification notification
     */
    public void setCurrentNotification(Notification notification) {
        setAlarmParams(notification);
    }

    private void setAlarmParams(Notification notification) {
        ////获取系统设置的铃声模式
        switch (mAudioManager.getRingerMode()) {
            case AudioManager.RINGER_MODE_SILENT:
                //静音模式，值为0，这时候不震动，不响铃
                notification.sound = null;
                notification.vibrate = null;
                break;
            case AudioManager.RINGER_MODE_VIBRATE:
                //震动模式，值为1，这时候震动，不响铃
                notification.sound = null;
//                notification.defaults |= Notification.DEFAULT_VIBRATE;
                notification.vibrate = VIBRATE;
                break;
            case AudioManager.RINGER_MODE_NORMAL:
                //常规模式，分两种情况：1_响铃但不震动，2_响铃+震动
                //获取我们应用的设置
                boolean isVoice = isSoundNotifiy();
                boolean isVirbation = isVibrationNotifiy();
                // 既有铃声又有震动
                if (isVoice && isVirbation) {
                    notification.defaults |= Notification.DEFAULT_SOUND;
//                    notification.defaults |= Notification.DEFAULT_VIBRATE;
                    notification.vibrate = VIBRATE;
                } else {
                    /**
                     * 声音，不振动
                     */
                    if (isVoice) {
                        notification.defaults |= Notification.DEFAULT_SOUND;
                        notification.vibrate = null;
                    }
                    /**
                     * 没有声音，只振动
                     */
                    else if (isVirbation) {
                        notification.sound = null;
//                    notification.defaults |= Notification.DEFAULT_VIBRATE;
                        notification.vibrate = VIBRATE;
                    } else {
                        //其他形式，静音不管
                        GLLog.trace(TAG, "静音");
                    }
                }
                //都给开灯
                notification.flags |= Notification.FLAG_SHOW_LIGHTS;
                notification.defaults |= Notification.DEFAULT_LIGHTS;
                break;
            default:
                break;
        }
    }

    public static void setSoundNotifiy(boolean isNotifiy) {
        GLCommonVariables.getIns().saveObject(GLCommonVariables.Keys.SOUND_NOTIFY, isNotifiy);
    }

    public static void setVibrationNotifiy(boolean isNotifiy) {
        GLCommonVariables.getIns().saveObject(GLCommonVariables.Keys.VIBRATION_NOTIFY, isNotifiy);
    }

    public static boolean isSoundNotifiy() {
        return (boolean) GLCommonVariables.getIns().readObject(GLCommonVariables.Keys.SOUND_NOTIFY, true);
    }

    public static boolean isVibrationNotifiy() {
        return (boolean) GLCommonVariables.getIns().readObject(GLCommonVariables.Keys.VIBRATION_NOTIFY, true);
    }
}
