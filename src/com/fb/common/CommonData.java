package com.fb.common;
/***********************************************
     @ date  : 2015年3月19日,下午3:12:50   
     @ author: Jeffee Chen                  
 ***********************************************
 *   Copyright © Jeffee, All Rights Reserved   *
 ***********************************************
 *  常用的公共数据
 **/


import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.fb.DB.DBProcess;
import com.fb.crawl.Crawl;
import com.restfb.json.JsonObject;
import com.restfb.types.Post;


public class CommonData {


	/***post更新周期，默认抓取两个月内的post****/
	public final static int POST_UPDATE_PERIOD = 30;

	/***comment更新周期，默认更新一个月内的comments***/
	public final static int COMMENT_UPDATE_PERIOD = 30;

	public final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public final static SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");

	//public static String MY_ACCESS_TOKEN = "CAAUD17UpsnUBACocejqsiSjlHQQq5ZCI21ZArkwqWZCtoyaDZBEV4SJprR1Vn5gbDe9babcf0rD7ZBU5NXXqI46W9aVSCirRAraqce4LLIZCNfDaIlXaSG50FN5dGY7yJp2mNQVZAJTlk4fkKmWcIJ8IyG0YnZCtJGXNi9xYTdQPVsZBMs6ZBuzsBri7XYcJZBeRygZD";

	public static String MY_ACCESS_TOKEN = "CAAUD17UpsnUBAPLRDbvUKfZB6ZCAuvpu4TEsbsdARSdEEv1DCmAoBODdZCRS9sxqAE8zWDRWD9YG6fHfXxToZBJgPMNonrKTSCW9KyHSLH6Rx4Gc1LOVBuO3I3ZBw7RlH5tImoEc5K8PatFP6vCnDcNgB1Q8tLQTZBuBTZCotIezFfJksVDUdnxQBZCibh8ZCxfAZD";
	private static Map<String, String> uNameMap;

	private static List<String> userList;

	public static String USER_TABLE = "user_table";

	public static String SUP_USER_TABLE="sup_user_table";

	public static String CORE_USER_TABLE="core_user_table";

	public static String POST_TABLE = "post_table";

	public static String SUP_POST_TABLE = "sup_post_table";

	public static String COMMETN_TABLE = "comment_table";

	public static String RESULT_TABLE = "result_table";

	static {
		initMap();
		userList = new LinkedList<>();
	}

	public static List<String> getUserList() {
		return userList;
	}

	public static String getNameByID(String uid) {
		if (uNameMap.get(uid)!=null){
			return uNameMap.get(uid);
		} else {
			String uName = Crawl.getNameByID(uid);
			uNameMap.put(uid, uName);
			FileProcess.write("E://facebook//nameMapping", uNameMap.toString());
			return uName;
		}
	}
	private static void initMap(){
		uNameMap = new HashMap<>();
		String sql = "SELECT userid, uname from user_table";
		List<String> list = DBProcess.get(sql, 2);
		for (String info : list) {
			String[] strs = info.split(";");
			uNameMap.put(strs[0].trim(), strs[1].trim());
		}
	}
}
