package com.dodge.board.persistence;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.dodge.board.domain.Recommendation;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long>{
	@Transactional
	@Modifying
	@Query("DELETE FROM Recommendation b WHERE b.b_seq = :b_seq")
	void deleteB_seq(@Param("b_seq")Long b_seq);
	
	
	@Query("SELECT b FROM Recommendation b WHERE b.b_seq = :b_seq AND b.id = :id AND b.re = :re")
	Recommendation getRecommendation(@Param("b_seq")Long b_seq, @Param("id")String id, @Param("re")String re);
	
	@Query("SELECT count(b) FROM Recommendation b WHERE b.b_seq = :b_seq AND b.re = :re")
	int getRecommendationCnt(@Param("b_seq")Long b_seq, @Param("re")String re);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM Recommendation b WHERE b.b_seq = :b_seq AND b.id = :id AND b.re = :re")
	void deleteRecommendation(@Param("b_seq")Long b_seq, @Param("id")String id, @Param("re")String re);
	
	
	
}
