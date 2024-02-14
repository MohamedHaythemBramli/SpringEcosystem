package br.jdbc.controller;

import br.jdbc.dao.EmployeeDao;
import br.jdbc.entity.Employee;
import br.jdbc.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v2/employees")
@RequiredArgsConstructor
public class EmployeeJDBCController {
    private final EmployeeRepository employeeRepository;

    @GetMapping("/count")
    public Long count() {
        return employeeRepository.count();
    }
    @PostMapping
    public Employee save(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }
    @GetMapping("/{id}")
    public Optional<Employee> findById(@PathVariable Integer id) {
        return employeeRepository.findById(id);
    }
    @GetMapping
    public Iterable<Employee> findAll() {
        return employeeRepository.findAll();
    }
    @PutMapping
    public Employee update(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
         employeeRepository.deleteById(id);
    }

    @GetMapping("/byName/{name}")
    public List<Employee> findByName(@PathVariable String name) {
        return employeeRepository.findByName(name);
    }

    @GetMapping("/byNameAndSalary/{name}/{salary}")
    public List<Employee> findByNameAndSalary(@PathVariable String name, @PathVariable Double salary) {
        return employeeRepository.findByNameAndSalary(name, salary);
    }
}
