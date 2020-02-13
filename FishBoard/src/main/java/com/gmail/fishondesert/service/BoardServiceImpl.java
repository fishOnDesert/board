package com.gmail.fishondesert.service;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gmail.fishondesert.dao.BoardDao;
import com.gmail.fishondesert.domain.Board;
import com.gmail.fishondesert.domain.PageMaker;
import com.gmail.fishondesert.domain.SearchCriteria;
import com.gmail.fishondesert.domain.User;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardDao boardDao;

	@Override
	public int addPost(HttpServletRequest request) {
		int result = -1; 
		
		//파라미터 읽기 
		try {
			request.setCharacterEncoding("utf-8");
			
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			//ip 주소 찾아오기 
			String ip = request.getRemoteAddr();
			//로그인 한 유저의 email 찾아오기 
			User user = (User) request.getSession().getAttribute("user");
			String email = user.getEmail();
			
			//Dao의 파라미터 만들기 
			Board board = new Board(); 
			if(title.trim().length() == 0) {
				title = "제목없음";
			}
			board.setTitle(title);
			if(content.trim().length() == 0) {
				content = "내용없음";
			}
			board.setContent(content);
			
			board.setIp(ip);
			board.setEmail(email);
			
			//Dao 메소드 호출 
			result = boardDao.addPost(board);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return result;
	}

	
	//페이징 없는 전체보기 메소드 
	@Override
	public List<Board> list() {
		List<Board> list = boardDao.list();
		
		Calendar cal = new GregorianCalendar();
		java.sql.Date today = new java.sql.Date(cal.getTimeInMillis());
		
		for(Board board : list) {
			String regdate = board.getRegdate().toString();

			if (today.toString().equals(regdate.substring(0, 10))) {
				board.setDispdate(regdate.substring(11, 16));
			} else {
				board.setDispdate(regdate.substring(5, 10));
			}

		}		
		return list;
	}

	
	@Override
	public Board detail(int bno) {
		Board board = null;
		//조회수 1 증가 
		int result = boardDao.updateReadcnt(bno);
		//조회수 1증가에 성공했으면 데이터 가져오기 
		if(result >= 0) {
			board = boardDao.detail(bno);
			board.setTitle(board.getTitle().trim());
		}
		return board;
	}

	@Override
	public int delete(int bno) {
		return boardDao.delete(bno);
	}

/*
	@Override
	public Map<String, Object> list(SearchCriteria criteria) {
		
		Map<String, Object> map = new HashMap<String, Object>(); 
//2디버깅
System.out.println("2디버깅-criteria:" + criteria);
		//페이지 번호에 해당하는 데이터 목록 가져오기 
		//키워드가 없으면 검색조건을 null로 해서 모든 데이터 가져오기 
		if(criteria.getKeyword() != null && criteria.getKeyword().trim().length() == 0) {
			criteria.setSearchType(null);
		}
		List<Board> list = boardDao.list(criteria); 
//4디버깅
System.out.println("4디버깅-list:" + list); 
		//데이터가 1개도 없다면 
		if(list.size() == 0) {
			criteria.setPage(criteria.getPage() - 1);
			list = boardDao.list(criteria);
		}
		
		Calendar cal = new GregorianCalendar(); 
		java.sql.Date today = new java.sql.Date(cal.getTimeInMillis());
		for(Board board : list) {
			String regdate = board.getRegdate().toString();
			board.setTitle(board.getTitle().trim());
			if(today.toString().equals(regdate.substring(0, 10))) {
				board.setDispdate(regdate.substring(11, 16));
			}else {
				board.setDispdate(regdate.substring(5, 10));
			}
			
		}
		//데이터 목록을 저장 
		map.put("list",  list);
		//출력할 페이지 번호 연산
		PageMaker pageMaker = new PageMaker(); 
		pageMaker.setCri(criteria);
		pageMaker.setTotalCount(boardDao.totalCount(criteria));
		map.put("pageMaker", pageMaker);
//5디버깅
System.out.println("5디버깅-service:" + pageMaker); 

		return map;
	}

*/


}
