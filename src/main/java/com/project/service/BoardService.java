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

import com.project.common.constant.ConstantConfig;
import com.project.common.constant.SearchLevelConfig;
import com.project.common.constant.SearchLevelConfig.Target;
import com.project.common.constant.UserRoleConfig.UserRole;
import com.project.config.IPConfig;
import com.project.config.SessionConfig;
import com.project.dao.BoardDAO;
import com.project.dto.MainDTO;
import com.project.dto.PageDTO;
import com.project.dto.board.BoardDTO;
import com.project.dto.board.BoardDetailDTO;
import com.project.dto.board.BoardSearchDTO;
import com.project.dto.board.PlusPointBoardDTO;
import com.project.dto.mb.InsertUserRoleDTO;
import com.project.dto.reply.ReplyDTO;
import com.project.exception.NotFoundException;
import com.project.exception.UnknownException;

@Service
@Transactional
public class BoardService {

	@Autowired
	private BoardDAO boardDAO;

	private static final Logger logger = LogManager.getLogger(BoardService.class);

	// main입니다.
	// oracle에서 autocommit 비활성화 했습니다.
	public List<MainDTO> getFirstWindow() {
		List<MainDTO> mainList = boardDAO.getFirstWindow();
		return mainList;
	}

	// 게시판 화면
	public List<BoardDTO> getBoardList(String catDomain, Integer curPage, Integer perPage) {
		String userType = getUserType();
		hasBlockUser(catDomain, userType);
		BoardSearchDTO boardSearchDTO = doPaging(catDomain, curPage, perPage);
		List<BoardDTO> boardList = boardDAO.getBoardList(boardSearchDTO);
		return boardList;
	}

	// 게시글 조건별 검색 기능
	public List<BoardDTO> getDetailBoardList(String catDomain, String target, String keyword, Integer curPage,
			Integer perPage) {
		String userType = getUserType();
		hasBlockUser(catDomain, userType);
		BoardSearchDTO boardSearchDTO = doPaging(catDomain, curPage, perPage);
		if (keyword == null) {
			keyword = "";
		}
		boardSearchDTO.setTarget(target);
		boardSearchDTO.setKeyword(keyword);
		List<BoardDTO> searchBoard = null;
		Integer checkLevel = SearchLevelConfig.Target.valueOf(target.toUpperCase()).getLevel();
		if (checkLevel < Target.COMP.getLevel()) {
			searchBoard = boardDAO.searchBoardBasic(boardSearchDTO);
		} else if (checkLevel == Target.COMP.getLevel()) {
			searchBoard = boardDAO.searchBoardComplex(boardSearchDTO);
		} else if (checkLevel == Target.ALL.getLevel()) {
			searchBoard = boardDAO.searchBoardAll(boardSearchDTO);
		} else {
			logger.error("boardSearch access User : {} default case target : {}, keyword : {}", userType, target, keyword);
			throw new UnknownException("BoardService boardSearch 예상치 못한 상태가 발생했습니다.");
		}
		return searchBoard;
	}

	// 게시글 등록
	public String createBoard(String catDomain, BoardDTO boardDTO) {
		String userType = getUserType();
		hasBlockUser(catDomain, userType);
		boardDTO.setCatDomain(catDomain);
		boardDTO.setCreator(userType); // ip 또는 id set
		boardDTO.setViewCnt(ConstantConfig.STARTVIEW);
		Integer createCheckCount = boardDAO.createBoard(boardDTO);
		String resultMesg = checkDataBaseUpdateStatus(createCheckCount, userType);
		if (resultMesg.equals(ConstantConfig.SUCCESS_MESG)) {
			boardDAO.increaseBoardCount(boardDTO);
		}
		return resultMesg;
	}

	// 게시글 수정
	public String updateBoard(String catDomain, BoardDTO boardDTO) {
		String userType = getUserType();
		hasBlockUser(catDomain, userType);
		boardDTO.setCatDomain(catDomain);
		Integer updateCheckCount = boardDAO.updateBoard(boardDTO);
		String resultMesg = checkDataBaseUpdateStatus(updateCheckCount, userType);
		return resultMesg;
	}

