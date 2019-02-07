package shop.model;

import java.util.HashMap;
import java.util.List;

public class ProductVO {
	private int product_idx;
	private String fk_cg_1st_code;
	private String fk_cg_2nd_code;
	private String pcode;
	private String pname;
	private String mainimg1;
	private String mainimg2;
	private String pspec;
	private int price;
	private String pcompany;
	private int totalsqty;
	private String pcontents; 
	private String fullCategory;
	
	private CategoryVO category;
	private List<HashMap<String,String>> stockList;
	
	public ProductVO() {}
	public ProductVO(int product_idx, String fk_cg_1st_code, String fk_cg_2nd_code, String pcode, String pname,
			String mainimg1, String mainimg2, String psec, int price, String pcompany, int totalsqty,String pcontents) {
		super();
		this.product_idx = product_idx;
		this.fk_cg_1st_code = fk_cg_1st_code;
		this.fk_cg_2nd_code = fk_cg_2nd_code;
		this.pcode = pcode;
		this.pname = pname;
		this.mainimg1 = mainimg1;
		this.mainimg2 = mainimg2;
		this.pspec = psec;
		this.price = price;
		this.pcompany = pcompany;
		this.totalsqty = totalsqty;
		this.pcontents = pcontents;
	}
	public int getProduct_idx() {
		return product_idx;
	}
	public void setProduct_idx(int product_idx) {
		this.product_idx = product_idx;
	}
	public String getFk_cg_1st_code() {
		return fk_cg_1st_code;
	}
	public void setFk_cg_1st_code(String fk_cg_1st_code) {
		this.fk_cg_1st_code = fk_cg_1st_code;
	}
	public String getFk_cg_2nd_code() {
		return fk_cg_2nd_code;
	}
	public void setFk_cg_2nd_code(String fk_cg_2nd_code) {
		this.fk_cg_2nd_code = fk_cg_2nd_code;
	}
	public String getPcode() {
		return pcode;
	}
	public void setPcode(String pcode) {
		this.pcode = pcode;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getMainimg1() {
		return mainimg1;
	}
	public void setMainimg1(String mainimg1) {
		this.mainimg1 = mainimg1;
	}
	public String getMainimg2() {
		return mainimg2;
	}
	public void setMainimg2(String mainimg2) {
		this.mainimg2 = mainimg2;
	}
	public String getPspec() {
		return pspec;
	}
	public void setPspec(String psec) {
		this.pspec = psec;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getPcompany() {
		return pcompany;
	}
	public void setPcompany(String pcompany) {
		this.pcompany = pcompany;
	}
	public int getTotalsqty() {
		return totalsqty;
	}
	public void setTotalsqty(int totalsqty) {
		this.totalsqty = totalsqty;
	}
	public String getPcontents() {
		return pcontents;
	}
	public void setPcontents(String pcontents) {
		this.pcontents = pcontents;
	}
	
	public CategoryVO getCategory() {
		return category;
	}
	public void setCategory(CategoryVO category) {
		this.category = category;
	}
	public List<HashMap<String, String>> getStockList() {
		return stockList;
	}
	public void setStockList(List<HashMap<String, String>> stockList) {
		this.stockList = stockList;
	}
	public String getFullCategory() {
		return fullCategory;
	}
	public void setFullCategory(String fullCategory) {
		this.fullCategory = fullCategory;
	}
	
}
