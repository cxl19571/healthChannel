package com.laya.health.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.laya.health.model.Test;
import com.laya.health.service.TestService;
import com.laya.utils.SystemConstant;


/**
 * 检测数据接口
 * 
 * @author yc
 *
 */

@Controller
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	private TestService service;

	/**
	 * 添加检测结果
	 * @param test
	 * @param tokenId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addTestResult", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addTestResult(Test test,@RequestParam(value="tokenId")String tokenId,HttpServletRequest request) {
		
		Map<String, Object> result = new HashMap<String, Object>();
	  
		 if(request.getSession().getId().equals(tokenId)){    //验证登录是否失效
			 
			 if(test!=null){
				 
				 result=this.service.addTestResult(test);
			 }
		 }else{
			 
			   result.put("Status", SystemConstant.MOBILE_TIME_OUT);
		 }

		return result;

	}

	/**
	 * 获取家庭成员检测数据用于显示成员列表
	 * @param userPhone
	 * @param tokenId
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getMembersTestResult")
	@ResponseBody
	public Map<String, Object> membersTestResult(
			@RequestParam(value = "userPhone") String userPhone,
			@RequestParam(value="tokenId")String tokenId,HttpServletRequest request) {

		Map<String, Object> result = new HashMap<String, Object>();

		if(request.getSession().getId().equals(tokenId)){   //验证登录是否失效
			
			if (!StringUtils.isEmpty(userPhone)) {

				result = this.service.getTestResult(userPhone);
			}
		}else{
			
			   result.put("Status", SystemConstant.MOBILE_TIME_OUT);
		}

		return result;
	}

	/**
	 * 获取指定家庭成员的检测数据
	 * @param userPhone
	 * @param userId
	 * @param tokenId
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getAllMembersTestResult")
	@ResponseBody
	public Map<String, Object> getAllMembersTestResult(
			@RequestParam(value = "userPhone") String userPhone,
			@RequestParam(value = "userId") String userId,
			@RequestParam(value="testType",required=false,defaultValue="1")Integer testType,
			@RequestParam(value="targetTime",required=false)String targetTime,
			@RequestParam(value="currentTime",required=false)String currentTime,
			@RequestParam(value="tokenId")String tokenId,
			HttpServletRequest request) {

		Map<String, Object> result = new HashMap<String, Object>();

		if(request.getSession().getId().equals(tokenId)){    //验证登录是否失效
			
			if (!StringUtils.isEmpty(userPhone)) {
				
				if (userId != null) {
					
					List<Test> testList = new ArrayList<Test>();
					
					testList = this.service.getAllMembersTestResult(userPhone,
							userId,targetTime,currentTime,testType);
					
					result.put("testList", testList);
				}
			}
		}else{
			       result.put("Status", SystemConstant.MOBILE_TIME_OUT);
		}
		  
	

		return result;

	}
	


}
