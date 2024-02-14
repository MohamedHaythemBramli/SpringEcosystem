package br.jdbc.mapper;

import br.jdbc.entity.Employee;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeMapper implements RowMapper<Employee> {
    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Employee(rs.getInt("id"),
                rs.getDouble("salary"),
                rs.getString("name"));
    }
}
