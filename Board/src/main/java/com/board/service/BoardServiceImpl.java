package com.board.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.board.domain.BoardDTO;
import com.board.mapper.BoardMapper;

// Mapper와 유사하며 비즈니스 로직을 담당하는 서비스 클래스임을 의미함
@Service
// implements : BoardService에 정의한 메서드를 구현하기 위해서 상속받음 (참고 블로그 : https://multifrontgarden.tistory.com/97)
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Override
	public boolean registerBoard(BoardDTO params) {
		// 쿼리가 정상실행되면 queryResult가 1이 들어가게 됨
		// 파라미터를 받아왔을때 idx값이 null. 즉 없다면 게시판 생성. 있다면 수정.
		int queryResult = 0;
		
		if (params.getIdx() == null) {
			queryResult = boardMapper.insertBoard(params);
		} else {
			queryResult = boardMapper.updateBoard(params);
		}
		return (queryResult == 1) ? true :false; // 쿼리 실행 결과 기준으로 true 또는 false 반환
	}

	@Override
	public BoardDTO getBoardDetail(Long idx) {
		return boardMapper.selectBoardDetail(idx);
	}

	@Override
	public boolean deleteBoard(Long idx) {
		int queryResult = 0;
		
		BoardDTO board = boardMapper.selectBoardDetail(idx);
		// 게시글이 없거나 delete_yn이 "Y"인 경우에는 실행되지 않음. 마찬가지로 쿼리 실행결과를 기준으로 true or false 반환
		if (board !=null && "N".equals(board.getDeleteYn())) { 
			queryResult = boardMapper.deleteBoard(idx);
		}
		return (queryResult ==1) ? true : false; 
	}

	@Override
	public List<BoardDTO> getBoardList() {
		List<BoardDTO> boardList = Collections.emptyList();
		
		int boardTotalCount = boardMapper.selectBoardTotalCount();
		
		if (boardTotalCount > 0 ) {
			boardList = boardMapper.selectBoardList();
		}
		return boardList;
	}

}
