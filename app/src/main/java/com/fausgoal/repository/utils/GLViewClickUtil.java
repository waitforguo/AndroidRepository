package com.fausgoal.repository.utils;

import android.os.SystemClock;
import android.view.View;

import com.fausgoal.repository.common.GLConst;


/**
 * view 防止多次点击的工具类
 * Created by Fausgoal on 2015/5/5.
 */
public class GLViewClickUtil {

    public interface NoFastClickListener {
        void onNoFastClick(View v);
    }

    public static void setNoFastClickListener(View v, final NoFastClickListener listener) {
        if (null == v) {
            throw new NullPointerException("v is not null!");
        }
        if (null == listener)
            return;

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFastClick()) {
                    listener.onNoFastClick(v);
                }
            }
        });
    }

    private static long mLastClickTime;

    private static boolean isFastClick() {
        long time = SystemClock.uptimeMillis();
        long timeOut = time - mLastClickTime;
        if (GLConst.NONE < timeOut && timeOut < 500) {
            return true;
        }
        mLastClickTime = time;
        return false;
    }
}
