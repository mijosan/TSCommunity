package com.dodge.board.service;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
import com.dodge.board.domain.Comment;
import com.dodge.board.domain.QBoard;
import com.dodge.board.domain.Recommendation;
import com.dodge.board.domain.Search;
import com.dodge.board.persistence.BoardRepository;
import com.dodge.board.persistence.CommentRepository;
import com.dodge.board.persistence.RecommendationRepository;
import com.querydsl.core.BooleanBuilder;

@Service("BasicBoardService")
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	private BoardRepository boardRepo;
	
	@Autowired
	private RecommendationRepository reRepo;
	
	@Autowired
	private CommentRepository cmRepo;
	
	
	//���� ���
	@Override
	public int insertReplyComment(Map<Object, Object> map, Comment comment) {
		
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		comment.setC_seq(cmRepo.getMaxC_Seq());
		comment.setC_writer(user.getUsername());
		comment.setB_seq(Long.valueOf(String.valueOf(map.get("b_seq"))));
		comment.setC_content(String.valueOf(map.get("c_content")));
		
		comment.setOriginNo(Long.valueOf(String.valueOf(map.get("originNo"))));
		
		cmRepo.updateGroupOrd(comment.getOriginNo(), Long.valueOf(String.valueOf(map.get("groupOrd")))+1L);
		comment.setGroupOrd(Long.valueOf(String.valueOf(map.get("groupOrd")))+1);//OriginNo�� ������ �߿� max(ord) + 1
	
		comment.setGroupLayer(Long.valueOf(String.valueOf(map.get("groupLayer")))+1);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		
		comment.setC_createDate(format.format(date));
		cmRepo.save(comment);
		
		return 1;
	}
	//��� ���
	@Override
	public int insertComment(Map<Object, Object> map, Comment comment) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		comment.setC_seq(cmRepo.getMaxC_Seq());
		comment.setC_writer(user.getUsername());
		
		comment.setB_seq(Long.valueOf(String.valueOf(map.get("b_seq"))));
		comment.setC_content(String.valueOf(map.get("c_content")));
		comment.setOriginNo(comment.getC_seq());
		comment.setGroupOrd(0L);
		comment.setGroupLayer(0L);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		
		comment.setC_createDate(format.format(date));
		cmRepo.save(comment);
		
		return 1;
	}
	
	@Override
	public List<Comment> getCommentList(Map<Object, Object> map) {
		List<Comment> commentList = cmRepo.getCommentList(Long.valueOf(String.valueOf(map.get("b_seq"))));

		for(Comment comment : commentList) {
			//���� ������
			String var = "";
			
			if(comment.getGroupLayer() != null) {
				for(int i=0;i<comment.getGroupLayer();i++) {
					var = var + "������";
					if(i == comment.getGroupLayer()-1) {
						var = var + "���� ";
					}
				}
			}
			comment.setC_content(var+comment.getC_content());
		}
		return commentList;
	}
	
	
	@Override
	public int deleteComment(Map<Object, Object> map) {
		
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
			return 2;
		}else {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if(user.getUsername().equals(map.get("c_writer"))){
				cmRepo.deleteById(Long.valueOf(String.valueOf(map.get("c_seq"))));
				return 1;
			}else {
				return 3;
			}
		}
	}
	
	@Override
	public int updateComment(Map<Object, Object> map) {
		
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
			return 2;
		}else {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(user.getUsername().equals(map.get("c_writer"))){
				cmRepo.updateComment(Long.valueOf(String.valueOf(map.get("c_seq"))),String.valueOf(map.get("c_content")));
				return 1;
			}else {
				return 3;
			}
		}
	}
	
	
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
			//���� ������
			String var = "";
			
			if(board.getGroupLayer() != null) {
				for(int i=0;i<board.getGroupLayer();i++) {
					var = var + "������";
					if(i == board.getGroupLayer()-1) {
						var = var + "���� ";
					}
				}
			}
			board.setTitle(var+board.getTitle());
			
			//���ƿ� ���� �ֱ�
			board.setLikeCnt(Long.valueOf(reRepo.getRecommendationCnt(board.getSeq(), "like")));
			
			//��� ���� �ֱ�
			board.setC_cnt(cmRepo.getCommentCnt(board.getSeq()));
			
			//�ֱ� 7�� ��¥ ���� ����
			Date now = new Date(); //���� ��¥
			Date createDate = board.getCreateDate(); //���� ��¥

			long diff = now.getTime() - createDate.getTime();
			long diffDays = diff / (24 * 60 * 60 * 1000);
			
			if(diffDays<=7) {
				board.setNewDate("new");
			}
		}
		return boardList;
	}
	
	@Override
	public void insertBoard(MultipartFile mf, Board board) throws IllegalStateException, IOException {
		
		String SAVE_PATH="C:/Users/ChoiTaesan/git/TSCommunity/TSCommunity/src/main/resources/static/file/";
		
		//���� ���ε� ó��
  		if(!mf.isEmpty()) {
  			System.out.println("���� ���ε�");
  			String originalFileName = System.currentTimeMillis() + mf.getOriginalFilename();
  			
  			DecimalFormat formatter = new DecimalFormat("###,###");
  			String fileSize = formatter.format(mf.getSize())+"byte";
  			
  			String safeFile = SAVE_PATH + originalFileName; //���� ���ϸ��� ���ε��Ͽ��� �Ȱ�ħ
  			
  			board.setFileSize(fileSize);
  			board.setOriginalFileName(originalFileName);
  			board.setFileName(mf.getOriginalFilename());
  			mf.transferTo(new File(safeFile));
  		}
  		
  		//spring security session member�� ��������
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String user_id = user.getUsername();

  		board.setWriter(user_id);

  		if(board.getBoardCheck().hashCode() != 0) {

  			if(board.getBoardCheck().equals("reply")) { //��� �϶�
  	  			board.setOriginNo(board.getOriginNo());
  	  			board.setSeq(boardRepo.getMaxSeq());
  	  			boardRepo.updateGroupOrd(board.getOriginNo(), board.getGroupOrd()+1L);
  	  			board.setGroupOrd(board.getGroupOrd()+1);//OriginNo�� ������ �߿� max(ord) + 1
  	  			board.setGroupLayer(board.getGroupLayer()+1);//������ Layer + 1
  	  		}else if(board.getBoardCheck().equals("update")){ //������Ʈ �϶�
  	  			
  	  		}
  		}else {//�۾��� �϶�

  			board.setSeq(boardRepo.getMaxSeq());
  	  		board.setOriginNo(board.getSeq());
  	  		board.setGroupOrd(0L);
  	  		board.setGroupLayer(0L);
  		}	
  		
  		boardRepo.save(board);
	}
	
	@Override
	public Board getBoard(Long seq) {
		Board board = boardRepo.findBySeq(seq);
		
		//��ȸ�� ����
		board.setCnt(board.getCnt()+1);
		board.setC_cnt(cmRepo.getCommentCnt(seq));
		boardRepo.save(board);
		
		return board;
	}
	@Override
	public Board save(Board seq) {
		
		return null;
	}
	
	@Override
	public int deleteBoard(Map<String, String> var) {
		//�ۼ��� ��
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		String user_id = user.getUsername();
		
		if(var.get("writer").equals(user_id)) { //�ۼ��ڿ� �����ڰ� ������
			boardRepo.deleteById(Long.valueOf(var.get("seq")));
			reRepo.deleteB_seq(Long.valueOf(var.get("seq")));
			cmRepo.deleteB_seq(Long.valueOf(var.get("seq")));
			return 1;
		}else {
			return 0;
		}
	}
	
	@Override
	public int updateCheck(Map<Object, Object> map) {
		//�ۼ��� ��
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		String user_id = user.getUsername();

		if(map.get("writer").equals(user_id)) { //�ۼ��ڿ� �����ڰ� ������
			
			return 1;
		}else {
			return 0;
		}
	}
	
	//��õ
	@Override
	public Map<Object,Object> updateRecommendation(Map<Object, Object> map, Recommendation re) {
		
		Long b_seq = Long.valueOf(String.valueOf(map.get("b_seq"))); //�Խñ� ��ȣ
		String var = String.valueOf(map.get("re"));
		
		//ó�� ���� ����ϱ� ����
		if(var.equals("likeAnddisLike")) {
			map.put("likeCnt", reRepo.getRecommendationCnt(b_seq, "like"));
			map.put("disLikeCnt", reRepo.getRecommendationCnt(b_seq, "disLike"));
			return map;
		}
		//��õ��
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		String user_id = user.getUsername(); //��õ��
		
			
		if(reRepo.getRecommendation(b_seq, user_id, var) == null) {//��õ�� �����ʾ����� ��õ
			re.setId(user_id);
			re.setB_seq(b_seq);
			re.setRe(var);
			reRepo.save(re);
			map.put("cnt", 1);
			map.put("count", reRepo.getRecommendationCnt(b_seq, var));
			return map;
		}else { // ��õ�� �̹� ������ ��õ ���(���ڵ� ����)
			reRepo.deleteRecommendation(b_seq, user_id, var);
			map.put("cnt", 0);
			map.put("count", reRepo.getRecommendationCnt(b_seq, var));
			return map;
		}
		
	}
	
}
