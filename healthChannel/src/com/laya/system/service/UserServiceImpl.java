package com.laya.system.service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.laya.advice.model.LoginMapping;
import com.laya.system.dao.*;
import com.laya.system.model.Role;
import com.laya.system.model.UserInfo;
import com.laya.utils.JsonUtils;
import com.laya.utils.Login39Utils;
import com.laya.utils.MD5Coder;
import com.laya.utils.Mobile;
import com.laya.utils.SystemConstant;
import com.laya.utils.SystemUtils;

@Service
public class UserServiceImpl implements UserService {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory
			.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;

	@Override
	public Boolean isExsit(String userPhone) {

		return userDao.isExsit(userPhone) > 0 ? true : false;
	}

	@Override
	public Map<String, Object> registerUser(UserInfo user, String appkey,
			String zone, String code) {

		Map<String, Object> map = new HashMap<String, Object>();

		String result = Mobile.SMSSDK_Verify(appkey, user.getUserPhone(), zone,
				code); // 短信服务验证接口result={"status":xxx}

		if (Integer.parseInt(result.substring(10, result.length() - 1)) == SystemConstant.RESPONSE_SUCCESS) {
			// 状态码200表示验证成功
			UserInfo userInfo = new UserInfo();

			try {

				String temp = MD5Coder.MD5(user.getUserPassword()); // 对密码进行MD5加密

				userInfo.setUserPassword(temp);

				userInfo.setUserPhone(user.getUserPhone());

				userInfo.setId(SystemUtils.constructPrimaryKey()); // 设置id

				userInfo.setRegisterTime(SystemUtils.dateConvert(new Date())); // 获取系统时间

				try {

					this.userDao.registerUser(userInfo);

					Role role = new Role();

					role.setId(SystemUtils.constructPrimaryKey());

					role.setCreateTime(SystemUtils.dateConvert(new Date()));

					role.setUserType(0); // 设置用户类型:0表示默认用户 1表示成员用户

					role.setParentId(user.getUserPhone());

					role.setIsRegister(1); // 1表示注册用户

					this.userDao.addDefaultMember(role);

					map.put("Status", SystemConstant.RESPONSE_SUCCESS); // 响应成功

				} catch (Exception e) {

					map.put("Status", SystemConstant.ADD_Exception); // 添加异常
				}
			} catch (Exception e1) {

				e1.printStackTrace();
			}

		} else {

			map.put("Status", SystemConstant.MESSAGE_VERIFY_ERROR); // 短信验证错误
		}

		return map;
	}

	@Override
	public Map<String, Object> isLogin(String userPhone, String userPassword,
			HttpServletRequest request) {

		Map<String, Object> map = new HashMap<String, Object>();

		String MD5Password = MD5Coder.MD5(userPassword); // 将密码加密

		UserInfo userinfo = new UserInfo();

		userinfo.setUserPhone(userPhone);

		userinfo.setUserPassword(MD5Password);

		UserInfo user = this.userDao.isLogin(userinfo);

		if (user != null) {

			String userId = user.getUserPhone();

			if (!StringUtils.isEmpty(userId)) {

				LoginMapping mapping = this.userDao.getMappingByUserId(userId);

				if (mapping == null) {

					Map<String, String> verifyMap = getVerifyBy39(user
							.getUserPhone());

					if (verifyMap.containsKey("pid")
							&& verifyMap.containsKey("verify")) {

						String pid = verifyMap.get("pid"); // 获取39健康网返回的pid

						String verify = verifyMap.get("verify"); // 获取39健康网返回的verify

						if (!(StringUtils.isEmpty(pid) && StringUtils
								.isEmpty(verify))) {

							LoginMapping entity = new LoginMapping(); // 添加映射记录

							entity.setUserId(user.getUserPhone());

							entity.setVerify(verify);

							entity.setMappingId(pid);
							try {

								this.userDao.addLoginMapping(entity);

							} catch (Exception e) {

								map.put("Status", SystemConstant.ADD_Exception);
							}

							user.setPid(pid); // 设置认证凭证,方便放入session中

							user.setVerify(verify);

						}

					} else {
						map.put("Status", SystemConstant.SERVER_ERROR_CODE);

						map.put("description",
								SystemConstant.REMOTE_SERVER_ERROR);
					}

				} else { // 如果查询出不为空,则代表39登录凭证已经存在直接存入session

					String pid = mapping.getMappingId(); // 获取映射id

					String verify = mapping.getVerify(); // 获取39认证

					if (!(StringUtils.isEmpty(pid) && StringUtils
							.isEmpty(verify))) {

						user.setPid(pid); // 返回凭证

						user.setVerify(verify);
					}

				}

			}

			user.setTokenId(request.getSession().getId());

			map.put("userInfo", user);

			map.put("Status", SystemConstant.RESPONSE_SUCCESS);

		} else {

			map.put("Status", SystemConstant.USERNAME_OR_PASSWORD_ERROR);
		}
		return map;
	}

