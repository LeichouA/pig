package com.manager.aop;


import com.manager.annotation.DataSource;
import com.manager.config.DataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class DynamicDataSourceAspect {


    @Before("@annotation(dataSource)")
    public void before(JoinPoint joinPoint, DataSource dataSource){

        if (dataSource.value().equals("slaveDataSource")){
            DataSourceContextHolder.setDataSource("slaveDataSource");
            log.info("switch to slave datasource..."+joinPoint.getClass().getName());
        }else {
            DataSourceContextHolder.setDataSource("masterDataSource");
            log.info("switch to master datasource..."+joinPoint.getClass().getName());
        }

    }

    @After("@annotation(dataSource)")
    public void after(JoinPoint joinPoint, DataSource dataSource){
        log.info("清除 datasource router...");
        DataSourceContextHolder.clear();
    }

}
