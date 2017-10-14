package cn.ou.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * ���ݿ⹤����
 * @author Administrator
 *
 */
public class JDBCUtils {
	private JDBCUtils(){}
	
	private static ComboPooledDataSource cpds = new ComboPooledDataSource();
	
	
	/**
	 * ��ȡ���ӳ�
	 * @return ��ȡ���ݿ�����
	 * @throws SQLException  ��ȡ����ʧ��
	 */
	public static Connection getConnection() throws SQLException{
		return cpds.getConnection();
	}
	
	/**
	 * �ر���Դ
	 * @param conn �黹���ݿ�����
	 * @param statement �رմ�����
	 * @param rs �رշ��ؽ����
	 */
	public static void close(Connection conn,Statement statement,ResultSet rs){
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally{
				rs = null;
			}
		}
		
		if(statement != null){
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally{
				statement = null;
			}
		}
		
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally{
				conn = null;
			}
		}
	}
}
