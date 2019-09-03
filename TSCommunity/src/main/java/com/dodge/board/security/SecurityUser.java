package com.dodge.board.security;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import com.dodge.board.domain.Member;

public class SecurityUser extends User{
	private static final long serialVersionUID = 1L;
	private Member member;
	
	public SecurityUser(Member member) {//{noop}를 쓰게되면 암호화를 적용하지 않는다.
		super(member.getId(), member.getPassword(), AuthorityUtils.createAuthorityList(member.getRole().toString()));
		this.member = member;
	}
	
	public Member getMember() {
		return member;
	}
}
