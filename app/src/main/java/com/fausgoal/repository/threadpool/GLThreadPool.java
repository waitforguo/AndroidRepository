package com.fausgoal.repository.threadpool;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.fausgoal.repository.common.GLConst;
import com.fausgoal.repository.utils.GLLog;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * Description：线程池
 * <br/><br/>Created by Fausgoal on 2015/8/31.
 * <br/><br/>
 */
public class GLThreadPool {
    private static final String TAG = "GLThreadPool";

    private static final int CORE_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 8;
    private static final int KEEP_ALIVE_TIME = 10; // 10 seconds

    public interface Job<T> {
        T run();
    }

    public interface CancelListener {
        void onCancel();
    }

    private class GLMessageHandler<T> extends Handler {
        private final GLFuture<T> mFuture;
        private GLFutureListener<T> mListener;

        GLMessageHandler(Looper looper, GLFuture<T> future, GLFutureListener<T> listener) {
            super(looper);
            this.mFuture = future;
            this.mListener = listener;
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == GLConst.NONE) {
                if (null != mListener) {
                    mListener.onFutureDone(mFuture);
                }
            }
        }
    }

    private final Executor mExecutor;
    private final Looper mLooper;

    public GLThreadPool(Looper looper) {
        mLooper = looper;
        mExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME,
                TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(),
                new GLPriorityThreadFactory("provider-thread-pool", android.os.Process.THREAD_PRIORITY_DEFAULT));
    }

    public <T> GLFuture<T> submit(Job<T> job) {
        return submit(job, null);
    }

    public <T> GLFuture<T> submit(Job<T> job, GLFutureListener<T> listener) {
        ChatWorker<T> worker = new ChatWorker<T>(job, listener);
        mExecutor.execute(worker);
        return worker;
    }

    private class ChatWorker<T> implements Runnable, GLFuture<T> {
        private Job<T> mJob;
        private CancelListener mCancelListener;
        private GLMessageHandler<T> mHandler;

        private volatile boolean mIsCancelled;
        private boolean mIsDone;
        private T mResult;


        ChatWorker(Job<T> job, GLFutureListener<T> listener) {
            this.mJob = job;
            if (null != listener) {
                this.mHandler = new GLMessageHandler<>(mLooper, this, listener);
            }
        }

        @Override
        public void run() {
            T result = null;
            try {
                result = mJob.run();
            } catch (Throwable ex) {
                GLLog.trace(TAG, "Exception in running a job-->" + ex);
                ex.printStackTrace();
            }

            synchronized (this) {
                mResult = result;
                mIsDone = true;
                notifyAll();
            }
            if (null != this.mHandler) {
                this.mHandler.sendEmptyMessage(GLConst.NONE);
            }
        }

        // Below are the methods for Future.
        public synchronized void cancel() {
            if (mIsCancelled)
                return;
            mIsCancelled = true;
            if (null != mCancelListener) {
                mCancelListener.onCancel();
            }
        }

        public boolean isCancelled() {
            return mIsCancelled;
        }

        public synchronized boolean isDone() {
            return mIsDone;
        }

        public synchronized T get() {
            while (!mIsDone) {
                try {
                    wait();
                } catch (Exception ex) {
                    GLLog.trace(TAG, "ingore exception-->" + ex);
                    ex.printStackTrace();
                }
            }
            return mResult;
        }

        public void waitDone() {
            get();
        }

        // Below are the methods for JobContext (only called from the
        // thread running the job)
        public synchronized void setCancelListener(CancelListener listener) {
            mCancelListener = listener;
            if (mIsCancelled && null != mCancelListener) {
                mCancelListener.onCancel();
            }
        }
    }
}