	// 게시글 삭제
	public String deleteBoard(Integer boardNum, BoardDTO boardDTO) {
		String userType = getUserType();
		hasBlockUser(boardDTO.getCatDomain(), userType);
		Integer deleteCheckCount = boardDAO.deleteBoard(boardDTO);
		String resultMesg = checkDataBaseUpdateStatus(deleteCheckCount, userType);
		if (resultMesg.equals(ConstantConfig.SUCCESS_MESG)) {
			boardDAO.decreaseBoardCount(boardDTO);
		}
		return resultMesg;
	}

	// 게시글 상세보기
	public BoardDetailDTO showBoard(String catDomain, Integer boardNum, Integer curPage) {
		String userType = getUserType();
		hasBlockUser(catDomain, userType);
		BoardDetailDTO boardDetailDTO = new BoardDetailDTO();
		boardDetailDTO.setCreator(userType);
		boardDetailDTO.setCatDomain(catDomain);
		boardDetailDTO.setBoardNum(boardNum);
		// 조회수 증가
		updateViewCount(boardDetailDTO);
		BoardDetailDTO board = boardDAO.showBoard(boardDetailDTO);
		// 리플 페이징 처리
		String choiceBoardNum = Integer.toString(boardNum);
		BoardSearchDTO boardSearchDTO = doPaging(choiceBoardNum, curPage, ConstantConfig.PERPAGE);
		List<ReplyDTO> replyList = boardDAO.getReplyList(boardSearchDTO);
		board.setList(replyList);
		return board;
	}

	// 좋아요 증가
	public String getGoodPoint(String catDomain, Integer boardNum) {
		String memberId = getUserType();
		hasBlockUser(catDomain, memberId);
		PlusPointBoardDTO plusPointBoardDTO = handlePlusPointBoardDTO(catDomain, boardNum, memberId);
		boolean isUpdatePointAllowed = canUpdatePoint(plusPointBoardDTO); //좋아요나 나빠요 버튼 눌렀는지 확인
		Integer getPointCount = ConstantConfig.FALSE_COUNT;
		if (isUpdatePointAllowed) {
			getPointCount = boardDAO.getGoodPoint(plusPointBoardDTO);
		}

		String resultMesg = checkDataBaseUpdateStatus(getPointCount, memberId);
		return resultMesg;
	}

	// 나빠요 증가
	public String getBadPoint(String catDomain, Integer boardNum) {
		String memberId = getUserType();
		hasBlockUser(catDomain, memberId);
		PlusPointBoardDTO plusPointBoardDTO = handlePlusPointBoardDTO(catDomain, boardNum, memberId);
		boolean isUpdatePointAllowed = canUpdatePoint(plusPointBoardDTO);
		Integer getPointCount = ConstantConfig.FALSE_COUNT;

		if (isUpdatePointAllowed) {
			getPointCount = boardDAO.getBadPoint(plusPointBoardDTO);
		}

		String resultMesg = checkDataBaseUpdateStatus(getPointCount, memberId);
		return resultMesg;
	}

	// 게시글 조회 수 증가 (시간 제한 걸어서 초당 제한)
	private void updateViewCount(BoardDetailDTO boardDetailDTO) {
		LocalDateTime lastClickTime = SessionConfig.getLastClick();
		Boolean check = Duration.between(lastClickTime, LocalDateTime.now()).getSeconds() >= 1;
		if (lastClickTime == null || check) {
			boardDetailDTO.setViewCnt(boardDetailDTO.getViewCnt() + ConstantConfig.PLUSVIEW);
			boardDAO.updateViewCount(boardDetailDTO);
		} else {
			logger.info("Too many clicked id" + SessionConfig.MbSessionDTO().getId());
		}
	}

