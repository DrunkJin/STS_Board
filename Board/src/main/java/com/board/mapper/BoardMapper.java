package com.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.board.domain.BoardDTO;

@Mapper
// 기존의 스프링은 DAO클래스에 Repository를 선언해서 해당 클래스가 DB와 통신하는 클래스임을 나타내곤 했지만 마이바티스는 인터페이스에 Mapper만 지정하면 XML Mapper에서 메서드의 이름과 일치하는 SQL문을 찾아 실행함
// Mapper의 영역은 DB와의 통신 즉, SQL 쿼리를 호출하는 것이 전부임.
public interface BoardMapper {
	
	public int insertBoard(BoardDTO params);
	// insert쿼리를 호출하는 메서드로 params에 게시글의 정보가 담김
	
	public BoardDTO selectBoardDetail(Long idx);
	// select쿼리가 실행되면 각 컬럼에 해당하는 결과값이 리턴타입으로 지정된 BoardDTO 클래스의 멤버 변수에 매핑. 파라미터로 PK인 게시글번호(idx)가 전달받고 where절 조건으로 idx를 통해 특정 게시글을 조회함.
	
	public int updateBoard(BoardDTO params);
	// insert와 마찬가지로 params에 게시글 정보가 담기게 됨.
	
	public int deleteBoard(Long idx);
	// 실제로 데이터 삭제가 아닌 delete_yn에서 N인 녀석들만 노출되게 하기 위한 역할. 최근 이런방법을 많이 사용한다고 한다. 파라미터로 idx를 받아서 where 조건으로 특정 게시글의 delete_yn 상태를 Y로 바꿈(삭제처리)
	public List<BoardDTO> selectBoardList();
	// 리턴 타입으로 지정된 List<BoardDTO>와 같이 <>안에 탑입을 파라미터로 갖는 형태를 제네릭 타입이라함. 리스트 안에 하나의 게시글을 조회하는 selectBoardDetail메서드를 호출한 결과를 여러개 저장하는 것과 유사함.
	
	public int selectBoardTotalCount();
	// 삭제여부가 N인 게시글의 개수를 조회하는 SELECT쿼리를 호출하는 메서드로 페이징 처리할 때 사용함.
}
