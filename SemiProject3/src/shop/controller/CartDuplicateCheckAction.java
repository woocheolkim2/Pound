package shop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import common.controller.AbstractController;
import shop.model.InterProductDAO;
import shop.model.ProductDAO;

public class CartDuplicateCheckAction extends AbstractController {
	
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String pcode = req.getParameter("pcode");
		String color = req.getParameter("color");
		String psize = req.getParameter("psize");
		InterProductDAO pdao = new ProductDAO();
		int stock_idx = pdao.getStockIdx(pcode, color, psize, true);
		boolean cartCheck = pdao.checkDuplicateProduct(super.getLoginMember(req).getUserid(), stock_idx);
		
		JSONObject jobj = new JSONObject();
		jobj.put("cartCheck", cartCheck);
		jobj.put("stock_idx", stock_idx);
		String str_jobj=jobj.toString();
		System.out.println(str_jobj);
		System.out.println(str_jobj);
		req.setAttribute("str_jobj", str_jobj);
		super.setViewPage("/WEB-INF/json.jsp");
	}

}
