package com.project.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

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
import com.project.dto.MyResponseEntityDTO;
import com.project.dto.mb.MbDTO;
import com.project.dto.mb.MbSessionDTO;
import com.project.dto.mb.SearchInfoDTO;
import com.project.dto.mb.UpdatePwDTO;
import com.project.service.MbService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/mb")
public class MbController {

	@Autowired
	MbService mbService;

	// 회원 가입 성공
	@PostMapping("/")
	@ApiOperation("insertMb")
	public MyResponseEntity<String> insertMb(@RequestBody @Valid MbDTO mbDTO) {
		String insertMb = mbService.insertMb(mbDTO);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("회원 가입 여부", insertMb));
	}

	// 아이디 중복 검사 성공
	@GetMapping("/id-check/{id}")
	@ApiOperation("checkId")
	public MyResponseEntity<String> compareId(@PathVariable("id") @NotBlank String id) {
		String checkId = mbService.compareId(id);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("아이디 중복 확인", checkId));
	}

	// 로그인 성공
	@PostMapping("/login")
	@ApiOperation("login")
	public MyResponseEntity<MbSessionDTO> getLogin(@RequestBody @Valid MbDTO mbDTO) {
		MbSessionDTO checkLogin = mbService.getLogin(mbDTO);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("로그인 여부", checkLogin));
	}

	// 로그아웃 작동
	@DeleteMapping("/logout")
	@ApiOperation("logout")
	public MyResponseEntity<Void> getLogout() {
		mbService.getLogout();
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("로그 아웃"));
	}

	// 마이 페이지 성공
	@GetMapping("/check/mypage")
	@ApiOperation("myPage")
	public MyResponseEntity<MbDTO> getMyPage() {
		MbDTO mbDTO = mbService.getMyPage();
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("마이 페이지", mbDTO));
	}

	// 마이 페이지 수정 성공
	@PutMapping("/check/mypage")
	@ApiOperation("updateMyPage")
	public MyResponseEntity<String> updateMyPage(@RequestBody MbDTO mbDTO) {
		String updateMyPage = mbService.updateMyPage(mbDTO);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("마이 페이지 수정", updateMyPage));
	}

	// 비밀번호 변경 성공
	@PutMapping("/check/pw")
	@ApiOperation("updatePW")
	public MyResponseEntity<String> updatePW(@RequestBody UpdatePwDTO updatePwDTO) {
		String checkInsert = mbService.updatePw(updatePwDTO);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("비밀번호 변경", checkInsert));
	}

	// 아이디 찾기 성공
	@PostMapping("/search-id")
	@ApiOperation("searchID")
	public MyResponseEntity<String> searchId(@RequestBody SearchInfoDTO searchInfoDTO) {
		String searchId = mbService.searchId(searchInfoDTO);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("아이디 찾기", searchId));
	}

	// 비밀번호 찾기 성공
	@PostMapping("/search-pw")
	@ApiOperation("searchPW")
	public MyResponseEntity<String> serarchPw(@RequestBody SearchInfoDTO searchInfoDTO) {
		String searchPw = mbService.searchPw(searchInfoDTO);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("비밀번호 찾기", searchPw));
	}
}
