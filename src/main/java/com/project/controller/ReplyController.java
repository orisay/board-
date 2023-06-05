package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.common.MyResponseEntity;
import com.project.dto.MyResponseEntityDTO;
import com.project.dto.ReplyDTO;
import com.project.service.ReplyService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/reply")
public class ReplyController {
	@Autowired
	ReplyService replyService;

	// 댓글 작성
	@PostMapping("/insert/{boardNum}")
	@ApiOperation("insertReply")
	public MyResponseEntity<Void> insertReply(@PathVariable("boardNum") Integer boardNum,
			@RequestBody ReplyDTO replyDTO, @PathVariable(name = "rplNum", required = false) Integer rplNum) {
		replyService.insertReply(boardNum, replyDTO, rplNum);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("댓글 작성 성공"));
	}

	// 댓글 수정
	@PutMapping("/update/{rplNum}")
	@ApiOperation("updateReply")
	public MyResponseEntity<Void> updateReply(@RequestBody ReplyDTO replyDTO) {
		replyService.updateReply(replyDTO);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("댓글 수정 성공"));
	}

	// 댓글 삭제
	//spring boot 내장 tomcat은 deletemapping requestbody 지원해준다.
	@DeleteMapping("/delete/{rplNum}")
	@ApiOperation("deleteReply")
	public MyResponseEntity<Void> deleteReply(@RequestBody ReplyDTO replyDTO) {
		replyService.deleteReply(replyDTO);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("댓글 삭제 성공"));
	}
}
