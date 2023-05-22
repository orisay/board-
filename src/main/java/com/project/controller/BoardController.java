package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.config.MyResponseEntity;
import com.project.dto.BoardDTO;
import com.project.dto.BoardDetailDTO;
import com.project.dto.MyResponseEntityDTO;
import com.project.dto.mainDTO;
import com.project.service.BoardService;

import io.swagger.annotations.ApiOperation;

@RestController
public class BoardController {

	@Autowired
	BoardService boardService;

	// 메인화면
	@GetMapping("/")
	@ApiOperation("main")
	public MyResponseEntity<List<mainDTO>> main() {
		List<mainDTO> mainList = boardService.getMainList();
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("main 화면", mainList));
	}

	// 게시판
	@GetMapping("/board/{catDomain}/{perPage}/{curPage}")
	@ApiOperation("boardList")
	public MyResponseEntity<List<BoardDTO>> getBoardList(@PathVariable("catDomain") String catDomain,
			@PathVariable(name = "curPage", required = false) Integer curPage,
			@PathVariable(name = "purPage", required = false) Integer perPage) {
		List<BoardDTO> boardList = boardService.getBoardList(catDomain, curPage, perPage);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("게시판 화면", boardList));
	}

	// 게시글 작성
	@PostMapping("/board/{catDomain}/write")
	@ApiOperation("insertBoard")
	public MyResponseEntity<Void> insertBoard(@PathVariable("catDomain") String catDomain,
			@RequestBody BoardDTO boardDTO) {
		boardService.insertBoard(catDomain, boardDTO);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("게시글 삭제 성공"));
	}

	// 게시글 수정
	@PutMapping("/board/{catDomain}/write/{boardNum}")
	@ApiOperation("updateBoard")
	public MyResponseEntity<Void> updateBoard(@PathVariable("catDomain") String catDomain,
			@PathVariable("boardNum") Integer boardNum, @RequestBody BoardDTO boardDTO) {
		boardService.updateBoard(catDomain, boardDTO);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("게시글 수정 성공"));
	}

	// 게시글 삭제
	@DeleteMapping("/board/del/{boardNum}")
	@ApiOperation("deleteBoard")
	public MyResponseEntity<Void> deleteBoard(@PathVariable("boardNum") Integer boardNum,
			@RequestBody BoardDTO boardDTO) {
		boardService.deleteBoard(boardNum, boardDTO);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("게시글 삭제 성공"));
	}

	// 게시글 검색
	@GetMapping("/board/{catDomain}/search/{perPage}/{curPage}")
	@ApiOperation("searchBoard")
	public MyResponseEntity<List<BoardDTO>> searchBoard(@PathVariable("cat") String catDomian,
			@PathVariable("perPage") Integer perPage, @PathVariable("curPage") Integer curPage,
			@RequestParam("target") String target, @RequestParam("keyword") String keyword) {
		List<BoardDTO> boardSearch = boardService.searchBoard(catDomian, perPage, curPage, target, keyword);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("게시글 검색 성공", boardSearch));
	}

	// 게시글 상세
	@GetMapping("/board/{catDomain}/boardNum/{boardNum}/{curPage}")
	@ApiOperation("boardDetail")
	public MyResponseEntity<BoardDetailDTO> boardDetail(@PathVariable("catDomain") String catDomain,
			@PathVariable("boardNum") Integer boardNum, @PathVariable("curPage") Integer curPage) {
		BoardDetailDTO board = boardService.boardDetail(catDomain, boardNum, curPage);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("게시글 상세 내용", board));
	}

}
