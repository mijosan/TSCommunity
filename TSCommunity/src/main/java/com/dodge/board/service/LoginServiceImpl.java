package com.dodge.board.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodge.board.domain.Member;
import com.dodge.board.persistence.MemberRepository;

@Service("BasicLoginService")
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Override
	public void postMember(Member member) {
		memberRepo.save(member);
	}
	
	@Override
	public int idCheck(String id) {
		boolean result = memberRepo.existsById(id);
		if(result == true) {
			return 1;
		}else {
			return 0;
		}
	}
}
