package com.dodge.board.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.dodge.board.domain.Board;
import com.dodge.board.domain.Comment;
import com.dodge.board.domain.Recommendation;
import com.dodge.board.domain.Search;

public interface BoardService {
	//�Խ��� ����
	Board save(Board seq);
	
	int deleteBoard(Map<String, String> map);
	
	Board getBoard(Long seq);

	Page<Board> getBoardList(int pageNum, int size, Search search, int likeBoard);
	
	void insertBoard(MultipartFile mf, Board board) throws IllegalStateException, IOException;
	
	Map<Object,Object> updateRecommendation(Map<Object, Object> var, Recommendation re);

	int updateCheck(Map<Object, Object> map);
	
	Page<Board> getMyBoardList(int pageNum, int size, Search search);
	
	Page<Comment> getMyCommentList(int pageNum, int size, Search search);
	
	int deleteMyComment(Map<Object, Object> map);
	
	Page<Board> getLikeList(int pageNum, int size, Search search);
	
	int deleteRecommendation(Map<Object, Object> map);
	
	//��� ����

	int insertComment(Map<Object, Object> map, Comment comment);

	List<Comment> getCommentList(Map<Object, Object> map);

	int deleteComment(Map<Object, Object> map);

	int updateComment(Map<Object, Object> map);

	int insertReplyComment(Map<Object, Object> map, Comment comment);

	Page<Board> getBoardList(int pageNum, int size, Search search, int likeBoard, String sort);






}
