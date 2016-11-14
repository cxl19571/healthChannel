package com.laya.utils;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


/**
 *SMSSDK短信验证接口
 *Android SMSSDK1.3.0以下的版本（不包括1.3.0）
 *iOS SMSSDK1.1.1以下的版本（不包括1.1.1）
 *此接口地址https://web.sms.mob.com/sms/verify
 *所需参数四个,分别表示
 *1.appkey:应用appKey
 *2.phone:手机号码
 *3.zone:区号
 *4.code:验证码
 *返回参数类型{status:200}
 * @author yc
 *
 */

public class Mobile {
	
    public static String SMSSDK_Verify(String appkey,String userPhone,String zone,String code){
    	   String result = requestData(SystemConstant.MESSAGE_VERIFY_URL,
    	   "appkey="+appkey+"&phone="+userPhone+"&zone="+zone+"&&code="+code);
    	          if(result.isEmpty()){
    	        	  
    	        	 return SystemConstant.MOBILE_TIME_OUT;
    	          }
    	          return result;
    }
    

    /**
     * 发起https 请求
     * @param address
     * @param m
     * @return
     */
    public  static String requestData(String address ,String params){
    	
            HttpURLConnection conn = null;
            try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager(){
                public X509Certificate[] getAcceptedIssuers(){return null;}
                public void checkClientTrusted(X509Certificate[] certs, String authType){}
                public void checkServerTrusted(X509Certificate[] certs, String authType){}
            }};
 
            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());
 
            //ip host verify
            HostnameVerifier hv = new HostnameVerifier() {
                 public boolean verify(String urlHostName, SSLSession session) {
                 return urlHostName.equals(session.getPeerHost());
                 }
            };
 
            //set ip host verify
            HttpsURLConnection.setDefaultHostnameVerifier(hv);
 
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
 
            URL url = new URL(address);
            conn = (HttpURLConnection) url.openConnection();
            
            conn.setRequestMethod("POST");// POST
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            // set params ;post params 
            if (params!=null) {
                conn.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                out.write(params.getBytes(Charset.forName("UTF-8")));
                out.flush();
                out.close();
            }
            conn.connect();
            //get result 
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String result =SystemUtils.parsRtn(conn.getInputStream());
                return result;
            } else {
                System.out.println(conn.getResponseCode() + " "+ conn.getResponseMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null)
                conn.disconnect();
        }
        return null;
    }

      

}
