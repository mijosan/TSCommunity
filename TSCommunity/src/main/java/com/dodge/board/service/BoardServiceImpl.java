package com.dodge.board.service;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dodge.board.domain.Board;
import com.dodge.board.domain.QBoard;
import com.dodge.board.domain.Recommendation;
import com.dodge.board.domain.Search;
import com.dodge.board.persistence.BoardRepository;
import com.dodge.board.persistence.RecommendationRepository;
import com.querydsl.core.BooleanBuilder;

@Service("BasicBoardService")
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	private BoardRepository boardRepo;
	
	@Autowired
	private RecommendationRepository reRepo;
	
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
					var = var + "　　　";
					if(i == board.getGroupLayer()-1) {
						var = var + "└─ ";
					}
				}
			}
			board.setTitle(var+board.getTitle());
		}
		return boardList;
	}
	
	@Override
	public void insertBoard(MultipartFile mf, Board board) throws IllegalStateException, IOException {
		
		String SAVE_PATH="C:/Users/ChoiTaesan/git/TSCommunity/TSCommunity/src/main/resources/static/file/";
		
		//파일 업로드 처리
  		if(!mf.isEmpty()) {
  			System.out.println("파일 업로드");
  			String originalFileName = System.currentTimeMillis() + mf.getOriginalFilename();
  			
  			DecimalFormat formatter = new DecimalFormat("###,###");
  			String fileSize = formatter.format(mf.getSize())+"byte";
  			
  			String safeFile = SAVE_PATH + originalFileName; //같은 파일명을 업로드하여도 안겹침
  			
  			board.setFileSize(fileSize);
  			board.setOriginalFileName(originalFileName);
  			board.setFileName(mf.getOriginalFilename());
  			mf.transferTo(new File(safeFile));
  		}
  		
  		//spring security session member값 가져오기
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String user_id = user.getUsername();

  		board.setWriter(user_id);
  		
  		//글쓰기 일때
  		board.setOriginNo(boardRepo.getMaxSeq());
  		board.setGroupOrd(0L);
  		board.setGroupLayer(0L);
  		
  		boardRepo.save(board);
	}
	@Override
	public Board getBoard(Long seq) {
		Board board = boardRepo.findBySeq(seq);
		
		//조회수 증가
		board.setCnt(board.getCnt()+1);
		boardRepo.save(board);
		
		return board;
	}
	@Override
	public Board save(Board seq) {
		
		return null;
	}
	
	@Override
	public int deleteBoard(Map<String, String> var) {
		//작성자 비교
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		String user_id = user.getUsername();
		
		if(var.get("writer").equals(user_id)) { //작성자와 접속자가 같을때
			boardRepo.deleteById(Long.valueOf(var.get("seq")));
			return 1;
		}else {
			return 0;
		}
	}
	
	//추천
	@Override
	public Map<Object,Object> updateRecommendation(Map<Object, Object> map, Recommendation re) {
		
		Long b_seq = Long.valueOf(String.valueOf(map.get("b_seq"))); //게시글 번호
		String var = String.valueOf(map.get("re"));
		
		//처음 갯수 출력하기 위해
		if(var.equals("likeAnddisLike")) {
			map.put("likeCnt", reRepo.getRecommendationCnt(b_seq, "like"));
			map.put("disLikeCnt", reRepo.getRecommendationCnt(b_seq, "disLike"));
			return map;
		}
		//추천자
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		String user_id = user.getUsername(); //추천자
		
		
		
			
		if(reRepo.getRecommendation(b_seq, user_id, var) == null) {//추천을 하지않았으면 추천
			re.setId(user_id);
			re.setB_seq(b_seq);
			re.setRe(var);
			reRepo.save(re);
			map.put("cnt", 1);
			map.put("count", reRepo.getRecommendationCnt(b_seq, var));
			return map;
		}else { // 추천을 이미 했을때 추천 취소(레코드 삭제)
			reRepo.deleteRecommendation(b_seq, user_id, var);
			map.put("cnt", 0);
			map.put("count", reRepo.getRecommendationCnt(b_seq, var));
			return map;
		}
		
	}
	
}
