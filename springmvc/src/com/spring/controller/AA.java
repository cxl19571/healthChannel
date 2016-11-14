package com.spring.controller;


import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class AA {

    @ExceptionHandler(value = {Exception.class})  
    @ResponseBody
    public String handleOtherExceptions( Exception ex) {  
        
    	String result="";
    	if(ex instanceof RuntimeException){
    		
    		result="运行时异常";
    	}
        if(ex instanceof IllegalAccessException){
        	
        	result="非法存取异常";
        }
        if(ex instanceof HttpSessionRequiredException){
        	
        	result="请重新登录";
        }
        
        return result;
    } 
    
}
