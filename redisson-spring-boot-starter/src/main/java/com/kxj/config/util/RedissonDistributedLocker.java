package com.kxj.config.util;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author xiangjin.kong
 * @date 2021/5/7 17:21
 */
@Component
@Slf4j
public class RedissonDistributedLocker implements DistributedLocker {

    @Autowired
    RedissonClient redissonClient;

    @Override
    public RLock lock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
        return lock;
    }

    @Override
    public RLock lock(String lockKey, int timeout) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, TimeUnit.SECONDS);
        return lock;
    }

    @Override
    public RLock lock(String lockKey, TimeUnit unit, int timeout) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, unit);
        return lock;
    }

    @Override
    public boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) throws Exception {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            boolean isSuccess = lock.tryLock(waitTime, leaseTime, unit);
            if (!isSuccess) {
                throw new Exception("系统繁忙，请稍后重试");
            }
            return isSuccess;
        } catch (InterruptedException e) {
            log.error("加锁失败：", e);
            throw new Exception("系统繁忙，请稍后重试");
        }
    }

    @Override
    public void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.unlock();
    }

    @Override
    public void unlock(RLock lock) {
        lock.unlock();
    }
}
