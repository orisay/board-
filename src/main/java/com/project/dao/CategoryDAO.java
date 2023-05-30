package com.project.dao;

import java.util.List;

import com.project.dto.CategoryDTO;

public interface CategoryDAO {

	List<CategoryDTO> controllerCategory();

	void insertCategory(CategoryDTO categoryDTO);

	void updateMng(CategoryDTO categoryDTO);

	void updateCat(CategoryDTO categoryDTO);

	void deleteCat(String catDomain);

}
