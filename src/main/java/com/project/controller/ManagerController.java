package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.common.MyResponseEntity;
import com.project.dto.BoardDTO;
import com.project.dto.MyResponseEntityDTO;
import com.project.dto.ReplyDTO;
import com.project.service.ManagerService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/mng")
public class ManagerController {
	@Autowired
	ManagerService adminservice;
	@DeleteMapping("/{catDomain}/{boardNum}")
	@ApiOperation("deleteBoardNumList")
	public MyResponseEntity<List<Integer>> deleteBoardNumList(@PathVariable("catDomain")String catDomain
			, @RequestParam("boardNum") List<BoardDTO> list) {
		List<Integer> deleteBoardNumList = adminservice.deleteBoardNumList(catDomain, list);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("삭제", deleteBoardNumList));
	}

	// 댓글 체크 삭제 or 1개 삭제
	@DeleteMapping("/{catDomain}/{boardNum}/{rplNum}")
	@ApiOperation("deleteRplNumList")
	public MyResponseEntity<List<Integer>> deleteRplNumList(@PathVariable("catDomain")String catDomain
			, @PathVariable("boardNum") Integer boardNum, @RequestParam("rplNum") List<ReplyDTO> list) {
		List<Integer> deleteRplNumList = adminservice.deleteRplNumList(catDomain, boardNum, list);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("삭제", deleteRplNumList));
	}

	// subMng 권한 부여 /mng에 접근 가능
	@PutMapping("/{catDomain}/{id}")
	@ApiOperation("changeSubManager")
	public MyResponseEntity<String> changeSubManager(@PathVariable("catDomain") String catDomain,
			@PathVariable("id") String id) {
		String subManager = adminservice.changeSubManager(catDomain, id);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("서브매니저 : ", subManager));
	}

}
