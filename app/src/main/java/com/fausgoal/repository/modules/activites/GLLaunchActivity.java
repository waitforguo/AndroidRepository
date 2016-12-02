package com.fausgoal.repository.modules.activites;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.fausgoal.repository.R;
import com.fausgoal.repository.base.GLParentActivity;
import com.fausgoal.repository.common.GLCommonVariables;
import com.fausgoal.repository.common.GLConst;
import com.fausgoal.repository.common.GLViewManager;
import com.fausgoal.repository.utils.GLDateUtil;
import com.fausgoal.repository.utils.GLPushUtil;
import com.fausgoal.repository.utils.GLResourcesUtil;
import com.fausgoal.repository.utils.GLSchemaUriUtils;
import com.fausgoal.repository.utils.GLViewClickUtil;


/**
 * Description：启动界面
 * <br/><br/>Created by Fausgoal on 2016/12/2.
 * <br/><br/>
 */
public class GLLaunchActivity extends GLParentActivity {

    private TextView tvSkip = null;

    private Handler mHandler = new Handler();

    private long mDelayed;
    private Handler mTimerHandler = new Handler();
    private String strSkipText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {
        strSkipText = GLResourcesUtil.getString(R.string.skip);
    }

    @Override
    protected void initView() {
        // 第一次启动,显示欢迎页
        boolean isFirstStart = (boolean) GLCommonVariables.getIns().readObject(GLCommonVariables.Keys.IS_FIRST_START, true);
        if (isFirstStart) {
            removeCallback();
            removeTimerCallback();
            GLViewManager.getIns().showActivity(mContext, GLGuideActivity.class, true,
                    R.anim.push_right_in, R.anim.push_right_out);
            return;
        }
        /**
         * 分发启动任务
         * 1、h5启动APP
         * 2、推送消息点击通知栏启动APP
         */
        else if (dispatchExtraContent()) {
            pop();
            return;
        }

        setContentView(R.layout.activity_launch_layout);
        tvSkip = findView(R.id.tvSkip);
        goToMainAct(3000, true);
    }

    @Override
    protected void setListener() {
        if (null != tvSkip) {
            GLViewClickUtil.setNoFastClickListener(tvSkip, this);
        }
    }

    /**
     * 分发启动任务
     */
    private boolean dispatchExtraContent() {
        //--------------------------Extra Start-------------------------------------//

        Intent intent = getIntent();
//        // 推送判断
//        boolean isPushNotifClick = isPushNotif(intent);
//        if (isPushNotifClick) {
//            String json = intent.getStringExtra(GLPushUtil.INTENT_JSON);
//            if (isSingleTask()) {
//                // 已经在应用内
//                GLPushUtil.isOpenPushContent = false;
//                // 直接打开
//                // TODO: 2016/12/2
////                PushDeal.newInstance(mContext, json);
//                return true;
//            } else {
//                // 不在应用内
//                GLPushUtil.isOpenPushContent = true;
//                GLPushUtil.mPushContent = json;
//            }
//        }

        // shcema跳转判断（网页）
        boolean isSchemaUri = GLSchemaUriUtils.catchSchemaUri(intent);
        if (isSchemaUri) {
            /**
             * 捕捉到指定事件，需要跳转
             * 如果launchMode 为 singleTask 之类的，则会进入onNewIntent, 里面记得要setIntent来刷新
             * 如果不是，则每次activity都是一个新的，跳转后要记得finish掉并出栈
             */
            if (isSingleTask()) {
                GLSchemaUriUtils.isOpenSchemaUri = false;
                // 如果内容未处理成功，直接重新打开MainActivity(launchMode="singleTask")
                return GLSchemaUriUtils.catchSchemaUriJump(mContext, intent);
            } else {
                GLSchemaUriUtils.isOpenSchemaUri = true;
            }
        }
        //--------------------------Extra End-------------------------------------//
        return false;
    }

    private boolean isPushNotif(Intent intent) {
        return intent.getBooleanExtra(GLPushUtil.PUSH_NOTIF_CLICK, false);
    }

    private boolean isSingleTask() {
        return mApp.isInMain || GLViewManager.getIns().getSizeNotWelAct() > GLConst.NONE;
    }

    private void pop() {
        GLViewManager.getIns().pop(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        goToMainDriect();
    }

    @Override
    public void onNoFastClick(View v) {
        super.onNoFastClick(v);
        switch (v.getId()) {
            case R.id.tvSkip:
                goToMainDriect();
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    private void removeCallback() {
        mHandler.removeCallbacks(mRunnable);
    }

    private void goToMainAct(long delayed, boolean isSkip) {
        if (null == mHandler) {
            mHandler = new Handler();
        }
        removeCallback();
        if (isSkip) {
            startTimer(delayed);
        } else {
            removeTimerCallback();
        }
        mHandler.postDelayed(mRunnable, delayed);
    }

    private void goToMainDriect() {
        removeCallback();
        removeTimerCallback();
        toMainAct();
    }

    private void toMainAct() {
        Intent intent = new Intent(mContext, GLMainActivity.class);
        Uri data = getIntent().getData();
        intent.setData(data);
        GLViewManager.getIns().showActivity(mContext, intent, true);
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    toMainAct();
                }
            });
        }
    };

    /**
     * ******************************************************************************************************************
     * ***********************************倒计时设置*********************************************************************
     * ******************************************************************************************************************
     */

    private void removeTimerCallback() {
        mTimerHandler.removeCallbacks(mTimerRunnable);
    }

    private void startTimer(long delayed) {
        removeTimerCallback();
        mDelayed = delayed / GLDateUtil.ONE_SECOND;
        if (mDelayed > GLConst.NONE) {
            setSkipText(mDelayed);
            tvSkip.setVisibility(View.VISIBLE);
            mTimerHandler.postDelayed(mTimerRunnable, GLDateUtil.ONE_SECOND);
        }
    }

    private Runnable mTimerRunnable = new Runnable() {
        @Override
        public void run() {
            mDelayed--;
            if (mDelayed >= GLConst.NONE) {
                setSkipText(mDelayed);
                mTimerHandler.postDelayed(this, GLDateUtil.ONE_SECOND);
            } else {
                removeTimerCallback();
            }
        }
    };

    private void setSkipText(long skipTimer) {
        String text = String.valueOf(skipTimer) + " " + strSkipText;
        tvSkip.setText(text);
    }

    /**
     * ******************************************************************************************************************
     * ***********************************倒计时设置End*******************************************************************
     * ******************************************************************************************************************
     */

    @Override
    protected void onDestroy() {
        removeCallback();
        removeTimerCallback();
        super.onDestroy();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(GLConst.NONE, R.anim.popupwindow_exit_alpha);
    }

    @Override
    protected boolean hasToolbar() {
        return false;
    }

    @Override
    protected boolean hasSwipeBackLayout() {
        return false;
    }
}
