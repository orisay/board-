package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.common.MyResponseEntity;
import com.project.dto.MyResponseEntityDTO;
import com.project.dto.board.BoardDTO;
import com.project.dto.board.RequestBoardDTO;
import com.project.dto.reply.ReplyDTO;
import com.project.dto.reply.RequestReplyDTO;
import com.project.service.ManagerService;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/mng")
public class ManagerController {

	@Autowired
	ManagerService managerService;

	// subMng 권한 부여 성공
	@PostMapping("/subMng/{catDomain}/{id}")
	@ApiOperation("insertSubManager")
	public MyResponseEntity<String> insertSubManager(@PathVariable("catDomain") String catDomain,
			@PathVariable("id") String id) {
		String subManager = managerService.insertSubManager(catDomain, id);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("서브매니저 추가 : ", subManager));
	}
	// subMng 삭제 성공
	@DeleteMapping("/subMng/{catDomain}/{id}")
	@ApiOperation("deleteSubManager")
	public MyResponseEntity<String> deleteSubManager(@PathVariable("catDomain") String catDomain,
			@PathVariable("id") String id) {
		String subManager = managerService.deleteSubManager(catDomain, id);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("서브매니저 삭제: ", subManager));
	}

	//게시글 삭제 성공
	//트리거 작동
	//2023-06-13T00:00:00.000+00:00
	@DeleteMapping("/board/{catDomain}")
	@ApiOperation("deleteBoardNumList")
	public MyResponseEntity<List<Integer>> deleteBoardNumList(@PathVariable("catDomain")String catDomain
			, @RequestBody  RequestBoardDTO deleteBoardNum) {
		List<BoardDTO> list = deleteBoardNum.getList();
		List<Integer> deleteBoardNumList = managerService.deleteBoardNumList(catDomain, list);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("삭제", deleteBoardNumList));
	}

	// 댓글 삭제
	// 뎁스 따라 api 작동 확인 트리거 작동 확인
	@DeleteMapping("/relpy/{catDomain}/{boardNum}")
	@ApiOperation("deleteRplNumList")
	public MyResponseEntity<List<Integer>> deleteRplNumList(@PathVariable("catDomain")String catDomain
			, @PathVariable("boardNum") Integer boardNum, @RequestBody RequestReplyDTO deleteRplNum) {
		List<ReplyDTO> list = deleteRplNum.getList();
		List<Integer> deleteRplNumList = managerService.deleteRplNumList(catDomain, boardNum, list);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("삭제", deleteRplNumList));
	}

	//블럭 추가
	@PostMapping("/block/{catDomain}/{id}")
	@ApiOperation("insertBlockUser")
	public MyResponseEntity<String> insertBlockUser(@PathVariable("catDomain") String catDomain,
			@PathVariable("id") String id){
		String blockUser = managerService.insertBlockUser(catDomain, id);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("차단 유저", blockUser));
	}

}
