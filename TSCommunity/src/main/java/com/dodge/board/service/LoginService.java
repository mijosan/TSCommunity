package com.dodge.board.service;

import com.dodge.board.domain.Member;

public interface LoginService {
	//ȸ������
	void postMember(Member member);
	
	//���̵� �ߺ�üũ
	int idCheck(String id);
	
	//�̸��� �ߺ�üũ
	int emailCheck(String email);
	
	//�̸��� ������
	int searchMember(String email);
}
