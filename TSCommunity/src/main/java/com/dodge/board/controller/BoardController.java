package com.dodge.board.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dodge.board.domain.Search;
import com.dodge.board.service.BoardService;

@Controller
public class BoardController {
	
	@Resource(name="BasicBoardService")
	private BoardService boardService;
	
	@RequestMapping(value = {"/board/getBoardList", "/system/getBoardList"})
	public String getBoardList(Model model, @RequestParam(value="pageNum" , defaultValue="1")int pageNum, @RequestParam(value="size" , defaultValue="10")int size
			, Search search) {
		System.out.println("�Խ��� ����Ʈ");
		
		if(search.getSearchCondition() == null) {
			search.setSearchCondition("TITLE");
		}
		if(search.getSearchKeyword() == null) {
			search.setSearchKeyword("");
		}
		model.addAttribute("boardList", boardService.getBoardList(pageNum, size, search));
		
		//�˻����ǰ� �˻�� �����Ͽ� ����¡ ó���ϱ�����
		model.addAttribute("searchCondition", search.getSearchCondition());
		model.addAttribute("searchKeyword", search.getSearchKeyword());
		return "board/getBoardList";
	}
}
