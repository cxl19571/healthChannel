package com.laya.utils;

import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtils {
	
    /**
     * 将Json字符串转化为Json对象
     * 
     */
    
    public static JSONObject stringParseJSON(String jsonEntity){
    	
    	return JSONObject.fromObject(jsonEntity);
    }
    
/**
 * 将map转换为json字符串
 * @param param
 * @return
 */
    public  static String mapPasrseJSON(Map<String,Object> param){
    	
    	JSONObject object=new JSONObject();
    	           object.putAll(param);
    	           
    	return object.toString();
    }
    
    /**
     * 通过json对象转换json数组
     * @param object
     * @param key
     * @return
     */
    public static JSONArray stringParseJSONArray(JSONObject jsonEntity,String key){
    	
    	return jsonEntity.getJSONArray(key);
    }

}
