package com.laya.system.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import com.laya.system.model.UserInfo;
import com.laya.system.service.UserService;
import com.laya.utils.SystemConstant;

@Controller
@SessionAttributes("userInfo")
@RequestMapping("/user")
public class UserController {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory
			.getLogger(UserController.class);
	@Autowired
	private UserService userService;

	/**
	 * 注册
	 * 
	 * @param password
	 * @param userPhone
	 * @param appkey
	 * @param zone
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/registerUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> register(
			@RequestParam(value = "userPassword") String password,
			@RequestParam(value = "userPhone") String userPhone,
			@RequestParam(value = "appkey") String appkey,
			@RequestParam(value = "zone") String zone,
			@RequestParam(value = "code") String code) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (!(userPhone.isEmpty() || userPhone == null)) {

			if (!(password.isEmpty() || password == null)) {

				if (!(appkey.isEmpty() || appkey == null)) {

					if (!(zone.isEmpty() || zone == null)) {

						if (!(code.isEmpty() || code == null)) {

							UserInfo user = new UserInfo();

							user.setUserPassword(password);

							user.setUserPhone(userPhone);

							map = userService.registerUser(user, appkey, zone,
									code);
						}
					}
				}
			}
		}

		return map;

	}

	/**
	 * 检查用户是否存在
	 * 
	 * @param userPhone
	 * @return
	 */

	@RequestMapping(value = "/checkPhoneIsExist")
	@ResponseBody
	public Map<String, Object> checkPhoneIsExist(String userPhone) {

		Map<String, Object> map = new HashMap<String, Object>();
		Boolean temp;
		if (!(userPhone == null || "".equals(userPhone))) {

			temp = userService.isExsit(userPhone);

			map.put("Status", temp);

		} else {

			map.put("Status", SystemConstant.PARAM_IS_EMPTY);
		}
		return map;
	}

	/**
	 * 登录
	 * 
	 * @param userPhone
	 * @param userPassword
	 * @param request
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/isLogin")
	@ResponseBody
	public Map<String, Object> isLogin(
			@RequestParam(value = "userPhone") String userPhone,
			@RequestParam(value = "userPassword") String userPassword,
			HttpServletRequest request, Model model, HttpSession session)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		if (!(StringUtils.isEmpty(userPhone)&&(StringUtils.isEmpty(userPassword)))){

			map = this.userService.isLogin(userPhone, userPassword, request);

			if (map.containsKey("userInfo") && map.get("userInfo") != null) {
				
				model.addAttribute("userInfo", map.get("userInfo"));
			}

		}
		return map;
	}
	
	/**
	 * 修改密码
	 * 
	 * @param userPhone
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePassword(
			@RequestParam(value = "userPhone") String userPhone,
			@RequestParam(value = "oldPassword") String oldPassword,
			@RequestParam(value = "newPassword") String newPassword,
			@RequestParam(value = "tokenId") String tokenId,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();

		if (request.getSession().getId().equals(tokenId)) {

			if (!StringUtils.isEmpty(userPhone)) {

				if (!StringUtils.isEmpty(oldPassword)) {

					if (!StringUtils.isEmpty(newPassword)) {

						result = this.userService.updatePassword(userPhone,
								oldPassword, newPassword);
					}
				}
			} else {

				result.put("Status", SystemConstant.PARAM_IS_EMPTY);
			}

		} else {
			result.put("Status", SystemConstant.MOBILE_TIME_OUT);
		}

		return result;
	}

	/**
	 * 重置密码
	 * 
	 * @param userPhone
	 * @param code
	 * @param password
	 * @param appkey
	 * @param zone
	 * @return
	 */
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> resetPassWord(
			@RequestParam(value = "userPhone") String userPhone,
			@RequestParam(value = "verifyCode") String code,
			@RequestParam(value = "password") String password,
			@RequestParam(value = "appkey") String appkey,
			@RequestParam(value = "zone") String zone) {
		Map<String, Object> result = new HashMap<String, Object>();

		if (!StringUtils.isEmpty(userPhone)) {

			if (!StringUtils.isEmpty(password)) {

				if (!StringUtils.isEmpty(code)) {

					if (!StringUtils.isEmpty(appkey)) {

						if (!StringUtils.isEmpty(zone)) {

							result = this.userService.resetPassword(userPhone,
									code, password, appkey, zone);
						}
					}
				}

			}
		}

		return result;

	}

}
