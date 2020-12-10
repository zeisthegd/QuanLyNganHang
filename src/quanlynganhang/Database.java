package quanlynganhang;
import model.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
public class Database {
	static Connection conn = null;
	
	public static ResultSet getKhachHangResultSet()
	{
		ResultSet result = null;
		String query = "select * from [KhachHang]";
		try(PreparedStatement pstmt = conn.prepareStatement(query))
		{
			result = pstmt.executeQuery();
			return result;
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		if(result == null)
			System.out.println("res null");
		return result;
		
	}
	
	
	public static void connectDatabase()
	{
		
		try {
			String dbURL = "jdbc:sqlserver://cache\\sqlexpress;databaseName=QuanLyNganHang";
			String username = "sa";
			String password = "6644889";
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(dbURL,username,password);
			if (conn != null) {
				System.out.println("Connected");
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public static Connection getConnection() {
		return conn;
	}
	public static String getCurrentDateTime()
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}
}
