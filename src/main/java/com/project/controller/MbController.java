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

import com.project.dto.MbDTO;
import com.project.service.MbService;

import io.swagger.annotations.ApiOperation;

@RestController
public class MbController {
	@Autowired
	MbService mbService;

	@PostMapping("/mb/insert")
	@ApiOperation("mbInsert")
	public ResponseEntity<MbDTO> mbInsert(@RequestBody MbDTO mbDTO) {
		Integer mbInsert = mbService.mbInsert(mbDTO);
		return null;
	}

	@GetMapping("/mb/{id}")
	@ApiOperation("idSearch")
	public ResponseEntity<String> idSearch(@PathVariable("id") String id) {
		Integer idSearch = mbService.idSearch(id);
		return null;
	}

	@PostMapping("/login")
	@ApiOperation("login")
	public ResponseEntity<MbDTO> login(@RequestBody MbDTO mbDTO) {
		MbDTO login = mbService.login(mbDTO);
		return null;
	}

	@DeleteMapping("/check/logout")
	@ApiOperation("logout")
	public ResponseEntity<Void>logout(){
		return null;
	}

	@GetMapping("/check/myPage")
	@ApiOperation("myPage")
	public ResponseEntity<MbDTO>myPage(){
		MbDTO mbDTO = mbService.myPage();
		return null;
	}

	@PutMapping("/check/update")
	@ApiOperation("myPageUpdate")
	public ResponseEntity<Void>myPageUpDate(@RequestBody MbDTO mbDTO){
		Integer myPageUpDate = mbService.myPageUpDate(mbDTO);
		return null;
	}
}
