package com.laya.system.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.laya.system.mapper.RoleMapper;
import com.laya.system.model.Role;

@Repository
@Transactional(readOnly=true)
public class RoleDaoImpl implements RoleDao {

	@Autowired
	private RoleMapper mapper;

	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
	@Override
	public void addRole(Role role) {

		this.mapper.addRole(role);
	}

	@Override
	public List<Role> getRoleList(String parentId) {

		return this.mapper.getRoleList(parentId);
	}
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
	@Override
	public void deleteRole(String id){

		this.mapper.deleteRole(id);
	}

	@Override
	public Integer findRoleIdByPhone(String userPhone) {

		return this.mapper.findRoleIdByPhone(userPhone);
	}
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
	@Override
	public void setDefaultRole(Map<String, Integer> params) {
		
		this.mapper.setDefaultRole(params);

	}
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
	@Override
	public void editMembers(Role role) {
		
		 this.mapper.editMembers(role);
	}

	@Override
	public Role findRoleById(String id) {
		
		return this.mapper.findRoleById(id);
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
	@Override
	public void deleteTestResult(Map<String, Object> params) {
		this.mapper.deleteTestResult(params);
		
	}

}
