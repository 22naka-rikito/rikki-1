package com.example.dao;

import java.util.List;

import com.example.entity.Users;

public interface UsersDao{
	public Users findUser(String loginId, String password);
	public Users findId(Integer id);
	public List<Users> findUsers();
	public Users findLoginId(String loginId);
	public void insertUser(String name, String loginId, String password);
	public void updateUser(String name, String loginId, String password, Integer id);
	public void deleteUser(String id);
}