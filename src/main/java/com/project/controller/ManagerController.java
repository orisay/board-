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
	// 게시글 한개 또는 여러개 삭제 그냥 리스트로 삭제 = 1개
	// 댓글 삭제 = 여러개
	// 카테고리별 부매니저 부여 변경 비워두기 공백으로 하면 없고 넣어두면 부매니저 두기
	// DB 부매니저 테이블 따로 두기 스프링 시큐리티

	// catDomain 필요여부 확인
	// 게시글 체크 삭제 or 1개 삭제 (boardNum을 list로 받아 여러개 삭제 가능)
	@DeleteMapping("/{boardNum}")
	@ApiOperation("deleteBoardNumList")
	public MyResponseEntity<List<Integer>> deleteBoardNumList(@RequestParam("boardNum") List<BoardDTO> list) {
		List<Integer> deleteBoardNumList = adminservice.deleteBoardNumList(list);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("삭제", deleteBoardNumList));
	}

	// 댓글 체크 삭제 or 1개 삭제
	@DeleteMapping("/{boardNum}/{rplNum}")
	@ApiOperation("deleteRplNumList")
	public MyResponseEntity<List<Integer>> deleteRplNumList(@PathVariable("boardNum") Integer boardNum,
			@RequestParam("rplNum") List<ReplyDTO> list) {
		List<Integer> deleteRplNumList = adminservice.deleteRplNumList(boardNum, list);
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
