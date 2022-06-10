package com.example.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.dao.OrderFormDao;
import com.example.entity.OrderForm;

@Repository
public class PgOrderFormDao implements OrderFormDao{
	private final String FIND_ORDER_DATE = "SELECT * FROM order_form WHERE order_date = :date";
	private final String INSERT_ORDER_FORM = "INSERT INTO order_form(user_id, order_date, option_id, rice_id) "
			+ "VALUES(:user_id, :date, 1, 1)";
	private final String FIND_ORDER_LIST = "SELECT o.id, u.name AS user_name, m.name AS bento_name, "
			+ "op.name AS option_name, o.order_date, r.type, m.price + op.price AS price "
			+ "FROM order_form AS o "
			+ "JOIN users AS u "
			+ "ON o.user_id = u.id "
			+ "LEFT JOIN menu AS m "
			+ "ON o.bento_id = m.id "
			+ "LEFT JOIN options AS op "
			+ "ON o.option_id = op.id "
			+ "LEFT JOIN rice AS r "
			+ "ON o.rice_id = r.id "
			+ "WHERE order_date = :date";
	private final String UPDATE_ORDER = "UPDATE order_form SET bento_id = :bento_id"
			+ ", option_id = :option_id, rice_id = :rice_id "
			+ "WHERE user_id = :user_id";
	
	@Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public List<OrderForm> findOrderDate(String date) {
		String sql = FIND_ORDER_DATE;
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("date", date);
		List<OrderForm> orderList = jdbcTemplate.query(sql, param, new BeanPropertyRowMapper<OrderForm>(OrderForm.class));
		return orderList.isEmpty() ? null : orderList;
	}

	@Override
	public void insertOrderForm(String date, Integer userId) {
		String sql = INSERT_ORDER_FORM;
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("user_id", userId);
		param.addValue("date", date);
		jdbcTemplate.update(sql, param);
	}

	@Override
	public List<OrderForm> findOrderList(String date) {
		String sql = FIND_ORDER_LIST;
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("date", date);
		List<OrderForm> orderList = jdbcTemplate.query(sql, param, new BeanPropertyRowMapper<OrderForm>(OrderForm.class));
		return orderList.isEmpty() ? null : orderList;
	}
	
	@Override
	public void updateOrder(Integer userId, Integer bentoId, Integer optionId, Integer riceId) {
		String sql = UPDATE_ORDER;
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("bento_id", bentoId);
		param.addValue("rice_id", riceId);
		param.addValue("option_id", optionId);
		param.addValue("user_id", userId);
		jdbcTemplate.update(sql, param);
	}
	
}