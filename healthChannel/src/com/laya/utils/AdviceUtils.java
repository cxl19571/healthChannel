package com.laya.utils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 接口说明:此接口为39问医生提问接口 参数说明: 必填参数;
 * 
 * @param appkey
 *            :askAPP
 * @param appsecret
 *            : 73613865e1b5551589bf6e61e69f4050
 * @param istreat
 *            :是否看过医生,0表示没看过;1表示看过.
 * @param content
 *            :提问的内容,问题描述.
 * @param sex
 *            :性别 .1表示男性; 2表示女性.
 * @param age
 *            :年龄
 * @param imagecount
 *            :上传图片数. 默认是9,如果大于0的时候再去获取images参数中的值
 * @param tomid
 *            :医生ID.默认是0,当用户点击了向医生提问,再把医生ID传递过来
 * @param ip
 *            :客户单ip地址
 * @param mhid
 *            :病例ID.默认是0
 * @param sysio
 *            :表示客户端设备类型 1.ios;2.android
 * 
 *            可选参数;
 * @param memberid
 *            :用户ID
 * @param images
 *            :上传的图片最多3张,文件流格式传输.例如:file[0],file[1],file[3]
 * @param tokenid
 *            :如果用户已经登陆,就传递.
 * @param newkeyname
 *            :医生诊断结果(当选择有看过医生的时候,可以填写此内容)
 * @param symptomdate
 *            :症状持续时间(当选择没有看过医生的时候,可以填写此内容)
 * 
 * @author yc
 *
 */

public class AdviceUtils {


	public static String postMethod(Map<String, Object> params,String postURL)
			throws Exception {
		
		Iterator<String> iter = params.keySet().iterator();               //获取参数列表

		MultipartEntity entity = new MultipartEntity();

		while (iter.hasNext()) {

			String key = iter.next();

			Object value = params.get(key);

			if (params.containsKey("images")) {           //如果包含图片则上传

				if (params.get("images") != null) {

					MultipartFile[] multipartFile = (MultipartFile[]) params
							.get("images");

					for (MultipartFile file : multipartFile) {
						
						String realPath=SystemConstant.FILE_URL+ File.separator + file.getOriginalFilename();

						file.transferTo(new File(realPath)); // 图片上传到本地

						entity.addPart(
								"images",
								new FileBody(new File(SystemConstant.FILE_URL
										+ File.separator
										+ file.getOriginalFilename()))); // 图片上传到远程服务器
					}

				}
			}
			entity.addPart(
					key,
					new StringBody(String.valueOf(value), Charset
							.forName("UTF-8"))); // 传递字符串

		}

		HttpClient client = new DefaultHttpClient();
         
		HttpPost post = new HttpPost(postURL);

		client.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 5000); // 设置连接超时时间

		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000); // 设置数据传输时间

		post.setEntity(entity);

		HttpResponse response = client.execute(post); // 执行post请求

		int code = response.getStatusLine().getStatusCode(); // 获取服务端响应代码

		String result = "";
		
		if (code == HttpStatus.SC_OK) { // 响应200则代表成功

			result = EntityUtils.toString(response.getEntity());
     
		}else{
			
			result="{\"code\":0,\"description\":\"远程服务器请求失败!!!\"}";
		}

		return result;

	}
   

}
