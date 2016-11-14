package com.laya.system.model;

/**
 * 角色模型
 * 
 * @author yc
 *
 */
public class Role {
	private String id; // 标识
	private Integer userType; // 用户类型
	private String createTime; // 创建时间
	private String parentId; // 父id,可以是手机号 也可是广电客户编号
	private String userName; // 成员用户名称
	private String headPicture; // 头像地址
	private String userSex; // 性别
	private Integer isRegister;   //是否为注册用户
	
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getHeadPicture() {
		return headPicture;
	}
	public void setHeadPicture(String headPicture) {
		this.headPicture = headPicture;
	}
	public String getUserSex() {
		return userSex;
	}
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getIsRegister() {
		return isRegister;
	}
	public void setIsRegister(Integer isRegister) {
		this.isRegister = isRegister;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "role[id=" + id + "userType="+ userType+",createTime="+createTime+",userSex="+userSex+",userName="+userName+",headpicture="+headPicture+
				",parentId="+parentId+"]";
	}
}
