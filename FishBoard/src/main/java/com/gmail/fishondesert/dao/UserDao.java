package com.gmail.fishondesert.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gmail.fishondesert.domain.User;

@Repository
public class UserDao {
	
	@Autowired
	private SqlSession sqlSession; 
	
	//email 중복체크를 위한 메소드 
	public String emailcheck(String email) {
		//email은 pk기 때문에 selectOne으로 가져오기 - user.xml의 id
		return sqlSession.selectOne("user.emailcheck", email);
	}
	
	//nickname 중복체크를 위한 메소드 
	public String nicknamecheck(String nickname) {
		return sqlSession.selectOne("user.nicknamecheck", nickname);
	}
	
	//회원가입을 위한 메소드 
	public int signUp(User user) {
		return sqlSession.insert("user.signUp", user);
	}
	
	//로그인 처리를 위한 메소드 
	public User login(String email) {
		return sqlSession.selectOne("user.login", email);
	}
	
}
