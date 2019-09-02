package com.dodge.board.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodge.board.domain.Member;
import com.dodge.board.service.LoginService;

@Controller
public class SecurityController {
	
	@Resource(name="BasicLoginService")
	private LoginService loginService;
	
	//홈으로 가기
	@RequestMapping("/home")
	public String accessHome() {
		System.out.println("홈화면");
		return "index";
	}
	
	@GetMapping("/system/login")
	public void login() {}
	
	//회원가입
	@RequestMapping("/register")
	public String register(Member member) {
		System.out.println("회원가입");
		loginService.postMember(member);
		return "redirect:system/login";
	}
	
	//아이디 중복체크(Ajax)
	@RequestMapping(value="system/idcheck.do")
	@ResponseBody //Ajax
	public Map<Object, Object> idCheck(@RequestBody String id) throws Exception {
		System.out.println("아이디 중복체크");
			
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("cnt", loginService.idCheck(id));
			
		return map;
	}
	
	@GetMapping("/system/accessDenied")
	public void accessDenied() {}
	
	@GetMapping("/system/logout")
	public void logout() {
		System.out.println("로그아웃");
	}
	
	@GetMapping("/admin/adminPage")
	public void admin() {}
	
}
