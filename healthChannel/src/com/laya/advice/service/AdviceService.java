package com.laya.advice.service;

import java.util.Map;

import org.apache.commons.codec.DecoderException;
import org.springframework.web.multipart.MultipartFile;

import com.laya.advice.model.Advice;


/**
 * 咨询业务逻辑
 * @author yc
 *
 */
public interface AdviceService {

	
	public Map<String,Object> startAdvice(Advice advice,MultipartFile [] fileSet) throws Exception;                //咨询
	
	public Map<String,Object> getQuestionSet(String roleId,String pid,String verify,String userId);                                 //查询提问列表
	
	public Map<String,Object> getQuestionInfo(String tid,String pid,String userId)throws DecoderException ;                 //获取问题详情 
	
	public Map<String,Object> addQuestion(String tid,String replyId,String memberId,String verify,String content);      //追加问题
	
	public Map<String,Object> deleteQuetion(String tid);           //删除问题
	
	
  
}
