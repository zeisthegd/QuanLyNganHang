package chucnang;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;

import model.KhachHang;
import quanlynganhang.Database;
import quanlynganhang.TrangChu;

import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;

public class ThemKhachHang {

	private JFrame frmThemKhachHang;

	private JTextField txtTenKhachHang;
	private JTextField txtSoCMND;
	private JTextField txtSDT;
	private JFormattedTextField ftxtSoTien;

	private KhachHang khachHangMoi;

	private int minAmount = 100000;
	private int remainingAmount = 50000;

	private JButton btnThem;
	private JTextField txtDiaChi;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ThemKhachHang window = new ThemKhachHang();
					window.frmThemKhachHang.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ThemKhachHang() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmThemKhachHang = new JFrame();
		frmThemKhachHang.setTitle("Thêm Khách Hàng");
		frmThemKhachHang.setBounds(100, 100, 310, 240);
		frmThemKhachHang.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmThemKhachHang.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Thêm Khách Hàng");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(69, 11, 161, 14);
		frmThemKhachHang.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Khách Hàng:");
		lblNewLabel_1.setBounds(10, 42, 100, 14);
		frmThemKhachHang.getContentPane().add(lblNewLabel_1);

		txtTenKhachHang = new JTextField();
		txtTenKhachHang.setBounds(136, 39, 150, 20);
		frmThemKhachHang.getContentPane().add(txtTenKhachHang);
		txtTenKhachHang.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Số CMND:");
		lblNewLabel_2.setBounds(10, 67, 100, 14);
		frmThemKhachHang.getContentPane().add(lblNewLabel_2);

		txtSoCMND = new JTextField();
		txtSoCMND.setColumns(10);
		txtSoCMND.setBounds(136, 64, 150, 20);
		frmThemKhachHang.getContentPane().add(txtSoCMND);

		txtSDT = new JTextField();
		txtSDT.setColumns(10);
		txtSDT.setBounds(136, 89, 150, 20);
		frmThemKhachHang.getContentPane().add(txtSDT);

		JLabel lblNewLabel_2_1 = new JLabel("Số điện thoại:");
		lblNewLabel_2_1.setBounds(10, 92, 100, 14);
		frmThemKhachHang.getContentPane().add(lblNewLabel_2_1);

		JLabel lblNewLabel_2_1_1 = new JLabel("Số tiền gửi lần đầu:");
		lblNewLabel_2_1_1.setBounds(10, 142, 122, 14);
		frmThemKhachHang.getContentPane().add(lblNewLabel_2_1_1);

		ftxtSoTien = new JFormattedTextField(NumberFormat.getNumberInstance());
		ftxtSoTien.setToolTipText("VN\u0110");
		ftxtSoTien.setBounds(136, 139, 150, 20);
		frmThemKhachHang.getContentPane().add(ftxtSoTien);

