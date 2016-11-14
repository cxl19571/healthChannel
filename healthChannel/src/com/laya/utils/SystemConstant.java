
package com.laya.utils;

import java.io.File;

public class SystemConstant {

	// app短信验证地址
	public static final String MESSAGE_VERIFY_URL = "https://web.sms.mob.com/sms/verify";
	
	// 39健康网接口地址
	public static final String LOGIN_39_URL = "http://api2.my.39.net/api/ThirdService/GlobalLogin";
	
	//39问医生提问接口地址
	public static final String POST_QUESTION_URL="http://ask-apiv3.39.net/app/topics.add";
	
	//39问医生问题列表地址
	public static final String QUESTION_LIST_URL="http://ask-apiv3.39.net/app/topics.mytopicv2";
	
	 //39问医生问题详情接口地址(正式)    http://askapi.39.net/app/topics.gettopic
	public static final String QUESTION_INFO_URL="http://ask-apiv3.39.net/app/topics.gettopic";
	
	//39问医生相关问题接口地址  
	public static final String RELATED_QUESTION_URL="http://ask-apiv3.39.net/app/topics.relatedtopicv2";
	
	//39问医生追问问题接口地址
	public static final String ADD_QUESTION_URL="http://ask-apiv3.39.net/app/topics.nquiry";
	
	//39问医生appkey
	public static final String APP_KEY="askAPP";
	
	//39问医生secret
	public static final String SECRET="73613865e1b5551589bf6e61e69f4050";
	
	public static final String REMOTE_SERVER_ERROR="请求远程服务器失败";
	
	//远程服务器请求失败状态码
	public static final Integer SERVER_ERROR_CODE=500;
	
	// 文件地址
	public static final String FILE_URL = "E:"+File.separator+"tomcat"+File.separator+"apache-tomcat-7.0.26"+File.separator+"uploadFolder";  

	//文件过大异常
	public static final String File_SIZE_ERROR="单个图片大小不能超过1MB";
	
	public static final String File_VERIFY_ERROR="图片格式错误";
	
	// 请求类型为web端
	public static final String CONSTANT_WEB = "WEB";

	// 请求类型为App端
	public static final String CONSTANT_APP = "APP";

	// 请求类型为广电端
	public static final String CONSTANT_SARFT = "SARFT";

	// 移动端请求超时
	public static final String MOBILE_TIME_OUT = "MOBILE_TIME_OUT";

	// 响应成功代码
	public static final Integer RESPONSE_SUCCESS = 200;

	// 用户注册,已经存在
	public static final String PHONE_IS_EXIST = "此电话号码已经存在";
	
	//用户已经登录
	public static final Integer USER_EXIST=20000;

	// 添加出现异常
	public static final String ADD_Exception = "添加出现异常";

	// 参数为空
	public static final String PARAM_IS_EMPTY = "参数为空";

	// 短信验证错误
	public static final String MESSAGE_VERIFY_ERROR = "短信验证错误";

	// 数据加密错误
	public static final String DES_ENCRYPT_ERROR = "数据加密错误";

	// 用户名或密码错误
	public static final Integer USERNAME_OR_PASSWORD_ERROR = 10086;

	// 血糖
	public static final String HC_BLOOD_SUGAR = "01";

	// 胆固醇
	public static final String HC_CHOLESTEROL = "02";

	// 甘油三酯
	public static final String HC_TRIGLYCERIDE = "04";

	// 尿酸
	public static final String HC_URIC_ACID = "08";

	// 空腹
	public static final String KONG_FU ="01";

	//早餐后
	public static final String ZAO_CAN_HOU ="02";
	
	//午餐前
	public static final String WU_CAN_QIAN="03";

	//午餐后
	public static final String WU_CAN_HOU="04";
	
	//晚餐前
	public static final String WAN_CAN_QIAN="05";
	
	//晚餐后
	public static final String WAN_CAN_HOU="06";
	
	// 睡前
	public static final String SHUI_QIAN = "07";
	

}
