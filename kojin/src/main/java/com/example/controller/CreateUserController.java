package com.example.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.controller.form.CreateUserForm;
import com.example.controller.form.IndexForm;
import com.example.dao.OrderFormDao;
import com.example.dao.UsersDao;
import com.example.entity.Users;

@Controller
public class CreateUserController{
	@Autowired
	UsersDao usersDao;
	@Autowired
	OrderFormDao orderFormDao;
	
	Calendar cal = Calendar.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	String now = sdf.format(cal.getTime());
	
	@RequestMapping( value="create")
	public String createUser(@ModelAttribute("create_user") CreateUserForm createUserForm, Model model) {
		return "create-user";
	}
	
	@RequestMapping( value="create", params="create")
	public String create(@Validated @ModelAttribute("create_user") CreateUserForm createUserForm, 
			BindingResult bindingResult, @ModelAttribute("index") IndexForm indexForm, Model model) {
		if (bindingResult.hasErrors()) {
            return "create-user";
        }
		Users user = usersDao.findLoginId(createUserForm.getLoginId());
		if(user != null) {
			model.addAttribute("msg", "LoginIDが重複しています");
			return "create-user";
		}
		usersDao.insertUser(createUserForm.getName(), createUserForm.getLoginId(), createUserForm.getPassword());
		Integer id = usersDao.findUser(createUserForm.getLoginId(), createUserForm.getPassword()).getId();
		orderFormDao.insertOrderForm(now, id);
		return "index";
	}
	
}