package com.kxj.config.util;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * @author xiangjin.kong
 * @date 2021/5/7 17:20
 * @desc 分布式锁工具类接口
 */
public interface DistributedLocker {

    /**
     * 对某个key加锁
     * @param lockKey
     * @return
     */
    RLock lock(String lockKey);

    /**
     * 加锁 设置时间
     * @param lockKey
     * @param timeout
     * @return
     */
    RLock lock(String lockKey, int timeout);

    /**
     * 加锁 设置时间 单位
     * @param lockKey
     * @param unit
     * @param timeout
     * @return
     */
    RLock lock(String lockKey, TimeUnit unit, int timeout);

    /**
     * 尝试加锁 失败则等待重新尝试
     * @param lockKey
     * @param unit
     * @param waitTime
     * @param leaseTime
     * @return
     */
    boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) throws Exception;

    /**
     * 解锁
     * @param lockKey
     * @return
     */
    void unlock(String lockKey);

    /**
     * 解锁
     * @param lock
     */
    void unlock(RLock lock);
}
