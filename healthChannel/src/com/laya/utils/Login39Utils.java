package com.laya.utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 39健康网接口工具类
 * 
 * @author yc
 *
 */
public class Login39Utils {

	/**
	 * 
	 * @param timeStamp
	 *            (时间戳) :系统时间戳
	 * @param sign
	 *            (签名) :将传递的参数appid, timestamp ,秘钥key和Json字符串根据参数名称先进行升序排序，
	 *            然后按照key1=value1&key2=value2的方式进行字符串拼接， 最后把拼接好的字符串进行md5加密.
	 * @param entityString
	 *            (Json字符串)
	 * @return
	 */
	

	private static Logger logger = LoggerFactory.getLogger(Login39Utils.class);

	// 提交数据
	public static String postMethod(String timeStamp, String sign,
			String entityString) throws IOException {

		String sendURL = SystemConstant.LOGIN_39_URL
				+ MessageFormat.format("?appid=39&timestamp={0}&sign={1}",
						timeStamp, sign);
		String postString = "=" + entityString; // 传输字符串格式,必须在开头添加一个"="符号
		// Post请求的url，与get不同的是不需要带参数

		URL postUrl = new URL(sendURL);
		// 打开连接
		HttpURLConnection connection = (HttpURLConnection) postUrl
				.openConnection();

		// 设置是否向connection输出，因为这个是post请求，参数要放在http正文内，因此需要设为true
		connection.setDoOutput(true);

		connection.setDoInput(true);
		// 设置请求方式,默认GET
		connection.setRequestMethod("POST");

		// Post 请求不能使用缓存
		connection.setUseCaches(false);
		// 设置连接超时
		connection.setConnectTimeout(50000);
		// 设置读取超时
		connection.setReadTimeout(50000);

		connection.setInstanceFollowRedirects(true);
		// 设置通用参数
		connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");

		connection.setRequestProperty("Accept",
				"application/json, text/javascript, */*; q=0.01");

		connection.connect();

		DataOutputStream out = new DataOutputStream(
				connection.getOutputStream());
		// 发送请求参数
		 out.write(postString.getBytes("utf-8"));
	
		 out.flush(); // flush and close
		 
		if (out != null) {
			out.close();
		}

		// 根据ResponseCode判断连接是否成功
		int responseCode = connection.getResponseCode();
		
		String result = "";
		
		if (responseCode != 200) {
			
			result="{\"code\":0,\"description\":\"远程服务器请求失败!!!\"}";
			
		} else {

			result = SystemUtils.parsRtn(connection.getInputStream());

			logger.info(result);
		}
		connection.disconnect(); // 关闭连接

		return result;
	}

	// 获取签名
	public static String getSign(String appid, String timestamp,
			String entityString) {

		Map<String, String> map = new TreeMap<String, String>(); // 通过Treemap特性对appid,timestamp,entityString,key
																	// 按map中key的自然顺序进行排序
		String appidName = "appid";
		String timeStampName = "timestamp";
		String entityStringName = "entitystring";
		map.put(appidName, appid);
		map.put(timeStampName, timestamp);
		map.put(entityStringName, entityString);
		map.put("key", "LaYA#39.NEt@SecRetkEY");
		String sign = getSignature(map);
		return sign;
	}

	// 进行签名

	private static String getSignature(Map<String, String> map) {
		StringBuilder sb = new StringBuilder(); // StringBuilder 用于拼接字符串

		Iterator<String> iter = map.keySet().iterator();
		while (iter.hasNext()) { // 遍历iterator获取参数并按照key1=value1&key2=value2&......拼接参数
			
			String key = iter.next();
			
			String value = map.get(key);
			
			sb.append(key);
			sb.append("=");
			sb.append(value);
			sb.append("&");
		}
    
		String temp=sb.deleteCharAt(sb.length() - 1).toString();
		
		String result = MD5Coder.MD5(temp); // 将拼接好的参数去掉最后一个&,再进行MD5加密,返回参数
		
		return result;
	}

	// 获取系统时间戳
	public static String getTimeStamp() {

		return String.valueOf(System.currentTimeMillis()); // 将时间戳long类型转化为String并返回
	}
	
}
