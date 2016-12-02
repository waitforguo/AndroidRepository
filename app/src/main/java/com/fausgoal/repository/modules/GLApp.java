package com.fausgoal.repository.modules;

import android.app.Application;
import android.content.Context;

import com.antfortune.freeline.FreelineCore;
import com.fausgoal.repository.threadpool.GLThreadPool;

/**
 * Description：Application
 * <br/><br/>Created by Fausgoal on 2016/12/2.
 * <br/><br/>
 */
public class GLApp extends Application {
    /**
     * 是否在应用里面
     */
    public boolean isInMain = false;

    private GLThreadPool mThreadPool = null;

    private static GLApp ins;

    public static GLApp getIns() {
        return ins;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ins = this;
        registerActivityLifecycleCallbacks(new GLAppLifeCycle());

        // init freeline
        FreelineCore.init(this, this);
        // 初始化数据
        GLInitialize initialize = new GLInitialize();
        initialize.init(getApplicationContext());
    }

    public GLThreadPool getThreadPool() {
        if (null == mThreadPool) {
            synchronized (GLThreadPool.class) {
                mThreadPool = new GLThreadPool(getMainLooper());
            }
        }
        return mThreadPool;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