	// 게시글, 댓글 페이징 처리
	private BoardSearchDTO doPaging(String catDomain, Integer curPage, Integer perPage) {
		if (curPage == null) {
			curPage = ConstantConfig.START_NUM;
		}
		if (perPage == null) {
			perPage = ConstantConfig.PERPAGE;
		}
		PageDTO pageDTO = new PageDTO();
		pageDTO.setCurPage(curPage);
		pageDTO.setPerPage(perPage);
		Integer totalCount = checkTotaldCount(catDomain);
		Integer totalPage = (int) Math.ceil((double) totalCount / (int) perPage);
		pageDTO.setTotalPage(totalPage);
		Integer startIdx = ((pageDTO.getCurPage() - 1) * pageDTO.getPerPage()) + 1;
		Integer endIdx = (pageDTO.getPerPage() * pageDTO.getCurPage());
		BoardSearchDTO boardSearchDTO = new BoardSearchDTO();
		boardSearchDTO.setStartIdx(startIdx);
		boardSearchDTO.setEndIdx(endIdx);
		boardSearchDTO.setCatDomain(catDomain);
		return boardSearchDTO;
	}

	// 게시글 총 갯수 조회, 댓글 갯수 총 조회
	private Integer checkTotaldCount(String catDomain) {
		Integer totalCount = null;
		if (!StringUtils.isNumeric(catDomain)) {
			totalCount = boardDAO.totalCountByCat(catDomain);
		} else {
			Integer boardNum = Integer.parseInt(catDomain);
			totalCount = boardDAO.totalCountByBoard(boardNum);
		}
		return totalCount;
	}

	// HandlePlusPointBoardDTO 핸들링
	private PlusPointBoardDTO handlePlusPointBoardDTO(String catDomain, Integer boardNum, String memberId) {
		PlusPointBoardDTO plusPointBoardDTO = new PlusPointBoardDTO();
		plusPointBoardDTO.setPoint(ConstantConfig.START_NUM);
		plusPointBoardDTO.setBoardNum(boardNum);
		plusPointBoardDTO.setId(memberId);
		logger.info("HandlePlusPointBoardDTO access User : {}", memberId);
		return plusPointBoardDTO;
	}

	// 갤러리 생성 좋아요나 나빠요 딱 한번 클릭 가능
	private boolean canUpdatePoint(PlusPointBoardDTO plusPointBoardDTO) {
		boolean idExists = boardDAO.canGetPoint(plusPointBoardDTO);
		if (!idExists) {
			logger.warn("You have already clicked.");
			throw new IllegalArgumentException("이미 클릭 하셨습니다.");
		}
		return idExists;
	}


	// guest인지 회원인지 확인
	private String getUserType() {
		String guestId = IPConfig.getIp(SessionConfig.getSession());
		String memberId = SessionConfig.MbSessionDTO().getId();
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

	// 차단 유저 확인
	private boolean hasBlockUser(String catDomain, String user) {
		InsertUserRoleDTO insertUserRoleDTO = new InsertUserRoleDTO();
		insertUserRoleDTO.setCatDomain(catDomain);
		insertUserRoleDTO.setId(user);
		Integer userRoleNum = boardDAO.checkUserBlockStatus(insertUserRoleDTO);
		if (userRoleNum == UserRole.BLOCK.getLevel()) {
			return false;
		} else if (userRoleNum > UserRole.BLOCK.getLevel()) {
			return true;
		} else {
			logger.warn("insertBoard access User : {} unknown status", user);
			throw new UnknownException("비정상적인 값이 발생했습니다.");
		}
	}

	//DB 영향 확인
	private String checkDataBaseUpdateStatus(Integer insertCheckCount, String user) {
		String resultMesg = null;
		if (insertCheckCount == ConstantConfig.SUCCESS_COUNT) {
			resultMesg = ConstantConfig.SUCCESS_MESG;
		} else if (insertCheckCount == ConstantConfig.FALSE_COUNT) {
			logger.warn("access User : {} DB is not affected.", user);
			throw new NotFoundException("결과 값이 정확하지 않습니다.");
		} else {
			logger.error("access User : {} unknown status", user);
			throw new UnknownException("BoardService insertBoard 예상치 못한 상태가 발생했습니다.");
		}
		return resultMesg;
	}

}
