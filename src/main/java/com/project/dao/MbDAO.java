package com.project.dao;

import com.project.dto.MbDTO;

public interface MbDAO {

	String insertMb(MbDTO mbDTO);

	Integer getId(String id);

	Integer getLogin(MbDTO mbDTO);

	MbDTO getMyPage(String mbId);

	Integer updateMyPage(MbDTO mbDTO);

}
