package com.fausgoal.repository.modules;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.fausgoal.repository.modules.activites.GLGuideActivity;
import com.fausgoal.repository.modules.activites.GLLaunchActivity;


/**
 * Description：应用生命周期
 * <br/><br/>Created by Fausgoal on 2016/12/2.
 * <br/><br/>
 */
public class GLAppLifeCycle implements Application.ActivityLifecycleCallbacks {

    private static boolean isInApp = false;

    public static boolean isInApp() {
        return isInApp;
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        String className = activity.getClass().getName();
        // 启动页与引导页不标识
        if (GLGuideActivity.class.getName().equals(className)
                || GLLaunchActivity.class.getName().equals(className)) {
            isInApp = false;
        } else {
            isInApp = true;
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        isInApp = false;
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
