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
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.text.Format;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Color;

public class ChuyenTien {

	private JFrame frmChuyenTien;
	private JTextField txtTenKhachHang;
	private JTextField txtSoCMND;
	private JTextField txtSDT;
	private JFormattedTextField ftxtSoTien;

	private KhachHang kh;
	private KhachHang nguoiNhan;
	
	private BigDecimal soTienChuyen;

	private int minAmount = 100000;
	private int maxAmount = 900000000;

	private JButton btnGui;
	private JPanel pNguoiChuyen_1;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_2_2;
	private JTextField txtNNhanTen;
	private JTextField txtNNhanCMND;
	private JTextField txtNNhanSDT;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChuyenTien window = new ChuyenTien();
					window.frmChuyenTien.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ChuyenTien() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmChuyenTien = new JFrame();
		frmChuyenTien.setTitle("Chuyển tiền");
		frmChuyenTien.setBounds(100, 100, 488, 340);
		frmChuyenTien.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmChuyenTien.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Chuyển Tiền");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(184, 11, 100, 14);
		frmChuyenTien.getContentPane().add(lblNewLabel);

		btnGui = new JButton("Chuyển Tiền");
		btnGui.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thucHienChuyenTien();
			}
		});
		btnGui.setBounds(326, 36, 138, 23);
		frmChuyenTien.getContentPane().add(btnGui);

		JPanel pNguoiChuyen = new JPanel();
		pNguoiChuyen.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Ng\u01B0\u1EDDi chuy\u1EC3n:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pNguoiChuyen.setBounds(10, 36, 306, 132);
		frmChuyenTien.getContentPane().add(pNguoiChuyen);
		pNguoiChuyen.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Khách Hàng:");
		lblNewLabel_1.setBounds(7, 21, 121, 14);
		pNguoiChuyen.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Số CMND:");
		lblNewLabel_2.setBounds(7, 46, 100, 14);
		pNguoiChuyen.add(lblNewLabel_2);

		JLabel lblNewLabel_2_1 = new JLabel("Số điện thoại:");
		lblNewLabel_2_1.setBounds(7, 71, 100, 14);
		pNguoiChuyen.add(lblNewLabel_2_1);

		JLabel lblNewLabel_2_1_1 = new JLabel("S\u1ED1 ti\u1EC1n c\u1EA7n g\u1EEDi:");
		lblNewLabel_2_1_1.setBounds(7, 96, 121, 14);
		pNguoiChuyen.add(lblNewLabel_2_1_1);

		txtTenKhachHang = new JTextField();
		txtTenKhachHang.setBounds(138, 21, 150, 20);
		pNguoiChuyen.add(txtTenKhachHang);
		txtTenKhachHang.setEditable(false);
		txtTenKhachHang.setColumns(10);

		txtSoCMND = new JTextField();
		txtSoCMND.setBounds(138, 46, 150, 20);
		pNguoiChuyen.add(txtSoCMND);
		txtSoCMND.setEditable(false);
		txtSoCMND.setColumns(10);

		txtSDT = new JTextField();
		txtSDT.setBounds(138, 71, 150, 20);
		pNguoiChuyen.add(txtSDT);
		txtSDT.setEditable(false);
		txtSDT.setColumns(10);

		ftxtSoTien = new JFormattedTextField(NumberFormat.getNumberInstance());
		ftxtSoTien.setBounds(138, 95, 150, 20);
		pNguoiChuyen.add(ftxtSoTien);
		ftxtSoTien.setToolTipText("VN\u0110");

		pNguoiChuyen_1 = new JPanel();
		pNguoiChuyen_1.setBorder(new TitledBorder(null, "Người nhận:", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		pNguoiChuyen_1.setLayout(null);
		pNguoiChuyen_1.setBounds(10, 179, 306, 112);
		frmChuyenTien.getContentPane().add(pNguoiChuyen_1);

		lblNewLabel_3 = new JLabel("Khách Hàng:");
		lblNewLabel_3.setBounds(10, 49, 121, 14);
		pNguoiChuyen_1.add(lblNewLabel_3);

		lblNewLabel_2_2 = new JLabel("Số điện thoại:");
		lblNewLabel_2_2.setBounds(10, 74, 100, 14);
		pNguoiChuyen_1.add(lblNewLabel_2_2);

		txtNNhanTen = new JTextField();
		txtNNhanTen.setEditable(false);
		txtNNhanTen.setColumns(10);
		txtNNhanTen.setBounds(141, 46, 150, 20);
		pNguoiChuyen_1.add(txtNNhanTen);

		txtNNhanSDT = new JTextField();
		txtNNhanSDT.setEditable(false);
		txtNNhanSDT.setColumns(10);
		txtNNhanSDT.setBounds(141, 74, 150, 20);
		pNguoiChuyen_1.add(txtNNhanSDT);

		lblNewLabel_4 = new JLabel("Số CMND:");
		lblNewLabel_4.setBounds(10, 24, 100, 14);
		pNguoiChuyen_1.add(lblNewLabel_4);

		txtNNhanCMND = new JTextField();
		txtNNhanCMND.setBounds(141, 15, 150, 20);
		pNguoiChuyen_1.add(txtNNhanCMND);
		txtNNhanCMND.setColumns(10);

		JButton btnFind = new JButton("Tìm Người Nhận");
		btnFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timNguoiNhan();
			}

			
		});
		btnFind.setBounds(326, 179, 138, 23);
		frmChuyenTien.getContentPane().add(btnFind);
	}

	public void setThongTinNguoiChuyen(KhachHang kh) {
		this.kh = kh;
		txtTenKhachHang.setText(kh.getTenKhachHang());
		txtSoCMND.setText(kh.getCmnd());
		txtSDT.setText(kh.getSdt());

	}

	private void thucHienChuyenTien() {
		if(daNhapDayDuThongTin())
		{
			if(nguoiChuyenDuDieuKienChuyenTien())
			{
				chuyenTien();
			}
		}
	}
	
	private boolean daNhapDayDuThongTin() {
		if(nguoiNhan != null)
		{
			if(!ftxtSoTien.getText().isEmpty())
			{
				return true;
			}
			else
				JOptionPane.showMessageDialog(frmChuyenTien, "Số tiền cần chuyển chưa được xác định!");			
		}
		else
		{
			JOptionPane.showMessageDialog(frmChuyenTien, "Người nhận chưa được xác định!");
		}
		return false;
	}

	private void timNguoiNhan() {
		String query = "select * from [KhachHang] where CMND = ?";
		try(PreparedStatement pstmt = Database.getConnection().prepareStatement(query))
		{
			pstmt.setString(1, txtNNhanCMND.getText().toString());
			ResultSet result = pstmt.executeQuery();
						
			if(result.next())
			{
				nguoiNhan = new KhachHang(Integer.parseInt(result.getString(1)) , result.getString(2), result.getString(3), 
						result.getString(4), result.getString(5), new BigDecimal(result.getString(6)));
				displayNguoiNhan();
				return;
			}
			else
			{
				txtNNhanSDT.setText("");
				txtNNhanCMND.setText("");
				txtNNhanTen.setText("");
				nguoiNhan = null;
				JOptionPane.showMessageDialog(frmChuyenTien, "Không tìm thấy!");
			}
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(frmChuyenTien, "Lỗi trong quá trình tìm người nhận!");
		}
		
	}
	
	private void displayNguoiNhan()
	{
		if(nguoiNhan != null)
		{
			txtNNhanSDT.setText(nguoiNhan.getSdt());
			txtNNhanTen.setText(nguoiNhan.getTenKhachHang());
		}
	}

	private void chuyenTien() {
		BigDecimal newTienNguoiNhan = nguoiNhan.getTien().add(soTienChuyen);
		BigDecimal newTienNguoiChuyen = kh.getTien().subtract(soTienChuyen);
		
		kh.setTien(newTienNguoiChuyen);
		nguoiNhan.setTien(newTienNguoiNhan);
		
		if (Database.updateTien(kh) && Database.updateTien(nguoiNhan))
		{
			JOptionPane.showMessageDialog(frmChuyenTien, "Đã thực hiện chuyển tiền!");
			inBienLai();
		}
		else
			JOptionPane.showMessageDialog(frmChuyenTien, "Lỗi trong quá trình thực hiện chuyển tiền!");
	}

	private void inBienLai() {
		String tenBienLai = kh.getCmnd() + "_CHUYEN-TIEN_" + Database.getCurrentDateTime() + ".txt";
		String noiDung = "";

		noiDung += "----- CHUYỂN TIỀN -----";
		noiDung += "\nTên nhân viên thực hiện: " + TrangChu.getTenNhanVien();
		noiDung += "\n----- Người chuyển -----";
		noiDung += "\nTên người chuyển: " + kh.getTenKhachHang();
		noiDung += "\nSố CMND: "+ kh.getCmnd();
		noiDung += "\nSố tiền chuyển: " + ftxtSoTien.getText().toString();
		noiDung += "\n----- Người nhận -----";
		noiDung += "\nTên người nhận: " + nguoiNhan.getTenKhachHang();
		noiDung += "\nSố CMND: "+ nguoiNhan.getCmnd();
		noiDung += "\nSố tiền nhận: " + ftxtSoTien.getText().toString();
		noiDung += "\nNgày giao dịch: " + Database.getCurrentDateTime();

		Database.inBienLai(Database.getChuyenTienBienLaiDir(), tenBienLai, noiDung, frmChuyenTien);
	}

	private boolean nguoiChuyenDuDieuKienChuyenTien()
	{
		
		if(!ftxtSoTien.getText().isEmpty())
		{
			soTienChuyen = new BigDecimal(ftxtSoTien.getText().toString().replace(",",""));
			if(kh.getTien().intValue() > soTienChuyen.intValue())
			{
				if(kh.getTien().subtract(soTienChuyen).intValue() >= 50000)
				{
					return true;
				}
				else
					JOptionPane.showMessageDialog(frmChuyenTien, "Số tiền còn lại phải lớn hơn 50000");
			}
			else
				JOptionPane.showMessageDialog(frmChuyenTien, kh.getTenKhachHang() + " không đủ tiền để thực hiện chuyển tiền!");
		}
		return false;
	}
	
	public JFrame getFrame() {
		return frmChuyenTien;
	}
}
