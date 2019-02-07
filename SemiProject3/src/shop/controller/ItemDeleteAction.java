package shop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import common.controller.AbstractController;
import member.model.MemberVO;
import shop.model.InterProductDAO;
import shop.model.ProductDAO;

public class ItemDeleteAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String cart_idx = req.getParameter("cart_idx");
		InterProductDAO pdao = new ProductDAO();
		int n = pdao.deleteCart(cart_idx);
		System.out.println(n);
		JSONObject jobj = new JSONObject();
		jobj.put("n", n);
		String str_jobj = jobj.toString();
		req.setAttribute("str_jobj", str_jobj);
		super.setViewPage("/WEB-INF/json.jsp");
	}

}
