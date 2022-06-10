package com.example.entity;

import lombok.Data;

@Data
public class Users{
	private Integer id;
	private String name;
	private String loginId;
	private String password;
	private Integer orderId;
	private Integer authority;
}