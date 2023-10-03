package com.project.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.dto.MyResponseEntityDTO;
import com.project.dto.board.BoardDTO;
import com.project.dto.board.BoardDetailDTO;
import com.project.common.MyResponseEntity;
import com.project.dto.MainDTO;
import com.project.service.BoardService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/board")
public class BoardController {

	@Autowired
	BoardService boardService;

	// 메인화면 성공
	@GetMapping("/")
	@ApiOperation("boardMain")
	public MyResponseEntity<List<MainDTO>> getFirstWindow() {
		List<MainDTO> viewMainList = boardService.getFirstWindow();
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("main 화면", viewMainList));
	}

	// 게시판 성공
	@GetMapping("/{catDomain}")
	@ApiOperation("getBoard")
	public MyResponseEntity<List<BoardDTO>> getBoardList(@PathVariable("catDomain") @NotBlank String catDomain,
			@RequestParam(name = "curPage", required = false, defaultValue = "1") Integer curPage,
			@RequestParam(name = "perPage", required = false) Integer perPage) {
		List<BoardDTO> boardList = boardService.getBoardList(catDomain, curPage, perPage);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("게시판 화면", boardList));
	}

	// 게시글 작성 성공
	@PostMapping("/{catDomain}")
	@ApiOperation("createBoard")
	public MyResponseEntity<Void> createBoard(@PathVariable("catDomain") @NotBlank String catDomain,
			@RequestBody @Valid BoardDTO boardDTO) {
		String resultMesg = boardService.createBoard(catDomain, boardDTO);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("게시글 작성 여부" + resultMesg));
	}

	// 게시글 수정 성공
	@PutMapping("/{catDomain}/{boardNum}")
	@ApiOperation("updateBoard")
	public MyResponseEntity<Void> updateBoard(@PathVariable("catDomain") @NotBlank String catDomain,
			@PathVariable("boardNum") @NotNull Integer boardNum, @RequestBody @Valid BoardDTO boardDTO) {
		String resultMesg = boardService.updateBoard(catDomain, boardDTO);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("게시글 수정 여부" + resultMesg));
	}

	// 게시글 삭제 성공
	@DeleteMapping("/{boardNum}")
	@ApiOperation("deleteBoard")
	public MyResponseEntity<Void> deleteBoard(@PathVariable("boardNum") @NotNull Integer boardNum,
			@RequestBody @Valid BoardDTO boardDTO) {
		String resultMesg = boardService.deleteBoard(boardNum, boardDTO);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("게시글 삭제 여부" + resultMesg));
	}

	// 게시글 검색 성공
	@GetMapping("/{catDomain}/search/{target}")
	@ApiOperation("getDetailBoard")
	public MyResponseEntity<List<BoardDTO>> getDetailBoardList(@PathVariable("catDomain") @NotBlank String catDomain,
			@PathVariable("target") @NotBlank String target,
			@RequestParam(name = "keyword", required = false) String keyword,
			@RequestParam(name = "curPage", required = false, defaultValue = "1") Integer curPage,
			@RequestParam(name = "perPage", required = false) @Max(value = 50, message = "15,30,50 중 선택해주세요.") Integer perPage) {
		List<BoardDTO> boardSearch = boardService.getDetailBoardList(catDomain, target, keyword, curPage, perPage);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("게시글 검색 ", boardSearch));
	}

	// 게시글 상세 성공
	// 댓글 계층 구조 페이징 성공
	@GetMapping("/detail/{catDomain}/{boardNum}")
	@ApiOperation("detailBoard")
	public MyResponseEntity<BoardDetailDTO> showBoard(@PathVariable("catDomain") @NotBlank String catDomain,
			@PathVariable("boardNum") @NotNull Integer boardNum,
			@RequestParam(name = "curPage", required = false) Integer curPage) {
		BoardDetailDTO showBoard = boardService.showBoard(catDomain, boardNum, curPage);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("게시글 상세 내용", showBoard));
	}

	// 좋아요 버튼
	@PostMapping("/good-point/{catDomain}/{boardNum}")
	@ApiOperation("GoodPoint")
	public MyResponseEntity<Void> getGoodPoint(@PathVariable("catDomain") @NotBlank String catDomain,
			@PathVariable("boardNum")  @NotNull Integer boardNum) {
		String resultMesg = boardService.getGoodPoint(catDomain, boardNum);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("굿 포인트 추가" + resultMesg));
	}

	// 나빠요 버튼
	@PostMapping("/bad-point/{catDomain}/{boardNum}")
	@ApiOperation("BadPoint")
	public MyResponseEntity<Void> getBadPoint(@PathVariable("catDomain")@NotBlank String catDomain,
			@PathVariable("boardNum") @NotNull Integer boardNum) {
		String resultMesg = boardService.getBadPoint(catDomain, boardNum);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("워스트 포인트 추가" + resultMesg));
	}
}
