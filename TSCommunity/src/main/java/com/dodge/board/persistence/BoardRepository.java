package com.dodge.board.persistence;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.dodge.board.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> , QuerydslPredicateExecutor<Board>{
	Board findBySeq(Long seq);

}
