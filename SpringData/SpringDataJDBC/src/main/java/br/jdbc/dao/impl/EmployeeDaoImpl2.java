package br.jdbc.dao.impl;

import br.jdbc.dao.EmployeeDao;
import br.jdbc.entity.Employee;
import br.jdbc.mapper.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jdbc.core.convert.EntityRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
@Qualifier("employeeDao2")
public class EmployeeDaoImpl2 implements EmployeeDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Override
    public Integer save(Employee employee) {
        String sql = "INSERT INTO employees (id, name, salary) VALUES (:id, :name, :salary)";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", employee.getId());
        parameters.put("name", employee.getName());
        parameters.put("salary", employee.getSalary());
        return namedParameterJdbcTemplate.update(sql,parameters);
    }

    @Override
    public Integer delete(Integer id) {
        String sql = "DELETE FROM employees WHERE id = :id";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", id);
        return namedParameterJdbcTemplate.update(sql,parameters);
    }

    @Override
    public Integer update(Employee employee) {
        String sql = "UPDATE employees SET name = :name, salary = :salary WHERE id = :id";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", employee.getId());
        parameters.put("name", employee.getName());
        parameters.put("salary", employee.getSalary());
        return namedParameterJdbcTemplate.update(sql,parameters);
    }

    @Override
    public Optional<Employee> findById(Integer id) {
        String sql = "SELECT * FROM employees WHERE id = :id";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", id);
        return Optional.of(namedParameterJdbcTemplate.queryForObject(sql, parameters, new EmployeeMapper()));
    }

    @Override
    public List<Employee> findAll() {
        String sql = "SELECT * FROM employees";
        return namedParameterJdbcTemplate.query(sql, new HashMap<>(), new EmployeeMapper());
    }

    @Override
    public Integer count() {
        String sql = "SELECT COUNT(*) FROM employees";
        return namedParameterJdbcTemplate.queryForObject(sql, new HashMap<>(), Integer.class);
    }
}
