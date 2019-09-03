package com.dodge.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodge.board.domain.Member;
import com.dodge.board.persistence.MemberRepository;

@Service("BasicLoginService")
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Autowired
	private EmailServiceImpl emailServiceImpl;
	
	@Override
	public void postMember(Member member) {
		memberRepo.save(member);
	}
	
	@Override
	public int idCheck(String id) {
		boolean result = memberRepo.existsById(id);
		if(result == true) { //아이디가 있으면
			return 1;
		}else {
			return 0;
		}
	}
	
	@Override
	public int emailCheck(String email) {
		List<Member> memberList = memberRepo.findByEmail(email);
		if(!memberList.isEmpty()) { //이메일이 비어있지 않다면
			return 1;
		}else {
			return 0;
		}
	}
	
	@Override
	public int searchMember(String email) {
		//1. 이메일 존재 여부 체크
		int result = emailCheck(email);
		
		if(result == 0) { //이메일이 없다면
			return 0;
		}else { //이메일이 있다면	
		//2. 이메일 있으니 회원정보 가져오기
			List<Member> memberList = memberRepo.findByEmail(email);
			Member member = memberList.get(0);
			String text = "회원님의 아이디와 비밀번호는 "+member.getId() + " / " +member.getPassword()+" 입니다";
			emailServiceImpl.sendSimpleMessage(email, "[닷지닷컴] 회원정보 입니다.", text);
			return 1;
		}
		
		
	}
}
