package com.example.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.dao.OptionDao;
import com.example.entity.Options;

@Repository
public class PgOptionDao implements OptionDao{

	@Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
	
	private final String FIND_ALL = "SELECT * FROM options";
	
	@Override
	public List<Options> findAll() {
		String sql = FIND_ALL;
		MapSqlParameterSource param = new MapSqlParameterSource();
		List<Options> optionList = jdbcTemplate.query(sql, param, new BeanPropertyRowMapper<Options>(Options.class));
		return optionList.isEmpty() ? null : optionList;
	}
	
}