package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.common.MyResponseEntity;
import com.project.dto.MbDTO;
import com.project.dto.MyResponseEntityDTO;
import com.project.service.MbService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/mb")
public class MbController {

	@Autowired
	MbService mbService;

	// 아이디 찾기 -메일주소 // 비밀 번호찾기 추가 해야함 - 아이디 -메일 주소
	// 비밀번호 변경

	// 회원 가입
	@PostMapping("/insert")
	@ApiOperation("insertMb")
	public MyResponseEntity<String> insertMb(@RequestBody MbDTO mbDTO) {
		String insertMb = mbService.insertMb(mbDTO);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("회원 가입 여부", insertMb));
	}

	// 아이디 중복 검사
	@GetMapping("/insert/{id}")
	@ApiOperation("checkId")
	public MyResponseEntity<String> checkId(@PathVariable("id") String id) {
		String checkId = mbService.checkId(id);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("아이디 중복 확인", checkId));
	}

	// 로그인
	@PostMapping("/login")
	@ApiOperation("login")
	public MyResponseEntity<String> getLogin(@RequestBody MbDTO mbDTO) {
		String checkLogin = mbService.getLogin(mbDTO);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("로그인 여부", checkLogin));
	}

	// 로그아웃
	@DeleteMapping("/check/logout")
	@ApiOperation("logout")
	public MyResponseEntity<Void> getLogout() {
		mbService.getLogout();
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("로그 아웃"));
	}

	// 마이 페이지
	@GetMapping("/check/myPage")
	@ApiOperation("myPage")
	public MyResponseEntity<MbDTO> getMyPage() {
		MbDTO mbDTO = mbService.getMyPage();
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("마이 페이지", mbDTO));
	}

	// 마이 페이지 수정
	@PutMapping("/check/update")
	@ApiOperation("updateMyPage")
	public MyResponseEntity<String> updateMyPage(@RequestBody MbDTO mbDTO) {
		String updateMyPage = mbService.updateMyPage(mbDTO);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("마이 페이지 수정", updateMyPage));
	}

	// 비밀번호 변경
	@PutMapping("/check/updatePw")
	@ApiOperation("updatePW")
	public MyResponseEntity<String> updatePW(@PathVariable("pw") String pw, @PathVariable("newPw") String newPw) {
		String checkInsert = mbService.updatePw(pw, newPw);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("비밀번호 변경", checkInsert));
	}

	// 아이디 찾기
	@GetMapping("/searchId")
	@ApiOperation("searchID")
	public MyResponseEntity<String> searchId(@PathVariable("nm") String nm, @PathVariable("addr1") String addr1,
			@PathVariable("addr2") String addr2) {
		String searchId = mbService.searchId(nm, addr1, addr2);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("아이디 찾기", searchId));
	}

	// 비밀번호 찾기
	@GetMapping("/searchPw")
	@ApiOperation("searchPW")
	public MyResponseEntity<String> serarchPw(@PathVariable("nm") String nm, @PathVariable("id") String id,
			@PathVariable("addr1") String addr1, @PathVariable("addr2") String addr2) {
		String searchPw = mbService.searchPw(nm, id, addr1, addr2);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("비밀번호 찾기", searchPw));
	}
}
