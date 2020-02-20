package com.gmail.fishondesert.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
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

	@Override
	public String convertAddress(String param) {
		String address = null; 
		
		//파라미터 읽기 
		//param의 구성 - latitude:longitude
		String [] coords = param.split(":");
		String latitude = coords[0];
		String longitude = coords[1];
		//데이터를 다운로드 받을 URL을 생성 
		String addr = "https://dapi.kakao.com/v2/local/geo/coord2address.json?x="
				+ longitude + "&y=" 
				+ latitude + "&input_coord=WGS84";
		//다운로드 받은 문자열을 저장할 객체 
		StringBuilder sb = new StringBuilder(); 
		try {
			//다운로드 받을 주고를 URL 객체로 생성 
			URL url = new URL(addr); 
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(30000);
			con.setUseCaches(false);
			con.addRequestProperty("Authorization", "KakaoAK 21a9071667119f1741d1811d0749ef7c");
			
			//스트림 가져오기 
			InputStreamReader isr = new InputStreamReader(con.getInputStream());
			BufferedReader br = new BufferedReader(isr);
			//문자열을 읽어서 sb에 저장 
			while(true) {
				String line = br.readLine();
				if(line == null) {
					break;
				}
				sb.append(line);
			}
			String json = sb.toString();
			//카카오에서 주는 데이터의 시작이 {이므로 JSON 객체로 변환
			JSONObject root = new JSONObject(json);
//디버깅2. 
System.out.println("디버깅2-root:" + root);
			
			JSONArray documents = root.getJSONArray("documents");
			if(documents.length() > 0) {
				JSONObject first = documents.getJSONObject(0);
//디버깅3.
System.out.println("디버깅3-documents:" + documents);
			JSONObject roadaddress = first.getJSONObject("road_address");
//디버깅4. 
System.out.println("디버깅4-roadaddress:" + roadaddress);
			address = roadaddress.getString("address_name");
//디버깅5. 
System.out.println("address:" + address);
			}
			isr.close();
			br.close();
			con.disconnect();
		}catch(Exception e) {
			System.out.println("주소 가져오기 에러:" + e.getMessage());
		}
		return address;
	}


	
	
}
