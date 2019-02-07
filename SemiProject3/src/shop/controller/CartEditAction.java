package shop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import common.controller.AbstractController;
import shop.model.InterProductDAO;
import shop.model.ProductDAO;

public class CartEditAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String cart_idx = req.getParameter("cart_idx");
		String oqty = req.getParameter("oqty");
		InterProductDAO pdao = new ProductDAO();
		int n = pdao.editCartOqty(cart_idx,Integer.parseInt(oqty));
		JSONObject jobj = new JSONObject();
		jobj.put("n", n);
		String str_jobj = jobj.toString();
		req.setAttribute("str_jobj", str_jobj);
		super.setViewPage("/WEB-INF/json.jsp");
	}

}
