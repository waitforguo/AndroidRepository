package com.fausgoal.repository.utils;

import android.content.Context;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Description：事件统计
 * <br/><br/>Created by Fausgoal on 2015/8/31.
 * <br/><br/>
 */
public class GLEventAnalysisUtil {

    private GLEventAnalysisUtil() {
    }

    public static void onResume(Context context) {
//        MobclickAgent.onResume(context);
    }

    public static void onPause(Context context) {
//        MobclickAgent.onPause(context);
    }

    public static void onEvent(Context context, String eventId) {
//        MobclickAgent.onEvent(context, eventId);
    }

    public static void onEvent(Context ctx, String eventId, Map<String, String> values) {
        if (null == values) {
            values = new HashMap<>();
        }
//        MobclickAgent.onEvent(ctx, eventId, values);
    }

    /**
     * 统计点击事件
     *
     * @param eventId 事件名
     * @param key     事件对应的key
     * @param value   事件对应的value
     */
    public static void makeBuriedPoint(Context context, String eventId, String key, String value) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put(key, value);
        GLEventAnalysisUtil.onEvent(context, eventId, map);
    }
}
