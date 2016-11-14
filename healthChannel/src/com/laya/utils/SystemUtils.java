package com.laya.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.net.URLCodec;

/**
 * 系统工具类
 * 
 * @author yc
 *
 */

public class SystemUtils {
	/**
	 * 日期格式转换
	 * 
	 * @param date
	 * @return 日期字符串
	 */
	public static String dateConvert(Object date) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		
		return dateFormat.format(date);
	}

	/**
	 * 处理日期(为当前日期添加一天时间)
	 * 
	 * @param currentDate
	 * @return
	 * @throws ParseException
	 */
	public static String strCovertDate(String currentDate)
			throws ParseException {

		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		Date current = dateFormat.parse(currentDate);

		long temp = current.getTime(); // 获取目标日期的毫秒数
	
		long hours = 24 * 60 * 60 * 1000; // 24小时的毫秒数

		long sum = temp + hours; // 合计毫秒数

		Date date = new Date(sum); // 转换为日期

		String result = dateFormat.format(date);

		return result;
	}


	/**
	 * 
	 * 
	 * 产生随机的UUID作为主键
	 */

	public static String constructPrimaryKey() {

		UUID uuid = UUID.randomUUID();                 //产生随机uuid

		String[] temp = uuid.toString().split("-");     //处理字符

		StringBuilder sb = new StringBuilder();

		for (String str : temp) {

			sb.append(str);
		}

		return sb.toString();
	}

	/**
	 * 获取返回数据
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String parsRtn(InputStream is) throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is,
				"utf-8"));
		StringBuffer buffer = new StringBuffer();
		
		String line = null;
		
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}
		if (reader != null) {
			reader.close();
		}
		return buffer.toString();
	}

    
    /**
     * 解码urlencode
     * @param urlEncoder
     * @return
     * @throws DecoderException
     */
    public static String URLDecoder(String urlEncoder) throws DecoderException{
    	
    	 URLCodec decode=new URLCodec();
    	
      return	decode.decode(urlEncoder);
    	
    }
    
    

	/**
	 * 将十六进制字符串转为ascii字符
	 * 
	 * @param str
	 * @return ascii字符串
	 */
	private static String toStringHex1(String str) {
		
		byte[] baKeyword = new byte[str.length() / 2];
		
		for (int i = 0; i < baKeyword.length; i++) {
			
			try {
				
				baKeyword[i] = (byte) (0xff & Integer.parseInt(
						
						str.substring(i * 2, i * 2 + 2), 16));
				
			   } catch (Exception e) {
				
				 e.printStackTrace();
			}
		}
		   try {
			   
			   str = new String(baKeyword, "ASCII");
			   
		     } catch (Exception e1) {
		    	 
			     e1.printStackTrace();
		}
		     return str;
	}

	/**
	 * 处理检测数据字符串
	 * 
	 * @param str
	 *            类似<01003239 34 313731 39>
	 * @return str
	 */
	public static Map<String, Object> strConvert(String str) {

		Map<String, Object> data = new HashMap<String, Object>();
		
		String str1 = str.substring(1, str.length() - 1); // 去掉字符串两边的<>符号
		
		String str2 = str1.replace(" ", ""); // 去掉字符串中间的空格
		
		String str3 = str2.substring(4, str2.length() - 2); // 截取检测结果数据
		
		String str4 = str2.substring(0, 2); // 截取检测数据类型 (01:血糖 02:胆固醇 04:甘油三酯
											// 08:尿酸)
		String temp = toStringHex1(str3); // 将检测结果十六进制数据转换为ascii字符
		
		String result = result(temp, str4); // 将检测结果处理成需要的字符串
		
		data.put("testType", str4); // 添加所有参数到Map中
		
		data.put("result", result);
		
		return data;
	}

	/**
	 * 返回检测数据的字符串格式
	 * 
	 * @param data
	 * @param testType
	 * @return
	 */
	private static String result(String data, String testType) {

		double s = Double.parseDouble(data.substring(0, 3)); // 每3个数字代表一个检测数据,前三个表示温度
		
		double b = Double.parseDouble(data.substring(3, data.length())); //
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(s / 10);
		
		sb.append("摄氏度");
		
		if (SystemConstant.HC_URIC_ACID.equals(testType)) {
			
			sb.append(b / 10);
			
		} else {
			
			sb.append(b);
		}
		sb.append("mg/dL");
		
		return sb.toString();
	}
	
	/**
	 * 验证图片后缀是否为jpg,jpeg,png
	 * @param suffix
	 * @return
	 */

	public static boolean verifySuffix(String suffix){
		
		boolean temp=false;
		
		if("JPG".equalsIgnoreCase(suffix)){
			
			temp= true;
		}
		
		if("JPEG".equalsIgnoreCase(suffix)){
			
			temp=true;
		}
		
		if("PNG".equalsIgnoreCase(suffix)){
			
			temp=true;
		}
		
		   return temp;
	}
	
}
