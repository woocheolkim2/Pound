package shop.model;

import java.sql.SQLException;

public class CartVO {
	private int cart_idx;
	private String fk_userid;
	private String fk_pcode;
	private int oqty;
	private int fk_stock_idx;
	
	private String ocolor;
	private String osize;
	private String pqty;
	
	private ProductVO product;
	
	public CartVO() {}

	public CartVO(int cart_idx, String fk_userid, String fk_pcode, int oqty,int fk_stock_idx) {
		super();
		this.cart_idx = cart_idx;
		this.fk_userid = fk_userid;
		this.fk_pcode = fk_pcode;
		this.oqty = oqty;
		this.fk_stock_idx = fk_stock_idx;
	}

	public int getCart_idx() {
		return cart_idx;
	}

	public void setCart_idx(int cart_idx) {
		this.cart_idx = cart_idx;
	}

	public String getFk_userid() {
		return fk_userid;
	}

	public void setFk_userid(String fk_userid) {
		this.fk_userid = fk_userid;
	}

	public String getFk_pcode() {
		return fk_pcode;
	}

	public void setFk_pcode(String fk_pcode) {
		this.fk_pcode = fk_pcode;
	}

	public int getOqty() {
		return oqty;
	}

	public void setOqty(int oqty) {
		this.oqty = oqty;
	}

	public int getFk_stock_idx() {
		return fk_stock_idx;
	}

	public void setFk_stock_idx(int fk_stock_idx) {
		this.fk_stock_idx = fk_stock_idx;
	}

	public String getOcolor() {
		return ocolor;
	}

	public void setOcolor(String ocolor) {
		this.ocolor = ocolor;
	}

	public String getOsize() {
		return osize;
	}

	public void setOsize(String osize) {
		this.osize = osize;
	}

	public ProductVO getProduct() {
		return product;
	}

	public void setProduct(ProductVO product) {
		this.product = product;
	}
	public void setProductByPcode(String pcode) throws SQLException {
		InterProductDAO pdao = new ProductDAO();
		this.product = pdao.getProductByPcode(fk_pcode);
	}

	public String getPqty() {
		return pqty;
	}

	public void setPqty(String pqty) {
		this.pqty = pqty;
	}
	
}
