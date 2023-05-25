package com.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dao.ManagerDAO;

@Service
public class ManagerService {
	@Autowired
	ManagerDAO adminDAO;

}
