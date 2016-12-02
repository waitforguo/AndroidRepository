/**
 *
 */
package com.fausgoal.repository.utils;

import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;


/**
 * px to dp or dp to px
 * 手机尺寸、尺寸转换等工具类
 * Created by Fausgoal on 2015/5/5.
 */
public class GLPixelsUtil {
    private static final String TAG = "GLPixelsUtil";

    private static final Resources resources;
    private static final DisplayMetrics displayMetrics;

    static {
        resources = Resources.getSystem();
        displayMetrics = resources.getDisplayMetrics();
    }

    public static int getScreenWidth() {
        return displayMetrics.widthPixels;
    }

    public static int getScreenHeight() {
        return displayMetrics.heightPixels;
    }

    /**
     * 根据手机的分辨率从dp 的单位 转成为px(像素)
     *
     * @param dp dp
     * @return px
     */
    public static int dp2px(float dp) {
        final float scale = displayMetrics.density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从px(像素) 的单位 转成为dp
     *
     * @param px px
     * @return dp
     */
    public static int px2dp(float px) {
        final float scale = displayMetrics.density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param px px
     * @return sp
     */
    public static int px2sp(float px) {
        final float scale = displayMetrics.scaledDensity;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param sp sp
     * @return px
     */
    public static int sp2px(float sp) {
        final float scale = displayMetrics.scaledDensity;
        return (int) (sp * scale + 0.5f);
    }

    /**
     * 获取手机屏幕的分辨率
     *
     * @return Point x 宽度， y 高度
     */
    public static Point getDeviceSize() {
        Point point = new Point();
        point.x = getScreenWidth();
        point.y = getScreenHeight();
        return point;
    }
}
