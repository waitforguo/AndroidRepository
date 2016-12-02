package com.fausgoal.repository.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Description：h5调用原生的相关处理
 * <br/><br/>Created by Fausgoal on 2016/12/2.
 * <br/><br/>
 */
public class GLBridgeUtils {
    /**
     * Demo： fausgoal://m.zhefengle.cn?bridge=detail&productId=825022
     * bridge=detail跳转到商品详情界面获取productId=825022的商品详情
     */

    private static final String BRIDGE_KEY = "bridge";

    private static final String BRIDGE_COPY = "copy";
    private static final String BRIDGE_DETAIL = "detail";

    private static final String BRIDGE_VALUE = "value";
    private static final String H5_DATA = "h5data";

    private GLBridgeUtils() {
    }

    public static boolean setBridge(Context context, Uri data, String tDataString) {
        tDataString = tDataString.trim();
        String bridgeKey = getQueryParam(data, BRIDGE_KEY);
        if (TextUtils.isEmpty(bridgeKey)) {
            return false;
        }

        /**
         * 复制内容到剪切板
         */
        if (BRIDGE_COPY.equals(bridgeKey)) {
            setBridgeCopy(context, data);
        } else if (BRIDGE_DETAIL.equals(bridgeKey)) {
            long productId = GLNumberUtils.stringToLong(getQueryParam(data, "productId"));
            // TODO: 2016/12/2  跳转到商品详情界面
        }
        // TODO: 2016/12/2  处理其他情况
        return true;
    }

    private static void setBridgeCopy(Context context, Uri data) {
        String value = getQueryParam(data, GLBridgeUtils.BRIDGE_VALUE);
        value = GLStringUtil.getURLDecoder(value);
        ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        manager.setPrimaryClip(ClipData.newPlainText(GLBridgeUtils.H5_DATA, value));
    }

    private static String getQueryParam(Uri data, String param) {
        return data.getQueryParameter(param);
    }
}
