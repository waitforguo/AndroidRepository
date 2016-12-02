package com.fausgoal.repository.modules.logic;

import android.app.Activity;
import android.os.Build;
import android.os.Environment;
import android.view.Window;
import android.view.WindowManager;

import com.fausgoal.repository.R;
import com.fausgoal.repository.common.GLConst;
import com.fausgoal.repository.manager.SystemBarTintManager;
import com.fausgoal.repository.utils.GLLog;
import com.fausgoal.repository.utils.GLResourcesUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Description：控件状态栏透明度及颜色设置
 * <br/><br/>Created by Fausgoal on 16/1/12.
 * <br/><br/>
 */
public class GLStatusBarLogic {
    private static final String TAG = "GLStatusBarLogic";

    public static final String XIAO_MI = "Xiaomi";
    public static final String SAMSUNG = "samsung";
    public static final String SONY = "sony";
    public static final String MEI_ZU = "Meizu";

    private final Activity mActivity;

    private IStatusBarColorChangedListener mStatusBarColorChangedListener = null;

    public void setIStatusBarColorChangerListener(IStatusBarColorChangedListener statusBarColorChangedListener) {
        mStatusBarColorChangedListener = statusBarColorChangedListener;
    }

    public GLStatusBarLogic(Activity activity) {
        this(activity, null);
    }

    public GLStatusBarLogic(Activity activity, IStatusBarColorChangedListener statusBarColorChangedListener) {
        mActivity = activity;
        setIStatusBarColorChangerListener(statusBarColorChangedListener);
    }

    public interface IStatusBarColorChangedListener {
        void onStatusBarColorChanged(int colorId);
    }

    public void setStatusBar() {
        // 4.4以上透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 经测试在代码里直接声明透明状态栏更有效
            Window window = mActivity.getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明状态栏
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

            boolean isSetStatusBarTextColor = setMobileStatusBar();
            setSystemBarTint(isSetStatusBarTextColor, GLConst.NEGATIVE);
        }
    }

    public void setPopupStatusBar() {
        // 4.4以上透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 经测试在代码里直接声明透明状态栏更有效
            Window window = mActivity.getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明状态栏
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

            boolean isSetStatusBarTextColor = setMobileStatusBar();
            setSystemBarTint(isSetStatusBarTextColor, R.color.alpha_bg_black);
        }
    }

    /**
     * 设置状态栏的背景色
     *
     * @param isSetStatusBarTextColor 是否设置了状态栏的文字颜色，true 设置了，把整体背景改为白色，false 未设置，使用定义的红色
     */
    private void setSystemBarTint(boolean isSetStatusBarTextColor, int popupColorId) {
        // 创建状态栏的管理实例
        SystemBarTintManager tintManager = new SystemBarTintManager(mActivity);
        // 激活状态栏设置
        tintManager.setStatusBarTintEnabled(true);
//        // 激活导航栏设置
//        tintManager.setNavigationBarTintEnabled(true);
        // 设置一个颜色给系统栏
//        tintManager.setStatusBarTintColor(GLResourcesUtil.getColor(R.color.standard_white));
//        tintManager.setNavigationBarTintColor(GLResourcesUtil.getColor(R.color.navigationBarColor));
//        tintManager.setNavigationBarTintEnabled(false);
        // 有修改状态文字颜色的使用白色，其他用红色
        int colorId;
        if (isSetStatusBarTextColor) {
            colorId = R.color.white;
            tintManager.setStatusBarTintColor(GLResourcesUtil.getColor(colorId));
            if (null != mStatusBarColorChangedListener) {
                mStatusBarColorChangedListener.onStatusBarColorChanged(R.color.white);
            }
        } else {
            colorId = R.color.colorPrimaryDark;
            if (popupColorId != GLConst.NEGATIVE) {
                colorId = popupColorId;
            }
            tintManager.setStatusBarTintColor(GLResourcesUtil.getColor(colorId));
        }
    }

    private boolean setMobileStatusBar() {
        String strManufacturef = Build.MANUFACTURER;
        GLLog.trace(TAG, "机型：" + strManufacturef);
        if (XIAO_MI.equalsIgnoreCase(strManufacturef)) {
            return setStatusBarTextColor(1);
        } else if (MEI_ZU.equalsIgnoreCase(strManufacturef)) {
            return setMeizuDarkIcon();
        }
        return false;
    }

    /**
     * 设置Meizu
     */
    private boolean setMeizuDarkIcon() {
        boolean isSetStatusBarTextColor = false;
        try {
            WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
            Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            // 变黑色
            int newFlag = meizuFlags.getInt(lp) | 0x200;
            // 变白色
//            int newFlag = meizuFlags.getInt(lp) & ~0x200;
            meizuFlags.set(lp, newFlag);
            mActivity.getWindow().setAttributes(lp);
            isSetStatusBarTextColor = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSetStatusBarTextColor;
    }

    /**
     * 只支持MIUI V6+
     *
     * @param type 0--只需要状态栏透明 1-状态栏透明且黑色字体 2-清除黑色字体
     */
    private boolean setStatusBarTextColor(int type) {
        if (!isMiUIV6Plus()) {
            return false;
        }
        boolean isSetStatusBarTextColor = false;
        Window window = mActivity.getWindow();
        Class clazz = window.getClass();
        try {
            Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_TRANSPARENT");
            int tranceFlag = field.getInt(layoutParams);
            field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            if (type == 0) {
                extraFlagField.invoke(window, tranceFlag, tranceFlag);//只需要状态栏透明
            } else if (type == 1) {
                extraFlagField.invoke(window, tranceFlag | darkModeFlag, tranceFlag | darkModeFlag);//状态栏透明且黑色字体
            } else {
                extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
            }
            isSetStatusBarTextColor = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSetStatusBarTextColor;
    }

    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";

    private boolean isMiUIV6Plus() {
        try {
            final BuildProperties prop = BuildProperties.newInstance();
            String name = prop.getProperty(KEY_MIUI_VERSION_NAME, "");
//            return "V6".equals(name);
            // V6后可以改状态栏字体颜色
            if (!"V5".equals(name)
                    && !"V4".equals(name)
                    && !"V3".equals(name)
                    && !"V2".equals(name)
                    && !"V1".equals(name)) {
                return true;
            }
        } catch (final IOException e) {
            return false;
        }
        return false;
    }

    public static class BuildProperties {
        private final Properties properties;

        private BuildProperties() throws IOException {
            properties = new Properties();
            properties.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
        }

        public boolean containsKey(final Object key) {
            return properties.containsKey(key);
        }

        public boolean containsValue(final Object value) {
            return properties.containsValue(value);
        }

        public Set<Map.Entry<Object, Object>> entrySet() {
            return properties.entrySet();
        }

        public String getProperty(final String name) {
            return properties.getProperty(name);
        }

        public String getProperty(final String name, final String defaultValue) {
            return properties.getProperty(name, defaultValue);
        }

        public boolean isEmpty() {
            return properties.isEmpty();
        }

        public Enumeration<Object> keys() {
            return properties.keys();
        }

        public Set<Object> keySet() {
            return properties.keySet();
        }

        public int size() {
            return properties.size();
        }

        public Collection<Object> values() {
            return properties.values();
        }

        public static BuildProperties newInstance() throws IOException {
            return new BuildProperties();
        }
    }
}
