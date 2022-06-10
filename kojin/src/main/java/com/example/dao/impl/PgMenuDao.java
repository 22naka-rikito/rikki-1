package com.example.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.dao.MenuDao;
import com.example.entity.Menu;

@Repository
public class PgMenuDao implements MenuDao{
	
	@Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
	
	private final String FIND_ALL = "SELECT * FROM menu";
	
	@Override
	public List<Menu> findAll() {
		String sql = FIND_ALL;
		MapSqlParameterSource param = new MapSqlParameterSource();
		List<Menu> menuList = jdbcTemplate.query(sql, param, new BeanPropertyRowMapper<Menu>(Menu.class));
		return menuList.isEmpty() ? null : menuList;
	}
	
}