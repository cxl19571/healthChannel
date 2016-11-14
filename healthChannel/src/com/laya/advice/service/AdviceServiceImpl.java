package com.laya.advice.service;


import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.codec.DecoderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import com.laya.advice.dao.AdviceDao;
import com.laya.advice.model.Advice;
import com.laya.advice.model.Question;
import com.laya.advice.model.QuestionInfo;
import com.laya.utils.AdviceUtils;
import com.laya.utils.JsonUtils;
import com.laya.utils.SystemConstant;
import com.laya.utils.SystemUtils;

/**
 * 咨询业务逻辑层
 * @method startAdvice :  提问
 * @method getQuestionSet :获取问题列表
 * @method getQuestionInfo :问题详情
 * @method getRelatedQuestion:相关问题列表
 * @method addQuestion :追加问题
 * @author yc
 *
 */
@Service
public class AdviceServiceImpl implements AdviceService {

	@Autowired
	private AdviceDao dao;

	@Override
	public Map<String, Object> startAdvice(Advice advice,
			MultipartFile[] fileSet) throws Exception {

		Map<String, Object> params = new TreeMap<String, Object>();

		Map<String, Object> result = new HashMap<String, Object>();

		if (advice.getIstreat() != null) {

			params.put("istreat", advice.getIstreat());
		}

		if (!StringUtils.isEmpty(advice.getContent())) {

			params.put("content", advice.getContent());
		}

		if (advice.getSex() != null) {

			params.put("sex", advice.getSex());
		}

		if (!StringUtils.isEmpty(advice.getAge())) {

			params.put("age", advice.getAge());
		}

		if (advice.getTomid() != null) {

			params.put("tomid", advice.getTomid());
		}

		if (!StringUtils.isEmpty(advice.getIp())) {

			params.put("ip", advice.getIp());
		}

		if (advice.getMhid() != null) {

			params.put("mhid", advice.getMhid());
		}

		if (advice.getSysio() != null) {

			params.put("sysio", advice.getSysio());
		}

		if (!StringUtils.isEmpty(advice.getNewkeyname())) {

			params.put("newkeyname", advice.getNewkeyname());
		}

		if (advice.getSymptomdate() != null) {

			params.put("symptomdate", advice.getSymptomdate());
		}

		if (!StringUtils.isEmpty(advice.getRoleId())) {

			params.put("roleid", advice.getRoleId());
		}
		
		List<String> fileNames=new ArrayList<String>();             //创建一个保存文件名称的集合

		if (fileSet != null) {
             
			if (fileSet.length > 0 && fileSet.length < 4) { // 限制上传文件数量不能超过3
				
				for (int i=0;i<fileSet.length;i++) {

					if (fileSet[i].getSize() > 1024 * 1024) { // 限制单个文件大小(不能超过1M)

						result.put("Status", SystemConstant.File_SIZE_ERROR);

						return result;
					}
					
					String suffix = fileSet[i].getOriginalFilename().substring(fileSet[i].getOriginalFilename().lastIndexOf("."));  //获取文件后缀   
					
					if(SystemUtils.verifySuffix(suffix)){
						
						fileNames.add(fileSet[i].getOriginalFilename());         //将文件名称添加到list中
						
					}else{
						
						result.put("Status", SystemConstant.File_VERIFY_ERROR);
						
						return result;
					}
					
					
				}
				params.put("imagecount", fileSet.length);

				params.put("images", fileSet);

			}
		} else {

			params.put("imagecount", 0);
		}

		if (!StringUtils.isEmpty(advice.getMemberid())) {

			params.put("memberid", advice.getMemberid());
		}

		if (!StringUtils.isEmpty(advice.getVerify())) {

			params.put("verify", advice.getVerify());
		}
		
		params.put("appkey", SystemConstant.APP_KEY); // 封装参数

		params.put("appsecret", SystemConstant.SECRET);
		
		String postMethod = AdviceUtils.postMethod(params,
				SystemConstant.POST_QUESTION_URL); // 调用39问医生接口进行提问

		if (!StringUtils.isEmpty(postMethod)) {

			JSONObject jsonEntity = JsonUtils.stringParseJSON(postMethod); // 将接口返回的字符串进行处理

			if (jsonEntity.containsKey("code")
					&& (Integer) jsonEntity.get("code") == 1) { // 判断是否成功响应
																// 1代表响应成功
				Question question = new Question();

				if (jsonEntity.containsKey("tid")
						&& !StringUtils.isEmpty(jsonEntity.get("tid"))) {

					String tid = jsonEntity.getString("tid");

					question.setTid(tid);
				}
            
				if (jsonEntity.containsKey("roleid")
						&& !StringUtils.isEmpty(jsonEntity.get("roleid"))) {

					String roleId = jsonEntity.getString("roleid");

					question.setRoleId(roleId);
				}

				if (jsonEntity.containsKey("memberid")
						&& !StringUtils.isEmpty(jsonEntity.get("memberid"))) {

					String memberId = jsonEntity.getString("memberid");

					question.setMemberId(memberId);
				}

				if (!StringUtils.isEmpty(advice.getUserId())) {

					question.setUserId(advice.getUserId());
				}
                if(!fileNames.isEmpty()){
                	
                	String images=fileNames.toString();
                	
                	question.setImages(images);
                	
                }
				question.setCreateTime(SystemUtils.dateConvert(new Date()));

				try {
            
					this.dao.addQuestion(question); // 保存问题信息

					result.put("Status", SystemConstant.RESPONSE_SUCCESS);

				} catch (Exception e) {

					result.put("description", SystemConstant.ADD_Exception);
				}

			} else {

				if (jsonEntity.containsKey("description")) {

					result.put("Status", SystemConstant.SERVER_ERROR_CODE);

					result.put("description",
							SystemConstant.REMOTE_SERVER_ERROR);
				} else {

					String tip = jsonEntity.getString("tip");

					result.put("Status", SystemConstant.SERVER_ERROR_CODE);

					result.put("description", SystemUtils.URLDecoder(tip));
				}

			}

		}
		return result;
	}

