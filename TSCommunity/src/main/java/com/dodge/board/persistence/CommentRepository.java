package com.dodge.board.persistence;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.dodge.board.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> , QuerydslPredicateExecutor<Comment>{

	
	@Query("SELECT coalesce(max(b.c_seq) + 1,1) FROM Comment b") 
	Long getMaxC_Seq();
	
	@Query("SELECT b FROM Comment b WHERE b.b_seq = :b_seq ORDER BY b.originNo ASC, b.groupOrd ASC")
	List<Comment> getCommentList(@Param("b_seq")Long b_seq);
	
	@Query("SELECT count(b) FROM Comment b WHERE b.b_seq = :b_seq")
	Long getCommentCnt(@Param("b_seq")Long b_seq);
	
	
	@Transactional
	@Modifying
	@Query("UPDATE Comment b SET b.c_content = :c_content WHERE b.c_seq = :c_seq")
	void updateComment(@Param("c_seq")Long c_seq, @Param("c_content")String c_content);
	
	
	@Transactional
	@Modifying
	@Query("UPDATE Comment b SET b.groupOrd = b.groupOrd + 1 WHERE b.originNo = :originNo AND b.groupOrd >= :groupOrd")
	void updateGroupOrd(@Param("originNo")Long originNo, @Param("groupOrd")Long groupOrd);
	
}
