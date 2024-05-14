package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Administrator;

//管理者のリポジトリ

@Repository
public class AdministratorRepository {

    private static final BeanPropertyRowMapper<Administrator> ADMINISTRATOR_ROW_MAPPER = new BeanPropertyRowMapper<>(
            Administrator.class);

    @Autowired
    private NamedParameterJdbcTemplate template;

    // 管理者情報登録
    public void insert(Administrator administrator) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(administrator);
        String insertSql = "INSERT INTO administrators(id,name,mailAddress,password) VALUES(:id,:name,:mailAddress,:password)";
        template.update(insertSql, param);
    }

    // メールアドレスとパスワードから管理者情報を取得する(1 件も存在しない場合は null を返す)。
    public Administrator findByMailAddressAndPassword(String mailAddress, String password) {
        String sql = "SELECT * FROM administrators WHERE mailAddress = :mailAddress AND password = :password";
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("mailAddress", mailAddress)
                .addValue("password", password);
                
        List<Administrator> administratorList = template.query(sql, param, ADMINISTRATOR_ROW_MAPPER);
        if (administratorList.size() == 0) {
            return null;
        }

        return administratorList.get(0);
    }
}
