package com.zhoulei.redis.util;

import redis.clients.jedis.JedisCluster;

import java.util.Random;

/**
 * redis 同步锁机制
 * 
 * @author dave
 * @date 2019-5-25
 *
 */
public class RedisLock {

	private JedisCluster jedisCluster;

	private static final int DEFAULT_ACQUIRY_RESOLUTION_MILLIS = 100;

	/**
	 * Lock key path.
	 */
	private String lockKey;

	/**
	 * 锁超时时间，防止线程在入锁以后，无限的执行等待
	 */
	private int expireMsecs = 60 * 1000;

	/**
	 * 锁等待时间，防止线程饥饿
	 */
	private int timeoutMsecs = 10 * 1000;

	private volatile boolean locked = false;

	/**
	 * Detailed constructor with default acquire timeout 10000 msecs and lock
	 * expiration of 60000 msecs.
	 *
	 * @param lockKey
	 *            lock key (ex. account:1, ...)
	 */
	public RedisLock(JedisCluster jedisCluster, String lockKey) {
		this.jedisCluster = jedisCluster;
		this.lockKey = lockKey + "_lock";
	}

	/**
	 * Detailed constructor with default lock expiration of 60000 msecs.
	 *
	 */
	public RedisLock(JedisCluster jedisCluster, String lockKey, int timeoutMsecs) {
		this(jedisCluster, lockKey);
		this.timeoutMsecs = timeoutMsecs;
	}

	/**
	 * Detailed constructor.
	 *
	 */
	public RedisLock(JedisCluster jedisCluster, String lockKey, int timeoutMsecs, int expireMsecs) {
		this(jedisCluster, lockKey, timeoutMsecs);
		this.expireMsecs = expireMsecs;
	}

	/**
	 * @return lock key
	 */
	public String getLockKey() {
		return lockKey;
	}

	private String get(final String key) {
		return jedisCluster.get(key);
	}

	private boolean setNX(final String key, final String value) {
		Long setnx = jedisCluster.setnx(key, value);
		return setnx == 1 ? true : false;
	}

	private String getSet(final String key, final String value) {
		return jedisCluster.getSet(key, value);
	}

	/**
	 * 获得 lock. 实现思路: 主要是使用了redis 的setnx命令,缓存了锁. reids缓存的key是锁的key,所有的共享,
	 * value是锁的到期时间(注意:这里把过期时间放在value了,没有时间上设置其超时时间) 执行过程:
	 * 1.通过setnx尝试设置某个key的值,(当前没有这个锁)则返回成功,获得锁
	 * 2.锁已经存在则获取锁的到期时间,和当前时间比较,超时的话,则设置新的值
	 *
	 * @return true if lock is acquired, false acquire timeouted
	 * @throws InterruptedException
	 *             in case of thread interruption
	 */
	public synchronized boolean lock() throws InterruptedException {
		int timeout = timeoutMsecs;
		while (timeout >= 0) {
			long expires = System.currentTimeMillis() + expireMsecs + 1;
			String expiresStr = String.valueOf(expires); // 锁到期时间
			if (this.setNX(lockKey, expiresStr)) {
				// lock acquired
				locked = true;
				return true;
			}
			String currentValueStr = this.get(lockKey); // redis里的时间
			if (null != currentValueStr && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
				// 判断是否为空，不为空的情况下，如果被其他线程设置了值，则第二个条件判断是过不去的
				// lock is expired

				String oldValueStr = this.getSet(lockKey, expiresStr);
				// 获取上一个锁到期时间，并设置现在的锁到期时间，
				// 只有一个线程才能获取上一个线上的设置时间，因为jedis.getSet是同步的
				if (null != oldValueStr && oldValueStr.equals(currentValueStr)) {
					// 防止误删（覆盖，因为key是相同的）了他人的锁——这里达不到效果，这里值会被覆盖，但是因为什么相差了很少的时间，所以可以接受
					// [分布式的情况下]:如过这个时候，多个线程恰好都到了这里，但是只有一个线程的设置值和当前值相同，他才有权利获取锁
					// lock acquired
					locked = true;
					return true;
				}
			}
			timeout -= DEFAULT_ACQUIRY_RESOLUTION_MILLIS;
			// 随机等待 5-10秒 防止夺锁
			Thread.sleep(new Random().nextInt(5000) + 5000);
		}
		return false;
	}

	/**
	 * Acqurired lock release.
	 */
	public synchronized void unlock() {
		if (locked) {
			jedisCluster.del(lockKey);
			locked = false;
		}
	}

}
