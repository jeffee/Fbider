package com.fb.object;

import com.restfb.json.JsonObject;

/**
 * Created by Jeffee Chen on 2015/4/16.
 */
public class Comment {
    private String commentID;
    private String createTime;
    private String postID;
    private String userID;
    private String userName;
    private String message;
    private long likeCount;

    public Comment(String pID, JsonObject jobj) {
        this.postID = pID;
        this.commentID = jobj.getString("id");
        this.message = jobj.getString("message");
        this.likeCount = jobj.getLong("like_count");
        this.createTime = jobj.getString("created_time");
        this.userID = jobj.getJsonObject("from").getString("id");
        this.userName = jobj.getJsonObject("from").getString("name");
    }

    public String toString() {
        return String.join(";", commentID, postID, userID, userName, message, createTime, ""+likeCount);
    }

}
