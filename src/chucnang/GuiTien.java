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

public class GuiTien {

	private JFrame frmGuiTien;
	private JTextField txtTenKhachHang;
	private JTextField txtSoCMND;
	private JTextField txtSDT;
	private JFormattedTextField ftxtSoTien;

	private KhachHang kh;

	private int minAmount = 100000;
	private int maxAmount = 900000000;

	private JButton btnGui;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiTien window = new GuiTien();
					window.frmGuiTien.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GuiTien() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmGuiTien = new JFrame();
		frmGuiTien.setTitle("Gửi tiền");
		frmGuiTien.setBounds(100, 100, 310, 213);
		frmGuiTien.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmGuiTien.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("G\u1EEDi Ti\u1EC1n");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(98, 11, 100, 14);
		frmGuiTien.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Khách Hàng:");
		lblNewLabel_1.setBounds(10, 42, 100, 14);
		frmGuiTien.getContentPane().add(lblNewLabel_1);

		txtTenKhachHang = new JTextField();
		txtTenKhachHang.setEditable(false);
		txtTenKhachHang.setBounds(136, 39, 150, 20);
		frmGuiTien.getContentPane().add(txtTenKhachHang);
		txtTenKhachHang.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Số CMND:");
		lblNewLabel_2.setBounds(10, 67, 100, 14);
		frmGuiTien.getContentPane().add(lblNewLabel_2);

		txtSoCMND = new JTextField();
		txtSoCMND.setEditable(false);
		txtSoCMND.setColumns(10);
		txtSoCMND.setBounds(136, 64, 150, 20);
		frmGuiTien.getContentPane().add(txtSoCMND);

		txtSDT = new JTextField();
		txtSDT.setEditable(false);
		txtSDT.setColumns(10);
		txtSDT.setBounds(136, 89, 150, 20);
		frmGuiTien.getContentPane().add(txtSDT);

		JLabel lblNewLabel_2_1 = new JLabel("Số điện thoại:");
		lblNewLabel_2_1.setBounds(10, 92, 100, 14);
		frmGuiTien.getContentPane().add(lblNewLabel_2_1);

		JLabel lblNewLabel_2_1_1 = new JLabel("S\u1ED1 ti\u1EC1n c\u1EA7n g\u1EEDi:");
		lblNewLabel_2_1_1.setBounds(10, 116, 100, 14);
		frmGuiTien.getContentPane().add(lblNewLabel_2_1_1);

		ftxtSoTien = new JFormattedTextField(NumberFormat.getNumberInstance());
		ftxtSoTien.setToolTipText("VN\u0110");
		ftxtSoTien.setBounds(136, 113, 150, 20);
		frmGuiTien.getContentPane().add(ftxtSoTien);

		btnGui = new JButton("Gửi Tiền");
		btnGui.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thucHienGuiTien();
			}
		});
		btnGui.setBounds(109, 142, 89, 23);
		frmGuiTien.getContentPane().add(btnGui);
	}

	public void setThongTinKhachHang(KhachHang kh) {
		this.kh = kh;
		txtTenKhachHang.setText(kh.getTenKhachHang());
		txtSoCMND.setText(kh.getCmnd());
		txtSDT.setText(kh.getSdt());

	}

	private void thucHienGuiTien() {
		if (!ftxtSoTien.getText().isEmpty()) {
			String tienGui = ftxtSoTien.getText().toString().replace(",", "");

			BigDecimal inputAmount = new BigDecimal(tienGui);
			if (inputAmount.intValue() <= maxAmount) {
				if (inputAmount.intValue() >= minAmount) {
					guiTien(inputAmount);
					inBienLai();

				} else {
					JOptionPane.showMessageDialog(frmGuiTien, "Số tiền gửi phải lớn hơn " + minAmount);
				}
			} else {
				JOptionPane.showMessageDialog(frmGuiTien, "Số tiền gửi phải nhỏ hơn " + maxAmount);
			}
		}
	}

	
	
	private void guiTien(BigDecimal addAmount) {
		BigDecimal newTien = kh.getTien().add(addAmount);
		kh.setTien(newTien);
		if(Database.updateTien(kh))
			JOptionPane.showMessageDialog(frmGuiTien, "Đã thực hiện gửi tiền!");
		else 
			JOptionPane.showMessageDialog(frmGuiTien, "Lỗi trong quá trình thực hiện gửi tiền!");
	}

	private void inBienLai() {
		String tenBienLai = kh.getCmnd() + "_GUI-TIEN_" + Database.getCurrentDateTime() + ".txt";
		String noiDung = "";

		noiDung += "----- GỬI TIỀN -----";
		noiDung +="\nTên nhân viên thực hiện: " + TrangChu.getTenNhanVien();
		noiDung +="\nTên khách hàng: " + kh.getTenKhachHang();
		noiDung += "\nSố CMND: "+ kh.getCmnd();
		noiDung +="\nSố tiền gửi: " + ftxtSoTien.getText().toString();
		noiDung +="\nNgày giao dịch: " + Database.getCurrentDateTime();
		
		Database.inBienLai(Database.getGuiTienBienLaiDir(), tenBienLai, noiDung, frmGuiTien);
}

	public JFrame getFrame() {
		return frmGuiTien;
	}

}
