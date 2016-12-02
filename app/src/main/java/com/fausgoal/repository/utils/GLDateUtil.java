package com.fausgoal.repository.utils;

/**
 * Description：日期处理工具类
 * <br/><br/>Created by Fausgoal on 2016/12/2.
 * <br/><br/>
 */
public class GLDateUtil {
    /**
     * 1s
     */
    public static final int ONE_SECOND = 1000;
    /**
     * 1m
     */
    public static final int ONE_MINUTE = 60 * ONE_SECOND;
    public static final long FIVE_MINUTES = 5 * 60 * ONE_SECOND;
    /**
     * 半小时
     */
    public static final int HALF_HOUR = 30 * ONE_MINUTE;
    public static final long HOURS = 60 * ONE_MINUTE;
    public static final long DAY = 24 * HOURS;
    public static final long HOURS_72 = 72 * HOURS;

    private GLDateUtil() {
    }
}
