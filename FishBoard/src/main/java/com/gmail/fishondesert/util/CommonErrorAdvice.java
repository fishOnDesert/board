package com.gmail.fishondesert.util;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

//설정된 패키지에서 예외가 발생하면 동작하도록 해주는 설정
@ControllerAdvice("com.gmail.fishondesert")
public class CommonErrorAdvice {
	
	//예외 처리 메소드 
	//Exception이 동작하면 호출되는 메소드 
	@ExceptionHandler(Exception.class)
	public ModelAndView errorHanding(Exception ex) {
		ModelAndView mav = new ModelAndView();
		//에러 페이지 이름을 설정 
		mav.setViewName("/error/error");
		//전달할 데이터 설정
		mav.addObject("exception", ex);
		return mav; 
	}
	
}
