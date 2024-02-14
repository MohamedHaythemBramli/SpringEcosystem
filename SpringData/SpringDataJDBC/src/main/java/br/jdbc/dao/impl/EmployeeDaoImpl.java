package br.jdbc.dao.impl;

import br.jdbc.dao.EmployeeDao;
import br.jdbc.entity.Employee;
import br.jdbc.mapper.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
@Qualifier("employeeDao")
public class EmployeeDaoImpl implements EmployeeDao {

    private final JdbcTemplate jdbcTemplate;
    @Override
    public Integer save(Employee employee) {
        return jdbcTemplate.update("insert into employees (id, name, salary) values (?,?,?)",
                new Object [] {employee.getId(), employee.getName(), employee.getSalary()});
    }

    @Override
    public Integer delete(Integer id) {
        return jdbcTemplate.update("delete from employees where id = ?",
                new Object [] {id});
    }

    @Override
    public Integer update(Employee employee) {
        return jdbcTemplate.update("update into employees (id, name, salary) values (?,?,?)",
                new Object [] {employee.getId(), employee.getName(), employee.getSalary()});
    }

    @Override
    public Optional<Employee> findById(Integer id) {
        return Optional.of(jdbcTemplate.queryForObject("select id,name,salary from employees where id =?",
                new Object[]{id}, new EmployeeMapper()));
    }

    @Override
    public List<Employee> findAll() {
        return jdbcTemplate.query("select id, name, salary from employees" , new EmployeeMapper());
    }

    @Override
    public Integer count() {
        return jdbcTemplate.queryForObject("select count(*) from employees", Integer.class);
    }
}
