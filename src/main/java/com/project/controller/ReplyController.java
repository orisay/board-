package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.dto.ReplyDTO;
import com.project.service.ReplyService;

import io.swagger.annotations.ApiOperation;

@RestController
public class ReplyController {
	@Autowired
	ReplyService replyService;

	//댓글 등록 수정 삭제(삭제는 안되고 cn = null 바꾸기 컬럼 위치 수정) 부모 댓글 자식 댓글 위치  따로?
	//댓글 등록 시 게시글 달린 rplCn 증가 댓글 삭제 시 강소 그러면 cn count 서비스에서 처리
	//정렬을 spring? db?


	@GetMapping("/board/reply/{boardNum}")
	@ApiOperation("replyInsert")
	public ResponseEntity<ReplyDTO> replyInsert(@PathVariable("boardNum")Integer boardNum,
			@RequestBody ReplyDTO replyDTO, @PathVariable(name = "rplNum", required = false)Integer rplNum){
		replyDTO.setBoardNum(boardNum);
		replyService.replyInsert(replyDTO, rplNum);
		return null;
	}

	@PutMapping("/board/reply/update/{rplNum}")
	@ApiOperation("replyUpdate")
	public ResponseEntity<ReplyDTO> replyUpdate(@PathVariable("rplNum")Integer rplNum,
			@RequestBody ReplyDTO replyDTO){
		replyDTO.setBoardNum(rplNum);
		replyService.replyUpdate(replyDTO);
		return null;
	}

	@PutMapping("/board/reply/delete/{rplNum}")
	@ApiOperation("replyDelete")
	public ResponseEntity<ReplyDTO> replyDelete(@PathVariable("rplNum")Integer rplNum){
		replyService.replyDelete(rplNum);
		return null;
	}
}
