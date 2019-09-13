package com.dodge.board.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dodge.board.domain.Board;
import com.dodge.board.domain.Recommendation;
import com.dodge.board.domain.Search;
import com.dodge.board.service.BoardService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class BoardController implements ApplicationContextAware{
	
	@Resource(name="BasicBoardService")
	private BoardService boardService;
	
	private WebApplicationContext context = null;
	
	
	
	@RequestMapping(value = {"/board/getBoardList", "/system/getBoardList", "getBoardList"})
	public String getBoardList(Model model, @RequestParam(value="pageNum" , defaultValue="1")int pageNum, @RequestParam(value="size" , defaultValue="10")int size
			, Search search) {
		System.out.println("�Խ��� ����Ʈ");
		
		if(search.getSearchCondition() == null) {
			search.setSearchCondition("TITLE");
		}
		if(search.getSearchKeyword() == null) {
			search.setSearchKeyword("");
		}
		Page<Board> boardList = boardService.getBoardList(pageNum, size, search);
		model.addAttribute("boardList", boardList);
		//�˻����ǰ� �˻�� �����Ͽ� ����¡ ó���ϱ�����
		model.addAttribute("searchCondition", search.getSearchCondition());
		model.addAttribute("searchKeyword", search.getSearchKeyword());
		
		return "board/getBoardList";
	}
	
	//�۾��� ��ư Ŭ��
	@GetMapping("/board/insertBoard")
	public String insertBoard(Model model, Board board) {
		model.addAttribute("board", board); //��� ������ ���� ������
		return "/board/insertBoard";
	}
	
	@PostMapping(value= {"/insertBoard","/board/insertBoard"})
	public String insertBoard(@RequestParam(value="uploadFile", required = false) MultipartFile mf, Board board) throws IllegalStateException, IOException {
		System.out.println("�Խñ� ���");

		boardService.insertBoard(mf, board);
		return "redirect:getBoardList";
	}
	
	@RequestMapping("/board/getBoard")
	public String getBoard(Model model, Board board) {
		System.out.println("�� �б�");
		
		model.addAttribute("board", boardService.getBoard(board.getSeq()));

		return "board/getBoard";
	}
	
	@RequestMapping("/board/deleteBoard")
	@ResponseBody
	public Map<Object, Object> deleteBoard(@RequestBody Map<String, String> var) {
		System.out.println("�Խñ� ����");
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("cnt", boardService.deleteBoard(var));
		
		return map;
	}
	
	@RequestMapping("/board/updateRecommendation")
	@ResponseBody
	public Map<Object, Object> updateRecommendation(@RequestBody Map<Object, Object> var, Recommendation re) {
		System.out.println("�Խñ� ��õ");
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("map", boardService.updateRecommendation(var, re));
		
		return map;
		
	}
	
	@RequestMapping("/board/updateCheck")
	@ResponseBody
	public Map<Object, Object> updateCheck(@RequestBody Map<Object, Object> map) {
		System.out.println("�Խñ� ���� üũ");
		
		map.put("cnt", boardService.updateCheck(map));
		map.put("seq", map.get("seq"));

		return map;
	}
	
	@RequestMapping("/board/updateBoard")
	public String updateBoard(Model model, Board board) {
		System.out.println("�Խñ� ���� ������ �ѱ��");

		model.addAttribute("board", board);

		return "/board/updateBoard";
	}
	//���� �ٿ�ε�
	@RequestMapping("board/download.do")
	public ModelAndView download(HttpServletRequest request, ModelAndView mv){
		String SAVE_PATH = "C:/Users/ChoiTaesan/git/TSCommunity/TSCommunity/src/main/resources/static/file/";
		String fullPath = SAVE_PATH+request.getParameter("originalFileName");
	
		File file = new File(fullPath);
		return new ModelAndView("download", "downloadFile", file);
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		
		this.context = (WebApplicationContext)arg0;
	}
	
	// �������Ͼ��ε� (����)
    @RequestMapping(value = "/file_uploader_html5.do",
                  method = RequestMethod.POST)
    @ResponseBody
    public String multiplePhotoUpload(HttpServletRequest request) {
        // ��������
        StringBuffer sb = new StringBuffer();
        try {
            // ���ϸ��� �޴´� - �Ϲ� �������ϸ�
            String oldName = request.getHeader("file-name");
            // ���� �⺻��� _ �󼼰��
            String filePath = "C:/Users/ChoiTaesan/git/TSCommunity/TSCommunity/src/main/resources/static/editor/photoUpload/";
            String saveName = sb.append(new SimpleDateFormat("yyyyMMddHHmmss")
                          .format(System.currentTimeMillis()))
                          .append(UUID.randomUUID().toString())
                          .append(oldName.substring(oldName.lastIndexOf("."))).toString();
            InputStream is = request.getInputStream();
            OutputStream os = new FileOutputStream(filePath + saveName);
            int numRead;
            byte b[] = new byte[Integer.parseInt(request.getHeader("file-size"))];
            while ((numRead = is.read(b, 0, b.length)) != -1) {
                os.write(b, 0, numRead);
            }
            os.flush();
            os.close();
            // ���� ���
            sb = new StringBuffer();
            sb.append("&bNewLine=true")
              .append("&sFileName=").append(oldName)
              .append("&sFileURL=").append("http://localhost:8080/editor/photoUpload/")
                                                  
        .append(saveName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
