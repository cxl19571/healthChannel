package com.laya.health.service;

import java.util.List;
import java.util.Map;

import com.laya.health.model.Test;

public interface TestService {
	
	public Map<String,Object> addTestResult(Test test);                    //添加检测结果
	
	public Map<String,Object> getTestResult(String userPhone);   //查询检测结果
	
	public List<Test> getAllMembersTestResult(String userPhone,String userId,String targetTime,String currentTime,Integer testType);        //通过手机号码和成员id查询所有检测结果
	
	public void updateTestResult(Test test);               //更新检测结果 
	
	public List<Test> getResultList(String userPhone,String userId,Integer status,Integer testType,String createTime);
	
	public List<Map<String,Object>> test(String userPhone);

}
