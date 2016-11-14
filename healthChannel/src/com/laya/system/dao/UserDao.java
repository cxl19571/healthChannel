package com.laya.system.dao;

import java.util.Map;

import com.laya.advice.model.LoginMapping;
import com.laya.system.model.Role;
import com.laya.system.model.UserInfo;
/**
 * DAO
 * @author 
 * @time 2015.5.15
 */

public interface UserDao {
	/**
	 * 验证用户是否存在
	 * @param user
	 * @return 
	 */
	
	public Integer isExsit(String userPhone);
	/**
	 * 注册用户
	 * 
	 */
	public void registerUser(UserInfo user);

	/**
	 * 通过用户名,密码判断是否登录
	 * @param map
	 * @return
	 */
	public UserInfo isLogin(UserInfo user);
	
	/**
	 * 通过手机号查询用户信息
	 * @param userPhone
	 * @return
	 */
	  public UserInfo findUserByPhone(String userPhone); 
	  
	  
	  /**
	   * 修改用户密码
	   * @param params
	   */
	  public void updatePassword(Map<String,Object> params);      
	  
	  
     /**
      * 添加本地用户与39用户映射记录
      * @param entity
      */
	  public void addLoginMapping(LoginMapping entity);      

      /**
       * 查询映射记录
       * @param userId
       * @return
       */
	  public LoginMapping getMappingByUserId(String userId);    
	  
	  /**
	   * 添加默认用户成员
	   * @param role
	   */
	  public void addDefaultMember(Role role);                
}
