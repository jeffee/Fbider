package com.fb.DB;

import com.fb.common.CommonData;
import org.apache.commons.dbutils.DbUtils;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Jeffee Chen on 2015/4/10.
 */
public class DBProcess {

    public static List<String> get(String sql, int count) {
        List<String> list = new ArrayList<>();
        Connection conn = null;
        ResultSet rs = null;
        Statement stmt = null;
        try {
            conn = ConnPool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                StringBuffer stb = new StringBuffer();
                stb.append(rs.getString(1));
                for (int i = 2; i <= count; i++) {
                    stb.append(";"+rs.getString(i));
                }
                list.add(stb.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(stmt);
            DbUtils.closeQuietly(conn);
        }
        return list;
    }

    public static String getString(String sql) {
        Connection conn = null;
        ResultSet rs = null;
        Statement stmt = null;
        String result = "";
        try {
            conn = ConnPool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                result = rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(stmt);
            DbUtils.closeQuietly(conn);
        }
        return result;
    }

    public static void update(String sql) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = ConnPool.getConnection();
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(stmt);
            DbUtils.closeQuietly(conn);
        }

    }

    public static Map<String, String> getMap(String sql) {
        Map<String, String> map = new HashMap<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConnPool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String uid = rs.getString(1);
                String since = rs.getString(2);
                map.put(uid, since);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(stmt);
            DbUtils.closeQuietly(conn);
        }
        return map;
    }

    public static void inport(File file, String table){
        inport(file.getPath(), table);
    }

    public static void inport(String fileName, String table) {
        String fStr = fileName.replaceAll("\\\\", "/");
        String sql = "load data infile '" + fStr + "' ignore into table " + table + " fields terminated by ';';";
       // System.out.println(sql);
        Connection conn = ConnPool.getConnection();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.execute(sql);
            new File(fileName).delete();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(stmt);
            DbUtils.closeQuietly(conn);
        }
    }

    public static void main(String[] args) {
        String sql = "select since from "+CommonData.SUP_USER_TABLE+" WHERE uid=\"10150145806225128\"";
        String str = DBProcess.getString(sql);
        System.out.println(str);
       // DBProcess.inport("E:/Data/facebook/raw pages/feeds/蔡英文 Tsai Ing-wen/46251501064_10152633261031065/comments/12", CommonData.COMMETN_TABLE);
        //DBProcess.get(sql, 2);
    }
}
