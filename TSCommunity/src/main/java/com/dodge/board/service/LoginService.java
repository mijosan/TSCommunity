package com.dodge.board.service;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.dodge.board.domain.Member;
import com.dodge.board.security.SecurityUser;

public interface LoginService {
	//회원가입
	void postMember(Member member);
	
	//아이디 중복체크
	int idCheck(String id);
	
	//이메일 중복체크
	int emailCheck(String email);
	
	//이메일 보내기
	int searchMember(String email);
	
	//비밀번호 변경
	int putPassword(String password);
}
