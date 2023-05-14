package com.project.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dao.BoardDAO;
import com.project.dto.BoardDTO;
import com.project.dto.BoardSearchDTO;
import com.project.dto.PageDTO;
import com.project.dto.mainDTO;

@Service
public class BoardService {
	@Autowired
	BoardDAO boardDAO;
	private static final Logger logger = LogManager.getLogger(BoardService.class);

	public List<mainDTO> mainList() {
		return boardDAO.mainList();
	}

	public List<BoardDTO> boardList(String cat, Integer curPage, Integer perPage) {
		if (curPage == null) {
			curPage = 1;
		}
		if (perPage == null) {
			perPage = 30;
		}
		BoardSearchDTO boardSearchDTO = paging(cat, curPage, perPage);
		List<BoardDTO> boardList = boardDAO.boardList(boardSearchDTO);
		return boardList;
	}

	public List<BoardDTO> boardSearch(String cat, Integer perPage, Integer curPage, String target, String keyword) {
		if (curPage == null) {
			curPage = 1;
		}
		if (perPage == null) {
			perPage = 30;
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

	public void boardInsert(BoardDTO boardDTO) {
		Integer insertCount = boardDAO.boardInsert(boardDTO);
		logger.info("boardInsert" + insertCount);
	}

	public void boardUpdate(BoardDTO boardDTO) {
		Integer updateCount = boardDAO.boardUpdate(boardDTO);
		logger.info("boardUpdate" + updateCount);
	}

	public void boardDelete(Integer boardNum) {
		Integer deleteCount = boardDAO.boardDelete(boardNum);
		logger.info("boardDelete" + deleteCount);
	}

}
