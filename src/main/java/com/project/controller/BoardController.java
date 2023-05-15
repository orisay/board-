package com.project.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.dto.BoardDTO;
import com.project.dto.BoardDetailDTO;
import com.project.dto.mainDTO;
import com.project.service.BoardService;

import io.swagger.annotations.ApiOperation;

@RestController
public class BoardController {
	BoardService boardService;

	@GetMapping("/")
	@ApiOperation("main")
	public ResponseEntity<List<mainDTO>> main() {
		List<mainDTO> mainList = boardService.mainList();
		return ResponseEntity.ok(mainList);
	}

	@GetMapping("/board/{catDomain}/{perPage}/{curPage}")
	@ApiOperation("boardList")
	public ResponseEntity<List<BoardDTO>> boardList(@PathVariable("catDomain") String catDomain,
			@PathVariable(name = "curPage", required = false) Integer curPage,
			@PathVariable(name = "purPage", required = false) Integer perPage) {
		List<BoardDTO> boardList = boardService.boardList(catDomain, curPage, perPage);
		return ResponseEntity.ok(boardList);
	}

	@PostMapping("/board/{catDomain}/write")
	@ApiOperation("boardWrite")
	public ResponseEntity<BoardDTO> boardInsert(@PathVariable("catDomain") String catDomain,
			@RequestBody BoardDTO boardDTO) {
		boardDTO.setCatDomain(catDomain);
		boardService.boardInsert(boardDTO);
		return ResponseEntity.ok(null);
		// to do
	}

	@PutMapping("/board/{catDomain}/write/{boardNum}")
	@ApiOperation("boardUpdate")
	public ResponseEntity<BoardDTO> boardUpdate(@PathVariable("catDomain") String catDomain,
			@PathVariable("boardNum") Integer boardNum, @RequestBody BoardDTO boardDTO) {
		boardDTO.setCatDomain(catDomain);
		boardService.boardUpdate(boardDTO);
		return ResponseEntity.ok(null);
		// to do
	}

	@DeleteMapping("/board/del/{boardNum}")
	@ApiOperation("boardDelete")
	public ResponseEntity<Integer> boardDelete(@PathVariable("boardNum") Integer boardNum) {
		boardService.boardDelete(boardNum);
		return ResponseEntity.ok(boardNum);
	}

	@GetMapping("/board/{catDomain}/{perPage}/{curPage}/search/")
	@ApiOperation("boardSearch")
	public ResponseEntity<List<BoardDTO>> search(@PathVariable("cat") String catDomian,
			@PathVariable("perPage") Integer perPage, @PathVariable("curPage") Integer curPage,
			@RequestParam("target") String target, @RequestParam("keyword") String keyword) {
		List<BoardDTO> boardSearch = boardService.boardSearch(catDomian, perPage, curPage, target, keyword);
		return ResponseEntity.ok(boardSearch);
	}

	@GetMapping("/board/{catDomain}/boardNum/{boardNum}/{curPage}")
	@ApiOperation("boardDetail")
	public ResponseEntity<BoardDetailDTO> boardDetail(@PathVariable("catDomain") String catDomain,
			@PathVariable("boardNum") Integer boardNum, @PathVariable("curPage") Integer curPage) {
		BoardDetailDTO boardDetailDTO = new BoardDetailDTO();
		boardDetailDTO.setCatDomain(catDomain);
		boardDetailDTO.setBoardNum(boardNum);
		BoardDetailDTO board = boardService.board(boardDetailDTO, curPage);
		return ResponseEntity.ok(board);
	}

}
