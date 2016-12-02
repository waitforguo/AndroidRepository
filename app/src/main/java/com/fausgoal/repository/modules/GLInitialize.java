package com.fausgoal.repository.modules;

import android.content.Context;

import com.fausgoal.repository.common.GLConst;
import com.fausgoal.repository.utils.GLPushUtil;


/**
 * Description：初始化
 * <br/><br/>Created by Fausgoal on 16/9/22.
 * <br/><br/>
 */
public final class GLInitialize {
    private Context mContext = null;

    public void init(Context context) {
        mContext = context;

        initUmeng();
        initBugly();

        GLPushUtil.mClientIdNUmber = GLConst.NONE;

        //Notice 拦截异常报警回调
        GLCrashHandler crashHandler = GLCrashHandler.getInstance();
        crashHandler.init(context);
    }

    /**
     * Bugly SDK初始化
     */
    private void initBugly() {
//        /***** Bugly高级设置 *****/
//        BuglyStrategy strategy = new BuglyStrategy();
//        strategy.setAppChannel(BuildConfig.FLAVOR); // 设置app渠道号
//        strategy.setAppVersion(BuildConfig.VERSION_NAME); //App的版本
//        strategy.setAppPackageName(BuildConfig.APPLICATION_ID); //App的包名
//        strategy.setDeviceID(SystemUtil.getDeviceId(mContext));
//
//        /**
//         * 参数1：上下文对象
//         * 参数2：APPID，平台注册时得到,注意替换成你的appId
//         * 参数3：是否开启调试模式，调试模式下会输出'CrashReport'tag的日志
//         */
//        Bugly.init(mContext, BuildConfig.BUGLY_APPID, GLConst.IS_LOG, strategy);
    }

    /**
     * 友盟工具各分享平台初始化
     */
    private void initUmeng() {
//        MobclickAgent.setDebugMode(BuildConfig.IS_LOG);
//        MobclickAgent.setCatchUncaughtExceptions(true);
//
//        PlatformConfig.setWeixin(AppConstants.WX_APP_ID, AppConstants.WX_APP_SECRET);
//        PlatformConfig.setSinaWeibo(AppConstants.SINA_APP_ID, AppConstants.SINA_APP_SECRET);
//        PlatformConfig.setQQZone(AppConstants.QQ_APP_ID, AppConstants.QQ_APP_SECRET);
//
//        //关闭友盟分享时默认的Progress Dialog
//        Config.dialogSwitch = false;
    }
}
