package com.fausgoal.repository.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.fausgoal.repository.R;
import com.fausgoal.repository.common.GLConst;
import com.fausgoal.repository.common.GLViewManager;
import com.fausgoal.repository.modules.GLApp;
import com.fausgoal.repository.modules.launch.GLLaunchActivity;
import com.fausgoal.repository.modules.logic.GLStatusBarLogic;
import com.fausgoal.repository.modules.logic.GLToolbarLogic;
import com.fausgoal.repository.utils.GLEventAnalysisUtil;
import com.fausgoal.repository.utils.GLLog;
import com.fausgoal.repository.utils.GLPixelsUtil;
import com.fausgoal.repository.utils.GLTextViewUtil;
import com.fausgoal.repository.utils.GLViewClickUtil;
import com.fausgoal.repository.widget.SwipeBackLayout;


/**
 * 可按照项目需求，将无冲突的通用函数，虚化到父类
 * <p/>
 * 想要实现向右滑动删除Activity效果只需要继承SwipeBackActivity即可，如果当前页面含有ViewPager
 * 只需要调用SwipeBackLayout的setViewPager()方法即可
 * <p/>
 * Created by Fausgoal on 2015/5/5.
 */
public abstract class GLParentActivity extends AppCompatActivity implements GLViewClickUtil.NoFastClickListener, GLStatusBarLogic.IStatusBarColorChangedListener {
    private static final String TAG = "GLParentActivity";

    public int mAppWidth = GLConst.NEGATIVE;
    public int mAppHeight = GLConst.NEGATIVE;
    public float mScaleDensity = GLConst.ONE;
    public Point mDeviceSizePoint = null;

    protected GLApp mApp = null;
    public Context mContext = null;
    protected LayoutInflater mInflater = null;
    protected InputMethodManager mIMM = null;

    private NotificationBroadcastReceiver mReceiver = null;
    private NotificationBroadcastReceiver mStickyReceiver = null;

    protected GLToolbarLogic mToolbarLogic = null;
    private int mStatusBarColor = GLConst.NONE;

    /**
     * Initiation of the data
     */
    protected abstract void initData();

    /**
     * Initiation of the components
     */
    protected abstract void initView();

    /**
     * 设置事件监听
     */
    protected void setListener() {
    }

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

    @Override
    public void onNoFastClick(View v) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        mApp = GLApp.getIns();
        mInflater = LayoutInflater.from(this);
        // 加到栈中
        GLViewManager.getIns().push(this);

        mDeviceSizePoint = GLPixelsUtil.getDeviceSize();
        mAppWidth = mDeviceSizePoint.x;

        int statebarHeight = GLPixelsUtil.dp2px(25);
        mAppHeight = mDeviceSizePoint.y - statebarHeight;

        mScaleDensity = getResources().getDisplayMetrics().density;

        mIMM = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        if (hasSwipeBackLayout()) {
            // 侧滑关闭
            SwipeBackLayout swipeBackLayout = new SwipeBackLayout(this);
            swipeBackLayout.attachToActivity(this);
        }

        if (hasToolbar()) {
            // 这句很关键，注意是调用父类的方法
            super.setContentView(R.layout.activity_base_layout);
        }

        String className = this.getClass().getName();
        // 这几个是要全屏显示的，不能设置透明状态栏
        if (!GLLaunchActivity.class.getName().equals(className)) {
            GLStatusBarLogic statusBarLogic = new GLStatusBarLogic(this, this);
            if (isPopupActivity()) {
                statusBarLogic.setPopupStatusBar();
            } else {
                statusBarLogic.setStatusBar();
            }
        }

        register(true);
        initData();
        initView();
        setListener();
    }

    @Override
    public void setContentView(@LayoutRes int layoutId) {
        setContentView(View.inflate(this, layoutId, null));
    }

    @Override
    public void setContentView(View view) {
        LinearLayout llRootLayout = findView(R.id.llRootLayout);
        if (hasToolbar() && null != llRootLayout) {
            View viewNavLine = findView(R.id.viewNavLine);
            if (null != viewNavLine) {
                boolean hasUnderLine = hasNavigationUnderLine();
                viewNavLine.setVisibility(hasUnderLine ? View.VISIBLE : View.GONE);
            }
            llRootLayout.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            initToolbar();
        } else {
            super.setContentView(view);
        }
    }

    @Override
    public void onStatusBarColorChanged(int colorId) {
        mStatusBarColor = colorId;
    }

    private void initToolbar() {
        if (hasToolbar()) {
            mToolbarLogic = new GLToolbarLogic(this);
            mToolbarLogic.setToolbarBgColor(mStatusBarColor);
        }
    }

    /**
     * 是否需要toolbar导航
     *
     * @return 默认是true，需要，在不需要的Activity中重写此方法返回false
     */
    protected boolean hasToolbar() {
        return true;
    }

    /**
     * 是否为Popup弹框
     *
     * @return true 为popup ，false正常的Activity(默认)
     */
    protected boolean isPopupActivity() {
        return false;
    }

    /**
     * 是否需要侧滑返回
     *
     * @return 默认是true，需要，在不需要的Activity中重写此方法返回false
     */
    protected boolean hasSwipeBackLayout() {
        return true;
    }

    protected boolean hasNavigationUnderLine() {
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        register(false);
        // 通用事务，不同处在子类实现
        GLEventAnalysisUtil.onResume(this);
    }

    @Override
    protected void onPause() {
        unregister(false);
        // 通用事务，不同处在子类实现
        super.onPause();
        GLEventAnalysisUtil.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        unregister(true);
        unregister(false);
        // 通用事务，不同处在子类实现
        GLViewManager.getIns().popWithoutFinish(this);
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // 通用事务，不同处在子类实现
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    protected <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }

    protected <T extends View> T findView(View parent, int id) {
        if (null == parent) {
            throw new NullPointerException("parent is not null!");
        }
        return (T) parent.findViewById(id);
    }

    protected boolean showInput(View view) {
        return mIMM.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    protected void showInputByEnter(final EditText editText) {
        GLTextViewUtil.setEditTextFocus(editText, false);
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showInput(editText);
            }
        }, 300);
    }

    protected boolean hideInput(View view) {
        return mIMM.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    protected boolean isInputActive() {
        return mIMM.isActive();
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
                unregisterReceiver(mStickyReceiver);
                mStickyReceiver = null;
            }
        } else {
            if (null != mReceiver) {
                unregisterReceiver(mReceiver);
                mReceiver = null;
            }
        }
    }

    private void addBroadcastReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        try {
            registerReceiver(receiver, filter);
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
