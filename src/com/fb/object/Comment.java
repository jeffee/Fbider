package com.fb.object;

import com.fb.common.Common;
import com.fb.common.JsonParse;
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

    public Comment(String pID, JsonObject obj) {
        postID = pID;
        message = JsonParse.getString(obj, "message").replaceAll(";", ",").replaceAll("\n", "//").replaceAll("\"", "");
        commentID = JsonParse.getString(obj, "id");
        likeCount = JsonParse.getLong(obj, "like_count");
        createTime = JsonParse.getString(obj, "created_time");
        userID = JsonParse.getString(obj, "from", "id");
        userName = JsonParse.getString(obj, "from", "name");
    }

    public String toString() {
        return String.join(";", commentID, postID, userID, userName, message, createTime, ""+likeCount);
    }

}
