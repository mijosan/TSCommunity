package com.dodge.board.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.dodge.board.domain.Member;

public interface MemberRepository extends CrudRepository<Member, String>{
	List<Member> findByEmail(String email);
	
}
