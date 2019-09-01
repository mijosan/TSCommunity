package com.dodge.board.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dodge.board.domain.Member;
import com.dodge.board.persistence.MemberRepository;

@Service
public class SecurityUserDetailsService implements UserDetailsService{
	
	@Autowired
	private MemberRepository memberRepo;
	
	//����� ���̵� �Ű������� �޾Ƽ� ȸ�������� ��ȸ�ϰ�, ��ȸ ����� UserDetails Ÿ���� ��ü�� ��ȯ�Ͽ� �����Ѵ�.
	//AuthencationProvider�� ���� (�̰��� ���� ����ڰ� �Է��� ��й�ȣ�� ���Ѵ�)
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { //request.getParameter("username")�� ��������
		Optional<Member> optional = memberRepo.findById(username);

		if(!optional.isPresent()) { //�˻���� ȸ�������� ������
			throw new UsernameNotFoundException(username + " ����� ����");
		}else { //�˻���� ȸ�������� ������ UserDetails ��ü�� ����
			Member member = optional.get();
			return new SecurityUser(member);//Member Ŭ������ UserDetails��ü�� �����ϱ� ���Ͽ� �̷�����(SecurityUser�� UserDetails�� ����� UserŬ������ ���)
		}
	}
}
