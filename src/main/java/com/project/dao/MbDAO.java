package com.project.dao;

import org.apache.ibatis.annotations.Mapper;

import com.project.dto.MbDTO;
import com.project.dto.UpdatePwDTO;
@Mapper
public interface MbDAO {

	Integer insertMb(MbDTO mbDTO);

	void insertRole(MbDTO mbDTO);

	String getId(String id);

	Integer getLogin(MbDTO mbDTO);

	MbDTO getMyPage(String mbId);

	Integer updateMyPage(MbDTO mbDTO);

	Integer updatePw(UpdatePwDTO updatePwDTO);

	String searchId(MbDTO mbDTO);

	String searchPw(MbDTO mbDTO);

}
