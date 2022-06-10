package com.example.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.controller.form.EditUserForm;
import com.example.dao.OrderFormDao;
import com.example.dao.UsersDao;
import com.example.entity.Users;

@Controller
public class EditUserController{
	@Autowired
	UsersDao usersDao;
	@Autowired
	OrderFormDao orderFormDao;
	@Autowired
	HttpSession session;
	
	Calendar cal = Calendar.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	String now = sdf.format(cal.getTime());
	
	@RequestMapping(value="edit", params="update")
	public String editUser(@Validated @ModelAttribute("edit_user") EditUserForm form, 
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
            return "edit-user";
        }
		Users user = usersDao.findLoginId(form.getLoginId());
		Users loginUser = (Users)session.getAttribute("user");
		if(user != null && user.getId() != loginUser.getId()) {
			model.addAttribute("msg", "LoginIDが重複しています");
			return "edit-user";
		}
		usersDao.updateUser(form.getName(), form.getLoginId(), form.getPassword(), loginUser.getId());
		model.addAttribute("orderList", orderFormDao.findOrderList(now));
		session.setAttribute("user", usersDao.findLoginId(form.getLoginId()));
		return "order-form";
	}
	
	@RequestMapping(value="edit", params="back")
	public String back(@ModelAttribute("edit_user") EditUserForm form, Model model) {
		model.addAttribute("orderList", orderFormDao.findOrderList(now));
		return "order-form";
	}
}