package com.dodge.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.dodge.board.domain.Board;
import com.dodge.board.domain.QBoard;
import com.dodge.board.domain.Search;
import com.dodge.board.persistence.BoardRepository;
import com.querydsl.core.BooleanBuilder;

@Service("BasicBoardService")
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	private BoardRepository boardRepo;

	@Override
	public Page<Board> getBoardList(int pageNum, int size, Search search) {
		PageRequest pageRequest = PageRequest.of(pageNum-1, size, new Sort(new Order(Direction.DESC, "originNo"), new Order(Direction.ASC, "groupOrd")));
		
		BooleanBuilder builder = new BooleanBuilder();
		
		QBoard qBoard = QBoard.board;
		if(search.getSearchCondition().equals("TITLE")) {
			builder.and(qBoard.title.like("%" + search.getSearchKeyword() + "%"));
		}else if(search.getSearchCondition().equals("CONTENT")) {
			builder.and(qBoard.content.like("%" + search.getSearchKeyword() + "%")).or(qBoard.title.like("%" + search.getSearchKeyword() + "%"));
		}else if(search.getSearchCondition().equals("TITLEORCONTENT")) {
			builder.and(qBoard.content.like("%" + search.getSearchKeyword() + "%"));
		}else if(search.getSearchCondition().equals("WRITER")) {
			builder.and(qBoard.writer.like("%" + search.getSearchKeyword() + "%"));
		}
		
		Page<Board> boardList = boardRepo.findAll(builder,pageRequest);

		for(Board board : boardList) {
			String var = "";
			
			if(board.getGroupLayer() != null) {
				for(int i=0;i<board.getGroupLayer();i++) {
					var = var + "¡¡¡¡¡¡";
					if(i == board.getGroupLayer()-1) {
						var = var + "¦¦¦¡ ";
					}
				}
			}
			board.setTitle(var+board.getTitle());
		}
		return boardList;
	}
	@Override
	public Board getBoard(Long seq) {
		
		return null;
	}
	@Override
	public Board save(Board seq) {
		
		return null;
	}
	@Override
	public void deleteBoard(Long seq) {
		
	}
	
}
