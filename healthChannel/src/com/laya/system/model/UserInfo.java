package com.laya.system.model;

import java.io.Serializable;

/**
 * 用户模型
 * 
 * @author yc
 *
 */
public class UserInfo implements Serializable {

	private String id; // 用户id
	private String userName; // 用户名称
	private String userPassword; // 用户密码
	private String userPhone; // 用户联系方式
	private String userEmail; // 用户邮箱
	private String registerTime; // 注册时间
	private String userType; // 用户类型
	private String description; // 描述
	private String sarft_userId; //广电用户卡号
	private String tokenId;     //sessionId
	private String pid;     //39登录认证通行证pid
	private String verify;  //39登录通行证verify

	private static final long serialVersionUID = 1L;


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}



	public String getUserPassword() {
		return userPassword;
	}



	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}



	public String getUserPhone() {
		return userPhone;
	}



	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}



	public String getUserEmail() {
		return userEmail;
	}



	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}



	public String getRegisterTime() {
		return registerTime;
	}



	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}



	public String getUserType() {
		return userType;
	}



	public void setUserType(String userType) {
		this.userType = userType;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}


	public String getSarft_userId() {
		return sarft_userId;
	}



	public void setSarft_userId(String sarft_userId) {
		this.sarft_userId = sarft_userId;
	}


	public String getTokenId() {
		return tokenId;
	}



	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}


	public String getPid() {
		return pid;
	}


	public void setPid(String pid) {
		this.pid = pid;
	}


	public String getVerify() {
		return verify;
	}


	public void setVerify(String verify) {
		this.verify = verify;
	}


	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", userName=" + userName
				+ ", userPassword=" + userPassword + ", userPhone=" + userPhone
				+ ", userEmail=" + userEmail + ", registerTime=" + registerTime
				+ ", userType=" + userType + ", description=" + description
				+ ", sarft_userId=" + sarft_userId + ", tokenId=" + tokenId
				+ ", pid=" + pid + ", verify=" + verify + "]";
	}
}
