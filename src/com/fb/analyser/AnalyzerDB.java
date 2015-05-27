package com.fb.analyser;

import com.fb.DB.DBProcess;
import com.fb.common.CommonData;

/**
 * Created by Jeffee Chen on 2015/5/7.
 */
public class AnalyzerDB extends DBProcess{

    public static void onMonth(String uid, String uname,String result) {
        String sql = String.format("insert into %s values ('%s','%s','%s') on duplicate key update monthLike='%s'", CommonData.RESULT_TABLE, uid, uname, result);
        update(sql);
    }

    public static void main(String[] args) {

    }
}
