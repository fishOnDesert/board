package com.gmail.fishondesert;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gmail.fishondesert.service.BoardService;

@Controller
@RequestMapping("/board/")
public class BoardController {
	
	@RequestMapping(value="addPost", method=RequestMethod.GET)
	public String addPost(Model model) {
		return "board/addPost";
	}
	
	@Autowired
	private BoardService boardService; 
		
	@RequestMapping(value="addPost", method=RequestMethod.POST)
	public String addPost(Model model, 
						HttpServletRequest request, 
						RedirectAttributes attr) {
		
		int result = boardService.addPost(request);
		//포스팅에 성공한다면 board/list로 리다이렉트
		if(result > 0) {
			attr.addFlashAttribute("mag", "게시글이 등록됐습니다.");
			return "redirect:list";
		}else {
			attr.addFlashAttribute("mag", "게시글 등록에 실패했습니다.");
			return "redirect:addPost";
		}
	}
	
	
}
