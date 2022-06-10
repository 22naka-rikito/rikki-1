package com.example.dao;

import java.util.List;

import com.example.entity.Reservation;

public interface ReservationDao{
	public List<Reservation> findAll();
	public Reservation findToday(String date);
	public void insertUser(Integer id, String date);
}