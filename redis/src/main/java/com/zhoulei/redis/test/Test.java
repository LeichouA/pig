package com.zhoulei.redis.test;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

/**
 * redisson 分布式锁 测试
 * @author dave
 *
 */
public class Test {

	public static void main(String[] args) {
		String[] nodes = "127.0.0.1:7000,127.0.0.1:7001,127.0.0.1:7002,127.0.0.1:7003,127.0.0.1:7004,127.0.0.1:7005"
				.split(",");
		// redisson版本是3.5，集群的ip前面要加上"redis://"
		for (int i = 0; i < nodes.length; i++) {
			nodes[i] = "redis://" + nodes[i];
		}
		Config config = new Config();
		config.useClusterServers() // 这是用的集群server
				.setScanInterval(60000) // 设置集群状态扫描时间
				.setMasterConnectionPoolSize(2000)// 设置连接数
				.setSlaveConnectionPoolSize(2000)
				// 同任何节点建立连接时的等待超时。时间单位是毫秒。默认：10000
				.setConnectTimeout(10000)
				// 当与某个节点的连接断开时，等待与其重新建立连接的时间间隔。时间单位是毫秒。默认:3000
				.setReconnectionTimeout(10000)
				// 等待节点回复命令的时间。该时间从命令发送成功时开始计时。默认:3000
				.setTimeout(10000)
				// 如果尝试达到 retryAttempts（命令失败重试次数）
				// 仍然不能将命令发送至某个指定的节点时，将抛出错误。如果尝试在此限制之内发送成功，则开始启用 timeout（命令等待超时）
				// 计时。默认值：3
				.setRetryAttempts(5)
				// 在一条命令发送失败以后，等待重试发送的时间间隔。时间单位是毫秒。 默认值：1500
				.addNodeAddress(nodes).setPassword("4#w1Q:;P>4D9tlb3vG~a");
		RedissonClient redisson = Redisson.create(config);
		String lockKey = "redisson_key";
		for (int i = 0; i < 300; i++) {
			final int b = i;
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					System.out.println(b);
					RLock lock = redisson.getFairLock(lockKey);// 加锁
					try {
						boolean isGetLock = lock.tryLock(180L, 180L, TimeUnit.SECONDS);
						if (isGetLock) {
							Thread.sleep(10000); // 获得锁之后可以进行相应的处理
							System.out.println("======获得锁后进行相应的操作======" + Thread.currentThread().getName() + "======线程:" + b);
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						lock.unlock();
					}
				}
			});
			t.start();
		}
		
		/*try {
			Thread.sleep(20000);
			redisson.shutdown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
	}
}
