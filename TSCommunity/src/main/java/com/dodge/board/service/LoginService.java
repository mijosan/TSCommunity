package com.dodge.board.service;

import com.dodge.board.domain.Member;

public interface LoginService {
	//회원가입
	void postMember(Member member);
	
	//아이디 중복체크
	int idCheck(String id);
	
	//이메일 중복체크
	int emailCheck(String email);
	
	//이메일 보내기
	int searchMember(String email);
}
