package quanlynganhang;

import java.awt.EventQueue;
import java.awt.Window;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JDesktopPane;
import javax.swing.JSplitPane;
import javax.swing.JButton;
import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TrangChu {

	private JFrame frame;
	private JTable table;
	
	private JLabel lblTenNhanVien;
	private JTextField txtFindTenKhachHang;
	private JTextField txtFindCMND;
	private JTextField txtFindSDT;
	
	private DefaultTableModel dataModel = new DefaultTableModel();

	private int findBy = 1;
	private String findKhachHangQuery = "select * fomr [KhachHang]";
	
	private String[] columnNames = {"ID","Tên Khách Hàng","Số CMND","Địa chỉ","Số điện thoại","Tiền"};
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrangChu window = new TrangChu();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TrangChu() {
		Database.connectDatabase();
		initialize();
		loadTatCaKhachHang();
		setFindKhachHangKeyPress();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 500, 401);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblTitle = new JLabel("Trang Ch\u1EE7");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTitle.setBounds(127, 0, 250, 30);
		frame.getContentPane().add(lblTitle);
		
		table = new JTable();
		table.setBounds(10, 94, 466, 191);
		frame.getContentPane().add(table);
		
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(10, 296, 139, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("New button");
		btnNewButton_1.setBounds(177, 296, 139, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("New button");
		btnNewButton_2.setBounds(337, 296, 139, 23);
		frame.getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("New button");
		btnNewButton_3.setBounds(10, 330, 139, 23);
		frame.getContentPane().add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("New button");
		btnNewButton_4.setBounds(177, 330, 139, 23);
		frame.getContentPane().add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("New button");
		btnNewButton_5.setBounds(337, 330, 139, 23);
		frame.getContentPane().add(btnNewButton_5);
		
		JLabel lblNewLabel = new JLabel("T\u00EAn Nh\u00E2n Vi\u00EAn: ");
		lblNewLabel.setBounds(10, 34, 102, 14);
		frame.getContentPane().add(lblNewLabel);
		
		lblTenNhanVien = new JLabel("");
		lblTenNhanVien.setBounds(110, 34, 122, 14);
		frame.getContentPane().add(lblTenNhanVien);
		
		txtFindTenKhachHang = new JTextField();
		
		txtFindTenKhachHang.setToolTipText("T\u00ECm theo T\u00CAN KH\u00C1CH H\u00C0NG");
		txtFindTenKhachHang.setBounds(10, 63, 150, 20);
		frame.getContentPane().add(txtFindTenKhachHang);
		txtFindTenKhachHang.setColumns(10);
		
		txtFindCMND = new JTextField();
		txtFindCMND.setToolTipText("T\u00ECm theo S\u1ED1 CMND");
		txtFindCMND.setColumns(10);
		txtFindCMND.setBounds(166, 63, 155, 20);
		frame.getContentPane().add(txtFindCMND);
		
		txtFindSDT = new JTextField();
		txtFindSDT.setToolTipText("T\u00ECm theo S\u1ED0 \u0110I\u1EC6N THO\u1EA0I");
		txtFindSDT.setColumns(10);
		txtFindSDT.setBounds(326, 63, 150, 20);
		frame.getContentPane().add(txtFindSDT);
	}

	
	private void loadTatCaKhachHang()
	{
		findKhachHangQuery = "select * from [KhachHang]";
		try {
			loadDanhSachKhachHang();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void loadDanhSachKhachHang() throws SQLException
	{
		setDataTableModelClickListener();
		table.setModel(dataModel);
		
		try(PreparedStatement pstmt = Database.getConnection().prepareStatement(findKhachHangQuery))
		{
			ResultSet result = null;
			result = pstmt.executeQuery();
			//-------------------------------------------------
			ResultSetMetaData mData = result.getMetaData();
			int colCount = mData.getColumnCount();
			String[] colNames = new String[colCount];
			//-------------------------------------------------
			for(int i = 1; i <= colCount; i++)
			{
				colNames[i - 1] = mData.getColumnName(i);
			}
			dataModel.setColumnIdentifiers(colNames);
			//-------------------------------------------------
			dataModel.addRow(columnNames);
			while(result.next())
			{
				String[] rowData = new String[colCount];
				for(int i = 1; i <= colCount; i++)
				{
					rowData[i-1] = result.getString(i);
				}
				dataModel.addRow(rowData);
			}
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}	
	}
	
	private void findKhachHangVoiDieuKien() throws SQLException
	{	
		setDataTableModelClickListener();
		dataModel.addRow(columnNames);
		table.setModel(dataModel);
		
		try(PreparedStatement pstmt = Database.getConnection().prepareStatement(findKhachHangQuery))
		{
			ResultSet result = null;
			result = pstmt.executeQuery();
			//-------------------------------------------------
			ResultSetMetaData mData = result.getMetaData();
			int colCount = mData.getColumnCount();
			String[] colNames = new String[colCount];
			//-------------------------------------------------
			for(int i = 1; i <= colCount; i++)
			{
				colNames[i - 1] = mData.getColumnName(i);
			}
			dataModel.setColumnIdentifiers(colNames);
			//-------------------------------------------------
			
			while(result.next())
			{
				String[] rowData = new String[colCount];
				for(int i = 1; i <= colCount; i++)
				{
					rowData[i-1] = result.getString(i);
				}
				System.out.println(result.getString(2));
				if((checkTenKhachHangContains(result.getString(2)) && findBy == 2)||(checkSoCMNDContains(result.getString(3))&& findBy == 3) ||(checkSoDTContains(result.getString(4))&& findBy == 4))
					dataModel.addRow(rowData);
					
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}	
	}
	private boolean checkTenKhachHangContains(String mainString)
	{
		String tenKhachHang = txtFindTenKhachHang.getText();
		
		
		if(!tenKhachHang.isEmpty())
		{
			if(mainString.contains(tenKhachHang))
				return true;
		}
		return false;
	}
	private boolean checkSoCMNDContains(String mainString)
	{
		String soCMND = txtFindCMND.getText();
		if(!soCMND.isEmpty())
		{
			if(mainString.contains(soCMND))
				return true;
		}
		return false;
	}
	private boolean checkSoDTContains(String mainString)
	{
		String sdt = txtFindSDT.getText();
		if(!sdt.isEmpty())
		{
			if(mainString.contains(sdt))
				return true;
		}
		return false;
	}
	
	private void setFindKhachHangKeyPress()
	{
		txtFindTenKhachHang.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) 
			{
				try
				{
					findBy = 2;
					if(!(txtFindTenKhachHang.getText().length() <= 0))
					{
						findKhachHangVoiDieuKien();
					}
					else {
						loadTatCaKhachHang();
					}	
				}
				catch(Exception ex)
				{
					System.out.println(ex.getMessage());
				}					
			}
		});
		
		txtFindCMND.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e)
			{			
				try
				{
					findBy = 3;
					if(!(txtFindCMND.getText().length() <= 0))
					{
						findKhachHangVoiDieuKien();
					}
					else {
						loadTatCaKhachHang();
					}	
				}
				catch(Exception ex)
				{
					System.out.println(ex.getMessage());
				}
			}						
		});
		
		txtFindSDT.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e)
			{
				try
				{
					findBy = 4;
					if(!(txtFindSDT.getText().length() <= 0))
					{
						findKhachHangVoiDieuKien();
					}
					else {
						loadTatCaKhachHang();
					}	
				}
				catch(Exception ex)
				{
					System.out.println(ex.getMessage());
				}			
			}
		});
	}
	
	
	private void setDataTableModelClickListener()
	{
		dataModel = new DefaultTableModel();
		dataModel.addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				System.out.println("Changed");
			}
		});
	}
	public JFrame getFrame() {
		return frame;
	}
	public void setTenNhanVien(String ten)
	{
		lblTenNhanVien.setText(ten);
	}
}
