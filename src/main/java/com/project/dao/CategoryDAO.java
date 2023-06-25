package com.project.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.dto.category.CategoryDTO;
import com.project.dto.mb.InsertUserRoleDTO;
@Mapper
public interface CategoryDAO {

	List<CategoryDTO> controllerCategory();

	Integer insertCategory(CategoryDTO categoryDTO);

	Integer insertCategoryByMb(CategoryDTO categoryDTO);

	Integer updateMng(CategoryDTO categoryDTO);

	Integer updateCat(CategoryDTO categoryDTO);

	Integer deleteCat(String catDomain);

	Integer insertMngRole(InsertUserRoleDTO insertUserRoleDTO);

}
