package com.dodge.board.service;

import org.springframework.data.domain.Page;

import com.dodge.board.domain.Board;
import com.dodge.board.domain.Search;

public interface BoardService {
	Board save(Board seq);
	
	void deleteBoard(Long seq);
	
	Board getBoard(Long seq);

	Page<Board> getBoardList(int pageNum, int size, Search search);
	
}
