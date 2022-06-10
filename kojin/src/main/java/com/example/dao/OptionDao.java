package com.example.dao;

import java.util.List;

import com.example.entity.Options;

public interface OptionDao{
	public List<Options> findAll();
}