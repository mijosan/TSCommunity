package com.dodge.board.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SecurityController {
	
	@RequestMapping("/system/login")
	public String login() {
		return "/system/login";
	}
	
	@RequestMapping("/login")
	public String googleLogin() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(auth.getPrincipal());
		return "/index";
	}
	
	@RequestMapping("/callback")
	public String callback() {
		System.out.println("redirecting to home page");
		return "/index";
	}
	
	@GetMapping("/system/accessDenied")
	public void accessDenied() {}
	
	@GetMapping("/system/logout")
	public void logout() {}
	
	@GetMapping("/admin/adminPage")
	public void admin() {}

	
}
