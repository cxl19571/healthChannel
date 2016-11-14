package com.laya.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 处理文件的工具类 包含文件上传,下载
 * 
 * @author Administrator
 *
 */
public class FileHandleUtils {

	/**
	 * 此方法用于文件,附件的下载
	 * 
	 * @param request
	 *            response,fileName
	 * 
	 */

	public static void downLoadFile(HttpServletRequest request,
			HttpServletResponse response, String fileName) {
		InputStream is = null;
		OutputStream os = null;
		// System.out.println(request.getCharacterEncoding());
		response.setCharacterEncoding("utf-8"); // 设置响应给客户端的编码格式
		response.setContentType("multipart/form-data"); // 设置响应类型

		response.setHeader("Content-Disposition", "attachment;fileName=" // 设置响应头
				+ fileName);
		try {
			// System.out.println(fileName);
			File file = new File(fileName);
			// System.out.println(file.getAbsolutePath());
			String realPath = request.getSession().getServletContext()
					.getRealPath("WEB-INF/uploadFolder"); // 得到文件在服务器中的地址
			// System.out.println(realPath);
			is = new FileInputStream(realPath + "/" + file);
			os = response.getOutputStream(); // 返回给客户端
			byte[] b = new byte[1024];
			int length;
			while ((length = is.read(b)) > 0) { // 开始读入内存中

				os.write(b, 0, length); // 开始写到客户端
			}
			os.flush(); // 刷新
			is.close(); // 关闭输入流
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 此方法用于文件,附件的上传
	 * 
	 * @param file
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	public static String uploadFile(HttpServletRequest request,
			MultipartFile file) throws IllegalStateException, IOException {
		
		
	//	 String realPath = request.getSession().getServletContext().getRealPath("uploadFolder");  //服务器存放文件地址

		 String resultURL = "";
		
		if(!StringUtils.isEmpty(file.getOriginalFilename())){    //判断文件名是否为空
			
			//对文件大小进行约束,小于等于1M文件才允许上传
			if (file.getSize() <=1024000) {
				
				File filePath = new File(SystemConstant.FILE_URL);
				
				if (!filePath.exists()) {   //文件夹不存在则创建
					filePath.mkdirs();
				}
	
				file.transferTo(new File(SystemConstant.FILE_URL + File.separator + file.getOriginalFilename())); // 开始上传

				InetAddress addr = InetAddress.getLocalHost();                 //获取主机地址
				
				resultURL = "http://" + addr.getHostAddress() +":"+ request.getLocalPort()
						+ request.getContextPath() + "/"+"uploadFolder" +"/"
						+ file.getOriginalFilename();                       // 返回文件地址供客户端调用

			}
			
		}
		    
		return resultURL;
	}
}