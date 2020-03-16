package com.mobei.caching.controller;

import com.mobei.caching.bean.Employee;
import com.mobei.caching.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/getEmployee")
    public Employee getEmployee(Integer id) {
        return employeeService.getEmp(id);
    }

    @GetMapping("/getEmployeeByCoding")
    public Employee getEmployeeByCoding(Integer id) {
        return employeeService.getEmpByCoding(id);
    }

    @GetMapping("/updateEmployee")
    public Employee updateEmployee(String lastName, String email) {
        Employee employeee = employeeService.getEmp(1);
        employeee.setLastName(lastName);
        employeee.setEmail(email);
        return employeeService.updateEmp(employeee);
    }

    @GetMapping("/deleteEmployee")
    public Integer deleteEmployee(Integer id) {
        employeeService.deleteEmp(1);
        return id;
    }

    @GetMapping("/getEmployeeByLastName")
    public Employee getEmployeeByLastName(String lastName) {
        Employee employee = employeeService.getEmployeeByLastName(lastName);
        return employee;
    }

}
