package com.gmail.fishondesert.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gmail.fishondesert.dao.BoardDao;
import com.gmail.fishondesert.domain.Board;
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



}
