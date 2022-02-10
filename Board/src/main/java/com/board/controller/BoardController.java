package com.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.board.service.BoardService;

@Controller // 해당 클래스가 사용자의 요청과 응답을 처리하는 즉, UI를 담당하는 컨트롤러 클래스임을 의미함.
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping("/board/write.do")
	public String openBoardWrite(Model model) {
		
		String title = "제목";
		String content = "내용";
		String writer = "작성자";
		
		model.addAttribute("t", title);
		model.addAttribute("c", content);
		model.addAttribute("w", writer);
		
		return "board/write";
	}
}
