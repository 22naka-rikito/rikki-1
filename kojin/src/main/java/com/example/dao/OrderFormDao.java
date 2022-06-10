package com.example.dao;

import java.util.List;

import com.example.entity.OrderForm;

public interface OrderFormDao{
	public List<OrderForm> findOrderDate(String date);
	public void insertOrderForm(String date, Integer userId);
	public List<OrderForm> findOrderList(String date);
	public void updateOrder(Integer userId, Integer bentoId, Integer optionId, Integer riceId);
}