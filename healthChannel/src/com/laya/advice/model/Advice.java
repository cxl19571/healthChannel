package com.laya.advice.model;

public class Advice {
	
	private String userId;   //用户id
	private Integer istreat; // 是否看过医生.0表示没看过,1表示看过
	private String content; // 提问的内容,问题描述.
	private Integer sex; // 性别 .1表示男,2表示女
	private Integer age; // 年龄 例如30岁,8个月,20天
	private Integer imagecount; // 上传图片数,默认是9;如果大于0的时候再去获取images参数中的值
	private Integer tomid; // 医生id
	private String ip; // 客户端ip地址
	private Integer mhid; // 病例ID,默认是0
	private Integer sysio; // 客户端设备类型.1表示IOS,2表示Android
	private String tokenId; // 如果用户已经登录,就传递此参数
	private String newkeyname; // 医生诊断结果
	private String symptomdate; // 症状持续时间(当选择没有看过医生时可传递此参数)
	private String verify;       //39认证
	private String memberid;     //39用户成员id
	private String roleId;     //本地成员id
	public Integer getIstreat() {
		return istreat;
	}
	public void setIstreat(Integer istreat) {
		this.istreat = istreat;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Integer getImagecount() {
		return imagecount;
	}
	public void setImagecount(Integer imagecount) {
		this.imagecount = imagecount;
	}
	public Integer getTomid() {
		return tomid;
	}
	public void setTomid(Integer tomid) {
		this.tomid = tomid;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Integer getMhid() {
		return mhid;
	}
	public void setMhid(Integer mhid) {
		this.mhid = mhid;
	}
	public Integer getSysio() {
		return sysio;
	}
	public void setSysio(Integer sysio) {
		this.sysio = sysio;
	}

	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	public String getNewkeyname() {
		return newkeyname;
	}
	public void setNewkeyname(String newkeyname) {
		this.newkeyname = newkeyname;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getVerify() {
		return verify;
	}
	public void setVerify(String verify) {
		this.verify = verify;
	}
	public String getMemberid() {
		return memberid;
	}
	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}
	@Override
	public String toString() {
		return "Advice [userId=" + userId + ", istreat=" + istreat
				+ ", content=" + content + ", sex=" + sex + ", age=" + age
				+ ", imagecount=" + imagecount + ", tomid=" + tomid + ", ip="
				+ ip + ", mhid=" + mhid + ", sysio=" + sysio + ", tokenId="
				+ tokenId + ", newkeyname=" + newkeyname + ", symptomdate="
				+ symptomdate + ", verify=" + verify + ", memberid=" + memberid
				+ ", roleId=" + roleId + "]";
	}
	public String getSymptomdate() {
		return symptomdate;
	}
	public void setSymptomdate(String symptomdate) {
		this.symptomdate = symptomdate;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
	
	
	

}
