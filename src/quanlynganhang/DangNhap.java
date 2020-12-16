package quanlynganhang;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JPasswordField;

public class DangNhap {

	private JFrame frmLogin;
	private JTextField txtUsername;
	private JButton btnLogin;
	
	
	Connection conn = null;
	private JPasswordField txtPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					DangNhap window = new DangNhap();
					window.frmLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DangNhap() {
		Database.connectDatabase();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.setTitle("\u0110\u0103ng Nh\u1EADp Nh\u00E2n Vi\u00EAn");
		frmLogin.setBounds(100, 100, 312, 192);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLogin.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Username:");
		lblNewLabel.setBounds(10, 54, 80, 14);
		frmLogin.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Password:");
		lblNewLabel_1.setBounds(10, 85, 80, 14);
		frmLogin.getContentPane().add(lblNewLabel_1);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(100, 51, 173, 20);
		frmLogin.getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		
		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		btnLogin.setBounds(100, 113, 89, 23);
		frmLogin.getContentPane().add(btnLogin);
		
		JLabel lblTitle = new JLabel("Qu\u1EA3n L\u00ED Ng\u00E2n H\u00E0ng");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTitle.setBounds(68, 11, 183, 32);
		frmLogin.getContentPane().add(lblTitle);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(100, 82, 173, 20);
		frmLogin.getContentPane().add(txtPassword);
	}
	
	private void login()
	{
		if(enteredUsernameAndPassword())
		{
			checkUser();
		}
		else
		{
			JOptionPane.showMessageDialog(frmLogin, "Please input your login info!");
		}
	}

	private boolean enteredUsernameAndPassword() {
		if(txtUsername.getText().equals("") && txtPassword.getText().equals(""))
			return false;
		return true;
	}

	private void checkUser() {
		
		String username = txtUsername.getText();
		String password = txtPassword.getText();
		
		String query = "select * from [NhanVien] where TenDangNhap = ? and MatKhau = ?";
		try(PreparedStatement pstmt = Database.getConnection().prepareStatement(query))
		{
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			ResultSet result = pstmt.executeQuery();
						
			if(!result.next())
			{
				JOptionPane.showMessageDialog(frmLogin, "Wrong password");
				return;
			}
			changeToMain(result.getString("TenNhanVien"));
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(frmLogin, "Lỗi đăng nhập!");
		}
		
	}

	private void changeToMain(String tenNhanVien) {	
		TrangChu frameTrangChu = new TrangChu();
		frameTrangChu.setTenNhanVien(tenNhanVien);
		frmLogin.dispose();
		frameTrangChu.getFrame().setVisible(true);	
	}
}
