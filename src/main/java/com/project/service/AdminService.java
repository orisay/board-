package com.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dao.AdminDAO;

@Service
public class AdminService {
	@Autowired
	AdminDAO adminDAO;

}
