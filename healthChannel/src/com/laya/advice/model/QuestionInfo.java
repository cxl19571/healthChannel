package com.laya.advice.model;


import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 返回问题详情模型
 * @author yc
 *
 */
public class QuestionInfo {
	
    public List<Map<String,Object>> replyInfo;     //回复详情
    
 //   public List<Map<String,String>> relatedQuestion;   //相关问题
    
    public Map<String,String> questionInfo;          // 问题详情
    
	public String [] medicalRecord;    //病历图片地址

	public String[] getMedicalRecord() {
		return medicalRecord;
	}

	public void setMedicalRecord(String[] medicalRecord) {
		this.medicalRecord = medicalRecord;
	}

//	public List<Map<String, String>> getRelatedQuestion() {
//		return relatedQuestion;
//	}
//
//	public void setRelatedQuestion(List<Map<String, String>> relatedQuestion) {
//		this.relatedQuestion = relatedQuestion;
//	}


	public Map<String, String> getQuestionInfo() {
		return questionInfo;
	}

	public void setQuestionInfo(Map<String, String> questionInfo) {
		this.questionInfo = questionInfo;
	}


	public List<Map<String, Object>> getReplyInfo() {
		return replyInfo;
	}

	public void setReplyInfo(List<Map<String, Object>> replyInfo) {
		this.replyInfo = replyInfo;
	}

	@Override
	public String toString() {
		return "QuestionInfo [replyInfo=" + replyInfo + ", questionInfo="
				+ questionInfo + ", medicalRecord="
				+ Arrays.toString(medicalRecord) + "]";
	}






}
