package com.mobei.caching;

import com.mobei.caching.bean.Employee;
import com.mobei.caching.mapper.EmployeeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void contextLoads() {
        Employee employee = employeeMapper.getEmpById(1);
        System.err.println(employee);
    }

    @Test
    public void test1() {
        stringRedisTemplate.opsForValue().append("ss1", "你好!");
    }

    @Test
    public void test2() {
        Employee employee = employeeMapper.getEmpById(1);
        redisTemplate.opsForValue().set("myEmployee", employee);
    }

}
