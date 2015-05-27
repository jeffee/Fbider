package com.fb.DB;

import com.fb.common.CommonData;

/**
 * Created by Jeffee Chen on 2015/5/6.
 * 与post相关的数据库操作
 */
public class PostDB extends DBProcess{

    private static String pattern = "'','','','','2015-01-01','2015-01-01','',0,0,0,''";       //表内除ID之外的各字段

    public static void updateLikeCount(String id, long count) {
        String sql = String.format("insert into %s values ('%s', %s) on duplicate key update likeCount=%d", CommonData.POST_TABLE, id, pattern, count);
        update(sql);
    }

    public static void updateCommentCount(String id, long count) {
        String sql = String.format("insert into %s values ('%s', %s) on duplicate key update commentCount=%d", CommonData.POST_TABLE, id, pattern, count);
        update(sql);

    }

    public static void updateTotalCount(String id, long likeCount, long commentCount) {
        String sql = String.format("insert into %s values ('%s', %s) on duplicate key update likeCount=%d, commentCount=%d", CommonData.POST_TABLE, id, pattern, likeCount, commentCount);
        update(sql);
    }

    public static void main(String[] args) {

    }
}
