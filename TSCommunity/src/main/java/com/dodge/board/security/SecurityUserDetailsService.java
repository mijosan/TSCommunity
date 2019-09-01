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
	
	//사용자 아이디를 매개변수로 받아서 회원정보를 조회하고, 조회 결과를 UserDetails 타입의 객체로 변환하여 리턴한다.
	//AuthencationProvider로 리턴 (이것을 통해 사용자가 입력한 비밀번호와 비교한다)
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { //request.getParameter("username")과 같은역할
		Optional<Member> optional = memberRepo.findById(username);
		System.out.println("하이");
		if(!optional.isPresent()) { //검색결과 회원정보가 없을때
			throw new UsernameNotFoundException(username + " 사용자 없음");
		}else { //검색결과 회원정보가 있을때 UserDetails 객체를 리턴
			Member member = optional.get();
			return new SecurityUser(member);//Member 클래스를 UserDetails객체로 리턴하기 위하여 이렇게함(SecurityUser는 UserDetails를 상속한 User클래스를 상속)
		}
	}
}
