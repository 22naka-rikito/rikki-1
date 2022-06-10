package com.example.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.dao.ReservationDao;
import com.example.entity.Reservation;

@Repository
public class PgReservationDao implements ReservationDao{

	private final String FIND_ALL = "SELECT * FROM reservation";
	private final String FIND_TODAY = "SELECT * FROM reservation WHERE order_date = :order_date";
	private final String INSERT_USER = "INSERT INTO reservation(user_id, order_date) VALUES(:id, :date)";
	
	@Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public List<Reservation> findAll() {
		String sql = FIND_ALL;
		MapSqlParameterSource param = new MapSqlParameterSource();
		List<Reservation> reservationList = jdbcTemplate.query(sql, param, new BeanPropertyRowMapper<Reservation>(Reservation.class));
		return reservationList.isEmpty() ? null : reservationList;
	}
	
	@Override
	public Reservation findToday(String date) {
		String sql = FIND_TODAY;
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("order_date", date);
		List<Reservation> reservationList = jdbcTemplate.query(sql, param, new BeanPropertyRowMapper<Reservation>(Reservation.class));
		return reservationList.isEmpty() ? null : reservationList.get(0);
	}

	@Override
	public void insertUser(Integer id, String date) {
		String sql = INSERT_USER;
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("id", id);
		param.addValue("date", date);
		jdbcTemplate.update(sql, param);
	}

}