package com.dodge.board.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.dodge.board.domain.Member;

public interface MemberRepository extends CrudRepository<Member, String>{
	List<Member> findByEmail(String email);
}
