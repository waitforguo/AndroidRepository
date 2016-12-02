package com.fausgoal.repository.base;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Description：为了防止直接new Handler()造成内存泄露
 * <br/><br/>Created by Fausgoal on 16/6/18.
 * <br/><br/>
 * 参考地址：<a href="http://droidyue.com/blog/2014/12/28/in-android-handler-classes-should-be-static-or-leaks-might-occur/">Android中Handler引起的内存泄露</a>
 */
public class GLHandler<T> extends Handler {
    // 使用弱引用
    private final WeakReference<T> mReference;
    private final Callback mCallback;

    public GLHandler(T t, Callback callback) {
        mReference = new WeakReference<>(t);
        mCallback = callback;
    }

    @Override
    public void handleMessage(Message msg) {
        // 判断当前对象是否被销毁，没有被销毁时才处理
        T t = mReference.get();
        if (null != t && null != mCallback) {
            mCallback.handleMessage(msg);
        }
    }

    public interface Callback {
        void handleMessage(Message msg);
    }
}
