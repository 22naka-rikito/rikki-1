package com.example.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.dao.RiceDao;
import com.example.entity.Rice;

@Repository
public class PgRiceDao implements RiceDao {

	@Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
	
	private final String FIND_ALL = "SELECT * FROM rice";
	
	@Override
	public List<Rice> findAll() {
		String sql = FIND_ALL;
		MapSqlParameterSource param = new MapSqlParameterSource();
		List<Rice> riceList = jdbcTemplate.query(sql, param, new BeanPropertyRowMapper<Rice>(Rice.class));
		return riceList.isEmpty() ? null : riceList;
	} 
	
}