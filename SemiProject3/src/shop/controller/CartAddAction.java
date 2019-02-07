package shop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import member.model.MemberVO;
import shop.model.InterProductDAO;
import shop.model.ProductDAO;

public class CartAddAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		super.setCglist(req);
		MemberVO member = super.getLoginMember(req);
		if(member==null) {
			req.setAttribute("msg", "로그인이 필요합니다.");
			req.setAttribute("loc", "login.do");
			super.setViewPage("/WEB-INF/msg.jsp");
			return;
		}
		String pcode = req.getParameter("postpcode");
		String color = req.getParameter("color");
		String psize = req.getParameter("size");
		String oqty = req.getParameter("oqty");
		InterProductDAO pdao = new ProductDAO();
		int stock_idx = pdao.getStockIdx(pcode, color, psize, true);
		int n=pdao.addCart(member.getUserid(), pcode, stock_idx, Integer.parseInt(oqty));
		String msg = "",loc="";
		if(n==1) {
			msg="장바구니 추가완료";
			loc="cart.do";
		}
		else {
			msg="장바구니 추가실패";
			loc="javascript:history.back();";
		}
		req.setAttribute("msg", msg);
		req.setAttribute("loc", loc);
		super.setViewPage("/WEB-INF/msg.jsp");
	}

}
