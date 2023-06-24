package com.project.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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

	// 댓글 작성 성공
	@PostMapping("/{boardNum}")
	@ApiOperation("createarentReply")
	public MyResponseEntity<Void> insertParentReply(@PathVariable("boardNum") @NotNull Integer boardNum,
			@RequestBody @Valid ReplyDTO replyDTO) {
		String resultMesg = replyService.insertParentReply(boardNum, replyDTO);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("댓글 작성" + resultMesg));
	}

	// 대댓글 작성
	@PostMapping("/{boardNum}/{rplNum}")
	@ApiOperation("createChildReReply")
	public MyResponseEntity<Void> insertChildReReply(@PathVariable("boardNum") @NotNull Integer boardNum,
			@PathVariable("rplNum") @NotNull Integer rplNum, @RequestBody @Valid ReplyDTO replyDTO) {
		String resultMesg = replyService.insertChildReReply(boardNum, rplNum, replyDTO);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("대댓글 작성" + resultMesg));
	}

	// 댓글 수정 성공
	@PutMapping("/{rplNum}")
	@ApiOperation("updateReply")
	public MyResponseEntity<Void> updateReply(@RequestBody ReplyDTO replyDTO) {
		String resultMesg = replyService.updateReply(replyDTO);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("댓글 수정" + resultMesg));
	}

	// 댓글 삭제 성공
	// 대댓글 null 변경 없는 것은 삭제 성공
	// spring boot 내장 tomcat은 deletemapping requestbody 지원해준다.
	@DeleteMapping("/{rplNum}")
	@ApiOperation("deleteReply")
	public MyResponseEntity<Void> deleteReply(@RequestBody ReplyDTO replyDTO) {
		String resultMesg = replyService.deleteReply(replyDTO);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("댓글 삭제" + resultMesg));
	}
}
