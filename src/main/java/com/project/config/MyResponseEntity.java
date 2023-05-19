package com.project.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.dto.MyResponseEntityDTO;

public class MyResponseEntity<T> extends ResponseEntity<MyResponseEntityDTO<T>>{

	//본문에 데이터 담을 필요 없는 것들 insert update
	public MyResponseEntity() {
		super(new MyResponseEntityDTO<T>(), HttpStatus.OK);
	}
	//삭제 요청시 상태값만 반환할 때 필요함 인자값으로 상태값만 받음
	public MyResponseEntity(HttpStatus status) {
		super(status);
	}
	//상태코드와 함께 데이터 전달할 때 select 상태코드 변경 가능
	public MyResponseEntity(MyResponseEntityDTO<T> body, HttpStatus status) {
		super(body, status);
	}
	//데이터 전달
	public MyResponseEntity(MyResponseEntityDTO<T> body) {
		super(body, HttpStatus.OK);
	}
}
