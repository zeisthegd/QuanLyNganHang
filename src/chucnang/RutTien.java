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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;

public class RutTien {

	private JFrame frmRutTien;
	private JTextField txtTenKhachHang;
	private JTextField txtSoCMND;
	private JTextField txtSDT;
	private JFormattedTextField ftxtSoTien;

	private KhachHang kh;

	private int minAmount = 100000;
	private int remainingAmount = 50000;

	private JButton btnRut;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RutTien window = new RutTien();
					window.frmRutTien.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RutTien() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmRutTien = new JFrame();
		frmRutTien.setTitle("Rút tiền");
		frmRutTien.setBounds(100, 100, 310, 213);
		frmRutTien.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmRutTien.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Rút Tiền");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(98, 11, 100, 14);
		frmRutTien.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Khách Hàng:");
		lblNewLabel_1.setBounds(10, 42, 100, 14);
		frmRutTien.getContentPane().add(lblNewLabel_1);

		txtTenKhachHang = new JTextField();
		txtTenKhachHang.setEditable(false);
		txtTenKhachHang.setBounds(136, 39, 150, 20);
		frmRutTien.getContentPane().add(txtTenKhachHang);
		txtTenKhachHang.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Số CMND:");
		lblNewLabel_2.setBounds(10, 67, 100, 14);
		frmRutTien.getContentPane().add(lblNewLabel_2);

		txtSoCMND = new JTextField();
		txtSoCMND.setEditable(false);
		txtSoCMND.setColumns(10);
		txtSoCMND.setBounds(136, 64, 150, 20);
		frmRutTien.getContentPane().add(txtSoCMND);

		txtSDT = new JTextField();
		txtSDT.setEditable(false);
		txtSDT.setColumns(10);
		txtSDT.setBounds(136, 89, 150, 20);
		frmRutTien.getContentPane().add(txtSDT);

		JLabel lblNewLabel_2_1 = new JLabel("Số điện thoại:");
		lblNewLabel_2_1.setBounds(10, 92, 100, 14);
		frmRutTien.getContentPane().add(lblNewLabel_2_1);

		JLabel lblNewLabel_2_1_1 = new JLabel("Số tiền cần rút");
		lblNewLabel_2_1_1.setBounds(10, 116, 100, 14);
		frmRutTien.getContentPane().add(lblNewLabel_2_1_1);

		ftxtSoTien = new JFormattedTextField(NumberFormat.getNumberInstance());
		ftxtSoTien.setToolTipText("VN\u0110");
		ftxtSoTien.setBounds(136, 113, 150, 20);
		frmRutTien.getContentPane().add(ftxtSoTien);

		btnRut = new JButton("Rút Tiền");
		btnRut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thucHienRutTien();
			}
		});
		btnRut.setBounds(109, 142, 89, 23);
		frmRutTien.getContentPane().add(btnRut);
	}

	public void setThongTinKhachHang(KhachHang kh) {
		this.kh = kh;
		txtTenKhachHang.setText(kh.getTenKhachHang());
		txtSoCMND.setText(kh.getCmnd());
		txtSDT.setText(kh.getSdt());

	}

	private void thucHienRutTien() {
		if (!ftxtSoTien.getText().isEmpty()) {
			String tienRut = ftxtSoTien.getText().toString().replace(",", "");

			BigDecimal outputAmount = new BigDecimal(tienRut);
			if (outputAmount.intValue() >= minAmount) {
				if (kh.getTien().subtract(outputAmount).intValue() >= remainingAmount) {
					rutTien(outputAmount);
					inBienLai();
				} else {
					JOptionPane.showMessageDialog(frmRutTien, "Số tiền còn lại phải lớn hơn " + remainingAmount);
				}
			} else {
				JOptionPane.showMessageDialog(frmRutTien, "Số tiền rút phải lớn hơn " + minAmount);
			}
		}
	}

	private void rutTien(BigDecimal subAmount) {
		BigDecimal newTien = kh.getTien().subtract(subAmount);
		kh.setTien(newTien);
		
		if(Database.updateTien(kh))
			JOptionPane.showMessageDialog(frmRutTien, "Đã thực hiện rút tiền!");
		else 
			JOptionPane.showMessageDialog(frmRutTien, "Lỗi trong quá trình thực hiện rút tiền!");
	}

	private void inBienLai() {
			String tenBienLai = kh.getCmnd() + "_RUT-TIEN_" + Database.getCurrentDateTime() + ".txt";
			String noiDung = "";

			noiDung += "----- RÚT TIỀN -----";
			noiDung +="\nTên nhân viên thực hiện: " + TrangChu.getTenNhanVien();
			noiDung +="\nTên khách hàng: " + kh.getTenKhachHang();
			noiDung += "\nSố CMND: "+ kh.getCmnd();
			noiDung +="\nSố tiền rút: " + ftxtSoTien.getText().toString();
			noiDung +="\nNgày giao dịch: " + Database.getCurrentDateTime();
			
			Database.inBienLai(Database.getRutTienBienLaiDir(), tenBienLai, noiDung, frmRutTien);
	}

	public JFrame getFrame() {
		return frmRutTien;
	}

}