	@Override
	public Map<String, Object> getQuestionSet(String roleId, String pid,
			String verify, String userId) {

		Map<String, Object> params = new HashMap<String, Object>();

		Map<String, Object> result = new HashMap<String, Object>();

		params.put("appkey", SystemConstant.APP_KEY); // 封装参数

		params.put("appsecret", SystemConstant.SECRET);

		params.put("memberid", pid);

		params.put("verify", verify);

		List<Question> list = this.dao.getMemberQuestionSet(roleId, userId); // 通过成员id和用户id查询所有tid集合

		if (!list.isEmpty()) {

			try {
				String postMethod = AdviceUtils.postMethod(params,
						SystemConstant.QUESTION_LIST_URL); // 调用远程问题列表接口并传递参数
				if (!StringUtils.isEmpty(postMethod)) {

					JSONObject object = JsonUtils.stringParseJSON(postMethod);

					if (object.containsKey("code")
							&& object.getLong("code") == 1) { // 返回json数据
																// code等于1则代表请求成功

						if (object.containsKey("data")) {

							JSONArray array = object.getJSONArray("data"); // 转换json数据为数组并开始解析

							Set<String> tidSet = new HashSet<String>();

							for (int i = 0; i < list.size(); i++) {

								tidSet.add(list.get(i).getTid()); // 将tid添加到set中
							}

							Set<Question> questionSet = new HashSet<Question>();

							for (int i = 0; i < array.size(); i++) {

								JSONObject json = array.getJSONObject(i);

								if (json.containsKey("tid")
										&& !StringUtils.isEmpty(json
												.getString("tid"))) {

									Question question = new Question();

									if (tidSet.contains(json.getString("tid"))) { // 通过比对相对应的tid,解析数据

										question.setTid(json.getString("tid"));

										if (json.containsKey("title")
												&& !StringUtils.isEmpty(json
														.getString("title"))) {

											question.setTitile(SystemUtils.URLDecoder(json
													.getString("title")));
										}

										if (json.containsKey("replycount")
												&& !StringUtils
														.isEmpty(json
																.getString("replycount"))) {

											question.setReplyCount(Integer.parseInt(json
													.getString("replycount")));
										}

										if (json.containsKey("createon")
												&& !StringUtils.isEmpty(json
														.getString("createon"))) {

											question.setCreateTime(json
													.getString("createon"));
										}

										questionSet.add(question); // 将构造的question模型保存到set中

									}
								}
							}
							result.put("Status",
									SystemConstant.RESPONSE_SUCCESS);

							result.put("questionSet", questionSet); // 返回问题详情和总数

							result.put("total", questionSet.size());

						}
					} else {

						if (object.containsKey("description")) {

							result.put("Status",
									SystemConstant.SERVER_ERROR_CODE);

							result.put("description",
									SystemConstant.REMOTE_SERVER_ERROR);
						} else {

							String tip = object.getString("tip");

							String decode = SystemUtils.URLDecoder(tip);

							result.put("Status",
									SystemConstant.SERVER_ERROR_CODE);

							result.put("description", decode);
						}
					}
				}

			} catch (Exception e) {

				e.printStackTrace();
			}
		} else {

			result.put("Status", SystemConstant.RESPONSE_SUCCESS);

			result.put("total", 0);
		}

		return result;
	}

