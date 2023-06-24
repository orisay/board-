package com.project.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.dto.mb.MbDTO;
import com.project.dto.mb.MbRoleDTO;
import com.project.dto.mb.SearchInfoDTO;
import com.project.dto.mb.UpdatePwDTO;
@Mapper
public interface MbDAO {

	Integer insertMb(MbDTO mbDTO);

	void insertRole(String checkId);

	String compareId(String id);

	MbDTO getLogin(MbDTO mbDTO);

	MbDTO getMyPage(String mbId);

	Integer updateMyPage(MbDTO mbDTO);

	Integer updatePw(UpdatePwDTO updatePwDTO);

	String searchId(SearchInfoDTO searchInfoDTO);

	String searchPw(SearchInfoDTO searchInfoDTO);

	List<MbRoleDTO> getRoleList(String id);

}
