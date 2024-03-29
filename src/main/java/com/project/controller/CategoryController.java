package com.project.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.common.MyResponseEntity;
import com.project.dto.MyResponseEntityDTO;
import com.project.dto.board.BoardDTO;
import com.project.dto.category.CategoryDTO;
import com.project.service.CategoryService;

import io.swagger.annotations.ApiOperation;

@RestController
public class CategoryController {

	@Autowired
	CategoryService categoryService;

	// 메인 성공
	@GetMapping("/cat")
	@ApiOperation("viewMainCategory")
	public MyResponseEntity<List<CategoryDTO>> viewMainCategory() {
		List<CategoryDTO> controllerCategory = categoryService.viewMainCategory();
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("관리자 권한", controllerCategory));
	}

	// 카테고리 추가 성공
	@PostMapping("/cat")
	@ApiOperation("createCategory")
	public MyResponseEntity<Void> createCategory(@RequestBody @Valid CategoryDTO categoryDTO) {
		String resultMesg = categoryService.createCategory(categoryDTO);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("카테고리 추가 여부" + resultMesg));
	}

	// 투표로 인한 카테고리 추가
	@PostMapping("/vote-cat/{point}")
	@ApiOperation("createCategoryByMember")
	public MyResponseEntity<Void> createCategoryByVote(@RequestBody @Valid BoardDTO boardDTO,
			@PathVariable("point") @NotNull Integer creationPoint) {
		String resultMesg = categoryService.createCategoryByVote(boardDTO, creationPoint);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("카테고리 추가 여부" + resultMesg));
	}

	// 카테고리 관리자 선택 성공
	@PutMapping("/cat/{catDomain}/mng/{id}")
	@ApiOperation("updateCategoryManager")
	public MyResponseEntity<Void> updateMng(@PathVariable("catDomain") @NotBlank String catDomain,
			@PathVariable("id") @NotBlank String id) {
		String resultMesg = categoryService.updateMng(catDomain, id);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("매니저 등록 " + resultMesg));
	}

	// 카테고리 이름 변경 성공
	@PutMapping("/cat/{catDomain}/cat/{cat}")
	@ApiOperation("updateCategoryName")
	public MyResponseEntity<Void> updateCat(@PathVariable("catDomain") @NotBlank String catDomain,
			@PathVariable("cat") @NotBlank String cat) {
		String resultMesg = categoryService.updateCat(catDomain, cat);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("카테고리 이름 변경 " + resultMesg));
	}

	// 카테고리 삭제 성공
	// 관련 게시글 전부 DB 트리거로 백업 성공
	@DeleteMapping("/cat/{catDomain}")
	@ApiOperation("deleteCategory")
	public MyResponseEntity<Void> deleteCat(@PathVariable("catDomain") @NotBlank String catDomain) {
		String resultMesg = categoryService.deleteCat(catDomain);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("카테고리 삭제 " + resultMesg));
	}
}
