package com.project.controller;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.common.MyResponseEntity;
import com.project.dto.BoardDTO;
import com.project.dto.BoardListDTO;
import com.project.dto.MyResponseEntityDTO;
import com.project.dto.ReplyDTO;
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

	//게시글 삭제
	@DeleteMapping("/board/{catDomain}")
	@ApiOperation("deleteBoardNumList")
	public MyResponseEntity<List<Integer>> deleteBoardNumList(@PathVariable("catDomain")String catDomain
			, @RequestBody BoardListDTO boardListDTO) {
		List<BoardDTO> list = boardListDTO.getList();
		List<Integer> deleteBoardNumList = managerService.deleteBoardNumList(catDomain, list);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("삭제", deleteBoardNumList));
	}

	// 댓글 체크 삭제 or 1개 삭제
	@DeleteMapping("/relpy/{catDomain}/{boardNum}")
	@ApiOperation("deleteRplNumList")
	public MyResponseEntity<List<Integer>> deleteRplNumList(@PathVariable("catDomain")String catDomain
			, @PathVariable("boardNum") Integer boardNum, @RequestBody List<ReplyDTO> list) {
		List<Integer> deleteRplNumList = managerService.deleteRplNumList(catDomain, boardNum, list);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("삭제", deleteRplNumList));
	}

}
