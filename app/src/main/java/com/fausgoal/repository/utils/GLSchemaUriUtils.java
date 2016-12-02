package com.fausgoal.repository.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;


/**
 * Description：h5调用原生
 * <br/><br/>Created by Fausgoal on 16/4/18.
 * <br/><br/>
 */
public class GLSchemaUriUtils {
    private static final String TAG = "GLSchemaUriUtils";

    /**
     * 标识是否需要在主界面自动跳转
     */
    public static boolean isOpenSchemaUri = false;

    // scheme
    private static final String SCHEME_FAUSGOAL = "fausgoal";
    // http及https是通过APP links跳转的
    private static final String SCHEME_HTTP = "http";
    private static final String SCHEME_HTTPS = "https";

    private GLSchemaUriUtils() {
    }

    /**
     * 只判断不跳转
     */
    public static boolean catchSchemaUri(Intent intent) {
        if (null == intent) {
            return false;
        }
        // 取得Schema，action等
        String tSchema = intent.getScheme();
        String tAction = intent.getAction();
        String tDataString = intent.getDataString();
        String tType = intent.getType();

        if (null != tAction) {
            GLLog.trace(TAG, "action: " + tAction);
        }
        if (null != tDataString) {
            GLLog.trace(TAG, "DataString: " + tDataString);
        }
        if (null != tType) {
            GLLog.trace(TAG, "Type: " + tType);
        }

        if (null == tSchema) {
            return false;
        }

        GLLog.trace(TAG, tSchema);

        // 取得URL
        Uri dataURI = intent.getData();
        return isSchemaUri(tSchema) && null != dataURI;
    }

    public static boolean catchSchemaUri(Context context, Intent intent) {
        if (null == intent) {
            return false;
        }
        // 取得Schema，action等
        String tSchema = intent.getScheme();
        String tAction = intent.getAction();
        String tDataString = intent.getDataString();
        String tType = intent.getType();

        if (null != tAction) {
            GLLog.trace(TAG, "action: " + tAction);
        }
        if (null != tDataString) {
            GLLog.trace(TAG, "DataString: " + tDataString);
        }
        if (null != tType) {
            GLLog.trace(TAG, "Type: " + tType);
        }

        if (null == tSchema) {
            return false;
        }

        GLLog.trace(TAG, tSchema);

        // 取得URL
        Uri dataURI = intent.getData();
        // 取得URL中的查询参数
        return isSchemaUri(tSchema) && null != dataURI && GLBridgeUtils.setBridge(context, dataURI, tDataString);
    }

    public static boolean catchSchemaUriJump(Context context, Intent intent) {
        if (null == intent) {
            return false;
        }
        // 取得Schema，action等
        String tDataString = intent.getDataString();

        // 取得URL
        Uri dataURI = intent.getData();
        if (null != dataURI) {
            GLLog.trace(TAG, " path：" + dataURI.getPath() + " dataURI：" + dataURI + "DataString: " + tDataString);
            // 取得URL中的查询参数
            return GLBridgeUtils.setBridge(context, dataURI, tDataString);
        }
        return false;
    }

    /**
     * 是否为定义的SchemaUri
     */
    private static boolean isSchemaUri(String tSchema) {
        return tSchema.equals(SCHEME_FAUSGOAL)
                || tSchema.equals(SCHEME_HTTP)
                || tSchema.equals(SCHEME_HTTPS);
    }
}
