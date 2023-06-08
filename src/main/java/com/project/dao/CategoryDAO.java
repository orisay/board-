package com.project.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.dto.CategoryDTO;
@Mapper
public interface CategoryDAO {

	List<CategoryDTO> controllerCategory();

	void insertCategory(CategoryDTO categoryDTO);

	void updateMng(CategoryDTO categoryDTO);

	void updateCat(CategoryDTO categoryDTO);

	void deleteCat(String catDomain);

}
