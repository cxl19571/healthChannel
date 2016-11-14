package com.laya.advice.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.laya.advice.mapper.AdviceMapper;
import com.laya.advice.model.Question;

@Repository
@Transactional(readOnly=true)
public class AdviceDaoImpl implements AdviceDao {

	@Autowired
	private AdviceMapper mapper;
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
	@Override
	public void addQuestion(Question question) {
		
		this.mapper.addQuestion(question);
	}

	@Override
	public List<Question> getMemberQuestionSet(String roleId,String userId) {
		
		Map<String,Object> params=new HashMap<String,Object>();
		
		params.put("roleId", roleId);
		
		params.put("userId", userId);
		
		return this.mapper.getMemberQuestionSet(params);
	}

	@Override
	public Map<String, Object> getMemberInfo(String tid,String userId) {
		
		Map<String,Object> params=new HashMap<String,Object>();
		
		params.put("tid", tid);
		
		params.put("userId", userId);
		
		return this.mapper.getMemberInfo(params);
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
	@Override
	public void deleteQuestion(String tid) {
		
		this.mapper.deleteQuestion(tid);
		
	}

	@Override
	public Question getQuestionByTid(String tid) {
		
		return this.mapper.getQuestionByTid(tid);
	}



}
