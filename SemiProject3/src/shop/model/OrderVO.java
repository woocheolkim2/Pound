package shop.model;

import java.util.List;

public class OrderVO {
	private String odrcode;
	private String fk_userid;
	private int sumtotalprice;
	private String orderdate;
	private int dvrfee;
	
	private List<OrderDetailVO> orderDetailList;
	
	public OrderVO() {}
	public OrderVO(String odrcode, String fk_userid, int sumtotalprice, String orderdate, int dvrfee) {
		super();
		this.odrcode = odrcode;
		this.fk_userid = fk_userid;
		this.sumtotalprice = sumtotalprice;
		this.orderdate = orderdate;
		this.dvrfee = dvrfee;
	}
	public String getOdrcode() {
		return odrcode;
	}
	public void setOdrcode(String odrcode) {
		this.odrcode = odrcode;
	}
	public String getFk_userid() {
		return fk_userid;
	}
	public void setFk_userid(String fk_userid) {
		this.fk_userid = fk_userid;
	}
	public int getSumtotalprice() {
		return sumtotalprice;
	}
	public void setSumtotalprice(int sumtotalprice) {
		this.sumtotalprice = sumtotalprice;
	}
	public String getOrderdate() {
		return orderdate;
	}
	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}
	public int getDvrfee() {
		return dvrfee;
	}
	public void setDvrfee(int dvrfee) {
		this.dvrfee = dvrfee;
	}
	public List<OrderDetailVO> getOrderDetailList() {
		return orderDetailList;
	}
	public void setOrderDetailList(List<OrderDetailVO> orderDetailList) {
		this.orderDetailList = orderDetailList;
	}
	
}
