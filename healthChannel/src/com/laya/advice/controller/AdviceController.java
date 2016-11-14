package com.laya.advice.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.DecoderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.laya.advice.model.Advice;
import com.laya.advice.service.AdviceService;
import com.laya.system.model.UserInfo;
import com.laya.utils.SystemConstant;
@Controller
@RequestMapping("/advice")
public class AdviceController {

	@Autowired
	private AdviceService service;

	/**
	 * 提问接口
	 * 
	 * @param advice
	 * @param request
	 * @param fileSet
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/postQuestion")
	@ResponseBody
	public Map<String, Object> advice(
			Advice advice, 
			HttpServletRequest request,
			@RequestParam(value = "file", required = false) MultipartFile[] fileSet,
			@ModelAttribute("userInfo") UserInfo user) {
		 
		Map<String,Object> result=new HashMap<String,Object>();
		
		if (advice != null) {

			if (!StringUtils.isEmpty(advice.getTokenId())) { // 判断是否登录

				if (request.getSession().getId().equals(advice.getTokenId())) { // 验证登录是否失效

					try {

						if (user != null) {

							if (!StringUtils.isEmpty(user.getPid())) {

								advice.setMemberid(user.getPid());
							}

							if (!StringUtils.isEmpty(user.getVerify())) {

								advice.setVerify(user.getVerify());
							}
							if (!StringUtils.isEmpty(user.getUserPhone())) {

								advice.setUserId(user.getUserPhone());
							}

							result = this.service.startAdvice(advice, fileSet);

						}

					} catch (Exception e) {

						e.printStackTrace();
					}

				} else {

					result.put("Status", SystemConstant.MOBILE_TIME_OUT);
				}

			}

		}

		return result;
	}


	/**
	 * 获取提问列表
	 * 
	 * @param request
	 * @param user
	 * @param roleId
	 * @param tokenId
	 * @return
	 */

	@RequestMapping(value = "/getQuestionSet")
	@ResponseBody     
	public Map<String, Object> getQuestionSet(HttpServletRequest request,
			@ModelAttribute("userInfo") UserInfo user,
			@RequestParam(value = "roleId") String roleId,
			@RequestParam(value = "tokenId") String tokenId 
			) {

		Map<String, Object> result = new HashMap<String, Object>();
		
		if (!StringUtils.isEmpty(tokenId)) { // 验证登录是否失效

			if (request.getSession().getId().equals(tokenId)) {

				if (!StringUtils.isEmpty(roleId)) {

					if (user != null) {

						String pid = user.getPid(); // 获取session中的pid,userid,verify

						String userId = user.getUserPhone();

						String verify = user.getVerify();

						if (!(StringUtils.isEmpty(pid)
								&& StringUtils.isEmpty(userId) && StringUtils
									.isEmpty(verify))) {

							result = this.service.getQuestionSet(roleId, pid,
									verify, userId);

						}else{
							
							result.put("Status", SystemConstant.MOBILE_TIME_OUT);
						}
					}
				}

			} else {

				result.put("Status", SystemConstant.MOBILE_TIME_OUT);
			}
		}
		return result;
	}

	/**
	 * 获取问题详情接口
	 * 
	 * @param tid
	 * @param user
	 * @param tokenId
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getQuestionInfo")
	@ResponseBody    
	public Map<String, Object> getQuestionInfo(
			@RequestParam(value = "tid") String tid,
			@ModelAttribute("userInfo") UserInfo user,
			@RequestParam(value = "tokenId") String tokenId ,
			HttpSession session) {

		Map<String, Object> result = new HashMap<String, Object>();

		if (!StringUtils.isEmpty(tokenId)) {
			// 验证登录是否失效
			if (session.getId().equals(tokenId)) {

				if (!StringUtils.isEmpty(tid)) {

					if (user != null) {

						String pid = user.getPid();

						String userId = user.getUserPhone();

						if (!(StringUtils.isEmpty(pid) && StringUtils
								.isEmpty(userId))) {

							try {

								result = this.service.getQuestionInfo(tid, pid,
										userId);
								
							} catch (DecoderException e) {

								e.printStackTrace();
							}
						}else{
							result.put("Status", SystemConstant.MOBILE_TIME_OUT);
						}

					}
				}
			} else {

				result.put("Status", SystemConstant.MOBILE_TIME_OUT);
			}

		}

		return result;
	}

	/**
	 * 追加问题接口
	 * @param tid
	 * @param replyId
	 * @param content
	 * @param user
	 * @param tokenId
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/addQuestion")
	@ResponseBody   
    public Map<String, Object> addQuestion(
			@RequestParam(value = "tid") String tid,
			@RequestParam(value = "replyId") String replyId,
			@RequestParam(value = "content") String content,
			@ModelAttribute("userInfo") UserInfo user,
			@RequestParam(value="tokenId")String tokenId,
			 HttpSession session) {

		Map<String, Object> result = new HashMap<String, Object>();

		if (!StringUtils.isEmpty(tokenId)) {

			if (session.getId().equals(tokenId)) {

				if (!StringUtils.isEmpty(tid)) {

					if (!StringUtils.isEmpty(replyId)) {

						if (!StringUtils.isEmpty(content)) {

							if (user != null) {

								String pid = user.getPid();

								String verify = user.getVerify();

								if (!(StringUtils.isEmpty(pid) && StringUtils
										.isEmpty(verify))) {

									result = this.service.addQuestion(tid,
											replyId, pid, verify, content);
								}else{
									
									result.put("Status", SystemConstant.MOBILE_TIME_OUT);
								}
							}
						}
					}
				}
			} else {

				result.put("Status", SystemConstant.MOBILE_TIME_OUT);
			}
		}

		return result;
	}
	
	/**
	 * 删除问题接口
	 * @param tid
	 * @param tokenId
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/deleteQuestion")
	@ResponseBody   
	public Map<String,Object> deleteQuestion(@RequestParam(value="tid")String tid,@RequestParam(value="tokenId")String tokenId,HttpSession session,HttpServletResponse res){
		
		Map<String,Object> result=new HashMap<String,Object>();
		
		if(!StringUtils.isEmpty(tokenId)){
			
			if(session.getId().equals(tokenId)){
				
				if(!StringUtils.isEmpty(tid)){
					
					result=this.service.deleteQuetion(tid);
				}
				
			}else{
				
				result.put("Status", SystemConstant.MOBILE_TIME_OUT);
			}
		}
		return result;
	}
	

	@ModelAttribute("userInfo")
	 Model getUserInfo(HttpSession session,Model model) {
		
		Object object=session.getAttribute("userInfo");
		
		if(object!=null){
			
			model.addAttribute("userInfo", object);
			
		}else{
			
			model.addAttribute("userInfo", new UserInfo());
		}
	  
	  return model;
	}
	
}
