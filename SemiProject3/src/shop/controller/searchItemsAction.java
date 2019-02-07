package shop.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import shop.model.InterProductDAO;
import shop.model.ProductDAO;
import shop.model.ProductVO;

public class searchItemsAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		super.setCglist(req);
		String searchName = req.getParameter("searchItemName");
		InterProductDAO pdao = new ProductDAO();
		List<ProductVO> searchItemsList = pdao.searchItemsByName(searchName);
		req.setAttribute("searchItems", searchItemsList);
		req.setAttribute("searchItemsSize", searchItemsList.size());
		super.setViewPage("/WEB-INF/shop/searchItems.jsp");
	}

}
