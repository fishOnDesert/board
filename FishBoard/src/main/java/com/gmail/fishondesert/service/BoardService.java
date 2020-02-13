package com.gmail.fishondesert.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.gmail.fishondesert.domain.Board;
import com.gmail.fishondesert.domain.SearchCriteria;

public interface BoardService {
	//게시글 작성을 위한 메소드
	public int addPost(HttpServletRequest request);
	
	public List<Board> list();

	//페이지 번호에 해당하는 데이터 목록을 가져오는 메소드
	//데이터 목록 외에도 다른 데이터를 리턴해야 하기 때문에 리턴 타입을 변경
	//public Map<String, Object> list(SearchCriteria criteria);
	
	//상세보기
	//dao 작업은 2개지만 사용자가 요청한 건 1개니까 service도 1개
	public Board detail(int bno);
	
	//게시글 삭제를 위한 메소드 
	public int delete(int bno);
	
	//게시글 수정을 위한 메소드 
	public Board updateView(int bno);
	
	//게시글 수정을 위한 메소드
	public int update(Board board);


}
