package com.dodge.board.service;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.dodge.board.domain.Member;
import com.dodge.board.security.SecurityUser;

public interface LoginService {
	//ȸ������
	void postMember(Member member);
	
	//���̵� �ߺ�üũ
	int idCheck(String id);
	
	//�̸��� �ߺ�üũ
	int emailCheck(String email);
	
	//�̸��� ������
	int searchMember(String email);
	
	//��й�ȣ ����
	int putPassword(String password);
	
	//ȸ��Ż��
	int deleteMember();
}
