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
	
	//Ȩ���� ����
	@RequestMapping("/home")
	public String accessHome() {
		System.out.println("Ȩȭ��");
		return "index";
	}
	
	@GetMapping("/system/login")
	public void login() {}
	
	//ȸ������
	@RequestMapping("/register")
	public String register(Member member) {
		System.out.println("ȸ������");
		loginService.postMember(member);
		return "redirect:system/login";
	}
	
	//���̵� �ߺ�üũ(Ajax)
	@RequestMapping(value="system/idCheck.do")
	@ResponseBody //Ajax
	public Map<Object, Object> idCheck(@RequestBody String id) throws Exception {
		System.out.println("���̵� �ߺ�üũ");
			
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("cnt", loginService.idCheck(id));
			
		return map;
	}
	
	//�̸��� �ߺ�üũ(Ajax)
	@RequestMapping(value="system/emailCheck.do")
	@ResponseBody //Ajax
	public Map<Object, Object> emailCheck(@RequestBody String email) throws Exception {
		System.out.println("�̸��� �ߺ�üũ");
				
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("cnt", loginService.emailCheck(email));
				
		return map;
	}
	
	//�̸��� ������
	@RequestMapping(value="system/searchMember.do")
	@ResponseBody //Ajax
	public Map<Object, Object> searchMember(@RequestBody String email) throws Exception {
		System.out.println("�̸��� ������");

		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("cnt", loginService.searchMember(email));
					
		return map;
	}
	
	//myInfo
	@GetMapping("/system/myInfo")
	public String myInfo() {
		return "system/myInfo";
	}
	
	//��й�ȣ ����(Ajax ��û)
	@RequestMapping("/system/putPassword.do")
	@ResponseBody
	public Map<Object, Object> putPassword(@RequestBody String password) throws Exception {
		System.out.println("��й�ȣ ����");

		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("cnt", loginService.putPassword(password));
					
		return map;
	}
	
	//ȸ��Ż�� �ϱ�(Ajax)
	@RequestMapping("/system/deleteMember.do")
	@ResponseBody
	public Map<Object, Object> deleteMember(){
		System.out.println("ȸ�� Ż��");
		
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("cnt", loginService.deleteMember());
					
		return map;
	}
	
	@GetMapping("/system/accessDenied")
	public void accessDenied() {}
	
	@GetMapping("/system/logout")
	public void logout() {
		System.out.println("�α׾ƿ�");
	}
	
	@GetMapping("/admin/adminPage")
	public void admin() {}
	
}
