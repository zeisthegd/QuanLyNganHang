package model;

public class KhachHang {
	private int id;
	private String tenKhachHang;
	private String cmnd;
	private String diaChi;
	private String sdt;
	private int tien;
		
	public KhachHang(int id, String tenKhachHang, String cmnd, String diaChi, String sdt, int tien) {
		super();
		this.id = id;
		this.tenKhachHang = tenKhachHang;
		this.cmnd = cmnd;
		this.diaChi = diaChi;
		this.sdt = sdt;
		this.tien = tien;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTenKhachHang() {
		return tenKhachHang;
	}
	public void setTenKhachHang(String tenKhachHang) {
		this.tenKhachHang = tenKhachHang;
	}
	public String getCmnd() {
		return cmnd;
	}
	public void setCmnd(String cmnd) {
		this.cmnd = cmnd;
	}
	public String getDiaChi() {
		return diaChi;
	}
	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}
	public String getSdt() {
		return sdt;
	}
	public void setSdt(String sdt) {
		this.sdt = sdt;
	}
	public int getTien() {
		return tien;
	}
	public void setTien(int tien) {
		this.tien = tien;
	}
	
	
}
