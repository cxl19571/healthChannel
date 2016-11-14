package com.laya.system.dao;

import java.util.List;
import java.util.Map;

import com.laya.system.model.Role;

public interface RoleDao {
	public void addRole(Role role); // 添加角色

	public List<Role> getRoleList(String parentId); // 查询默认角色下的所有家庭成员
	
	public void deleteRole(String id) ;        //通过成员id删除
	
	public Integer findRoleIdByPhone(String userPhone);  //通过手机号查询id
	
	public void  setDefaultRole(Map<String, Integer> params);            //设置默认成员
	
	public void editMembers(Role role);                 //编辑成员
	
	public Role findRoleById(String id);             //通过id查询成员信息 
	
	 public void deleteTestResult(Map<String,Object> params);    //删除成员检测数据
}
