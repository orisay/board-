package com.project.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.config.ConstantConfig;
import com.project.config.SessionConfig;
import com.project.dao.BoardDAO;
import com.project.dto.BoardDTO;
import com.project.dto.BoardDetailDTO;
import com.project.dto.BoardSearchDTO;
import com.project.dto.PageDTO;
import com.project.dto.ReplyDTO;
import com.project.dto.mainDTO;

@Service
public class BoardService {

	//TO DO 마무리 예외처리 할 것
	@Autowired
	BoardDAO boardDAO;
	@Autowired
	SessionConfig sessionConfig;

	private static final Logger logger = LogManager.getLogger(BoardService.class);

	//main입니다.
	//db에서 autocommit 비활성화 했습니다.
	@Transactional
	public List<mainDTO> getMainList() {
		List<mainDTO> mainList = boardDAO.getMainList();
		return mainList;
	}

	//게시판 화면
	@Transactional
	public List<BoardDTO> boardList(String catDomain, Integer curPage, Integer perPage) {
		BoardSearchDTO boardSearchDTO = paging(catDomain, curPage, perPage);
		List<BoardDTO> boardList = boardDAO.boardList(boardSearchDTO);
		return boardList;
	}

	//게시글 검색 기능
	@Transactional
	public List<BoardDTO> boardSearch(String catDomain, Integer perPage, Integer curPage, String target, String keyword) {
		BoardSearchDTO boardSearchDTO = paging(catDomain, curPage, perPage);
		boardSearchDTO.setTarget(target);
		boardSearchDTO.setKeyword(keyword);

		switch (target) {

		case "creator":
			return boardDAO.boardBasicSearch(boardSearchDTO);

		case "ttl":
			return boardDAO.boardBasicSearch(boardSearchDTO);

		case "cn":
			return boardDAO.boardBasicSearch(boardSearchDTO);

		case "rplCn":
			return boardDAO.boardBasicSearch(boardSearchDTO);

		case "ttl+cn":
			return boardDAO.boardComplexSearch(boardSearchDTO);

		case "all":
			return boardDAO.boardAllSearch(boardSearchDTO);

		default:
			logger.warn("boardSearch default case target" + target, " &keyword" + keyword);
			return new ArrayList<BoardDTO>();
		}

	}

	//게시글 등록
	@Transactional
	public void boardInsert(String catDomain, BoardDTO boardDTO) {
		boardDTO.setCatDomain(catDomain);
		Integer insertCount = boardDAO.boardInsert(boardDTO);
		logger.info("boardInsert" + insertCount);
	}

	//게시글 수정
	@Transactional
	public void boardUpdate(String catDomain, BoardDTO boardDTO) {
		boardDTO.setCatDomain(catDomain);
		Integer updateCount = boardDAO.boardUpdate(boardDTO);
		logger.info("boardUpdate" + updateCount);
	}

	//게시글 삭제
	@Transactional
	public void boardDelete(Integer boardNum, BoardDTO boardDTO) {
		Integer deleteCount = boardDAO.boardDelete(boardNum);
		System.out.println("boardDelete"+boardDTO);
		boardDAO.boardBackUp(boardDTO);
		logger.info("boardDelete" + deleteCount);
	}

	//게시글 상세보기
	@Transactional
	public BoardDetailDTO board(String catDomain, Integer boardNum, Integer curPage) {
		BoardDetailDTO boardDetailDTO = new BoardDetailDTO();
		boardDetailDTO.setCatDomain(catDomain);
		boardDetailDTO.setBoardNum(boardNum);
		viewUpdate(boardDetailDTO);
		BoardDetailDTO board = boardDAO.board(boardDetailDTO);
		String selectNum = Integer.toString(boardNum);
		BoardSearchDTO boardSearchDTO = paging(selectNum, curPage, ConstantConfig.perPage);
		List<ReplyDTO> replyList = boardDAO.replyList(boardSearchDTO);
		board.setList(replyList);
		return board;
	}

	//게시글 조회 수 증가 (시간 제한 걸어서 초당 제한)
	@Transactional
	private void viewUpdate(BoardDetailDTO boardDetailDTO) {
		LocalDateTime lastClickTime = SessionConfig.getLastClick();
		Boolean check = Duration.between(lastClickTime, LocalDateTime.now()).getSeconds() >= 1;
		if (lastClickTime == null || check) {
			boardDAO.viewUpdate(boardDetailDTO);
		} else {
			logger.info("Too many clicked id" + SessionConfig.getMbDTO().getId());
		}

	}

	//게시글, 댓글 페이징 처리
	private BoardSearchDTO paging(String catDomain, Integer curPage, Integer perPage) {
		if (curPage == null) {
			curPage = 1;
		}
		if (perPage == null) {
			perPage = ConstantConfig.perPage;
		}
		PageDTO pageDTO = new PageDTO();
		pageDTO.setCurPage(curPage);
		pageDTO.setPerPage(perPage);
		Integer totalCount = boardDAO.categoryBoardCount(catDomain);
		Integer totalPage = (int) ((double) totalCount / (int) perPage);
		pageDTO.setTotalPage(totalPage);
		Integer startIdx = ((pageDTO.getCurPage() - 1) * pageDTO.getCurPage()) + 1;
		Integer endIdx = (pageDTO.getPerPage() * pageDTO.getCurPage());
		BoardSearchDTO boardSearchDTO = new BoardSearchDTO();
		boardSearchDTO.setStartIdx(startIdx);
		boardSearchDTO.setEndIdx(endIdx);
		boardSearchDTO.setCat(catDomain);
		return boardSearchDTO;
	}



}