		btnThem = new JButton("Rút");
		btnThem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thucHienThemKhachHang();
			}

		});
		btnThem.setBounds(108, 167, 89, 23);
		frmThemKhachHang.getContentPane().add(btnThem);

		JLabel lblDiaChi = new JLabel("Địa chỉ:");
		lblDiaChi.setBounds(10, 117, 100, 14);
		frmThemKhachHang.getContentPane().add(lblDiaChi);

		txtDiaChi = new JTextField();
		txtDiaChi.setColumns(10);
		txtDiaChi.setBounds(136, 114, 150, 20);
		frmThemKhachHang.getContentPane().add(txtDiaChi);
	}

	private void thucHienThemKhachHang() {
		if (daNhapDayDuThongTin()) {
			if(duLieuDauVaoHopLe())
			{
				taoKhachHangMoi();
				if(!coTrungLap())
					insertKhachHangVaoCSDL();
			}
		}
	}

	private void insertKhachHangVaoCSDL() {

		String query = "insert into [KhachHang] values(?,?,?,?,?)";
		try (PreparedStatement pstmt = Database.getConnection().prepareStatement(query)) {
			pstmt.setString(1, khachHangMoi.getTenKhachHang().toString());
			pstmt.setString(2, khachHangMoi.getCmnd().toString());
			pstmt.setString(3, khachHangMoi.getDiaChi().toString());
			pstmt.setString(4, khachHangMoi.getSdt().toString());
			pstmt.setString(5, khachHangMoi.getTien().toString());

			pstmt.executeUpdate();
			showMessage("Đã thực insert khách hàng mới vào CSDL!");
		} catch (Exception ex) {
			showMessage(ex.getMessage());
		}
	}
	
	private boolean duLieuDauVaoHopLe()
	{
		if(!(txtTenKhachHang.getText().toString().length() >= 5))
		{
			showMessage("Tên khách hàng phải là chuỗi lớn hơn 5 kí tự!");
			return false;
		}
		else if(!(txtSoCMND.getText().length() == 9))
		{
			showMessage("Số chứng minh nhân dân là số có 9 chữ số!");
			return false;
		}
		else if(!(txtSDT.getText().length() >= 10))
		{
			showMessage("Số điện thoại là số có hơn 9 chữ số!");
			return false;
		}
		else if(!(Integer.parseInt(ftxtSoTien.getText().toString()) >= 50000))
		{
			showMessage("Số tiền gửi lần đầu phải lớn hơn hoặc bằng 50000!");
			return false;
		}
		return true;
	}

	private boolean daNhapDayDuThongTin() {
		if (checkTextField(txtTenKhachHang, "Bạn chưa nhập tên khách hàng!"))
			return true;
		else if (checkTextField(txtSoCMND, "Bạn chưa nhập số CMND của khách hàng!"))
			return true;
		else if (checkTextField(txtSDT, "Bạn chưa nhập số điện thoại khách hàng!"))
			return true;
		else if (checkTextField(txtDiaChi, "Bạn chưa nhập địa chỉ!"))
			return true;
		else if (checkTextField(ftxtSoTien, "Bạn chưa nhập số tiền gửi lần đầu!"))
			return true;
		return false;
	}

	private void taoKhachHangMoi() {
		
		int id = 0;
		String ten = txtTenKhachHang.getText().toString();
		String cmnd = txtSoCMND.getText().toString();
		String diaChi = txtDiaChi.getText().toString();
		String sdt = txtSDT.getText().toString();
		BigDecimal tien = new BigDecimal(ftxtSoTien.getText().toString().replace(",", ""));

		khachHangMoi = new KhachHang(id, ten, cmnd, diaChi, sdt, tien);

	}

	private boolean coTrungLap() {
		String cmnd = "select * from [KhachHang] where CMND = ?";
		String sdt = "select * from [KhachHang] where SDT = ?";
		if (trungLap(cmnd, txtSoCMND.getText())) {
			showMessage("Số CMND đã tồn tại trong CSDL!");
			return true;
		} else if (trungLap(sdt, txtSDT.getText())) {
			showMessage("Số điện thoại đã tồn tại trong CSDL!");
			return true;
		}
		return false;

	}

	private boolean trungLap(String query, String variableToCheck) {
		try (PreparedStatement pstmt = Database.getConnection().prepareStatement(query)) {
			pstmt.setString(1, variableToCheck);

			ResultSet result = pstmt.executeQuery();
			if (result.next())
				return true;
			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			return true;
		}
	}

	private boolean checkTextField(JTextField txtField, String errorMessage) {
		if (txtField.getText().isEmpty()) {
			showMessage(errorMessage);
			return false;
		}
		return true;
	}

	private void inBienLai() {
		try {
			String saveDirectory = System.getProperty("user.dir") + "\\BienLai\\ThemKhachHang";
			String tenBienLai = khachHangMoi.getCmnd() + "_THEM-KHACH-HANG_" + Database.getCurrentDateTime() + ".txt";

			File bienLai = new File(saveDirectory, tenBienLai);
			bienLai.getParentFile().mkdirs();
			FileWriter myWriter = new FileWriter(bienLai);

			myWriter.write("----- THÊM KHÁCH HÀNG -----");
			myWriter.write("\nTên nhân viên thực hiện: " + TrangChu.getTenNhanVien());
			myWriter.write("\nTên khách hàng: " + khachHangMoi.getTenKhachHang());
			myWriter.write("\nSố CMND: " + khachHangMoi.getCmnd());		
			myWriter.write("\nSố điện thoại: " + khachHangMoi.getSdt());
			myWriter.write("\nĐịa chỉ: " + khachHangMoi.getDiaChi());
			myWriter.write("\nSố tiền gửi lần đầu: " + ftxtSoTien.getText().toString());
			myWriter.write("\nNgày giao dịch: " + Database.getCurrentDateTime());
			myWriter.close();

			showMessage("Đã in biên lai!");
		} catch (IOException e) {
			showMessage("Đã xảy ra lỗi!");
			e.printStackTrace();
		}
	}

	private void showMessage(String message) {
		JOptionPane.showMessageDialog(frmThemKhachHang, message);
	}

	public JFrame getFrame() {
		return frmThemKhachHang;
	}
}
