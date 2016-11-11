package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

@SessionAttributes("userInfo")
@Controller
@RequestMapping(value="/test2")
public class Test2Controller {
	
	
	@RequestMapping(value="/tt.do")
	@ResponseBody
	public String test(@ModelAttribute("userInfo")Test test) throws IllegalAccessException{
		
		if(test.getAge()>40){
			
		   throw new RuntimeException("年龄不能大于40");
		}
		
		if(test.getAge()==40){
			
			throw new IllegalAccessException("我也不知道这异常");
		}
		return test.getUserName()+"===="+test.getAge();
	}



}
