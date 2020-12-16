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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import model.KhachHang;
import chucnang.*;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class TrangChu {

	private JFrame frmTrangChu;
	private JTable table;

	private JLabel lblTenNhanVien;
	private JTextField txtFindTenKhachHang;
	private JTextField txtFindCMND;
	private JTextField txtFindSDT;

	private JButton btnGuiTien;
	private JButton btnRutTien;
	private JButton btnChuyenTien;
	private JButton btnThemKhachHang;
	private JButton btnRefresh;
	private JButton btnThoat;
	
	private static String tenNhanVien = "";

	private DefaultTableModel dataModel = new DefaultTableModel();

	private int selectedKhachHangRow = -1;
	private KhachHang selectedKhachHang;

	private int findBy = 1;
	private String findKhachHangQuery = "select * fomr [KhachHang]";

	private String[] columnNames = { "ID", "Tên Khách Hàng", "Số CMND", "Địa chỉ", "Số điện thoại", "Tiền" };
	
	int[] columnsWidth = {
            30, 125, 80, 180, 80, 120
        };
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrangChu window = new TrangChu();
					window.frmTrangChu.setVisible(true);
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

		setTatCaButtons();
		loadTatCaKhachHang();
		setFindKhachHangKeyPress();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTrangChu = new JFrame();
		frmTrangChu.setTitle("Trang Chủ");
		frmTrangChu.setBounds(100, 100, 670, 401);
		frmTrangChu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTrangChu.getContentPane().setLayout(null);

		JLabel lblTitle = new JLabel("Trang Ch\u1EE7");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTitle.setBounds(201, 11, 250, 30);
		frmTrangChu.getContentPane().add(lblTitle);
		

		btnGuiTien = new JButton("Gửi Tiền");
		btnGuiTien.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnGuiTien.setBounds(10, 296, 205, 23);
		frmTrangChu.getContentPane().add(btnGuiTien);

		btnRutTien = new JButton("Rút Tiền");
		btnRutTien.setBounds(226, 296, 205, 23);
		frmTrangChu.getContentPane().add(btnRutTien);

		btnChuyenTien = new JButton("Chuyển Tiền");
		btnChuyenTien.setBounds(441, 296, 205, 23);
		frmTrangChu.getContentPane().add(btnChuyenTien);

		btnThemKhachHang = new JButton("Thêm Khách Hàng");
		btnThemKhachHang.setBounds(10, 330, 205, 23);
		frmTrangChu.getContentPane().add(btnThemKhachHang);

		btnRefresh = new JButton("Refresh");

		btnRefresh.setBounds(226, 330, 205, 23);
		frmTrangChu.getContentPane().add(btnRefresh);

		btnThoat = new JButton("Thoát");
		btnThoat.setBounds(441, 330, 205, 23);
		frmTrangChu.getContentPane().add(btnThoat);

		JLabel lblNewLabel = new JLabel("T\u00EAn Nh\u00E2n Vi\u00EAn: ");
		lblNewLabel.setBounds(10, 34, 102, 14);
		frmTrangChu.getContentPane().add(lblNewLabel);

		lblTenNhanVien = new JLabel("");
		lblTenNhanVien.setBounds(110, 34, 122, 14);
		frmTrangChu.getContentPane().add(lblTenNhanVien);

		txtFindTenKhachHang = new JTextField();

		txtFindTenKhachHang.setToolTipText("T\u00ECm theo T\u00CAN KH\u00C1CH H\u00C0NG");
		txtFindTenKhachHang.setBounds(10, 63, 205, 20);
		frmTrangChu.getContentPane().add(txtFindTenKhachHang);
		txtFindTenKhachHang.setColumns(10);

		txtFindCMND = new JTextField();
		txtFindCMND.setToolTipText("T\u00ECm theo S\u1ED1 CMND");
		txtFindCMND.setColumns(10);
		txtFindCMND.setBounds(226, 63, 205, 20);
		frmTrangChu.getContentPane().add(txtFindCMND);

		txtFindSDT = new JTextField();
		txtFindSDT.setToolTipText("T\u00ECm theo S\u1ED0 \u0110I\u1EC6N THO\u1EA0I");
		txtFindSDT.setColumns(10);
		txtFindSDT.setBounds(441, 63, 205, 20);
		frmTrangChu.getContentPane().add(txtFindSDT);
		
		scrollPane = new JScrollPane(table);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setEnabled(false);
		scrollPane.setBounds(10, 94, 636, 191);
		frmTrangChu.getContentPane().add(scrollPane);
		
				table = new JTable();
				scrollPane.setViewportView(table);
				table.setBorder(new LineBorder(new Color(0, 0, 0), 2));
	}

	private void loadTatCaKhachHang() {
		findKhachHangQuery = "select * from [KhachHang]";
		try {
			loadDanhSachKhachHang();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void loadDanhSachKhachHang() throws SQLException {
		setDataTableModelClickListener();
		table.setModel(dataModel);

		try (PreparedStatement pstmt = Database.getConnection().prepareStatement(findKhachHangQuery)) {
			ResultSet result = null;
			result = pstmt.executeQuery();
			// -------------------------------------------------
			ResultSetMetaData mData = result.getMetaData();
			int colCount = mData.getColumnCount();
			dataModel.setColumnIdentifiers(getColNames(colCount));		
			setTableColumnWidth();
			// -------------------------------------------------
			while (result.next()) {
				String[] rowData = new String[colCount];
				for (int i = 1; i <= colCount; i++) {
					rowData[i - 1] = result.getString(i);
				}
				dataModel.addRow(rowData);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	private void findKhachHangVoiDieuKien() throws SQLException {
		setDataTableModelClickListener();

		table.setModel(dataModel);

		try (PreparedStatement pstmt = Database.getConnection().prepareStatement(findKhachHangQuery)) {
			ResultSet result = null;
			result = pstmt.executeQuery();
			
			// -------------------------------------------------
			ResultSetMetaData mData = result.getMetaData();
			int colCount = mData.getColumnCount();
			dataModel.setColumnIdentifiers(getColNames(colCount));
			setTableColumnWidth();
			// -------------------------------------------------
			
			
			while (result.next()) {
				String[] rowData = new String[colCount];
				for (int i = 1; i <= colCount; i++) {
					rowData[i - 1] = result.getString(i);
				}
				if ((checkTenKhachHangContains(result.getString(2)) && findBy == 2)
						|| (checkSoCMNDContains(result.getString(3)) && findBy == 3)
						|| (checkSoDTContains(result.getString(5)) && findBy == 5))
					dataModel.addRow(rowData);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private String[] getColNames(int colCount)
	{
		String[] colNames = new String[colCount];
		for (int i = 1; i <= colCount; i++) {
			colNames[i - 1] = columnNames[i - 1];
		}
		return colNames;
	}

	private void setTatCaButtons() {
		setBtnGuiTien();
		setBtnRutTien();
		setBtnChuyenTien();
		setBtnThemKhachHang();
		setBtnThoat();
		setBtnRefresh();
	}
	
	

	private void setBtnGuiTien() {
		btnGuiTien.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(selectedKhachHang != null)
				{
					GuiTien windowGuiTien = new GuiTien();
					windowGuiTien.setThongTinKhachHang(selectedKhachHang);
					windowGuiTien.getFrame().setVisible(true);
				}
				else
				{
					JOptionPane.showMessageDialog(frmTrangChu, "Bạn chưa chọn khách hàng cần thực hiện thao tác!");
				}
			
			}
		});
	}

	private void setBtnRutTien() {
		btnRutTien.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(selectedKhachHang != null)
				{
					RutTien windowRutTien = new RutTien();
					windowRutTien.setThongTinKhachHang(selectedKhachHang);
					windowRutTien.getFrame().setVisible(true);
				}
				else
				{
					JOptionPane.showMessageDialog(frmTrangChu, "Bạn chưa chọn khách hàng cần thực hiện thao tác!");
				}

			}
		});
	}

	private void setBtnChuyenTien() {
		btnChuyenTien.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(selectedKhachHang != null)
				{
					ChuyenTien windowChuyenTien = new ChuyenTien();
					windowChuyenTien.setThongTinNguoiChuyen(selectedKhachHang);
					windowChuyenTien.getFrame().setVisible(true);
				}
				else
				{
					JOptionPane.showMessageDialog(frmTrangChu, "Bạn chưa chọn khách hàng cần thực hiện thao tác!");
				}

			}
		});
	}

	private void setBtnThemKhachHang() {
		btnThemKhachHang.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ThemKhachHang windowThemKhachHang = new ThemKhachHang();
				windowThemKhachHang.getFrame().setVisible(true);

			}
		});
	}

	private void setBtnThoat() {
		btnThoat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frmTrangChu.dispose();

			}
		});
	}

	private void setBtnRefresh() {
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtFindTenKhachHang.setText("");
				txtFindSDT.setText("");
				txtFindCMND.setText("");
				loadTatCaKhachHang();
			}
		});
	}

	private void setDataTableModelClickListener() {
		dataModel = new DefaultTableModel();
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				try
				{
					selectedKhachHangRow = table.getSelectedRow();

					int id = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
					String ten = table.getValueAt(table.getSelectedRow(), 1).toString();
					String cmnd = table.getValueAt(table.getSelectedRow(), 2).toString();
					String diaChi = table.getValueAt(table.getSelectedRow(), 3).toString();
					String sdt = table.getValueAt(table.getSelectedRow(), 4).toString();
					BigDecimal tien = new BigDecimal(table.getValueAt(table.getSelectedRow(), 5).toString());

					selectedKhachHang = new KhachHang(id, ten, cmnd, diaChi, sdt, tien);
					System.out.println(selectedKhachHang.toString());
				}
				catch(Exception ex)
				{
					System.out.println("Không thể chọn title!");
				}		
			}
		});
	}

	private void setFindKhachHangKeyPress() {
		txtFindTenKhachHang.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				try {
					findBy = 2;
					if (!(txtFindTenKhachHang.getText().length() <= 0)) {
						findKhachHangVoiDieuKien();
					} else {
						loadTatCaKhachHang();
					}
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
			}
		});

		txtFindCMND.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				try {
					findBy = 3;
					if (!(txtFindCMND.getText().length() <= 0)) {
						findKhachHangVoiDieuKien();
					} else {
						loadTatCaKhachHang();
					}
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
			}
		});

		txtFindSDT.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				try {
					findBy = 5;
					if (!(txtFindSDT.getText().length() <= 0)) {
						findKhachHangVoiDieuKien();
					} else {
						loadTatCaKhachHang();
					}
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
			}
		});
	}

	private boolean checkTenKhachHangContains(String mainString) {
		String tenKhachHang = txtFindTenKhachHang.getText();

		if (!tenKhachHang.isEmpty()) {
			if (mainString.contains(tenKhachHang))
				return true;
		}
		return false;
	}

	private boolean checkSoCMNDContains(String mainString) {
		String soCMND = txtFindCMND.getText();
		if (!soCMND.isEmpty()) {
			if (mainString.contains(soCMND))
				return true;
		}
		return false;
	}

	private boolean checkSoDTContains(String mainString) {
		String sdt = txtFindSDT.getText();
		if (!sdt.isEmpty()) {
			if (mainString.contains(sdt))
				return true;
		}
		return false;
	}

	private void setTableColumnWidth()
	{
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		int i = 0;
		 for (int width : columnsWidth) {
	            TableColumn column = table.getColumnModel().getColumn(i++);
	            column.setMinWidth(width);
	            column.setMaxWidth(width);
	            column.setPreferredWidth(width);
	            column.setCellRenderer(centerRenderer);
	            
	        }
		 
	}
	
	public JFrame getFrame() {
		return frmTrangChu;
	}

	public void setTenNhanVien(String ten) {
		tenNhanVien = ten;
		lblTenNhanVien.setText(tenNhanVien);
	}
	
	public static String getTenNhanVien()
	{
		return tenNhanVien;
	}
}
