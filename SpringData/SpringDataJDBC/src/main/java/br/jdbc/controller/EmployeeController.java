package br.jdbc.controller;

import br.jdbc.dao.EmployeeDao;
import br.jdbc.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/employees")
public class EmployeeController {


    private final   EmployeeDao employeeDao;
    @Autowired
    public EmployeeController(@Qualifier("employeeDao2") EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @GetMapping("/count")
    public Integer count() {
        return employeeDao.count();
    }
    @PostMapping
    public Integer save(@RequestBody Employee employee) {
        return employeeDao.save(employee);
    }
    @GetMapping("/{id}")
    public Optional<Employee> findById(@PathVariable Integer id) {
        return employeeDao.findById(id);
    }
    @GetMapping
    public List<Employee> findAll() {
        return employeeDao.findAll();
    }
    @PutMapping
    public Integer update(@RequestBody Employee employee) {
        return employeeDao.update(employee);
    }

    @DeleteMapping("/{id}")
    public Integer delete(@PathVariable Integer id) {
        return employeeDao.delete(id);
    }

}
