package com.example.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.controller.form.OrderForm;
import com.example.dao.OrderFormDao;
import com.example.entity.Users;

@Controller
public class OrderCotroller{
	@Autowired
	OrderFormDao orderFormDao;
	
	@Autowired
	HttpSession session;
	
	Calendar cal = Calendar.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	String now = sdf.format(cal.getTime());
	
	@RequestMapping(value={"/order"}, params="decision")
	public String orderDecision(@ModelAttribute("order") OrderForm form, Model model) {
		Users user = (Users)session.getAttribute("user");
		orderFormDao.updateOrder(user.getId(), form.getBentoId(), form.getOptionId(), form.getRiceId());
		
		model.addAttribute("orderList", orderFormDao.findOrderList(now));
		return "order-form";
	}
}