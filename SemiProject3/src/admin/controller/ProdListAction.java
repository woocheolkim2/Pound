package admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import shop.model.InterProductDAO;
import shop.model.ProductDAO;
import shop.model.ProductVO;

public class ProdListAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(!"POST".equalsIgnoreCase(req.getMethod())) {
			req.setAttribute("msg", "비정상적인 접근입니다.");
			req.setAttribute("loc", "javascript:history.back();");
			super.setViewPage("/WEB-INF/msg.jsp");
			return;
		}
		InterProductDAO pdao = new ProductDAO();
		List<ProductVO> plist = pdao.getAllProduct();
		req.setAttribute("prodList", plist);
		req.setAttribute("goPage", "productManagement.jsp");
        super.setViewPage("/WEB-INF/admin/index.jsp");
	}

}
