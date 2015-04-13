/***********************************************
     @ date  : 2015年3月20日,上午9:10:25   
     @ author: Jeffee Chen                  
 ***********************************************
 *   Copyright © Jeffee, All Rights Reserved   *
 ***********************************************
 * 处理链接相关操作
 **/
package com.fb.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlProcess {

	/***用新的token替换掉原来url中的旧token
	 * 数据采集如果不是在一个token的有效期内完成，到下一个token生效时原有url会无效，需要更新
	 * ***/
	public static String updateAccessToken(String url){
		String newUrl = "";
		Pattern pattern = Pattern.compile("access_token=(.*?)&");
		Matcher matcher = pattern.matcher(url+"&");
		if(matcher.find()){
			StringBuffer sb = new StringBuffer();
			matcher.appendReplacement(sb, "access_token="+CommonData.MY_ACCESS_TOKEN+"&");
			matcher.appendTail(sb);
			newUrl = sb.toString();
		}
		return newUrl;
	}
	
	public static String updateLimit(String url) {
		String newUrl = "";
		Pattern pattern = Pattern.compile("limit=(.*?)&");
		Matcher matcher = pattern.matcher(url+"&");
		if(matcher.find()){
			StringBuffer sb = new StringBuffer();
			matcher.appendReplacement(sb, "limit=1000&");
			matcher.appendTail(sb);
			newUrl = sb.toString();
		}
		return newUrl;
	}
	
	/***去除url中的http标记
	 * 原url类似为：
	 * https://graph.facebook.com/v2.2/10154914374065128/comments?limit=1000&
	 * https://graph.facebook.com/v2.0/10154914374065128/comments?limit=1000&
	 * 版本号有所不同
	 * **/
	public static String updateUrl(String url) {
		if (url.trim().startsWith("http"))
			url = UrlProcess.discardHttp(url);
		if(url.indexOf("access_token=")==-1)
			url += "&access_token=" + CommonData.MY_ACCESS_TOKEN + "&";
		return url;
	}

	private static String discardHttp(String url){
		Pattern pattern = Pattern.compile(".*?com/v.*?/(.*)");
		Matcher matcher = pattern.matcher(url);
		if(matcher.find())
			return matcher.group(1);
		return null;
	}
	public static void main(String[] args) {
		String url = "https://graph.facebook.com/v2.2/10154914374065128/comments?limit=1000&";
		UrlProcess.updateUrl(url);

	}

}
