package com.laya.advice.model;
/**
 * 39健康网登录映射模型
 * @author yc
 *
 */
public class LoginMapping {

	private Integer id;
	private String userId;        //拉雅用户id
	private String mappingId;      //39健康网id
	private String verify;         //39认证
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMappingId() {
		return mappingId;
	}
	public void setMappingId(String mappingId) {
		this.mappingId = mappingId;
	}
	public String getVerify() {
		return verify;
	}
	public void setVerify(String verify) {
		this.verify = verify;
	}
	@Override
	public String toString() {
		return "LoginMapping [id=" + id + ", userId=" + userId + ", mappingId="
				+ mappingId + ", verify=" + verify + "]";
	}



}
