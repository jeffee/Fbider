package com.fb.object;

/**
 * Created by Jeffee Chen on 2015/5/6.
 */
public class SupPost {

    private String postID;
    private String userID;
    private String commentAfter;
    private String likeAfter;
    private String createdTime;

    public SupPost(String postID, String userID, String commentAfter, String likeAfter, String createdTime) {
        this.postID = postID;
        this.userID = userID;
        this.commentAfter = commentAfter;
        this.likeAfter = likeAfter;
        this.createdTime = createdTime;
    }

    public String toString() {
        return String.format("%s;%s;%s;%s;%s", postID, userID, commentAfter, likeAfter, createdTime);
    }

    public static void main(String[] args) {

    }
}
