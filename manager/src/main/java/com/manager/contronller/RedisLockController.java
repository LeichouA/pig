package com.manager.contronller;

import com.manager.config.redis.RedisCache;
import com.manager.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class RedisLockController {
    private static String product1Count = "product1Count";//商品1的数量key
    private static String lockKey = "testLockKey";//分布式锁的key

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 初始化设置商品数量
     *
     * @return
     */
    @RequestMapping("/setProductCount")
    public String setValue() {
        redisTemplate.opsForValue().set(product1Count, "100");
        User user = new User();
        User user2 = new User();
        user.setUserId(1);
        user.setEmail("sdfsdfsd");
        user.setUserName("jayce");

        user2.setUserId(2);
        user2.setEmail("2sdfsdfsd");
        user2.setUserName("jayce2");
        redisCache.setCacheObject("user",user);

        List<User> list= new ArrayList<>();
        list.add(user);
        list.add(user2);
        redisCache.setCacheList("Luser",list);
        redisCache.setCacheMapValue("Muser","user",user);

        Set<User> set = new HashSet<>();
        set.add(user);
        set.add(user2);

        redisCache.setCacheSet("SetUser",set);

        User user1 = redisCache.getCacheObject("user");
        log.info("getCacheObject:"+user1.toString());
        List<User> list1=redisCache.getCacheList("Luser");
        log.info("getCacheList:"+list1);

        User user3 = redisCache.getCacheMapValue("Muser","user");
        log.info(user3.toString());

        return "success";
    }

    /**
     * 模拟秒杀抢购，并发多个请求过来，查看是否出现超卖
     *
     * @return
     */
    @RequestMapping("/spike")
    public String spike() {
        String flag = "success";
        RLock lock = redissonClient.getLock(lockKey);
        try {
            //lock.lockAsync(5 , TimeUnit.SECONDS);
            lock.lock(5, TimeUnit.SECONDS); //设置60秒自动释放锁  （默认是30秒自动过期）
            Future<Boolean> res = lock.tryLockAsync(100, 5, TimeUnit.SECONDS);
            boolean result = res.get();
            System.out.println("result:" + result);
            if (result) {
                int stock = Integer.parseInt(redisTemplate.opsForValue().get(product1Count).toString());
                if (stock > 0) {
                    redisTemplate.opsForValue().set(product1Count, (stock - 1) + "");
                } else {
                    flag = "fail";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock(); //释放锁
        }
        return flag;
    }
}
