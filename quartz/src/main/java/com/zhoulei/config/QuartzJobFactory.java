package com.zhoulei.config;

import com.zhoulei.entity.QuartzConfigDTO;
import com.zhoulei.service.QuartzJobService;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class QuartzJobFactory extends AdaptableJobFactory {

    @Autowired
    private AutowireCapableBeanFactory capableBeanFactory;

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        //调用父类的方法
        Object jobInstance = super.createJobInstance(bundle);
        //进行注入
        capableBeanFactory.autowireBean(jobInstance);
        return jobInstance;
    }

    @Autowired
    private QuartzJobService quartzJobService;


    @PostConstruct
    public Object addJob() {
        QuartzConfigDTO configDTO = new QuartzConfigDTO();
        Map<String,Object> map = new HashMap<>();
        map.put("hello","world");
        configDTO.setJobName("myJob");
        configDTO.setGroupName("myGroup");
        configDTO.setJobClass("com.zhoulei.job.TfCommandJob");
        configDTO.setCronExpression("0/5 * * * * ?");
        configDTO.setParam(map);
        quartzJobService.addJob(configDTO.getJobClass(), configDTO.getJobName(), configDTO.getGroupName(), configDTO.getCronExpression(), configDTO.getParam());
        return HttpStatus.OK;
    }
}
