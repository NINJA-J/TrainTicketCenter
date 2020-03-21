package com.icss.Dao.old;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.icss.util.DbInfo;

public abstract class BaseDao {
	
protected Connection connection;	


	public Connection getConnection() throws Exception {
		this.OpenConncetion();
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	/**
	 * ͨ��JDBCֱ�Ӵ�oracle��ȡ���ݿ����ӳ�
	 * @throws Exception
	 */
	public void OpenConncetion() throws Exception{
		try {
			if(connection == null || connection.isClosed()){
				
				DbInfo dbInfo = DbInfo.instance();				
				Class.forName(dbInfo.getDbdriver());	    //�������������Ƿ����
				connection = DriverManager.getConnection(dbInfo.getDbUrl(),dbInfo.getUname(),dbInfo.getPwd());	
			}			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		}catch (SQLException e){
			e.printStackTrace();
			throw e;
		}		
		
	}
	
	/**
	 * �˴����쳣�������׳���ֻ��Ҫ��¼
	 */
	public void closeConnection(){
		
		if( connection != null){
			try {
				if(!connection.isClosed())
					connection.close();	
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	public void beginTransaction() throws Exception{		
		this.OpenConncetion();
		this.connection.setAutoCommit(false);		
	}
	
	public void commit() throws Exception{
		
		if(this.connection != null)
			this.connection.commit();
		
	}
	
	public void rollback() throws Exception{
		if(this.connection != null){
			this.connection.rollback();
		}		
	}

	public int generateStationCover(int s, int e){
		int t=0;
		for(int i = s; i < e; i++) t = t * 2 + 1;
		for(int i = 0; i < s; i++) t *= 2;
		return t;
	}

}
