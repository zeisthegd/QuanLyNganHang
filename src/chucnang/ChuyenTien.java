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
import java.text.NumberFormat;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.text.Format;
import javax.swing.border.TitledBorder;

public class ChuyenTien {

	private JFrame frmChuyenTien;
	private JTextField txtTenKhachHang;
	private JTextField txtSoCMND;
	private JTextField txtSDT;
	private JFormattedTextField ftxtSoTien;

	private KhachHang kh;

	private int minAmount = 100000;
	private int maxAmount = 900000000;

	private JButton btnGui;
	private JPanel pNguoiChuyen_1;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_2_2;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

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
		pNguoiChuyen.setBorder(new TitledBorder(null, "Ng\u01B0\u1EDDi chuy\u1EC3n", TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
																		pNguoiChuyen_1.setBorder(new TitledBorder(null, "Ng\u01B0\u1EDDi nh\u1EADn:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
																		pNguoiChuyen_1.setLayout(null);
																		pNguoiChuyen_1.setBounds(10, 179, 306, 112);
																		frmChuyenTien.getContentPane().add(pNguoiChuyen_1);
																		
																		lblNewLabel_3 = new JLabel("Khách Hàng:");
																		lblNewLabel_3.setBounds(10, 49, 121, 14);
																		pNguoiChuyen_1.add(lblNewLabel_3);
																		
																		lblNewLabel_2_2 = new JLabel("Số điện thoại:");
																		lblNewLabel_2_2.setBounds(10, 74, 100, 14);
																		pNguoiChuyen_1.add(lblNewLabel_2_2);
																		
																		textField = new JTextField();
																		textField.setEditable(false);
																		textField.setColumns(10);
																		textField.setBounds(141, 46, 150, 20);
																		pNguoiChuyen_1.add(textField);
																		
																		textField_2 = new JTextField();
																		textField_2.setEditable(false);
																		textField_2.setColumns(10);
																		textField_2.setBounds(141, 74, 150, 20);
																		pNguoiChuyen_1.add(textField_2);
																		
																		lblNewLabel_4 = new JLabel("Số CMND:");
																		lblNewLabel_4.setBounds(10, 24, 100, 14);
																		pNguoiChuyen_1.add(lblNewLabel_4);
																		
																		textField_1 = new JTextField();
																		textField_1.setBounds(141, 15, 150, 20);
																		pNguoiChuyen_1.add(textField_1);
																		textField_1.setColumns(10);
																		
																		JButton btnFind = new JButton("Tìm Khách Hàng");
																		btnFind.setBounds(326, 179, 138, 23);
																		frmChuyenTien.getContentPane().add(btnFind);
	}

	public void setThongTinKhachHang(KhachHang kh) {
		this.kh = kh;
		txtTenKhachHang.setText(kh.getTenKhachHang());
		txtSoCMND.setText(kh.getCmnd());
		txtSDT.setText(kh.getSdt());

	}

	private void thucHienChuyenTien() {
		if (!ftxtSoTien.getText().isEmpty()) {
			String tienGui = ftxtSoTien.getText().toString().replace(",", "");

			BigDecimal inputAmount = new BigDecimal(tienGui);
			if (inputAmount.intValue() <= maxAmount) {
				if (inputAmount.intValue() >= minAmount) {
					ChuyenTien(inputAmount);
					inBienLai();

				} else {
					JOptionPane.showMessageDialog(frmChuyenTien, "Số tiền gửi phải lớn hơn " + minAmount);
				}
			} else {
				JOptionPane.showMessageDialog(frmChuyenTien, "Số tiền gửi phải nhỏ hơn " + maxAmount);
			}
		}
	}

	
	
	private void ChuyenTien(BigDecimal addAmount) {
		BigDecimal newTien = kh.getTien().add(addAmount);
		kh.setTien(newTien);
		if(Database.updateTien(kh))
			JOptionPane.showMessageDialog(frmChuyenTien, "Đã thực hiện gửi tiền!");
		else 
			JOptionPane.showMessageDialog(frmChuyenTien, "Lỗi trong quá trình thực hiện gửi tiền!");
	}

	private void inBienLai() {
		String tenBienLai = kh.getCmnd() + "_GUI-TIEN_" + Database.getCurrentDateTime() + ".txt";
		String noiDung = "";

		noiDung += "----- GỬI TIỀN -----";
		noiDung +="\nTên nhân viên thực hiện: " + TrangChu.getTenNhanVien();
		noiDung +="\nTên khách hàng: " + kh.getTenKhachHang();
		noiDung +="\nSố tiền gửi: " + ftxtSoTien.getText().toString();
		noiDung +="\nNgày giao dịch: " + Database.getCurrentDateTime();
		
		Database.inBienLai(Database.getChuyenTienBienLaiDir(), tenBienLai, noiDung, frmChuyenTien);
}

	public JFrame getFrame() {
		return frmChuyenTien;
	}
}
