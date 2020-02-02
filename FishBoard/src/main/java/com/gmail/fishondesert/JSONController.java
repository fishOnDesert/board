package com.gmail.fishondesert;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gmail.fishondesert.service.UserService;

//JSON을 리턴할 수 있는 컨트롤러 
@RestController
public class JSONController {
	@Autowired
	private UserService userService;
	
	//email 중복검사 요청을 처리할 메소드
	@RequestMapping(value="user/emailcheck", method=RequestMethod.GET)
	public Map<String, Object> emailcheck(@RequestParam("email") String email){
		//email 체크 결과가 String으로 오는데 
		//JSON으로는 안 읽혀지므로 Map<String, Object>로 받아야 함
		Map<String, Object> map = new HashMap<String, Object>();
		String result = userService.emailcheck(email);
		//중복된 이메일이 없으면 result라는 키에 true를 저장하고, 중복되면 false를 저장함
		map.put("result", result == null); //기본값은 null
		return map;
	}
	
}
