package com.spring.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/domain")
public class TestController {

	@RequestMapping(value="/test.top")
	public String test1(){
		return "index";
	}
	
	@RequestMapping(value="/test2.top")
	@ResponseBody
	public Map<String,Object> test2(){
		
		Map<String,Object> result=new HashMap<String,Object>();
		
		List<Test> indexList=new ArrayList<Test>();
		Test tt=new Test();
		tt.setAge(20);
		tt.setUserName("yanchen");
		Test t2=new Test();
		t2.setAge(40);
		t2.setUserName("周杰伦");
		indexList.add(tt);
		indexList.add(t2);
		result.put("list", indexList);
		return result;
	}
}
