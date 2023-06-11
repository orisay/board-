package com.project.dao;

import org.apache.ibatis.annotations.Mapper;

import com.project.dto.MbDTO;
import com.project.dto.SearchInfoDTO;
import com.project.dto.UpdatePwDTO;
@Mapper
public interface MbDAO {

	Integer insertMb(MbDTO mbDTO);

	void insertRole(MbDTO mbDTO);

	String getId(String id);

	MbDTO getLogin(MbDTO mbDTO);

	MbDTO getMyPage(String mbId);

	Integer updateMyPage(MbDTO mbDTO);

	Integer updatePw(UpdatePwDTO updatePwDTO);

	String searchId(SearchInfoDTO searchInfoDTO);

	String searchPw(SearchInfoDTO searchInfoDTO);

}
