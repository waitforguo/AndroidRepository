package com.fausgoal.repository.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.fausgoal.repository.modules.main.GLMainActivity;
import com.fausgoal.repository.modules.launch.GLLaunchActivity;
import com.fausgoal.repository.utils.GLLog;

import java.util.Stack;


/**
 * Description：class to manage all views/activities
 * <br/><br/>Created by Fausgoal on 2015/4/28.
 * <br/><br/>
 */
public final class GLViewManager {
    private static final String TAG = "GLViewManager";

    /**
     * no intent specify tags
     */
    public static final int NO_FLAGS = GLConst.NEGATIVE;

    private Stack<Activity> mActivityStack = null;

    private static GLViewManager ins = null;

    public static GLViewManager getIns() {
        if (null == ins) {
            synchronized (GLViewManager.class) {
                ins = new GLViewManager();
            }
        }
        return ins;
    }

    private GLViewManager() {
        mActivityStack = new Stack<>();
    }

    public int getSize() {
        return mActivityStack.size();
    }

    public boolean isExist(String className) {
        for (Activity activity : mActivityStack) {
            if (className.equals(activity.getComponentName().getClassName()))
                return true;
        }
        return false;
    }

    public Activity getActivity(String className) {
        for (Activity activity : mActivityStack) {
            if (className.equals(activity.getComponentName().getClassName()))
                return activity;
        }
        return null;
    }

    /**
     * 通过index获得栈内的activity
     *
     * @param index index
     * @return Activity
     */
    public Activity getActivityAtIndex(int index) {
        if (null == mActivityStack) {
            return null;
        }

        int size = mActivityStack.size();
        if (index < GLConst.NONE || index >= size) {
            return null;
        }
        return mActivityStack.get(index);
    }

    /**
     * 获取栈内第一个Activity
     *
     * @return Activity
     */
    public Activity getFristActivity() {
        if (null == mActivityStack) {
            return null;
        }
        return getActivityAtIndex(GLConst.NONE);
    }

    /**
     * 获取栈内最后一个Activity
     *
     * @return Activity
     */
    public Activity getLastActivity() {
        if (null == mActivityStack) {
            return null;
        }
        int size = mActivityStack.size();
        return getActivityAtIndex(size - GLConst.ONE);
    }

    /**
     * 获取栈中的数量，排除 GLLaunchActivity 的数量
     */
    public int getSizeNotWelAct() {
        int count = GLConst.NONE;
        if (null != mActivityStack) {
            int size = mActivityStack.size();
            count = size;
            for (int i = size - GLConst.ONE; i >= GLConst.NONE; i--) {
                Activity act = mActivityStack.get(i);
                if (act instanceof GLLaunchActivity) {
                    // 如果是 GLLaunchActivity 数量减1
                    count -= GLConst.ONE;
                }
            }
        }
        return count;
    }

    /**
     * push target activity info the stack
     *
     * @param activity activity
     */
    public void push(Activity activity) {
        if (null == mActivityStack)
            return;
        mActivityStack.push(activity);
    }

    /**
     * pop up the current activity from stack
     */
    public void pop() {
        Activity currentActivity = mActivityStack.pop();
        if (null != currentActivity) {
            pop(currentActivity);
        }
    }

    /**
     * pop up the current activity from stack
     *
     * @param activity activity
     */
    public void pop(Activity activity) {
        if (null == activity)
            return;
        popWithoutFinish(activity);
        activity.finish();
//        activity.overridePendingTransition(R.anim.gl_no_anim, R.anim.gl_slide_out_to_right);
    }

    public void pop(Class<?> cls) {
        if (null == cls) {
            return;
        }

        if (null != mActivityStack) {
            Activity act;
            int size = mActivityStack.size();
            for (int i = 0; i < size; i++) {
                act = mActivityStack.get(i);
                if (act.getClass().getName().equals(cls.getName())) {
                    pop(act);
                    break;
                }
            }
        }
    }

    public void pop(Activity activity, int enterAnim, int exitAnim) {
        pop(activity);
        activity.overridePendingTransition(enterAnim, exitAnim);
    }

    /**
     * pop up the current activity from stack without finish
     */
    public void popWithoutFinish(Activity activity) {
        if (null == activity) {
            GLLog.trace(TAG, "popWithoutFinish activity is null");
            return;
        }
        mActivityStack.remove(activity);
    }

    /**
     * 指定这个activity不关闭，其他都关闭
     *
     * @param activity 指定不关闭的Activity
     */
    public synchronized void popWithoutFinishThisActivity(Activity activity) {
        if (null != mActivityStack) {
            int size = mActivityStack.size();
            for (int i = size - GLConst.ONE; i >= GLConst.NONE; i--) {
                Activity act = mActivityStack.get(i);

                if (null != activity && activity.equals(act)) {
                    continue;
                }
                pop(act);
            }
        }
    }

    /**
     * pop all activities
     */
    public synchronized void popAllActivity() {
        if (null != mActivityStack) {
            int size = mActivityStack.size();
            for (int i = GLConst.NONE; i < size; i++) {
                mActivityStack.get(i).finish();
            }
            mActivityStack.clear();
        }
    }

    public synchronized void popToMainActivity() {
        if (null != mActivityStack) {
            int size = mActivityStack.size();
            for (int i = size - GLConst.ONE; i >= GLConst.NONE; i--) {
                Activity act = mActivityStack.get(i);
                if (!(act instanceof GLMainActivity)) {
                    pop(act);
                }
            }
        }
    }

    public void showActivity(Context context, Class<?> c, boolean isFinish) {
        showActivity(context, c, isFinish, getDefEnterAnim(), getDefExitAnim());
    }

    public void showActivity(Context context, Class<?> c, boolean isFinish, int enterAnim, int exitAnim) {
        Intent intent = new Intent(context, c);
        showActivity(context, intent, isFinish, enterAnim, exitAnim);
    }

    public void showActivity(Context context, Intent intent, boolean isFinish) {
        showActivity(context, intent, isFinish, getDefEnterAnim(), getDefExitAnim());
    }

    public void showActivity(Context context, Intent intent, boolean isFinish, int enterAnim, int exitAnim) {
//        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        context.startActivity(intent);
        if (context instanceof Activity) {
            ((Activity) context).overridePendingTransition(enterAnim, exitAnim);
        }
        if (isFinish) {
            pop(((Activity) context));
        }
    }

    public void showActivity(Context context, Intent intent, int requestCode) {
        showActivity(context, intent, requestCode, getDefEnterAnim(), getDefExitAnim());
    }

    public void showActivity(Context context, Intent intent, int requestCode, int enterAnim, int exitAnim) {
//        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        if (context instanceof Activity) {
            ((Activity) context).startActivityForResult(intent, requestCode);
            ((Activity) context).overridePendingTransition(enterAnim, exitAnim);
        } else {
            showActivity(context, intent, false, enterAnim, exitAnim);
        }
    }

    private int getDefEnterAnim() {
//        return R.anim.gl_slide_in_from_right;
        return 0;
    }

    private int getDefExitAnim() {
//        return R.anim.gl_slide_out_to_left;
        return 0;
    }
}
