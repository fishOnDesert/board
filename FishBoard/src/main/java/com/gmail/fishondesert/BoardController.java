package com.gmail.fishondesert;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gmail.fishondesert.domain.Board;
import com.gmail.fishondesert.domain.SearchCriteria;
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

	
	@RequestMapping(value="list", method=RequestMethod.GET)
	public String list(Model model) {
		List<Board> list = boardService.list();
		model.addAttribute("list",list);
		return "board/list";
	}
	
/*
	//전체보기 - 페이징 처리 list
	@RequestMapping(value="list", method=RequestMethod.GET)
	public String list(
			//파라미터를 criteria로 받아서 사용하고 결과페이지에 cri라는 이름으로 넘겨주는 설정
			@ModelAttribute("cri") SearchCriteria criteria, Model model) {
//1.디버깅
System.out.println("1디버깅-controller:" + criteria);
		Map<String, Object> map = boardService.list(criteria);
		model.addAttribute("map", map);
//6.디버깅
System.out.println("6디버깅-map:" + map);
		return "board/list";
	}
*/
	
	@RequestMapping(value="detail/{bno}", method=RequestMethod.GET)
	public String detail(@PathVariable("bno") int bno, 
						Model model) {
		Board vo = boardService.detail(bno);
		model.addAttribute("vo",vo);
		return "board/detail";
	}

	@RequestMapping(value="delete/{bno}", method=RequestMethod.GET)
	public String delete(@PathVariable("bno") int bno, 
						Model model,
						RedirectAttributes attr) {
		
		int result = boardService.delete(bno);
		
		if (result > 0) {
			attr.addFlashAttribute("msg", "삭제성공");
			return "redirect:/board/list";
		} else {
			return "redirect:/board/update";
		}
	}
	
	
}
