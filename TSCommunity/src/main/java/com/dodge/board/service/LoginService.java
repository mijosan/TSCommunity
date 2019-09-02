package com.dodge.board.service;

import com.dodge.board.domain.Member;

public interface LoginService {
	void postMember(Member member);
	
	int idCheck(String id);
}
