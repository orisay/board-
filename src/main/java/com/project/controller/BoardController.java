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
import com.project.dto.mainDTO;
import com.project.service.BoardService;

import io.swagger.annotations.ApiOperation;

@RestController
public class BoardController {
	BoardService boardService;

	// 기본 메인화면 = 1 카테고리 별 최신글 순 조인 필수 새로운 dto
	// 메인 = 카테고리 이름,카테고리별 게시글,최신글 업데이트 시간
	// 카테고리 누르고 보여질 게시판 화면 = 1 초기 dto
	// 게시글 등록, 수정, 삭제 = 3 초기 dto
	// 페이징 처리 서비스에서, 검색 기능
	// 게시글(메소드에서 매번 추가 게시글 조회 수 증가 서비스 로직에서, 댓글 달린 수(여기서 안해도 괜찮고)) = 1
	// 관리자 메뉴?

	@GetMapping("/")
	@ApiOperation("main")
	public ResponseEntity<List<mainDTO>> main() {
		List<mainDTO> mainList = boardService.mainList();
		return ResponseEntity.ok(mainList);
	}

	@GetMapping("/board/{cat}/{perPage}/{curPage}")
	@ApiOperation("board")
	public ResponseEntity<List<BoardDTO>> board(@PathVariable("cat")String cat,
			@PathVariable(name = "curPage", required = false)Integer curPage,
			@PathVariable(name = "purPage", required = false)Integer perPage){
		List<BoardDTO> boardList = boardService.boardList(cat, curPage, perPage);
		return ResponseEntity.ok(boardList);
	}

	@PostMapping("/board/{cat}/write")
	@ApiOperation("boardWrite")
	public ResponseEntity<BoardDTO> boardInsert(@PathVariable("cat")String cat,
			@RequestBody BoardDTO boardDTO){
		boardDTO.setCat(cat);
		boardService.boardInsert(boardDTO);
		return ResponseEntity.ok(null);
	}

	@PutMapping("/board/{cat}/write/{boardNum}")
	@ApiOperation("boardUpdate")
	public ResponseEntity<BoardDTO> boardUpdate(@PathVariable("cat")String cat,
			@RequestBody BoardDTO boardDTO){
		boardDTO.setCat(cat);
		boardService.boardUpdate(boardDTO);
		return ResponseEntity.ok(null);
	}

	@DeleteMapping("/board/del/{boardNum}")
	@ApiOperation("boardDelete")
	public ResponseEntity<Integer> boardDelete(@PathVariable("boardNum")Integer boardNum){
	boardService.boardDelete(boardNum);
	return ResponseEntity.ok(null);
	}

	@GetMapping("/board/{cat}/{perPage}/{curPage}/search/")
	@ApiOperation("boardSearch")
	public ResponseEntity<List<BoardDTO>> search(@PathVariable("cat") String cat,
			@PathVariable("perPage")Integer perPage, @PathVariable("curPage")Integer curPage,
			@RequestParam("target")String target, @RequestParam("keyword") String keyword){
		List<BoardDTO> boardSearch = boardService.boardSearch(cat, perPage, curPage, target, keyword);
		return ResponseEntity.ok(boardSearch);
	}


}
