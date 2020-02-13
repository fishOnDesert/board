package com.gmail.fishondesert.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gmail.fishondesert.domain.Board;
import com.gmail.fishondesert.domain.SearchCriteria;

@Repository
public class BoardDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public int addPost(Board board) {
		//mapper 파일 board.xml에서 확인
		return sqlSession.insert("board.addPost", board);
	}
	//게시글 전체를 가져오는 메소드 
	public List<Board> list(){
		return sqlSession.selectList("board.list");
	}
	
	//데이터 개수를 가져오는 메소드 - 페이징 구현
	//board.xml에서 SQL 구문에서 확인  
	//resultType이 Integer니까 int 
	//parameterType 없으니까 매개변수 x 
	public int totalCount(SearchCriteria criteria) {
		return sqlSession.selectOne("board.totalCount");
	}
	
	//페이지 번호에 해당하는 데이터 목록을 가져오는 메소드 
/*	public List<Board> list(SearchCriteria cri){
//3디버깅
System.out.println("3디버깅-DAO의 criteria:" + cri);
		return sqlSession.selectList("board.list", cri);
	}
*/	
	//조회수를 1 증가시키는 메소드 
	public int updateReadcnt(int bno) {
		return sqlSession.update("board.updatereadcnt", bno);
	}
	
	//글번호로 1개의 데이터를 찾아오는 메소드 
	public Board detail(int bno) {
		return sqlSession.selectOne("board.detail", bno);
	}
	
	//글번호로 데이터를 수정하는 메소드 
	public int update(Board board) {
		return sqlSession.update("board.update", board);
	}
	
	//글번호로 데이터를 삭제하는 메소드 
	public int delete(int bno) {
		return sqlSession.delete("board.delete", bno);
	}
	
}
