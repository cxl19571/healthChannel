package com.laya.advice.dao;

import java.util.List;
import java.util.Map;

import com.laya.advice.model.Question;

public interface AdviceDao {

    public void addQuestion(Question question);           //添加问题
	
    public List<Question> getMemberQuestionSet(String roleId,String userId);         //查询问题列表
    
    public Map<String,Object> getMemberInfo(String tid,String userId);         //查询用户信息
    
    public void deleteQuestion(String tid);                       //删除问题
    
    public Question getQuestionByTid(String tid);                    //通过tid查询问题
	
}