	@Override
	public Map<String, Object> getQuestionInfo(String tid, String pid,
			String userId) throws DecoderException {

		Map<String, Object> params = new HashMap<String, Object>();

		Map<String, Object> result = new HashMap<String, Object>();

		params.put("appkey", SystemConstant.APP_KEY); // 封装参数

		params.put("appsecret", SystemConstant.SECRET);

		params.put("memberid", pid);

		params.put("tid", tid);

		try {

			String postMethod = AdviceUtils.postMethod(params,            //调用远程接口
					SystemConstant.QUESTION_INFO_URL);
			
			if (!StringUtils.isEmpty(postMethod)) {

				JSONObject object = JsonUtils.stringParseJSON(postMethod); // 将字符串转为json
				
				if (object.containsKey("code") && object.get("code") != null) {

					Integer code = (Integer) object.get("code"); // 获取返回json中的状态码:1,成功
																	// 0失败
					if (code == 1) {
                                 
						if (object.containsKey("apptopic")
								&& object.get("apptopic") != null) { // 验证返回json中是否有apptopic对象

							JSONObject jsonObject = JsonUtils
									.stringParseJSON(object.get("apptopic")
											.toString());

							QuestionInfo info = new QuestionInfo(); // 创建问题详情返回模型

							Map<String,String> questionInfo=new HashMap<String,String>();
							
							if (jsonObject.containsKey("title")
									&& !StringUtils.isEmpty(jsonObject
											.getString("title"))) {

								String title = SystemUtils
										.URLDecoder(jsonObject
												.getString("title"));
								questionInfo.put("title", title);
							
							}

							if (jsonObject.containsKey("createon")
									&& !StringUtils.isEmpty(jsonObject
											.getString("createon"))) {

								String createTime = jsonObject
										.getString("createon");
								questionInfo.put("createTime", createTime);
                                
						
							}
							if (jsonObject.containsKey("duration")
									&& !StringUtils.isEmpty(jsonObject
											.getString("duration"))) {

								String durationTime = SystemUtils
										.URLDecoder(jsonObject
												.getString("duration"));
                                
								questionInfo.put("durationTime", durationTime);
							}
							
							if(jsonObject.containsKey("age")&&!StringUtils.isEmpty(jsonObject.get("age"))){
								
								questionInfo.put("userAge", jsonObject.get("age").toString());
							}

							if (jsonObject.containsKey("topicsimg")
									&& jsonObject.get("topicsimg") != null) {

								JSONArray jsonArray = JsonUtils
										.stringParseJSONArray(jsonObject,
												"topicsimg"); // 通过key转换为jsonArray

								if (jsonArray.size() > 0) {

									String[] medicalRecord = new String[jsonArray
											.size()]; // 创建一个存放病历图片的数组

									for (int i = 0; i < jsonArray.size(); i++) {

										JSONObject json = jsonArray
												.getJSONObject(i);

										if (json.containsKey("imageurl")
												&& !StringUtils.isEmpty(json
														.getString("imageurl"))) {

											medicalRecord[i] = json
													.getString("imageurl");
										}
									}
									info.setMedicalRecord(medicalRecord); // 设置病历图片
								}

							}

							if (jsonObject.containsKey("doctorreply")
									&& jsonObject.get("doctorreply") != null) {

								JSONArray jsonArray = JsonUtils
										.stringParseJSONArray(jsonObject,
												"doctorreply"); // 通过key将字符串转为jsonArray

								if (jsonArray.size() > 0) {

									List<Map<String, Object>> replyInfo = new ArrayList<Map<String, Object>>();

									for (int i = 0; i < jsonArray.size(); i++) {

										JSONObject json = jsonArray
												.getJSONObject(i);

										Map<String, Object> replyMap = new TreeMap<String, Object>();      //回复详情模型

										if (json.containsKey("replyid")
												&& !StringUtils.isEmpty(json
														.getString("replyid"))) {

											String replyId = json
													.getString("replyid");

											replyMap.put("replyId", replyId); // 设置回复id
										}
										if (json.containsKey("realname")
												&& !StringUtils.isEmpty(json
														.getString("realname"))) {

											String doctorName = SystemUtils
													.URLDecoder(json
															.getString("realname")); // 转码urldecode

											replyMap.put("doctorName",
													doctorName); // 设置医生名称
										}

										if (json.containsKey("keshiname")
												&& !StringUtils
														.isEmpty(json
																.getString("keshiname"))) {

											String keshiName = SystemUtils
													.URLDecoder(json
															.getString("keshiname"));

											replyMap.put("keshiName", keshiName); // 设置科室名称
										}

										if (json.containsKey("imageurl")
												&& !StringUtils.isEmpty(json
														.getString("imageurl"))) {

											String doctorPicture = json
													.getString("imageurl");

											replyMap.put("doctorPicture",
													doctorPicture); // 设置医生图片
										}

										if (json.containsKey("mainbody")
												&& !StringUtils.isEmpty(json
														.getString("mainbody"))) {

											String replyContent = SystemUtils
													.URLDecoder(json
															.getString("mainbody"));

											replyMap.put("replyContent",
													replyContent); // 设置回复内容
										}

										if (json.containsKey("createon")
												&& !StringUtils.isEmpty(json
														.getString("createon"))) {

											String replyTime = json
													.getString("createon");

											replyMap.put("replyTime", replyTime); // 设置回复时间
										}
										
										if(json.containsKey("inquire")&&json.get("inquire")!=null){
											
											JSONArray jsonarray=JsonUtils.stringParseJSONArray(json, "inquire");
											
											if(jsonarray.size()>0){
												
												List<Map<String,Object>> inquireList=new ArrayList<Map<String,Object>>();
												
												for (int j = 0; j < jsonarray.size(); j++) {
													
													JSONObject inquire=jsonarray.getJSONObject(j);
													
													Map<String,Object> inquireMap=new TreeMap<String,Object>();
													
													if(inquire.containsKey("replyid")&&!StringUtils.isEmpty(inquire.get("replyid"))){
														
														inquireMap.put("replyId", inquire.get("replyid"));
													}
													
													if(inquire.containsKey("mainbody")&&!StringUtils.isEmpty(inquire.getString("mainbody"))){
														
														String title=SystemUtils.URLDecoder(inquire.getString("mainbody"));
														
														inquireMap.put("title", title);
													}
													
													if(inquire.containsKey("createon")&&!StringUtils.isEmpty(inquire.getString("createon"))){
														
													   String time=inquire.getString("createon");
													   
													   inquireMap.put("time", time);
													}
													
													if(inquire.containsKey("memberid")&&!StringUtils.isEmpty(inquire.getString("memberid"))){
														
														String memberId=inquire.getString("memberid");
														
														if(memberId.equals(pid)){
															
															inquireMap.put("userType", 1);
														}else{
															inquireMap.put("userType", 2);
														}
													}
													
													inquireList.add(inquireMap);
												}
												
												    replyMap.put("inquireList", inquireList);
											}
											
										}

										replyInfo.add(replyMap); // 设置问题回复详情
									}

									info.setReplyInfo(replyInfo);
								}

								
							}
							

							Map<String, Object> userMap = this.dao
									.getMemberInfo(tid, userId); // 查询用户信息

							if (userMap != null) {

								if (userMap.containsKey("userName")
										&& userMap.get("userName") != null) {
                                    
									questionInfo.put("userName", userMap.get("userName").toString());
								}

								if (userMap.containsKey("userSex")
										&& userMap.get("userSex") != null) {
									
									questionInfo.put("userSex", userMap.get("userSex")
											.toString());
								}

							}

//							List<Map<String, String>> relateList = getRelatedQuestion(
//									questionInfo.get("title"), tid); // 返回相关问题列表
//
//							if (!relateList.isEmpty()) {
//
//								info.setRelatedQuestion(relateList);
//							}
							
                            info.setQuestionInfo(questionInfo);
							
							result.put("Status",
									SystemConstant.RESPONSE_SUCCESS);

							result.put("data", info);

						}

					} else {

						if (object.containsKey("description")) {

							result.put("Status",
									SystemConstant.SERVER_ERROR_CODE);

							result.put("description",
									SystemConstant.REMOTE_SERVER_ERROR);
						} else {

							String tip = object.getString("tip");

							String decode = SystemUtils.URLDecoder(tip);

							result.put("description", decode);
						}

					}
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		return result;
	}
	

	// 获取相关问题列表

//	public List<Map<String, String>> getRelatedQuestion(String keyword,
//			String tid) {
//
//		List<Map<String, String>> relatedList = new ArrayList<Map<String, String>>();
//
//		Map<String, Object> params = new HashMap<String, Object>();
//
//		params.put("appkey", SystemConstant.APP_KEY); // 封装参数
//
//		params.put("appsecret", SystemConstant.SECRET);
//
//		params.put("pn", 1);
//
//		params.put("ps", 3);
//
//		params.put("tid", tid);
//
//		params.put("keyword", keyword);
//
//		try {
//			String postMethod = AdviceUtils.postMethod(params,
//					SystemConstant.RELATED_QUESTION_URL);
//
//			if (!StringUtils.isEmpty(postMethod)) {
//
//				JSONObject object = JsonUtils.stringParseJSON(postMethod); // 返回结果转换为json对象
//
//				if (object.containsKey("code") && object.get("code") != null) {
//
//					Integer code = object.getInt("code");
//
//					if (code == 1) {
//
//						if (object.containsKey("data")
//								&& object.get("data") != null) {
//
//							JSONArray jsonArray = JsonUtils
//									.stringParseJSONArray(object, "data");
//
//							if (jsonArray.size() > 0) {
//
//								for (int i = 0; i < jsonArray.size(); i++) {
//
//									JSONObject json = jsonArray
//											.getJSONObject(i);
//
//									Map<String, String> questionMap = new HashMap<String, String>();
//
//									if (json.containsKey("tid")
//											&& !StringUtils.isEmpty(json
//													.getString("tid"))) {
//
//										questionMap.put("tid",
//												json.getString("tid"));
//									}
//
//									if (json.containsKey("content")
//											&& !StringUtils.isEmpty(json
//													.getString("content"))) {
//
//										String title = SystemUtils
//												.URLDecoder(json
//														.getString("content"));
//
//										questionMap.put("title", title);
//									}
//
//									if (json.containsKey("replycount")
//											&& !StringUtils.isEmpty(json
//													.getString("replycount"))) {
//
//										questionMap.put("replyCount",
//												json.getString("replycount"));
//									}
//
//									relatedList.add(questionMap); // 添加返回相关问题结果
//								}
//
//							} else {
//
//								Map<String, String> result = new HashMap<String, String>();
//
//								result.put("total", "0");
//
//								relatedList.add(result);
//							}
//						}
//					} else {
//
//						Map<String, String> result = new HashMap<String, String>();
//
//						if (object.containsKey("tip")
//								&& !StringUtils
//										.isEmpty(object.getString("tip"))) {
//
//							result.put("Status",
//									SystemConstant.SERVER_ERROR_CODE.toString());
//
//							result.put("description", SystemUtils
//									.URLDecoder(object.getString("tip")));
//
//							relatedList.add(result);
//						}
//
//					}
//				}
//			}
//		} catch (Exception e) {
//
//			e.printStackTrace();
//		}
//
//		return relatedList;
//	}

	@Override
	public Map<String, Object> addQuestion(String tid, String replyId,
			String memberId, String verify, String content) {

		Map<String,Object> params=new HashMap<String,Object>();
		
		Map<String,Object> result=new HashMap<String,Object>();
		
		params.put("appkey", SystemConstant.APP_KEY);   // 封装参数

		params.put("appsecret", SystemConstant.SECRET);
		
		params.put("tid", tid);
		
		params.put("verify", verify);
		
		params.put("content", content);
		
		params.put("memberid", memberId);
		
		params.put("rid", replyId);
		
		try {
			    String postMethod=AdviceUtils.postMethod(params, SystemConstant.ADD_QUESTION_URL);   //追加问题
			       
			    if(!StringUtils.isEmpty(postMethod)){
			    	
			    	JSONObject object=JsonUtils.stringParseJSON(postMethod);
			    	
			    	if(object.containsKey("code")&&!StringUtils.isEmpty(object.get("code"))){
			    		
			    		if(Integer.parseInt(object.getString("code"))==1){      //code等于代表请求远程服务器成功
			    			
			    			result.put("Status", SystemConstant.RESPONSE_SUCCESS);
			    			
			    			result.put("content", content);
			    			
			    			result.put("time", SystemUtils.dateConvert(new Date()));
			    			
			    		}else{
			    			
			    			result.put("Status", SystemConstant.SERVER_ERROR_CODE);
			    			
			    			String tip=SystemUtils.URLDecoder(object.getString("tip"));
			    			
			    			result.put("description", tip);
			    			
			    		}
			    	}
			    }
			 
		 } catch (Exception e) {
			
			 e.printStackTrace();
		}
		return result;
	}

	@Override
	public Map<String, Object> deleteQuetion(String tid) {
	
		Map<String, Object> result=new HashMap<String,Object>();
		
		Question question=this.dao.getQuestionByTid(tid);
		
		if(question!=null){
			
			String imagesName=question.getImages();
			
			if(!StringUtils.isEmpty(imagesName)){
				
				   String sb=imagesName.substring(1, imagesName.length()-1);     //截取字符串形式为[xxxx.jpg,xxxx.jpg,xxxx.jpg]
				   
				    String [] ss=sb.split(",");
				    
				    for (int i = 0; i < ss.length; i++) {
						
				    	File file=new File(SystemConstant.FILE_URL+File.separator+ss[i]);
				    	
				    	if(file.exists()){
				    		
				    		file.delete();
				    	}
					}
			}
			
			this.dao.deleteQuestion(tid);
			
			result.put("Status", SystemConstant.RESPONSE_SUCCESS);
			
		}
		
		return result;
	}
	
               
}
