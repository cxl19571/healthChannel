package com.laya.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.laya.system.model.Role;
import com.laya.system.service.RoleService;
import com.laya.utils.SystemConstant;

@Controller
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleService service;

	
	/**
	 * 添加家庭成员
	 * @param role
	 * @param file
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/addfamilymembers",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addFamilyMembers(Role role,
			@RequestParam(value = "member_head") MultipartFile file,
			HttpServletRequest request,@RequestParam(value="tokenId")String tokenId) {
		
		Map<String, Object> map = new HashMap<String, Object>();

		if(request.getSession().getId().toString().equals(tokenId)){   //验证登录是否失效
			
			if (!(role == null || file.isEmpty())) {
				
				map = service.addFamilyMembers(role, file, request);
			}
		}else{
			    map.put("Status", SystemConstant.MOBILE_TIME_OUT);
		}

		return map;
	
	}

	/**
	 * 查询此用户下所有家庭成员
	 * @param userPhone
	 * @return
	 */
	@RequestMapping(value="/getRoleList")
	@ResponseBody
	public Map<String, Object> getRoleList(
			@RequestParam(value = "userPhone") String userPhone,@RequestParam(value="tokenId")String tokenId,HttpSession session) {
		
		Map<String, Object> map = new HashMap<String, Object>();
	
		if(!StringUtils.isEmpty(tokenId)){
			
			if(session.getId().equals(tokenId)){          //验证登录是否失效
				
				if (!(userPhone == null || userPhone.isEmpty())) {
					
					List<Role> list = new ArrayList<Role>();
					
					list = this.service.getRoleList(userPhone);
					
					if (!list.isEmpty()) {
						
						map.put("Status", SystemConstant.RESPONSE_SUCCESS);
						
						map.put("membersList", list);
						
						map.put("total", list.size());
						
					} else {

						map.put("description", "家庭成员为空");
					}
				}
			}else{
				
				        map.put("Status", SystemConstant.MOBILE_TIME_OUT);
			}
		}
	
		
	
		return map;
	}

	/**
	 * 删除家庭成员
	 * @param id
	 * @param userPhone
	 */
	@RequestMapping(value = "/deletefamilymembers")
	@ResponseBody
	public Map<String,Object> deleteRole(@RequestParam(value = "id") String id,@RequestParam(value="userPhone")String userPhone,@RequestParam(value="tokenId")String tokenId,HttpServletRequest request) {
    
		 Map<String, Object> result=new HashMap<String,Object>();
	    if(request.getSession().getId().equals(tokenId)){               //验证登录是否失效
	    	
	    	if (!StringUtils.isEmpty(id)) {
				
				if(!StringUtils.isEmpty(userPhone)){
					
					this.service.deleteRole(id,userPhone);
					
					result.put("Status", SystemConstant.RESPONSE_SUCCESS);
				}
			}
	    	
	    }else{
	    	     result.put("Status", SystemConstant.MOBILE_TIME_OUT);
	    }	
	       return result;
	}

	/**
	 * 编辑家庭成员
	 * @param role
	 * @param file
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/editfamilymembers",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> editMember(Role role,
			@RequestParam(value = "member_head") MultipartFile file,
			@RequestParam(value="tokenId")String tokenId,
			HttpServletRequest request) {

		Map<String, Object> result = new HashMap<String, Object>();
		
		if(request.getSession().getId().equals(tokenId)){        //验证登录是否失效
			
			if (role != null) {
				
				result = this.service.editMembers(role, file, request);
			}
			
		}else{
			
			   result.put("Status", SystemConstant.MOBILE_TIME_OUT);
		}

		return result;
	}

	/**
	 * 设置默认家庭成员
	 * @param preId
	 * @param currentId
	 */
 
	@RequestMapping(value="/setdefaultmembers",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> setDefaultMember(@RequestParam(value="preId")String preId,@RequestParam(value="currentId")String currentId,@RequestParam(value="tokenId")String tokenId,HttpServletRequest request){
	
		 Map<String,Object> result=new HashMap<String,Object>();
		 
		 if(request.getSession().getId().equals(tokenId)){         //验证登录是否失效
			 
			 if(!(preId==null||currentId==null)){
				  
				 this.service.setDefaultRole(preId, currentId);
				 
				 result.put("Status", SystemConstant.RESPONSE_SUCCESS);
			 }
		 }else{
			 
			    result.put("Status", SystemConstant.MOBILE_TIME_OUT);
		 }
		
		  return result;
		
	}


}
