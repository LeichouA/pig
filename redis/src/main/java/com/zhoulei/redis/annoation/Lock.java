//package com.zhoulei.redis.annoation;
//
//import com.funpay.common.redis.enums.LockModel;
//
//import java.lang.annotation.*;
//
///**
// * redis分布式锁注解
// *
// * @author dave
// * @date 2020-5-21 16:07:07
// *
// */
//@Retention(RetentionPolicy.RUNTIME)
//@Target({ ElementType.METHOD, ElementType.TYPE })
//@Documented
//public @interface Lock {
//
//	/**
//	 * 固定前缀
//	 * @return
//	 */
//	String keyPrefix() default "";
//
//	/**
//	 * 要锁定的keys中包含的属性
//	 */
//	String[] keys() default "";
//
//	LockModel lockModel() default LockModel.AUTO;
//
//	/**
//	 * 锁超时时间,默认180L秒(可在配置文件全局设置)
//	 */
//	long expireTime() default 180L;
//
//	/**
//	 * 超时时间 等待加锁超时时间,默认180L秒 -1 则表示一直等待(可在配置文件全局设置)
//	 */
//	long waitTime() default 180L;
//
//	/**
//	 *
//	 */
//	long retryTimes() default 180L;
//}
