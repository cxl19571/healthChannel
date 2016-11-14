package com.laya.health.model;

/**
 * 健康检测
 * @author yc
 *
 */
public class Test {
	
	private String id;                //id标识
	private String userId;             //成员用户id
	private String userPhone;           //手机号码
	private Integer status;             //检测状态
	private String createTime;         //检测时间
	private String result;             //检测结果
	private Integer testType;          //检测类型
	private String  temperature;       //温度
	
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	public Integer getTestType() {
		return testType;
	}
	public void setTestType(Integer testType) {
		this.testType = testType;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Override
	public String toString() {
		return "Test [id=" + id + ", userId=" + userId + ", userPhone="
				+ userPhone + ", status=" + status + ", createTime="
				+ createTime + ", result=" + result + ", testType=" + testType
				+ ", temperature=" + temperature + "}]";
	}



}
