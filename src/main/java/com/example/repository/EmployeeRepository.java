package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Employee;

//管理者のリポジトリ

@Repository
public class EmployeeRepository {

    private static final BeanPropertyRowMapper<Employee> EMPLOYEE_ROW_MAPPER = new BeanPropertyRowMapper<>(
            Employee.class);

    @Autowired
    private NamedParameterJdbcTemplate template;

    public List<Employee> findAll() {
        String sql = "SELECT * FROM Employees ORDER BY hireDate DESC";
        List<Employee> employeeList = template.query(sql, EMPLOYEE_ROW_MAPPER);
        return employeeList;
    }

    public Employee load(Integer id) {
        String loadSql = "SELECT * FROM Employees WHERE id=:id";
        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
        Employee employee = template.queryForObject(loadSql, param, EMPLOYEE_ROW_MAPPER);
        return employee;

    }

    public void update(Employee employee) {
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", employee.getId())
                .addValue("name", employee.getName())
                .addValue("image", employee.getImage())
                .addValue("gender", employee.getGender())
                .addValue("hireDate", employee.getHireDate())
                .addValue("mailAddress", employee.getMailAddress())
                .addValue("zipCode", employee.getZipCode())
                .addValue("address", employee.getAddress())
                .addValue("telephone", employee.getTelephone())
                .addValue("salary", employee.getSalary())
                .addValue("characteristics", employee.getCharacteristics())
                .addValue("dependentsCount", employee.getDependentsCount());
        String updateSql = "UPDATE employees SET name=:name,image=:image,gender=:gender,hireDate=:hireDate,mailAddress=:mailAddress,zipCode=:zipCode,address=:address,telephone=:telephone,salary=:salary,characteristics=:characteristics,dependentsCount=:dependentsCount WHERE id=:id";
        template.update(updateSql, param);

    }

}
