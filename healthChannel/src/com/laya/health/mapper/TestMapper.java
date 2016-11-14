package com.laya.health.mapper;

import java.util.List;
import java.util.Map;

import com.laya.health.model.Test;
import com.laya.system.model.Role;

public interface TestMapper {
	
	public void addTestResult(Test test);                //添加检测结果
	
	public Test getTestResult(Map<String,Object> params);           //通过手机号码,用户id查询检测结果
	
	public Map<String,Object> getMapTest(String userId);             //联合查询
	
	public List<Role> getMembersInfo(String userPhone);            //通过手机号查询成员信息
	
	public List<Test> getAllMembersTestResult(Map<String,Object> params);        //通过手机号码查询所有检测结果
	
    public List<Map<String,Object>> test(String userPhone);         
    
    public void updateTestResult(Test test);                      //更新检测结果
    
    public List<Test> getResultList(Map<String,Object> params);
	
}
