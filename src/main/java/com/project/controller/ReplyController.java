package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.config.MyResponseEntity;
import com.project.dto.MyResponseEntityDTO;
import com.project.dto.ReplyDTO;
import com.project.service.ReplyService;

import io.swagger.annotations.ApiOperation;

@RestController
public class ReplyController {
	@Autowired
	ReplyService replyService;

	@PostMapping("/board/reply/{boardNum}")
	@ApiOperation("replyInsert")
	public MyResponseEntity<Void> replyInsert(@PathVariable("boardNum") Integer boardNum,
			@RequestBody ReplyDTO replyDTO, @PathVariable(name = "rplNum", required = false) Integer rplNum) {
		replyService.replyInsert(boardNum, replyDTO, rplNum);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("댓글 작성 성공"));
	}

	@PutMapping("/board/reply/update/{rplNum}")
	@ApiOperation("replyUpdate")
	public MyResponseEntity<Void> replyUpdate(@PathVariable("rplNum") Integer rplNum,
			@RequestBody ReplyDTO replyDTO) {
		replyService.replyUpdate(rplNum, replyDTO);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("댓글 수정 성공"));
	}

	@PutMapping("/board/reply/delete/{rplNum}")
	@ApiOperation("replyDelete")
	public MyResponseEntity<Void> replyDelete(@PathVariable("rplNum") Integer rplNum,
			@RequestBody ReplyDTO replyDTO) {
		replyService.replyDelete(rplNum, replyDTO);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("댓글 삭제 성공"));
	}
}
