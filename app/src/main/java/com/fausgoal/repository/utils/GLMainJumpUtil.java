package com.fausgoal.repository.utils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

/**
 * Description：主界面跳转处理
 * <br/><br/>Created by Fausgoal on 16/6/17.
 * <br/><br/>
 */
public class GLMainJumpUtil {
    private GLMainJumpUtil() {
    }

    /**
     * 打开主界面处理的自动跳转
     */
    public static void toJump(final Context context, final Intent intent) {
        /**
         * bridge跳转
         */
        if (GLSchemaUriUtils.isOpenSchemaUri) {
            GLSchemaUriUtils.isOpenSchemaUri = false;
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    GLSchemaUriUtils.catchSchemaUri(context, intent);
                }
            }, 1500);
        }
        /**
         * push
         */
        else if (GLPushUtil.isOpenPushContent) {
            GLPushUtil.isOpenPushContent = false;
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    String json = GLPushUtil.mPushContent;
                    if (!TextUtils.isEmpty(json)) {
                        // TODO: 2016/12/2  处理推送的结果
//                        PushDeal.newInstance(context, json);
                        GLPushUtil.mPushContent = null;
                    }
                }
            }, 1500);
        }
    }
}
