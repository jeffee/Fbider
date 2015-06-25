/***********************************************
     @ date  : 2015年4月1日,下午3:41:07   
     @ author: Jeffee Chen                  
 ***********************************************
 *   Copyright © Jeffee, All Rights Reserved   *
 ************************************************/
package com.fb.crawl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fb.common.CommonData;
import com.fb.common.FileProcess;
import com.fb.common.UrlProcess;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.exception.FacebookNetworkException;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.json.JsonObject;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

public class Crawl {

	@SuppressWarnings("deprecation")
	private static FacebookClient client;

	private static long PERIOD = 60*1000;
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
			//System.out.println(url);
			return client.fetchObject(url + "&" + "", JsonObject.class);
		} catch (FacebookOAuthException e) {
			if (e.getErrorCode() == 2) {			//facebook错误，只需重新执行就可以
				initClient();
				return get(url);
			}
		} catch (FacebookNetworkException e) {
			 if (e.getHttpStatusCode() == null) {		//网络错误
				try {
					System.err.println("Haha ************************");
					System.err.println("Network error, please check the network connection!");
					System.err.println("Now is " + CommonData.dateFormat.format(new Date()));
					Thread.sleep(PERIOD);
					System.out.println("hello");
					initClient();
					return get(url);

				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
		return null;
	}

	private static void waitForNet() {

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
		if(jObj==null || jObj.getJsonArray("data").length()<1)		//拿到的数据为空，直接返回
			return list;

		list.add(jObj);					//存储当前数据
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

	/***
	 * 用于抓取comment、like等页面,递归实现
	 *
	 * **/
	public static JsonObject getPage(String url) {
		long beforeTime = System.currentTimeMillis();
		JsonObject jObj = get(url);
		long afterTime = System.currentTimeMillis();
		System.out.println((afterTime - beforeTime) + " ms spents");
		return jObj;
	}

	public static void main(String[] args) {
		String url = "46251501064_10152633836061065/comments?limit=1000";
		JsonObject obj = Crawl.get("10150145806225128?");

		FileProcess.write("E:\\cai.txt", obj.toString());
	}

}
