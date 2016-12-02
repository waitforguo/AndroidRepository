package com.fausgoal.repository.threadpool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description：
 * <br/><br/>Created by Fausgoal on 2015/5/26.
 * <br/><br/>
 */
public class GLPriorityThreadFactory implements ThreadFactory {
    private final int mPriority;
    private final AtomicInteger mNumber = new AtomicInteger();
    private final String mName;

    public GLPriorityThreadFactory(String name, int priority) {
        mName = name;
        mPriority = priority;
    }

    public Thread newThread(Runnable r) {
        return new Thread(r, mName + '-' + mNumber.getAndIncrement()) {
            @Override
            public void run() {
                android.os.Process.setThreadPriority(mPriority);
                super.run();
            }
        };
    }
}
