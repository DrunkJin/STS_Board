package com.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.board.domain.BoardDTO;
import com.board.service.BoardService;

@Controller // 해당 클래스가 사용자의 요청과 응답을 처리하는 즉, UI를 담당하는 컨트롤러 클래스임을 의미함.
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping("/board/write.do")
	// RequestParam : 뷰에서 전달받은 파라미터를 처리하는 데 사용됨. required 기본값은 true이기 떄문에 false로 지정해주지 않으면 새로운 게시글을 작성할 때 idx값이 없어서 오류가 발생한다.
	public String openBoardWrite(@RequestParam(value="idx", required=false) Long idx, Model model) {
		
		if (idx == null) {
			model.addAttribute("board", new BoardDTO());
		} else {
				BoardDTO board = boardService.getBoardDetail(idx);
				if (board==null) {
						return "redirect:/board/list.do";
				}
				model.addAttribute("board", board);
		}
		return "board/write";
	} // if else 문을 통해서 idx를 받은 경우와 안받은 경우로 나뉘어서 새로운 게시글 등록과 수정 기능으로 나누어 주었다. 게시글 등록이라는 기능이 동일하기 때문에 하나로 처리하였다.
	
	@PostMapping("/board/register.do")
	public String registerBoard(final BoardDTO params) {
		try { boolean isRegistered = boardService.registerBoard(params);
				if (isRegistered == false) {
					// 게시글 등록에 실패했다는 메세지 기능 넣기
				}
		} catch (DataAccessException e) {
					// 데이터베이스 처리과정에 문제가 생겼다는 메세지 넣기
		} catch (Exception e) {
					// 시스템에 문제가 발생했다는 메세지 전달
		} return "redirect:/board/list.do";
	}
	
	@GetMapping(value="/board/list.do")
	public String openBoardList(Model model) {
		List<BoardDTO> boardList = boardService.getBoardList();
		model.addAttribute("boardList", boardList);
		
		return "board/list";
	}
	
	@GetMapping(value="/board/view.do")
	public String openBoardDetail(@RequestParam(value="idx", required=false) Long idx, Model model) {
			if(idx==null) {
					// 올바르지 않은 접근이라는 메세지를 전달하고 게시글 리스트로 리다이렉트
				return "redirect:/board/list.do";
			}
			BoardDTO board = boardService.getBoardDetail(idx);
			if (board == null || "Y".equals(board.getDeleteYn())) {
				 // 없는 게시글이거나 이미 삭제된 게시글이라는 메시지를 전달하고 게시글리스트로 리다이렉트
				return "redirect:/board/list.do";
			}
			model.addAttribute("board", board);
			return "board/view";
	}
	
	@PostMapping(value="/board/delete.do")
	public String deleteBoard(@RequestParam(value="idx", required=false) Long idx) {
		if (idx == null) {
					// 글번호가 없으니 올바르지 않은 접근이라는 메세지를 전달하고 게시글 리스트로 리다이렉트
			return "redirect:/board/list.do";
		} 
		try {
			boolean isDeleted = boardService.deleteBoard(idx);
			if(isDeleted ==false) {
				// 게시글 삭제 실패 메세지 보냄
			} 
		}catch (DataAccessException e) {
					// 데이터베이스 처리 과정에 문제가 발생했다고 전달
			} catch (Exception e) {
					// 시스템 문제가 발생했다는 메세지 전달
			}
			return "redirect:/board/list.do";
		}
	}



