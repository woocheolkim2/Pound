package shop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import common.controller.AbstractController;
import shop.model.InterProductDAO;
import shop.model.ProductDAO;

public class GetPqtyAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String fk_pcode = req.getParameter("pcode");
		String color = req.getParameter("color");
		String size = req.getParameter("size");
		System.out.println(fk_pcode+color+size);
		InterProductDAO pdao = new ProductDAO();
		int stock_idx = pdao.getStockIdx(fk_pcode, color, size, true);
		int pqty = pdao.getPqty(stock_idx);
		System.out.println(pqty);
		JSONObject jobj = new JSONObject();
		jobj.put("pqty", pqty);
		String str_jobj = jobj.toString();
		req.setAttribute("str_jobj", str_jobj);
		super.setViewPage("/WEB-INF/json.jsp");
	}

}
