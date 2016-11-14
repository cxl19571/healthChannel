package com.laya.advice.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.laya.advice.model.Question;


public interface AdviceMapper {

	public void addQuestion(Question question);           //添加问题
	
	public List<Question> getMemberQuestionSet(Map<String,Object> params);         //查询问题列表
	
	public Map<String,Object> getMemberInfo(Map<String,Object> params);         //查询用户信息
	
	public void deleteQuestion(@Param(value = "tid") String tid);             //删除提问
	
	public Question getQuestionByTid(String tid);                    //通过tid查询问题
	
}
