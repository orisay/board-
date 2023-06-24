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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.common.MyResponseEntity;
import com.project.dto.MyResponseEntityDTO;
import com.project.dto.category.CategoryDTO;
import com.project.service.CategoryService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/cat")
public class CategoryController {

	@Autowired
	CategoryService categoryService;

	// 메인 성공
	@GetMapping("/")
	@ApiOperation("controllerByCategory")
	public MyResponseEntity<List<CategoryDTO>> controllerCategory() {
		List<CategoryDTO> controllerCategory = categoryService.controllerCategory();
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("관리자 권한", controllerCategory));
	}

	// 카테고리 추가 성공
	@PostMapping("/")
	@ApiOperation("createCategory")
	public MyResponseEntity<String> insertCategory(@RequestBody @Valid CategoryDTO categoryDTO) {
		String insertCheck = categoryService.insertCategory(categoryDTO);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("카테고리 추가 여부" + insertCheck));
	}

	// 카테고리 관리자 선택 성공
	@PutMapping("/{catDomain}/mng/{id}")
	@ApiOperation("updateCategoryMng")
	public MyResponseEntity<String> updateMng(@PathVariable("catDomain")@NotBlank String catDomain,
			@PathVariable("id")@NotBlank String id) {
		String updateCheckId = categoryService.updateMng(catDomain, id);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("등록된 매니저", updateCheckId));
	}

	// 카테고리 이름 변경 성공
	@PutMapping("/{catDomain}/cat/{cat}")
	@ApiOperation("updateCategoryName")
	public MyResponseEntity<String> updateCat(@PathVariable("catDomain")@NotBlank String catDomain,
			@PathVariable("cat")@NotNull String cat) {
		String updateCheckCat = categoryService.updateCat(catDomain,cat);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("변경된 카테고리 이름" + updateCheckCat));
	}

	// 카테고리 삭제 성공
	// 관련 게시글 전부  DB 트리거로 백업 성공
	@DeleteMapping("/{catDomain}")
	@ApiOperation("deleteCategory")
	public MyResponseEntity<Void> deleteCat(@PathVariable("catDomain")@NotBlank String catDomain) {
		String deleteCheckCat = categoryService.deleteCat(catDomain);
		return new MyResponseEntity<>(new MyResponseEntityDTO<>("삭제 된 카테고리" + deleteCheckCat));
	}
}
