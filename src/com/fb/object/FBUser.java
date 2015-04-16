/***********************************************
     @ date  : 2015年4月1日,下午2:47:25   
     @ author: Jeffee Chen                  
 ***********************************************
 *   Copyright © Jeffee, All Rights Reserved   *
 ************************************************/
package com.fb.object;

import com.fb.text.TextProcess;
import com.restfb.json.JsonObject;

public class FBUser {

	private String id;

	private String name;

	private String category;

	private long likes;

	private long talking_about_count;

	private String username;

	private String about;

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCategory() {
		return category;
	}

	public long getLikes() {
		return likes;
	}

	public long getTalking_about_count() {
		return talking_about_count;
	}

	public String getUsername() {
		return username;
	}

	public String getAbout() {
		return about;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return TextProcess.join(id, name, username, category, "" + likes, ""
				+ talking_about_count, about);
	}


	public FBUser(JsonObject jobj) {
		this.id = getString("id", jobj);
		this.name = getString("name", jobj);
		this.username = getString("username", jobj);
		this.category = getString("category", jobj);
		this.likes = getLong("likes", jobj);
		this.talking_about_count = getLong("talking_about_count", jobj);
		this.about = getString("about", jobj);
	}

	private String getString(String key, JsonObject jobj) {
		try {
			return jobj.getString(key);
		} catch (Exception e) {
			return "";
		}
	}

	private long getLong(String key, JsonObject jobj) {
		try {
			return jobj.getLong(key);
		} catch (Exception e) {
			return 0;
		}
	}
}
