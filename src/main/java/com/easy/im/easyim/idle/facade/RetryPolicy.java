package com.easy.im.easyim.idle.facade;

/**
 * @author by yuanpeng.song
 * @ClassName RetryPolicy
 * @Description 重试策略接口
 * @Date 2021/11/18 0018 22:21
 **/
public interface RetryPolicy {

    /**
     * called when an operation has failed for some reason
     * this method should return true to make another attempt
     *
     * @param retryCount
     * @return
     */
    boolean allowRetry(int retryCount);

    /**
     * get sleep time in ms of current retry count
     *
     * @param retryCount
     * @return
     */
    long getSleepTimeMs(int retryCount);
}
