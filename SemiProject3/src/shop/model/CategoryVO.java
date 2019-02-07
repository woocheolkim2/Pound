package shop.model;

import java.util.HashMap;
import java.util.List;

public class CategoryVO {
	private int cg_1st_idx;
	private String cg_1st_code;
	private String cg_1st_name;
	
	private List<HashMap<String,String>> cg2ndList;
	
	CategoryVO(){}
	public CategoryVO(int cg_1st_idx, String cg_1st_code, String cg_1st_name) {
		super();
		this.cg_1st_idx = cg_1st_idx;
		this.cg_1st_code = cg_1st_code;
		this.cg_1st_name = cg_1st_name;
	}
	public int getCg_1st_idx() {
		return cg_1st_idx;
	}
	public void setCg_1st_idx(int cg_1st_idx) {
		this.cg_1st_idx = cg_1st_idx;
	}
	public String getCg_1st_code() {
		return cg_1st_code;
	}
	public void setCg_1st_code(String cg_1st_code) {
		this.cg_1st_code = cg_1st_code;
	}
	public String getCg_1st_name() {
		return cg_1st_name;
	}
	public void setCg_1st_name(String cg_1st_name) {
		this.cg_1st_name = cg_1st_name;
	}
	public List<HashMap<String, String>> getCg2ndList() {
		return cg2ndList;
	}
	public void setCg2ndList(List<HashMap<String, String>> cg2ndList) {
		this.cg2ndList = cg2ndList;
	}
	
}
