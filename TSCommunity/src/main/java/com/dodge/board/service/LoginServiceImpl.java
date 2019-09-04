package com.dodge.board.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dodge.board.domain.Member;
import com.dodge.board.persistence.MemberRepository;

@Service("BasicLoginService")
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Autowired
	private EmailServiceImpl emailServiceImpl;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	public void postMember(Member member) {
		member.setPassword(encoder.encode(member.getPassword()));
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
		//2. 이메일 있으면 임시비번을 생성하여 update후 메일링
			
			List<Member> memberList = memberRepo.findByEmail(email);
			Member member = memberList.get(0);
			
			Random random = new Random();
			int temp = random.nextInt(1000000);
			//임시 비번 update
			member.setPassword(encoder.encode(String.valueOf(temp)));
			memberRepo.save(member);
			
			String text = "회원님의 아이디와 비밀번호는 "+member.getId() + " / " +temp+" 입니다. 꼭 회원 비밀번호를 변경하세요.";
			emailServiceImpl.sendSimpleMessage(email, "[닷지닷컴] 회원정보 입니다.", text);
			return 1;
		}
	}
	
	//비밀번호 변경
	@Override
	public int putPassword(String password) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String user_id = user.getUsername();
		Optional<Member> memberList = memberRepo.findById(user_id);
		
		Member member = memberList.get();
		member.setPassword(encoder.encode(password));
		memberRepo.save(member);
		return 1;
	}
	
	//회원 탈퇴
	@Override
	public int deleteMember() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String user_id = user.getUsername();
		memberRepo.deleteById(user_id);
		
		return 1;
	}
}
