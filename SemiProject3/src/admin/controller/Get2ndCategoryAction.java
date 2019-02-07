package admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import common.controller.AbstractController;
import member.model.MemberVO;
import shop.model.CategoryVO;
import shop.model.InterProductDAO;
import shop.model.ProductDAO;

public class Get2ndCategoryAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String cg_1st_code = req.getParameter("cg_1st_code");
		InterProductDAO pdao = new ProductDAO();
		List<CategoryVO> categoryList = pdao.getAllCategory();
		req.setAttribute("categoryList", categoryList);
		req.setAttribute("cg_1st_code", cg_1st_code);
		CategoryVO category = pdao.getOneCategory(cg_1st_code,true);
		System.out.println(category.getCg2ndList().size());
		req.setAttribute("category", category);
		super.setViewPage("/WEB-INF/admin/get2ndCategory.jsp");
	}

}
