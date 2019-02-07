package start.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import shop.model.CategoryVO;
import shop.model.InterProductDAO;
import shop.model.ProductDAO;
import shop.model.ProductVO;

public class QnAAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/qna/QnA.jsp");
		
		InterProductDAO pdao = new ProductDAO();
		List<CategoryVO> cglist = pdao.getAllCategory();
		List<ProductVO> productList = pdao.getAllProduct();
		HttpSession session = req.getSession();
		session.setAttribute("cglist", cglist);
		req.setAttribute("productList", productList);

	}

}
