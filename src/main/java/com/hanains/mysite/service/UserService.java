package com.hanains.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanains.mysite.dao.UserDao;
import com.hanains.mysite.vo.UserVo;

@Service
public class UserService {
	
	@Autowired
	UserDao userDao;
	
	public void join(UserVo vo){
		userDao.insert(vo);
	}
	
	public UserVo login(UserVo vo){
		UserVo userVo = userDao.get(vo);
		return userVo;
	}
	/*
	 * getUser No,나 Email Update 
	 * 회원정보 수정할때 써야할 서비스들 구현 
	 * 미작성
	 * */
}
