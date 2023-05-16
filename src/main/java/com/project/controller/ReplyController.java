package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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


	@PostMapping("/board/reply/{boardNum}")
	@ApiOperation("replyInsert")
	public ResponseEntity<ReplyDTO> replyInsert(@PathVariable("boardNum") Integer boardNum,
			@RequestBody ReplyDTO replyDTO, @PathVariable(name = "rplNum", required = false) Integer rplNum) {
		replyService.replyInsert(boardNum, replyDTO, rplNum);
		return null;
	}

	@PutMapping("/board/reply/update/{rplNum}")
	@ApiOperation("replyUpdate")
	public ResponseEntity<ReplyDTO> replyUpdate(@PathVariable("rplNum") Integer rplNum,
			@RequestBody ReplyDTO replyDTO) {
		replyService.replyUpdate(rplNum, replyDTO);
		return null;
	}

	@PutMapping("/board/reply/delete/{rplNum}")
	@ApiOperation("replyDelete")
	public ResponseEntity<ReplyDTO> replyDelete(@PathVariable("rplNum") Integer rplNum) {
		replyService.replyDelete(rplNum);
		return null;
	}
}
