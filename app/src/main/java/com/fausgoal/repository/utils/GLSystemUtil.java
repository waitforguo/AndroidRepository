package com.fausgoal.repository.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * Description：获取系统相关信息的工具类
 * <br/><br/>Created by Fausgoal on 2016/12/2.
 * <br/><br/>
 */
public class GLSystemUtil {
    private GLSystemUtil() {
    }

    /**
     * 操作系统版本
     *
     * @return 固件版本
     */
    public static String getDeviceVersion() {
        // 固件版本
        return Build.VERSION.RELEASE;
    }

    /**
     * 手机生产厂家及型号
     *
     * @return 手机型号
     */
    public static String getDeviceModel() {
        return Build.MODEL;
    }

    /**
     * 生产厂家
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;// 生产厂家
    }

    /**
     * 获取设备唯一标示
     */
    public static String getDeviceId(Context context) {
        try {
            // 这里要加异常处理，如果是在6.0(包括6.0)之后的系统在没获取到权限之前调用会报异常
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取运营商
     */
    public static String getCarrier(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String strSimOperator = tm.getSimOperator();

        String strCarrier = null;
        if (!TextUtils.isEmpty(strSimOperator)) {
            switch (strSimOperator) {
                case "46000":
                case "46002":
                case "46007":
                case "46008":
                    strCarrier = "中国移动";
                    break;
                case "46001":
                case "46006":
                case "46009":
                    strCarrier = "中国联通";
                    break;
                case "46003":
                case "46005":
                case "46011":
                    strCarrier = "中国电信";
                    break;
                default:
                    strCarrier = "其他";
                    break;
            }
        }
        return strCarrier;
    }

    /**
     * 获取用户的网络类型(2/3/4G、wifi)
     */
    public static String getNetworkType(Context context) {
        String strNetworkType = null;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (null != networkInfo && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                strNetworkType = "WIFI";
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                String strSubTypeName = networkInfo.getSubtypeName();
                // TD-SCDMA   networkType is 17
                int networkType = networkInfo.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                        strNetworkType = "2G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                        strNetworkType = "3G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                        strNetworkType = "4G";
                        break;
                    default:
                        // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                        if (strSubTypeName.equalsIgnoreCase("TD-SCDMA")
                                || strSubTypeName.equalsIgnoreCase("WCDMA")
                                || strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                            strNetworkType = "3G";
                        } else {
                            strNetworkType = strSubTypeName;
                        }
                        break;
                }
            }
        }
        return strNetworkType;
    }
}
