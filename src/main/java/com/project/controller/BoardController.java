package com.project.controller;

import java.util.List;

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

import com.project.common.MyResponseEntity;
import com.project.dto.BoardDTO;
import com.project.dto.BoardDetailDTO;
import com.project.dto.MyResponseEntityDTO;
import com.project.dto.MainDTO;
import com.project.service.BoardService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/board")
public class BoardController {

	@Autowired
	BoardService boardService;

	// 메인화면
	@GetMapping("/")
	@ApiOperation("boardMain")
	public MyResponseEntity<List<MainDTO>> main() {
		List<MainDTO> mainList = boardService.getMainList();
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("main 화면", mainList));
	}

	// 게시판
	@GetMapping("/{catDomain}/{perPage}/{curPage}")
	@ApiOperation("boardList")
	public MyResponseEntity<List<BoardDTO>> getBoardList(@PathVariable("catDomain") String catDomain,
			@PathVariable(name = "curPage", required = false) Integer curPage,
			@PathVariable(name = "purPage", required = false) Integer perPage) {
		List<BoardDTO> boardList = boardService.getBoardList(catDomain, curPage, perPage);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("게시판 화면", boardList));
	}

	// 게시글 작성
	@PostMapping("/insert/{catDomain}")
	@ApiOperation("insertBoard")
	public MyResponseEntity<String> insertBoard(@PathVariable("catDomain") String catDomain,
			@RequestBody BoardDTO boardDTO) {
		String resultMesg = boardService.insertBoard(catDomain, boardDTO);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("게시글 작성 여부", resultMesg));
	}

	// 게시글 수정
	@PutMapping("/update/{catDomain}/{boardNum}")
	@ApiOperation("updateBoard")
	public MyResponseEntity<String> updateBoard(@PathVariable("catDomain") String catDomain,
			@PathVariable("boardNum") Integer boardNum, @RequestBody BoardDTO boardDTO) {
		String resultMesg = boardService.updateBoard(catDomain, boardDTO);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("게시글 수정 여부", resultMesg));
	}

	// 게시글 삭제
	@DeleteMapping("/delete/{boardNum}")
	@ApiOperation("deleteBoard")
	public MyResponseEntity<String> deleteBoard(@PathVariable("boardNum") Integer boardNum,
			@RequestBody BoardDTO boardDTO) {
		String resultMesg = boardService.deleteBoard(boardNum, boardDTO);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("게시글 삭제 성공", resultMesg));
	}

	// 게시글 검색
	@GetMapping("/search/{catDomain}/{perPage}/{curPage}")
	@ApiOperation("searchBoard")
	public MyResponseEntity<List<BoardDTO>> searchBoard(@PathVariable("cat") String catDomian,
			@PathVariable(name = "perPage", required = false) Integer perPage, @PathVariable(name = "curPage", required = false) Integer curPage,
			@RequestParam("target") String target, @RequestParam("keyword") String keyword) {
		List<BoardDTO> boardSearch = boardService.searchBoard(catDomian, perPage, curPage, target, keyword);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("게시글 검색 성공", boardSearch));
	}

	// 게시글 상세
	@GetMapping("/detail/{catDomain}/{boardNum}/{curPage}")
	@ApiOperation("boardDetail")
	public MyResponseEntity<BoardDetailDTO> boardDetail(@PathVariable("catDomain") String catDomain,
			@PathVariable("boardNum") Integer boardNum, @PathVariable(name = "curPage" , required = false) Integer curPage) {
		BoardDetailDTO board = boardService.boardDetail(catDomain, boardNum, curPage);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("게시글 상세 내용", board));
	}

}
