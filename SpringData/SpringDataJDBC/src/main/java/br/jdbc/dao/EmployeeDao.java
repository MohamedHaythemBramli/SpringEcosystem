package br.jdbc.dao;

import br.jdbc.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeDao {
    Integer save(Employee employee);
    Integer delete(Integer id);

    Integer update(Employee employee);
    Optional<Employee> findById(Integer id);

    List<Employee> findAll();

    Integer count();

}
