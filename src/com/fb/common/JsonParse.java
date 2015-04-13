/***********************************************
     @ date  : 2015年4月1日,下午3:28:51   
     @ author: Jeffee Chen                  
 ***********************************************
 *   Copyright © Jeffee, All Rights Reserved   *
 ***********************************************
 * 解析Json文档
 **/
package com.fb.common;

import com.restfb.json.JsonObject;

public class JsonParse {

	public static String getString(JsonObject obj, String key){
		try {
			return  obj.getString(key);
		} catch (Exception e) {
			return "";
		}
	}
	
	public static String getString(JsonObject obj, String key1, String key2){
		try {
			return  obj.getJsonObject(key1).getString(key2);
		} catch (Exception e) {
			return "";
		}
	}
	
	public static long getLong(JsonObject obj, String key){
		try {
			return obj.getLong(key);
		} catch (Exception e) {
			return 0;
		}
	}

	public static long getLong(JsonObject obj, String key1, String key2){
		try {
			return obj.getJsonObject(key1).getLong(key2);
		} catch (Exception e) {
			return 0;
		}
	}

}
