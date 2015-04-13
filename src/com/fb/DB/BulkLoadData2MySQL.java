package com.fb.DB;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BulkLoadData2MySQL {

	public BulkLoadData2MySQL() {
		// TODO Auto-generated constructor stub
	}
	 //private static final Logger logger = Logger.getLogger(BulkLoadData2MySQL.class);  
	   
	    private static Connection conn = null;  
	    
	 	  
	    /** 
	     *  
	     * load bulk data from InputStream to MySQL 
	     */  
	    public static int bulkLoadFromInputStream(String sql,  InputStream dataStream) throws SQLException {  
	        if(dataStream==null){  
	           // logger.info("InputStream is null ,No data is imported");  
	            return 0;  
	        }  
	        conn = ConnPool.getConnection();  
	        PreparedStatement statement = conn.prepareStatement(sql);  
	  
	        int result = 0;  
	  
	        if (statement.isWrapperFor(com.mysql.jdbc.Statement.class)) {  
	  
	            com.mysql.jdbc.PreparedStatement mysqlStatement = statement  
	                    .unwrap(com.mysql.jdbc.PreparedStatement.class);  
	  
	            mysqlStatement.setLocalInfileInputStream(dataStream);  
	            result = mysqlStatement.executeUpdate();  
	        }  
	        return result;  
	    }  
	  
	    public static void main(String[] args) {  
	        String testSql = "LOAD DATA LOCAL INFILE 'sql.csv' IGNORE INTO TABLE fb.PostTable";  
	        
	      /*  InputStream dataStream = getTestDataInputStream();  
	        BulkLoadData2MySQL dao = new BulkLoadData2MySQL();  
	        try {  
	            //long beginTime=System.currentTimeMillis();  
	            int rows=dao.bulkLoadFromInputStream(testSql, dataStream);  
	            //long endTime=System.currentTimeMillis();  
	            //logger.info("importing "+rows+" rows data into mysql and cost "+(endTime-beginTime)+" ms!");  
	        } catch (SQLException e) {  
	            e.printStackTrace();  
	        }  
	        System.exit(1);  */
	    }  
}