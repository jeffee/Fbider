package com.fb.DB;

import com.fb.common.CommonData;

/**
 * Created by Jeffee Chen on 2015/5/6.
 */
public class SupPostDB extends DBProcess{

    public static void insert(String pID, String commentAfter, String likeAfter, String createdTime){
        String sql = String.format("insert IGNORE into %s values ('%s','%s','%s','%s','%s')", CommonData.SUP_POST_TABLE, pID, pID.split("_")[0], commentAfter, likeAfter, createdTime);
        update(sql);
    }

    public static void updateCommentAfter(String pID, String commentAfter) {
        String sql = String.format("insert into %s values ('%s','','','2015-01-01','2015-01-01') on duplicate key update commentAfter='%s'", CommonData.SUP_POST_TABLE, pID, commentAfter);
        update(sql);
    }

    public static void updateLikeAfter(String pID, String likeAfter) {
        String sql = String.format("insert into %s values ('%s','','','2015-01-01','2015-01-01') on duplicate key update likeAfter='%s'", CommonData.SUP_POST_TABLE, pID, likeAfter);
        update(sql);
    }

    public static void main(String[] args) {

    }
}
