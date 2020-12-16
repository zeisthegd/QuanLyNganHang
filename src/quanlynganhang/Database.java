package quanlynganhang;

import model.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Database {
	static Connection conn = null;

	static final String userDir = System.getProperty("user.dir");
	static final String guiTienBienLaiDir = userDir + "\\BienLai\\GuiTien";
	static final String rutTienBienLaiDir = userDir + "\\BienLai\\RutTien";
	static final String themKhachHangBienLaiDir = userDir + "\\BienLai\\ThemKhachHang";
	static final String chuyenTienBienLaiDir = userDir + "\\BienLai\\ChuyenTien";
	
	
	public static ResultSet getKhachHangResultSet() {
		ResultSet result = null;
		String query = "select * from [KhachHang]";
		try (PreparedStatement pstmt = conn.prepareStatement(query)) {
			result = pstmt.executeQuery();
			return result;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		if (result == null)
			System.out.println("res null");
		return result;

	}

	public static void connectDatabase() {

		try {
			String dbURL = "jdbc:sqlserver://cache\\sqlexpress;databaseName=QuanLyNganHang";
			String username = "sa";
			String password = "6644889";
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(dbURL, username, password);
			if (conn != null) {
				System.out.println("Connected");
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public static boolean updateTien(KhachHang kh) {
		String query = "update [KhachHang] set Tien = ? where CMND = ?";
		try (PreparedStatement pstmt = Database.getConnection().prepareStatement(query)) {
			pstmt.setString(1, kh.getTien().toString());
			pstmt.setString(2, kh.getCmnd().toString());

			pstmt.executeUpdate();
			return true;
		} catch (Exception ex) {
			return false;
		}

	}

	public static void inBienLai(String bienLaiDir, String tenBienLai, String noiDung, JFrame frmThongBao) {

		try {

			File bienLai = new File(bienLaiDir, tenBienLai);
			bienLai.getParentFile().mkdirs();
			OutputStreamWriter myWriter = new OutputStreamWriter(new FileOutputStream(bienLai), StandardCharsets.UTF_8);
			myWriter.write(noiDung);
			myWriter.close();
			JOptionPane.showMessageDialog(frmThongBao, "Đã in biên lai!");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(frmThongBao, "Đã xảy ra lỗi!");
			e.printStackTrace();
		}

	}

	public static Connection getConnection() {
		return conn;
	}

	public static String getCurrentDateTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH_mm_ss");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}
	
	public static String getGuiTienBienLaiDir() {
		return guiTienBienLaiDir;
	}

	public static String getRutTienBienLaiDir() {
		return rutTienBienLaiDir;
	}

	public static String getThemKhachHangBienLaiDir() {
		return themKhachHangBienLaiDir;
	}

	public static String getChuyenTienBienLaiDir() {
		return chuyenTienBienLaiDir;
	}
}
