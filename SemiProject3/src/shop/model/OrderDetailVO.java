package shop.model;

import java.sql.SQLException;

public class OrderDetailVO {
	private int Order_detail_idx;
	private String fk_odrcode;
	private String fk_pcode;
	private int fk_stock_idx;
	private int oqty;
	private int totalPrice;
	private String dvrdate;
	private int dvrstatus;
	
	private String osize;
	private String ocolor;
	
	private ProductVO pvo;
	
	public OrderDetailVO() {}
	public OrderDetailVO(int order_detail_idx, String fk_odrcode, String fk_pcode, int fk_stock_idx, int oqty,
			int totalPrice, String dvrdate, int dvrstatus, ProductVO pvo) {
		super();
		Order_detail_idx = order_detail_idx;
		this.fk_odrcode = fk_odrcode;
		this.fk_pcode = fk_pcode;
		this.fk_stock_idx = fk_stock_idx;
		this.oqty = oqty;
		this.totalPrice = totalPrice;
		this.dvrdate = dvrdate;
		this.dvrstatus = dvrstatus;
		this.pvo = pvo;
	}

	public int getOrder_detail_idx() {
		return Order_detail_idx;
	}

	public void setOrder_detail_idx(int order_detail_idx) {
		Order_detail_idx = order_detail_idx;
	}

	public String getFk_odrcode() {
		return fk_odrcode;
	}

	public void setFk_odrcode(String fk_odrcode) {
		this.fk_odrcode = fk_odrcode;
	}

	public String getFk_pcode() {
		return fk_pcode;
	}

	public void setFk_pcode(String fk_pcode) {
		this.fk_pcode = fk_pcode;
	}

	public int getFk_stock_idx() {
		return fk_stock_idx;
	}

	public void setFk_stock_idx(int fk_stock_idx) {
		this.fk_stock_idx = fk_stock_idx;
	}

	public int getOqty() {
		return oqty;
	}

	public void setOqty(int oqty) {
		this.oqty = oqty;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getDvrdate() {
		return dvrdate;
	}

	public void setDvrdate(String dvrdate) {
		this.dvrdate = dvrdate;
	}

	public int getDvrstatus() {
		return dvrstatus;
	}

	public void setDvrstatus(int dvrstatus) {
		this.dvrstatus = dvrstatus;
	}

	public ProductVO getPvo() {
		return pvo;
	}

	public String getOsize() {
		return osize;
	}
	public void setOsize(String osize) {
		this.osize = osize;
	}
	public String getOcolor() {
		return ocolor;
	}
	public void setOcolor(String ocolor) {
		this.ocolor = ocolor;
	}
	public void setPvo(ProductVO pvo) {
		this.pvo = pvo;
	}
	public void setPvoByPcode(String fk_pcode) throws SQLException {
		InterProductDAO pdao = new ProductDAO();
		this.pvo = pdao.getProductByPcode(fk_pcode);
	}
}
