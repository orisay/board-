package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

	@PostMapping("/reply/{boardNum}")
	@ApiOperation("insertReply")
	public MyResponseEntity<Void> insertReply(@PathVariable("boardNum") Integer boardNum,
			@RequestBody ReplyDTO replyDTO, @PathVariable(name = "rplNum", required = false) Integer rplNum) {
		replyService.insertReply(boardNum, replyDTO, rplNum);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("댓글 작성 성공"));
	}

	@PutMapping("/reply/update/{rplNum}")
	@ApiOperation("updateReply")
	public MyResponseEntity<Void> updateReply(@PathVariable("rplNum") Integer rplNum,
			@RequestBody ReplyDTO replyDTO) {
		replyService.updateReply(rplNum, replyDTO);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("댓글 수정 성공"));
	}

	@PutMapping("/reply/delete/{rplNum}")
	@ApiOperation("deleteReply")
	public MyResponseEntity<Void> deleteReply(@PathVariable("rplNum") Integer rplNum,
			@RequestBody ReplyDTO replyDTO) {
		replyService.deleteReply(rplNum, replyDTO);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("댓글 삭제 성공"));
	}
}
