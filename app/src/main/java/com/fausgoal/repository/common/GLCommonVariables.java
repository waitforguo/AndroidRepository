package com.fausgoal.repository.common;

import android.app.Activity;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.fausgoal.repository.modules.GLApp;
import com.fausgoal.repository.utils.GLBase64;
import com.fausgoal.repository.utils.GLLog;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * 简易写入preference操作，存放相关字段和默认值
 * <br/><br/>Created by Fausgoal on 2015/5/8.
 * <br/><br/>
 */
public class GLCommonVariables {
    private static final String TAG = "GLCommonVariables";

    public static class Keys {
        /**
         * 是否第一次启动APP
         */
        public static final String IS_FIRST_START = "isFirstStart";
        /**
         * 声音提示
         */
        public static final String SOUND_NOTIFY = "soundNotify";
        /**
         * 振动提示
         */
        public static final String VIBRATION_NOTIFY = "vibrationNotify";
    }

    private static final String PREF_FILE = "com.fausgoal.repository_preferences";

    private static GLCommonVariables ins = null;
    private SharedPreferences mSharedPreferences = null;

    public static GLCommonVariables getIns() {
        if (null == ins) {
            synchronized (GLCommonVariables.class) {
                ins = new GLCommonVariables();
            }
        }
        return ins;
    }

    private GLCommonVariables() {
        reset(getSharedPreferences());
    }

    private SharedPreferences getSharedPreferences() {
        return GLApp.getIns().getSharedPreferences(PREF_FILE, Activity.MODE_PRIVATE);
    }

    private void reset(SharedPreferences sharedPreferences) {
        if (null == sharedPreferences) {
            GLLog.trace(TAG, "sharedPreferences is null");
            return;
        }
        mSharedPreferences = sharedPreferences;
    }

    /**
     * String key, int defValue
     *
     * @param key   key
     * @param value value
     */
    private void putString(String key, String value) {
        mSharedPreferences.edit().putString(key, value).apply();
    }

    /**
     * Retrieve a String value from the preferences
     *
     * @param key      key
     * @param defValue defValue
     * @return value
     */
    private String getString(String key, String defValue) {
        return mSharedPreferences.getString(key, defValue);
    }

    public void saveObject(String key, Object object) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        try {
            bos = new ByteArrayOutputStream();
            // 创建对象输出流，并封装字节流
            oos = new ObjectOutputStream(bos);
            // 将对象写入字节流
            oos.writeObject(object);

            // 将字节流编码成base64的字符窜
            byte[] bytes = bos.toByteArray();
            String value = GLBase64.encode(bytes);
            putString(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null)
                    bos.close();
                if (oos != null)
                    oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Object readObject(String key, Object o) {
        if (TextUtils.isEmpty(key)) {
            return o;
        }
        Object object = null;

        ByteArrayInputStream bis = null;
        ObjectInputStream ois;
        try {
            String value = getString(key, "");
            byte[] bytes = GLBase64.decode(value);

            if (bytes.length > 0) {
                //封装到字节流
                bis = new ByteArrayInputStream(bytes);
                //再次封装
                ois = new ObjectInputStream(bis);
                //读取对象
                object = ois.readObject();
            } else {
                object = o;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null)
                    bis.close();
                if (bis != null)
                    bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return object;
    }

    public Object readObject(String key) {
        return readObject(key, null);
    }

    public void clear() {
        mSharedPreferences.edit().clear().apply();
    }
}
