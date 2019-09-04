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
		if(result == true) { //���̵� ������
			return 1;
		}else {
			return 0;
		}
	}
	
	@Override
	public int emailCheck(String email) {
		List<Member> memberList = memberRepo.findByEmail(email);
		if(!memberList.isEmpty()) { //�̸����� ������� �ʴٸ�
			return 1;
		}else {
			return 0;
		}
	}
	
	@Override
	public int searchMember(String email) {
		//1. �̸��� ���� ���� üũ
		int result = emailCheck(email);
		
		if(result == 0) { //�̸����� ���ٸ�
			return 0;
		}else { //�̸����� �ִٸ�	
		//2. �̸��� ������ �ӽú���� �����Ͽ� update�� ���ϸ�
			
			List<Member> memberList = memberRepo.findByEmail(email);
			Member member = memberList.get(0);
			
			Random random = new Random();
			int temp = random.nextInt(1000000);
			//�ӽ� ��� update
			member.setPassword(encoder.encode(String.valueOf(temp)));
			memberRepo.save(member);
			
			String text = "ȸ������ ���̵�� ��й�ȣ�� "+member.getId() + " / " +temp+" �Դϴ�. �� ȸ�� ��й�ȣ�� �����ϼ���.";
			emailServiceImpl.sendSimpleMessage(email, "[��������] ȸ������ �Դϴ�.", text);
			return 1;
		}
	}
	
	//��й�ȣ ����
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
	
	//ȸ�� Ż��
	@Override
	public int deleteMember() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String user_id = user.getUsername();
		memberRepo.deleteById(user_id);
		
		return 1;
	}
}
