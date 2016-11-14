package com.laya.health.dao;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.laya.health.mapper.TestMapper;
import com.laya.health.model.Test;
import com.laya.system.model.Role;
@Repository
@Transactional(readOnly=true)
public class TestDaoImpl implements TestDao {

	@Autowired
	private TestMapper mapper;
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
	@Override
	public void addTestResult(Test test) {
	
		this.mapper.addTestResult(test);
	}

	@Override
	public Test getTestResult(Map<String,Object> params) {
		
		
		return this.mapper.getTestResult(params);
	}

	@Override
	public Map<String, Object> getMapTest(String userId) {
	
		return this.mapper.getMapTest(userId);
	}

	@Override
	public List<Role> getMembersInfo(String userPhone) {
	
		return this.mapper.getMembersInfo(userPhone);
	}

	@Override
	public List<Test> getAllMembersTestResult(Map<String,Object> params) {
		
		
		return this.mapper.getAllMembersTestResult(params);
	}

	@Override
	public List<Map<String, Object>> test(String userPhone) {
		
		
		return this.mapper.test(userPhone);
	}
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
	@Override
	public void updateTestResult(Test test) {
		
		this.mapper.updateTestResult(test);
		
	}

	@Override
	public List<Test> getResultList(Map<String, Object> params) {
		
		return this.mapper.getResultList(params);
	}



}
