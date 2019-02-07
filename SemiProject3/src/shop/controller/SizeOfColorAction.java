package shop.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import shop.model.InterProductDAO;
import shop.model.ProductDAO;

public class SizeOfColorAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String pcode = req.getParameter("pcode");
		String color = req.getParameter("color");
		InterProductDAO pdao = new ProductDAO();
		List<HashMap<String, Object>> sizeList = pdao.getSizeOfColor(pcode, color);
		req.setAttribute("sizeList", sizeList);
		super.setViewPage("/WEB-INF/shop/sizeOfColor.jsp");
	}

}
