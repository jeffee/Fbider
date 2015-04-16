package com.fb.DB;

import org.apache.commons.dbutils.DbUtils;

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
            StringBuffer stb = new StringBuffer();
            while (rs.next()) {
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


    public static void main(String[] args) {

    }
}
