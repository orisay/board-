package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.service.ManagerService;

@RestController
@RequestMapping("/mng")
public class ManagerController {
	@Autowired
	ManagerService adminservice;
	//게시글 한개 또는 여러개 삭제 그냥 리스트로 삭제 = 1개
	//댓글 삭제 = 1개
	//카테고리별 부매니저 부여 변경 비워두기  공백으로 하면 없고 넣어두면 부매니저 두기
	//DB 부매니저 테이블 따로 두기 스프링 시큐리티

}
