package com.laya.system.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.laya.advice.model.LoginMapping;
import com.laya.system.mapper.UserInfoMapper;
import com.laya.system.model.Role;
import com.laya.system.model.UserInfo;
@Repository
@Transactional(readOnly=true)
public class UserDaoImpl implements UserDao {
	    
	    @Autowired
    	private UserInfoMapper mapper;

	@Override
	public Integer isExsit(String userPhone) {

		return this.mapper.isExsit(userPhone);
	}


	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
	@Override
	public void registerUser(UserInfo user) {
		
		     this.mapper.registerUser(user);
		
	}


	@Override
	public UserInfo isLogin(UserInfo user) {
		
		return this.mapper.isLogin(user);
	}



	@Override
	public UserInfo findUserByPhone(String userPhone) {
		
		return this.mapper.findUserByPhone(userPhone);
	}


	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
	@Override
	public void updatePassword(Map<String, Object> params) {
		
		this.mapper.updatePassword(params);
		
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
	@Override
	public void addLoginMapping(LoginMapping entity) {
		
		this.mapper.addLoginMapping(entity);
		
	}


	@Override
	public LoginMapping getMappingByUserId(String userId) {
		
		return this.mapper.getMappingByUserId(userId);
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
	@Override
	public void addDefaultMember(Role role) {
		
		this.mapper.addDefaultMember(role);
		
	}












}
