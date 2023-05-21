package com.example.firebasepractice.controller;

import com.example.firebasepractice.entity.Employee;
import com.example.firebasepractice.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    public List<Employee> getEmployees() throws ExecutionException, InterruptedException {
        return employeeService.getEmployees();
    }

    @PostMapping("/employee")
    public String addEmployee(@RequestBody Employee employee) throws ExecutionException, InterruptedException {
        return employeeService.addEmployee(employee);
    }

    @PutMapping("/employee")
    public String updateEmployee(@RequestBody Employee employee) throws ExecutionException, InterruptedException {
        return employeeService.updateEmployee(employee);
    }

    @GetMapping("/employees/{name}")
    public Employee getEmployee(@PathVariable String name) throws ExecutionException, InterruptedException {
        return employeeService.getEmployee(name);
    }

    @DeleteMapping("/employees/{name}")
    public String deleteEmployee(@PathVariable String name){
        return employeeService.deleteEmployee(name);
    }

}
