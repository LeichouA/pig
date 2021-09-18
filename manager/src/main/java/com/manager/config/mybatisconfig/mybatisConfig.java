package com.manager.config.mybatisconfig;

import com.manager.config.DynamicDataSourceRouter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class mybatisConfig {

    @Resource(name = "masterDataSource")
    private DataSource masterDataSource;

    @Resource(name = "slaveDataSource")
    private DataSource slaveDataSource;

    //    @Primary
    @Bean(name = "routingDataSource")
    public DynamicDataSourceRouter routingDataSource() {
        DynamicDataSourceRouter proxy = new DynamicDataSourceRouter();
        Map<Object, Object> targetDataSources = new HashMap<>(2);
        targetDataSources.put("masterDataSource", masterDataSource);
        targetDataSources.put("slaveDataSource", slaveDataSource);

        proxy.setDefaultTargetDataSource(masterDataSource);
        proxy.setTargetDataSources(targetDataSources);
        return proxy;
    }

    /**
     * 2、根据数据源创建SqlSessionFactory
     * @throws Exception
     */
    @Bean(name="sessionFactory")
    public SqlSessionFactory sessionFactory(@Qualifier("routingDataSource")DynamicDataSourceRouter dataSource) throws Exception{
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        sessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));    //*Mapper.xml位置
        return sessionFactoryBean.getObject();
    }
}
