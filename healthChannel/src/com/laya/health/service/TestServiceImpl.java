package com.laya.health.service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laya.health.dao.TestDao;
import com.laya.health.model.Test;
import com.laya.system.model.Role;
import com.laya.utils.SystemConstant;
import com.laya.utils.SystemUtils;

/**
 * @method addTestResult:添加检测结果
 * @method getTestResult:通过用户名获取检测数据
 * @method getAllMembersTestResult:获取所有家庭成员检测数据
 * @method updateTestResult:修改检测数据
 * @method 
 * 
 * @author yc
 *
 */
@Service
public class TestServiceImpl implements TestService {

	@Autowired
	private TestDao dao;

	@Override
	public Map<String, Object> addTestResult(Test test) {

		Map<String, Object> result = new HashMap<String, Object>();

			if (!(StringUtils.isEmpty(test.getUserPhone()))) {

				if (!(StringUtils.isEmpty(test.getResult()))) {

					if (test.getStatus() != null) {
                         
						if (!StringUtils.isEmpty(test.getCreateTime())) {

							if (test.getTestType() != null) {
								
                                      String time=test.getCreateTime();
                                      
								if (!StringUtils.isEmpty(time)) {

									String currentTime = null;

									try {
										currentTime = SystemUtils
												.strCovertDate(test
														.getCreateTime());    // 对检测时间进行处理,返回24小时后的时间
									} catch (ParseException e1) {

										e1.printStackTrace();
									} 

									List<Test> list = getResultList(
											test.getUserPhone(),
											
											test.getUserId(), test.getStatus(),
											
											test.getTestType(), currentTime); // 通过手机号码,用户id,检测状态,检测类型,目标时间查询检测数据

									if (list.isEmpty()) { // 如果集合为空则代表没有数据,开始添加新数据

										Test temp = new Test();
										
                                        temp.setId(SystemUtils.constructPrimaryKey());
                                        
										temp.setResult(test.getResult());

										temp.setUserId(test.getUserId());

										temp.setUserPhone(test.getUserPhone());

										temp.setCreateTime(test.getCreateTime());

										temp.setStatus(test.getStatus());

										temp.setTemperature(test
												.getTemperature());

										temp.setTestType(test.getTestType());

										try {

											this.dao.addTestResult(temp); // 保存

											result.put(
													"Status",
													SystemConstant.RESPONSE_SUCCESS);

										} catch (Exception e) {

											e.printStackTrace();
										}

									} else {          // 集合不为空的话,那么说明存在数据.对该数据进行修改

										Test tt = list.get(0); // 选择时间最近的一条数据

										tt.setCreateTime(test.getCreateTime());

										tt.setResult(test.getResult());

										tt.setTemperature(test.getTemperature());
                                      
										this.dao.updateTestResult(tt); // 修改

										result.put("Status",
												SystemConstant.RESPONSE_SUCCESS);

									}
								}

							}

						}

					}
				}
			}
 
		return result;
	}

	@Override
	public Map<String, Object> getTestResult(String userPhone) {

		Map<String, Object> result = new HashMap<String, Object>();

		if (!StringUtils.isEmpty(userPhone)) {

			List<Role> roles = this.dao.getMembersInfo(userPhone); // 通过手机号码获取成员信息

			if (!roles.isEmpty()) {

				result.put("roles", roles);

				Iterator<Role> iter = roles.iterator();

				while (iter.hasNext()) {

					Map<String, Object> params = new HashMap<String, Object>();

					params.put("userPhone", userPhone);

					params.put("userId", iter.next().getId());

					Test test = this.dao.getTestResult(params); // 通过手机号和成员id获取检测数据中最近的一条

					if (test != null) {

						result.put("testResult" + test.getUserId(), test); // 返回该数据

					} else {

						result.put("testResult" + params.get("userId"),
								"没有检测数据");
					}

				}
			}

		}
		return result;
	}

	@Override
	public List<Test> getAllMembersTestResult(String userPhone, String userId,String targetTime,String currentTime,Integer testType) {

		Map<String, Object> params = new HashMap<String, Object>();
		
		if(!StringUtils.isEmpty(targetTime)){
			
			params.put("targetTime", targetTime);       //目标时间
		}
		
		if(!StringUtils.isEmpty(currentTime)){
			
			params.put("currentTime", currentTime);    //当前时间
		}
		
		if(testType!=null){
			
			params.put("testType", testType);         //检测类型
		}
        
		if(!StringUtils.isEmpty(userPhone)){         //用户号码
			
			params.put("userPhone", userPhone);
		}
		
		if(!StringUtils.isEmpty(userId)){
			
			params.put("userId", userId);         //角色id
		}

		return this.dao.getAllMembersTestResult(params);
	}

	@Override
	public void updateTestResult(Test test) {

		this.dao.updateTestResult(test);
	}

	@Override
	public List<Test> getResultList(String userPhone, String userId,
			Integer status, Integer testType, String createTime) {

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("userPhone", userPhone);

		params.put("userId", userId);

		params.put("status", status);

		params.put("testType", testType);

		params.put("createTime", createTime);

		return this.dao.getResultList(params);
	}

	@Override
	public List<Map<String, Object>> test(String userPhone) {
		
		return this.dao.test(userPhone);
	}

}
