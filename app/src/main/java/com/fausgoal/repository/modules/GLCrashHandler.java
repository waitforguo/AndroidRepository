package com.fausgoal.repository.modules;

import android.content.Context;

import com.fausgoal.repository.common.GLConst;
import com.fausgoal.repository.common.GLViewManager;

/**
 * Description：APP异常拦截处理
 * <br/><br/>Created by Fausgoal on 16/6/4.
 * <br/><br/>
 */
public class GLCrashHandler implements Thread.UncaughtExceptionHandler {

    public static final String TAG = "GLCrashHandler";

    private static GLCrashHandler ins;
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private GLCrashHandler() {
    }

    public static GLCrashHandler getInstance() {
        if (null == ins) {
            synchronized (GLCrashHandler.class) {
                ins = new GLCrashHandler();
            }
        }
        return ins;
    }

    public void init(Context ctx) {
        mContext = ctx;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        ex.printStackTrace();
        if (!(GLConst.IS_DEBUG && GLConst.IS_LOG)) {
            if (null != mDefaultHandler) {
                mDefaultHandler.uncaughtException(thread, ex);
            } else {
                //clear activity stacks
                GLViewManager.getIns().popAllActivity();
                System.exit(1);
            }
        } else {
            //clear activity stacks
            GLViewManager.getIns().popAllActivity();
            System.exit(1);
        }
    }
}
