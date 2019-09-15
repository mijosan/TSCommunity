package com.dodge.board.persistence;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.dodge.board.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> , QuerydslPredicateExecutor<Comment>{
	
	@Query("SELECT coalesce(max(b.c_seq) + 1,1) FROM Comment b") 
	Long getMaxC_Seq();
	
	@Query("SELECT b FROM Comment b WHERE b.b_seq = :b_seq")
	List<Comment> getCommentList(@Param("b_seq")Long b_seq);
	
	@Query("SELECT count(b) FROM Comment b WHERE b.b_seq = :b_seq")
	Long getCommentCnt(@Param("b_seq")Long b_seq);
}
