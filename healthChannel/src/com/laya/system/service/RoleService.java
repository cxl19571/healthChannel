package com.laya.system.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.laya.system.model.Role;

public interface RoleService {
	
	public Map<String,Object> addFamilyMembers(Role role,MultipartFile file,HttpServletRequest request);                       //添加家庭成员

	public List<Role> getRoleList(String userPhone);        //查询默认角色下的所有家庭成员
	
	public void deleteRole(String id,String userPhone);                 //删除角色
	
	public void  setDefaultRole(String preDefaultId,String currentDefaultId);              //设置默认成员 
	
	public Map<String,Object> editMembers(Role role,MultipartFile file,HttpServletRequest request);                       //添加家庭成员
	
	public Role findRoleById(String id);             //通过id查询成员信息 
}
