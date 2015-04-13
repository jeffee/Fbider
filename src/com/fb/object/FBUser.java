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

	private String link;

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

	public String getLink() {
		return link;
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
				+ talking_about_count, about, link);
	}


	public FBUser(JsonObject jobj) {
		this.id = jobj.getString("id");
		this.name = jobj.getString("name");
		this.username = jobj.getString("username");
		this.category = jobj.getString("category");
		this.likes = jobj.getLong("likes");
		this.talking_about_count = jobj.getLong("talking_about_count");
		this.about = jobj.getString("about");
		this.link = jobj.getString("link");
	}

}
