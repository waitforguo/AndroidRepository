package com.fausgoal.repository.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.fausgoal.repository.utils.GLLog;
import com.fausgoal.repository.utils.GLViewClickUtil;


/**
 * Description：可按照项目需求，将无冲突的通用函数，虚化到父类
 * <br/><br/>Created by Fausgoal on 15-11-27.
 * <br/><br/>
 */
public abstract class GLParentFragment extends Fragment implements GLViewClickUtil.NoFastClickListener {
    private static final String TAG = "GLParentFragment";

    protected Context mContext = null;

    private NotificationBroadcastReceiver mReceiver = null;//广播通知接收
    private NotificationBroadcastReceiver mStickyReceiver = null;

    /**
     * Initiation of the data
     */
    protected abstract void initData();

    /**
     * Initiation of the components
     */
    protected abstract void initView();

    /**
     * notification broadcast
     *
     * @param context 上下文
     * @param intent  通知内容
     */
    protected void onNotify(Context context, Intent intent) {
    }

    /**
     * init broadcast intent filter
     *
     * @return IntentFilter
     */
    protected IntentFilter getIntentFilter() {
        return null;
    }

    /**
     * 常驻广播通知回调
     *
     * @param context 上下文
     * @param intent  通知内容
     */
    protected void onStickyNotify(Context context, Intent intent) {
    }

    /**
     * 初始化常驻广播过滤器
     *
     * @return IntentFilter
     */
    protected IntentFilter getStickyIntentFilter() {
        return null;
    }

    protected void setListener() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();

        register(true);
        initData();
        initView();
        setListener();
    }

    protected <T extends View> T findView(View parent, int id) {
        if (null == parent) {
            throw new NullPointerException("parent is not null!");
        }
        return (T) parent.findViewById(id);
    }

    @Override
    public void onResume() {
        super.onResume();
        register(false);
        // 通用事务，不同处在子类实现
    }

    @Override
    public void onPause() {
        unregister(false);
        // 通用事务，不同处在子类实现
        super.onPause();
    }

    @Override
    public void onDestroy() {
        unregister(true);
        unregister(false);
        // 通用事务，不同处在子类实现
        super.onDestroy();
    }

    @Override
    public void onNoFastClick(View v) {
    }

    /**********************************************************************/
    /******************************broadcast receiver**********************/
    /**********************************************************************/

    /**
     * 消除常驻广播接收器
     */
    protected void detachStickyBroadcastReceiver() {
        unregister(true);
    }

    /**
     * 添加注册广播
     *
     * @param isSticky 是否常驻的广播
     */
    private void register(boolean isSticky) {
        IntentFilter filter;
        if (isSticky) {
            if (null == mStickyReceiver) {
                filter = getStickyIntentFilter();
                if (null != filter) {
                    mStickyReceiver = new NotificationBroadcastReceiver(true);
                    addBroadcastReceiver(mStickyReceiver, filter);
                }
            }
        } else {
            if (null == mReceiver) {
                filter = getIntentFilter();
                if (null != filter) {
                    mReceiver = new NotificationBroadcastReceiver(false);
                    addBroadcastReceiver(mReceiver, filter);
                }
            }
        }
    }

    /**
     * 注销广播
     *
     * @param isSticky 是否为常驻广播
     */
    private void unregister(boolean isSticky) {
        if (isSticky) {
            if (null != mStickyReceiver) {
                getActivity().unregisterReceiver(mStickyReceiver);
                mStickyReceiver = null;
            }
        } else {
            if (null != mReceiver) {
                getActivity().unregisterReceiver(mReceiver);
                mReceiver = null;
            }
        }
    }

    private void addBroadcastReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        try {
            getActivity().registerReceiver(receiver, filter);
        } catch (Exception e) {
            GLLog.trace(TAG, "register exception " + e.getMessage());
        }
    }

    private class NotificationBroadcastReceiver extends BroadcastReceiver {
        private boolean isSticky = false;

        public NotificationBroadcastReceiver(boolean isSticky) {
            this.isSticky = isSticky;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (isSticky) {
                onStickyNotify(context, intent);
            } else {
                onNotify(context, intent);
            }
        }
    }
}
