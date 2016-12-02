package com.fausgoal.repository.utils;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.fausgoal.repository.modules.GLApp;


/**
 * Description：资源文件工具类
 * <br/><br/>Created by Fausgoal on 2015/8/4.
 * <br/><br/>
 */
public class GLResourcesUtil {
    private GLResourcesUtil() {
    }

    public static String getString(int id) {
        return GLApp.getIns().getApplicationContext().getResources().getString(id);
    }

    public static int getColor(int id) {
        return GLApp.getIns().getApplicationContext().getResources().getColor(id);
    }

    public static Drawable getDrawable(int id) {
        return GLApp.getIns().getApplicationContext().getResources().getDrawable(id);
    }

    public static <T extends View> T findView(View parent, int id) {
        if (null == parent) {
            throw new NullPointerException("parent is not null!");
        }
        return (T) parent.findViewById(id);
    }
}
