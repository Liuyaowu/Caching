package com.mobei.caching.service;

import com.mobei.caching.bean.Employee;
import com.mobei.caching.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.*;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;

@CacheConfig(cacheNames = "emp")
@Service
public class EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private RedisCacheManager redisCacheManager;

    //意思是添加了一个名字叫emp的Cache,默认用参数当做key,如这里的id
//    @Cacheable(value = "emp",/*key = "#root.args[0]"*/key = "#p0", /*keyGenerator = "myKeyGenerator"*/unless = "#a0>1")
//    public Employee getEmp(Integer id) {
//        System.out.println("查询: " + id + "号员工");
//        return employeeMapper.getEmpById(id);
//    }

    @Cacheable(value = "emp", key = "#p0")
    public Employee getEmp(Integer id) {
        System.out.println("查询: " + id + "号员工");
        return employeeMapper.getEmpById(id);
    }

    //编码方式执行缓存
    public Employee getEmpByCoding(Integer id) {
        System.out.println("编码方式--  查询: " + id + "号员工");
        Employee employee = employeeMapper.getEmpById(id);
        Cache cache = redisCacheManager.getCache("emp");
        cache.put("emp:" + id, employee);
        return employee;
    }

    @CachePut(value = "emp", key = "#result.id")
    public Employee updateEmp(Employee employee) {
        System.out.println("更新员工: " + employee);
        employeeMapper.updateEmp(employee);
        return employee;
    }

    @CacheEvict(value = "emp", key = "#id", beforeInvocation = true)
    public void deleteEmp(Integer id) {
        System.out.println("删除员工: " + id);
        int a = 10 / 0;
        //employeeMapper.deleteEmpById(id);
    }

    @Caching(
            cacheable = {@Cacheable(value = "emp", key = "#lastName")},
            put = {@CachePut(value = "emp", key = "#result.id")}
    )
    public Employee getEmployeeByLastName(String lastName) {
        System.out.println("根据名称查询员工: " + lastName);
        return employeeMapper.getEmpByLastName(lastName);
    }

}