	@Override
	public Map<String, Object> updatePassword(String userPhone,
			String oldPassword, String newPassword) {
		Map<String, Object> result = new HashMap<String, Object>();

		UserInfo user = getUserPasswordByPhone(userPhone);

		if (user != null) {

			String password = MD5Coder.MD5(oldPassword); // 加密用户输入的旧密码

			if (user.getUserPassword().equals(password)) { // 判断用户输入的旧密码是否正确.

				Map<String, Object> params = new HashMap<String, Object>();

				params.put("newPassword", MD5Coder.MD5(newPassword)); // 将用户的新密码加密并保存

				params.put("id", user.getId());

				this.userDao.updatePassword(params);

				result.put("Status", SystemConstant.RESPONSE_SUCCESS);

			} else {

				result.put("Status", "原密码错误");
			}
		} else {

			result.put("Status", "此用户还未注册");
		}

		return result;
	}

	@Override
	public UserInfo getUserPasswordByPhone(String userPhone) {

		return this.userDao.findUserByPhone(userPhone);

	}

	@Override
	public Map<String, Object> resetPassword(String userPhone, String code,
			String password, String appkey, String zone) {

		Map<String, Object> result = new HashMap<String, Object>();

		UserInfo user = getUserPasswordByPhone(userPhone); // 通过电话号码查询用户密码

		if (user != null) {

			String status = Mobile.SMSSDK_Verify(appkey, userPhone, zone, code); // 短信验证接口返回{"status":xxx}

			if (Integer.parseInt(status.substring(10, status.length() - 1)) == SystemConstant.RESPONSE_SUCCESS) { // 状态码200则代表验证成功

				Map<String, Object> params = new HashMap<String, Object>();

				String newPassword = MD5Coder.MD5(password); // 加密新密码

				params.put("newPassword", newPassword);

				params.put("id", user.getId());

				this.userDao.updatePassword(params); // 更新数据

				result.put("Status", SystemConstant.RESPONSE_SUCCESS);

			} else {

				result.put("Status", "短信验证失败");
			}
		} else {

			result.put("Status", "此用户不存在");
		}
		return result;
	}

	/**
	 * 获取39健康网登录凭证
	 * 
	 * @param userId
	 * @return
	 */
	public Map<String, String> getVerifyBy39(String userId) {

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("BindType", 5);

		params.put("openid", userId);

		String entityString = JsonUtils.mapPasrseJSON(params); // 构造json字符串

		String postMethod = "";

		String timestamp = Login39Utils.getTimeStamp(); // 获取系统时间戳

		String sign = Login39Utils.getSign("39", timestamp, entityString); // 获取39健康网登录签名

		Map<String, String> result = new HashMap<String, String>();

		try {

			postMethod = Login39Utils.postMethod(timestamp, sign, entityString); // 开始验证

			if (!StringUtils.isEmpty(postMethod)) {

				JSONObject object = JsonUtils.stringParseJSON(postMethod); // 将返回的json字符串转化为json对象

				if (object.containsKey("code")
						&& (Integer) object.get("code") == 0) { // code=0说明远程服务器请求失败

					result.put("code", object.getString("code"));

					result.put("description", object.getString("description"));

				} else {

					if (object.containsKey("Data")
							&& object.get("Data") != null) {

						JSONArray array = object.getJSONArray("Data");

						JSONObject json = array.getJSONObject(0);

						String pid = json.getString("<pid>k__BackingField");

						String verify = json
								.getString("<Verify>k__BackingField");

						if (!(StringUtils.isEmpty(pid) && StringUtils
								.isEmpty(verify))) {

							result.put("pid", pid); // 获取pid和verify凭证并返回

							result.put("verify", verify);
						}

					}

				}

			}

		} catch (IOException e) {

			e.printStackTrace();
		}

		return result;
	}
}
