package start.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import shop.model.CategoryVO;
import shop.model.InterProductDAO;
import shop.model.ProductDAO;
import shop.model.ProductVO;

public class HomeAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		InterProductDAO pdao = new ProductDAO();
		List<CategoryVO> cglist = pdao.getAllCategory();
		List<ProductVO> productList = pdao.getAllProduct();
		req.setAttribute("cglist", cglist);
		req.setAttribute("productList", productList);
		super.setViewPage("/WEB-INF/index.jsp");
	}

}
