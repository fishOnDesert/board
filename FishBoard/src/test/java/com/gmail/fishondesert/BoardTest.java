package com.gmail.fishondesert;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class BoardTest {

	//테스트 할 객체 주입
	@Autowired
	private SqlSession sqlSession;
	
	//테스트 할 메소드 
	@Test
	public void mybatisTest() {
		System.out.println("mybatis:" + sqlSession);
	}
}
