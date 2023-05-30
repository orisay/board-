package com.project.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.config.ConstantConfig;
import com.project.config.IPConfig;
import com.project.config.SessionConfig;
import com.project.dao.BoardDAO;
import com.project.dto.BoardDTO;
import com.project.dto.BoardDetailDTO;
import com.project.dto.BoardSearchDTO;
import com.project.dto.PageDTO;
import com.project.dto.ReplyDTO;
import com.project.dto.mainDTO;
import com.project.exception.UnknownException;

@Service
public class BoardService {

	// TO DO 마무리 예외처리 할 것
	@Autowired
	BoardDAO boardDAO;

	private static final Logger logger = LogManager.getLogger(BoardService.class);

	// main입니다.
	// oracle에서 autocommit 비활성화 했습니다.
	@Transactional
	public List<mainDTO> getMainList() {
		List<mainDTO> mainList = boardDAO.getMainList();
		return mainList;
	}

	// 게시판 화면
	@Transactional
	public List<BoardDTO> getBoardList(String catDomain, Integer curPage, Integer perPage) {
		String user = getAccessRight();
		if (catDomain == null) {
			logger.warn("getBoardList access User : {} null value catDomain : {}", user, catDomain);
			throw new IllegalArgumentException("BoardService getBoardList null value catDomain " + ": " + catDomain);
		}
		BoardSearchDTO boardSearchDTO = paging(catDomain, curPage, perPage);
		List<BoardDTO> boardList = boardDAO.getBoardList(boardSearchDTO);
		return boardList;
	}

	// 게시글 조건별 검색 기능
	@Transactional
	public List<BoardDTO> searchBoard(String catDomain, Integer perPage, Integer curPage, String target,
			String keyword) {
		String user = getAccessRight();

		if (catDomain == null || target == null || keyword == null) {
			logger.warn("searchBoard access User : {} null value catDomain : {}, target : " + "{}, keyword : {}", user,
					catDomain, target, keyword);
			StringBuilder errorMesg = new StringBuilder();
			errorMesg.append("BoardService searchBoard null value catDomain : ").append(catDomain).append(" tartget : ")
					.append(target).append(" keyword : ").append(keyword);
			throw new IllegalArgumentException(errorMesg.toString());
		}
		BoardSearchDTO boardSearchDTO = paging(catDomain, curPage, perPage);
		boardSearchDTO.setTarget(target);
		boardSearchDTO.setKeyword(keyword);
		List<BoardDTO> searchBoard = null;

		Integer checkLevel = ConstantConfig.Target.valueOf(target.toUpperCase()).getLevel();
		if (checkLevel < 4) {
			searchBoard = boardDAO.searchBoardBasic(boardSearchDTO);
		} else if (checkLevel == 4) {
			searchBoard = boardDAO.searchBoardComplex(boardSearchDTO);
		} else if (checkLevel > 4) {
//			BoardSearchSpecialDTO boardSearchSpecialDTO = new BoardSearchSpecialDTO();
			//선 복사 대상 후 주입 대상
//			BeanUtils.copyProperties(boardSearchDTO, boardSearchSpecialDTO);
			//Target.getAllValue는 List<ENUM>으로 전부 가져온다.
			//mybatis에서는 ENUM을 자동으로 String 치환해서 먹는다.
//			boardSearchSpecialDTO.setKeyword(Target.getAllValue());
			searchBoard = boardDAO.searchBoardAll(boardSearchDTO);
		} else {
			logger.error("boardSearch access User : {} default case target : {}, keyword : {}", user, target, keyword);
			throw new UnknownException("BoardService boardSearch 예상치 못한 상태가 발생했습니다.");
		}
		return searchBoard;
	}

	// 게시글 등록
	@Transactional
	public String insertBoard(String catDomain, BoardDTO boardDTO) {
		String user = getAccessRight();
		if (catDomain == null || boardDTO == null) {
			logger.warn("insertBoard access User : {} null value catDomain : {}, boardDTO " + ": {}", user, catDomain,
					boardDTO);
			StringBuilder errorMesg = new StringBuilder();
			errorMesg.append("BoardService insertBoard null value catDomain : ").append(catDomain)
					.append(" boardDTO : ").append(boardDTO);
			throw new IllegalArgumentException(errorMesg.toString());
		}
		boardDTO.setCatDomain(catDomain);
		boardDTO.setCreator(user);
		boardDTO.setView(ConstantConfig.startView);
		Integer insertCount = boardDAO.insertBoard(boardDTO);
		String resultMesg = null;
		if (insertCount == 1) {
			resultMesg = "성공했습니다";
		} else if (insertCount == 0) {
			logger.warn("insertBoard access User : {} DB is not affected. catDomain: {}, boardDTO: {}", user, catDomain,
					boardDTO);
			resultMesg = "실패했습니다";
		} else {
			logger.error("insertBoard access User : {} unknown status", user);
			throw new UnknownException("BoardService insertBoard 예상치 못한 상태가 발생했습니다.");
		}
		return resultMesg;
	}

