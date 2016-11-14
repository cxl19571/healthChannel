package com.laya.system.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.laya.system.model.UserInfo;

public interface UserService {
	
	public Boolean isExsit(String userPhone);             //判断用户是否存在
	
	public Map<String,Object>  registerUser(UserInfo user,String code,String appkey,String zone);        //注册用户
	
	public Map<String,Object> isLogin(String userPhone,String userPassword,HttpServletRequest request);   //判断用户是否登陆成功

	public UserInfo getUserPasswordByPhone(String userPhone);      //通过手机号查询用户
	  
	public Map<String,Object> updatePassword(String userPhone,String oldPassword,String newPassword);      //修改用户密码
	
	public Map<String,Object> resetPassword(String userPhone,String code,String password,String appkey,String zone);            //重置密码
}
