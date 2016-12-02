package com.fausgoal.repository.utils;


import com.fausgoal.repository.common.GLConst;

/**
 * 打日志的工具类
 *
 * @author Fausgoal
 * @date 2015/4/28.
 */
public class GLLog {
    /**
     * GLLog Tag
     */
    private static final String TAG = "Fausgoal";

    /**
     * debug mode
     */
    private static boolean bDebug = true;

    /**
     * Debug trace
     */
    public static void trace(String TAG, String content) {
        if (isLoggable() && bDebug) {
            print(TAG, content, null);
        }
    }

    /**
     * Debug trace
     */
    public static void trace(String TAG, String content, Throwable tr) {
        if (isLoggable() && bDebug) {
            print(TAG, content, tr);
        }
    }

    /**
     * exception print
     */
    public static void trace(String TAG, String content, boolean force) {
        if (isLoggable() && (force || bDebug)) {
            print(TAG, content, null);
        }
    }

    private synchronized static void print(String TAG, String content, Throwable tr) {
        // 处理系统显示单条Log信息的长度是固定的，为4*1024个字符长度
        if (content.length() > 4000) {
            int index = GLConst.NONE;
            for (int i = GLConst.NONE; i < content.length(); i += 4000) {
                String tag = TAG + " " + (index + GLConst.ONE);
                if (i + 4000 < content.length()) {
                    println(tag, content.substring(i, i + 4000), null);
                } else {
                    println(tag, content.substring(i, content.length()), tr);
                }
                index++;
            }
        } else {
            println(TAG, content, tr);
        }
    }

    private static void println(String TAG, String content, Throwable tr) {
        if (null == tr) {
            android.util.Log.d(GLLog.TAG, TAG + " : " + content);
        } else {
            android.util.Log.d(GLLog.TAG, TAG + " : " + content, tr);
        }
    }

    /**
     * @return true 才会打日志，如果是debug模式下，是一直打日志的
     */
    private static boolean isLoggable() {
        if (GLConst.IS_LOG) {
            // 一直打印日志
            return true;
        }
        // 在命令行中设置之后可打印日志
        return android.util.Log.isLoggable(TAG, android.util.Log.DEBUG);
    }
}
