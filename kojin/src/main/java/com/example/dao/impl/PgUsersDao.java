package com.example.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.dao.UsersDao;
import com.example.entity.Users;

@Repository
public class PgUsersDao implements  UsersDao{
	private final String FIND_USER = "SELECT * FROM users WHERE login_id = :login_id AND password = :password";
	private final String FIND_USER_ID = "SELECT * FROM users WHERE id = :id";
	private final String FIND_USER_AUTHORITY = "SELECT * FROM users WHERE authority = 2";
	private final String FIND_LOGIN_ID = "SELECT * FROM users WHERE login_id = :login_id";
	private final String INSERT_USER = "INSERT INTO users(name, login_id, password, authority) "
			+ "VALUES(:name, :login_id, :password, 2)";
	private final String UPDATE_USER = "UPDATE users SET name = :name, "
			+ "login_id = :login_id, password = :password WHERE id = :id";
	private static final String DELETE_USER = "DELETE FROM users WHERE id = :id";
	
	
	@Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public Users findUser(String loginId, String password) {
		String sql = FIND_USER;
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("login_id", loginId);
		param.addValue("password", password);
		List<Users> usersList = jdbcTemplate.query(sql, param, new BeanPropertyRowMapper<Users>(Users.class));
		return usersList.isEmpty() ? null : usersList.get(0);
	}
	
	@Override
	public Users findId(Integer id) {
		String sql = FIND_USER_ID;
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("id", id);
		List<Users> usersList = jdbcTemplate.query(sql, param, new BeanPropertyRowMapper<Users>(Users.class));
		return usersList.isEmpty() ? null : usersList.get(0);
	}
	
	@Override
	public List<Users> findUsers() {
		String sql = FIND_USER_AUTHORITY;
		MapSqlParameterSource param = new MapSqlParameterSource();
		List<Users> usersList = jdbcTemplate.query(sql, param, new BeanPropertyRowMapper<Users>(Users.class));
		return usersList;
	}
	
	@Override
	public Users findLoginId(String loginId) {
		String sql = FIND_LOGIN_ID;
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("login_id", loginId);
		List<Users> usersList = jdbcTemplate.query(sql, param, new BeanPropertyRowMapper<Users>(Users.class));
		return usersList.isEmpty() ? null : usersList.get(0);
	}
	
	@Override
	public void insertUser(String name, String loginId, String password) {
		String sql = INSERT_USER;
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("name", name);
		param.addValue("login_id", loginId);
		param.addValue("password", password);
		jdbcTemplate.update(sql, param);
	}
	
	public void updateUser(String name, String loginId, String password, Integer id) {
    	String sql = UPDATE_USER;
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("name", name);
        param.addValue("login_id", loginId);
        param.addValue("password", password);
        param.addValue("id", id);
        jdbcTemplate.update(sql, param);
    }

	@Override
	public void deleteUser(String id) {
		String sql = DELETE_USER;
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("id", Integer.parseInt(id));
        jdbcTemplate.update(sql, param);
	}
}