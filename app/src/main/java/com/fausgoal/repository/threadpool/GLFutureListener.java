package com.fausgoal.repository.threadpool;


/**
 * Description：
 * <br/><br/>Created by Fausgoal on 2015/8/31.
 * <br/><br/>
 */
public interface GLFutureListener<T> {
    void onFutureDone(GLFuture<T> future);
}
