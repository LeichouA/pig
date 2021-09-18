package com.zhoulei.redis.config;


import com.zhoulei.redis.util.RedisProperties;
import com.zhoulei.redis.util.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author: dave
 * @Description: 配置项
 * @Date: 2018/6/14 16:28
 */
@Configuration
public class JedisClusterConfig {

	@Autowired
	private RedisProperties redisProperties;

	@Bean
	public JedisCluster getJedisCluster() {
		String[] serverArray = redisProperties.getClusterNodes().split(",");
		Set<HostAndPort> nodes = new HashSet<>();

		for (String ipPort : serverArray) {
			if (StringUtils.isEmpty(ipPort)) {
				continue;
			}
			String[] ipPortPair = ipPort.split(":");
			nodes.add(new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim())));
		}

		JedisCluster jedisCluster = null;
		if (!nodes.isEmpty()) {
			JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
			jedisPoolConfig.setMaxIdle(redisProperties.getMaxIdle());
			jedisPoolConfig.setMaxWaitMillis(redisProperties.getMaxWait());
			jedisPoolConfig.setMaxTotal(redisProperties.getMaxActive());
			jedisPoolConfig.setMinIdle(redisProperties.getMinIdle());
			jedisPoolConfig.setTestOnBorrow(true);
			jedisCluster = new JedisCluster(nodes, redisProperties.getConnectionTimeout(),
					redisProperties.getExpireSeconds(), redisProperties.getMaxAttempts(),
					redisProperties.getPassword().trim(), jedisPoolConfig);
		}
		return jedisCluster;
	}

//	@Bean
//	public RedissonClient getRedisson() {
//		String[] nodes = redisProperties.getClusterNodes().split(",");
//		// redisson版本是3.5，集群的ip前面要加上"redis://"
//		for (int i = 0; i < nodes.length; i++) {
//			nodes[i] = "redis://" + nodes[i];
//		}
//		RedissonClient redisson = null;
//		Config config = new Config();
//		config.useClusterServers() // 这是用的集群server
//				.setScanInterval(6000)// 设置集群状态扫描时间
//				.setMasterConnectionPoolSize(2000)// 设置连接数
//				.setSlaveConnectionPoolSize(2000)
//				// 同任何节点建立连接时的等待超时。时间单位是毫秒。默认：10000
//				.setConnectTimeout(redisProperties.getConnectionTimeout())
//				// 当与某个节点的连接断开时，等待与其重新建立连接的时间间隔。时间单位是毫秒。默认:3000
//				.setReconnectionTimeout(10000)
//				// 等待节点回复命令的时间。该时间从命令发送成功时开始计时。默认:3000
//				.setTimeout(10000)
//				// 如果尝试达到 retryAttempts（命令失败重试次数）
//				// 仍然不能将命令发送至某个指定的节点时，将抛出错误。如果尝试在此限制之内发送成功，则开始启用 timeout（命令等待超时）
//				// 计时。默认值：3
//				.setRetryAttempts(5)
//				// 在一条命令发送失败以后，等待重试发送的时间间隔。时间单位是毫秒。 默认值：1500
//				.setRetryInterval(3000).addNodeAddress(nodes).setPassword(redisProperties.getPassword().trim());
//		redisson = Redisson.create(config);
//		return redisson;
//	}

}