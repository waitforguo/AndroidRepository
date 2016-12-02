package com.fausgoal.repository.utils;


import android.text.TextUtils;

import com.fausgoal.repository.common.GLConst;

import java.math.BigDecimal;


/**
 * Description：数据转换处理工具类
 * <br/><br/>Created by Fausgoal on 2015/5/12.
 * <br/><br/>
 */
public class GLNumberUtils {
    private GLNumberUtils() {
    }

    public static int floatToInt(float fl) {
        try {
            return (int) fl;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return GLConst.NONE;
    }

    public static double stringToDouble(String str) {
        if (TextUtils.isEmpty(str))
            return GLConst.NONE;
        try {
            return Double.valueOf(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return GLConst.NONE;
    }

    public static long stringToLong(String str) {
        if (TextUtils.isEmpty(str))
            return GLConst.NONE;
        try {
            return Long.valueOf(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return GLConst.NONE;
    }

    public static int stringToInt(String str) {
        if (TextUtils.isEmpty(str))
            return GLConst.NONE;
        try {
            return Integer.valueOf(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return GLConst.NONE;
    }

    public static float doubleToFloat(double dl) {
        dl = get2PointDouble(dl);
        return (float) dl;
    }

    /**
     * 将1.0 转为1，1.1 保留1.1
     */
    public static String getFloatValue(float fl) {
        if (fl % 1.0f == GLConst.NONE) {
            return String.valueOf((long) fl);
        }
        return String.valueOf(fl);
    }

    /**
     * 将1.0 转为1，1.1 保留1.1
     */
    public static String getDoubleValue(double d) {
        if (d % 1.0d == GLConst.NONE) {
            return String.valueOf((long) d);
        }
        return String.valueOf(d);
    }

    public static double get1PointDouble(double d) {
        BigDecimal bigDecimal = new BigDecimal(d);
        return bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double get2PointDouble(double d) {
        BigDecimal bigDecimal = new BigDecimal(d);
        return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double get1PointDouble(BigDecimal bigDecimal) {
        if (null == bigDecimal) {
            bigDecimal = new BigDecimal(GLConst.NONE);
        }
        return bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double get2PointDouble(BigDecimal bigDecimal) {
        if (null == bigDecimal) {
            bigDecimal = new BigDecimal(GLConst.NONE);
        }
        return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 根据显示的数据条数，及数据总数计算分页多少页
     *
     * @param loadDataCount  每页显示数据的条数
     * @param totalDataCount 数据总数
     * @return 分页多少页
     */
    public static long getTotalPageCount(long loadDataCount, long totalDataCount) {
        if (loadDataCount <= GLConst.NONE) {
            loadDataCount = GLConst.ONE;
        }

        long totalPageCount = totalDataCount / loadDataCount;

        if (totalDataCount % loadDataCount != GLConst.NONE) {
            totalPageCount += GLConst.ONE;
        }

        if (totalPageCount <= GLConst.NONE) {
            totalPageCount = GLConst.ONE;
        }
        return totalPageCount;
    }

    public static int findMax(int[] positions) {
        int max = Integer.MIN_VALUE;
        for (int value : positions) {
            if (value > max)
                max = value;
        }
        return max;
    }

    public static int findMin(int[] positions) {
        int min = Integer.MAX_VALUE;
        for (int value : positions) {
            if (value != GLConst.NEGATIVE && value < min)
                min = value;
        }
        return min;
    }
}
