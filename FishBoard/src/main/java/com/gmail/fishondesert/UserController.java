package com.gmail.fishondesert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gmail.fishondesert.domain.User;
import com.gmail.fishondesert.service.UserService;



//컨트롤러로 만들기 위한 어노테이션
@Controller 
public class UserController {
	//회원가입 페이지 요청을 처리하는 메소드 
	@RequestMapping(value="user/signUp", method=RequestMethod.GET)
	public String signUp(Model model) {
		return "user/signUp";
	}
	
	@Autowired
	private UserService userService; 
	//회원가입 요청을 처리하는 메소드 
	@RequestMapping(value="user/signUp", method=RequestMethod.POST)
	public String signUp(Model model, 
						MultipartHttpServletRequest request, 
						RedirectAttributes attr) {
		
		int result = userService.signUp(request);
		//회원가입에 성공한 경우
		if(result > 0) {
			attr.addFlashAttribute("insert", "success");
			return "redirect:/";
		}else {
			return "redirect:signUp";
		}
	}
	
	//home.jsp에서 login 요청이 왔을 때 처리하는 메소드 
	@RequestMapping(value = "user/login", method = RequestMethod.GET)
	public String login(Model model) {
		return "user/login";
	}
	
	// home.jsp에서 login 요청이 왔을 때 처리하는 메소드
	@RequestMapping(value = "user/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, 
						HttpSession session, 
						Model model, 
						RedirectAttributes attr) {
		User user = userService.login(request);
		// 로그인 실패하는 경우
		if (user == null) {
			attr.addFlashAttribute("msg", "없는 이메일이거나 잘못된 비밀번호입니다");
			return "redirect:login";
		} else {
			// 로그인에 성공했을 때는 로그인 정보를 세션에 저장
			session.setAttribute("user", user);
			if (session.getAttribute("dest") == null) {
				return "redirect:/";
			} else {
				return "redirect:/" + session.getAttribute("dest").toString();
			}
		}
	}
	
	//로그아웃 처리를 위한 메소드 
	@RequestMapping(value = "user/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		// 세션 초기화
		session.invalidate();
		return "redirect:/";
	}
}
