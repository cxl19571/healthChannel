package com.laya.system.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.laya.system.dao.RoleDao;
import com.laya.system.model.Role;
import com.laya.utils.FileHandleUtils;
import com.laya.utils.SystemConstant;
import com.laya.utils.SystemUtils;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao dao;

	@Override
	public Map<String, Object> addFamilyMembers(Role role, MultipartFile file,
			HttpServletRequest request) {

		Map<String, Object> map = new HashMap<String, Object>();

		if (!StringUtils.isEmpty(role.getParentId())) {

			String realPath;
			try {
				if (!(file.isEmpty() || file == null)) {

					realPath = FileHandleUtils.uploadFile(request, file); // 文件不为空则上传

					File img = new File(SystemConstant.FILE_URL
							+ File.separator + file.getOriginalFilename());

					if (img.exists()) {

						Role temp = new Role();

						temp.setId(SystemUtils.constructPrimaryKey());

						temp.setUserSex(role.getUserSex());

						temp.setUserName(role.getUserName());

						temp.setHeadPicture(realPath);

						temp.setParentId(role.getParentId());

						temp.setUserType(1); // 设置成员类型为1,表示普通成员.0代表默认成员

						temp.setIsRegister(0); // 设置是否为注册用户,0表示非注册用户,1表示注册用户

						temp.setCreateTime(SystemUtils.dateConvert(new Date()));

						this.dao.addRole(temp); // 保存成员信息

						map.put("Status", SystemConstant.RESPONSE_SUCCESS);
					}

				}
			} catch (IllegalStateException e) {

				e.printStackTrace();

			} catch (IOException e) {

				e.printStackTrace();
			}

		}

		return map;
	}

	@Override
	public List<Role> getRoleList(String userPhone) {

		return this.dao.getRoleList(userPhone);
	}

	@Override
	public void deleteRole(String id, String userPhone) {

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("userId", id);

		params.put("userPhone", userPhone);

		Role role = findRoleById(id); // 通过id查询家庭成员信息

		if (role != null) {

			if (role.getUserType() != 0) { // 判断是否是默认用户 0:默认 1:普通

				if (role.getIsRegister() != 1) { // 判断是否是注册用户 0:非注册 1:注册

					// 默认用户或注册用户不允许删除
					String headPictrueURL = role.getHeadPicture(); // 获取上传头像地址

					if (!StringUtils.isEmpty(headPictrueURL)) { // 头像地址不为空

						String[] array = headPictrueURL.split("/"); // 拆分地址字符串

						String fileName = array[array.length - 1]; // 获取到头像名字

						File file = new File(SystemConstant.FILE_URL
								+ File.separator + fileName);

						if (file.exists()) { // 如果图片存在则删除

							file.delete();
						}
					}
					this.dao.deleteRole(id); // 通过id删除成员信息

					this.dao.deleteTestResult(params); // 通过电话号码和成员id删除检测数据
				}
			}

		}

	}

	@Override
	public void setDefaultRole(String preDefaultId, String currentDefaultId) {

		Role preRole = this.dao.findRoleById(preDefaultId); // 通过前一个id查询成员信息

		if (preRole != null) {

			preRole.setId(preDefaultId);

			preRole.setUserType(1); // 设置前一个成员的用户类型为1,表示普通用户

			this.dao.editMembers(preRole);
		}

		Role currentRole = this.dao.findRoleById(currentDefaultId); // 通过当前id查询成员信息

		if (currentRole != null) {

			currentRole.setId(currentDefaultId);

			currentRole.setUserType(0); // 设置当前成员的用户类型为0,表示默认用户

			this.dao.editMembers(currentRole);
		}

	}

	@Override
	public Map<String, Object> editMembers(Role role, MultipartFile file,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();

		if (role.getId() != null) {

			Role roles = findRoleById(role.getId()); // 通过id查询成员信息

			if (roles != null) {

				if (!(file.isEmpty() || file == null)) { // 判断上传文件是否为空

					try {
						String realPath = FileHandleUtils.uploadFile(request, // 不为空则上传新图片
								file);

						File img = new File(SystemConstant.FILE_URL
								+ File.separator + file.getOriginalFilename());

						if (img.exists()) {

							String headPictureURL = roles.getHeadPicture(); // 获取原图片地址

							if (!StringUtils.isEmpty(headPictureURL)) {

								String[] array = headPictureURL.split("/"); // 拆分字符串

								String fileName = array[array.length - 1]; // 获取到头像名字

								File temp = new File(SystemConstant.FILE_URL
										+ File.separator + fileName);

								if (temp.exists()) {

									temp.delete(); // 删除原图片
								}
							}
						}

						roles.setHeadPicture(realPath); // 设置新图片地址

					} catch (Exception e) {

						e.printStackTrace();
					}
				}

				if (!StringUtils.isEmpty(role.getUserName())) {

					roles.setUserName(role.getUserName());
				}

				if (!StringUtils.isEmpty(role.getUserSex())) {

					roles.setUserSex(role.getUserSex());
				}
				roles.setCreateTime(SystemUtils.dateConvert(new Date()));

				this.dao.editMembers(roles);

				result.put("Status", SystemConstant.RESPONSE_SUCCESS);
			}
		}

		return result;
	}

	@Override
	public Role findRoleById(String id) {

		return this.dao.findRoleById(id);
	}

}
