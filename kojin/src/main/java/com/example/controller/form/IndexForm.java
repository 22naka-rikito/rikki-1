package com.example.controller.form;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class IndexForm{
	@NotBlank
	private String loginId;
	@NotBlank
	private String pass;
}