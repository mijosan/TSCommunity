package com.dodge.board.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	@RequestMapping(value="system/idCheck.do")
	@ResponseBody //Ajax
	public Map<Object, Object> idCheck(@RequestBody String id) throws Exception {
		System.out.println("아이디 중복체크");
			
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("cnt", loginService.idCheck(id));
			
		return map;
	}
	
	//이메일 중복체크(Ajax)
	@RequestMapping(value="system/emailCheck.do")
	@ResponseBody //Ajax
	public Map<Object, Object> emailCheck(@RequestBody String email) throws Exception {
		System.out.println("이메일 중복체크");
				
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("cnt", loginService.emailCheck(email));
				
		return map;
	}
	
	//이메일 보내기
	@RequestMapping(value="system/searchMember.do")
	@ResponseBody //Ajax
	public Map<Object, Object> searchMember(@RequestBody String email) throws Exception {
		System.out.println("이메일 보내기");

		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("cnt", loginService.searchMember(email));
					
		return map;
	}
	
	//myInfo
	@GetMapping("/myInfo")
	public String myInfo() {
		return "system/myInfo";
	}
	
	//비밀번호 변경(Ajax 요청)
	@RequestMapping("putPassword.do")
	@ResponseBody
	public Map<Object, Object> putPassword(@RequestBody String password) throws Exception {
		System.out.println("비밀번호 변경");

		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("cnt", loginService.putPassword(password));
					
		return map;
	}
	
	//회원탈퇴 하기(Ajax)
	@RequestMapping("deleteMember.do")
	@ResponseBody
	public Map<Object, Object> deleteMember(){
		System.out.println("회원 탈퇴");
		
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("cnt", loginService.deleteMember());
					
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
