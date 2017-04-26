package com.fausgoal.repository.utils;

import com.fausgoal.repository.common.GLConst;

import java.util.Collection;
import java.util.Map;

/**
 * Description：List集合的工具类
 * <br/><br/>Created by Fausgoal on 15/9/5.
 * <br/><br/>
 */
public class GLListUtil {
    public static boolean isEmpty(Collection<?> list) {
        return list == null || list.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return null == map || map.isEmpty();
    }

    public static boolean isEmpty(Object[] array) {
        return null == array || array.length == GLConst.NONE;
    }

    public static int getSize(Collection<?> datas) {
        return (null == datas) ? GLConst.NONE : datas.size();
    }

    public static void clear(Collection<?> datas) {
        if (!isEmpty(datas)) {
            datas.clear();
        }
    }
}
