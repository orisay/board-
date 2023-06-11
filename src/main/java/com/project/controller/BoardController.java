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

	// 메인화면 성공
	@GetMapping("/")
	@ApiOperation("boardMain")
	public MyResponseEntity<List<MainDTO>> main() {
		List<MainDTO> mainList = boardService.getMainList();
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("main 화면", mainList));
	}

	// 게시판 성공
	@GetMapping("/{catDomain}")
	@ApiOperation("readBoard")
	public MyResponseEntity<List<BoardDTO>> getBoardList(@PathVariable("catDomain") String catDomain,
			@RequestParam(name = "curPage", required = false) Integer curPage,
			@RequestParam(name = "perPage", required = false) Integer perPage) {
		List<BoardDTO> boardList = boardService.getBoardList(catDomain, curPage, perPage);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("게시판 화면", boardList));
	}

	// 게시글 작성 성공
	@PostMapping("/{catDomain}")
	@ApiOperation("createBoard")
	public MyResponseEntity<String> insertBoard(@PathVariable("catDomain") String catDomain,
			@RequestBody BoardDTO boardDTO) {
		String resultMesg = boardService.insertBoard(catDomain, boardDTO);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("게시글 작성 여부", resultMesg));
	}

	// 게시글 수정 성공
	@PutMapping("/{catDomain}/{boardNum}")
	@ApiOperation("updateBoard")
	public MyResponseEntity<String> updateBoard(@PathVariable("catDomain") String catDomain,
			@PathVariable("boardNum") Integer boardNum, @RequestBody BoardDTO boardDTO) {
		String resultMesg = boardService.updateBoard(catDomain, boardDTO);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("게시글 수정 여부", resultMesg));
	}

	// 게시글 삭제 성공
	@DeleteMapping("/{boardNum}")
	@ApiOperation("deleteBoard")
	public MyResponseEntity<String> deleteBoard(@PathVariable("boardNum") Integer boardNum,
			@RequestBody BoardDTO boardDTO) {
		String resultMesg = boardService.deleteBoard(boardNum, boardDTO);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("게시글 삭제 여부", resultMesg));
	}

	// 게시글 검색 성공
	@GetMapping("/{catDomain}/search/{target}")
	@ApiOperation("searchBoard")
	public MyResponseEntity<List<BoardDTO>> searchBoard(@PathVariable("catDomain") String catDomain
			,@PathVariable("target") String target
			,@RequestParam(name = "keyword", required = false) String keyword
			,@RequestParam(name = "curPage", required = false) Integer curPage
			,@RequestParam(name = "perPage", required = false) Integer perPage) {
		List<BoardDTO> boardSearch = boardService.searchBoard(catDomain, target, keyword, curPage, perPage);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("게시글 검색 성공", boardSearch));
	}

	// 게시글 상세 성공
	// 댓글 계층 구조 페이징 성공
	@GetMapping("/detail/{catDomain}/{boardNum}")
	@ApiOperation("detailBoard")
	public MyResponseEntity<BoardDetailDTO> boardDetail(@PathVariable("catDomain") String catDomain,
			@PathVariable("boardNum") Integer boardNum,
			@RequestParam(name = "curPage", required = false) Integer curPage) {
		BoardDetailDTO board = boardService.boardDetail(catDomain, boardNum, curPage);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("게시글 상세 내용", board));
	}

}
