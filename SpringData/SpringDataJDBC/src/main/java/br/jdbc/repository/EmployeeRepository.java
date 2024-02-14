package br.jdbc.repository;

import br.jdbc.entity.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee,Integer> {

    List<Employee> findByName(String name);
    List<Employee> findByNameAndSalary(String name, Double salary);
}
