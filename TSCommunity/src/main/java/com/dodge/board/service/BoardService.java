package com.dodge.board.service;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.dodge.board.domain.Board;
import com.dodge.board.domain.Search;

public interface BoardService {
	Board save(Board seq);
	
	void deleteBoard(Long seq);
	
	Board getBoard(Long seq);

	Page<Board> getBoardList(int pageNum, int size, Search search);
	
	void insertBoard(MultipartFile mf, Board board) throws IllegalStateException, IOException;
	
	
}
