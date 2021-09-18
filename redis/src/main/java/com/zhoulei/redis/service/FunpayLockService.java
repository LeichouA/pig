package com.zhoulei.redis.service;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

public interface FunpayLockService {

	RLock lock(String lockKey);

	RLock lock(String lockKey, long timeout);

	RLock lock(String lockKey, TimeUnit unit, long timeout);

	boolean getFairLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime);
	
	boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime);

	void unlock(String lockKey);

	void unlock(RLock lock);
}
