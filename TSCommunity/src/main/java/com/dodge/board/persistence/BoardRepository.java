package com.dodge.board.persistence;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.dodge.board.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> , QuerydslPredicateExecutor<Board>{
	Board findBySeq(Long seq);
	
	
	@Query("SELECT coalesce(max(b.seq) + 1,1) FROM Board b") 
	Long getMaxSeq();

	@Transactional
	@Modifying
	@Query("UPDATE Board b SET b.groupOrd = b.groupOrd + 1 WHERE b.originNo = :originNo AND b.groupOrd >= :groupOrd")
	void updateGroupOrd(@Param("originNo")Long originNo, @Param("groupOrd")Long groupOrd);

}
