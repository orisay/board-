package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.config.MyResponseEntity;
import com.project.config.SessionConfig;
import com.project.dto.MbDTO;
import com.project.dto.MyResponseEntityDTO;
import com.project.service.MbService;

import io.swagger.annotations.ApiOperation;

@RestController
public class MbController {
	@Autowired
	MbService mbService;

	@PostMapping("/mb/insert")
	@ApiOperation("insertMb")
	public MyResponseEntity<String> insertMb(@RequestBody MbDTO mbDTO) {
		String insertMb = mbService.insertMb(mbDTO);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("회원 가입 여부",insertMb));
	}

	@GetMapping("/mb/{id}")
	@ApiOperation("idSearch")
	public MyResponseEntity<String> getId(@PathVariable("id") String id) {
		String searchId = mbService.getId(id);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("아이디 중복 확인", searchId));
	}

	@PostMapping("/login")
	@ApiOperation("login")
	public MyResponseEntity<String> getLogin(@RequestBody MbDTO mbDTO) {
		String checkLogin = mbService.getLogin(mbDTO);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("로그인 여부", checkLogin));
	}

	@DeleteMapping("/check/logout")
	@ApiOperation("logout")
	public MyResponseEntity<Void> getLogout() {
		 mbService.getLogout();
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("로그 아웃"));
	}

	@GetMapping("/check/myPage")
	@ApiOperation("myPage")
	public MyResponseEntity<MbDTO> getMyPage() {
		MbDTO mbDTO = mbService.getMyPage();
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("마이 페이지", mbDTO));
	}

	@PutMapping("/check/update")
	@ApiOperation("updateMyPage")
	public MyResponseEntity<String> updateMyPage(@RequestBody MbDTO mbDTO) {
		String updateMyPage = mbService.updateMyPage(mbDTO);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("마이 페이지 수정 성공", updateMyPage));
	}
}
