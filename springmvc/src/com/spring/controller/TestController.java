package com.spring.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

@SessionAttributes("userInfo")
@Controller
@RequestMapping(value="/domain")
public class TestController {

	@RequestMapping(value="/test.top")
	public String test1(){
		return "index";
	}
	
	@RequestMapping(value="/test2.top")
	@ResponseBody
	public Map<String,Object> test2(Model model){
		
		Map<String,Object> result=new HashMap<String,Object>();
		
		List<Test> indexList=new ArrayList<Test>();
		Test tt=new Test();
		tt.setAge(20);
		tt.setUserName("yanchen");
		Test t2=new Test();
		t2.setAge(48);
		t2.setUserName("周杰伦");
		model.addAttribute("userInfo", tt);
		model.addAttribute("userInfo", t2);
		indexList.add(tt);
		indexList.add(t2);
		result.put("list", indexList);
		return result;
	}
	
	@RequestMapping(value="/test3.top")
	@ResponseBody
	public String test3(@ModelAttribute("userInfo") Test tt){
		
		return tt.getUserName()+"=="+tt.getAge();
	}
	
	@ModelAttribute
	public Object test4(Model model,HttpSession session){
		
		Object object=session.getAttribute("userInfo");
		
		if(object==null){
			
			model.addAttribute("userInfo", new Test());
			
			return model;
			
		}else{
			
			return object;
		}
		
	}
	
	@RequestMapping(value="/upload")
	public String upload(@RequestParam(value="file")MultipartFile file,Test test){
		
		return "";
	}
}
