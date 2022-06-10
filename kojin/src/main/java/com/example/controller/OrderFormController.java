package com.example.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.controller.form.EditUserForm;
import com.example.controller.form.IndexForm;
import com.example.controller.form.OrderForm;
import com.example.dao.MenuDao;
import com.example.dao.OptionDao;
import com.example.dao.OrderFormDao;
import com.example.dao.RiceDao;
import com.example.dao.UsersDao;
import com.example.entity.Users;

@Controller
public class OrderFormController{
	@Autowired
	UsersDao usersDao;
	@Autowired
	OrderFormDao orderFormDao;
	@Autowired
	MenuDao menuDao;
	@Autowired
	OptionDao optionDao;
	@Autowired
	RiceDao riceDao;
	
	@Autowired
	HttpSession session;
	
	Calendar cal = Calendar.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	String now = sdf.format(cal.getTime());
	
	@RequestMapping(value={"/logout"})
	public String logout(@ModelAttribute("index") IndexForm form, Model model) {
		session.invalidate();
		return "index";
	}
	
	@RequestMapping(value="edit")
	public String userEdit(@ModelAttribute("edit_user") EditUserForm form, Model model) {
		Users user = (Users)session.getAttribute("user");
		if(user == null) {
			return "index";
		}
		form.setName(user.getName());
		form.setLoginId(user.getLoginId());
		form.setPassword(user.getPassword());
		return "edit-user";
	}
	
	@RequestMapping(value="show")
	public String userShow(Model model) {
		Users user = (Users)session.getAttribute("user");
		if(user == null) {
			return "index";
		}
		List<Users> list = usersDao.findUsers();
		model.addAttribute("userList", list);
		return "show-user";
	}
	
	@RequestMapping(value="order")
	public String order(@ModelAttribute("order") OrderForm orderform, Model model) {
		model.addAttribute("bento", menuDao.findAll());
		model.addAttribute("options", optionDao.findAll());
		model.addAttribute("rice", riceDao.findAll());
		return "order";
	}
}