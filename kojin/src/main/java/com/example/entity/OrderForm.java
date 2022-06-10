package com.example.entity;

import lombok.Data;

@Data
public class OrderForm{
	private Integer id;
	private Integer userId;
	private Integer bentoId;
	private Integer optionId;
	private Integer riceId;
	private String orderDate;
	private String userName;
	private String bentoName;
	private String optionName;
	private String type;
	private Integer price;
}