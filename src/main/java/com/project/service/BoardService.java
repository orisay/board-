package com.project.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.ParseConversionEvent;

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
	@Autowired
	BoardDAO boardDAO;
	private static final Logger logger = LogManager.getLogger(BoardService.class);

	@Transactional
	public List<mainDTO> mainList() {
		return boardDAO.mainList();
	}

	@Transactional
	public List<BoardDTO> boardList(String cat, Integer curPage, Integer perPage) {
		if (curPage == null) {
			curPage = 1;
		}
		if (perPage == null) {
			perPage = ConstantConfig.perPage;
		}
		BoardSearchDTO boardSearchDTO = paging(cat, curPage, perPage);
		List<BoardDTO> boardList = boardDAO.boardList(boardSearchDTO);
		return boardList;
	}

	@Transactional
	public List<BoardDTO> boardSearch(String cat, Integer perPage, Integer curPage, String target, String keyword) {
		if (curPage == null) {
			curPage = 1;
		}
		if (perPage == null) {
			perPage = ConstantConfig.perPage;
		}
		BoardSearchDTO boardSearchDTO = paging(cat, curPage, perPage);
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

	@Transactional
	public void boardInsert(BoardDTO boardDTO) {
		Integer insertCount = boardDAO.boardInsert(boardDTO);
		logger.info("boardInsert" + insertCount);
	}

	@Transactional
	public void boardUpdate(BoardDTO boardDTO) {
		Integer updateCount = boardDAO.boardUpdate(boardDTO);
		logger.info("boardUpdate" + updateCount);
	}

	@Transactional
	public void boardDelete(Integer boardNum) {
		Integer deleteCount = boardDAO.boardDelete(boardNum);
		logger.info("boardDelete" + deleteCount);
	}

	@Transactional
	public BoardDetailDTO board(BoardDetailDTO boardDetailDTO, Integer curPage) {
		viewUpdate(boardDetailDTO);
		BoardDetailDTO board = boardDAO.board(boardDetailDTO);
		String boardNum = Integer.toString(boardDetailDTO.getBoardNum());
		BoardSearchDTO boardSearchDTO = paging(boardNum, curPage, ConstantConfig.perPage);
		List<ReplyDTO> replyList = boardDAO.replyList(boardSearchDTO);
		board.setList(replyList);
		return board;
	}

	@Transactional
	private void viewUpdate(BoardDetailDTO boardDetailDTO) {
		LocalDateTime lastClickTime = SessionConfig.getLastClick();
		Boolean check = Duration.between(lastClickTime, LocalDateTime.now()).getSeconds() >= 1;
		if (lastClickTime == null || check == true) {
			boardDAO.viewUpdate(boardDetailDTO);
		} else {
			logger.info("Too many clicked id" + SessionConfig.getMbDTO().getId());
		}

	}

	@Transactional
	private BoardSearchDTO paging(String cat, Integer curPage, Integer perPage) {
		PageDTO pageDTO = new PageDTO();
		pageDTO.setCurPage(curPage);
		pageDTO.setPerPage(perPage);
		Integer totalCount = boardDAO.categoryBoardCount(cat);
		Integer totalPage = (int) ((double) totalCount / (int) perPage);
		pageDTO.setTotalPage(totalPage);
		Integer startIdx = ((pageDTO.getCurPage() - 1) * pageDTO.getCurPage()) + 1;
		Integer endIdx = (pageDTO.getPerPage() * pageDTO.getCurPage());
		BoardSearchDTO boardSearchDTO = new BoardSearchDTO();
		boardSearchDTO.setStartIdx(startIdx);
		boardSearchDTO.setEndIdx(endIdx);
		boardSearchDTO.setCat(cat);
		return boardSearchDTO;
	}

}
