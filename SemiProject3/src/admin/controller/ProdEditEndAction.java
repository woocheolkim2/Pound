package admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import shop.model.*;

public class ProdEditEndAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		String[] psize = req.getParameterValues("psize");
		String[] color = req.getParameterValues("color");
		String[] pqty = req.getParameterValues("pqty");			
		String pcode = req.getParameter("fk_pcode");
		String pname = req.getParameter("pname");
		String price = req.getParameter("price");
		
		
		
		ProductDAO pdao = new ProductDAO();
		
		int n = pdao.updateProduct(pcode, psize, color, pqty, pname, price);
		
		String msg = (n > 0)?"제품정보 변경 성공!!":"제품정보 변경 실패!!";
		String loc = (n > 0)?"prodManagement.do":"javascript:history.back();";
		//System.out.println(n);
		
		req.setAttribute("msg", msg);
		req.setAttribute("loc", loc);
					
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/msg.jsp");
		
	}

}
