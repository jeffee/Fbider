/***********************************************
     @ date  : 2015年4月1日,下午3:41:07   
     @ author: Jeffee Chen                  
 ***********************************************
 *   Copyright © Jeffee, All Rights Reserved   *
 ************************************************/
package com.fb.crawl;

import java.util.ArrayList;
import java.util.List;

import com.fb.common.UrlProcess;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.json.JsonObject;

public class Crawl {

	@SuppressWarnings("deprecation")
	private static FacebookClient client;

	static {
		initClient();
	}

	/***
	 * @todo 当发生client权限错误时再重新初始化client
	 * ***/
	@SuppressWarnings("deprecation")
	public static JsonObject get(String url) {
		url = UrlProcess.updateUrl(url);
		try {
			System.out.println(url);
			return client.fetchObject(url + "", JsonObject.class);
		} catch (FacebookOAuthException e) {
			e.printStackTrace();
			if(e.getErrorCode()==2){
				initClient();
				return get(url);
			}

/*			System.out.println(e.getErrorCode());
			System.err.println("The client has been re-initiated^^^^^^^^^^");
			initClient();
			return get(url);*/
		}
		return null;
	}



	private static void initClient() {
		client = new DefaultFacebookClient(Version.VERSION_2_2);
	}

	public static String getNameByID(String uid) {
		String url = uid + "?fields=name&";
		return get(url).getString("name");
	}

	/***
	 * 用于抓取comment、like等页面,递归实现
	 *
	 * **/
	public static List<JsonObject> getPages(String url) {
		long beforeTime = System.currentTimeMillis();
		List<JsonObject> list = new ArrayList<>();
		String next;
		JsonObject jObj = get(url);
		list.add(jObj);
		try {
			next = jObj.getJsonObject("paging").getString("next") + "&";
		} catch (Exception e) {
			return list;
		}
		long afterTime = System.currentTimeMillis();
		System.out.println((afterTime-beforeTime)+" ms spents");
		list.addAll(getPages(next));
		return list;
	}

	public static void main(String[] args) {
		String url = "232716627404_10152748927117405?";
		List<JsonObject> list = Crawl.getPages(url);
		for (JsonObject obj : list) {
			System.out.println(obj.toString());
		}
		System.out.println(list.size());
	}

}
