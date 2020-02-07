package com.gmail.fishondesert.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gmail.fishondesert.domain.Board;

@Repository
public class BoardDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public int addPost(Board board) {
		//mapper 파일 board.xml에서 확인
		return sqlSession.insert("board.addPost", board);
	}
}
