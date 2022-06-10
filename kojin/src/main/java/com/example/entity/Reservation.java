package com.example.entity;

import lombok.Data;

@Data
public class Reservation{
	private Integer id;
	private Integer userId;
	private String orderDate;
}