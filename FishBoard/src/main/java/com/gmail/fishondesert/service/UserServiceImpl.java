package com.gmail.fishondesert.service;

import java.io.File;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gmail.fishondesert.dao.UserDao;
import com.gmail.fishondesert.domain.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;

	@Override
	public String emailcheck(String email) {
		return userDao.emailcheck(email);
	}

	@Override
	public String nicknamecheck(String nickname) {
		return userDao.nicknamecheck(nickname);
	}

	@Override
	public int signUp(MultipartHttpServletRequest request) {
		int result = -1;
		
		try {
			//1. 파라미터 인코딩 설정
			request.setCharacterEncoding("utf-8");
			//2. 파라미터 읽기 
			String email = request.getParameter("email");
			String pw = request.getParameter("pw");
			String nickname = request.getParameter("nickname");
			MultipartFile image = request.getFile("image");
			
			//파일을 업로드할 경로 설정 
			//경로 기준은 webapp 또는 WebContent
			String uploadPath = request.getServletContext().getRealPath("/userimage");
			//파일명이 중복되지 않도록 랜덤 문자열 생성 
			UUID uid = UUID.randomUUID();
			//원본 파일 이름 가져오기 
			String filename = image.getOriginalFilename();
			//DAO의 파라미터 만들기 
			User user = new User(); 
			//선택한 파일이 있는 경우 
			if(filename.length() > 0) {
				//파일 이름 만들기 
				filename = uid + "_" + filename;
				//실제 업로드 할 경로 만들기 
				String filepath = uploadPath + "/" + filename;
				//파일 업로드 
				File file = new File(filepath);
				image.transferTo(file);
			}else {
				filename = "default.png";
			}
			//3. DAO 파라미터 만들기 
			user.setImage(filename);
			user.setEmail(email);
			user.setNickname(nickname);
			//비밀번호는 암호화해서 저장하기 
			user.setPw(BCrypt.hashpw(pw, BCrypt.gensalt(10)));
			
			//4. DAO 메소드 호출 
			result = userDao.signUp(user);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public User login(HttpServletRequest request) {
		// request로 하는 경우, 참조형인 경우는 null을 기본값으로 설정
		User user = null;

		try {
			request.setCharacterEncoding("utf-8");
			String email = request.getParameter("email");
			String pw = request.getParameter("pw");
			// 로그인 관련된 DAO 메소드호출
			user = userDao.login(email);
			// email에 해당하는 데이터가 있다면
			if (user != null) {
				// 암호화된 비밀번호를 비교
				// 비밀번호가 일치하면 로그인성공
				// 그렇지 않으면 로그인 실패
				if (BCrypt.checkpw(pw, user.getPw())) {
					user.setPw(null);
				} else {
					user = null;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return user;
	}


	
	
}
