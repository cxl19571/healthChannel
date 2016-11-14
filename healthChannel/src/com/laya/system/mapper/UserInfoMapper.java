package com.laya.system.mapper;

import java.util.Map;

import com.laya.advice.model.LoginMapping;
import com.laya.system.model.Role;
import com.laya.system.model.UserInfo;
public interface UserInfoMapper {

	
	  public int isExsit(String userPhone);                  //验证该用户是否已经注册
	  
      public void registerUser(UserInfo user);                //注册用户
      
      public void addDefaultMember(Role role);                //添加默认用户成员
      
      public UserInfo findUserByPhone(String userPhone);               //通过电话号码查询用户
      
      public UserInfo isLogin(UserInfo user);                //判断是否登录
      
      public void updatePassword(Map<String,Object> params);      //修改用户密码
      
      public void addLoginMapping(LoginMapping entity);      //添加本地用户与39用户映射记录
      
      public LoginMapping getMappingByUserId(String userId);         //通过userid查询映射记录
      
      
}