	// 게시글 수정
	@Transactional
	public String updateBoard(String catDomain, BoardDTO boardDTO) {
		String user = getAccessRight();
		if (catDomain == null || boardDTO == null) {
			logger.warn("updateBoard access User : {} null value catDomain : {}, " + "boardDTO : {}", user, catDomain,
					boardDTO);
			StringBuilder errorMesg = new StringBuilder();
			errorMesg.append("BoardService updateBoard null value catDomain : ").append(catDomain)
					.append(" boardDTO : ").append(boardDTO);
			throw new IllegalArgumentException(errorMesg.toString());
		}
		boardDTO.setCatDomain(catDomain);
		Integer updateCount = boardDAO.updateBoard(boardDTO);
		String resultMesg = null;
		if (updateCount == 1) {
			resultMesg = "성공했습니다.";
		} else if (updateCount == 0) {
			logger.warn("updateBoard access User : {} DB is not affected. catDomain: {}, boardDTO: {}", user, catDomain,
					boardDTO);
			resultMesg = "실패했습니다.";
		} else {
			logger.error("updateBoard access User : {} unknown status", user);
			throw new UnknownException("BoardService updateBoard 예상치 못한 상태가 발생했습니다.");
		}
		return resultMesg;
	}

	// 게시글 삭제
	@Transactional
	public String deleteBoard(Integer boardNum, BoardDTO boardDTO) {
		String user = getAccessRight();
		if (boardNum == null || boardDTO == null) {
			logger.warn("deleteBoard access User : {} null value boardNum : {}," + " boardDTO : {}", user, boardNum,
					boardDTO);
			StringBuilder errorMesg = new StringBuilder();
			errorMesg.append("BoardService deleteBoard null value boardNum : ").append(boardNum).append(" boardDTO : ")
					.append(boardDTO);
			throw new IllegalArgumentException(errorMesg.toString());
		}
		Integer deleteCount = boardDAO.deleteBoard(boardNum);
		System.out.println("boardDelete" + boardDTO);
		String resultMesg = null;
		if (deleteCount == 1) {
			boardDAO.backUpBoard(boardDTO);
			resultMesg = "성공했습니다";
		} else if (deleteCount == 0) {
			logger.warn("deleteBoard access User : {} DB is not affected. boardNum: {}, boardDTO: {}", user, boardNum,
					boardDTO);
			resultMesg = "실패했습니다";
		} else {
			logger.error("deleteBoard access User : {} unknown status", user);
			throw new UnknownException("BoardService deleteBoard 예상치 못한 상태가 발생했습니다.");
		}

		return resultMesg;
	}

	// 게시글 상세보기
	@Transactional
	public BoardDetailDTO boardDetail(String catDomain, Integer boardNum, Integer curPage) {
		String user = getAccessRight();
		if (catDomain == null || boardNum == null) {
			logger.warn("boardDetail access User : {} null value catDomain" + " : {}, boardNum : {}", user, catDomain,
					boardNum);
			StringBuilder errorMesg = new StringBuilder();
			errorMesg.append("BoardService boardDetail null value catDomain : ").append(catDomain)
					.append(" boardNum : ").append(boardNum);
			throw new IllegalArgumentException(errorMesg.toString());
		}

		BoardDetailDTO boardDetailDTO = new BoardDetailDTO();
		boardDetailDTO.setCatDomain(catDomain);
		boardDetailDTO.setBoardNum(boardNum);
		viewUpdate(boardDetailDTO);
		BoardDetailDTO board = boardDAO.boardDetail(boardDetailDTO);
		// 리플 페이징 처리
		String selectNum = Integer.toString(boardNum);
		BoardSearchDTO boardSearchDTO = paging(selectNum, curPage, ConstantConfig.perPage);
		List<ReplyDTO> replyList = boardDAO.replyList(boardSearchDTO);
		board.setList(replyList);
		return board;
	}

	// 게시글 조회 수 증가 (시간 제한 걸어서 초당 제한)
	@Transactional
	private void viewUpdate(BoardDetailDTO boardDetailDTO) {
		LocalDateTime lastClickTime = SessionConfig.getLastClick();
		Boolean check = Duration.between(lastClickTime, LocalDateTime.now()).getSeconds() >= 1;
		if (lastClickTime == null || check) {
			boardDetailDTO.setView(boardDetailDTO.getView()+ConstantConfig.plusView);
			boardDAO.updateView(boardDetailDTO);
		} else {
			logger.info("Too many clicked id" + SessionConfig.getMbDTO().getId());
		}

	}

	// 게시글, 댓글 페이징 처리
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
		Integer totalCount = checkTotaldCount(catDomain);
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

	private Integer checkTotaldCount(String catDomain) {
		Integer totalCount = null;
		if (StringUtils.isNumeric(catDomain)) {
			totalCount = boardDAO.totalCountByCat(catDomain);
		} else {
			totalCount = boardDAO.totalCountByBoard(catDomain);
		}
		return totalCount;
	}

	// guest인지 회원인지 확인
	private String getAccessRight() {
		String guestId = IPConfig.getIp(SessionConfig.getSession());
		String memberId = SessionConfig.getMbDTO().getId();
		String mesg = null;

		if (guestId == null) {
			logger.error("세션에서 정보를 찾을 수 없는 상황에 접급 했습니다.");
			throw new UnknownException("세션정보가 없는 상황에서 접근");
		} else if (guestId != null && memberId == null) {
			mesg = guestId;
		} else {
			mesg = memberId;
		}
		return mesg;

	}

}
