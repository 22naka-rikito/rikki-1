package com.example.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.controller.form.CreateUserForm;
import com.example.controller.form.IndexForm;
import com.example.dao.OrderFormDao;
import com.example.dao.ReservationDao;
import com.example.dao.UsersDao;
import com.example.entity.Reservation;
import com.example.entity.Users;

@Controller
public class IndexController{
	@Autowired
	UsersDao usersDao;
	@Autowired
	OrderFormDao orderFormDao;
	@Autowired
	ReservationDao reservationDao;
	@Autowired
	HttpSession session;
	
	Calendar cal = Calendar.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	String now = sdf.format(cal.getTime());
	
	@RequestMapping(value="/back")
	public String back(Model model) {
		model.addAttribute("orderList", orderFormDao.findOrderList(now));
		return "order-form";
	}
	
	@RequestMapping(value="/delete")
	public String delete(@RequestParam("id") String id, Model model) {
		usersDao.deleteUser(id);
		List<Users> list = usersDao.findUsers();
		model.addAttribute("userList", list);
		return "show-user";
	}
	
	@RequestMapping(value={"/", "/index"})
	public String index(@ModelAttribute("index") IndexForm form, Model model ) {
		return "index";
	}
	
	@RequestMapping(value="/index", params="login")
	public String login(@Validated @ModelAttribute("index") IndexForm form, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
            return "index";
        }
		Users user = usersDao.findUser(form.getLoginId(), form.getPass());
		if(user != null) {
			session.setAttribute("user", user);
			if(orderFormDao.findOrderDate(now) == null) {
				List<Users> list = usersDao.findUsers();
				for(Users u : list) {
					orderFormDao.insertOrderForm(now, u.getId());
				}
			}
			if(reservationDao.findToday(now) == null) {
				List<Reservation> list = reservationDao.findAll();
				Integer userId = list.get(list.size() - 1).getUserId();
				Users userOld = usersDao.findId(userId);
				List<Users> userList = usersDao.findUsers();
				Integer index = userList.indexOf(userOld);
				index = index + 1 >= userList.size() ? 0 : index + 1;
				reservationDao.insertUser(userList.get(index).getId(), now);
				session.setAttribute("orderUser", userList.get(index));
			}else {
				List<Reservation> list = reservationDao.findAll();
				Integer userId = list.get(list.size() - 1).getUserId();
				Users userToday = usersDao.findId(userId);
				session.setAttribute("orderUser", userToday);
			}
			model.addAttribute("orderList", orderFormDao.findOrderList(now));
			return "order-form";
		}else {
			model.addAttribute("msg", "???");
			return "index";
		}
	}
	
	@RequestMapping(value="/index", params="create_user")
	public String createUser(@ModelAttribute("index") IndexForm form, @ModelAttribute("create_user") CreateUserForm createUserForm, Model model) {
		createUserForm.setName(null);
		createUserForm.setLoginId(null);
		createUserForm.setPassword(null);
		return "create-user";
	}
}