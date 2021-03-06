package com.zhoulei.dao;


import com.zhoulei.entity.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by EalenXie on 2018/6/4 14:27
 */
public interface JobEntityRepository extends JpaRepository<JobEntity, Long> {

    JobEntity getById(Integer id);

}
