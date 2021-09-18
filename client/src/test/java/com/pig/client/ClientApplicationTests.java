package com.pig.client;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pig.client.mapper.CourseMapper;
import com.pig.client.pojo.Course;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class ClientApplicationTests {

	@Autowired
	private CourseMapper courseMapper;

	@Test
	public void testDbAdd(){
		for (int i = 1; i <= 4; i++) {
			Course course = new Course();
			course.setCname("sql_"+i);
			course.setUserId(100L+i);
			course.setStatus("1");
			courseMapper.insert(course);
		}
	}

	@Test
	public void testDbGet(){
		QueryWrapper<Course> wrapper = new QueryWrapper<>();
		wrapper.eq("cid",557605627916976129L);
		wrapper.eq("user_id",101L);
		Course course = courseMapper.selectOne(wrapper);
		System.out.println(course);
	}
	@Test
	public void testDbGetList() {
		QueryWrapper<Course> wrapper = new QueryWrapper<>();
		wrapper.eq("cstatus", "1");
		wrapper.in("cid", 557605627916976129L, 557588085982887936L);
		List<Course> courseList = courseMapper.selectList(wrapper);
		System.out.println(courseList.toString());

	}
}
