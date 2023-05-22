package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.config.MyResponseEntity;
import com.project.dto.CategoryDTO;
import com.project.dto.MyResponseEntityDTO;
import com.project.service.CategoryService;

import io.swagger.annotations.ApiOperation;

@RestController
public class CategoryController {

	@Autowired
	CategoryService categoryService;
	// cat 생성,수정(spring security) 삭제(db role)
	// 수정 내 = (카테고리 관리자 선택, 카테고리 이름 변경)
	// cat 화면 표?가 좋을듯

	// 메인
	@GetMapping("/cat/controller")
	@ApiOperation("controllerCategory")
	public MyResponseEntity<List<CategoryDTO>> controllerCategory() {
		List<CategoryDTO> controllerCategory = categoryService.controllerCategory();
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("관리자 권한", controllerCategory));
	}

	// 카테고리 추가
	@PostMapping("/cat/controller/insert")
	@ApiOperation("insertCategory")
	public MyResponseEntity<String> insertCategory(@RequestBody CategoryDTO categoryDTO) {
		String insertCheck = categoryService.insertCategory(categoryDTO);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("카테고리 추가 여부", insertCheck));
	}

	// 카테고리 관리자 선택
	@PutMapping("/cat/controller/updateAdmin/{catDomain}/{id}")
	@ApiOperation("updateCategoryAdmin")
	public MyResponseEntity<String> updateAdmin(@PathVariable("catDomain")String catDomain,
			@PathVariable("id") String id) {
		String updateCheckId = categoryService.updateMng(catDomain, id);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("등록된 매니저", updateCheckId));
	}

	// 카테고리 이름 변경
	@PutMapping("/cat/controller/updateCat/{catDomain}/{cat}")
	@ApiOperation("updateCategoryName")
	public MyResponseEntity<String> updateCat(@PathVariable("catDomain")String catDomain,
			@PathVariable("cat") String cat) {
		String updateCheckCat = categoryService.updateCat(catDomain,cat);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("변경된 카테고리 이름" + updateCheckCat));
	}

	// 카테고리 삭제
	// 관련 게시글 전부  DB 트리거로 백업
	@DeleteMapping("/cat/controller/deleteCat/{catDomain}")
	@ApiOperation("deleteCategory")
	public MyResponseEntity<Void> deleteCat(@PathVariable("catDomain") String catDomain) {
		String deleteCheckCat = categoryService.deleteCat(catDomain);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("삭제 된 카테고리" + deleteCheckCat));
	}
}
