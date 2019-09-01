package com.dodge.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dodge.board.domain.Member;

@Controller
public class SecurityController {

	//Ȩ���� ����
	@RequestMapping("/home")
	public String accessHome() {
		return "/index";
	}
	
	@RequestMapping("/system/login")
	public void login() {}
	
	//ȸ������
	@RequestMapping("/register")
	public String register(Member member) {
		return "redirect:home";
	}
	@GetMapping("/system/accessDenied")
	public void accessDenied() {}
	
	@GetMapping("/system/logout")
	public void logout() {}
	
	@GetMapping("/admin/adminPage")
	public void admin() {}
	
}
