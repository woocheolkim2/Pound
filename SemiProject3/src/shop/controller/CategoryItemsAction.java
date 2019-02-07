package shop.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import shop.model.CategoryVO;
import shop.model.InterProductDAO;
import shop.model.ProductDAO;
import shop.model.ProductVO;

public class CategoryItemsAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		super.setCglist(req);
		String cg1stcode = req.getParameter("cg1stcode");
		InterProductDAO pdao = new ProductDAO();
		CategoryVO category = pdao.getOneCategory(cg1stcode,true);
		List<ProductVO> plist = pdao.getProductByCategory(cg1stcode);	
		req.setAttribute("searchItems", plist);
		req.setAttribute("category", category);
		super.setViewPage("/WEB-INF/shop/categoryItems.jsp");
	}

}
