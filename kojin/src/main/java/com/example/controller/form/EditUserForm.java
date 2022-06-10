package com.example.controller.form;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class EditUserForm{
	@NotBlank
	private String name;
	@NotBlank
	private String loginId;
	@NotBlank
	private String password;
}