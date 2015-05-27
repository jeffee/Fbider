package com.fb.object;

import com.fb.common.Common;
import com.fb.common.JsonParse;
import com.restfb.json.JsonObject;

/***********************************************
     @ date  : 2015年3月23日,下午5:11:25   
     @ author: Jeffee Chen                  
 ***********************************************
 *   Copyright © Jeffee, All Rights Reserved   *
 ************************************************/

public class Post {
	private String createTime;
	private String updateTime;
	private String statusType;
	private String link;
	private String message;
	private String type;
	private String objectID;
	private long likeCount = 0;
	private long commentCount = 0;
	private long shareCount = 0;
	private String userID;
	private String postID;
	
	public Post(JsonObject obj) {
		createTime = Common.getTime(obj, "created_time");
		updateTime = Common.getTime(obj, "updated_time");
		statusType = JsonParse.getString(obj, "status_type");
		link = JsonParse.getString(obj, "link");
		message = JsonParse.getString(obj, "message").replaceAll(";", ",").replaceAll("\n", "//").replaceAll("\"", "");
		type = JsonParse.getString(obj, "type");
		objectID = JsonParse.getString(obj, "object_id");
		shareCount = JsonParse.getLong(obj,"shares","count");
		userID = JsonParse.getString(obj, "from", "id");
		postID = JsonParse.getString(obj, "id");
	}

	
	
	public String toString(){
		return postID+";"+userID+";"+objectID+";"+statusType+";"+type+";"+createTime+";"+updateTime+";"+message+";"+shareCount+";"+likeCount+";"+commentCount+";"+link;
	}



	public static void main(String[] args) {
		String str = "2012-07-07T11:12:54+0000";
		System.out.println();

	}

}
