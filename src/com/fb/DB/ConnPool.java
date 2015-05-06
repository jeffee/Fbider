package com.fb.DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;



public class ConnPool {

	private static DataSource dataSource;

	private static String CONN_URL = "jdbc:mysql://192.168.1.77:3306/facebook?autoReconnect=true";
	private static String USER = "root";

	private static String PASS = "123456";


/*	private static String CONN_URL = "jdbc:mysql://192.168.1.208:3306/";
	private static String USER = "root";

	private static String PASS = "186256fx162252";*/

	/**获取连接 */
	public static Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			System.out.println("无法获得连接?");
			e.printStackTrace();
			return null;
		}
	}

	static {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUsername(USER);
		ds.setPassword(PASS);
		ds.setUrl(CONN_URL);
		ds.setInitialSize(4); //
		ds.setMaxActive(20);
		ds.setMaxIdle(5);
		ds.setMaxWait(5000);
		ds.setTestOnBorrow(true);
		ds.setTestOnReturn(true);
		ds.setTestWhileIdle(true);
		ds.setValidationQuery("select count(*) from DUAL");
		dataSource = ds;
	}

	/** ������Դ����״̬ */
	public static Map<String, Integer> getDataSourceStats() throws SQLException {
		BasicDataSource bds = (BasicDataSource) dataSource;
		Map<String, Integer> map = new HashMap<String, Integer>(2);
		map.put("active_number", bds.getNumActive());
		map.put("idle_number", bds.getNumIdle());
		return map;
	}

	/** �ر����Դ */
	protected static void shutdownDataSource() throws SQLException {
		BasicDataSource bds = (BasicDataSource) dataSource;
		bds.close();
	}

	public static void main(String[] args) {
		for (int i = 0; i <= 40; i++) {
			Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;
			try {
				System.out.println("geting!");
				conn = ConnPool.getConnection();
				System.out.println("finished!");
				stmt = conn.createStatement();
				System.out.println(getDataSourceStats());

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (rs != null)
						rs.close();
					if (stmt != null)
						stmt.close();
					if (conn != null)
						conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
